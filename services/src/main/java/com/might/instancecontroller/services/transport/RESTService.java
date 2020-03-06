package com.might.instancecontroller.services.transport;

import com.fasterxml.jackson.core.type.TypeReference;
import com.might.instancecontroller.services.transport.impl.RestResponse;

import javax.ws.rs.core.MultivaluedMap;

public interface RESTService {

    <T> T get(String endpointUrl,
              MultivaluedMap<String, String> headers,
              TypeReference<T> typeReference);

    <T> RestResponse getRaw(String endpointUrl,
                            MultivaluedMap<String, String> headers,
                            TypeReference<T> typeReference);

    <T> T post(String endpointUrl,
               Object data,
               MultivaluedMap<String, String> headers,
               TypeReference<T> typeReference);

    <T> RestResponse postRaw(String endpointUrl,
               Object data,
               MultivaluedMap<String, String> headers,
               TypeReference<T> typeReference);

    <T> T update(String endpointUrl, Object object);

    <T> T delete(String endpointUrl, Object object);
}
