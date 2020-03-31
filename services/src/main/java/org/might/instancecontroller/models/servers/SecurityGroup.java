package org.might.instancecontroller.models.servers;

import java.io.Serializable;

public class SecurityGroup implements Serializable {

    private static final long serialVersionUID = 8221794171762254481L;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
