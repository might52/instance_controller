package com.might.instance_controller.controllers;

import com.might.instance_controller.annotations.RequireConnection;
import com.might.instance_controller.services.ComputeService;
import com.might.instance_controller.services.KeystoneServise;
import com.might.instance_controller.utils.OSProperties;
import com.might.instance_controller.services.transport.impl.RestResponse;
import com.might.instance_controller.services.transport.RESTService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.might.instance_controller.utils.OSProperties.TIMEOUT;
import static com.might.instance_controller.utils.OSProperties.TOKEN;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("login")
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    private RESTService restService;
    private OSProperties osProperties;
    private KeystoneServise keystoneServise;
    private ComputeService computeService;

    @Autowired
    public LoginController(RESTService restService,
                           OSProperties osProperties,
                           KeystoneServise keystoneServise,
                           ComputeService computeService) {
        this.restService = restService;
        this.osProperties = osProperties;
        this.keystoneServise = keystoneServise;
        this.computeService = computeService;
    }

    @GetMapping("/getTest")
    public void testGet(){
    }

    @GetMapping("/getToken")
    public Object auth(HttpServletResponse response){
        RestResponse restResponse = (RestResponse) keystoneServise.authenticate();
        response.addHeader(TOKEN, restResponse.getHeaders().getFirst(TOKEN));
        response.addHeader(TIMEOUT, restResponse.getHeaders().getFirst(TIMEOUT));
        return restResponse.getStringEntity();
    }

    @RequireConnection
    @GetMapping("/getServerList")
    public Object getServices(HttpServletResponse response) {
        RestResponse restResponse = (RestResponse) computeService.getListInstance();
        return restResponse.getStringEntity();
    }

    @GetMapping
    public String initData(Model model) {
        return "Hello, world!";
    }


}
