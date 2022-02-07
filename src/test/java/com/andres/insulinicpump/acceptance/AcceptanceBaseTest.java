package com.andres.insulinicpump.acceptance;

import com.andres.insulinicpump.device.MainControlLoop;
import org.apache.commons.lang3.SystemUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.Paths;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public abstract class AcceptanceBaseTest {

    /* In testing environment the main control loop has to be simulated */
    @MockBean
    private MainControlLoop mainControlLoop;


    protected WebDriver driver = null;
    protected static final String HOME = "http://localhost:8080";

    @BeforeAll
    public void setUp(){
        setUpChrome();
    }

    private void setUpChrome(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1600,695");
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

    @AfterAll
    public void shutDown(){
        if(this.driver != null){
            this.driver.close();
            this.driver.quit();
        }
    }
}
