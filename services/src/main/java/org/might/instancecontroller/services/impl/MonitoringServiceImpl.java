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

import com.fasterxml.jackson.core.type.TypeReference;
import org.might.instancecontroller.dba.entity.Event;
import org.might.instancecontroller.dba.entity.Server;
import org.might.instancecontroller.models.monitoring.HostCreateModel;
import org.might.instancecontroller.models.monitoring.HostDeletionModel;
import org.might.instancecontroller.models.monitoring.HostResponse;
import org.might.instancecontroller.models.servers.OpenstackServer;
import org.might.instancecontroller.services.EventService;
import org.might.instancecontroller.services.MonitoringService;
import org.might.instancecontroller.services.transport.RESTService;
import org.might.instancecontroller.utils.FunctionHelper;
import org.might.instancecontroller.utils.MonitoringHelper;
import org.might.instancecontroller.utils.SettingsHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Service
public class MonitoringServiceImpl implements MonitoringService {

    /**
     * Logger initialization.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(
            MonitoringServiceImpl.class
    );

    private static final String MONITORING_CREATION_TEMPLATE = "performed the setUp monitoring, hostId: {}";
    private static final String MONITORING_DELETION_TEMPLATE = "performed the remove monitoring, hostId: {}";
    private static final String RESOLVED = "RESOLVED";
    private final RESTService restService;
    private final SettingsHelper settingsHelper;
    private final EventService eventService;

    @Autowired
    public MonitoringServiceImpl(RESTService restService,
                                 SettingsHelper settingsHelper,
                                 EventService eventService) {
        this.restService = restService;
        this.settingsHelper = settingsHelper;
        this.eventService = eventService;
    }

    @Override
    public HostResponse setUpMonitoring(final OpenstackServer openstackServer) {
        LOGGER.debug("Perform set up monitoring for vm {}, ip address: {}, hostname: {}",
                openstackServer.getId(),
                openstackServer.getAddresses()
                        .getNetworks()
                        .get(FunctionHelper.NETWORK_NAME_PUBLIC)
                        .get(0)
                        .getAddr(),
                openstackServer.getName()
        );
        HostCreateModel hostCreateModel = MonitoringHelper.getHostCreateModel(openstackServer);
        HostResponse hostResponse = restService.post(
                settingsHelper.getZabbixUrl(),
                hostCreateModel,
                null,
                new TypeReference<HostResponse>() {
                });
        LOGGER.debug(MONITORING_CREATION_TEMPLATE, hostResponse.getResult().getHostids().get(0));
        return hostResponse;
    }

    @Override
    public HostResponse removeMonitoring(Server server) {
        LOGGER.debug("Removing server with id: {} " +
                        "from monitoring system with HostId: {} " +
                        "and OpesntackId: {}",
                server.getId(),
                server.getMonitoringId(),
                server.getServerId()
        );
        HostDeletionModel hostDeletionModel = MonitoringHelper.getHostDeletionModel(server);
        HostResponse hostResponse = restService.post(
                settingsHelper.getZabbixUrl(),
                hostDeletionModel,
                null,
                new TypeReference<HostResponse>() {
                });
        LOGGER.debug(MONITORING_DELETION_TEMPLATE, hostResponse.getResult().getHostids().get(0));
        LOGGER.debug("Clearing events in DBs related to the server Openstack: {}, monitoring id: {}, " +
                        "server id: {}, function name: {}",
                server.getName(),
                server.getMonitoringId(),
                server.getServerId(),
                server.getFunction().getName());
        List<Event> events = FunctionHelper.getActiveEventsByFunction(server.getFunction());
        events.forEach(event -> {
            if (event.getServerId().equals(server.getServerId())) {
                event.setActive(false);
                event.setRecoveryTime(new Time(System.currentTimeMillis()));
                event.setRecoveryDate(new Date(System.currentTimeMillis()));
                event.setStatus(RESOLVED);
                eventService.saveEvent(event);
            }
        });
        return hostResponse;
    }
}
