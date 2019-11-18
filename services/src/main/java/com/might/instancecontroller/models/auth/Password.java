package com.might.instancecontroller.models.auth;

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
