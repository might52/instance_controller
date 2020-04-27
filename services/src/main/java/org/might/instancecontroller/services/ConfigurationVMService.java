package org.might.instancecontroller.services;

public interface ConfigurationVMService {
    /**
     * Perform configuration part on the VM via SSH.
     * @param host server IP address or hostname.
     * @param scripts configuration scripts.
     */
    void setUpVM(String host, String scripts);
}
