package org.might.instancecontroller.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import org.might.instancecontroller.models.auth.*;
import org.might.instancecontroller.services.transport.RESTService;
import org.might.instancecontroller.services.transport.impl.RestResponse;
import org.might.instancecontroller.services.KeystoneService;
import org.might.instancecontroller.utils.AuthSessionBean;
import org.might.instancecontroller.utils.SettingsHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;

import static org.might.instancecontroller.utils.SettingsHelper.TIMEOUT;
import static org.might.instancecontroller.utils.SettingsHelper.OS_TOKEN;

@Service
public class KeystoneServiceImpl implements KeystoneService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KeystoneServiceImpl.class);

    private static final String X_TOKE_KEY = "X-Subject-Token";

    private RESTService restService;
    private SettingsHelper settingsHelper;
    private AuthSessionBean authSessionBean;

    @Autowired
    public KeystoneServiceImpl(RESTService restService,
                               SettingsHelper settingsHelper,
                               AuthSessionBean authSessionBean) {
        this.restService = restService;
        this.settingsHelper = settingsHelper;
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
    public RestResponse authenticate() {
        RestResponse response = restService.postRaw(
                settingsHelper.getOsAuthUrl(),
                getAuthModel(),
                null,
                new TypeReference<>(){
                }
        );
        setAuthDate(response);
        return response;
    }

    /**
     * Set authData during the current session.
     *
     * @param restResponse - response after call.
     */
    private void setAuthDate(RestResponse restResponse) {
        if (!StringUtils.isEmpty(restResponse.getHeaders().getFirst(X_TOKE_KEY))) {
            authSessionBean.setConnected(true);
            authSessionBean.setToken(restResponse.getHeaders().getFirst(OS_TOKEN));
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
        domain.setName(settingsHelper.getOsUserDomainName());
        User user = new User();
        user.setName(settingsHelper.getOsUsername());
        user.setPassword(settingsHelper.getOsPassword());
        user.setDomain(domain);
        Password password = new Password();
        password.setUser(user);
        Identity identity = new Identity();
        identity.setMethods(new ArrayList<>() {{
            add("password");
        }});
        identity.setPassword(password);
        Domain projDomain = new Domain();
        projDomain.setName(settingsHelper.getOsProjectDomainName());
        Project project = new Project();
        project.setDomain(projDomain);
        project.setName(settingsHelper.getOsProjectName());
        Scope scope = new Scope();
        scope.setProject(project);
        auth.setIdentity(identity);
        auth.setScope(scope);

        return auth;
    }

}
