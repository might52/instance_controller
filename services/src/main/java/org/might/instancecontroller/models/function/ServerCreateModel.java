package org.might.instancecontroller.models.function;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.List;

@JsonRootName("server")
public class ServerCreateModel {
    @JsonProperty("name")
    private String name;
    @JsonProperty("imageRef")
    private String imageRef;
    @JsonProperty("flavorRef")
    private String flavorRef;
    @JsonProperty("OS-DCF:diskConfig")
    private String diskConfig;
    @JsonProperty("availability_zone")
    private String zone;
    @JsonProperty("networks")
    private List<NetworkModel> networks;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageRef() {
        return imageRef;
    }

    public void setImageRef(String imageRef) {
        this.imageRef = imageRef;
    }

    public String getFlavorRef() {
        return flavorRef;
    }

    public void setFlavorRef(String flavorRef) {
        this.flavorRef = flavorRef;
    }

    public String getDiskConfig() {
        return diskConfig;
    }

    public void setDiskConfig(String diskConfig) {
        this.diskConfig = diskConfig;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public List<NetworkModel> getNetworks() {
        return networks;
    }

    public void setNetworks(List<NetworkModel> networks) {
        this.networks = networks;
    }
}
