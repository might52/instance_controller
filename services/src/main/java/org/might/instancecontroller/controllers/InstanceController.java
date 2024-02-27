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
package org.might.instancecontroller.controllers;

import org.might.instancecontroller.annotations.RequireConnection;
import org.might.instancecontroller.dba.entity.Function;
import org.might.instancecontroller.dba.entity.Server;
import org.might.instancecontroller.models.function.FunctionModel;
import org.might.instancecontroller.models.monitoring.HostResponse;
import org.might.instancecontroller.models.servers.OpenstackServer;
import org.might.instancecontroller.services.ComputeService;
import org.might.instancecontroller.services.ConfigurationService;
import org.might.instancecontroller.services.FlavorService;
import org.might.instancecontroller.services.FunctionService;
import org.might.instancecontroller.services.ImageService;
import org.might.instancecontroller.services.InstanceService;
import org.might.instancecontroller.services.MonitoringService;
import org.might.instancecontroller.services.ServerService;
import org.might.instancecontroller.utils.FunctionHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("api/v1/function")
public class InstanceController {

    private final ImageService imageService;
    private final FlavorService flavorService;
    private final ConfigurationService configurationService;
    private final FunctionService functionService;
    private final ServerService serverService;
    private final InstanceService instanceService;
    private final MonitoringService monitoringService;
    private final ComputeService computeService;

    private static final String FUNCTION_HAS_SERVER =
            "Function contains the instantiated servers or " +
            "functions are equal.";

    private static final Logger LOGGER = LoggerFactory.getLogger(InstanceController.class);

    @Autowired
    public InstanceController(ImageService imageService,
                              FlavorService flavorService,
                              ConfigurationService configurationService,
                              FunctionService functionService,
                              ServerService serverService,
                              InstanceService instanceService,
                              MonitoringService monitoringService,
                              ComputeService computeService) {
        this.imageService = imageService;
        this.flavorService = flavorService;
        this.configurationService = configurationService;
        this.functionService = functionService;
        this.serverService = serverService;
        this.instanceService = instanceService;
        this.monitoringService = monitoringService;
        this.computeService = computeService;
    }

    @GetMapping("/all")
    public List<FunctionModel> getInstanceList () {
        List<FunctionModel> functionModelList = new ArrayList<>();
        for (Function function :
                functionService.getAll()) {
            functionModelList.add(
                    new FunctionModel(
                            function,
                            FunctionHelper.getFunctionServers(function),
                            FunctionHelper.getFunctionStatusByFunction(function),
                            FunctionHelper.getActiveEventsByFunctionWithLag(function))
            );
        }

        return functionModelList;
    }

    @GetMapping("/{id}")
    public FunctionModel getInstanceById (
            @PathVariable Long id) {
        Function function = new Function();
        if (functionService.getFunctionById(id).isPresent()) {
            function = functionService.getFunctionById(id).get();
        }

        return new FunctionModel(
                function,
                FunctionHelper.getFunctionServers(function),
                FunctionHelper.getFunctionStatusByFunction(function),
                FunctionHelper.getActiveEventsByFunctionWithLag(function));
    }

    @PostMapping("/")
    public void saveFunction(@RequestBody Function function) {
        LOGGER.debug("Function body: {}", function);
        LOGGER.debug("Function id: {}", function.getId());
        LOGGER.debug("Compare function, is equals: {}", FunctionHelper.compareWithDBFunction(function));
        // TODO: add validations for servers, which is really work with function.
        if (FunctionHelper.compareWithDBFunction(function) &&
                !FunctionHelper.getFunctionServers(function).isEmpty()) {
            LOGGER.error(FUNCTION_HAS_SERVER);
            throw new RuntimeException(FUNCTION_HAS_SERVER);
        }

        configurationService.saveConfiguration(function.getConfiguration());
        imageService.saveImage(function.getImage());
        flavorService.saveFlavor(function.getFlavor());
        functionService.saveFunction(function);
    }

    @RequireConnection
    @PostMapping("/instantiate/{id}")
    public void instantiate(@PathVariable Long id) throws InterruptedException {
        Function function = FunctionHelper.getFunctionById(id);
        instanceService.instantiate(function);
    }

    @RequireConnection
    @PostMapping("/instantiate/reinstantiate/{serverId}")
    public void reInstantiate(@PathVariable Long serverId) throws InterruptedException {
        Server server = serverService.getServerById(serverId).isPresent() ?
                serverService.getServerById(serverId).get() :
                null;
        if (server == null) {
            return;
        }

        if (server.getFunction() == null) {
            return;
        }

        instanceService.reInstantiateByServerId(server.getId());
    }

    @RequireConnection
    @PutMapping("/instantiate/monitoring/{serverId}")
    public void reSetupMonitoring(@PathVariable Long serverId) {
        Server server = serverService.getServerById(serverId).isPresent() ?
                serverService.getServerById(serverId).get() :
                null;
        if (Objects.isNull(server)) {
            return;
        }

        if (Objects.nonNull(server.getMonitoringId())) {
            monitoringService.removeMonitoring(server);
        }

        OpenstackServer openstackServer = computeService.getServer(server.getServerId());
        HostResponse hostResponse = monitoringService.setUpMonitoring(openstackServer);
        server.setMonitoringId(Long.parseLong(hostResponse.getResult().getHostids().get(0)));
        serverService.saveServer(server);
    }

    @RequireConnection
    @DeleteMapping("/instantiate/{functionId}")
    public void release(@PathVariable Long functionId) throws InterruptedException {
        Function function = FunctionHelper.getFunctionById(functionId);
        Server server = FunctionHelper.getServerToRelease(function);
        if (server != null) {
            instanceService.releaseInstanceByServerId(server.getId());
        }
    }


    @RequireConnection
    @DeleteMapping("/instantiate/{functionId}/{serverId}")
    public void release(@PathVariable Long functionId,
                        @PathVariable Long serverId) throws InterruptedException {
        Function function = FunctionHelper.getFunctionById(functionId);
        List<Server> serverList = FunctionHelper.getFunctionServers(function);
        if (serverList.stream().anyMatch(el -> el.getId().equals(serverId))) {
            instanceService.releaseInstanceByServerId(serverId);
        }
    }
}
