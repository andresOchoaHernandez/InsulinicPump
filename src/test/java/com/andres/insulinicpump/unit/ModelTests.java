package com.andres.insulinicpump.unit;

import com.andres.insulinicpump.view.model.BGBounds;
import com.andres.insulinicpump.view.model.DataPoint;
import com.andres.insulinicpump.view.model.DeviceData;
import org.junit.Test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import static org.junit.Assert.*;

public class ModelTests {
    @Test
    public void bgBoundsTest(){
        BGBounds bb = new BGBounds(72,126);
        assertEquals(1l,(long)bb.getId());
        assertEquals(72,bb.getSafeLowBound());
        assertEquals(126,bb.getSafeHighBound());

        bb.setId(5l);
        bb.setSafeLowBound(90);
        bb.setSafeHighBound(200);

        assertEquals(5l,(long)bb.getId());
        assertEquals(90,bb.getSafeLowBound());
        assertEquals(200,bb.getSafeHighBound());
    }

    @Test
    public void dataPoint(){
        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Timestamp(System.currentTimeMillis()));

        DataPoint dp = new DataPoint(
                now,
                123,
                0
        );

        assertEquals(now,dp.getTimeStamp());
        assertEquals(123,(int)dp.getGlucoseLevel());

        String after=
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Timestamp(System.currentTimeMillis()));

        dp.setTimeStamp(after);
        dp.setGlucoseLevel(200);
        dp.setDerivative(-1);

        assertEquals(after,dp.getTimeStamp());
        assertEquals(200,(int)dp.getGlucoseLevel());
        assertEquals(-1,dp.getDerivative());
    }

    @Test
    public void deviceData(){
        DeviceData dd = new DeviceData(
                50,
                50,
                "1 h",
                "DEV ERROR",
                5
        );

        assertEquals(50,(int)dd.getBatteryLevel());
        assertEquals(50,(int)dd.getInsulinReservoir());
        assertEquals("1 h",dd.getGraphDuration());
        assertEquals("DEV ERROR",dd.getDeviceStatus());
        assertEquals(5,(int)dd.getDeliveredInsulin());

        dd.setId(3l);
        dd.setBatteryLevel(20);
        dd.setInsulinReservoir(20);
        dd.setGraphDuration("0 h");
        dd.setDeviceStatus("OK");
        dd.setDeliveredInsulin(0);

        assertEquals(3l,(long)dd.getId());
        assertEquals(20,(int)dd.getBatteryLevel());
        assertEquals(20,(int)dd.getInsulinReservoir());
        assertEquals("0 h",dd.getGraphDuration());
        assertEquals("OK",dd.getDeviceStatus());
        assertEquals(0,(int)dd.getDeliveredInsulin());
    }
}
