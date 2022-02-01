package com.andreso.insulinicpump.device;
import com.andreso.insulinicpump.device.pumpcontroller.BolusRestController;
import com.andreso.insulinicpump.device.pumpcontroller.Controller;
import com.andreso.insulinicpump.device.pumpcontroller.lock;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class MainControlLoop implements CommandLineRunner {

    private final Controller controller;
    private int gramsOfCarbs;
    private boolean waitForRestControllerData = true;

    public void setWaitForRestControllerData(boolean waitForRestControllerData) {
        this.waitForRestControllerData = waitForRestControllerData;
    }

    public void setGramsOfCarbs(int gramsOfCarbs) {
        this.gramsOfCarbs = gramsOfCarbs;
    }

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
                //read all BloodSensor mock data
                break;
            }

            synchronized (this){
                // StandBy mode
                System.out.println("[MCL] StandBy mode...");
                wait(5000);
            }

        }
        stopDevice();
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
        controller.sendInformationToViewController();
        controller.refreshDisplay();
    }

    public void bolus(){
        synchronized (this){
            System.out.println("[BOLUS] acquired the lock...");

            System.out.println("[BOLUS] waiting for BolusRestController data");
            while(waitForRestControllerData){}
            System.out.println("[BOLUS] BolusRestController gave me the data!");

            controller.bolusInsulinDeliver(gramsOfCarbs);
            System.out.println("[BOLUS]delivered " + gramsOfCarbs + " grams of cb");

            gramsOfCarbs=0;
            waitForRestControllerData=true;

            notify();
        }
    }
}
