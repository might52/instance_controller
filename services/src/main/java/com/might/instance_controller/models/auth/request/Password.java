package com.might.instance_controller.models.auth.request;

import java.io.Serializable;

public class Password implements Serializable {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
