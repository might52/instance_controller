package org.might.instancecontroller.models.auth;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = 2359256489435414956L;

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
