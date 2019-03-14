package com.orangehrmlive.demo.steps;

import com.orangehrmlive.demo.pages.SaveJobTitlePage;
import com.orangehrmlive.demo.testdata.JobTitle;
import com.orangehrmlive.demo.utils.PageSteps;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class PageSaveJobTitleSteps extends PageSteps {
    private SaveJobTitlePage saveJobTitlePage;

    public void open() {
        saveJobTitlePage.open();
    }

    public void set_job_title(String title) {
        saveJobTitlePage.setJobTitle(title);
    }

    public void set_job_description(String description) {
        saveJobTitlePage.setJobDescription(description);
    }

    public void set_job_specification(String specification) {
        saveJobTitlePage.setJobSpecification(specification);
    }

    public void set_job_note(String note) {
        saveJobTitlePage.setJobNote(note);
    }

    public void set_job_title(String jobTitle, String jobDescription, String jobSpecification, String jobNote) {
        set_job_title(jobTitle);
        set_job_description(jobDescription);
        set_job_specification(jobSpecification);
        set_job_note(jobNote);
    }

    public void set_job_title(JobTitle jobTitle) {
        set_job_title(jobTitle.getTitle(), jobTitle.getDescription(), jobTitle.getSpecification(), jobTitle.getNote());
    }

    public void click_cancel() {
        saveJobTitlePage.clickCancel();
    }

    public void page_is_opened() {

        String currentURL = driver.getCurrentUrl();
        String pageURL = saveJobTitlePage.getURL() + ".*";
        boolean isOpened = currentURL.matches(pageURL);
        assertTrue(isOpened,"Page isn't opened.\nExpected\t:" + pageURL + "\nActual\t\t:" + currentURL);
    }

    public void click_save() {
        saveJobTitlePage.clickSave();
    }

    public void click_edit() {
        saveJobTitlePage.clickSave();
    }

    public void fields_shoud_be_disabled() {
        assertFalse(saveJobTitlePage.isEnabledJobTitle(), "Field [Job Title] is enabled");
        assertFalse(saveJobTitlePage.isEnabledJobDescription(), "Field [Job Description] is enabled");
        assertFalse(saveJobTitlePage.isEnabledJobSpecification(), "Field [Job Specification] is enabled");
        assertFalse(saveJobTitlePage.isEnabledJobNote(), "Field [Job Note] is enabled");
    }

    public void fields_shoud_be_enabled() {
        assertAll("Field should be enabled",
                () -> assertTrue(saveJobTitlePage.isEnabledJobTitle(), "Field [Job Title] is enabled"),
                () -> assertTrue(saveJobTitlePage.isEnabledJobDescription(), "Field [Job Description] is enabled"),
                () -> assertTrue(saveJobTitlePage.isEnabledJobSpecification(), "Field [Job Specification] is enabled"),
                () -> assertTrue(saveJobTitlePage.isEnabledJobNote(), "Field [Job Note] is enabled")
        );
    }

    public void job_title_is_shown(JobTitle jobTitle) {
        assertAll("Job Title [" + jobTitle + "] is shown",
                () -> assertEquals(jobTitle.getTitle(), saveJobTitlePage.getJobTitle()),
                () -> assertEquals(jobTitle.getDescription(), saveJobTitlePage.getJobDescription()),
                () -> assertEquals(Paths.get(jobTitle.getSpecification()).getFileName(), Paths.get(saveJobTitlePage.getJobSpecification()).getFileName()),
                () -> assertEquals(jobTitle.getNote(), saveJobTitlePage.getJobNote())
        );
    }

    public void validation_message_job_title_is_displayed(String message) {
        assertEquals(message,saveJobTitlePage.getValidationMessageJobTitle());
    }

    public void validation_message_job_note_is_displayed(String message) {
        assertEquals(message,saveJobTitlePage.getValidationMessageJobNote());
    }

    public void double_click_save() {
        saveJobTitlePage.dblClickSave();
    }

    public void message_is_displayed(String message) {
        String actualMessage = saveJobTitlePage.getMessage();
        assertTrue(actualMessage.contains(message),"Message [" + message + "] isn't displayed. Expected: " + message + ", but was: " + actualMessage);
    }

    public void length_of_description_field_is_limited(JobTitle jobTitle, int countLetters) {
        String actualDescription = saveJobTitlePage.getJobDescription();
        String expectedDescription = jobTitle.getDescription().substring(0,countLetters);
        assertAll(
                ()-> assertEquals(actualDescription.length(),countLetters,"Length of Job Description field is more than " + countLetters),
                ()-> assertEquals(expectedDescription,actualDescription)
        );
    }


    public void click_job_specification_delete_current() {
        saveJobTitlePage.clickDeleteJobSpecification();
    }

    public void click_job_specification_replace_current() {
        saveJobTitlePage.clickReplaceJobSpecification();
    }
}
