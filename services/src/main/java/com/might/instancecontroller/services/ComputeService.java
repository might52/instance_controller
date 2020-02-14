package com.might.instancecontroller.services;

import com.might.instancecontroller.models.servers.Instance;
import org.glassfish.jersey.message.internal.MsgTraceEvent;

import java.util.List;

public interface ComputeService {
    String getListInstance();
    String getInstanceStatus(String instanceId);
    String getInstanceName(String instanceId);
    List<Instance> getInstanceList();
    Instance getInstance(String instanceId);
}
