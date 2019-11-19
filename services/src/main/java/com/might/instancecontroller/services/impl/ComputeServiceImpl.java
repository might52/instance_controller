package com.might.instancecontroller.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.might.instancecontroller.annotations.RequireConnection;
import com.might.instancecontroller.models.servers.Server;
import com.might.instancecontroller.services.ComputeService;
import com.might.instancecontroller.services.transport.RESTService;
import com.might.instancecontroller.services.transport.impl.RestResponse;
import com.might.instancecontroller.utils.AuthSessionBean;
import com.might.instancecontroller.utils.OSProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

@Service
public class ComputeServiceImpl implements ComputeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ComputeServiceImpl.class);

    private OSProperties osProperties;
    private RESTService restService;
    private AuthSessionBean authSessionBean;

    @Autowired
    public ComputeServiceImpl(RESTService restService,
                              OSProperties osProperties,
                              AuthSessionBean authSessionBean) {
        this.restService = restService;
        this.osProperties = osProperties;
        this.authSessionBean = authSessionBean;
    }

    @RequireConnection
    public Object getListInstance() {
        RestResponse restResponse = (RestResponse) restService.get(osProperties.getOsComputeUrl(), getAuthHeaders());
        LOGGER.info(restResponse.getStringEntity());
        return restResponse;
    }

    @Override
    public String getInstanceStatus(String instanceId) {
        try {
            Server server = restService.get(osProperties.getOsComputeUrl(), getAuthHeaders(), new TypeReference<Server>());
            return server.getStatus();
        } catch (Exception ex) {
            return null;
        }
    }


    private MultivaluedMap getAuthHeaders() {
        MultivaluedMap<String, String> headers = new MultivaluedHashMap<>();
        headers.putSingle(OSProperties.TONKEN_NAME, authSessionBean.getToken());
        return headers;
    }


}
