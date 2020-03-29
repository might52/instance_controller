package org.might.instancecontroller.models.auth;

import java.io.Serializable;

public class Project implements Serializable {
    private String name;
    private Domain domain;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }
}
