package org.might.instancecontroller.controllers;

import org.might.instancecontroller.services.KeystoneService;
import org.might.instancecontroller.services.transport.impl.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

import static org.might.instancecontroller.utils.SettingsHelper.TIMEOUT;
import static org.might.instancecontroller.utils.SettingsHelper.OS_TOKEN;

@RestController
@RequestMapping("auth")
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    private KeystoneService keystoneService;

    @Autowired
    public AuthController(KeystoneService keystoneService) {
        this.keystoneService = keystoneService;
    }

    @GetMapping("/token")
    public Object auth(HttpServletResponse response) {
        RestResponse restResponse = (RestResponse) keystoneService.authenticate();
        response.addHeader(OS_TOKEN, restResponse.getHeaders().getFirst(OS_TOKEN));
        response.addHeader(TIMEOUT, restResponse.getHeaders().getFirst(TIMEOUT));
        return restResponse.getStringEntity();
    }

    @GetMapping
    public String initData(Model model) {
        return "Hello, world!";
    }

}
