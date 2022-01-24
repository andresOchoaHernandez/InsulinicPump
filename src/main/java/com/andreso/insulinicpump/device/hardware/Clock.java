package com.andreso.insulinicpump.device.hardware;

public class Clock extends DeviceComponent{

    @Override
    public boolean selfTest() {
        return this.isDeviceWorkingProperly;
    }


}