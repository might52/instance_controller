package com.might.instance_controller.annotations.impl;


import com.might.instance_controller.annotations.RequireConnection;
import com.might.instance_controller.services.AuthSessionBean;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class RequireConnectionHandlerInterceptorAdapter extends HandlerInterceptorAdapter {

    private AuthSessionBean authSessionBean;

    @Autowired
    public RequireConnectionHandlerInterceptorAdapter(AuthSessionBean authSessionBean) {
        this.authSessionBean = authSessionBean;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            Method method = ((HandlerMethod) handler).getMethod();
            if (method.isAnnotationPresent(RequireConnection.class)) {
                if (!authSessionBean.getConnected()) {
                    throw new RuntimeException("Please authenticate before using the service via the the getToken call");
                }
            }
        }

        return true;
        // return super.preHandle(request, response, handler)
    }

}
