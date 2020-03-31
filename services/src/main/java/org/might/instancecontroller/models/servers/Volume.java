package org.might.instancecontroller.models.servers;

import java.io.Serializable;

public class Volume implements Serializable {

    private static final long serialVersionUID = 8571054790360346592L;

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

    @Override
    public String toString() {
        return "Volume{" +
                "id='" + id + '\'' +
                ", delete_on_termination=" + delete_on_termination +
                '}';
    }
}
