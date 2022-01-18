package com.andreso.insulinicpump.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DeviceData {

    @Id
    private Long id=1l;
    private Integer batteryLevel;
    private Integer insulinReservoir;
    private Integer graphDuration;
    private String deviceStatus;

    protected DeviceData(){}

    public DeviceData(Integer batteryLevel,Integer insulinReservoir,Integer graphDuration,String deviceStatus){
        this.batteryLevel = batteryLevel;
        this.insulinReservoir = insulinReservoir;
        this.graphDuration = graphDuration;
        this.deviceStatus = deviceStatus;
    }

    @Override
    public String toString() {
        return "DeviceData{" +
                "id=" + id +
                ", batteryLevel=" + batteryLevel +
                ", insulinReservoir=" + insulinReservoir +
                ", graphDuration=" + graphDuration +
                ", deviceStatus='" + deviceStatus + '\'' +
                '}';
    }

    public String getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(Integer batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public Integer getInsulinReservoir() {
        return insulinReservoir;
    }

    public void setInsulinReservoir(Integer insulinReservoir) {
        this.insulinReservoir = insulinReservoir;
    }

    public Integer getGraphDuration() {
        return graphDuration;
    }

    public void setGraphDuration(Integer graphDuration) {
        this.graphDuration = graphDuration;
    }
}
