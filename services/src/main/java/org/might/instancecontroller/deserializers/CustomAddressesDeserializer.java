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
package org.might.instancecontroller.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.might.instancecontroller.models.servers.Addresses;
import org.might.instancecontroller.models.servers.Network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class CustomAddressesDeserializer extends JsonDeserializer<Addresses> {
    @Override
    public Addresses deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException {
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
