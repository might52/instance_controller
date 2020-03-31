package org.might.instancecontroller.controllers;

import org.might.instancecontroller.dba.entity.Function;
import org.might.instancecontroller.models.function.FunctionModel;
import org.might.instancecontroller.services.ComputeService;
import org.might.instancecontroller.services.FunctionService;
import org.might.instancecontroller.services.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/function")
public class InstanceController {

    private final ComputeService computeService;
    private final FunctionService functionService;
    private final ServerService serverService;

    @Autowired
    public InstanceController(ComputeService computeService,
                              FunctionService functionService,
                              ServerService serverService) {
        this.computeService = computeService;
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
}
