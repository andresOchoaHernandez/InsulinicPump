package com.andreso.insulinicpump.device.hardware;

public class Pump extends DeviceComponent{

    @Override
    public boolean selfTest() {
        return this.isDeviceWorkingProperly;
    }

    public boolean deliverInsulin(){
        System.out.println("Delivering insulin ...");
        return true;
    }

}