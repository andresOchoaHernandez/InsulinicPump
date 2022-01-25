package com.andreso.insulinicpump.device.hardware;

public class PowerSupply extends DeviceComponent{

    private int batteryLevel;

    public PowerSupply(){
        batteryLevel = 100;
    }



    @Override
    public boolean selfTest() {
        return this.isDeviceWorkingProperly;
    }


}
