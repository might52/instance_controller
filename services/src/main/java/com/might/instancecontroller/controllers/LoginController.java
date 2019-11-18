package com.might.instancecontroller.controllers;

import com.might.instancecontroller.annotations.RequireConnection;
import com.might.instancecontroller.services.ComputeService;
import com.might.instancecontroller.services.KeystoneService;
import com.might.instancecontroller.services.transport.RESTService;
import com.might.instancecontroller.services.transport.impl.RestResponse;
import com.might.instancecontroller.utils.OSProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

import static com.might.instancecontroller.utils.OSProperties.TIMEOUT;
import static com.might.instancecontroller.utils.OSProperties.TOKEN;

@RestController
@RequestMapping("login")
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    private RESTService restService;
    private OSProperties osProperties;
    private KeystoneService keystoneService;
    private ComputeService computeService;

    @Autowired
    public LoginController(RESTService restService,
                           OSProperties osProperties,
                           KeystoneService keystoneService,
                           ComputeService computeService) {
        this.restService = restService;
        this.osProperties = osProperties;
        this.keystoneService = keystoneService;
        this.computeService = computeService;
    }

    @GetMapping("/getTest")
    public void testGet() {
    }

    @GetMapping("/getToken")
    public Object auth(HttpServletResponse response) {
        RestResponse restResponse = (RestResponse) keystoneService.authenticate();
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
