package org.might.instancecontroller.models.monitoring;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;
import java.sql.Time;

public class NotificationModel {
    @JsonProperty("status")
    private String status;
    @JsonProperty("name")
    private String name;
    @JsonProperty("time")
    private Time time;
    @JsonProperty("subject")
    private String subject;
    @JsonProperty("host_ip")
    private String hostIp;
    @JsonProperty(value = "date")
    private Date date;
    @JsonProperty("host_name")
    private String hostName;
    @JsonProperty("ack_status")
    private String ackStatus;
    @JsonProperty("problemId")
    private String problemId;
    @JsonProperty("active")
    private boolean active;
    @JsonProperty("host_desc")
    private String hostDesc;
    @JsonProperty("severity")
    private String severity;

    /**
     *   "status": "PROBLEM",
     *   "name": "Unavailable by ICMP ping",
     *   "time": "20:50:30",
     *   "subject": "problem|Unavailable by ICMP ping|192.168.20.107|PROBLEM|High",
     *   "host_ip": "192.168.20.107",
     *   "date": "2020.04.28",
     *   "host_name": "webServerFunction_webserv_1",
     *   "ack_status": "No",
     *   "problemId": "569",
     *   "active": true,
     *   "host_desc": "c5d8e3dd-5ffa-4ffa-b15c-7b9683ff14e1",
     *   "severity": "High"
     */

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = Date.valueOf(date.replace(".", "-"));
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

    public String getHostDesc() {
        return hostDesc;
    }

    public void setHostDesc(String hostDesc) {
        this.hostDesc = hostDesc;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }
}
