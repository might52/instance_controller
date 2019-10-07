package com.might.instance_controller.models.auth;

import javax.ws.rs.Produces;
import java.io.Serializable;

@Produces
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
