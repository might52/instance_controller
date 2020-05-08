package org.might.instancecontroller.controllers;

import org.might.instancecontroller.annotations.RequireConnection;
import org.might.instancecontroller.dba.entity.Function;
import org.might.instancecontroller.dba.entity.Server;
import org.might.instancecontroller.models.function.FunctionModel;
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

    private final ImageService imageService;
    private final FlavorService flavorService;
    private final ConfigurationService configurationService;
    private final FunctionService functionService;
    private final ServerService serverService;
    private final InstanceService instanceService;

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
                              InstanceService instanceService) {
        this.imageService = imageService;
        this.flavorService = flavorService;
        this.configurationService = configurationService;
        this.functionService = functionService;
        this.serverService = serverService;
        this.instanceService = instanceService;
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
