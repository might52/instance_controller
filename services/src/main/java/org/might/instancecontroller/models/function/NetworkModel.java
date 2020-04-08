package org.might.instancecontroller.models.function;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NetworkModel {
    @JsonProperty("uuid")
    private String uuid;
    @JsonProperty(value = "fixed_ip")
    private String fixed_ip;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFixed_ip() {
        return fixed_ip;
    }

    public void setFixed_ip(String fixed_ip) {
        this.fixed_ip = fixed_ip;
    }


    public NetworkModel(String uuid) {
        this.uuid = uuid;
    }

    public NetworkModel(String uuid, String fixed_ip) {
        this.uuid = uuid;
        this.fixed_ip = fixed_ip;
    }
}