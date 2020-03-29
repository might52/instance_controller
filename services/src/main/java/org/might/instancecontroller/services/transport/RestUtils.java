package org.might.instancecontroller.services.transport;

import org.might.instancecontroller.utils.AuthSessionBean;
import org.might.instancecontroller.utils.OSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

@Service
public class RestUtils {

    private static AuthSessionBean AUTH_SESSION_BEAN;

    public static MultivaluedMap getAuthHeaders() {
        MultivaluedMap<String, String> headers = new MultivaluedHashMap<>();
        headers.putSingle(OSUtils.TONKEN_NAME, AUTH_SESSION_BEAN.getToken());
        return headers;
    }

    @Autowired
    private RestUtils(final AuthSessionBean authSessionBean) {
        AUTH_SESSION_BEAN = authSessionBean;
    }
}
