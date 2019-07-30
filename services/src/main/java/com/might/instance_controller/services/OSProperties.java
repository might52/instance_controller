package com.might.instance_controller.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Configuration
@PropertySource(value = "classpath:service.properties", ignoreResourceNotFound = true)
public class OSProperties implements Serializable {

    public static final String TOKEN = "X-Subject-Token";
    public static final String TIMEOUT = "Keep-Alive";

    @Value("${auth.port}")
    public String AUTH_PORT;
    @Value("${compute.port}")
    public String COMPUTE_PORT;
    @Value("${openstack.host.ip}")
    public String OS_HOST_IP;
    @Value("${os.username}")
    public String OS_USERNAME;
    @Value("${os.password}")
    public String OS_PASSWORD;
    @Value("${os.auth.url}")
    public String OS_AUTH_URL;
    @Value("${os.project.name}")
    public String OS_PROJECT_NAME;
    @Value("${os.user.domain.name}")
    public String OS_USER_DOMAIN_NAME;
    @Value("${os.project.domain.name}")
    public String OS_PROJECT_DOMAIN_NAME;
    @Value("${os.identity.api.version}")
    public String OS_IDENTITY_API_VERSION;


}
