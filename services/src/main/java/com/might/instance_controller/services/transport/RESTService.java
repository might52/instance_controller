package com.might.instance_controller.services.transport;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Service;

import javax.websocket.server.ServerEndpoint;
import java.util.Map;

public interface RESTService  {
    <T> Object get(String endpointUrl, Map objects);
    <T> Object post(String endpointUrl, Map objects);
    <T> T update(String endpointUrl, Map objects);
    <T> T delete(String endpointUrl, Map objects);
}
