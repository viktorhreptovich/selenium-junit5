package com.orangehrmlive.demo.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestConfiguration {

    private Properties properties;
    private final String browserName;
    private final String uniqueSession;
    private final String baseURL;
    private final String userName;
    private final String userPassword;

    private static TestConfiguration instance = new TestConfiguration();

    public static TestConfiguration getInstance() {
        return instance;
    }

    private TestConfiguration() {
        properties = new Properties();
        try {
            properties.load(new FileInputStream("test.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        browserName = properties.getProperty("browserName", "chrome");
        uniqueSession = properties.getProperty("uniqueSession", "true");
        baseURL = properties.getProperty("baseURL", "");
        userName = properties.getProperty("userName", "");
        userPassword = properties.getProperty("userPassword", "");
    }

    public String getBrowserName() {
        return browserName;
    }

    public boolean isUniqueSession() {
        return Boolean.valueOf(uniqueSession).booleanValue();
    }

    public String getBaseURL() {
        return baseURL;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

}
