package org.might.instancecontroller.models.monitoring;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HostResponse {
    /**
     * {
     *     "jsonrpc": "2.0",
     *     "result": {
     *         "hostids": [
     *             "10273"
     *         ]
     *     },
     *     "id": 1
     * }
     */
    @JsonProperty("jsonrpc")
    private String jsonrpc;
    @JsonProperty("result")
    private HostResult result;
    @JsonProperty("id")
    private String id;

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public HostResult getResult() {
        return result;
    }

    public void setResult(HostResult result) {
        this.result = result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}


