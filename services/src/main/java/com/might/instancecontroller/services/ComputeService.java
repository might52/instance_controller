package com.might.instancecontroller.services;

public interface ComputeService {
    Object getListInstance();
    String getInstanceStatus(String instanceId);
    String getInstanceName(String instanceId);
}
