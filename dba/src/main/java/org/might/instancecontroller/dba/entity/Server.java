package org.might.instancecontroller.dba.entity;

import javax.persistence.*;

@Entity
@Table(name = "server")
public class Server {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name", unique = true)
    private String name;
    @Column(name = "serverId", unique = true)
    private String serverId;
    @ManyToOne(targetEntity = Function.class, optional = false)
    private Function function;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Server)) {
            return false;
        }

        Server server = (Server) o;

        if (!getName().equals(server.getName())) {
            return false;
        }

        if (!getServerId().equals(server.getServerId())) {
            return false;
        }

        return getFunction().equals(server.getFunction());
    }

}
