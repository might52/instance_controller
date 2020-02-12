package com.might.instancecontroller.controllers;

import com.might.instancecontroller.annotations.RequireConnection;
import com.might.instancecontroller.models.servers.Instance;
import com.might.instancecontroller.services.ComputeService;
import com.might.instancecontroller.services.InstanceStatus;
import com.might.instancecontroller.services.KeystoneService;
import com.might.instancecontroller.services.transport.RESTService;
import com.might.instancecontroller.services.transport.impl.RestResponse;
import com.might.instancecontroller.utils.OSUtils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static com.might.instancecontroller.utils.OSUtils.TIMEOUT;
import static com.might.instancecontroller.utils.OSUtils.TOKEN;

@RestController
@RequestMapping("login")
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    private RESTService restService;
    private OSUtils osUtils;
    private KeystoneService keystoneService;
    private ComputeService computeService;

    @Autowired
    public LoginController(RESTService restService,
                           OSUtils osUtils,
                           KeystoneService keystoneService,
                           ComputeService computeService) {
        this.restService = restService;
        this.osUtils = osUtils;
        this.keystoneService = keystoneService;
        this.computeService = computeService;
    }

    @GetMapping("/getToken")
    public Object auth(HttpServletResponse response) {
        RestResponse restResponse = (RestResponse) keystoneService.authenticate();
        response.addHeader(TOKEN, restResponse.getHeaders().getFirst(TOKEN));
        response.addHeader(TIMEOUT, restResponse.getHeaders().getFirst(TIMEOUT));
        return restResponse.getStringEntity();
    }

    @RequireConnection
    @GetMapping("/getServerListString")
    public Object getServices(HttpServletResponse response) {
        RestResponse restResponse = (RestResponse) computeService.getListInstance();
        return restResponse.getStringEntity();
    }

    @RequireConnection
    @GetMapping("/getServerList")
    public List<Instance> getInstanceList() {
        List<Instance> instances = computeService.getInstanceList();
        return instances;
    }

    @RequireConnection
    @GetMapping("/getInstanceStatus/{instanceId}")
    public InstanceStatus getInstanceStatus(@PathVariable String instanceId) {
         String value = computeService.getInstanceStatus(instanceId);
         return InstanceStatus.getInstanceStatus(value);
    }

    @RequireConnection
    @GetMapping("/getInstanceName/{instanceId}")
    public String getInstanceName(@PathVariable String instanceId) {
        return computeService.getInstanceName(instanceId);
    }

    @GetMapping
    public String initData(Model model) {
        return "Hello, world!";
    }

}
