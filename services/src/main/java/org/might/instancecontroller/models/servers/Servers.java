package org.might.instancecontroller.models.servers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties
public class Servers implements Serializable {

    private static final long serialVersionUID = -2513310349377743466L;

    @JsonProperty("servers")
    private List<OpenstackServer> openstackServers;

    public List<OpenstackServer> getOpenstackServers() {
        return openstackServers;
    }

    public void setOpenstackServers(List<OpenstackServer> openstackServers) {
        this.openstackServers = openstackServers;
    }

}
