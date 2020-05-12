package org.might.instancecontroller.utils;

import org.might.instancecontroller.dba.entity.Event;
import org.might.instancecontroller.dba.entity.Function;
import org.might.instancecontroller.dba.entity.Server;
import org.might.instancecontroller.models.function.NetworkModel;
import org.might.instancecontroller.models.function.ServerCreateModel;
import org.might.instancecontroller.models.servers.OpenstackServer;
import org.might.instancecontroller.services.EventService;
import org.might.instancecontroller.services.FunctionService;
import org.might.instancecontroller.services.FunctionStatus;
import org.might.instancecontroller.services.ServerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Service
public class FunctionHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(FunctionHelper.class);

    private static final String UNDERSCORE = "_";
    private static final String DISK_CONFIG = "AUTO";
    private static final String ZONA = "nova";
    public static final String NETWORK_NAME_PRIVATE = "internal_network";
    public static final String NETWORK_NAME_PUBLIC = "external_network";
    public static final String UUID_PRIVATE = "cb9cb58f-40a5-48dd-817e-b97730dd7f27";
    public static final String UUID_PUBLIC = "df1fa7d3-3cee-4f95-9590-cef671fab31f";
    private static final String FUNCTION_NAME_PATTERN = "(function_name)";
    private static EventService EVENT_SERVICE;
    private static FunctionService FUNCTION_SERVICE;
    private static ServerService SERVER_SERVICE;
    private static final String FUNCTION_TEMPLATE = "Got function - " +
            "Id: {}, " +
            "Name: {}, " +
            "Image: {}, " +
            "Flavor: {}, " +
            "Description: {}, " +
            "Configuration: {}";

    private static final String SERVER_TEMPLATE =
            "Server Id: {}, MonitoringId: {}, Name: {}, ServerId: {}";

    /**
     * Compare the functions.
     * @param function function.
     * @return Boolean
     */
    public static boolean compareWithDBFunction(Function function) {
        Function functionFromDB;
        if (FUNCTION_SERVICE.getFunctionById(function.getId()).isPresent()) {
            functionFromDB = FUNCTION_SERVICE.getFunctionById(function.getId()).get();
        } else {
            return false;
        }

        return function.equals(functionFromDB);
    }

    /**
     * Return server list for particular function.
     * @param function function.
     * @return {@link List<Server>}
     */
    public static List<Server> getFunctionServers(Function function) {
        return SERVER_SERVICE
                .getAll()
                .stream()
                .filter(el -> el.getFunction().equals(function))
                .collect(Collectors.toList());
    }

    /**
     * Return server create model.
     * @param function function.
     * @return {@link ServerCreateModel}
     */
    public static ServerCreateModel getServerCreateModelAutoNetwork(Function function) {
        ServerCreateModel serverCreateModel = new ServerCreateModel();
        serverCreateModel.setName(getNewServerName(function));
        serverCreateModel.setDiskConfig(DISK_CONFIG);
        serverCreateModel.setFlavorRef(function.getFlavor().getReference());
        serverCreateModel.setImageRef(function.getImage().getReference());
        serverCreateModel.setZone(ZONA);
        List<NetworkModel> networkModelList = new ArrayList<>() {{
            add(new NetworkModel(UUID_PRIVATE));
            add(new NetworkModel(UUID_PUBLIC));
        }};

        serverCreateModel.setNetworks(networkModelList);
        return serverCreateModel;
    }

    /**
     * Return server create model.
     * @param server {@link Server}
     * @param openstackServer {@link OpenstackServer}
     * @return {@link ServerCreateModel}
     */
    public static ServerCreateModel getServerCreateModelWithParticularNetwork(
            Server server,
            OpenstackServer openstackServer) {
        ServerCreateModel serverCreateModel = new ServerCreateModel();
        serverCreateModel.setName(server.getName());
        serverCreateModel.setDiskConfig(DISK_CONFIG);
        serverCreateModel.setFlavorRef(server.getFunction().getFlavor().getReference());
        serverCreateModel.setImageRef(server.getFunction().getImage().getReference());
        serverCreateModel.setZone(ZONA);
        List<NetworkModel> networkModelList = new ArrayList<>() {{
            add(new NetworkModel(
                    UUID_PRIVATE,
                    openstackServer
                            .getAddresses()
                            .getNetworks()
                            .get(FunctionHelper.NETWORK_NAME_PRIVATE)
                            .get(0)
                            .getAddr()
                    )
            );
            add(new NetworkModel(
                    UUID_PUBLIC,
                    openstackServer
                            .getAddresses()
                            .getNetworks()
                            .get(FunctionHelper.NETWORK_NAME_PUBLIC)
                            .get(0)
                            .getAddr()
                    )
            );
        }};
        serverCreateModel.setNetworks(networkModelList);
        return serverCreateModel;
    }

    /**
     * Return a Function from DB.
     * @param id
     * @return <{@link Function}>
     */
    public static Function getFunctionById(Long id){
        Function function;
        if (FUNCTION_SERVICE.getFunctionById(id).isPresent()) {
            function = FUNCTION_SERVICE.getFunctionById(id).get();
        } else {
            LOGGER.error("Function with Id: {} did't find at DBs", id);
            throw new RuntimeException(
                    String.format(
                            "Function with Id: %s did't find at DBs",
                            id
                    )
            );
        }
        LOGGER.debug(FUNCTION_TEMPLATE,
                function.getId(),
                function.getName(),
                function.getImage().getReference(),
                function.getFlavor().getReference(),
                function.getDescription(),
                function.getConfiguration().getScript());

        return function;
    }

    /**
     * Return prepared scripts for configuration VM.
     * @param function
     * @return String.
     */
    public static String getScriptsForFunction(Function function, String serverName) {
        String scripts = Pattern.compile(FUNCTION_NAME_PATTERN, Pattern.CASE_INSENSITIVE)
                .matcher(function.getConfiguration().getScript())
                .replaceAll(serverName);
        LOGGER.debug("Generated scripts to set up vm: {}", scripts);
        return scripts;
    }

    /**
     * Return {@link Server} server to release.
     * @param function {@link Function}
     * @return
     */
    public static Server getServerToRelease(Function function) {
        List<Server> serverList = getFunctionServers(function);
        if (serverList.isEmpty()) {
            return null;
        }

        serverList.sort(new ServerAmountComparator());
        Server server = serverList
                .get(serverList.size() -1);
        LOGGER.debug("Get server to release: " + SERVER_TEMPLATE,
                server.getId(),
                server.getMonitoringId(),
                server.getName(),
                server.getServerId());

        return server;
    }

    public static FunctionStatus getFunctionStatusByFunction(Function function) {
        List<Server> servers = FunctionHelper.getFunctionServers(function);
        servers = servers.stream().filter(server ->
                server.getMonitoringId() != null
        ).collect(Collectors.toList());
//        servers = servers
//                .stream()
//                .filter(server -> server.getMonitoringId() != null)
//                .collect(Collectors.toList());
        List<Event> activeEvents = FunctionHelper.getActiveEventsByFunction(function);

        if (activeEvents.size() > 0 && servers.size() > 0 && activeEvents.size() == servers.size()) {
            return FunctionStatus.CRITICAL;
        }

        if (activeEvents.size() == 0 && servers.size() != 0) {
            return FunctionStatus.ACTIVE;
        }

        if (activeEvents.size() > 0) {
            return FunctionStatus.HAS_PROBLEM;
        }

        return FunctionStatus.UNKNOWN;

    }

    /**
     * Return list of only active events for function.
     * @param function function.
     * @return {@link List<Event>}
     */
    public static List<Event> getActiveEventsByFunction(Function function) {
        return getEventsByFunction(function,false);
    }

    public static List<Event> getActiveEventsByFunctionWithLag(Function function) {
        return getEventsByFunction(function,true);
    }

    /**
     * Return list of only active events for function.
     * @param function function.
     * @return {@link List<Event>}
     */
    private static List<Event> getEventsByFunction(Function function,
                                                  boolean loadWithLag) {
        List<Server> servers = FunctionHelper.getFunctionServers(function);
        List<Event> events = EVENT_SERVICE.getAll().stream()
                    .filter(Event::getActive)
                    .collect(Collectors.toList());

        if (loadWithLag) {
            events.addAll(
                    EVENT_SERVICE.getAll().stream()
                            .filter(event -> !event.getActive())
                            .collect(Collectors.toList())
                            .stream()
                            .filter(event -> {
                        if (event.getRecoveryDate().toString()
                                .equals(new Date(System.currentTimeMillis()).toString())) {
                            LocalTime localTime = new Time(System.currentTimeMillis() - 5 * 60 * 1000).toLocalTime();
                            LocalTime localTime1 = event.getRecoveryTime().toLocalTime();
                            if (localTime.getHour() == localTime1.getHour()) {
                                return localTime.getMinute() <= localTime1.getMinute();
                            }
                            return false;
                        }
                        return false;
                    }).collect(Collectors.toList())
            );
        }

        List<Event> result = new ArrayList<>();
        servers.forEach(server -> {
            for (Event event: events.stream().distinct().collect(Collectors.toList())) {
                if (Objects.isNull(server) || Objects.isNull(event)) {
                    return;
                }

                if (loadWithLag && server.getName().equals(event.getHostName())) {
                    result.add(event);
                    return;
                }

                if (server.getServerId().equals(event.getServerId())) {
                    result.add(event);
                }
            }
        });

        return result;
    }

    /**
     * Return name for new Instance of the function.
     * @param function function.
     * @return
     */
    private static String getNewServerName(Function function) {
        List<Server> serverList = getFunctionServers(function);
        int size;
        if (serverList.isEmpty()) {
            size = 1;
        } else {
            serverList.sort(new ServerAmountComparator());
            size = Integer.parseInt(
                    serverList.get(serverList.size() -1).getName().split(UNDERSCORE)[2]
            ) + 1;
        }

        String serverName = function.getName() +
                UNDERSCORE +
                function.getPrefix() +
                UNDERSCORE +
                size;
        LOGGER.debug("Prepared server name: {}", serverName);
        return serverName;
    }


    @Autowired
    private FunctionHelper(FunctionService FUNCTION_SERVICE,
                           ServerService SERVER_SERVICE,
                           EventService EVENT_SERVICE) {
        FunctionHelper.FUNCTION_SERVICE = FUNCTION_SERVICE;
        FunctionHelper.SERVER_SERVICE = SERVER_SERVICE;
        FunctionHelper.EVENT_SERVICE = EVENT_SERVICE;
    }

}
