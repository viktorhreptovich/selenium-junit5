package com.orangehrmlive.demo.utils;

import com.sun.org.apache.bcel.internal.classfile.Unknown;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

public class WebDriverFactory {

    public static WebDriver getDriver(TestConfiguration configuration) {
        String browser = configuration.getBrowserName();
        WebDriver driver;
        switch (browser) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");
                driver = new ChromeDriver(chromeOptions);
                break;
            case "internet explorer":
                InternetExplorerOptions internetExplorerOptions = new InternetExplorerOptions();
                internetExplorerOptions.introduceFlakinessByIgnoringSecurityDomains();
                driver = new InternetExplorerDriver(internetExplorerOptions);
                driver.manage().window().maximize();
                break;
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                driver = new FirefoxDriver(firefoxOptions);
                driver.manage().window().maximize();
                break;
                default:
                    throw new RuntimeException("Unknown browserName: " + browser + ". Check test.properties[browserName]. Available: chrome, internet explorer, firefox");
        }
        return driver;
    }
}
