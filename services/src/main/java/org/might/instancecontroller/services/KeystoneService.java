package org.might.instancecontroller.services;

import org.might.instancecontroller.services.transport.impl.RestResponse;

public interface KeystoneService {
    /**
     * Return state of connection.
     * @return
     */
    Boolean isConnected();
    /**
     * Perform the authenticate on the Openstack.
     * @return
     */
    RestResponse authenticate();
}
