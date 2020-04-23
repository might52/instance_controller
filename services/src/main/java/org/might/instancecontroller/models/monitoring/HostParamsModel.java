package org.might.instancecontroller.models.monitoring;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.List;

@JsonRootName("params")
public class HostParamsModel {
    /**
     * "host": "webServerFunction_webserv_1"
     */
    @JsonProperty("host")
    private String host;
    /**
     * "description": "webServerFunction_webserv_1webServerFunction_webserv_1webServerFunction_webserv_1webServerFunction_webserv_1",
     */
    @JsonProperty("description")
    private String description;
    /**
     * "interfaces": [
     *      {
     *          "type": 1,
     *          "main": 1,
     *          "useip": 1,
     *          "ip": "192.168.20.101",
     *          "dns": "",
     *          "port": "10050"
     *      }
     * ],
     */
    private List<HostInterfaceModel> interfaces;
    /**
     * "groups": [
     *   {
     *      "groupid": "2"
     *   },
     *   {
     *      "groupid": "12"
     *   }
     * ],
     */
    @JsonProperty("groups")
    private List<HostGroupModel> groups;
    /**
     * "templates": [
     *     {
     *         "templateid": "10001"
     *     },
     *     {
     *         "templateid": "10186"
     *     }
     * ]
     */
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
