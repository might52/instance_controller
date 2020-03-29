package org.might.instancecontroller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class InstanceControllerApplication {
    public static void main(String[] args) {
        final SpringApplication app = new SpringApplication(InstanceControllerApplication.class);
        app.run(args);
    }
}
