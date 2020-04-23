package org.might.instancecontroller.services.impl;

import com.jcraft.jsch.*;
import org.might.instancecontroller.services.ConfigurationVMService;
import org.might.instancecontroller.utils.SettingsHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ConfigurationVMServiceImpl implements ConfigurationVMService {

    private static SettingsHelper SETTINGS_HELPER;
    private static final int SSH_PORT = 22;
    private static final int CONNECTION_TIMEOUT = 10000;
    private static final int BUFFER_SIZE = 1024;
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationVMServiceImpl.class);

    public void setUpVM(String host, String scripts) {
        LOGGER.debug("Start configuration VM for monitoring, ip address: {}, scripts: {}", host, scripts);
        if (!executeCommand(host, scripts)) {
            throw new RuntimeException(
                    String.format(
                            "Something wen't wrong during configuration VM: %s, scripts: %s",
                            host,
                            scripts
                    )
            );
        }
    }

    private static boolean executeCommand(String host, String commands) {
        JSch jSch = new JSch();
        try {
            Session session = jSch.getSession(SETTINGS_HELPER.getVmUserName(), host, SSH_PORT);
            session.setPassword(SETTINGS_HELPER.getVmPassword());
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect(CONNECTION_TIMEOUT);
            Channel channel = session.openChannel("exec");
            ChannelExec channelExec = (ChannelExec) channel;
            channelExec.setCommand(commands);
            channelExec.setInputStream(null);
            channelExec.setErrStream(System.err);
            channelExec.connect();
            InputStream in = channelExec.getInputStream();
            String dataFromChannel = readResponse(channelExec, in, null);
            List<String> allList = new ArrayList<>(Collections.singletonList(dataFromChannel));
            allList.forEach(System.out::println);
            channel.disconnect();
            session.disconnect();
        } catch (JSchException | IOException ex) {
            System.out.println(
                    String.format(
                            "%s \n %s",
                            ex.getMessage(),
                            ex.getLocalizedMessage()
                    )
            );
            return false;
        }

        return true;
    }

    private static String readResponse(Channel channel, InputStream inputStream, String encoding) {
        try {
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[BUFFER_SIZE];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            if (encoding == null || encoding.isEmpty()) {
                return result.toString();
            } else {
                return result.toString(encoding);
            }
        } catch (IOException e) {
            throw new RuntimeException(
                    "Error while reading the response from server",
                    e);
        }
    }
/*
    private static String readResponse(Channel channel, InputStream in)
            throws IOException {
        StringBuilder result = new StringBuilder();
        byte[] tmp = new byte[BUFFER_SIZE];
        while (true) {
            while (in.available() > 0) {
                int i = in.read(tmp, 0, BUFFER_SIZE);
                if (i < 0) {
                    break;
                }
                result.append(new String(tmp, 0, i));
            }
            if (channel.isClosed()) {
                int exitStatus = channel.getExitStatus();
                System.out.println("exit-status: " + exitStatus);
                break;
            }
//            trySleep(1000);
        }
        return result.toString();
    }*/

    @Autowired
    public ConfigurationVMServiceImpl(SettingsHelper settingsHelper) {
        this.SETTINGS_HELPER = settingsHelper;
    }
}
