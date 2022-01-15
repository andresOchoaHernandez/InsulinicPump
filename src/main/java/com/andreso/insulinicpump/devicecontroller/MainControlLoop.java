package com.andreso.insulinicpump.devicecontroller;

public class MainControlLoop implements Runnable{

    private Controller controller;

    public MainControlLoop(){
        this.controller = new Controller();
    }

    @Override
    public void run() {

        while(true){
            controller.readGlucoseLevel();
            controller.calculateInsulinDose();
            controller.deliverInsulin();
            controller.executeDeviceRoutineTest();
            controller.sendInformationToViewController();
            controller.standByMode();
        }
    }
}
