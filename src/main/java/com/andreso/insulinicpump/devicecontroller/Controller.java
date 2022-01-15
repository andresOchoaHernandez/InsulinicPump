package com.andreso.insulinicpump.devicecontroller;

public class Controller {

    public void readGlucoseLevel(){
        System.out.println("Reading glucose levels ...");
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
        System.out.println("Updating view ...");
    }

    public void standByMode() {
        System.out.println("Entering stand by mode ...");
        try{
            Thread.sleep(30000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
