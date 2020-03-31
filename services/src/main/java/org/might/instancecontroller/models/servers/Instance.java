package org.might.instancecontroller.models.servers;

import java.io.Serializable;

public class Instance implements Serializable {

    private static final long serialVersionUID = -7328720013906343772L;

    private Server server;

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    @Override
    public String toString() {
        return "Instance{" +
                "server=" + server.toString() +
                '}';
    }
}