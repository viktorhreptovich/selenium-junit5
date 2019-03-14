package com.orangehrmlive.demo.tests;

import com.orangehrmlive.demo.annotations.Steps;
import com.orangehrmlive.demo.annotations.WithAuthorization;
import com.orangehrmlive.demo.steps.PageJobTitlesSteps;
import com.orangehrmlive.demo.steps.PageSaveJobTitleSteps;
import com.orangehrmlive.demo.steps.PrePostConditionsSteps;
import com.orangehrmlive.demo.testdata.JobTitle;
import com.orangehrmlive.demo.utils.BaseTest;
import com.orangehrmlive.demo.utils.TestConfiguration;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@WithAuthorization
public class ViewJobsTest extends BaseTest {

    @Steps
    private PageJobTitlesSteps pageJobTitles;
    @Steps
    private PageSaveJobTitleSteps pageSaveJobTitle;
    @Steps
    private PrePostConditionsSteps prePostConditions;


    @Test
    @DisplayName("Add Job Title")
    void addJobTitle() {
        JobTitle jobTitle = JobTitle.generate();
        String pathToFile = new File(BaseTest.class.getClassLoader().getResource("testdata").getPath()).getAbsolutePath() + "\\0.5Mb.dat";
        jobTitle.withSpecification(pathToFile);
        postCondition = ()-> prePostConditions.delete_job_title(jobTitle);

        pageJobTitles.open();
        pageJobTitles.click_add();
        pageSaveJobTitle.page_is_opened();
        pageSaveJobTitle.set_job_title(jobTitle);
        pageSaveJobTitle.click_save();
        pageJobTitles.page_is_opened();
        pageJobTitles.message_is_displayed("Successfully Saved");
        pageJobTitles.table_job_titles_contains(jobTitle);
    }


    private static Stream<Arguments> requiredFieldsParameters() {
        String pathToFile = new File(BaseTest.class.getClassLoader().getResource("testdata").getPath()).getAbsolutePath() + "\\0.5Mb.dat";
        return Stream.of(
                Arguments.of(JobTitle.create("","","","")),
                Arguments.of(JobTitle.create("", "job description", pathToFile, "job note" )),
                Arguments.of(JobTitle.create("          ", "job description", pathToFile, "job note" ))

        );
    }

    @ParameterizedTest(name = "{index} with ''{0}''")
    @MethodSource("requiredFieldsParameters")
    @DisplayName("Vaidate required field of Job Title")
    void vaidateRequiredFieldOfJobTitle(JobTitle jobTitle) {
        pageSaveJobTitle.open();
        pageSaveJobTitle.set_job_title(jobTitle);
        pageSaveJobTitle.click_save();
        pageSaveJobTitle.page_is_opened();
        pageSaveJobTitle.validation_message_job_title_is_displayed("Required");
        pageSaveJobTitle.click_cancel();
        pageJobTitles.table_job_titles_is_not_contains(jobTitle);
    }

    @Test
    @DisplayName("Attempt create duplicate Job Title")
    void attemtCreateDuplicateJobTitle() {
        JobTitle jobTitle = JobTitle.generate();
        prePostConditions.create_job_title(jobTitle);
        postCondition = ()-> prePostConditions.delete_job_title(jobTitle);

        pageSaveJobTitle.open();
        pageSaveJobTitle.set_job_title(jobTitle);
        pageSaveJobTitle.click_save();
        pageSaveJobTitle.validation_message_job_title_is_displayed("Already exists");
        pageSaveJobTitle.click_cancel();
        pageJobTitles.table_job_titles_contains_unique(jobTitle);
    }

    @Test
    @DisplayName("Attempt create duplicate Job Title double click Save")
    void attemtCreateDuplicateNewJobTitleDblClickSave() {
        JobTitle jobTitle = JobTitle.generate();
        postCondition = ()-> prePostConditions.delete_job_title(jobTitle);

        pageSaveJobTitle.open();
        pageSaveJobTitle.set_job_title(jobTitle);
        pageSaveJobTitle.double_click_save();
        pageJobTitles.table_job_titles_contains_unique(jobTitle);
    }

    @Test
    @DisplayName("Validate limit Job Description field (<400)")
    void validateLimitJobDescriptionField(){
        JobTitle jobTitle = JobTitle.generate();
        String more400 = RandomStringUtils.random(410,true,true);
        jobTitle.withDescription(more400);

        pageSaveJobTitle.open();
        pageSaveJobTitle.set_job_title(jobTitle);
        pageSaveJobTitle.length_of_description_field_is_limited(jobTitle,400);
    }


    private static Stream<Arguments> correctJobSpecificationFile() {
        String pathToFiles = new File(BaseTest.class.getClassLoader().getResource("testdata").getPath()).getAbsolutePath();

        return Stream.of(
                Arguments.of(pathToFiles + "\\0Mb.dat", "size = 0Mb"),
                Arguments.of(pathToFiles + "\\0.5Mb.dat", "size = 0.5Mb"),
                Arguments.of(pathToFiles + "\\0.99Mb.dat", "size = 0.99Mb"),
                Arguments.of(pathToFiles + "\\1Mb.dat", "size = 1Mb")
        );
    }

    @DisplayName("Add Job Title with correct job specification file")
    @ParameterizedTest(name = "{index} with file: ''{1}''")
    @MethodSource("correctJobSpecificationFile")
    void addJobTitleWithCorrectJobSpecificationFile(String fileName, String size) {
        JobTitle jobTitle = JobTitle.generate().withSpecification(fileName);
        postCondition = ()-> prePostConditions.delete_job_title(jobTitle);

        pageJobTitles.open();
        pageJobTitles.click_add();
        pageSaveJobTitle.page_is_opened();
        pageSaveJobTitle.set_job_title(jobTitle);
        pageSaveJobTitle.click_save();
        pageJobTitles.page_is_opened();
        pageJobTitles.message_is_displayed("Successfully Saved");
        pageJobTitles.table_job_titles_contains(jobTitle);
        pageJobTitles.table_job_titles_click(jobTitle);
        pageSaveJobTitle.page_is_opened();
        pageSaveJobTitle.job_title_is_shown(jobTitle);

    }


    private static Stream<Arguments> incorrectJobSpecificationFile() {
        String pathToFiles = new File(BaseTest.class.getClassLoader().getResource("testdata").getPath()).getAbsolutePath();

        return Stream.of(
                Arguments.of(pathToFiles + "\\1.1Mb.dat", "size = 1.1Mb"),
                Arguments.of(pathToFiles + "\\2Mb.dat", "size = 2Mb")
        );
    }

    @DisplayName("Add Job Title with incorrect job specification file")
    @ParameterizedTest(name = "{index} with file: ''{1}''")
    @MethodSource("incorrectJobSpecificationFile")
    void addJobTitleWithIncorrectJobSpecificationFile(String fileName, String size) {
        JobTitle jobTitle = JobTitle.generate().withSpecification(fileName);

        pageJobTitles.open();
        pageJobTitles.click_add();
        pageSaveJobTitle.page_is_opened();
        pageSaveJobTitle.set_job_title(jobTitle);
        pageSaveJobTitle.click_save();
        pageSaveJobTitle.message_is_displayed("Validation Failed");
        pageSaveJobTitle.page_is_opened();
        pageSaveJobTitle.click_cancel();
        pageJobTitles.table_job_titles_is_not_contains(jobTitle);
    }


    @Test
    @DisplayName("Validate limit Job Note field (<400)")
    void validateLimitJobNoteField(){
        JobTitle jobTitle = JobTitle.generate();
        String more400 = RandomStringUtils.random(410,true,true);
        jobTitle.withNote(more400);

        pageSaveJobTitle.open();
        pageSaveJobTitle.set_job_title(jobTitle);
        pageSaveJobTitle.validation_message_job_note_is_displayed("Should be less than 400 characters");
        pageSaveJobTitle.click_save();
        pageSaveJobTitle.page_is_opened();
        pageSaveJobTitle.click_cancel();
        pageJobTitles.table_job_titles_is_not_contains(jobTitle);
    }

    @Test
    @DisplayName("Edit Job Title")
    void editJobTitle() {
        JobTitle jobTitle1 = JobTitle.generate();
        JobTitle jobTitle2 = JobTitle.generate();
        prePostConditions.create_job_title(jobTitle1);
        postCondition = ()-> prePostConditions.delete_job_title(jobTitle2);

        pageJobTitles.open();
        pageJobTitles.table_job_titles_click(jobTitle1);
        pageSaveJobTitle.page_is_opened();
        pageSaveJobTitle.job_title_is_shown(jobTitle1);
        pageSaveJobTitle.fields_shoud_be_disabled();
        pageSaveJobTitle.click_edit();
        pageSaveJobTitle.fields_shoud_be_enabled();
        pageSaveJobTitle.set_job_title(jobTitle2);
        pageSaveJobTitle.click_save();
        pageJobTitles.page_is_opened();
        pageJobTitles.message_is_displayed("Successfully Updated");
        pageJobTitles.table_job_titles_contains(jobTitle2);
        pageJobTitles.table_job_titles_is_not_contains(jobTitle1);
        pageJobTitles.table_job_titles_click(jobTitle2);
        pageSaveJobTitle.page_is_opened();
        pageSaveJobTitle.job_title_is_shown(jobTitle2);
    }

    @Test
    @DisplayName("Edti Job Title: delete specification")
    void editJobTitleDeleteSpecification(){
        JobTitle jobTitle = JobTitle.generate();
        String pathToFile = new File(BaseTest.class.getClassLoader().getResource("testdata").getPath()).getAbsolutePath() + "\\0.5Mb.dat";
        jobTitle.withSpecification(pathToFile);
        JobTitle jobTitle2 = jobTitle.copy();
        jobTitle2.withSpecification("");
        prePostConditions.create_job_title(jobTitle);
        postCondition = ()-> prePostConditions.delete_job_title(jobTitle);

        pageJobTitles.open();
        pageJobTitles.table_job_titles_click(jobTitle);
        pageSaveJobTitle.page_is_opened();
        pageSaveJobTitle.click_edit();
        pageSaveJobTitle.click_job_specification_delete_current();
        pageSaveJobTitle.click_save();
        pageJobTitles.table_job_titles_click(jobTitle);
        pageSaveJobTitle.job_title_is_shown(jobTitle2);
    }

    @Test
    @DisplayName("Edit Job Title: replace specification")
    void editJobTitleReplaceSpecification(){
        JobTitle jobTitle1 = JobTitle.generate();
        String pathToFile1 = new File(BaseTest.class.getClassLoader().getResource("testdata").getPath()).getAbsolutePath() + "\\0Mb.dat";
        jobTitle1.withSpecification(pathToFile1);
        JobTitle jobTitle2 = jobTitle1.copy();
        String pathToFile2 = new File(BaseTest.class.getClassLoader().getResource("testdata").getPath()).getAbsolutePath() + "\\0.5Mb.dat";
        jobTitle2.withSpecification(pathToFile2);
        prePostConditions.create_job_title(jobTitle1);
        postCondition = ()-> prePostConditions.delete_job_title(jobTitle2);

        pageJobTitles.open();
        pageJobTitles.table_job_titles_click(jobTitle1);
        pageSaveJobTitle.page_is_opened();
        pageSaveJobTitle.click_edit();
        pageSaveJobTitle.click_job_specification_replace_current();
        pageSaveJobTitle.set_job_specification(jobTitle2.getSpecification());
        pageSaveJobTitle.click_save();
        pageJobTitles.table_job_titles_click(jobTitle1);
        pageSaveJobTitle.job_title_is_shown(jobTitle2);
    }

    @Test
    @DisplayName("Delete Job Title: single select")
    void deleteJobTitleSingleSelect() {
        JobTitle jobTitle = JobTitle.generate();
        prePostConditions.create_job_title(jobTitle);
        postCondition = () -> prePostConditions.delete_job_title(jobTitle);

        pageJobTitles.open();
        pageJobTitles.table_job_titles_select(jobTitle);
        pageJobTitles.click_delete();
        pageJobTitles.should_see_dialog_delete_records();
        pageJobTitles.close_dialog_delete_records();
        pageJobTitles.table_job_titles_contains(jobTitle);

        pageJobTitles.click_delete();
        pageJobTitles.click_cancel_dialog_delete_records();
        pageJobTitles.table_job_titles_contains(jobTitle);

        pageJobTitles.click_delete();
        pageJobTitles.click_ok_dialog_delete_records();
        pageJobTitles.message_is_displayed("Successfully Deleted");
        pageJobTitles.table_job_titles_is_not_contains(jobTitle);
    }

    @Test
    @DisplayName("Delete Job Title: multi select")
    void deleteJobTitleMultiSelect() {
        List<JobTitle> jobTitles = new ArrayList<JobTitle>(Arrays.asList(JobTitle.generate(), JobTitle.generate()));
        prePostConditions.create_job_title(jobTitles);

        pageJobTitles.open();
        pageJobTitles.table_job_titles_select(jobTitles);
        pageJobTitles.click_delete();
        pageJobTitles.should_see_dialog_delete_records();
        pageJobTitles.click_ok_dialog_delete_records();
        pageJobTitles.message_is_displayed("Successfully Deleted");
        pageJobTitles.table_job_titles_is_not_contains(jobTitles);
    }

}

