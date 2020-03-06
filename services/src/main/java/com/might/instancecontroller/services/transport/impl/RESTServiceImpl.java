package com.might.instancecontroller.services.transport.impl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.might.instancecontroller.services.transport.RESTService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServiceUnavailableException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Service
public class RESTServiceImpl implements RESTService, Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(RESTServiceImpl.class);
    private static final String CURLY_BRACES = "{}";
    private static final String ENCODING = "UTF-8";
    private static final String ERROR_MESSAGE_TEMPLATE = "Error: %s";
    private static final String URL_MESSAGE_TEMPLATE = "Request url: %s";
    private static final String REQUEST_BODY_MESSAGE_TEMPLATE = "Request body: %s";


    private Client restClient;
    private ObjectMapper jsonSerializer;

    @Autowired
    public RESTServiceImpl() {
        initSerializer();
        initClient();
    }

    /**
     * Perform get request to the destination endpoint.
     *
     * @param endpointUrl - target url endpoint.
     * @param headers     - headers for request.
     * @param <T>         - hz wtf eto.
     * @return -
     */
    @Override
    public <T> T get(String endpointUrl, MultivaluedMap<String, String> headers, TypeReference<T> type) {
        RestResponse restResponse = getRequest(endpointUrl, headers);
        return parseResponse(restResponse, type);
    }

    public <T> RestResponse getRaw(String endpointUrl,
                                    MultivaluedMap<String, String> headers,
                                    TypeReference<T> type
    ) {
        return this.getRequest(
                endpointUrl,
                headers);
    }

    /**
     * Perform get request to the destination endpoint.
     *
     * @param endpointUrl - target url endpoint.
     * @param headers     - headers for request.
     * @return - rest response object.
     */
    private RestResponse getRequest(String endpointUrl, MultivaluedMap<String, String> headers) {
        ClientResponse clientResponse;
        RestResponse restResponse;
        try {
            WebResource webResource = this
                    .restClient
                    .resource(endpointUrl);
            WebResource.Builder builder = webResource.getRequestBuilder();
            addHeaders(builder, headers);
            LOGGER.debug(String.format(URL_MESSAGE_TEMPLATE, webResource.getURI()));
            clientResponse = builder
                    .accept(MediaType.APPLICATION_JSON_TYPE)
                    .get(ClientResponse.class);
            restResponse = new RestResponse(clientResponse);
            checkResponseStatus(restResponse);
        } catch (Exception ex) {
            LOGGER.error(String.format(ERROR_MESSAGE_TEMPLATE, ex));
            throw ex;
        }

        return restResponse;
    }

    private <T> T parseResponse(RestResponse restResponse, TypeReference<T> type) {
        try {
            return jsonSerializer.readValue(restResponse.getStringEntity(), type);
        } catch (JsonParseException ex) {
            LOGGER.error(String.format("JSON parse exception occurred: %s", ex.getMessage()));
        } catch (JsonMappingException ex) {
            LOGGER.error(String.format("JSON mapping exception occurred: %s", ex.getMessage()));
        } catch (IOException ex) {
            LOGGER.error(String.format("IOException exception occurred: %s", ex.getMessage()));
        }

        return null;
    }

    public <T> T post(String endpointUrl,
                      Object data,
                      MultivaluedMap<String, String> headers,
                      TypeReference<T> type
    ) {
        RestResponse restResponse = this.postRequest(
                endpointUrl,
                data,
                headers,
                type);
        return this.parseResponse(restResponse, type);
    }

    public <T> RestResponse postRaw(String endpointUrl,
                      Object data,
                      MultivaluedMap<String, String> headers,
                      TypeReference<T> type
    ) {
        return this.postRequest(
                endpointUrl,
                data,
                headers,
                type);
    }

    private RestResponse postRequest(String endpointUrl,
                                 Object data,
                                 MultivaluedMap<String, String> headers,
                                 TypeReference type) {
        ClientResponse clientResponse;
        RestResponse restResponse;
        try {
            String body = getEntityString(data);
            WebResource webResource = restClient.resource(endpointUrl);
            WebResource.Builder builder = webResource.getRequestBuilder();
            addHeaders(builder, headers);
            LOGGER.debug(String.format(URL_MESSAGE_TEMPLATE, webResource.getURI()));
            LOGGER.debug(String.format(REQUEST_BODY_MESSAGE_TEMPLATE, body));
            clientResponse = builder
                    .accept(MediaType.APPLICATION_JSON)
                    .entity(body, MediaType.APPLICATION_JSON)
                    .post(ClientResponse.class);
            restResponse = new RestResponse(clientResponse);
            checkResponseStatus(restResponse);
        } catch (Exception ex) {
            LOGGER.error(String.format(ERROR_MESSAGE_TEMPLATE, ex));
            throw ex;
        }

        return restResponse;
    }

    public <T> T update(String endpointUrl, Object object) {
        return null;
    }

    public <T> T delete(String endpointUrl, Object object) {
        return null;
    }

    /**
     * Convert object to string JSON.
     *
     * @param object
     * @return string representation of object.
     */
    private String getEntityString(Object object) {
        String result = "";
        try {
            result = jsonSerializer.writeValueAsString(object);
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
        this.jsonSerializer.enable(SerializationFeature.WRAP_ROOT_VALUE);
        this.jsonSerializer.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
    }

    /**
     * Initialisation of rest client.
     */
    private void initClient() {
        this.restClient = Client.create();
    }

    /***
     * Validate the current response rest client.
     * @param restResponse - client response.
     */
    private void checkResponseStatus(RestResponse restResponse) {
        LOGGER.debug(CURLY_BRACES, restResponse.toString());
        LOGGER.debug(CURLY_BRACES, restResponse.getStringEntity());
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
                LOGGER.error(String.format("%s", restResponse.getStringEntity()), ENCODING);
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
     *
     * @param builder - builder of the request.
     * @param headers - map of headers.
     * @return - builder with headers.
     */
    private WebResource.Builder addHeaders(WebResource.Builder builder, MultivaluedMap<String, String> headers) {
        LOGGER.debug("Headers: {}", headers);
        if (headers == null) {
            return builder;
        }

        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            for (String value : entry.getValue()) {
                builder.header(entry.getKey(), value);
            }
        }

        return builder;
    }
}

