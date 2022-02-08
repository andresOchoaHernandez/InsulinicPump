package com.andres.insulinicpump.acceptance;

import org.junit.Test;
import org.openqa.selenium.By;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.Assert.assertEquals;

@DirtiesContext
public class InsulinDeliverTest extends AcceptanceBaseTest{
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
}
