package com.andres.insulinicpump.acceptance;

import com.andres.insulinicpump.device.MainControlLoop;
import com.andres.insulinicpump.device.pumpcontroller.Controller;
import com.andres.insulinicpump.device.pumpcontroller.ControllerData;
import org.apache.commons.lang3.SystemUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.Paths;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public abstract class AcceptanceBaseTest {
    protected WebDriver driver = null;
    protected static final String CHART = "http://localhost:8080";

    protected Controller controller;
    protected ControllerData conData;

    @Before
    public void setUp(){
        setUpChrome();
    }

    private void setUpChrome(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1600,695");
        options.addArguments("--headless");
        if(SystemUtils.IS_OS_WINDOWS){
            System.setProperty("webdriver.chrome.driver", Paths.get("./src/main/resources/chromedriver_win32_96/chromedriver.exe").toString());
        }
        else if (SystemUtils.IS_OS_MAC){
            System.setProperty("webdriver.chrome.driver", Paths.get("./src/main/resources/chromedriver_mac64_96/chromedriver").toString());
        }
        else if (SystemUtils.IS_OS_LINUX){
            System.setProperty("webdriver.chrome.driver", Paths.get("./src/main/resources/chromedriver_linux64_96/chromedriver.exe").toString());
        }

        System.setProperty("webdriver.chrome.silentOutput", "true");

        if(this.driver == null)
            this.driver = new ChromeDriver(options);
    }

    @After
    public void shutDown(){
        if(this.driver != null){
            this.driver.quit();
        }
    }
}
