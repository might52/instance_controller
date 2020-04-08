package org.might.instancecontroller.utils;

import org.might.instancecontroller.dba.entity.Function;
import org.might.instancecontroller.dba.entity.Server;
import org.might.instancecontroller.models.function.NetworkModel;
import org.might.instancecontroller.models.function.ServerCreateModel;
import org.might.instancecontroller.services.FunctionService;
import org.might.instancecontroller.services.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FunctionHelper {

    private static final String COLON = ":";
    private static final String DISK_CONFIG = "AUTO";
    private static final String ZONA = "nova";
    private static final String UUID_PRIVATE = "cb9cb58f-40a5-48dd-817e-b97730dd7f27";
    private static final String UUID_PUBLIC = "df1fa7d3-3cee-4f95-9590-cef671fab31f";
    private static FunctionService FUNCTION_SERVICE;
    private static ServerService SERVER_SERVICE;

    public static Boolean compareFunction(Function function) {
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
        if (serverList.isEmpty()) {
            return function.getName() +
                    COLON +
                    function.getPrefix() +
                    COLON +
                    "1";
        }

        int size = serverList.size() + 1;
        return function.getName() +
                COLON +
                function.getPrefix() +
                COLON +
                size;
    }

    @Autowired
    private FunctionHelper(FunctionService FUNCTION_SERVICE,
                           ServerService SERVER_SERVICE) {
        FunctionHelper.FUNCTION_SERVICE = FUNCTION_SERVICE;
        FunctionHelper.SERVER_SERVICE = SERVER_SERVICE;
    }

}
