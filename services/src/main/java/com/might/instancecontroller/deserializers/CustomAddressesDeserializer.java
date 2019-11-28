package com.might.instancecontroller.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.might.instancecontroller.models.servers.Addresses;
import com.might.instancecontroller.models.servers.Network;

import java.io.IOException;
import java.util.*;


public class CustomAddressesDeserializer extends JsonDeserializer<Addresses> {

    @Override
    public Addresses deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        if (!p.getCurrentName().equals("addresses")) {
            return null;
        }

        Addresses addresses = new Addresses();
        Map<String, List<Network>> networks = new HashMap<>();
        List<Network> networkList;
        ObjectCodec oc = p.getCodec();
        JsonNode jsonNode = oc.readTree(p);
        Iterator<Map.Entry<String, JsonNode>> iterator = jsonNode.fields();
        while (iterator.hasNext()) {
            Map.Entry<String, JsonNode> el = iterator.next();
            networkList = new ArrayList<Network>();
            JsonNode field = el.getValue();
            Network network = new Network();
            field.forEach(e -> {
                Iterator<Map.Entry<String, JsonNode>> networkFields = e.fields();
                do {
                    Map.Entry<String, JsonNode> networkItem = networkFields.next();
                    if (networkItem.getKey().equals("OS-EXT-IPS-MAC:mac_addr")) {
                        network.setMacAddr(networkItem.getValue().asText());
                    }
                    if (networkItem.getKey().equals("version")) {
                        network.setVersion(Integer.parseInt(networkItem.getValue().asText()));
                    }
                    if (networkItem.getKey().equals("addr")) {
                        network.setAddr(networkItem.getValue().asText());
                    }
                    if (networkItem.getKey().equals("OS-EXT-IPS:type")) {
                        network.setType(networkItem.getValue().asText());
                    }
                } while (networkFields.hasNext());
            });
            networkList.add(network);
            networks.put(el.getKey(), networkList);
        }

        addresses.setNetworks(networks);
        return addresses;
    }

}
