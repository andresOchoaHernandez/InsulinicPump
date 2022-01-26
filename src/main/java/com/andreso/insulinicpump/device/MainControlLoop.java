package com.andreso.insulinicpump.device;
import com.andreso.insulinicpump.device.pumpcontroller.Controller;

public class MainControlLoop implements Runnable{

    private final Controller controller;

    public MainControlLoop(){
        this.controller = new Controller();
    }

    @Override
    public void run() {

        controller.executeDeviceRoutineTest();
        controller.turnOnDisplay();

        while(true){

            try{
                controller.readGlucoseLevel();
            }
            catch(Exception e ){
                // read all CGM mock data from blood sensor
                break;
            }

            controller.calculateInsulinDose();
            controller.deliverInsulin();
            controller.executeDeviceRoutineTest();
            controller.sendInformationToViewController();
            controller.refreshDisplay();
            controller.standByMode();
        }
        controller.turnOffDisplay();
    }
}
