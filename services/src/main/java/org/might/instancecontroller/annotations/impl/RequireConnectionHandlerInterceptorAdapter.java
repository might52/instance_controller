package org.might.instancecontroller.annotations.impl;


import org.might.instancecontroller.annotations.RequireConnection;
import org.might.instancecontroller.services.KeystoneService;
import org.might.instancecontroller.utils.AuthSessionBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class RequireConnectionHandlerInterceptorAdapter extends HandlerInterceptorAdapter {

    private AuthSessionBean authSessionBean;
    private KeystoneService keystoneService;

    @Autowired
    public RequireConnectionHandlerInterceptorAdapter(
            AuthSessionBean authSessionBean,
            KeystoneService keystoneService) {
        this.authSessionBean = authSessionBean;
        this.keystoneService = keystoneService;
    }

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) {
        if (handler instanceof HandlerMethod) {
            Method method = ((HandlerMethod) handler).getMethod();
            if (method.isAnnotationPresent(RequireConnection.class)) {
                if (!authSessionBean.getConnected()) {
                    keystoneService.authenticate();
                }
            }
        }

        return true;
    }

}
