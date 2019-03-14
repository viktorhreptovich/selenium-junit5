package com.orangehrmlive.demo.steps;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import com.orangehrmlive.demo.testdata.JobTitle;
import com.orangehrmlive.demo.utils.TestConfiguration;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class PrePostConditionsSteps {

    public void create_job_title(JobTitle jobTitle) {
        String baseURL = TestConfiguration.getInstance().getBaseURL();

        Response getLogin = RestAssured.get(baseURL);
        String html = getLogin.getBody().asString();
        String phpsessid = getLogin.getCookie("PHPSESSID");
        Document doc = Jsoup.parse(html);
        String csrfToken = doc.getElementById("csrf_token").attr("value");


        Response validateCredentials = RestAssured.given()
                .cookie("PHPSESSID", phpsessid)
                .param("_csrf_token", csrfToken)
                .param("txtUsername", "Admin")
                .param("txtPassword", "admin123")
                .param("Submit", "LOGIN")
                .post(baseURL + "/auth/validateCredentials");
        phpsessid = validateCredentials.getCookie("PHPSESSID");


        Response getSaveJobTitle = RestAssured.given()
                .cookie("PHPSESSID", phpsessid)
                .get(baseURL + "/index.php/admin/saveJobTitle");

        html = getSaveJobTitle.getBody().asString();
        doc = Jsoup.parse(html);
        csrfToken = doc.getElementById("jobTitle__csrf_token").attr("value");


        RequestSpecification saveJobTitle = RestAssured.given()
                .cookie("PHPSESSID", phpsessid)
                .param("jobTitle[_csrf_token]", csrfToken)
                .param("jobTitle[jobTitle]", jobTitle.getTitle())
                .param("jobTitle[jobDescription]", jobTitle.getDescription())
                .param("jobTitle[note]", jobTitle.getNote());
        if (!jobTitle.getSpecification().isEmpty() && Files.exists(Paths.get(jobTitle.getSpecification()))) {
            saveJobTitle.multiPart("jobTitle[jobSpec]", new File(jobTitle.getSpecification()));
        }
        saveJobTitle.post(baseURL + "/index.php/admin/saveJobTitle/jobTitleId/");
    }

    public void create_job_title(List<JobTitle> jobTitles) {
        String baseURL = TestConfiguration.getInstance().getBaseURL();

        Response getLogin = RestAssured.get(baseURL);
        String html = getLogin.getBody().asString();
        String phpsessid = getLogin.getCookie("PHPSESSID");
        Document doc = Jsoup.parse(html);
        String csrfToken = doc.getElementById("csrf_token").attr("value");


        Response validateCredentials = RestAssured.given()
                .cookie("PHPSESSID", phpsessid)
                .param("_csrf_token", csrfToken)
                .param("txtUsername", "Admin")
                .param("txtPassword", "admin123")
                .param("Submit", "LOGIN")
                .post(baseURL + "/auth/validateCredentials");
        phpsessid = validateCredentials.getCookie("PHPSESSID");
        String finalPhpsessid = phpsessid;

        jobTitles.forEach(jobTitle -> {
            Response getSaveJobTitle = RestAssured.given()
                    .cookie("PHPSESSID", finalPhpsessid)
                    .get(baseURL + "/index.php/admin/saveJobTitle");

            String htmlJob = getSaveJobTitle.getBody().asString();
            Document docJob = Jsoup.parse(htmlJob);
            String csrfTokenJob = docJob.getElementById("jobTitle__csrf_token").attr("value");

            RequestSpecification saveJobTitle = RestAssured.given()
                    .cookie("PHPSESSID", finalPhpsessid)
                    .param("jobTitle[_csrf_token]", csrfTokenJob)
                    .param("jobTitle[jobTitle]", jobTitle.getTitle())
                    .param("jobTitle[jobDescription]", jobTitle.getDescription())
                    .param("jobTitle[note]", jobTitle.getNote());
            if (!jobTitle.getSpecification().isEmpty() && Files.exists(Paths.get(jobTitle.getSpecification()))) {
                saveJobTitle.multiPart("jobTitle[jobSpec]", new File(jobTitle.getSpecification()));
            }
            saveJobTitle.post(baseURL + "/index.php/admin/saveJobTitle/jobTitleId/");
        });
    }

    public void delete_job_title(JobTitle jobTitle) {
        String baseURL = TestConfiguration.getInstance().getBaseURL();

        Response getLogin = RestAssured.get(baseURL);
        String html = getLogin.getBody().asString();
        String phpsessid = getLogin.getCookie("PHPSESSID");
        Document doc = Jsoup.parse(html);
        String csrfToken = doc.getElementById("csrf_token").attr("value");

        Response validateCredentials = RestAssured.given()
                .cookie("PHPSESSID", phpsessid)
                .param("_csrf_token", csrfToken)
                .param("txtUsername", "Admin")
                .param("txtPassword", "admin123")
                .param("Submit", "LOGIN")
                .post(baseURL + "/auth/validateCredentials");

        phpsessid = validateCredentials.getCookie("PHPSESSID");

        Response getViewJobTitleList = RestAssured.given()
                .cookie("PHPSESSID", phpsessid)
                .get(baseURL + "/index.php/admin/viewJobTitleList");

        html = getViewJobTitleList.getBody().asString();
        doc = Jsoup.parse(html);
        csrfToken = doc.getElementById("defaultList__csrf_token").attr("value");
        String selRow = "";
        try {
            selRow = doc.getElementsMatchingText(jobTitle.getTitle()).attr("href");
        } catch (Exception e) {
        }

        if (!selRow.isEmpty()) {
            selRow = selRow.split("\\?jobTitleId=")[1];
            RestAssured.given()
                    .cookie("PHPSESSID", phpsessid)
                    .param("defaultList[_csrf_token]", csrfToken)
                    .param("chkSelectRow[]", selRow)
                    .post(baseURL + "/index.php/admin/deleteJobTitle");
        }
    }
}
