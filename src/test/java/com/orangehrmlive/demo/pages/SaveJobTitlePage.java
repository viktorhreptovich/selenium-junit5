package com.orangehrmlive.demo.pages;

import com.orangehrmlive.demo.annotations.RelativeUrl;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

@RelativeUrl("/index.php/admin/saveJobTitle")
public class SaveJobTitlePage extends Page {
    @FindBy(id = "jobTitle_jobTitle")
    private WebElement inputJobTitle;
    @FindBy(css = "span.validation-error[for='jobTitle_jobTitle']")
    private WebElement validatorJobTitle;
    @FindBy(id = "jobTitle_jobDescription")
    private WebElement inputJobDescription;
    @FindBy(id = "jobTitle_jobSpec")
    private WebElement inputJobSpecification;
    @FindBy(css = "#fileLink a")
    private WebElement linkJobSpecification;
    @FindBy(id = "jobTitle_jobSpecUpdate_2")
    private WebElement buttonDeleteJobSpecification;
    @FindBy(id = "jobTitle_jobSpecUpdate_3")
    private WebElement buttonReplaceJobSpecification;

    @FindBy(id = "jobTitle_note")
    private WebElement inputJobNote;
    @FindBy(css = "span.validation-error[for='jobTitle_note']")
    private WebElement validatorJobNote;
    @FindBy(id = "btnSave")
    private WebElement buttonSave;
    @FindBy(id = "btnCancel")
    private WebElement buttonCancel;

    public SaveJobTitlePage(WebDriver driver) {
        super(driver);
    }

    public void setJobTitle(String jobTitle) {
        inputJobTitle.clear();
        inputJobTitle.sendKeys(jobTitle);
    }

    public String getJobTitle() {
        return String.valueOf(jse.executeScript("return arguments[0].value;", inputJobTitle));
    }

    public void setJobDescription(String jobDescription) {
        inputJobDescription.clear();
        inputJobDescription.sendKeys(jobDescription);
    }

    public String getJobDescription() {
        return String.valueOf(jse.executeScript("return arguments[0].value;", inputJobDescription));
    }

    public void setJobSpecification(String jobSpecification) {
        inputJobSpecification.clear();
        if (!jobSpecification.isEmpty()) {
            inputJobSpecification.sendKeys(jobSpecification);
        }
    }

    public String getJobSpecification() {
        if (isElementPresent(linkJobSpecification)) {
            return String.valueOf(jse.executeScript("return arguments[0].text;", linkJobSpecification));
        } else {
            return String.valueOf(jse.executeScript("return arguments[0].value;", inputJobSpecification));
        }
    }

    public void setJobNote(String jobNote) {
        inputJobNote.clear();
        inputJobNote.sendKeys(jobNote);
    }

    public String getJobNote() {
        return String.valueOf(jse.executeScript("return arguments[0].value;", inputJobNote));
    }

    public void clickSave() {
        buttonSave.click();
    }

    public void dblClickSave() {
        Actions action = new Actions(driver);
        action.doubleClick(buttonSave).perform();
    }

    public void clickCancel() {
        buttonCancel.click();
    }

    public boolean isEnabledJobTitle() {
        return inputJobTitle.isEnabled();
    }

    public boolean isEnabledJobDescription() {
        return inputJobDescription.isEnabled();
    }

    public boolean isEnabledJobSpecification() {
        return inputJobSpecification.isEnabled();
    }

    public boolean isEnabledJobNote() {
        return inputJobNote.isEnabled();
    }

    public String getValidationMessageJobTitle() {
        if (isElementPresent(validatorJobTitle)) {
            return validatorJobTitle.getText();
        } else {
            return "";
        }
    }

    public String getValidationMessageJobNote() {
        if (isElementPresent(validatorJobNote)) {
            return validatorJobNote.getText();
        } else {
            return "";
        }
    }

    public String getMessage() {
        try {
            return driver.findElement(By.cssSelector("div.fadable")).getText();
        } catch (NoSuchElementException e) {
            return "";
        }
    }

    public void clickDeleteJobSpecification() {
        buttonDeleteJobSpecification.click();
    }

    public void clickReplaceJobSpecification() {
        buttonReplaceJobSpecification.click();
    }
}
