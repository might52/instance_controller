package org.might.instancecontroller.models.auth;

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
