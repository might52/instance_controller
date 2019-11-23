package com.might.instancecontroller.models.servers;

import com.fasterxml.jackson.annotation.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@JsonRootName(value = "servers")
public class Servers implements Serializable {

    private String taskState;
    private List<Addresses> addresses;
    private List<Link> links;
    private Image image;
    private String vmState;
    private String instanceName;
    private String launchedAt;
    private Flavor flavor;
    private String id;
    private List<SecurityGroup> securityGroups;
    private String userId;
    private String diskConfig;
    private String accessIPv4;
    private String accessIPv6;
    private int progress;
    private int powerState;
    private String availabilityZone;
    private String configDrive;
    private String status;
    private String updated;
    private String hostId;
    private String host;
    private String terminatedAt;
    private String keyName;
    private String hypervisorHostname;
    private String name;
    private String created;
    private String tenantId;
    private List<String> volumes_attached;
    private Map<String,String> metadata;

    public Servers() {
    }

    public Servers(String taskState, List<Addresses> addresses,
                   List<Link> links, Image image, String vmState,
                   String instanceName, String launchedAt,
                   Flavor flavor, String id,
                   List<SecurityGroup> securityGroups,
                   String userId, String diskConfig,
                   String accessIPv4, String accessIPv6,
                   int progress, int powerState,
                   String availabilityZone, String configDrive,
                   String status, String updated,
                   String hostId, String host,
                   String terminatedAt, String keyName,
                   String hypervisorHostname, String name,
                   String created, String tenantId,
                   List<String> volumes_attached,
                   Map<String, String> metadata) {
        this.taskState = taskState;
        this.addresses = addresses;
        this.links = links;
        this.image = image;
        this.vmState = vmState;
        this.instanceName = instanceName;
        this.launchedAt = launchedAt;
        this.flavor = flavor;
        this.id = id;
        this.securityGroups = securityGroups;
        this.userId = userId;
        this.diskConfig = diskConfig;
        this.accessIPv4 = accessIPv4;
        this.accessIPv6 = accessIPv6;
        this.progress = progress;
        this.powerState = powerState;
        this.availabilityZone = availabilityZone;
        this.configDrive = configDrive;
        this.status = status;
        this.updated = updated;
        this.hostId = hostId;
        this.host = host;
        this.terminatedAt = terminatedAt;
        this.keyName = keyName;
        this.hypervisorHostname = hypervisorHostname;
        this.name = name;
        this.created = created;
        this.tenantId = tenantId;
        this.volumes_attached = volumes_attached;
        this.metadata = metadata;
    }

    public String getTaskState() {
        return taskState;
    }

    public void setTaskState(String taskState) {
        this.taskState = taskState;
    }

    public List<Addresses> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Addresses> addresses) {
        this.addresses = addresses;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getVmState() {
        return vmState;
    }

    public void setVmState(String vmState) {
        this.vmState = vmState;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public String getLaunchedAt() {
        return launchedAt;
    }

    public void setLaunchedAt(String launchedAt) {
        this.launchedAt = launchedAt;
    }

    public Flavor getFlavor() {
        return flavor;
    }

    public void setFlavor(Flavor flavor) {
        this.flavor = flavor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<SecurityGroup> getSecurityGroups() {
        return securityGroups;
    }

    public void setSecurityGroups(List<SecurityGroup> securityGroups) {
        this.securityGroups = securityGroups;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDiskConfig() {
        return diskConfig;
    }

    public void setDiskConfig(String diskConfig) {
        this.diskConfig = diskConfig;
    }

    public String getAccessIPv4() {
        return accessIPv4;
    }

    public void setAccessIPv4(String accessIPv4) {
        this.accessIPv4 = accessIPv4;
    }

    public String getAccessIPv6() {
        return accessIPv6;
    }

    public void setAccessIPv6(String accessIPv6) {
        this.accessIPv6 = accessIPv6;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getPowerState() {
        return powerState;
    }

    public void setPowerState(int powerState) {
        this.powerState = powerState;
    }

    public String getAvailabilityZone() {
        return availabilityZone;
    }

    public void setAvailabilityZone(String availabilityZone) {
        this.availabilityZone = availabilityZone;
    }

    public String getConfigDrive() {
        return configDrive;
    }

    public void setConfigDrive(String configDrive) {
        this.configDrive = configDrive;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getTerminatedAt() {
        return terminatedAt;
    }

    public void setTerminatedAt(String terminatedAt) {
        this.terminatedAt = terminatedAt;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getHypervisorHostname() {
        return hypervisorHostname;
    }

    public void setHypervisorHostname(String hypervisorHostname) {
        this.hypervisorHostname = hypervisorHostname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public List<String> getVolumes_attached() {
        return volumes_attached;
    }

    public void setVolumes_attached(List<String> volumes_attached) {
        this.volumes_attached = volumes_attached;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }
}
