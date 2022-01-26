package com.andres.insulinicpump.unit;

import com.andreso.insulinicpump.device.deviceutils.ControllerData;
import org.junit.Test;

import static org.junit.Assert.*;

public class ControllerDataTest {

    @Test
    public void instantiationTest(){
        ControllerData cd = new ControllerData();

        assertTrue(cd.isFirstTimeStamp());
        assertEquals(cd.getSafeLowBound(),72);
        assertEquals(cd.getSafeHighBound(),126);
        assertEquals(cd.getTimeStamp(),"");
        assertEquals(cd.getCurrentBloodGlucoseReading(),0);
        assertEquals(cd.getDerivative(),0);
        assertNotNull(cd.getReadings());
        assertEquals(cd.getBatteryLevel(),0);
        assertEquals(cd.getInsulinReservoir(),0);
        assertEquals(cd.getDeviceStatus(),"OK");
        assertEquals(cd.getGraphDuration(),"");
        assertEquals(cd.getDeliveredInsulin(),0);
        assertEquals(cd.getMinimumDose(),1);
    }

    @Test
    public void settingTest(){
        ControllerData cd = new ControllerData();

        cd.setFirstTimeStamp(false);
        assertFalse(cd.isFirstTimeStamp());

        cd.setSafeLowBound(100);
        assertEquals(cd.getSafeLowBound(),100);

        cd.setSafeHighBound(200);
        assertEquals(cd.getSafeHighBound(),200);

        cd.setTimeStamp("2021-12-11 12:13:14");
        assertEquals(cd.getTimeStamp(),"2021-12-11 12:13:14");

        cd.setCurrentBloodGlucoseReading(123);
        assertEquals(cd.getCurrentBloodGlucoseReading(),123);

        cd.setDerivative(-1);
        assertEquals(cd.getDerivative(),-1);

        cd.setBatteryLevel(100);
        assertEquals(cd.getBatteryLevel(),100);

        cd.setInsulinReservoir(100);
        assertEquals(cd.getInsulinReservoir(),100);

        cd.setDeviceStatus("DEV ERROR");
        assertEquals(cd.getDeviceStatus(),"DEV ERROR");

        cd.setGraphDuration("5 h");
        assertEquals(cd.getGraphDuration(),"5 h");

        cd.setDeliveredInsulin(5);
        assertEquals(cd.getDeliveredInsulin(),5);

        cd.setMinimumDose(5);
        assertEquals(cd.getMinimumDose(),5);
    }

}
