package com.might.instancecontroller.services.transport;

import com.might.instancecontroller.utils.AuthSessionBean;
import com.might.instancecontroller.utils.OSUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

public class RestUtils {

    @Autowired
    private static AuthSessionBean AUTH_SESSION_BEAN;

    public static MultivaluedMap getAuthHeaders() {
        MultivaluedMap<String, String> headers = new MultivaluedHashMap<>();
        headers.putSingle(OSUtils.TONKEN_NAME, AUTH_SESSION_BEAN.getToken());
        return headers;
    }

    private RestUtils() {}
}
