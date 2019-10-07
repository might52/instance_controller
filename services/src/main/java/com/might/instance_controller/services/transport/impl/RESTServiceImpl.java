package com.might.instance_controller.services.transport.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.might.instance_controller.services.transport.RESTService;
import com.sun.jersey.api.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServiceUnavailableException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RESTServiceImpl implements RESTService, Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(RESTServiceImpl.class);

    private Client restClient;
    private ObjectMapper jsonSerializer;
    private static final String CURLY_BRACES = "{}";
    private static final String ENCODING = "UTF-8";

    @Autowired
    public RESTServiceImpl() {
        initSerializer();
        initClient();
    }

    /**
     * Perform get request to the destination endpoint.
     * @param endpointUrl - target url endpoint.
     * @param headers - headers for request.
     * @param <T> - hz wtf eto.
     * @return -
     */
    @Override
    public <T> Object get(String endpointUrl, MultivaluedMap<String, String> headers) {
        ClientResponse clientResponse;
        RestResponse restResponse;
        try {
            WebResource webResource = this
                    .restClient
                    .resource(endpointUrl);
            WebResource.Builder builder = webResource.getRequestBuilder();
            addHeaders(builder, headers);
            LOGGER.info(String.format("Request url: %s", webResource.getURI()));
            clientResponse = builder
                    .accept(MediaType.APPLICATION_JSON_TYPE)
                    .get(ClientResponse.class);
            restResponse = new RestResponse(clientResponse);
            checkResponseStatus(restResponse);
        } catch (Exception ex) {
            LOGGER.error(String.format("Error: %s", ex));
            throw ex;
        }

        return restResponse;
    }

    /**
     * Perform post request to the destination endpoint.
     * @param endpointUrl - target url source.
     * @param objects - object for.
     * @param <T> - hz wtf eto.
     * @return entity object of response.
     */
    public <T> Object post(String endpointUrl, Map objects) {
        ClientResponse clientResponse;
        try {
            String body = getEntityString(objects);
            WebResource webResource = restClient.resource(endpointUrl);
            LOGGER.info(String.format("Request url: %s", webResource.getURI()));
            LOGGER.info(String.format("Request body: %s", body));
            clientResponse = webResource
                    .accept(MediaType.APPLICATION_JSON)
                    .entity(body, MediaType.APPLICATION_JSON)
                    .post(ClientResponse.class);
        } catch (Exception ex) {
            LOGGER.error(String.format("Error: %s", ex));
            throw ex;
        }

        return new RestResponse(clientResponse);
    }

    public <T> T update(String endpointUrl, Map objects) {
        return null;
    }

    public <T> T delete(String endpointUrl, Map objects) {
        return null;
    }

    /**
     * Convert object to string JSON.
     * @param params
     * @return string representation of object.
     */
    private String getEntityString(Map params){
        String result = "";
        try {
            result = jsonSerializer.writeValueAsString(params);
        } catch (JsonProcessingException ex) {

        }
        if (!result.isEmpty()) {
            return result;
        }

        return null;
    }

    /***
     * Initialisation of the serializer.
     */
    private void initSerializer() {
        this.jsonSerializer = new ObjectMapper();
    }

    /**
     * Initialisation of rest client.
     */
    private void initClient(){
        this.restClient = Client.create();
    }

    /***
     * Validate the current response rest client.
     * @param restResponse - client response.
     */
    private void checkResponseStatus(RestResponse restResponse) {
        LOGGER.info(CURLY_BRACES, restResponse.toString());
        final HttpStatus responseStatus = HttpStatus.valueOf(restResponse.getStatus());
        switch (responseStatus) {
            case OK:
            case CREATED:
            case NO_CONTENT:
                //All ok
                return;
            case INTERNAL_SERVER_ERROR: {
                LOGGER.error(restResponse.getStringEntity(), ENCODING);
                throw new ServiceUnavailableException(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            }
            case SERVICE_UNAVAILABLE:
                throw new ServiceUnavailableException("Service unavailable");
            case UNAUTHORIZED: {

                LOGGER.error(String.format("%s", restResponse.getStringEntity(), ENCODING));
                throw new RuntimeException("Unauthorized Exception");
            }
            case NOT_FOUND:
                throw new NotFoundException("Not found");
            case UNPROCESSABLE_ENTITY: {
                LOGGER.error(restResponse.getStringEntity(), ENCODING);
                throw new ServiceUnavailableException(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase());
            }
            default:
                throw new RuntimeException(String.format("Unexpected error occurred during REST service call. %d %s",
                        responseStatus.value(), restResponse.toString()), null);
        }
    }

    /**
     * Add headers to the response.
      * @param builder - builder of the request.
     * @param headers - map of headers.
     * @return - builder with headers.
     */
    private WebResource.Builder addHeaders(WebResource.Builder builder, MultivaluedMap<String, String> headers) {
        LOGGER.debug("Headers: {}", headers);
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            for (String value : entry.getValue()) {
                builder.header(entry.getKey(), value);
            }
        }

        return builder;
    }
}

