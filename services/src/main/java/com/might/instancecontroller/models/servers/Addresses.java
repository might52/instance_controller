package com.might.instancecontroller.models.servers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.List;
import java.util.Map;

@JsonRootName("addresses")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Addresses {
    private Map<String, List<Network>> networks;

    public Map<String, List<Network>> getNetworks() {
        return networks;
    }

    public void setNetworks(Map<String, List<Network>> networks) {
        this.networks = networks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Addresses)) {
            return false;
        }

        Addresses addresses = (Addresses) o;

        return getNetworks().equals(addresses.getNetworks());
    }

    @Override
    public int hashCode() {
        return getNetworks().hashCode();
    }
}
