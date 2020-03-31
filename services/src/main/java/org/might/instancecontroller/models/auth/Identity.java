package org.might.instancecontroller.models.auth;

import java.io.Serializable;
import java.util.List;

public class Identity implements Serializable {

    private static final long serialVersionUID = 6103096682277401834L;

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
