package com.andreso.insulinicpump.device.hardware;

public class Pump extends DeviceComponent{

    @Override
    public boolean selfTest() {
        return this.isDeviceWorkingProperly;
    }


}