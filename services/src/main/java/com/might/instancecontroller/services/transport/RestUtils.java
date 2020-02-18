package com.might.instancecontroller.services.transport;

import com.might.instancecontroller.utils.AuthSessionBean;
import com.might.instancecontroller.utils.OSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
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

    public static HttpServletResponse addAccessControlAllowOriginHeader(HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        return response;
    }

    @Autowired
    private RestUtils(final AuthSessionBean authSessionBean) {
        AUTH_SESSION_BEAN = authSessionBean;
    }
}
