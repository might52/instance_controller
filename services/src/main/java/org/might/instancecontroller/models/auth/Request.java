package org.might.instancecontroller.models.auth;

import java.io.Serializable;

public class Request implements Serializable {

    private static final long serialVersionUID = -7097636709810366027L;

    private Auth auth;

    public Auth getAuth() {
        return auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }
}
