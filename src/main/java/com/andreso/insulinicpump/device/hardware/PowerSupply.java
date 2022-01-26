package com.andreso.insulinicpump.device.hardware;

public class PowerSupply extends DeviceComponent{

    private Float batteryLevel = 100f;

    private Float readBatteryLevelFromDriver(){
        batteryLevel -= 0.1f;
        return batteryLevel;
    }

    public int getBatteryLevel(){
        return Math.round(readBatteryLevelFromDriver());
    }

    @Override
    public boolean selfTest() {
        return this.isDeviceWorkingProperly;
    }


}
