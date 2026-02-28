# React Date Picker Handling Guide

## Overview
This guide explains how to handle the React date picker on the DemoQA Practice Form page.

## Date Picker Structure

The React date picker has three components:

```html
<!-- Month Dropdown -->
<select class="react-datepicker__month-select">
  <option value="0">January</option>
  <option value="1">February</option>
  ...
  <option value="11">December</option>
</select>

<!-- Year Dropdown -->
<select class="react-datepicker__year-select">
  <option>1900</option>
  <option>1901</option>
  ...
  <option>2100</option>
</select>

<!-- Day Calendar Grid -->
<div class="react-datepicker__day">1</div>
<div class="react-datepicker__day">2</div>
...
```

## Implementation

### Method Signature
```java
public void enterDOB(String dob) throws InterruptedException
```

### Supported Date Formats

#### Format 1: "dd MMM yyyy" (Recommended)
```java
praticeFormPage.enterDOB("15 May 1990");
praticeFormPage.enterDOB("01 January 2000");
praticeFormPage.enterDOB("31 December 1995");
```

#### Format 2: "dd-MM-yyyy"
```java
praticeFormPage.enterDOB("15-05-1990");
praticeFormPage.enterDOB("01-01-2000");
praticeFormPage.enterDOB("31-12-1995");
```

### How It Works

1. **Opens the date picker** by clicking the input field
2. **Parses the date string** into day, month, and year
3. **Selects the month** from the dropdown using Selenium Select class
4. **Selects the year** from the dropdown using Selenium Select class
5. **Clicks the day** from the calendar grid

### Code Example

```java
// In your step definition
@Then("User enters date of birth {string}")
public void user_enters_date_of_birth(String dob) throws InterruptedException {
    praticeFormPage.enterDOB(dob);
}
```

### Feature File Example

```gherkin
Scenario: Fill practice form with date
  Given Lanuch the URL "https://demoqa.com/automation-practice-form"
  When Verify "Student Registration Form" page loaded
  Then User enters first name "John"
  And User enters last name "Doe"
  And Select gender as "Male"
  And User enters mobile number "1234567890"
  And User enters date of birth "15 May 1990"
```

## XPath Strategies Used

### Month Dropdown
```xpath
//select[@class='react-datepicker__month-select']
```

### Year Dropdown
```xpath
//select[@class='react-datepicker__year-select']
```

### Day Selection (avoiding outside-month days)
```xpath
//div[contains(@class,'react-datepicker__day') and 
     not(contains(@class,'outside-month')) and 
     text()='15']
```

## Key Points

✅ **Uses Selenium Select class** - Proper way to handle `<select>` dropdowns
✅ **Avoids outside-month days** - XPath filters out grayed-out days from adjacent months
✅ **Flexible date formats** - Accepts both "dd MMM yyyy" and "dd-MM-yyyy"
✅ **Wait times included** - Small Thread.sleep() between selections for stability
✅ **Error handling** - Throws IllegalArgumentException for unsupported formats

## Alternative Approaches

### 1. Using JavaScript Executor (Not Recommended for Learning)
```java
JavascriptExecutor js = (JavascriptExecutor) driver;
js.executeScript("document.getElementById('dateOfBirthInput').value='15-05-1990'");
```

### 2. Using sendKeys (May not work with React components)
```java
dateOfBirth_txt.sendKeys("15051990");
```

## Troubleshooting

### Issue: Day not clickable
**Solution:** Ensure you're not clicking days with class `outside-month`

### Issue: Dropdown not found
**Solution:** Add explicit wait before selecting:
```java
WebDriverWait wait = new WebDriverWait(driver, 10);
wait.until(ExpectedConditions.visibilityOfElementLocated(
    By.xpath("//select[@class='react-datepicker__month-select']")));
```

### Issue: Date picker doesn't open
**Solution:** Ensure the input field is clickable and visible:
```java
WebDriverWait wait = new WebDriverWait(driver, 10);
wait.until(ExpectedConditions.elementToBeClickable(dateOfBirth_txt));
dateOfBirth_txt.click();
```

## Testing Examples

### Test Different Dates
```gherkin
Examples:
  | firstName | lastName | gender | mobile     | dob           |
  | John      | Doe      | Male   | 1234567890 | 15 May 1990   |
  | Jane      | Smith    | Female | 9876543210 | 01 Jan 2000   |
  | Bob       | Johnson  | Other  | 5555555555 | 31 Dec 1985   |
```

### Scenario Outline
```gherkin
Scenario Outline: Fill form with different dates
  Given Lanuch the URL "https://demoqa.com/automation-practice-form"
  When Verify "Student Registration Form" page loaded
  Then User enters first name "<firstName>"
  And User enters last name "<lastName>"
  And Select gender as "<gender>"
  And User enters mobile number "<mobile>"
  And User enters date of birth "<dob>"
  
  Examples:
    | firstName | lastName | gender | mobile     | dob           |
    | John      | Doe      | Male   | 1234567890 | 15 May 1990   |
```

## Complete Page Object Implementation

```java
@FindBy(xpath = "//input[@id='dateOfBirthInput']")
WebElement dateOfBirth_txt;

public void enterDOB(String dob) throws InterruptedException {
    // Click to open date picker
    dateOfBirth_txt.click();
    Thread.sleep(500);
    
    // Parse date
    String[] dateParts;
    String day, month, year;
    
    if (dob.contains(" ")) {
        dateParts = dob.split(" ");
        day = dateParts[0];
        month = dateParts[1];
        year = dateParts[2];
    } else if (dob.contains("-")) {
        dateParts = dob.split("-");
        day = dateParts[0];
        month = getMonthName(dateParts[1]);
        year = dateParts[2];
    } else {
        throw new IllegalArgumentException("Unsupported date format");
    }
    
    // Select month
    Select selectMonth = new Select(driver.findElement(
        By.xpath("//select[@class='react-datepicker__month-select']")));
    selectMonth.selectByVisibleText(month);
    
    // Select year
    Select selectYear = new Select(driver.findElement(
        By.xpath("//select[@class='react-datepicker__year-select']")));
    selectYear.selectByVisibleText(year);
    
    // Select day
    driver.findElement(By.xpath(
        "//div[contains(@class,'react-datepicker__day') and " +
        "not(contains(@class,'outside-month')) and " +
        "text()='" + Integer.parseInt(day) + "']")).click();
}
```

## Summary

The date picker handling is now fully implemented and supports:
- ✅ Month dropdown selection using Selenium Select
- ✅ Year dropdown selection using Selenium Select  
- ✅ Day selection from calendar grid
- ✅ Multiple date format support
- ✅ Proper waits and error handling
- ✅ Clean, maintainable code

Use the format **"dd MMM yyyy"** (e.g., "15 May 1990") in your feature files for best results!

