package com.might.instancecontroller.services.impl;

import com.might.instancecontroller.models.auth.*;
import com.might.instancecontroller.services.KeystoneService;
import com.might.instancecontroller.services.transport.RESTService;
import com.might.instancecontroller.services.transport.impl.RestResponse;
import com.might.instancecontroller.utils.AuthSessionBean;
import com.might.instancecontroller.utils.OSProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;

import static com.might.instancecontroller.utils.OSProperties.TIMEOUT;
import static com.might.instancecontroller.utils.OSProperties.TOKEN;

@Service
public class KeystoneServiceImpl implements KeystoneService {

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
     * Provide the state of the connection.
     *
     * @return state of connection to the Keystone.
     */
    public Boolean isConnected() {
        return authSessionBean.getConnected();
    }

    /**
     * Authenticate customer to the OS.
     *
     * @return RestResponse object;
     */
    public Object authenticate() {
        RestResponse response = (RestResponse) restService.post(osProperties.getOsAuthUrl(), getAuthModel());
        setAuthDate(response);
        return response;
    }

    /**
     * Set authData during the current session.
     *
     * @param restResponse - response after call.
     */
    private void setAuthDate(RestResponse restResponse) {
        if (!StringUtils.isEmpty(restResponse.getHeaders().getFirst("X-Subject-Token"))) {
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
     *
     * @return the Auth model.
     */
    private Auth getAuthModel() {
        Auth auth = new Auth();
        Domain domain = new Domain();
        domain.setName(osProperties.getOsUserDomainName());
        User user = new User();
        user.setName(osProperties.getOsUsername());
        user.setPassword(osProperties.getOsPassword());
        user.setDomain(domain);
        Password password = new Password();
        password.setUser(user);
        Identity identity = new Identity();
        identity.setMethods(new ArrayList<String>() {{
            add("password");
        }});
        identity.setPassword(password);
        Domain projDomain = new Domain();
        projDomain.setName(osProperties.getOsProjectDomainName());
        Project project = new Project();
        project.setDomain(projDomain);
        project.setName(osProperties.getOsProjectName());
        Scope scope = new Scope();
        scope.setProject(project);
        auth.setIdentity(identity);
        auth.setScope(scope);

        return auth;
    }

}