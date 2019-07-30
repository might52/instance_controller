package com.might.instance_controller.models.auth.request;

import java.io.Serializable;

public class Request implements Serializable {
    private Auth auth;

    public Auth getAuth() {
        return auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }
}
