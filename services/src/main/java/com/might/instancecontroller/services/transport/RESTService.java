package com.might.instancecontroller.services.transport;

import com.fasterxml.jackson.core.type.TypeReference;
import com.might.instancecontroller.services.transport.impl.RestResponse;

import javax.ws.rs.core.MultivaluedMap;

public interface RESTService {

    /**
     * Perform GET request to the endpoint.
     * @param endpointUrl
     * @param headers
     * @param typeReference
     * @param <T>
     * @return
     */
    <T> T get(String endpointUrl,
              MultivaluedMap<String, String> headers,
              TypeReference<T> typeReference);

    /**
     * Perform GET request to the endpoint.
     * @param endpointUrl
     * @param headers
     * @param typeReference
     * @param <T>
     * @return Return RestRequest object.
     */
    <T> RestResponse getRaw(String endpointUrl,
                            MultivaluedMap<String, String> headers,
                            TypeReference<T> typeReference);

    /**
     * Perform POST request to the endpoint.
     * @param endpointUrl
     * @param data
     * @param headers
     * @param typeReference
     * @param <T>
     * @return Return required object.
     */
    <T> T post(String endpointUrl,
               Object data,
               MultivaluedMap<String, String> headers,
               TypeReference<T> typeReference);

    /**
     * Perform POST request to the endpoint.
     * @param endpointUrl
     * @param data
     * @param headers
     * @param typeReference
     * @param <T>
     * @return Return RestRequiest object.
     */
    <T> RestResponse postRaw(String endpointUrl,
               Object data,
               MultivaluedMap<String, String> headers,
               TypeReference<T> typeReference);

    /**
     * Perform UPDATE request to the endpoint.
     * @param endpointUrl
     * @param object
     * @param <T>
     * @return
     */
    <T> T update(String endpointUrl, Object object);

    /**
     * Perform DELETE request to the endpoint.
     * @param endpointUrl
     * @param object
     * @param <T>
     * @return
     */
    <T> T delete(String endpointUrl, Object object);
}
