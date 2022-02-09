package com.andres.insulinicpump.unit;

import com.andres.insulinicpump.device.MainControlLoop;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext
public class RestControllersTests {

    @MockBean
    private MainControlLoop mainControlLoop;

    @Test
    public void calculateBolus(){
        String[] params = {"gramsOfCarbs"};
        String[] values = {"60"};
        assertTrue(buildAndSendHttpPostRequest(
                "http://localhost:8080/calculateBolus",
                params,
                values
        ));
    }

    @Test
    public void insertDataPoint(){
        String[] params = {"timeStamp","glucoseLevel","derivative"};
        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Timestamp(System.currentTimeMillis()));
        String[] values = {
                now,
                "120",
                "1"
        };
        assertTrue(buildAndSendHttpPostRequest(
                "http://localhost:8080/insertDataPoint",
                params,
                values
        ));

    }

    @Test
    public void insertDeviceData(){
        String[] params = {
                "batteryLevel",
                "insulinReservoir",
                "graphDuration",
                "deviceStatus",
                "deliveredInsulin"
        };
        String[] values = {
                "50",
                "50",
                "1 h",
                "OK",
                "1"
        };
        assertTrue(buildAndSendHttpPostRequest(
                "http://localhost:8080/insertDeviceData",
                params,
                values
        ));
    }

    @Test
    public void insertBounds(){
        String[] params = {"safeLowBound","safeHighBound"};
        String[] values = {
                "72",
                "126"
        };
        assertTrue(buildAndSendHttpPostRequest(
                "http://localhost:8080/insertBounds",
                params,
                values
        ));
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
