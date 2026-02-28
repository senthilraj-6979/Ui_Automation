package com.pages;

import com.utilities.UIActionUtility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class PraticeFormPage {

    private WebDriver driver;

    @FindBy(xpath = "(//h1|//h5)[@class='text-center']")
    WebElement PraticeForm_Lbl;

    @FindBy(xpath = "//input[@id='firstName']")
    WebElement userName_txt;

    @FindBy(xpath = "//input[@id='lastName']")
    WebElement lastName_txt;

    @FindBy(xpath = "//input[@id='userEmail']")
    WebElement email_txt;

    @FindBy(xpath = "//input[@id='userNumber']")
    WebElement phone_txt;

    @FindBy(xpath = "//input[@id='dateOfBirthInput']")
    WebElement dateOfBirth_txt;

    @FindBy(xpath = "//input[@id='uploadPicture']")
    WebElement uploadElement;

    @FindBy(how = How.ID, using = "submit")
    WebElement submit_btn;



    public PraticeFormPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public String pageHeader(String expectedHeader){

        String actualHeader = PraticeForm_Lbl.getText();
        return actualHeader;
    }

    public void enterFirstName(String uName) {
        userName_txt.sendKeys(uName);
    }

    public void enterLastName(String pwd) {
        lastName_txt.sendKeys(pwd);
    }

    public void selectGender(String genderType) {
        WebElement genderOption = driver.findElement(By.xpath("//input[@value='" + genderType + "']"));
        genderOption.click();
    }

    public void enterPhone_no(String phone) {
        phone_txt.sendKeys(phone);
    }

    public void uploadPicture(String filePath) {
        UIActionUtility.waitForElementToBeClickable(driver, uploadElement);
        uploadElement.sendKeys(filePath);
    }


    /**
     * Select hobby checkbox by hobby name
     * Correct XPath: //input[contains(@id, 'hobbies-checkbox')] (not 'hobbies-checkbox')
     * @param hobbyName Name of hobby (e.g., "Sports", "Reading", "Music")
     */
    public void selectHobby(String hobbyName) {
        // Correct XPath with comma in contains()
        By hobbyLocator = By.xpath("//label[contains(text(), '" + hobbyName + "')]//preceding-sibling::input[@type='checkbox']");
        WebElement hobbyCheckbox = UIActionUtility.waitAndGetClickableElement(driver, hobbyLocator);

        // Click only if not already selected
        if (!hobbyCheckbox.isSelected()) {
            hobbyCheckbox.click();
        }
    }

    /**
     * Alternative method - Select hobby by ID
     * Example: hobbies-checkbox-0, hobbies-checkbox-1, etc.
     */
    public void selectHobbyById(String hobbyId) {
        By hobbyLocator = By.xpath("//input[@id='" + hobbyId + "']");
        WebElement hobbyCheckbox = UIActionUtility.waitAndGetClickableElement(driver, hobbyLocator);
        hobbyCheckbox.click();
    }

    /**
     * Handles React date picker with dropdowns using explicit waits
     * @param dob Date in format "dd MMM yyyy" (e.g., "15 Jan 1990")
     */
    public void enterDOB(String dob) {
        // Click to open the date picker with explicit wait
        UIActionUtility.waitForElementToBeClickable(driver, dateOfBirth_txt);
        dateOfBirth_txt.click();

        // Parse the date string
        String[] dateParts;
        String day, month, year;

        if (dob.contains(" ")) {
            // Format: "15 Jan 1990"
            dateParts = dob.split(" ");
            day = dateParts[0];
            month = dateParts[1];
            year = dateParts[2];
        }
        else {
            throw new IllegalArgumentException("Date format not supported. Use 'dd MMM yyyy'");
        }

        // Wait for and select Month from dropdown
        By monthLocator = By.xpath("//select[@class='react-datepicker__month-select']");
        WebElement monthDropdown = UIActionUtility.waitAndGetPresentElement(driver, monthLocator);
        Select selectMonth = new Select(monthDropdown);
        selectMonth.selectByVisibleText(month);

        // Wait for and select Year from dropdown
        By yearLocator = By.xpath("//select[@class='react-datepicker__year-select']");
        WebElement yearDropdown = UIActionUtility.waitAndGetPresentElement(driver, yearLocator);
        Select selectYear = new Select(yearDropdown);
        selectYear.selectByVisibleText(year);

        // Wait for and click Day from calendar
        By dayLocator = By.xpath(
            "//div[contains(@class,'react-datepicker__day') and " +
            "not(contains(@class,'outside-month')) and " +
            "text()='" + Integer.parseInt(day) + "']");
        WebElement dayElement = UIActionUtility.waitAndGetClickableElement(driver, dayLocator);
        dayElement.click();
    }


    /**
     * Select state from React Select dropdown
     * @param state The state name to select (e.g., "Rajasthan")
     */
    public void selectState(String state) throws InterruptedException {
        selectFromReactDropdown("//input[@id='react-select-3-input']", state);
    }

    /**
     * Select city from React Select dropdown
     * @param city The city name to select (e.g., "Jaipur")
     */
    public void selectCity(String city) throws InterruptedException {
        selectFromReactDropdown("//input[@id='react-select-4-input']", city);
    }

    /**
     * Generic method to select value from React Select dropdown
     * @param inputXPath XPath of the input field (e.g., "//input[@id='react-select-3-input']")
     * @param optionValue The option text to select (e.g., "Rajasthan", "Jaipur")
     */
    public void selectFromReactDropdown(String inputXPath, String optionValue) throws InterruptedException {
        // Click to open the dropdown using utility method
        By inputLocator = By.xpath(inputXPath);
        WebElement input = UIActionUtility.waitAndGetClickableElement(driver, inputLocator);
        input.click();

        // Send the search text to filter options
        input.sendKeys(optionValue);

        // Wait for option to appear and be clickable
        // React Select options appear in divs with id containing '-option'
        By optionLocator = By.xpath(
                "//div[contains(@id, '-option') and contains(., '" + optionValue + "')]");
        WebElement option = UIActionUtility.waitAndGetClickableElement(driver, optionLocator);
        option.click();
    }


    public void clickSubmit() {
        submit_btn.click();
    }
}
