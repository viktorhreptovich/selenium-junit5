package com.orangehrmlive.demo.pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends Page {


    @FindBy(id = "txtUsername")
    private WebElement inputUsername;
    @FindBy(id = "txtPassword")
    private WebElement inputPassword;
    @FindBy(id = "btnLogin")
    private WebElement buttonLogin;
    @FindBy(id = "spanMessage")
    private WebElement labelMessage;

    public LoginPage(WebDriver webDriver) {
        super(webDriver);
    }


    public void setUserName(String username) {
        inputUsername.sendKeys(username);
    }

    public void setPassword(String password) {
        inputPassword.sendKeys(password);
    }

    public void clickLogin() {
        buttonLogin.click();
    }

    public String getMessage() {
        try {
            return labelMessage.getText();
        } catch (NoSuchElementException e) {
            return "";
        }
    }
}
