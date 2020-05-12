package org.might.instancecontroller;

import org.might.instancecontroller.annotations.impl.RequireConnectionHandlerInterceptorAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    private RequireConnectionHandlerInterceptorAdapter requireConnectionHandlerInterceptorAdapter;

    public WebConfig(RequireConnectionHandlerInterceptorAdapter requireConnectionHandlerInterceptorAdapter) {
        this.requireConnectionHandlerInterceptorAdapter = requireConnectionHandlerInterceptorAdapter;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requireConnectionHandlerInterceptorAdapter);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/api/**")
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("GET", "POST","PUT", "DELETE");
    }
}
