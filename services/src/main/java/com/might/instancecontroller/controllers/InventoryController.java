package com.might.instancecontroller.controllers;

import com.might.instancecontroller.annotations.RequireConnection;
import com.might.instancecontroller.models.servers.Instance;
import com.might.instancecontroller.services.ComputeService;
import com.might.instancecontroller.services.InstanceStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("api/v1/instance")
public class InventoryController {

    private ComputeService computeService;

    @Autowired
    public InventoryController(ComputeService computeService) {
        this.computeService = computeService;
    }

    @RequireConnection
    @GetMapping("/all/string")
    public String getInstanceListAsString(HttpServletResponse response) {
        String instanceList = computeService.getListInstance();
        return instanceList;
    }

    @RequireConnection
    @GetMapping("/all")
    public List<Instance> getInstanceList() {
        List<Instance> instances = computeService.getInstanceList();
        return instances;
    }

    @RequireConnection
    @GetMapping("/status/{instanceId}")
    public InstanceStatus getInstanceStatus(@PathVariable String instanceId) {
        String value = computeService.getInstanceStatus(instanceId);
        return InstanceStatus.getInstanceStatus(value);
    }

    @RequireConnection
    @GetMapping("/name/{instanceId}")
    public String getInstanceName(@PathVariable String instanceId) {
        return computeService.getInstanceName(instanceId);
    }

    @RequireConnection
    @GetMapping("/{instanceId}")
    public Instance getInstance(@PathVariable String instanceId) {
        Instance instance = (Instance) computeService.getInstance(instanceId);
        return instance;
    }
}
