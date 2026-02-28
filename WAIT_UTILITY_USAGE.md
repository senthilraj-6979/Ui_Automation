# Wait Utility Methods - UIActionUtility

## Overview
Common utility methods have been added to `UIActionUtility` class to handle explicit waits in Selenium. These methods eliminate the need to create `WebDriverWait` instances repeatedly and make the code more readable and maintainable.

## Available Methods

### 1. `waitAndGetClickableElement(WebDriver driver, By locator)`
Waits for an element to be clickable and returns it (default 10 seconds timeout).

**Usage:**
```java
By buttonLocator = By.xpath("//button[@id='submit']");
WebElement button = UIActionUtility.waitAndGetClickableElement(driver, buttonLocator);
button.click();
```

**Before:**
```java
WebDriverWait wait = new WebDriverWait(driver, 10);
wait.until(ExpectedConditions.elementToBeClickable(buttonLocator));
WebElement button = driver.findElement(buttonLocator);
```

### 2. `waitAndGetClickableElement(WebDriver driver, By locator, int timeoutInSeconds)`
Same as above but with custom timeout.

**Usage:**
```java
WebElement button = UIActionUtility.waitAndGetClickableElement(driver, buttonLocator, 20);
```

### 3. `waitAndGetVisibleElement(WebDriver driver, By locator)`
Waits for an element to be visible and returns it (default 10 seconds timeout).

**Usage:**
```java
By messageLocator = By.xpath("//div[@class='success-message']");
WebElement message = UIActionUtility.waitAndGetVisibleElement(driver, messageLocator);
String text = message.getText();
```

### 4. `waitAndGetVisibleElement(WebDriver driver, By locator, int timeoutInSeconds)`
Same as above but with custom timeout.

### 5. `waitAndGetPresentElement(WebDriver driver, By locator)`
Waits for an element to be present in DOM and returns it (default 10 seconds timeout).

**Usage:**
```java
By dropdownLocator = By.xpath("//select[@class='month-select']");
WebElement dropdown = UIActionUtility.waitAndGetPresentElement(driver, dropdownLocator);
Select select = new Select(dropdown);
```

### 6. `waitAndGetPresentElement(WebDriver driver, By locator, int timeoutInSeconds)`
Same as above but with custom timeout.

### 7. `waitForElementToBeClickable(WebDriver driver, WebElement element)`
Waits for a WebElement (already located using @FindBy) to be clickable (default 10 seconds timeout).

**Usage:**
```java
@FindBy(xpath = "//input[@id='uploadPicture']")
WebElement uploadElement;

public void uploadPicture(String filePath) {
    UIActionUtility.waitForElementToBeClickable(driver, uploadElement);
    uploadElement.sendKeys(filePath);
}
```

### 8. `waitForElementToBeClickable(WebDriver driver, WebElement element, int timeoutInSeconds)`
Same as above but with custom timeout.

## Benefits

1. **Code Reusability**: No need to create WebDriverWait instances repeatedly
2. **Consistency**: Standardized wait patterns across the project
3. **Readability**: Cleaner and more concise code
4. **Maintainability**: Single place to update wait logic if needed
5. **Less Boilerplate**: Reduces repetitive code

## Examples from PraticeFormPage

### Example 1: Upload Picture
```java
public void uploadPicture(String filePath) {
    UIActionUtility.waitForElementToBeClickable(driver, uploadElement);
    uploadElement.sendKeys(filePath);
}
```

### Example 2: Select from React Dropdown
```java
public void selectFromReactDropdown(String fieldId, String optionValue) {
    By inputLocator = By.xpath("//input[@id='" + fieldId + "']");
    WebElement input = UIActionUtility.waitAndGetClickableElement(driver, inputLocator);
    input.click();

    By optionLocator = By.xpath("//div[contains(@class, 'option') and contains(text(), '" + optionValue + "')]");
    WebElement option = UIActionUtility.waitAndGetClickableElement(driver, optionLocator);
    option.click();
}
```

### Example 3: Date Picker with Multiple Wait Types
```java
public void enterDOB(String dob) {
    // Wait for element (WebElement) to be clickable
    UIActionUtility.waitForElementToBeClickable(driver, dateOfBirth_txt);
    dateOfBirth_txt.click();

    // Wait for dropdown to be present
    By monthLocator = By.xpath("//select[@class='react-datepicker__month-select']");
    WebElement monthDropdown = UIActionUtility.waitAndGetPresentElement(driver, monthLocator);
    
    // Wait for day to be clickable
    By dayLocator = By.xpath("//div[contains(@class,'react-datepicker__day')]");
    WebElement dayElement = UIActionUtility.waitAndGetClickableElement(driver, dayLocator);
    dayElement.click();
}
```

## Migration Guide

### Old Pattern:
```java
WebDriverWait wait = new WebDriverWait(driver, 10);
wait.until(ExpectedConditions.elementToBeClickable(locator));
WebElement element = driver.findElement(locator);
element.click();
```

### New Pattern:
```java
WebElement element = UIActionUtility.waitAndGetClickableElement(driver, locator);
element.click();
```

## Note
These utility methods replace `Thread.sleep()` with proper explicit waits, which is a Selenium best practice for more reliable and faster test execution.

