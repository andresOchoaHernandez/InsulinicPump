package com.andreso.insulinicpump.device.hardware;

public abstract class DeviceComponent {

    protected boolean isDeviceWorkingProperly=true;

    public abstract boolean selfTest();

    public void setDeviceStateVariable(boolean isDeviceWorkingProperly){
        this.isDeviceWorkingProperly = isDeviceWorkingProperly;
    }

    public boolean getDeviceStateVariable(){
        return this.isDeviceWorkingProperly;
    }
}
