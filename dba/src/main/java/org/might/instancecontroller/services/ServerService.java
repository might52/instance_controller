package org.might.instancecontroller.services;

import org.might.instancecontroller.dba.entity.Server;

import java.util.List;
import java.util.Optional;

public interface ServerService {
    Optional<Server> getServerById(Long id);
    void saveServer(Server server);
    void deleteServer(Server server);
    List<Server> getAll();
}
