/*
 * MIT License
 *
 * Copyright (c) 2024 Andrei F._
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.might.instancecontroller.services.transport.impl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.might.instancecontroller.services.transport.ObjectMapper;
import org.might.instancecontroller.services.transport.RESTService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    //region params

    private static final Logger LOGGER = LoggerFactory.getLogger(
            RESTServiceImpl.class
    );
    private static final String CURLY_BRACES = "{}";
    private static final String ENCODING = "UTF-8";
    private static final String ERROR_MESSAGE_TEMPLATE = "Error: %s";
    private static final String URL_MESSAGE_TEMPLATE = "Request url: %s";
    private static final String REQUEST_BODY_MESSAGE_TEMPLATE =
            "Request body: %s";
    private static final long serialVersionUID = 6125278665108890171L;

    private Client restClient;
    private ObjectMapper objectMapper;
    private ObjectReader objectReader;
    private ObjectWriter objectWriter;

    //endregion

    //region Init

    public RESTServiceImpl() {
        initSerializer();
        initClient();
    }

    /***
     * Initialisation of the serializer.
     */
    private void initSerializer() {
        // TODO: make writer and reader thread safe.
        //  Need to use ObjectReader and ObjectWriter.
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(
                SerializationFeature.WRAP_ROOT_VALUE
        );
        this.objectMapper.enable(
                DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT
        );
    }

    /**
     * Initialisation of rest client.
     */
    private void initClient() {
        this.restClient = Client.create();
    }

    //endregion

    //region Requests

    //region GET

    /**
     * Perform get request to the destination endpoint.
     *
     * @param endpointUrl target url endpoint.
     * @param headers     headers for request.
     * @param <T>         hz wtf eto.
     * @return -
     */
    @Override
    public <T> T get(String endpointUrl,
                     MultivaluedMap<String, String> headers,
                     TypeReference<T> type
    ) {
        RestResponse restResponse = getRequest(endpointUrl, headers);
        checkResponseStatus(restResponse);
        return parseResponse(restResponse, type);
    }

    /**
     * Return the RestResponse on get method.
     *
     * @param endpointUrl target url endpoint.
     * @param headers     headers for request.
     * @param type        type of required object.
     * @param <T>         should be empty.
     * @return <RestResponse>
     */
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
     * @param endpointUrl target url endpoint.
     * @param headers     headers for request.
     * @return - rest response object.
     */
    private RestResponse getRequest(String endpointUrl,
                                    MultivaluedMap<String, String> headers
    ) {
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
        } catch (Exception ex) {
            LOGGER.error(String.format(ERROR_MESSAGE_TEMPLATE, ex));
            throw ex;
        }

        return restResponse;
    }

    //endregion

    //region POST

    /**
     * Perform post request to the destination endpoint.
     *
     * @param endpointUrl target url endpoint.
     * @param data        request body.
     * @param headers     headers for request.
     * @param type        type of required object.
     * @param <T>         return object type.
     * @return required object.
     */
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
        checkResponseStatus(restResponse);
        return this.parseResponse(restResponse, type);
    }

    /**
     * Return the RestResponse on post method.
     *
     * @param endpointUrl target url endpoint.
     * @param data        request body.
     * @param headers     headers for request.
     * @param type        type of required object.
     * @param <T>         should be empty.
     * @return <RestResponse>
     */
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

    /**
     * Perform post request to the destination endpoint.
     *
     * @param endpointUrl target url endpoint.
     * @param data        request body.
     * @param headers     headers for request.
     * @param type        type of required object.
     * @return RestResponse.
     */
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
        } catch (Exception ex) {
            LOGGER.error(String.format(ERROR_MESSAGE_TEMPLATE, ex));
            throw ex;
        }

        return restResponse;
    }

    //endregion

    //region UPDATE

    public <T> T update(String endpointUrl, Object object) {
        return null;
    }

    //endregion

    //region DELETE

    /**
     * Perform DELETE request to the destination endpoint.
     *
     * @param endpointUrl target url endpoint.
     * @param data        request body.
     * @param headers     headers for request.
     * @param type        type of required object.
     * @param <T>         type of returned object.
     * @return return required object.
     */
    public <T> T delete(String endpointUrl,
                        Object data,
                        MultivaluedMap<String, String> headers,
                        TypeReference<T> type
    ) {
        RestResponse restResponse = this.deleteRequest(
                endpointUrl,
                data,
                headers,
                type);
        checkResponseStatus(restResponse);
        return this.parseResponse(restResponse, type);
    }

    /**
     * Return the RestResponse on post method.
     *
     * @param endpointUrl target url endpoint.
     * @param data        request body.
     * @param headers     headers for request.
     * @param type        type of required object.
     * @param <T>         type of returned object.
     * @return RestResponse.
     */
    public <T> RestResponse deleteRaw(String endpointUrl,
                                      Object data,
                                      MultivaluedMap<String, String> headers,
                                      TypeReference<T> type
    ) {
        return this.deleteRequest(
                endpointUrl,
                data,
                headers,
                type);
    }

    /**
     * Perform DELETE request to the destination endpoint.
     *
     * @param endpointUrl target url endpoint.
     * @param data        request body.
     * @param headers     headers for request.
     * @param type        type of required object.
     * @return RestResponse.
     */
    private RestResponse deleteRequest(String endpointUrl,
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
                    .delete(ClientResponse.class);
            restResponse = new RestResponse(clientResponse);
        } catch (Exception ex) {
            LOGGER.error(String.format(ERROR_MESSAGE_TEMPLATE, ex));
            throw ex;
        }

        return restResponse;
    }

    //endregion

    //endregion

    //region RequestUtils

    /**
     * Parsed the response and return the object.
     *
     * @param restResponse result of request.
     * @param type         type required object.
     * @param <T>          reference to type.
     * @return required object.
     */
    private <T> T parseResponse(RestResponse restResponse,
                                TypeReference<T> type
    ) {
        try {
            return objectMapper.readValue(restResponse.getStringEntity(), type);
        } catch (JsonParseException ex) {
            LOGGER.error(String.format("JSON parse exception occurred: %s", ex.getMessage()));
        } catch (JsonMappingException ex) {
            LOGGER.error(String.format("JSON mapping exception occurred: %s", ex.getMessage()));
        } catch (IOException ex) {
            LOGGER.error(String.format("IOException exception occurred: %s", ex.getMessage()));
        }

        return null;
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
            case ACCEPTED:
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
     * Convert object to string JSON.
     *
     * @param object POJO for serialization.
     * @return string representation of object.
     */
    private String getEntityString(Object object) {
        String result = "";
        try {
            result = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException ex) {

        }
        if (!result.isEmpty()) {
            return result;
        }

        return null;
    }

    /**
     * Add headers to the response.
     *
     * @param builder - builder of the request.
     * @param headers - map of headers.
     * @return - builder with headers.
     */
    private WebResource.Builder addHeaders(
            WebResource.Builder builder,
            MultivaluedMap<String, String> headers
    ) {
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

    //endregion

}

