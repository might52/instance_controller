package org.might.instancecontroller.models.function;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.might.instancecontroller.dba.entity.Server;
import org.might.instancecontroller.dba.entity.Function;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class FunctionModel implements Serializable {

    private static final long serialVersionUID = 476941359250274252L;
    @JsonIgnoreProperties
    private Function function;
    @JsonProperty("servers")
    private List<Server> serverList;

    public FunctionModel(Function function, List<Server> serverList) {
        this.function = function;
        this.serverList = agregateServers(serverList);
    }

    private List<Server> agregateServers(List<Server> serverList) {
        return serverList
                        .stream()
                        .filter(el -> el.getFunction().equals(this.function))
                        .collect(Collectors.toList());
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }

    public List<Server> getServerList() {
        return serverList;
    }

    public void setServerList(List<Server> serverList) {
        this.serverList = serverList;
    }

}
