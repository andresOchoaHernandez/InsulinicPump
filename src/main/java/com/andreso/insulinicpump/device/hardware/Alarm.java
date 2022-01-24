package com.andreso.insulinicpump.device.hardware;

public class Alarm extends DeviceComponent{

    @Override
    public boolean selfTest() {
        return this.isDeviceWorkingProperly;
    }


}
