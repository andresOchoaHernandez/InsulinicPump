package com.andreso.insulinicpump.device.pumpcontroller;

import com.andreso.insulinicpump.device.deviceutils.HttpRequestHandler;
import com.andreso.insulinicpump.device.hardware.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class Controller {

    private boolean isFirstTimeStamp = true;
    private Timestamp firstTimeStamp;

    private LinkedList<Integer> readings;

    /* data */
    private int minimumDose;
    private final int safeLowBound;
    private final int safeHighBound;
    private String timeStamp;
    private int currentBloodGlucoseReading;
    private int batteryLevel;
    private int insulinReservoir;
    private String deviceStatus;
    private int deliveredInsulin;
    private int derivative;

    /* hardware components */
    private final Display display;
    private final BloodSensor bloodSensor;
    private final Clock clock;
    private final PowerSupply powerSupply;
    private final Alarm alarm;
    private final Pump pump;

    /* helper class */
    private final HttpRequestHandler httpRequestHandler;


    public Controller(){
        display = new Display();
        bloodSensor = new BloodSensor();
        clock = new Clock();
        powerSupply = new PowerSupply();
        alarm = new Alarm();
        httpRequestHandler = new HttpRequestHandler();
        pump = new Pump();

        /* data */
        timeStamp = "";
        currentBloodGlucoseReading = 0;safeLowBound=72;safeHighBound=126;
        readings = new LinkedList<>();
        deliveredInsulin=0;derivative=0;minimumDose = 5;deviceStatus = "OK";
        httpRequestHandler.sendSafeBounds(safeLowBound,safeHighBound);

        /* mocked data */
        batteryLevel = 0;insulinReservoir = 0;deviceStatus = "OK";
    }

    public void turnOnDisplay(){
        this.display.turnOn();
    }

    public void turnOffDisplay(){
        this.display.turnOff();
    }

    public void refreshDisplay(){this.display.refresh();}

    public void readGlucoseLevel(){
        String[] datapoint = bloodSensor.getMeasurement();
        this.timeStamp = datapoint[0];
        this.currentBloodGlucoseReading = Integer.parseInt(datapoint[1]);

        if (isFirstTimeStamp)
            this.firstTimeStamp = createTimeStamp(timeStamp);
        isFirstTimeStamp = false;
    }

    public void calculateInsulinDose(){

        // TODO: check if measurements are in the safe range

        if (readings.size()<2){
            readings.add(currentBloodGlucoseReading);
        }
        else if (readings.size() == 2){

            if(currentBloodGlucoseReading < readings.getLast()){
                deliveredInsulin = 0;
                derivative = -1;
            }
            else if (currentBloodGlucoseReading == readings.getLast()){
                deliveredInsulin = 0;
                derivative = 0;
            }
            else {

                derivative = 1;

                if((currentBloodGlucoseReading - readings.getLast()) < (readings.getLast() - readings.getFirst())){
                    deliveredInsulin = 0;
                }
                else{
                    deliveredInsulin = Math.round((currentBloodGlucoseReading - readings.getLast())/4.0f);

                    if (deliveredInsulin == 0) {deliveredInsulin = minimumDose;}
                }
            }

            readings.removeFirst();
            readings.add(currentBloodGlucoseReading);
        }
    }

    public void deliverInsulin(){
        boolean insulinDelivered = pump.deliverInsulin();
        if(!insulinDelivered) deviceStatus = "DEV ERROR";
    }

    public void executeDeviceRoutineTest(){

        // TODO: retrieve device components information like: batteryLevel insulin reservoir level etc...

        boolean isSystemWorking =
                alarm.selfTest() &&
                bloodSensor.selfTest() &&
                clock.selfTest() &&
                display.selfTest() &&
                powerSupply.selfTest() &&
                pump.selfTest();

        if (isSystemWorking){
            deviceStatus = "OK";
        }
        else{
            deviceStatus = "DEV ERROR";
        }
    }

    public void sendInformationToViewController(){
        httpRequestHandler.sendDataPoint(timeStamp, currentBloodGlucoseReading,derivative);
        httpRequestHandler.sendDeviceInformation(batteryLevel,insulinReservoir,calculateGraphDuration(timeStamp),deviceStatus,deliveredInsulin);
    }

    public void standByMode() {

        System.out.println("Entering stand by mode ...");
        try{
            Thread.sleep(1000);
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
