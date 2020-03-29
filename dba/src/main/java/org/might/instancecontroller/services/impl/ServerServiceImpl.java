package org.might.instancecontroller.services.impl;

import org.might.instancecontroller.dba.entity.Server;
import org.might.instancecontroller.dba.repository.ServerRepository;
import org.might.instancecontroller.services.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServerServiceImpl implements ServerService {

    private ServerRepository serverRepository;

    @Autowired
    public ServerServiceImpl(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    @Override
    public Optional<Server> getServerById(Long id) {
        return serverRepository.findById(id);
    }

    @Override
    public void saveServer(Server server) {
        serverRepository.save(server);
    }

    @Override
    public void deleteServer(Server server) {
        serverRepository.delete(server);
    }

    @Override
    public List<Server> getAll() {
        return serverRepository.findAll();
    }
}
