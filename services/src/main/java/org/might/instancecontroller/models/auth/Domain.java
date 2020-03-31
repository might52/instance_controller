package org.might.instancecontroller.models.auth;

import java.io.Serializable;

public class Domain implements Serializable {

    private static final long serialVersionUID = 5454763492993565402L;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
