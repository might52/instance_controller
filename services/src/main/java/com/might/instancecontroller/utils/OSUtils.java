package com.might.instancecontroller.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Configuration
@PropertySource(value = "classpath:service.properties", ignoreResourceNotFound = true)
public class OSUtils implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(OSUtils.class);

    public static final String TOKEN = "X-Subject-Token";
    public static final String TONKEN_NAME = "X-Auth-Token";
    public static final String TIMEOUT = "Keep-Alive";

    private static final String SERVERS = "servers";
    private static final String DETAILS = "detail";
    private static final String ACTION = "action";

    private static final String SLASH = "/";

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

    public String getOsIdentityApiVersion() {
        return osIdentityApiVersion;
    }

    public String getOsNeutronUrl() {
        return osNeutronUrl;
    }

    public void setOsNeutronUrl(String osNeutronUrl) {
        this.osNeutronUrl = osNeutronUrl;
    }

    public String getOsComputeUrl() {
        return new ArrayList<String>() {{
            add(osComputeUrl);
            add(SERVERS);
            add(DETAILS);
        }}.stream().collect(Collectors.joining(SLASH));
    }

    public String getServerUrl(String serverId) {
        return new ArrayList<String>() {{
            add(osComputeUrl);
            add(SERVERS);
            add(serverId);
        }}.stream().collect(Collectors.joining(SLASH));
    }

    public String getServerUrlAction(String serverId) {
        return String.join(
                SLASH,
                this.getServerUrl(serverId),
                ACTION
        );
    }
}
