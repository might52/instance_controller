package org.might.instancecontroller.models.monitoring;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class HostGroupModel implements Serializable {

    private static final long serialVersionUID = -2383555847599954936L;

    @JsonProperty("groupid")
    private String groupId;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
