package org.might.instancecontroller.models.monitoring;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class HostDeletionModel {
    /**
     * {
     *     "jsonrpc": "2.0",
     *     "method": "host.delete",
     *     "params": [
     *     	"10275"
     *     ],
     *     "id": 1,
     *     "auth": "{{auth_token_zabbix}}"
     * }
     */

    /**
     * "jsonrpc": "2.0"
     */
    @JsonProperty("jsonrpc")
    private String jsonrpc;
    /**
     * "method": "host.delete"
     */
    @JsonProperty("method")
    private String method;

    @JsonProperty("params")
    private List<String> params;

    /**
     * "id": 1
     */
    @JsonProperty("id")
    private int id;
    /**
     * "auth": "{{auth_token_zabbix}}"
     */
    @JsonProperty("auth")
    private String auth;


    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }
}
