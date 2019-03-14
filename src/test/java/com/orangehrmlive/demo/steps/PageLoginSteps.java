package com.orangehrmlive.demo.steps;

import com.orangehrmlive.demo.pages.LoginPage;
import com.orangehrmlive.demo.utils.PageSteps;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PageLoginSteps extends PageSteps {
    private LoginPage loginPage;

    public void open(){
        loginPage.open();
    }

    public void login(String username, String password) {
        loginPage.setUserName(username);
        loginPage.setPassword(password);
        loginPage.clickLogin();
    }

    public void validation_message_is_showed(String message) {
        assertEquals(message,loginPage.getMessage());
    }
}
