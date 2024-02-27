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
package org.might.instancecontroller.models.monitoring;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.List;

@JsonRootName("params")
public class HostParamsModel {
    @JsonProperty("host")
    private String host;
    @JsonProperty("description")
    private String description;
    private List<HostInterfaceModel> interfaces;
    @JsonProperty("groups")
    private List<HostGroupModel> groups;
    @JsonProperty("templates")
    private List<HostTemplateModel> templates;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<HostInterfaceModel> getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(List<HostInterfaceModel> interfaces) {
        this.interfaces = interfaces;
    }

    public List<HostGroupModel> getGroups() {
        return groups;
    }

    public void setGroups(List<HostGroupModel> groups) {
        this.groups = groups;
    }

    public List<HostTemplateModel> getTemplates() {
        return templates;
    }

    public void setTemplates(List<HostTemplateModel> templates) {
        this.templates = templates;
    }
}
