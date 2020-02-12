package com.might.instancecontroller.models.servers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties
public class InstanceList implements Serializable {

    @JsonProperty("servers")
    private List<Server> instances;

    public List<Server> getInstances() {
        return instances;
    }

    public void setInstances(List<Server> instances) {
        this.instances = instances;
    }

    @Override
    public String toString() {
        return "InstanceList{" +
                "instances=" + instances +
                '}';
    }
}
