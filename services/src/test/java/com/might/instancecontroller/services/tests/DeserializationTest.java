package com.might.instancecontroller.services.tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.might.instancecontroller.models.servers.Addresses;
import com.might.instancecontroller.models.servers.Instance;
import com.might.instancecontroller.models.servers.Network;
import com.might.instancecontroller.models.servers.Server;
import com.might.instancecontroller.services.InstanceStatus;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Provider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DeserializationTest {

    private String serverResponce;
    private ObjectMapper jsonSerializer;

    public DeserializationTest() throws IOException {
        this.serverResponce = Files.readString(Paths.get("src/main/resources/Jsons/Server.json"), StandardCharsets.UTF_8);
        this.jsonSerializer = new ObjectMapper();
        this.jsonSerializer.enable(SerializationFeature.WRAP_ROOT_VALUE);
    }

    @Test
    public void canGetStatusServer() throws IOException {
        Instance instance = this.jsonSerializer.readValue(this.serverResponce, new TypeReference<Instance>() {});
        String status = instance.getServer().getStatus();
        Assert.assertEquals(InstanceStatus.ACTIVE, InstanceStatus.getInstanceStatus(status));
    }

    @Test
    public void canGetServerName() throws IOException {
        Instance instance = this.jsonSerializer.readValue(this.serverResponce, new TypeReference<Instance>() {});
        String name = instance.getServer().getName();
        Assert.assertEquals("first_vm_node", name);
    }

    @Test
    public void canGetAddresses() throws IOException {
        Instance instance = this.jsonSerializer.readValue(this.serverResponce, new TypeReference<Instance>() {});
        Assert.assertEquals(getEtalonInstance().getServer().getAddresses(), instance.getServer().getAddresses());
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
