package com.andreso.insulinicpump.device.hardware;

public class Pump extends DeviceComponent{

    private int unitsOfInsulinReservoir;
    private int reservoirFullCapacity;

    public Pump(){
        reservoirFullCapacity = readInsulinReservoirFullCapacityFromDriver();
        unitsOfInsulinReservoir = reservoirFullCapacity;
    }

    private int readInsulinReservoirFullCapacityFromDriver(){
        /* =============== mocked data ============ */
        return 300;
        /* ======================================== */
    }

    @Override
    public boolean selfTest() {
        return this.isDeviceWorkingProperly;
    }

    public boolean deliverInsulin(int unitsOfInsulin){
        System.out.println("[PUMP] delivered " + unitsOfInsulin + " units of insulin");
        unitsOfInsulinReservoir -= unitsOfInsulin;
        return true;
    }

    public int getInsulinReservoirLevel(){
        return 100 * unitsOfInsulinReservoir / reservoirFullCapacity;
    }

}