package com.might.instancecontroller.services;

import com.fasterxml.jackson.annotation.JsonRootName;

import java.io.Serializable;

public class BaseActions implements Serializable {

    @JsonRootName(value = "os-start")
    public static class Start implements Serializable {
    }

    @JsonRootName(value = "os-stop")
    public static class Stop implements Serializable{
    }
}
