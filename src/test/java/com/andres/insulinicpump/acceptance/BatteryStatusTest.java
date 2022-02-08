package com.andres.insulinicpump.acceptance;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.test.annotation.DirtiesContext;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@DirtiesContext
public class BatteryStatusTest extends AcceptanceBaseTest{
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
}
