/*
 * MIT License
 *
 * Copyright (c) 2024 Andrei F._
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.might.instancecontroller.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import org.might.instancecontroller.models.auth.Auth;
import org.might.instancecontroller.models.auth.Domain;
import org.might.instancecontroller.models.auth.Identity;
import org.might.instancecontroller.models.auth.Password;
import org.might.instancecontroller.models.auth.Project;
import org.might.instancecontroller.models.auth.Scope;
import org.might.instancecontroller.models.auth.User;
import org.might.instancecontroller.services.KeystoneService;
import org.might.instancecontroller.services.transport.RESTService;
import org.might.instancecontroller.services.transport.impl.RestResponse;
import org.might.instancecontroller.utils.AuthSessionBean;
import org.might.instancecontroller.utils.SettingsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;

import static org.might.instancecontroller.utils.SettingsHelper.OS_TOKEN;
import static org.might.instancecontroller.utils.SettingsHelper.TIMEOUT;

@Service
public class KeystoneServiceImpl implements KeystoneService {


    private static final String X_TOKE_KEY = "X-Subject-Token";
    private final RESTService restService;
    private final SettingsHelper settingsHelper;
    private final AuthSessionBean authSessionBean;

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
                new TypeReference<>() {
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
