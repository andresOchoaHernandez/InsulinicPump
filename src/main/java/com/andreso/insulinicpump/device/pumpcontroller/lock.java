package com.andreso.insulinicpump.device.pumpcontroller;

public class lock {
    private static lock instance = null;
    private lock(){}

    public static lock getInstance(){
        if(instance == null){
            instance = new lock();
        }
        return instance;
    }
}
