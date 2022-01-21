package com.andreso.insulinicpump.devicecontroller;

import com.andreso.insulinicpump.model.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Controller {

    private boolean isFirstTimeStamp = true;
    private Timestamp firstTimeStamp;

    /* data */
    private String timeStamp;
    private int BG;
    private int batteryLevel;
    private int insulinReservoir;
    private String deviceStatus;
    private Float deliveredInsulin;
    private int derivative;

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
        batteryLevel = 0;insulinReservoir = 0;deviceStatus = "OK";deliveredInsulin=0.0f;derivative=1;
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

        requestHandler.sendDataPoint(timeStamp,BG,derivative);
        requestHandler.sendDeviceInformation(batteryLevel,insulinReservoir,calculateGraphDuration(timeStamp),deviceStatus,deliveredInsulin);

        /* mocked data */
        List<Integer> derivatives = new ArrayList<>();
        derivatives.add(-1);derivatives.add(0);derivatives.add(1);
        derivative =derivatives.get(new Random().nextInt(derivatives.size()));
        batteryLevel++;
        insulinReservoir++;
        deliveredInsulin++;
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

    private String calculateGraphDuration(String timestamp){
        Timestamp currentTimeStamp = createTimeStamp(timestamp);

        long difference = currentTimeStamp.getTime() - firstTimeStamp.getTime();
        int hours = (int) TimeUnit.MILLISECONDS.toHours(difference);

        return hours + " h";
    }
}
