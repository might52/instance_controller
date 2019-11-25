package com.might.instancecontroller.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.might.instancecontroller.models.servers.Addresses;
import com.might.instancecontroller.models.servers.Network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CustomAddressesDeserialize extends StdDeserializer<Addresses> {

    public CustomAddressesDeserialize() {
        this(null);
    }

    public CustomAddressesDeserialize(Class<?> vc) {
        super(vc);
    }

    @Override
    public Addresses deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        if (p.getCurrentName().equals("addresses")) {
            return null;
        }


        Addresses addresses = new Addresses();
        Map<String, List<Network>> networks = new HashMap<>();
        List<Network> networkList = new ArrayList<>();
        String networkName = "";
        Network network = new Network();
        while (p.nextToken() != null && p.getCurrentName().equals("links")) {
            switch (p.getCurrentToken()){
                case FIELD_NAME:
                case START_ARRAY:
                    network = new Network();
                    break;
                case START_OBJECT:
                    p.nextToken();
                    if (p.getCurrentName().contains("mac_addr")) {
                        p.nextToken();
                        network.setMacAddr(p.getText());
                    }
                    p.nextToken();
                    if (p.getCurrentName().contains("version")) {
                        p.nextToken();
                        network.setVersion(Integer.parseInt(p.getText()));
                    }
                    p.nextToken();
                    if (p.getCurrentName().contains("addr")) {
                        p.nextToken();
                        network.setAddr(p.getText());
                    }
                    p.nextToken();
                    if (p.getCurrentName().contains("type")) {
                        p.nextToken();
                        network.setType(p.getText());
                    }
                    break;
                case END_OBJECT:
                case END_ARRAY:
                    networkName = p.getCurrentName();
                    networkList.add(network);
                    networks.put(networkName, networkList);
                    break;
                default:
                    break;
            }
        }
        addresses.setNetworks(networks);
        return addresses;
    }

}
