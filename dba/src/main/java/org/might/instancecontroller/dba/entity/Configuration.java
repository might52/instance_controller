package org.might.instancecontroller.dba.entity;

import javax.persistence.*;

@Entity
@Table(name = "configuration")
public class Configuration {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "script", length = 500)
    private String script;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Configuration)) {
            return false;
        }

        Configuration that = (Configuration) o;

        return getScript() != null ? getScript().equals(that.getScript()) : that.getScript() == null;
    }

}
