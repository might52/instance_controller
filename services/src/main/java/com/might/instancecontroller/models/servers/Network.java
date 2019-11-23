package com.might.instancecontroller.models.servers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
}


