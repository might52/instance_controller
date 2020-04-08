package org.might.instancecontroller.models.servers;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Instance implements Serializable {

    private static final long serialVersionUID = -7328720013906343772L;

    @JsonProperty("server")
    private OpenstackServer openstackServer;

    public OpenstackServer getOpenstackServer() {
        return openstackServer;
    }

    public void setOpenstackServer(OpenstackServer openstackServer) {
        this.openstackServer = openstackServer;
    }

    @Override
    public String toString() {
        return "Instance{" +
                "openstackServer=" + openstackServer.toString() +
                '}';
    }
}