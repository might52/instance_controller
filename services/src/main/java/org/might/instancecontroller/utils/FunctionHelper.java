package org.might.instancecontroller.utils;

import org.might.instancecontroller.dba.entity.Function;
import org.might.instancecontroller.dba.entity.Server;
import org.might.instancecontroller.models.function.NetworkModel;
import org.might.instancecontroller.models.function.ServerCreateModel;
import org.might.instancecontroller.services.FunctionService;
import org.might.instancecontroller.services.ServerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
    private static FunctionService FUNCTION_SERVICE;
    private static ServerService SERVER_SERVICE;
    private static final String FUNCTION_TEMPLATE = "Got function - " +
            "Id: {}, " +
            "Name: {}, " +
            "Image: {}, " +
            "Flavor: {}, " +
            "Description: {}, " +
            "Configuration: {}";


    /**
     * Compare the functions.
     * @param function
     * @return Boolean
     */
    public static boolean compareFunction(Function function) {
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
     * @param function
     * @return
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
     * @param function
     * @return
     */
    public static ServerCreateModel getServerCreateModelAutoNetwork(Function function) {
        ServerCreateModel serverCreateModel = new ServerCreateModel();
        serverCreateModel.setName(getServerName(function));
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
     * Return name for new Instance of the function.
     * @param function
     * @return
     */
    private static String getServerName(Function function) {
        List<Server> serverList = SERVER_SERVICE
                .getAll()
                .stream()
                .filter(el -> el.getFunction().equals(function))
                .collect(Collectors.toList());
        int size = serverList.isEmpty() ? 1 : serverList.size() + 1;
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
                           ServerService SERVER_SERVICE) {
        FunctionHelper.FUNCTION_SERVICE = FUNCTION_SERVICE;
        FunctionHelper.SERVER_SERVICE = SERVER_SERVICE;
    }


}
