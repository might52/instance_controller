package com.might.instance_controller.services.Impl;

import com.might.instance_controller.models.auth.request.*;
import com.might.instance_controller.services.AuthSessionBean;
import com.might.instance_controller.services.KeystoneServise;
import com.might.instance_controller.services.OSProperties;
import com.might.instance_controller.services.transport.Impl.RestResponse;
import com.might.instance_controller.services.transport.RESTService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static com.might.instance_controller.services.OSProperties.TIMEOUT;
import static com.might.instance_controller.services.OSProperties.TOKEN;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class KeystoneServiceImpl implements KeystoneServise {

    private static final Logger LOGGER = LoggerFactory.getLogger(KeystoneServiceImpl.class);

    private RESTService restService;
    private OSProperties osProperties;
    private AuthSessionBean authSessionBean;

    @Autowired
    public KeystoneServiceImpl(RESTService restService,
                               OSProperties osProperties,
                               AuthSessionBean authSessionBean) {
        this.restService = restService;
        this.osProperties = osProperties;
        this.authSessionBean = authSessionBean;
    }

    /**
     *
     * @return state of connection to the Keystone.
     */
    public Boolean isConnected() {
        return authSessionBean.getConnected();
    }

    /**
     * Authenticate customer to the OS.
     * @return RestResponse object;
     */
    public Object authenticate() {
        Map<String, Object>  authMap = new HashMap<>();
        authMap.put("auth", getAuthModel());
        RestResponse response = (RestResponse) restService.post(osProperties.OS_AUTH_URL, authMap);
        setAuthDate(response);
        return response;
    }

    /**
     * Set authData during the current session.
     * @param restResponse - response after call.
     */
    private void setAuthDate(RestResponse restResponse){
        if (StringUtils.isEmpty(restResponse.getHeaders().getFirst(TOKEN))){
            authSessionBean.setConnected(true);
            authSessionBean.setToken(restResponse.getHeaders().getFirst(TOKEN));
            authSessionBean.setTimeout(restResponse.getHeaders().getFirst(TIMEOUT));
        } else {
            authSessionBean.setConnected(false);
            authSessionBean.setToken("");
            authSessionBean.setTimeout("");
        }
    }

    /**
     * Generate the Auth model body.
     * @return the Auth model.
     */
    private Auth getAuthModel(){
        Auth auth = new Auth();
        Domain domain = new Domain();
        domain.setName(osProperties.OS_USER_DOMAIN_NAME);
        User user = new User();
        user.setName(osProperties.OS_USERNAME);
        user.setPassword(osProperties.OS_PASSWORD);
        user.setDomain(domain);
        Password password = new Password();
        password.setUser(user);
        Identity identity = new Identity();
        identity.setMethods(new ArrayList<String>(){{
            add("password");
        }});
        identity.setPassword(password);
        Domain projDomain = new Domain();
        projDomain.setName(osProperties.OS_PROJECT_DOMAIN_NAME);
        Project project = new Project();
        project.setDomain(projDomain);
        project.setName(osProperties.OS_PROJECT_NAME);
        Scope scope = new Scope();
        scope.setProject(project);
        auth.setIdentity(identity);
        auth.setScope(scope);

        return auth;
    }

}
