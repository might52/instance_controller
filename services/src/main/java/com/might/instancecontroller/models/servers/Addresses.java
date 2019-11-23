package com.might.instancecontroller.models.servers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.Map;

@JsonRootName("addresses")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Addresses {
    private Map<String, Network> networks;

    public Map<String, Network> getNetworks() {
        return networks;
    }

    public void setNetworks(Map<String, Network> networks) {
        this.networks = networks;
    }
}
