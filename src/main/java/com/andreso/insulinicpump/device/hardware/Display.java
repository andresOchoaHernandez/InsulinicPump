package com.andreso.insulinicpump.device.hardware;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.nio.file.Paths;

public class Display extends DeviceComponent{

    private final WebDriver driver;
    private static final String HOME = "http://localhost:8080";

    public Display(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1600,695");
        System.setProperty("webdriver.chrome.driver", Paths.get("./src/main/resources/chromedriver_win32_96/chromedriver.exe").toString());
        this.driver = new ChromeDriver(options);
    }

    @Override
    public boolean selfTest() {
        return this.isDeviceWorkingProperly;
    }

    public void turnOn(){
        this.driver.get(HOME);
    }

    public void refresh(){ turnOn();}

    public void turnOff(){
        this.driver.close();
    }

}
