package com.might.instancecontroller.models.servers;

import com.fasterxml.jackson.annotation.*;

import java.util.List;
import java.util.Map;

@JsonRootName(value = "server")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Server {
    @JsonProperty("OS-EXT-STS:task_state")
    private String taskState;
    @JsonProperty("addresses")
    private Addresses addresses;
    @JsonProperty("links")
    private List<Link> links;
    @JsonProperty("image")
    private Image image;
    @JsonProperty("OS-EXT-STS:vm_state")
    private String vmState;
    @JsonProperty("OS-EXT-SRV-ATTR:instance_name")
    private String instanceName;
    @JsonProperty("OS-SRV-USG:launched_at")
    private String launchedAt;
    @JsonProperty("flavor")
    private Flavor flavor;
    @JsonProperty("id")
    private String id;
    @JsonProperty(value = "security_groups")
    private List<SecurityGroup> securityGroups;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("OS-DCF:diskConfig")
    private String diskConfig;
    @JsonProperty("accessIPv4")
    private String accessIPv4;
    @JsonProperty("accessIPv6")
    private String accessIPv6;
    @JsonProperty("progress")
    private int progress;
    @JsonProperty("OS-EXT-STS:power_state")
    private int powerState;
    @JsonProperty("OS-EXT-AZ:availability_zone")
    private String availabilityZone;
    @JsonProperty("config_drive")
    private String configDrive;
    @JsonProperty("status")
    private String status;
    @JsonProperty("updated")
    private String updated;
    @JsonProperty("hostId")
    private String hostId;
    @JsonProperty("OS-EXT-SRV-ATTR:host")
    private String host;
    @JsonProperty("OS-SRV-USG:terminated_at")
    private String terminatedAt;
    @JsonProperty("key_name")
    private String keyName;
    @JsonProperty("OS-EXT-SRV-ATTR:hypervisor_hostname")
    private String hypervisorHostname;
    @JsonProperty("name")
    private String name;
    @JsonProperty("created")
    private String created;
    @JsonProperty("tenant_id")
    private String tenantId;
    @JsonProperty("os-extended-volumes:volumes_attached")
    private List<String> volumes_attached;
    @JsonProperty("metadata")
    private Map<String,String> metadata;

    public Server() {
    }

    public String getTaskState() {
        return taskState;
    }

    public void setTaskState(String taskState) {
        this.taskState = taskState;
    }

    public Addresses getAddresses() {
        return addresses;
    }

    public void setAddresses(Addresses addresses) {
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
