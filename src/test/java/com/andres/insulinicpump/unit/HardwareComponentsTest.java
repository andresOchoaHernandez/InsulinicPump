package com.andres.insulinicpump.unit;

import com.andres.insulinicpump.device.hardware.*;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class HardwareComponentsTest {

    @Test
    public void deviceComponent(){
        DeviceComponent dc = new DeviceComponent() {
            @Override
            public boolean selfTest() {
                return this.isDeviceWorkingProperly;
            }
        };
        assertTrue(dc.getDeviceStateVariable());
        dc.setDeviceStateVariable(false);
        assertFalse(dc.getDeviceStateVariable());
    }

    @Test
    public void alarm(){
        Alarm a = new Alarm();
        assertTrue(a.selfTest());
    }

    @Test
    public void bloodSensor(){
        BloodSensor b = new BloodSensor();
        assertTrue(b.selfTest());

        try {
            LinkedList<Integer> mockedData = readMockedData();
            for(Integer dataPoint: mockedData){
                if(dataPoint != b.getMeasurement()){
                    assertTrue(false);
                }
            }
            assertTrue(true);
        }
        catch (Exception e ){
            e.printStackTrace();
            assertTrue(false);
        }

    }

    private LinkedList<Integer> readMockedData() throws Exception{

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

    @Test
    public void clock(){
        Clock c = new Clock();
        assertTrue(c.selfTest());

        /* first time stamp */
        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Timestamp(System.currentTimeMillis()));
        assertEquals(now,c.getCurrentTimeStamp());

        /* five minutes into the future */
        String nowPlusFiveMinutes = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Timestamp(System.currentTimeMillis()+300000));
        assertEquals(nowPlusFiveMinutes,c.getCurrentTimeStamp());
    }

    @Test
    public void powerSupply(){
        PowerSupply ps = new PowerSupply();
        assertTrue(ps.selfTest());

        assertEquals(100,ps.getBatteryLevel());

        ps.getBatteryLevel();
        ps.getBatteryLevel();
        ps.getBatteryLevel();
        ps.getBatteryLevel();
        ps.getBatteryLevel();

        assertEquals(99,ps.getBatteryLevel());
    }

    @Test
    public void pump(){
        Pump p = new Pump();
        assertTrue(p.selfTest());
        assertEquals(100,p.getInsulinReservoirLevel());
        assertTrue(p.deliverInsulin(20));
        assertEquals(100*(300-20)/300,p.getInsulinReservoirLevel());
    }

    @Test
    public void display(){
        Display d = new Display();
        assertTrue(d.selfTest());
        d.turnOff();
    }
}
