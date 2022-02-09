package com.andres.insulinicpump.unit;

import com.andres.insulinicpump.device.MainControlLoop;
import com.andres.insulinicpump.unit.utils.TestHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

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
        assertTrue(TestHelper.buildAndSendHttpPostRequest(
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
        assertTrue(TestHelper.buildAndSendHttpPostRequest(
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
        assertTrue(TestHelper.buildAndSendHttpPostRequest(
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
        assertTrue(TestHelper.buildAndSendHttpPostRequest(
                "http://localhost:8080/insertBounds",
                params,
                values
        ));
    }
}
