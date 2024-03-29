/*
 * MIT License
 *
 * Copyright (c) 2024 Andrei F._
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.might.instancecontroller.services.impl;

import org.might.instancecontroller.dba.entity.Function;
import org.might.instancecontroller.dba.entity.Server;
import org.might.instancecontroller.models.function.ServerCreateModel;
import org.might.instancecontroller.models.monitoring.HostResponse;
import org.might.instancecontroller.models.servers.OpenstackServer;
import org.might.instancecontroller.services.ComputeService;
import org.might.instancecontroller.services.ConfigurationVMService;
import org.might.instancecontroller.services.InstanceService;
import org.might.instancecontroller.services.MonitoringService;
import org.might.instancecontroller.services.ServerService;
import org.might.instancecontroller.utils.FunctionHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstanceServiceImpl implements InstanceService {

    private final ServerService serverService;
    private final ComputeService computeService;
    private final ConfigurationVMService configurationVMService;
    private final MonitoringService monitoringService;

    private static final Logger LOGGER = LoggerFactory.getLogger(InstanceServiceImpl.class);

    @Autowired
    public InstanceServiceImpl(
            ServerService serverService,
            ComputeService computeService,
            ConfigurationVMService configurationVMService,
            MonitoringService monitoringService
    ) {
        this.serverService = serverService;
        this.computeService = computeService;
        this.configurationVMService = configurationVMService;
        this.monitoringService = monitoringService;
    }

    @Override
    public void instantiate(Function function) throws InterruptedException {
        ServerCreateModel serverCreateModel =
                FunctionHelper.getServerCreateModelAutoNetwork(
                        function
                );

        Server serverDba = new Server();
        serverDba.setName(serverCreateModel.getName());
        serverDba.setFunction(function);
        serverService.saveServer(serverDba);

        createAndConfigureInstance(serverDba, serverCreateModel);

    }

    @Override
    public void reInstantiateByServerId(Long serverId) throws InterruptedException {
        Server serverDbaOld = serverService.getServerById(serverId).isPresent() ?
                serverService.getServerById(serverId).get() : null;

        OpenstackServer openstackServer = computeService.getServer(serverDbaOld.getServerId());

        LOGGER.debug("Save in memory instance settings, " +
                        "Instance name: {}, " +
                        "Openstack server id: {}, " +
                        "external network: {}, " +
                        "internal network: {}",
                serverDbaOld.getName(),
                serverDbaOld.getServerId(),
                openstackServer.getAddresses()
        );

        ServerCreateModel serverCreateModel =
                FunctionHelper.getServerCreateModelWithParticularNetwork(
                        serverDbaOld,
                        openstackServer
                );

        releaseInstanceByServerId(serverDbaOld.getId());

        Server serverDba = new Server();
        serverDba.setName(serverCreateModel.getName());
        serverDba.setFunction(serverDbaOld.getFunction());
        serverService.saveServer(serverDba);

        createAndConfigureInstance(serverDba, serverCreateModel);
    }

    @Override
    public void releaseInstanceByServerId(Long serverId) throws InterruptedException {
        Server server;
        if (serverService.getServerById(serverId).isPresent()) {
            server = serverService.getServerById(serverId).get();
        } else {
            LOGGER.error("Server with Id: {} didn't find at DB.", serverId);
            throw new RuntimeException(
                    String.format(
                            "Server with Id: %s didn't find at DB.",
                            serverId
                    )
            );
        }

        if (server.getMonitoringId() != null) {
            LOGGER.debug("Skip removing from monitoring system. Id: {}, ServerId: {}",
                    server.getId(),
                    server.getServerId());
            monitoringService.removeMonitoring(server);
        }

        OpenstackServer openstackServer = computeService.getServer(server.getServerId());
        if (openstackServer != null) {
            LOGGER.debug(
                    "Perform server deletion in Openstack: {} with DB id: {}",
                    server.getServerId(),
                    server.getId()
            );
            computeService.deleteServer(server.getServerId());
        }
        Thread.sleep(15000);

        LOGGER.debug(
                "Deletion the server: {} and Openstack Id: {} in DB",
                server.getName(),
                server.getServerId()
        );

        serverService.deleteServer(server);
    }

    private void createAndConfigureInstance(final Server serverDba, final ServerCreateModel serverCreateModel)
            throws InterruptedException {
        OpenstackServer openstackServer =
                this.computeService.createServer(
                        serverCreateModel
                );

        LOGGER.debug(
                "Save the newly created server: {} at openstack with Id: {} at DBs",
                serverCreateModel.getName(),
                openstackServer.getId()
        );

        serverDba.setServerId(openstackServer.getId());
        serverService.saveServer(serverDba);

        //TODO: add configuration set up, wait 15 minutes.
        LOGGER.debug("Waiting for instantiation the VM: {} minutes.", 900000 / 60 / 1000);
        Thread.sleep(900000);

        openstackServer = computeService.getServer(openstackServer.getId());

        configurationVMService.setUpVM(
                openstackServer
                        .getAddresses()
                        .getNetworks()
                        .get(FunctionHelper.NETWORK_NAME_PUBLIC)
                        .get(0)
                        .getAddr(),
                FunctionHelper.getScriptsForFunction(
                        serverDba.getFunction(),
                        openstackServer.getName()
                )
        );
        HostResponse hostResponse = monitoringService.setUpMonitoring(openstackServer);
        serverDba.setMonitoringId(Long.parseLong(hostResponse.getResult().getHostids().get(0)));
        serverService.saveServer(serverDba);
    }
}
