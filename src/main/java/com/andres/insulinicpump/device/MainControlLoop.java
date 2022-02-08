package com.andres.insulinicpump.device;
import com.andres.insulinicpump.device.pumpcontroller.Controller;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class MainControlLoop implements CommandLineRunner {

    private final Controller controller;
    private int gramsOfCarbs;
    private boolean waitForRestControllerData = true;

    public MainControlLoop(){
        this.controller = new Controller();
    }

    @Override
    public void run(String...args) throws Exception{
        startDevice();
        while(true){
            try{
                controlLoop();
            }
            catch(Exception e){
                break;
            }

            synchronized (this){
                wait(1000);
            }

        }
        stopDevice();
    }

    public void setWaitForRestControllerData(boolean waitForRestControllerData) {
        this.waitForRestControllerData = waitForRestControllerData;
    }

    public void setGramsOfCarbs(int gramsOfCarbs) {
        this.gramsOfCarbs = gramsOfCarbs;
    }

    public void bolus(){

        synchronized (this){

            while(waitForRestControllerData){}

            controller.bolusInsulinDeliver(gramsOfCarbs);
            gramsOfCarbs=0;
            waitForRestControllerData=true;
            notify();
        }
    }

    private void startDevice(){
        controller.executeDeviceRoutineTest();
        controller.turnOnDisplay();
    }

    private void stopDevice(){
        controller.turnOffDisplay();
        System.exit(0);
    }

    private void controlLoop(){
        controller.readGlucoseLevel();
        controller.calculateInsulinDose();
        controller.deliverInsulin();
        controller.executeDeviceRoutineTest();
        controller.collectHardwareInfo();
        controller.sendInformationToViewController();
        controller.refreshDisplay();
    }
}
