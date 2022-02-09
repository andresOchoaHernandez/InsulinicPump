package com.andres.insulinicpump.unit.utils;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TestHelper {

    public static boolean buildAndSendHttpPostRequest(String URL,String[]parameterNames,String[]values){

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

    public static LinkedList<Integer> readMockedData() throws Exception{

        LinkedList<Integer> data = new LinkedList<>();

        BufferedReader br = new BufferedReader(new FileReader("src\\main\\resources\\CGMData\\data.txt"));
        try{
            String line = br.readLine();
            while (line != null) {
                data.add(Integer.parseInt(line));
                line = br.readLine();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }

        return data;
    }
}
