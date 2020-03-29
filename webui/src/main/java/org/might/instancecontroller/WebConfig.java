package org.might.instancecontroller;

import org.might.instancecontroller.annotations.impl.RequireConnectionHandlerInterceptorAdapter;
import org.might.instancecontroller.services.KeystoneService;
import org.might.instancecontroller.utils.AuthSessionBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    private AuthSessionBean authSessionBean;
    private KeystoneService keystoneService;

    @Autowired
    public WebConfig(
            AuthSessionBean authSessionBean,
            KeystoneService keystoneService) {
        this.authSessionBean = authSessionBean;
        this.keystoneService = keystoneService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(
                new RequireConnectionHandlerInterceptorAdapter(
                        authSessionBean,
                        keystoneService
                )
        );
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/api/**")
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("GET", "POST","PUT", "DELETE");
    }
}
