package com.might.instancecontroller.models.auth;

import java.io.Serializable;
import java.util.List;

public class Identity implements Serializable {
    private List<String> methods;
    private Password password;

    public List<String> getMethods() {
        return methods;
    }

    public void setMethods(List<String> methods) {
        this.methods = methods;
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }
}
