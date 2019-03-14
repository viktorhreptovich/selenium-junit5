package com.orangehrmlive.demo.steps;

import com.orangehrmlive.demo.pages.JobTitlesPage;
import com.orangehrmlive.demo.testdata.JobTitle;
import com.orangehrmlive.demo.utils.PageSteps;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PageJobTitlesSteps extends PageSteps {

    JobTitlesPage jobTitlesPage;

    public PageJobTitlesSteps open() {
        jobTitlesPage.open();
        return this;
    }

    public void click_add() {
        jobTitlesPage.clickAdd();
    }

    public void page_is_opened() {
        String currentURL = driver.getCurrentUrl();
        String pageURL = jobTitlesPage.getURL();
        boolean isOpened = currentURL.equals(pageURL);
        assertTrue(isOpened, "Page isn't opened.\nExpected\t:" + pageURL + "\nActual\t\t:" + currentURL);
    }

    public void table_job_titles_contains(JobTitle jobTitle) {
        boolean contains = jobTitlesPage.tableJobTitlesContainsRow(jobTitle.getTitle());
        assertTrue(contains, "Job title[name=" + jobTitle.getTitle() + "] isn't showed in table");
    }


    public void table_job_titles_contains_unique(JobTitle jobTitle) {
        boolean contains = jobTitlesPage.tableJobTitlesContainsRow(jobTitle.getTitle());
        assertEquals(1, jobTitlesPage.getCountRowsJobTitle(jobTitle.getTitle()), "JobTitle[name=" + jobTitle.getTitle() + "] isn't unique");
    }

    public void table_job_titles_contains(List<JobTitle> jobTitles) {
        jobTitles.forEach(this::table_job_titles_contains);
    }

    public void table_job_titles_is_not_contains(JobTitle jobTitle) {
        boolean contains = !jobTitlesPage.tableJobTitlesContainsRow(jobTitle.getTitle());
        assertTrue(contains, "Job title[name=" + jobTitle.getTitle() + "] is showed in table");
    }

    public void table_job_titles_is_not_contains(List<JobTitle> jobTitles) {
        jobTitles.forEach(this::table_job_titles_is_not_contains);
    }

    public void table_job_titles_select(JobTitle jobTitle) {
        jobTitlesPage.tableJobTitlesSelectRow(jobTitle.getTitle());
    }

    public void table_job_titles_select(List<JobTitle> jobTitles) {
        jobTitles.forEach(this::table_job_titles_select);
    }

    public void table_job_titles_click(JobTitle jobTitle) {
        jobTitlesPage.tableJobTitlesClickRow(jobTitle.getTitle());
    }

    public void message_is_displayed(String message) {
        String actualMessage = jobTitlesPage.getMessage();
        assertTrue(actualMessage.contains(message),
                "Message [" + message + "] isn't shown. Expected: " + message + ", but was: " + actualMessage);
    }

    public void click_delete() {
        jobTitlesPage.clickDelete();
    }

    public void should_see_dialog_delete_records() {
        assertTrue(jobTitlesPage.isShownDialogDeleteRecords(), "Dialog Delete Records isn't shown");
    }

    public void close_dialog_delete_records() {
        jobTitlesPage.closeDialogDeleteRecords();
    }

    public void click_cancel_dialog_delete_records() {
        jobTitlesPage.clickCancelDialogDeleteRecords();
    }

    public void click_ok_dialog_delete_records() {
        jobTitlesPage.clickOkDialogDeleteRecords();
    }

}
