package com.might.instancecontroller.services;

import com.might.instancecontroller.models.servers.Instance;

import java.util.List;

public interface ComputeService {
    Object getListInstance();
    String getInstanceStatus(String instanceId);
    String getInstanceName(String instanceId);
    List<Instance> getInstanceList();
}
