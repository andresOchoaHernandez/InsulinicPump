package com.andreso.insulinicpump.devicecontroller;

import com.andreso.insulinicpump.model.*;

public class Controller {

    /* data */
    private String timeStamp;
    private int BG;
    private int batteryLevel;
    private int insulinReservoir;
    private int graphDuration;
    private String deviceStatus;

    /* hardware components */
    private DeviceDisplay display;
    private BloodSensor bloodSensor;
    private Clock clock;
    private PowerSupply powerSupply;
    private Alarm alarm;

    /* helper class */
    private RequestHandler requestHandler;


    public Controller(){
        display = new DeviceDisplay();
        bloodSensor = new BloodSensor();
        clock = new Clock();
        powerSupply = new PowerSupply();
        alarm = new Alarm();
        requestHandler = new RequestHandler();

        /* data */
        timeStamp = "";BG = 0;

        /* mocked data */
        batteryLevel = 0;insulinReservoir = 0;graphDuration = 0;deviceStatus = "OK";
    }

    public void turnOnDisplay(){
        this.display.turnOn();
    }

    public void turnOffDisplay(){
        this.display.turnOff();
    }

    public void readGlucoseLevel(){
        System.out.println("Reading glucose levels ...");

        String[] datapoint = bloodSensor.getMeasurement();
        this.timeStamp = datapoint[0];
        this.BG = Integer.parseInt(datapoint[1]);
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

        requestHandler.sendDataPoint(timeStamp,BG);
        requestHandler.sendDeviceInformation(batteryLevel,insulinReservoir,graphDuration,deviceStatus);

        /* mocked data */
        batteryLevel++;
        insulinReservoir++;
        graphDuration++;
    }

    public void standByMode() {
        System.out.println("Entering stand by mode ...");


        try{
            Thread.sleep(50);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }


    }
}
