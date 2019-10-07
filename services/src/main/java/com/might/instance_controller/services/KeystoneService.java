package com.might.instance_controller.services;

import java.util.Map;

public interface KeystoneService {
    Boolean isConnected();
    Object authenticate();
}
