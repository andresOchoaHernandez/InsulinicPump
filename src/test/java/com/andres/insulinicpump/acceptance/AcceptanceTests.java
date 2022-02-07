package com.andres.insulinicpump.acceptance;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class AcceptanceTests extends AcceptanceBaseTest{

    @Test
    public void batteryStatus(){
        conData.setBatteryLevel(50);
        conData.setInsulinReservoir(50);
        conData.setGraphDuration("1");
        conData.setDeviceStatus("OK");
        conData.setDeliveredInsulin(0);

        controller.sendInformationToViewController();

        driver.get(CHART);
        WebElement batteryLevel = driver.findElement(By.id("bData"));
        assertEquals("50%",batteryLevel.getText());

        conData.setBatteryLevel(19);
        controller.sendInformationToViewController();
        driver.get(CHART);
        try {
            driver.findElement(By.xpath("//p[contains(text(),'battery is low')]"));
            assertTrue(true);
        }
        catch(NoSuchElementException e){
            assertTrue(false);
        }

    }

    @Test
    public void insulinReservoirStatus(){
        conData.setBatteryLevel(50);
        conData.setInsulinReservoir(50);
        conData.setGraphDuration("1 h");
        conData.setDeviceStatus("OK");
        conData.setDeliveredInsulin(0);
        controller.sendInformationToViewController();

        driver.get(CHART);
        WebElement insulinReservoirLevel = driver.findElement(By.id("iData"));
        assertEquals("50%",insulinReservoirLevel.getText());

        conData.setInsulinReservoir(19);
        controller.sendInformationToViewController();

        driver.get(CHART);
        try {
            driver.findElement(By.xpath("//p[contains(text(),'insulin reservoir is low')]"));
            assertTrue(true);
        }
        catch(NoSuchElementException e){
            assertTrue(false);
        }
    }

    @Test
    public void deviceStatus(){
        conData.setBatteryLevel(50);
        conData.setInsulinReservoir(50);
        conData.setGraphDuration("1 h");
        conData.setDeviceStatus("OK");
        conData.setDeliveredInsulin(0);
        controller.sendInformationToViewController();

        driver.get(CHART);
        WebElement deviceStatus = driver.findElement(By.id("dData"));
        assertEquals("OK",deviceStatus.getText());

        conData.setDeviceStatus("DEV ERROR");
        controller.sendInformationToViewController();
        driver.get(CHART);
        try {
            driver.findElement(By.xpath("//p[contains(text(),'device is not working properly')]"));
            assertTrue(true);
        }
        catch(NoSuchElementException e){
            assertTrue(false);
        }
    }

    @Test
    public void insulinDeliver(){
        /* r2 < r1 */
        conData.setCurrentBloodGlucoseReading(130);
        controller.calculateInsulinDose();
        controller.sendInformationToViewController();
        conData.setCurrentBloodGlucoseReading(126);
        controller.calculateInsulinDose();
        controller.sendInformationToViewController();
        conData.setCurrentBloodGlucoseReading(125);
        controller.calculateInsulinDose();
        controller.sendInformationToViewController();
        driver.get(CHART);
        assertEquals("0 units",driver.findElement(By.id("diData")).getText());

        /* r2 = r1 && r2 > 126 mg/dL */
        conData.setCurrentBloodGlucoseReading(130);
        controller.calculateInsulinDose();
        controller.sendInformationToViewController();
        conData.setCurrentBloodGlucoseReading(130);
        controller.calculateInsulinDose();
        controller.sendInformationToViewController();
        driver.get(CHART);
        assertEquals(conData.getMinimumDose() + " units",driver.findElement(By.id("diData")).getText());

        /* r2 = r1 && r2 <= 126 mg/dL */
        conData.setCurrentBloodGlucoseReading(120);
        controller.calculateInsulinDose();
        controller.sendInformationToViewController();
        conData.setCurrentBloodGlucoseReading(120);
        controller.calculateInsulinDose();
        controller.sendInformationToViewController();
        driver.get(CHART);
        assertEquals("0 units",driver.findElement(By.id("diData")).getText());

        /* r2 > r1 && rate falling */
        conData.setCurrentBloodGlucoseReading(130);
        controller.calculateInsulinDose();
        controller.sendInformationToViewController();
        conData.setCurrentBloodGlucoseReading(135);
        controller.calculateInsulinDose();
        controller.sendInformationToViewController();
        conData.setCurrentBloodGlucoseReading(136);
        controller.calculateInsulinDose();
        controller.sendInformationToViewController();
        driver.get(CHART);
        assertEquals("0 units",driver.findElement(By.id("diData")).getText());

        /* r2 > r1 && rate rising approx != 0 */
        conData.setCurrentBloodGlucoseReading(130);
        controller.calculateInsulinDose();
        controller.sendInformationToViewController();
        conData.setCurrentBloodGlucoseReading(135);
        controller.calculateInsulinDose();
        controller.sendInformationToViewController();
        conData.setCurrentBloodGlucoseReading(141);
        controller.calculateInsulinDose();
        controller.sendInformationToViewController();
        driver.get(CHART);
        assertEquals(Math.round((141 - 135)/4.0f) + " units",driver.findElement(By.id("diData")).getText());

        /* r2 > r1 && rate rising approx == 0 */
        conData.setCurrentBloodGlucoseReading(130);
        controller.calculateInsulinDose();
        controller.sendInformationToViewController();
        conData.setCurrentBloodGlucoseReading(131);
        controller.calculateInsulinDose();
        controller.sendInformationToViewController();
        conData.setCurrentBloodGlucoseReading(133);
        controller.calculateInsulinDose();
        controller.sendInformationToViewController();
        driver.get(CHART);
        assertEquals(conData.getMinimumDose() + " units",driver.findElement(By.id("diData")).getText());
    }

    private void sleep(){
        try{
            Thread.sleep(10000);
        }
        catch(Exception e){

        }
    }

    @Test
    public void bolus(){
        conData.setCurrentBloodGlucoseReading(120);
        controller.sendInformationToViewController();
        driver.get(CHART);
        driver.findElement(By.xpath("//button[contains(text(),'BOLUS')]")).click();
        driver.findElement(By.xpath("//input[@value='7']")).click();
        driver.findElement(By.xpath("//input[@value='0']")).click();
        driver.findElement(By.xpath("//input[@value='\uD83D\uDDF8']")).click();
        try{
            driver.findElement(By.xpath("//p[contains(text(),'bolus dose before the meal')]"));
            assertTrue(true);
        }
        catch(NoSuchElementException e){
            assertTrue(false);
        }

        //driver.findElement(By.xpath("//button[contains(text(),'DELIVER')]")).click();
    }
}
