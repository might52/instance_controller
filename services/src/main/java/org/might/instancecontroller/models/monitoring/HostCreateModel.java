package org.might.instancecontroller.models.monitoring;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class HostCreateModel {
    /**
     * "jsonrpc": "2.0"
     */
    @JsonProperty("jsonrpc")
    private String jsonrpc;
    /**
     * "method": "host.create"
     */
    @JsonProperty("method")
    private String method;
    /**
     * params: {}
     */
    @JsonProperty("params")
    private HostParamsModel paramsModel;
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

    public HostParamsModel getParamsModel() {
        return paramsModel;
    }

    public void setParamsModel(HostParamsModel paramsModel) {
        this.paramsModel = paramsModel;
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