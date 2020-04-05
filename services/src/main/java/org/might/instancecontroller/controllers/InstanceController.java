package org.might.instancecontroller.controllers;

import org.might.instancecontroller.dba.entity.Function;
import org.might.instancecontroller.models.function.FunctionModel;
import org.might.instancecontroller.services.*;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(InstanceController.class);

    @Autowired
    public InstanceController(ComputeService computeService,
                              ImageService imageService,
                              FlavorService flavorService,
                              ConfigurationService configurationService,
                              FunctionService functionService,
                              ServerService serverService) {
        this.computeService = computeService;
        this.imageService = imageService;
        this.flavorService = flavorService;
        this.configurationService = configurationService;
        this.functionService = functionService;
        this.serverService = serverService;
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

        return new FunctionModel(function, serverService.getAll());
    }

    @PostMapping("/{id}")
    public void saveFunction(
            @PathVariable Long id, @RequestBody Function function) {
        LOGGER.debug("Function body: {}", function);
        LOGGER.debug("Function id: {}", id);
        LOGGER.debug("Compare function, is equals: {}", compareFunction(function));
        // TODO: add validations for servers, which is really work with function.
        if (compareFunction(function)) {
            return;
        }

        configurationService.saveConfiguration(function.getConfiguration());
        imageService.saveImage(function.getImage());
        flavorService.saveFlavor(function.getFlavor());
        functionService.saveFunction(function);
    }

    private Boolean compareFunction(Function function) {
        Function functionFromDB;
        if (this.functionService.getFunctionById(function.getId()).isPresent()) {
            functionFromDB = this.functionService.getFunctionById(function.getId()).get();
        } else {
            return false;
        }
        return function.equals(functionFromDB);

    }

}
