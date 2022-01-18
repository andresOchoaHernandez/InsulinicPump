package com.andreso.insulinicpump.model;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.nio.file.Paths;

public class DeviceDisplay {

    private WebDriver driver;
    private static String HOME = "http://localhost:8080";

    public DeviceDisplay(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1600,615");
        System.setProperty("webdriver.chrome.driver", Paths.get("./src/main/resources/chromedriver_win32_96/chromedriver.exe").toString());
        this.driver = new ChromeDriver(options);
    }

    public void turnOn(){
        this.driver.get(HOME);
    }

    public void turnOff(){
        this.driver.close();
    }

}
