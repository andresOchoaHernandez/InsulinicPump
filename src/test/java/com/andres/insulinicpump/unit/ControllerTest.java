package com.andres.insulinicpump.unit;

import com.andres.insulinicpump.device.pumpcontroller.Controller;
import com.andres.insulinicpump.unit.utils.TestHelper;
import org.junit.Test;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

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
            long firstRead = TestHelper.readMockedData().removeFirst();
            assertEquals(firstRead,c.getControllerData().getCurrentBloodGlucoseReading());
        }
        catch(Exception e){
            e.printStackTrace();
            assertTrue(false);
        }
    }

    @Test
    public void executeDeviceRoutineTest_Test(){
        Controller c = new Controller();
        c.turnOffDisplay();

        c.executeDeviceRoutineTest();
        assertEquals("OK",c.getControllerData().getDeviceStatus());
    }
}
