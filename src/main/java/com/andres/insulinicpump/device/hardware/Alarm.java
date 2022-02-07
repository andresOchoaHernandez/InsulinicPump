package com.andres.insulinicpump.device.hardware;

public class Alarm extends DeviceComponent{

    @Override
    public boolean selfTest() {
        return this.isDeviceWorkingProperly;
    }


}
