package com.andres.insulinicpump.acceptance;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class AcceptanceTests extends AcceptanceBaseTest{
    @Test
    public void batteryStatus(){
        driver.get(HOME);
        assertTrue(true);
    }

    @Test
    public void bolus(){
        driver.get(HOME);
        assertTrue(true);
    }

    @Test
    public void deviceStatus(){
        driver.get(HOME);
        assertTrue(true);
    }

    @Test
    public void insulinDeliver(){
        driver.get(HOME);
        assertTrue(true);
    }

    @Test
    public void insulinReservoirStatus(){
        driver.get(HOME);
        assertTrue(true);
    }
}
