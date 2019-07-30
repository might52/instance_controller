package com.might.instance_controller.models.auth.request;

import java.io.Serializable;

public class User implements Serializable {
    private Domain domain;
    private String name;
    private String password;

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
