package com.might.instance_controller.models.auth.request;

import java.io.Serializable;

public class Domain implements Serializable {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
