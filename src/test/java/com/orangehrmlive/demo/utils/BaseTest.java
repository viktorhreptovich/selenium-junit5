package com.orangehrmlive.demo.utils;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.orangehrmlive.demo.annotations.WithAuthorization;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

public class BaseTest {

    protected static WebDriver driver;
    boolean isUniqueSession = TestConfiguration.getInstance().isUniqueSession();

    public interface PostCondition {
        void apply();
    }

    public PostCondition postCondition;

    @BeforeAll
    static void setupAll() {
        if (driver == null) {
            driver = WebDriverFactory.getDriver(TestConfiguration.getInstance());
        }
    }

    @BeforeEach
    void setUp() throws Throwable {
        if (isUniqueSession) {
            if (driver == null) {
                driver = WebDriverFactory.getDriver(TestConfiguration.getInstance());
            }
        }
        StepsFactory.initPageSteps(this, driver);
        if (this.getClass().isAnnotationPresent(WithAuthorization.class)) {
            authorization();
        }
        postCondition = null;
    }

    @AfterEach
    void tearDown() {
        if (isUniqueSession) {
            driver.quit();
            driver = null;
        } else {
            driver.manage().deleteAllCookies();
            driver.get("data:,");
        }
        if (postCondition != null) {
            postCondition.apply();
        }
    }

    @AfterAll
    static void tearDownAll() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }


    private void authorization() {
        String loginURL = TestConfiguration.getInstance().getBaseURL();

        Response getLogin = RestAssured.get(loginURL);
        String html = getLogin.getBody().asString();
        String phpsessid = getLogin.getCookie("PHPSESSID");
        Document doc = Jsoup.parse(html);
        String csrfToken = doc.getElementById("csrf_token").attr("value");
        TestConfiguration testConfiguration = TestConfiguration.getInstance();

        Response validateCredentials = RestAssured.given()
                .cookie("PHPSESSID", phpsessid)
                .param("_csrf_token", csrfToken)
                .param("txtUsername", testConfiguration.getUserName())
                .param("txtPassword", testConfiguration.getUserPassword())
                .param("Submit", "LOGIN")
                .post(loginURL + "/index.php/auth/validateCredentials");

        phpsessid = validateCredentials.getCookie("PHPSESSID");

        driver.get(loginURL);
        driver.manage().deleteAllCookies();
        driver.manage().addCookie(new Cookie("PHPSESSID", phpsessid));
        driver.manage().addCookie(new Cookie("Loggedin","True"));
    }

}
