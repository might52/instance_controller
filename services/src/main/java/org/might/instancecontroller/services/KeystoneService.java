package org.might.instancecontroller.services;

public interface KeystoneService {
    Boolean isConnected();

    Object authenticate();
}
