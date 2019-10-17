package com.might.instance_controller.services.transport;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Service;

import javax.websocket.server.ServerEndpoint;
import javax.ws.rs.core.MultivaluedMap;
import java.util.Map;

public interface RESTService  {
    <T> Object get(String endpointUrl, MultivaluedMap<String, String> headers);
    <T> Object post(String endpointUrl, Object object);
    <T> T update(String endpointUrl, Object object);
    <T> T delete(String endpointUrl, Object object);
}
