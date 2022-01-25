package com.andreso.insulinicpump.device.pumpcontroller;

import com.andreso.insulinicpump.device.deviceutils.ControllerData;
import com.andreso.insulinicpump.device.deviceutils.HttpRequestHandler;
import com.andreso.insulinicpump.device.hardware.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class Controller {

    private final ControllerData controllerData;

    private final Display display;
    private final BloodSensor bloodSensor;
    private final Clock clock;
    private final PowerSupply powerSupply;
    private final Alarm alarm;
    private final Pump pump;

    private final HttpRequestHandler httpRequestHandler;

    public Controller(){
        controllerData = new ControllerData();

        display = new Display();
        bloodSensor = new BloodSensor();
        clock = new Clock();
        powerSupply = new PowerSupply();
        alarm = new Alarm();
        pump = new Pump();

        httpRequestHandler = new HttpRequestHandler();
    }

    public void turnOnDisplay(){
        this.display.turnOn();
    }

    public void turnOffDisplay(){
        this.display.turnOff();
    }

    public void refreshDisplay(){this.display.refresh();}

    public void readGlucoseLevel(){
        controllerData.setTimeStamp(clock.getCurrentTimeStamp());
        controllerData.setCurrentBloodGlucoseReading(bloodSensor.getMeasurement());

        if (controllerData.isFirstTimeStamp())
            controllerData.setFirstTimeStamp(createTimeStamp(controllerData.getTimeStamp()));
        controllerData.setFirstTimeStamp(false);
    }

    public void calculateInsulinDose(){

        // TODO: check if measurements are in the safe range

        LinkedList<Integer> readings = controllerData.getReadings();
        int readingsSize = readings.size();
        int currentBloodGlucoseReading = controllerData.getCurrentBloodGlucoseReading();


        if (readingsSize<2){
            readings.add(currentBloodGlucoseReading);
        }
        else if (readingsSize == 2){

            if(currentBloodGlucoseReading < readings.getLast()){
                controllerData.setDeliveredInsulin(0);
                controllerData.setDerivative(-1);
            }
            else if (currentBloodGlucoseReading == readings.getLast()){
                controllerData.setDeliveredInsulin(0);
                controllerData.setDerivative(0);
            }
            else {

                controllerData.setDerivative(1);

                if((currentBloodGlucoseReading  - readings.getLast()) < (readings.getLast() - readings.getFirst())){
                    controllerData.setDeliveredInsulin(0);
                }
                else{
                    controllerData.setDeliveredInsulin(Math.round((currentBloodGlucoseReading - readings.getLast())/4.0f));

                    if (controllerData.getDeliveredInsulin() == 0) {controllerData.setDeliveredInsulin(controllerData.getMinimumDose());}
                }
            }

            readings.removeFirst();
            readings.add(currentBloodGlucoseReading);
        }
    }

    public void deliverInsulin(){
        boolean insulinDelivered = pump.deliverInsulin();
        if(!insulinDelivered) controllerData.setDeviceStatus("DEV ERROR");
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
            controllerData.setDeviceStatus("OK");
        }
        else{
            controllerData.setDeviceStatus("DEV ERROR");
        }
    }

    public void sendInformationToViewController(){
        controllerData.setGraphDuration(calculateGraphDuration(controllerData.getTimeStamp()));

        httpRequestHandler.sendSafeBounds(controllerData.getSafeLowBound(),
                                          controllerData.getSafeHighBound());

        httpRequestHandler.sendDataPoint(controllerData.getTimeStamp(),
                                         controllerData.getCurrentBloodGlucoseReading(),
                                         controllerData.getDerivative());

        httpRequestHandler.sendDeviceInformation(controllerData.getBatteryLevel(),
                                                 controllerData.getInsulinReservoir(),
                                                 controllerData.getGraphDuration(),
                                                 controllerData.getDeviceStatus(),
                                                 controllerData.getDeliveredInsulin());
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

        long difference = currentTimeStamp.getTime() - controllerData.getFirstTimeStamp().getTime();
        int hours = (int) TimeUnit.MILLISECONDS.toHours(difference);

        return hours + " h";
    }
}
