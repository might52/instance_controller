package org.might.instancecontroller.models.auth;

import java.io.Serializable;

public class Password implements Serializable {

    private static final long serialVersionUID = -1117237289454869152L;

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
