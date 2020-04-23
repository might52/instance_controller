package org.might.instancecontroller.models.monitoring;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.io.Serializable;

@JsonRootName("interface")
public class HostInterfaceModel implements Serializable {

    private static final long serialVersionUID = -5226217870892361475L;

    /**
     * "type": 1
     */
    @JsonProperty("type")
    private String type;
    /**
     * "main": 1
     */
    @JsonProperty("main")
    private String main;
    /**
     * "useip": 1
     */
    @JsonProperty("useip")
    private String useip;
    /**
     * "ip": "192.168.20.101"
     */
    @JsonProperty("ip")
    private String ip;
    /**
     * "dns": ""
     */
    @JsonProperty("dns")
    private String dns;
    /**
     * "port": "10050"
     */
    @JsonProperty("port")
    private String port;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getUseip() {
        return useip;
    }

    public void setUseip(String useip) {
        this.useip = useip;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDns() {
        return dns;
    }

    public void setDns(String dns) {
        this.dns = dns;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
