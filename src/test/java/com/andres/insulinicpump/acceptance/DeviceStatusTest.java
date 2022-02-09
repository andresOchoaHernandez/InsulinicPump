package com.andres.insulinicpump.acceptance;

import com.andres.insulinicpump.device.MainControlLoop;
import com.andres.insulinicpump.device.pumpcontroller.Controller;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@DirtiesContext
public class DeviceStatusTest extends AcceptanceBaseTest{

    /* In testing environment the main control loop has to be simulated for
     * the reasons I explained in readme.
     */
    @MockBean
    private MainControlLoop mainControlLoop;

    @Test
    public void deviceStatus(){

        controller = new Controller();
        controller.turnOffDisplay();
        conData = controller.getControllerData();

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
}
