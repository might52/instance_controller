package com.might.instance_controller.services.Impl;

import com.might.instance_controller.services.AuthSessionBean;
import com.might.instance_controller.services.ComputeService;
import com.might.instance_controller.services.OSProperties;
import com.might.instance_controller.services.transport.impl.RestResponse;
import com.might.instance_controller.services.transport.RESTService;
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

    public Object getListInstance() {
        MultivaluedMap<String, String> headers = new MultivaluedHashMap<>();
        headers.putSingle(OSProperties.TONKEN_NAME, authSessionBean.getToken());
        RestResponse restResponse = (RestResponse) restService.get(osProperties.OS_COMPUTE_URL, headers);
        return restResponse;
    }
}
