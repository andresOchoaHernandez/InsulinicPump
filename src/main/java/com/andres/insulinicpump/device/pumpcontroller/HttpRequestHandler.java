package com.andres.insulinicpump.device.pumpcontroller;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class HttpRequestHandler {

    private static final String BASE = "http://localhost:8080";

    private final String[] dataPointParams = {"timeStamp","glucoseLevel","derivative"};
    private final String[] deviceInformationParams = {"batteryLevel","insulinReservoir","graphDuration","deviceStatus","deliveredInsulin"};
    private final String[] safeBoundsParams = {"safeLowBound","safeHighBound"};

    public void sendDataPoint(String timeStamp, Integer glucoseLevel,int derivative){

        String[] values = {timeStamp,glucoseLevel.toString(),Integer.toString(derivative)};

        boolean error = !buildAndSendHttpPostRequest(BASE + "/insertDataPoint",dataPointParams,values);
        if (error) System.out.println(" <data point> ERROR");
    }

    public void sendDeviceInformation(Integer batteryLevel,Integer insulinReservoir,String graphDuration,String deviceStatus,int deliveredInsulin){

        String[] values = {batteryLevel.toString(),insulinReservoir.toString(),graphDuration,deviceStatus,Integer.toString(deliveredInsulin)};

        boolean error = !buildAndSendHttpPostRequest(BASE + "/insertDeviceData",deviceInformationParams,values);
        if (error) System.out.println(" <DeviceComponent information> ERROR");
    }

    public void sendSafeBounds(int safeLowBound, int safeHighBound){

        String[] values = {Integer.toString(safeLowBound),Integer.toString(safeHighBound)};

        boolean error = !buildAndSendHttpPostRequest(BASE + "/insertBounds",safeBoundsParams,values);
        if (error) System.out.println(" <safe bounds> ERROR");
    }

    private boolean buildAndSendHttpPostRequest(String URL,String[]parameterNames,String[]values){

        if(parameterNames.length != values.length) return false;

        int numberOfParams = parameterNames.length;

        try {
            HttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(URL);

            List<NameValuePair> params = new ArrayList<>(numberOfParams);

            for (int i = 0;i<numberOfParams;i++) {
                params.add(new BasicNameValuePair(parameterNames[i],values[i]));
            }

            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

            HttpResponse response = httpClient.execute(httpPost);

            if(response.getStatusLine().getStatusCode() != 200){
                return false;
            }

        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
