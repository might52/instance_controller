package com.might.instance_controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.might.instance_controller")
public class InstanceControllerApplication {
    public static void main(String[] args) {
//        SpringApplication.run(InstanceControllerApplication.class, args);
        SpringApplication app = new SpringApplication(InstanceControllerApplication.class);
        app.run(args);
    }
}
