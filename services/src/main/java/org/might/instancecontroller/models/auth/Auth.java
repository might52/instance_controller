package org.might.instancecontroller.models.auth;

import com.fasterxml.jackson.annotation.JsonRootName;

import java.io.Serializable;


@JsonRootName(value = "auth")
public class Auth implements Serializable {

    private static final long serialVersionUID = -5178464425661583285L;

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
