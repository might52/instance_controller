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
package org.might.instancecontroller.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class SettingsHelper implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(SettingsHelper.class);

    public static final String OS_TOKEN = "X-Subject-Token";
    public static final String OS_TOKEN_NAME = "X-Auth-Token";
    public static final String TIMEOUT = "Keep-Alive";

    private static final String SERVERS = "servers";
    private static final String DETAILS = "detail";
    private static final String ACTION = "action";

    private static final String SLASH = "/";

    private static final long serialVersionUID = 2230912249116629395L;

    @Value("${os.username}")
    private String osUsername;
    @Value("${os.password}")
    private String osPassword;
    @Value("${os.auth.url}")
    private String osAuthUrl;
    @Value("${os.project.name}")
    private String osProjectName;
    @Value("${os.user.domain.name}")
    private String osUserDomainName;
    @Value("${os.project.domain.name}")
    private String osProjectDomainName;
    @Value("${os.identity.api.version}")
    private String osIdentityApiVersion;
    @Value("${os.compute.url}")
    private String osComputeUrl;
    @Value("${os.neutron.url}")
    private String osNeutronUrl;
    @Value("${vm.user}")
    private String vmUserName;
    @Value("${vm.pass}")
    private String vmPassword;
    @Value("${zabbix.auth.token}")
    private String zabbixAuthToken;
    @Value("${zabbix.url}")
    private String zabbixUrl;

    public String getOsUsername() {
        return osUsername;
    }

    public String getOsPassword() {
        return osPassword;
    }

    public String getOsAuthUrl() {
        return osAuthUrl;
    }

    public String getOsProjectName() {
        return osProjectName;
    }

    public String getOsUserDomainName() {
        return osUserDomainName;
    }

    public String getOsProjectDomainName() {
        return osProjectDomainName;
    }

    public String getVmUserName() {
        return vmUserName;
    }

    public String getVmPassword() {
        return vmPassword;
    }

    public String getZabbixAuthToken() {
        return zabbixAuthToken;
    }

    public String getZabbixUrl() {
        return zabbixUrl;
    }

    /**
     * Get server details string url.
     * @return
     */
    public String getServerDetailsUrl() {
        return new ArrayList<String>() {{
            add(osComputeUrl);
            add(SERVERS);
            add(DETAILS);
        }}.stream().collect(Collectors.joining(SLASH));
    }

    /**
     * Get server url by id.
     * @param serverId
     * @return
     */
    public String getServerUrl(String serverId) {
        return new ArrayList<String>() {{
            add(osComputeUrl);
            add(SERVERS);
            add(serverId);
        }}.stream().collect(Collectors.joining(SLASH));
    }

    /**
     * Get server action url by id.
     * @param serverId
     * @return
     */
    public String getServerUrlAction(String serverId) {
        return String.join(
                SLASH,
                this.getServerUrl(serverId),
                ACTION
        );
    }

    /**
     * Get servers url.
     * @return
     */
    public String getServersUrl() {
        return new ArrayList<String>() {{
            add(osComputeUrl);
            add(SERVERS);
        }}.stream().collect(Collectors.joining(SLASH));
    }

    private SettingsHelper() {
    }
}
