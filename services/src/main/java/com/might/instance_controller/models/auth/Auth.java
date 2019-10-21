package com.might.instance_controller.models.auth;

import com.fasterxml.jackson.annotation.JsonRootName;

import java.io.Serializable;


@JsonRootName(value = "auth")
public class Auth implements Serializable {
    private Identity identity;
    private Scope scope;

    public Identity getIdentity() {
        return identity;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }
}
