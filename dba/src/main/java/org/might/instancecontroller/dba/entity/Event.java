package org.might.instancecontroller.dba.entity;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "hostip")
    private String hostIp;
    @Column(name = "hostname")
    private String hostName;
    @Column(name = "ackStatus")
    private String ackStatus;
    @Column(name = "problemId")
    private String problemId;
    @Column(name = "active")
    private boolean active;
    @Column(name = "serverId")
    private String serverId;
    @Column(name = "severity")
    private String severity;
    @Column(name = "status")
    private String status;
    @Column(name = "time")
    private Time time;
    @Column(name = "recoveryTime")
    private Time recoveryTime;
    @Column(name = "date")
    private Date date;
    @Column(name = "recoveryDate")
    private Date recoveryDate;

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

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getAckStatus() {
        return ackStatus;
    }

    public void setAckStatus(String ackStatus) {
        this.ackStatus = ackStatus;
    }

    public String getProblemId() {
        return problemId;
    }

    public void setProblemId(String problemId) {
        this.problemId = problemId;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Time getRecoveryTime() {
        return recoveryTime;
    }

    public void setRecoveryTime(Time recoveryTime) {
        this.recoveryTime = recoveryTime;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getRecoveryDate() {
        return recoveryDate;
    }

    public void setRecoveryDate(Date recoveryDate) {
        this.recoveryDate = recoveryDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Event event = (Event) o;

        return name.equals(event.name) &&
                hostIp.equals(event.hostIp) &&
                hostName.equals(event.hostName) &&
                problemId.equals(event.problemId) &&
                serverId.equals(event.serverId) &&
                severity.equals(event.severity);
    }
}
