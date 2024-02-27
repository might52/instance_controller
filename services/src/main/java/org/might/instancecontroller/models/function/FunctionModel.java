/*
 * MIT License
 *
 * Copyright (c) 2024 Andrei F._
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.might.instancecontroller.models.function;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.might.instancecontroller.dba.entity.Event;
import org.might.instancecontroller.dba.entity.Function;
import org.might.instancecontroller.dba.entity.Server;
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
