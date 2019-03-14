package com.orangehrmlive.demo.pages;

import com.orangehrmlive.demo.annotations.RelativeUrl;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@RelativeUrl("/index.php/admin/viewJobTitleList")
public class JobTitlesPage extends Page{

    @FindBy(id="btnAdd")
    private WebElement buttonAdd;
    @FindBy(id="btnDelete")
    private WebElement buttonDelete;
    @FindBy(id = "deleteConfModal")
    private WebElement dialogDeleteRecoreds;

    public JobTitlesPage(WebDriver driver) {
        super(driver);
    }

    public void clickAdd() {
        buttonAdd.click();
    }

    public void clickDelete(){
        buttonDelete.click();
    }

    public boolean tableJobTitlesContainsRow(String jobTitle) {
        return driver.findElements(By.linkText(jobTitle)).size()==1;
    }

    public int getCountRowsJobTitle(String jobTitle) {
        return driver.findElements(By.linkText(jobTitle)).size();
    }

    public void tableJobTitlesSelectRow(String title) {
        driver.findElement(By.xpath("//*[@id='resultTable']//td[a[text()='"+title+"']]/preceding-sibling::td/input")).click();
    }

    public void tableJobTitlesClickRow(String title) {
        driver.findElement(By.xpath("//*[@id='resultTable']//td/a[text()='"+title+"']")).click();
    }

    public String getMessage() {
        try {
            return driver.findElement(By.cssSelector("div.fadable")).getText();
        }catch (NoSuchElementException e){
            return "";
        }
    }

    public boolean isShownDialogDeleteRecords() {
        return dialogDeleteRecoreds.isDisplayed();
    }

    public void closeDialogDeleteRecords() {
        dialogDeleteRecoreds.findElement(By.cssSelector("a.close")).click();
    }

    public void clickCancelDialogDeleteRecords() {
        dialogDeleteRecoreds.findElement(By.cssSelector("input[value='Cancel']")).click();
    }

    public void clickOkDialogDeleteRecords() {
        dialogDeleteRecoreds.findElement(By.id("dialogDeleteBtn")).click();
    }

}
