package com.andreso.insulinicpump.devicecontroller;

import com.andreso.insulinicpump.model.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;


// TODO : MODIFY GRAPH DURATION IN UI, CONTROLLER ETC TO SHOW GRAPH DURATION IN HOURS AND MINUTES

public class Controller {

    private boolean isFirstTimeStamp = true;
    private Timestamp firstTimeStamp;

    /* data */
    private String timeStamp;
    private int BG;
    private int batteryLevel;
    private int insulinReservoir;
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
        batteryLevel = 0;insulinReservoir = 0;deviceStatus = "OK";
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

        if (isFirstTimeStamp)
            this.firstTimeStamp = createTimeStamp(timeStamp);
        isFirstTimeStamp = false;
    }

    private Timestamp createTimeStamp(String timeStamp){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date parsedDate = new Date(dateFormat.parse(timeStamp).getTime());
            return new java.sql.Timestamp(parsedDate.getTime());
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
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
        System.out.println(calculateGraphDuration(timeStamp));
        requestHandler.sendDeviceInformation(batteryLevel,insulinReservoir,calculateGraphDuration(timeStamp),deviceStatus);

        /* mocked data */
        batteryLevel++;
        insulinReservoir++;
    }

    private int calculateGraphDuration(String timestamp){
        Timestamp currentTimeStamp = createTimeStamp(timestamp);
        return (int) ((currentTimeStamp.getTime() - firstTimeStamp.getTime())/1000)%3600/60;
    }

    public void standByMode() {
        System.out.println("Entering stand by mode ...");


        try{
            Thread.sleep(5000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }


    }
}
