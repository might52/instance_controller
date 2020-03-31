package org.might.instancecontroller.models.auth;

import java.io.Serializable;

public class Scope implements Serializable {

    private static final long serialVersionUID = -562336948212825009L;

    private Project project;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
