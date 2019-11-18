package com.might.instancecontroller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.might.instancecontroller")
public class InstanceControllerApplication {
    public static void main(String[] args) {
//        SpringApplication.run(InstanceControllerApplication.class, args);
        SpringApplication app = new SpringApplication(InstanceControllerApplication.class);
        app.run(args);
    }
}
