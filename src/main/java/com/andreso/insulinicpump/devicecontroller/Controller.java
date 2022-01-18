package com.andreso.insulinicpump.devicecontroller;

import com.andreso.insulinicpump.model.DeviceDisplay;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    private int datapoint = 0;
    private DeviceDisplay display = new DeviceDisplay();

    public void turnOnDisplay(){
        this.display.turnOn();
    }

    public void turnOffDisplay(){
        this.display.turnOff();
    }

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

        System.out.println("Sending information to server ...");

        try {
            HttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("http://localhost:8080/insertDataPoint");

            List<NameValuePair> params = new ArrayList<>(2);
            params.add(new BasicNameValuePair("timeStamp", Integer.toString(datapoint)));
            params.add(new BasicNameValuePair("glucoseLevel", Integer.toString(datapoint)));
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

            HttpResponse response = httpClient.execute(httpPost);

            if(response.getStatusLine().getStatusCode() != 200){
                System.out.println("<CONTOLLER> PROBLEMS DURING REQUEST SENDING!");
            }

            datapoint++;

        }
        catch(Exception e){
            e.printStackTrace();
        }
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
}
