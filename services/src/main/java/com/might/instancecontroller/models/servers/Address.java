package com.might.instancecontroller.models.servers;

import java.util.List;

public class Address {
    private String name;
    private List<NetworkLabel> networkLabels;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<NetworkLabel> getNetworkLabels() {
        return networkLabels;
    }

    public void setNetworkLabels(List<NetworkLabel> networkLabels) {
        this.networkLabels = networkLabels;
    }

    class NetworkLabel {
        private String addr;
        private int version;

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }
    }
}
