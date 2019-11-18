package com.might.instancecontroller.services.transport;

import javax.ws.rs.core.MultivaluedMap;

public interface RESTService {
    <T> Object get(String endpointUrl, MultivaluedMap<String, String> headers);

    <T> Object post(String endpointUrl, Object object);

    <T> T update(String endpointUrl, Object object);

    <T> T delete(String endpointUrl, Object object);
}
