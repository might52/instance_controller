package org.might.instancecontroller.models.monitoring;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class HostResult {
    @JsonProperty("hostids")
    private List<String> hostids;

    public List<String> getHostids() {
        return hostids;
    }

    public void setHostids(List<String> hostids) {
        this.hostids = hostids;
    }
}
