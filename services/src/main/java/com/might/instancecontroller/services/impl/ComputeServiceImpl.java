package com.might.instancecontroller.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.might.instancecontroller.annotations.RequireConnection;
import com.might.instancecontroller.models.servers.Instance;
import com.might.instancecontroller.services.ComputeService;
import com.might.instancecontroller.services.transport.RESTService;
import com.might.instancecontroller.services.transport.impl.RestResponse;
import com.might.instancecontroller.utils.AuthSessionBean;
import com.might.instancecontroller.utils.OSUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

@Service
public class ComputeServiceImpl implements ComputeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ComputeServiceImpl.class);

    private OSUtils osUtils;
    private RESTService restService;
    private AuthSessionBean authSessionBean;

    @Autowired
    public ComputeServiceImpl(RESTService restService,
                              OSUtils osUtils,
                              AuthSessionBean authSessionBean) {
        this.restService = restService;
        this.osUtils = osUtils;
        this.authSessionBean = authSessionBean;
    }

    @RequireConnection
    public Object getListInstance() {
        RestResponse restResponse = (RestResponse) restService.get(osUtils.getOsComputeUrl(), getAuthHeaders());
        LOGGER.info(restResponse.getStringEntity());
        return restResponse;
    }

    @Override
    public String getInstanceStatus(String instanceId) {
        try {
            Instance instance = restService.get(osUtils.getServerUrl(instanceId),
                    getAuthHeaders(),
                    new TypeReference<Instance>() {});
            return instance.getServer().getStatus();
        } catch (Exception ex) {
            return null;
        }
    }


    private MultivaluedMap getAuthHeaders() {
        MultivaluedMap<String, String> headers = new MultivaluedHashMap<>();
        headers.putSingle(OSUtils.TONKEN_NAME, authSessionBean.getToken());
        return headers;
    }


}
