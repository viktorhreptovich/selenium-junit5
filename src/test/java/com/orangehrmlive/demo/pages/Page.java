package com.orangehrmlive.demo.pages;

import com.orangehrmlive.demo.annotations.RelativeUrl;
import com.orangehrmlive.demo.utils.TestConfiguration;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class Page {

    protected WebDriver driver;
    protected JavascriptExecutor jse;

    public Page(WebDriver driver) {
        this.driver = driver;
        this.jse = (JavascriptExecutor) driver;
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public void open() {
        driver.get(getURL());
    }

    public String getURL() {
        String baseURL = TestConfiguration.getInstance().getBaseURL();
        String relativeURL = "";
        if (this.getClass().getAnnotation(RelativeUrl.class) != null) {
            relativeURL = this.getClass().getAnnotation(RelativeUrl.class).value();
        }
        String URL = baseURL + relativeURL;
        return URL;
    }

    public boolean isElementPresent(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
