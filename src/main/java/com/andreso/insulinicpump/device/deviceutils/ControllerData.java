package com.andreso.insulinicpump.device.deviceutils;

import java.sql.Timestamp;
import java.util.LinkedList;

public class ControllerData {

    private boolean isFirstTimeStamp;
    private Timestamp firstTimeStamp;

    private int safeLowBound;
    private int safeHighBound;

    private String timeStamp;
    private int currentBloodGlucoseReading;
    private int derivative;
    private LinkedList<Integer> readings;

    private int batteryLevel;
    private int insulinReservoir;
    private String deviceStatus;
    private String graphDuration;

    private int deliveredInsulin;
    private int minimumDose;

    public ControllerData(){
        isFirstTimeStamp = true;

        safeLowBound=72;
        safeHighBound=126;

        timeStamp = "";
        currentBloodGlucoseReading = 0;
        derivative=0;
        readings = new LinkedList<>();

        batteryLevel = 0;
        insulinReservoir = 0;
        deviceStatus = "OK";
        graphDuration = "";

        deliveredInsulin=0;
        minimumDose = 5;
    }

    public LinkedList<Integer> getReadings(){
        return this.readings;
    }

    public boolean isFirstTimeStamp() {
        return isFirstTimeStamp;
    }

    public void setFirstTimeStamp(boolean firstTimeStamp) {
        isFirstTimeStamp = firstTimeStamp;
    }

    public Timestamp getFirstTimeStamp() {
        return firstTimeStamp;
    }

    public void setFirstTimeStamp(Timestamp firstTimeStamp) {
        this.firstTimeStamp = firstTimeStamp;
    }

    public int getSafeLowBound() {
        return safeLowBound;
    }

    public int getSafeHighBound() {
        return safeHighBound;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getCurrentBloodGlucoseReading() {
        return currentBloodGlucoseReading;
    }

    public void setCurrentBloodGlucoseReading(int currentBloodGlucoseReading) {
        this.currentBloodGlucoseReading = currentBloodGlucoseReading;
    }

    public int getDerivative() {
        return derivative;
    }

    public void setDerivative(int derivative) {
        this.derivative = derivative;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(int batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public int getInsulinReservoir() {
        return insulinReservoir;
    }

    public void setInsulinReservoir(int insulinReservoir) {
        this.insulinReservoir = insulinReservoir;
    }

    public String getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String getGraphDuration() {
        return graphDuration;
    }

    public void setGraphDuration(String graphDuration) {
        this.graphDuration = graphDuration;
    }

    public int getDeliveredInsulin() {
        return deliveredInsulin;
    }

    public void setDeliveredInsulin(int deliveredInsulin) {
        this.deliveredInsulin = deliveredInsulin;
    }

    public int getMinimumDose() {
        return minimumDose;
    }
}
