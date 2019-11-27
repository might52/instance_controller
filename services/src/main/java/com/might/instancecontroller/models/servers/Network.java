package com.might.instancecontroller.models.servers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Network {

    private String macAddr;
    private int version;
    private String addr;
    private String type;

    public Network() {
    }

    public String getMacAddr() {
        return macAddr;
    }

    public void setMacAddr(String macAddr) {
        this.macAddr = macAddr;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Network)) {
            return false;
        }

        Network network = (Network) o;

        if (getVersion() != network.getVersion()) {
            return false;
        }

        if (!getMacAddr().equals(network.getMacAddr())) {
            return false;
        }

        if (!getAddr().equals(network.getAddr())) {
            return false;
        }

        return getType().equals(network.getType());
    }

    @Override
    public int hashCode() {
        int result = getMacAddr().hashCode();
        result = 31 * result + getVersion();
        result = 31 * result + getAddr().hashCode();
        result = 31 * result + getType().hashCode();
        return result;
    }
}


