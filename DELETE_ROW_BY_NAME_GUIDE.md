# 🎯 DELETE ROW FROM TABLE BASED ON NAME - Complete Guide

## Your HTML Structure

```html
<table class="table table-striped table-bordered table-hover">
  <thead>
    <tr>
      <th>First Name</th>
      <th>Last Name</th>
      <th>Age</th>
      <th>Email</th>
      <th>Salary</th>
      <th>Department</th>
      <th>Action</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>Cierra</td>
      <td>Vega</td>
      <td>39</td>
      <td>cierra@example.com</td>
      <td>10000</td>
      <td>Insurance</td>
      <td>
        <div class="action-buttons">
          <span id="delete-record-1">
            <svg>...</svg> <!-- Delete icon -->
          </span>
        </div>
      </td>
    </tr>
    <!-- More rows -->
  </tbody>
</table>
```

---

## ✅ SOLUTION: Delete Row by Name

### Method 1: Find Row by First Name, Then Click Delete (RECOMMENDED)

```java
public void deleteRowByFirstName(String firstName) {
    // XPath: Find row containing firstName, then click delete button in that row
    String xpath = String.format(
        "//table[contains(@class, 'table-striped')]//tbody//tr[contains(., '%s')]" +
        "//span[contains(@id, 'delete-record')]",
        firstName
    );
    
    WebElement deleteButton = driver.findElement(By.xpath(xpath));
    deleteButton.click();
}

// Usage:
deleteRowByFirstName("Cierra");  // Deletes Cierra Vega's row
deleteRowByFirstName("Alden");   // Deletes Alden Cantrell's row
```

**How it works:**
1. Finds `<tr>` containing "Cierra"
2. Within that `<tr>`, finds the `<span id="delete-record-*">`
3. Clicks it

---

### Method 2: More Specific - Check First Name Column Exactly

```java
public void deleteRowByFirstNameExact(String firstName) {
    String xpath = String.format(
        "//table[contains(@class, 'table-striped')]//tbody//tr" +
        "//td[1 and text()='%s']" +  // First column (td[1]) exactly matches firstName
        "/ancestor::tr//span[contains(@id, 'delete-record')]",
        firstName
    );
    
    WebElement deleteButton = driver.findElement(By.xpath(xpath));
    deleteButton.click();
}

// Usage:
deleteRowByFirstNameExact("Cierra");  // Only deletes if first column exactly = "Cierra"
```

**Why use this:**
- More precise (checks only first name column)
- Avoids matching last names or other columns

---

### Method 3: Delete by Full Name (First + Last)

```java
public void deleteRowByFullName(String firstName, String lastName) {
    String xpath = String.format(
        "//table[contains(@class, 'table-striped')]//tbody//tr" +
        "[contains(., '%s') and contains(., '%s')]" +
        "//span[contains(@id, 'delete-record')]",
        firstName, lastName
    );
    
    WebElement deleteButton = driver.findElement(By.xpath(xpath));
    deleteButton.click();
}

// Usage:
deleteRowByFullName("Cierra", "Vega");  // Very specific match
```

---

### Method 4: Delete by Any Column Value

```java
public void deleteRowByColumnValue(String value) {
    String xpath = String.format(
        "//table[contains(@class, 'table-striped')]//tbody//tr[contains(., '%s')]" +
        "//span[contains(@id, 'delete-record')]",
        value
    );
    
    WebElement deleteButton = driver.findElement(By.xpath(xpath));
    deleteButton.click();
}

// Usage:
deleteRowByColumnValue("Insurance");          // Deletes row with Insurance dept
deleteRowByColumnValue("cierra@example.com"); // Deletes by email
deleteRowByColumnValue("10000");              // Deletes by salary
```

---

### Method 5: Delete by Row Index

```java
public void deleteRowByIndex(int rowIndex) {
    String xpath = String.format(
        "(//table[contains(@class, 'table-striped')]//tbody//tr)[%d]" +
        "//span[contains(@id, 'delete-record')]",
        rowIndex
    );
    
    WebElement deleteButton = driver.findElement(By.xpath(xpath));
    deleteButton.click();
}

// Usage:
deleteRowByIndex(1);  // Deletes first row
deleteRowByIndex(2);  // Deletes second row
```

---

### Method 6: Delete by Email

```java
public void deleteRowByEmail(String email) {
    String xpath = String.format(
        "//table[contains(@class, 'table-striped')]//tbody//tr[contains(., '%s')]" +
        "//span[contains(@id, 'delete-record')]",
        email
    );
    
    WebElement deleteButton = driver.findElement(By.xpath(xpath));
    deleteButton.click();
}

// Usage:
deleteRowByEmail("cierra@example.com");
```

---

## 📊 Complete Page Object Example

```java
package com.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class WebTablePage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath = "//table[contains(@class, 'table-striped')]")
    private WebElement table;

    public WebTablePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    // Delete by first name
    public void deleteRowByFirstName(String firstName) {
        String xpath = String.format(
            "//table[contains(@class, 'table-striped')]//tbody//tr[contains(., '%s')]" +
            "//span[contains(@id, 'delete-record')]",
            firstName
        );
        WebElement deleteButton = wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath(xpath))
        );
        deleteButton.click();
    }

    // Delete by full name
    public void deleteRowByFullName(String firstName, String lastName) {
        String xpath = String.format(
            "//table[contains(@class, 'table-striped')]//tbody//tr" +
            "[contains(., '%s') and contains(., '%s')]" +
            "//span[contains(@id, 'delete-record')]",
            firstName, lastName
        );
        WebElement deleteButton = wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath(xpath))
        );
        deleteButton.click();
    }

    // Verify name exists in table
    public boolean isNameInTable(String name) {
        try {
            String xpath = String.format(
                "//table[contains(@class, 'table-striped')]//tbody//tr[contains(., '%s')]",
                name
            );
            WebElement row = driver.findElement(By.xpath(xpath));
            return row.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
```

---

## 🔥 Step Definition Example

```java
package stepDef;

import com.pages.WebTablePage;
import io.cucumber.java.en.And;

public class WebTableStepDef extends Hooks {
    private WebTablePage webTablePage;

    @And("Click on the Delete button for {string}")
    public void clickOnTheDeleteButtonFor(String firstName) {
        webTablePage = new WebTablePage(driver);
        webTablePage.deleteRowByFirstName(firstName);
    }

    @And("Verify {string} removed from table")
    public void verifyRemovedFromTable(String name) {
        Assert.assertFalse(
            "Name '" + name + "' still exists in table",
            webTablePage.isNameInTable(name)
        );
    }
}
```

---

## 🎯 Feature File Example

```gherkin
@Table
Feature: Table Handling

  Scenario: Delete row by name
    Given User launches the URL "https://demoqa.com/webtables"
    And Verify "Cierra" exists in table
    When Click on the Delete button for "Cierra"
    Then Verify "Cierra" removed from table

  Scenario: Add and delete user
    Given User launches the URL "https://demoqa.com/webtables"
    When Click on the Add button
    And User enters first name "John"
    And User enters last name "Doe"
    And User enters email "john@test.com"
    And User enters age "30"
    And User enters salary "50000"
    And User enters department "IT"
    And Click on the Submit button
    Then Verify "John" added to the table
    When Click on the Delete button for "John"
    Then Verify "John" removed from table
```

---

## 🔍 XPath Breakdown

### Basic XPath Structure:
```xpath
//table[contains(@class, 'table-striped')]  // Find table
  //tbody                                     // Find table body
    //tr[contains(., 'Cierra')]              // Find row containing 'Cierra'
      //span[contains(@id, 'delete-record')] // Find delete button in that row
```

### Step-by-Step Explanation:

1. **`//table[contains(@class, 'table-striped')]`**
   - Find table with class containing 'table-striped'

2. **`//tbody//tr`**
   - Find all rows in table body

3. **`[contains(., 'Cierra')]`**
   - Filter to row containing text 'Cierra' ANYWHERE in the row

4. **`//span[contains(@id, 'delete-record')]`**
   - Within that row, find span with id containing 'delete-record'

5. **Click it!**
   - Delete button clicked ✓

---

## 💡 Advanced: Delete with Confirmation

```java
public void deleteRowWithConfirmation(String firstName) {
    // 1. Find and click delete button
    String xpath = String.format(
        "//table[contains(@class, 'table-striped')]//tbody//tr[contains(., '%s')]" +
        "//span[contains(@id, 'delete-record')]",
        firstName
    );
    WebElement deleteButton = wait.until(
        ExpectedConditions.elementToBeClickable(By.xpath(xpath))
    );
    deleteButton.click();
    
    // 2. Wait for confirmation dialog (if exists)
    try {
        WebElement confirmButton = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//button[text()='Confirm' or text()='Yes' or text()='OK']")
            )
        );
        confirmButton.click();
    } catch (Exception e) {
        // No confirmation dialog, deletion already complete
    }
}
```

---

## 🎨 Visual Reference

```
Table Structure:
┌───────────┬──────────┬─────┬───────────────────┬────────┬────────────┬────────┐
│ First     │ Last     │ Age │ Email             │ Salary │ Department │ Action │
│ Name      │ Name     │     │                   │        │            │        │
├───────────┼──────────┼─────┼───────────────────┼────────┼────────────┼────────┤
│ Cierra    │ Vega     │ 39  │ cierra@example... │ 10000  │ Insurance  │ 🗑️ ← Click this
├───────────┼──────────┼─────┼───────────────────┼────────┼────────────┼────────┤
│ Alden     │ Cantrell │ 45  │ alden@example...  │ 12000  │ Compliance │ 🗑️
├───────────┼──────────┼─────┼───────────────────┼────────┼────────────┼────────┤
│ Kierra    │ Gentry   │ 29  │ kierra@example... │ 2000   │ Legal      │ 🗑️
└───────────┴──────────┴─────┴───────────────────┴────────┴────────────┴────────┘

To delete "Cierra" row:
1. Find row containing "Cierra"  ✓
2. Find delete button in that row ✓
3. Click it                       ✓
4. Row deleted!                   ✅
```

---

## ✅ Summary

### Best Method for Your Case:

```java
// RECOMMENDED: Simple and effective
public void deleteRowByFirstName(String firstName) {
    String xpath = String.format(
        "//table[contains(@class, 'table-striped')]//tbody//tr[contains(., '%s')]" +
        "//span[contains(@id, 'delete-record')]",
        firstName
    );
    driver.findElement(By.xpath(xpath)).click();
}
```

**Usage in Cucumber:**
```gherkin
And Click on the Delete button for "Cierra"
And Click on the Delete button for "test"
```

**Why this works:**
- ✅ Finds row containing the name
- ✅ Clicks delete button in that specific row
- ✅ Simple and reliable
- ✅ Works with any name

---

**Your Table.feature is ready to run with the WebTablePage.java and WebTableStepDef.java!** 🎉


