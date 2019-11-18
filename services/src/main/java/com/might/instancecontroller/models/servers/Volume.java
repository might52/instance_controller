package com.might.instancecontroller.models.servers;

public class Volume {
    private String id;
    private boolean delete_on_termination;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isDelete_on_termination() {
        return delete_on_termination;
    }

    public void setDelete_on_termination(boolean delete_on_termination) {
        this.delete_on_termination = delete_on_termination;
    }
}
