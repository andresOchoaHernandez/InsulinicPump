package com.andres.insulinicpump.device.pumpcontroller;

import com.andres.insulinicpump.device.hardware.*;

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

    public ControllerData getControllerData(){
        /* for testing purposes only */
        return this.controllerData;
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

        if (controllerData.isFirstTimeStamp())
            controllerData.setFirstTimeStamp(createTimeStamp(controllerData.getTimeStamp()));
        controllerData.setFirstTimeStamp(false);

        controllerData.setCurrentBloodGlucoseReading(bloodSensor.getMeasurement());
    }

    public void calculateInsulinDose(){

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

                if (controllerData.getSafeLowBound() <= currentBloodGlucoseReading && currentBloodGlucoseReading <= controllerData.getSafeHighBound()){
                    controllerData.setDeliveredInsulin(0);
                    controllerData.setDerivative(0);
                }
                else if(controllerData.getSafeHighBound()<=currentBloodGlucoseReading ){
                    controllerData.setDeliveredInsulin(controllerData.getMinimumDose());
                    controllerData.setDerivative(0);
                }
                else{
                    controllerData.setDeliveredInsulin(0);
                    controllerData.setDerivative(0);
                }
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
        boolean insulinDelivered = pump.deliverInsulin(controllerData.getDeliveredInsulin());
        if(!insulinDelivered) controllerData.setDeviceStatus("DEV ERROR");
    }

    public void bolusInsulinDeliver(int gramsOfCarbs){
        int CHO = gramsOfCarbs / controllerData.getGramsOfCarbsDisposedByOneGramOfInsulin();
        int highBloodCorrectionDose = Math.abs(controllerData.getCurrentBloodGlucoseReading() - controllerData.getSafeHighBound())/ controllerData.getCorrectionFactor();

        int bolusDose = CHO + highBloodCorrectionDose;

        boolean insulinDelivered = pump.deliverInsulin(bolusDose);
        if(!insulinDelivered) controllerData.setDeviceStatus("DEV ERROR");
    }

    public void executeDeviceRoutineTest(){

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

    public void collectHardwareInfo(){
        controllerData.setBatteryLevel(powerSupply.getBatteryLevel());
        controllerData.setGraphDuration(calculateGraphDuration(controllerData.getTimeStamp()));
        controllerData.setInsulinReservoir(pump.getInsulinReservoirLevel());
    }

    public void sendInformationToViewController(){

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
