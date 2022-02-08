package com.andres.insulinicpump.unit;

import com.andres.insulinicpump.device.pumpcontroller.Controller;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class ControllerTest {
    @Test
    public void instantiationTest(){
        Controller c = new Controller();
        assertNotNull(c);
        c.turnOffDisplay();
    }

    @Test
    public void readGlucoseLevelTest(){
        Controller c = new Controller();
        c.turnOffDisplay();

        c.readGlucoseLevel();
        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Timestamp(System.currentTimeMillis()));
        assertEquals(now,c.getControllerData().getTimeStamp());
        assertEquals(now,
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getControllerData().getFirstTimeStamp()));
        assertFalse(c.getControllerData().isFirstTimeStamp());
        try{
            long firstRead = readMockedData().removeFirst();
            assertEquals(firstRead,c.getControllerData().getCurrentBloodGlucoseReading());
        }
        catch(Exception e){
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
    public void executeDeviceRoutineTest_Test(){
        Controller c = new Controller();
        c.turnOffDisplay();

        c.executeDeviceRoutineTest();
        assertEquals("OK",c.getControllerData().getDeviceStatus());
    }
}
