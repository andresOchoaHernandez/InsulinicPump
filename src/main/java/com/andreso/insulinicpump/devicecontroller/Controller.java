package com.andreso.insulinicpump.devicecontroller;

import com.andreso.insulinicpump.model.*;

public class Controller {

    /* mocked data */
    private int datapoint = 0;
    private int batteryLevel = 0;
    private int insulinReservoir = 0;
    private int graphDuration = 0;
    private String deviceStatus = "OK";

    /* hardware components */
    private DeviceDisplay display = new DeviceDisplay();
    private BloodSensor bloodSensor = new BloodSensor();
    private Clock clock = new Clock();
    private PowerSupply powerSupply = new PowerSupply();
    private Alarm alarm = new Alarm();

    /* helper class */
    private RequestHandler requestHandler = new RequestHandler();

    public void turnOnDisplay(){
        this.display.turnOn();
    }

    public void turnOffDisplay(){
        this.display.turnOff();
    }

    public void readGlucoseLevel(){
        System.out.println("Reading glucose levels ...");
    }

    public void calculateInsulinDose(){
        System.out.println("Calculating insulin dose to deliver ...");
    }

    public void deliverInsulin(){
        System.out.println("Delivering insulin ...");
    }

    public void executeDeviceRoutineTest(){
        System.out.println("Executing self test routine ...");
    }

    public void sendInformationToViewController(){

        System.out.println("Sending information to server ...");

        requestHandler.sendDataPoint(datapoint,datapoint);
        requestHandler.sendDeviceInformation(batteryLevel,insulinReservoir,graphDuration,deviceStatus);

        /* mocked data */
        datapoint++;
        batteryLevel++;
        insulinReservoir++;
        graphDuration++;
    }

    public void standByMode() {
        System.out.println("Entering stand by mode ...");
        try{
            Thread.sleep(9000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
