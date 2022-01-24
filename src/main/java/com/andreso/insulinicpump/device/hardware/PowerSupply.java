package com.andreso.insulinicpump.device.hardware;

public class PowerSupply extends DeviceComponent{

    @Override
    public boolean selfTest() {
        return this.isDeviceWorkingProperly;
    }


}
