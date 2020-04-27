package org.might.instancecontroller.utils;

import org.might.instancecontroller.dba.entity.Server;

import java.util.Comparator;

public class ServerAmountComparator implements Comparator<Server> {
    private static final String UNDERSCORE = "_";

    @Override
    public int compare(Server server1, Server server2) {
        int number1 = Integer.parseInt(server1.getName().split(UNDERSCORE)[2]);
        int number2 = Integer.parseInt(server2.getName().split(UNDERSCORE)[2]);
        if (number1 == number2) {
            return 0;
        }

        if (number1 > number2) {
            return 1;
        } else {
            return -1;
        }
    }
}
