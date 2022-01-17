package com.andreso.insulinicpump.devicecontroller;

public class MainControlLoop implements Runnable{

    private Controller controller;

    public MainControlLoop(){
        this.controller = new Controller();
    }

    @Override
    public void run() {

        int loopIndex = 1 ;

        while(true){
            controller.turnOnDisplay();
            controller.readGlucoseLevel();
            controller.calculateInsulinDose();
            controller.deliverInsulin();
            controller.executeDeviceRoutineTest();
            controller.sendInformationToViewController();
            controller.standByMode();

            if (loopIndex == 5)break;

            loopIndex++;
        }
        controller.turnOffDisplay();
    }
}
