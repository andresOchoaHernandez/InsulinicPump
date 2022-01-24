package com.andreso.insulinicpump.device;

import com.andreso.insulinicpump.device.pumpcontroller.Controller;

public class MainControlLoop implements Runnable{

    private final Controller controller;

    public MainControlLoop(){
        this.controller = new Controller();
    }

    @Override
    public void run() {
        int loopIndex = 1 ;

        controller.turnOnDisplay();

        while(true){
            controller.readGlucoseLevel();
            controller.calculateInsulinDose();
            controller.deliverInsulin();
            controller.executeDeviceRoutineTest();
            controller.sendInformationToViewController();
            controller.refreshDisplay();
            controller.standByMode();

            if (loopIndex == 150)break;

            loopIndex++;
        }

        controller.turnOffDisplay();
    }
}
