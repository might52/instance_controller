package org.might.instancecontroller;

/*
import org.might.instancecontroller.utils.AuthSessionBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;
*/

import org.springframework.context.annotation.PropertySource;

@org.springframework.context.annotation.Configuration
@PropertySource(value = {
        "classpath:service.properties",
        "classpath:application.properties"
}, ignoreResourceNotFound = true)
public class Configuration {

/*    @Bean("AuthSessionBean")
    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public AuthSessionBean AuthSessionBean() {
        return new AuthSessionBean();
    }*/

}
