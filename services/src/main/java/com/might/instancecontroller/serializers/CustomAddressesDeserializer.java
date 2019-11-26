package com.might.instancecontroller.serializers;

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
        List<Network> networkList = new ArrayList<>();
        String networkName = "";
        Network network = new Network();
        ObjectCodec oc = p.getCodec();
        JsonNode jsonNode = oc.readTree(p);
        Iterator<JsonNode> iterator = jsonNode.elements();
        while (iterator.hasNext()) {
            JsonNode el = iterator.next();
            System.out.println(el);
            Iterator<JsonNode> iterator1 = el.iterator();
            System.out.println(iterator1.next());
        }

//        networkName = p.getCurrentName();
//        networkList.add(network);
//        networks.put(networkName, networkList);

//        if (p.getCurrentName().contains("mac_addr")) {
//            p.nextToken();
//            network.setMacAddr(p.getText());
//        }
//        p.nextToken();
//        if (p.getCurrentName().contains("version")) {
//            p.nextToken();
//            network.setVersion(Integer.parseInt(p.getText()));
//        }
//        p.nextToken();
//        if (p.getCurrentName().contains("addr")) {
//            p.nextToken();
//            network.setAddr(p.getText());
//        }
//        p.nextToken();
//        if (p.getCurrentName().contains("type")) {
//            p.nextToken();
//            network.setType(p.getText());
//        }

        addresses.setNetworks(networks);
        return addresses;
    }

}
