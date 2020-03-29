package org.might.instancecontroller.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Assert;
import org.junit.Test;
import org.might.instancecontroller.models.servers.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DeserializationTest {

    private final String serversReponse;
    private final String serverResponse;
    private final ObjectMapper jsonSerializer;

    public DeserializationTest() throws IOException {
        this.serverResponse = Files.readString(Paths.get("src/main/resources/Jsons/Server.json"), StandardCharsets.UTF_8);
        this.serversReponse = Files.readString(Paths.get("src/main/resources/Jsons/Servers.json"), StandardCharsets.UTF_8);
        this.jsonSerializer = new ObjectMapper();
        this.jsonSerializer.enable(SerializationFeature.WRAP_ROOT_VALUE);
        this.jsonSerializer.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

//        this.jsonSerializer.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
//        this.jsonSerializer.enable(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS);
//        this.jsonSerializer.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    @Test
    public void canGetServerStatus() throws IOException {
        Instance instance = this.jsonSerializer.readValue(this.serverResponse, new TypeReference<Instance>() {});
        String status = instance.getServer().getStatus();
        Assert.assertEquals(ServerStatus.ACTIVE, ServerStatus.getServerStatus(status));
    }

    @Test
    public void canGetServerName() throws IOException {
        Instance instance = this.jsonSerializer.readValue(this.serverResponse, new TypeReference<Instance>() {});
        String name = instance.getServer().getName();
        Assert.assertEquals("first_vm_node", name);
    }

    @Test
    public void canGetServerAddresses() throws IOException {
        Instance instance = this.jsonSerializer.readValue(this.serverResponse, new TypeReference<Instance>() {});
        Assert.assertEquals(getEtalonInstance().getServer().getAddresses(), instance.getServer().getAddresses());
    }

    @Test
    public void canGetServerList() throws IOException {
        Servers instances =
                this.jsonSerializer.readValue(
                        this.serversReponse,
                        new TypeReference<Servers>() {});
        Assert.assertNotNull(instances);
    }

    /**
     * Getting the etalon instance.
     * @return Instance.class
     */
    private Instance getEtalonInstance() {
        Instance instance = new Instance();
        Server server = new Server();
        Addresses addresses = new Addresses();
        Map<String, List<Network>> networks = new HashMap<>();
        List<Network> networkList = new ArrayList<>() {{
            add(new Network(){{
                setMacAddr("fa:16:3e:6d:58:59");
                setVersion(4);
                setAddr("192.168.30.26");
                setType("fixed");
            }});
        }};
        networks.put("internal_network", networkList);
        networkList = new ArrayList<>() {{
            add(new Network(){{
                setMacAddr("fa:16:3e:84:f6:4f");
                setVersion(4);
                setAddr("192.168.20.102");
                setType("fixed");
            }});
        }};
        networks.put("external_network", networkList);
        addresses.setNetworks(networks);
        server.setAddresses(addresses);
        instance.setServer(server);
        return instance;
    }
}
