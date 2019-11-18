package com.might.instancecontroller;

import com.might.instancecontroller.annotations.impl.RequireConnectionHandlerInterceptorAdapter;
import com.might.instancecontroller.utils.AuthSessionBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    private AuthSessionBean authSessionBean;

    @Autowired
    public WebConfig(AuthSessionBean authSessionBean) {
        this.authSessionBean = authSessionBean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequireConnectionHandlerInterceptorAdapter(authSessionBean));
    }
}
