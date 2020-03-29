package org.might.instancecontroller.models.auth;

import java.io.Serializable;

public class Scope implements Serializable {
    private Project project;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
