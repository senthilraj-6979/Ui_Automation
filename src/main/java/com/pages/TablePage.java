package com.pages;

import com.utilities.UIActionUtility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TablePage {

        private WebDriver driver;

        @FindBy(xpath = "//table[contains(@class, 'table-striped')]")
        private WebElement stripedTable;

        @FindBy(id = "addNewRecordButton")
        private WebElement addBtn;

        @FindBy(className = "modal-content")
        private WebElement registrationFormContent;

        @FindBy(id="registration-form-modal")
        private WebElement registrationFormModal;

        @FindBy(id="userEmail")
        private WebElement emailField;

        @FindBy(id="age")
        private WebElement ageField;

        @FindBy(id="salary")
        private WebElement salary;

        @FindBy(id="department")
        private WebElement department;

        @FindBy(id="submit")
        private WebElement submitBtn;

        @FindBy(css = "table.table-striped.table-bordered.table-hover")
        private WebElement table;


        public TablePage(WebDriver driver) {
                this.driver = driver;
                PageFactory.initElements(driver, this);
        }

        public void clickAddButton() {
                UIActionUtility.waitForElementToBeClickable(driver,addBtn);
                addBtn.click();
        }
        public void isRegistrationFormDisplayed() {
                UIActionUtility.isElementDisplayed(registrationFormModal);
        }

        public void enterEmail(String email) {
              UIActionUtility.typeText(emailField,email);
        }

        public void enterAge(String age) {
                UIActionUtility.typeText(ageField,age);
        }

        public void enterDepartment(String dept) {
                UIActionUtility.typeText(department,dept);
        }

        public void enterSalary(String salary) {
                UIActionUtility.typeText(this.salary,salary);
        }

        public void clickSubmitButton() {
                UIActionUtility.clickElement(submitBtn);
        }

        public void verifyNewRecordAdded(String name) throws InterruptedException {
                String xpath =   "//table[contains(@class, 'table-striped')]//tr//td[contains(text(),'"+name+"')]";
                WebElement newRecord = driver.findElement(By.xpath(xpath));
                if (UIActionUtility.isElementDisplayed(newRecord)) {
                        System.out.println("New record added successfully: " + name);
                } else {
                        System.out.println("Failed to add new record: " + name);
                }
                Thread.sleep(3000);
        }

        public void deleteRecord(String name) throws InterruptedException {
                String xpath =   "//table[contains(@class, 'table-striped')]//tr//td[contains(text(),'"+name+"')]/following-sibling::td//span[@title='Delete']";

                WebElement deleteBtn = driver.findElement(By.xpath(xpath));
                UIActionUtility.clickElement(deleteBtn);
                if (UIActionUtility.isElementDisplayed(deleteBtn)) {
                        System.out.println("New record added successfully: " + name);
                } else {
                        System.out.println("Failed to add new record: " + name);
                }
                Thread.sleep(3000);

        }

}
