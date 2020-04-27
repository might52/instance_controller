package org.might.instancecontroller.controllers;

import org.might.instancecontroller.annotations.RequireConnection;
import org.might.instancecontroller.dba.entity.Function;
import org.might.instancecontroller.dba.entity.Server;
import org.might.instancecontroller.models.function.FunctionModel;
import org.might.instancecontroller.models.function.ServerCreateModel;
import org.might.instancecontroller.models.monitoring.HostResponse;
import org.might.instancecontroller.models.servers.OpenstackServer;
import org.might.instancecontroller.services.*;
import org.might.instancecontroller.utils.FunctionHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/function")
public class InstanceController {

    private final ComputeService computeService;
    private final ImageService imageService;
    private final FlavorService flavorService;
    private final ConfigurationService configurationService;
    private final FunctionService functionService;
    private final ServerService serverService;
    private final ConfigurationVMService configurationVMService;
    private final MonitoringService monitoringService;

    private static final Logger LOGGER = LoggerFactory.getLogger(InstanceController.class);

    @Autowired
    public InstanceController(ComputeService computeService,
                              ImageService imageService,
                              FlavorService flavorService,
                              ConfigurationService configurationService,
                              FunctionService functionService,
                              ServerService serverService,
                              ConfigurationVMService configurationVMService,
                              MonitoringService monitoringService) {
        this.computeService = computeService;
        this.imageService = imageService;
        this.flavorService = flavorService;
        this.configurationService = configurationService;
        this.functionService = functionService;
        this.serverService = serverService;
        this.configurationVMService = configurationVMService;
        this.monitoringService = monitoringService;
    }

    @GetMapping("/all")
    public List<FunctionModel> getInstanceList () {
        List<FunctionModel> functionModelList = new ArrayList<>();
        for (Function function :
                functionService.getAll()) {
            functionModelList.add(new FunctionModel(function, serverService.getAll()));
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

        return new FunctionModel(function, FunctionHelper.getFunctionServers(function));
    }

    @PostMapping("/")
    public void saveFunction(@RequestBody Function function) {
        LOGGER.debug("Function body: {}", function);
        LOGGER.debug("Function id: {}", function.getId());
        LOGGER.debug("Compare function, is equals: {}", FunctionHelper.compareFunction(function));
        // TODO: add validations for servers, which is really work with function.
        if (FunctionHelper.compareFunction(function) &&
                !FunctionHelper.getFunctionServers(function).isEmpty()) {
            LOGGER.error("Function contains the instantiated servers or " +
                    "functions are equal.");
            return;
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
        ServerCreateModel serverCreateModel =
                FunctionHelper.getServerCreateModelAutoNetwork(
                        function
                );

        OpenstackServer openstackServer =
                this.computeService.createServer(
                        serverCreateModel
                );

        LOGGER.debug(
                "Save the server: {} and Id: {} at DBs",
                serverCreateModel.getName(),
                openstackServer.getId()
        );

        Server serverDba = new Server();
        serverDba.setName(serverCreateModel.getName());
        serverDba.setFunction(function);
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
                        function,
                        openstackServer.getName()
                )
        );
        HostResponse hostResponse = monitoringService.setUpMonitoring(openstackServer);
        serverDba.setMonitoringId(Long.parseLong(hostResponse.getResult().getHostids().get(0)));
        serverService.saveServer(serverDba);
    }

    @RequireConnection
    @DeleteMapping("/instantiate/{id}")
    public void release(@PathVariable Long id) {
        Function function = FunctionHelper.getFunctionById(id);
//        OpenstackServer openstackServer =
//                this.computeService.createServer(
//                        serverCreateModel
//                );

        //TODO: add configuration set up, wait 15 minutes.
//        LOGGER.debug("Waiting for instantiation the VM: {} minutes.", 900000 / 60 / 1000);
//        Thread.sleep(900000);
//
//        openstackServer = computeService.getServer(openstackServer.getId());
//
//        configurationVMService.setUpVM(
//                openstackServer
//                        .getAddresses()
//                        .getNetworks()
//                        .get(FunctionHelper.NETWORK_NAME_PUBLIC)
//                        .get(0)
//                        .getAddr(),
//                FunctionHelper.getScriptsForFunction(
//                        function,
//                        openstackServer.getName()
//                )
//        );
//        monitoringService.setUpMonitoring(openstackServer);
    }

}
