package com.might.instancecontroller.models.servers;

import java.io.Serializable;

public class Instance implements Serializable {
    private Server server;

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }
}