package org.might.instancecontroller.models.monitoring;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class HostTemplateModel implements Serializable {

    private static final long serialVersionUID = -7479105107764180770L;

    @JsonProperty("templateid")
    private String templateid;

    public String getTemplateid() {
        return templateid;
    }

    public void setTemplateid(String templateid) {
        this.templateid = templateid;
    }
}
