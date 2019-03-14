package com.orangehrmlive.demo.utils;

import com.orangehrmlive.demo.annotations.Steps;
import com.orangehrmlive.demo.pages.Page;
import com.orangehrmlive.demo.testdata.JobTitle;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.lang.reflect.Field;


public abstract class PageSteps {

    protected WebDriver driver;

    public PageSteps() {

    }

    public boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void user_is_logged_in() {
        Assert.assertTrue("User isn't logged in", isElementPresent(By.id("welcome")));
    }

}
