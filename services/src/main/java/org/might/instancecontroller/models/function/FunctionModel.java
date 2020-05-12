package org.might.instancecontroller.models.function;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.might.instancecontroller.dba.entity.Event;
import org.might.instancecontroller.dba.entity.Server;
import org.might.instancecontroller.dba.entity.Function;
import org.might.instancecontroller.services.FunctionStatus;

import java.io.Serializable;
import java.util.List;

public class FunctionModel implements Serializable {

    private static final long serialVersionUID = 476941359250274252L;
    @JsonIgnoreProperties
    private Function function;
    @JsonProperty("servers")
    private List<Server> serverList;
    @JsonProperty("events")
    private List<Event> eventList;
    @JsonProperty("functionStatus")
    private FunctionStatus functionStatus;

    public FunctionModel(Function function, List<Server> serverList) {
        this.function = function;
        this.serverList = serverList;
    }

    public FunctionModel(Function function, List<Server> serverList,
                         FunctionStatus functionStatus, List<Event> eventList) {
        this.function = function;
        this.serverList = serverList;
        this.functionStatus = functionStatus;
        this.eventList = eventList;
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

    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }

    public FunctionStatus getFunctionStatus() {
        return functionStatus;
    }

    public void setFunctionStatus(FunctionStatus functionStatus) {
        this.functionStatus = functionStatus;
    }
}
