# 🎨 VISUAL EXAMPLES: Contains with Class Names

## Your HTML Element
```html
<table class="table table-striped table-bordered table-hover">
    <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>1</td>
            <td>John Doe</td>
            <td>john@example.com</td>
        </tr>
        <tr>
            <td>2</td>
            <td>Jane Smith</td>
            <td>jane@example.com</td>
        </tr>
    </tbody>
</table>
```

---

## 1️⃣ Find the Entire Table

### Option A: CSS (BEST)
```java
@FindBy(css = "table.table-striped.table-bordered.table-hover")
private WebElement table;
```

### Option B: XPath with contains
```java
@FindBy(xpath = "//table[contains(@class, 'table-striped')]")
private WebElement table;
```

### Option C: XPath with multiple contains
```java
@FindBy(xpath = "//table[contains(@class, 'table-striped') and contains(@class, 'table-bordered')]")
private WebElement table;
```

---

## 2️⃣ Find Specific Cells

### Cell at Row 1, Column 2 (John Doe)
```java
@FindBy(xpath = "//table[contains(@class, 'table-striped')]//tbody//tr[1]//td[2]")
private WebElement johnDoeCell;
```

### Cell at Row 2, Column 3 (jane@example.com)
```java
@FindBy(xpath = "//table[contains(@class, 'table-striped')]//tbody//tr[2]//td[3]")
private WebElement janeEmailCell;
```

---

## 3️⃣ Find All Rows

```java
@FindBy(xpath = "//table[contains(@class, 'table-striped')]//tbody//tr")
private List<WebElement> allRows;

// Usage:
int rowCount = allRows.size(); // Returns 2
```

---

## 4️⃣ Find Row Containing Specific Text

### Find row with "John Doe"
```java
@FindBy(xpath = "//table[contains(@class, 'table-striped')]//tbody//tr[contains(., 'John Doe')]")
private WebElement johnRow;
```

### Find row with "jane@example.com"
```java
@FindBy(xpath = "//table[contains(@class, 'table-striped')]//tbody//tr[contains(., 'jane@example.com')]")
private WebElement janeRow;
```

---

## 5️⃣ Find Cell by Text

### Find cell containing "Jane Smith"
```java
@FindBy(xpath = "//table[contains(@class, 'table-striped')]//tbody//td[contains(text(), 'Jane Smith')]")
private WebElement janeNameCell;
```

---

## 6️⃣ Dynamic Methods

### Get Any Cell
```java
public WebElement getCell(int row, int column) {
    String xpath = String.format(
        "//table[contains(@class, 'table-striped')]//tbody//tr[%d]//td[%d]",
        row, column
    );
    return driver.findElement(By.xpath(xpath));
}

// Usage:
WebElement cell = getCell(1, 2);  // Returns "John Doe" cell
String text = cell.getText();     // Returns "John Doe"
```

---

### Get Cell Text Directly
```java
public String getCellText(int row, int column) {
    return getCell(row, column).getText();
}

// Usage:
String email = getCellText(2, 3);  // Returns "jane@example.com"
```

---

### Get All Data in a Row
```java
public List<String> getRowData(int row) {
    String xpath = String.format(
        "//table[contains(@class, 'table-striped')]//tbody//tr[%d]//td",
        row
    );
    List<WebElement> cells = driver.findElements(By.xpath(xpath));
    
    List<String> data = new ArrayList<>();
    for (WebElement cell : cells) {
        data.add(cell.getText());
    }
    return data;
}

// Usage:
List<String> johnData = getRowData(1);
// Returns: ["1", "John Doe", "john@example.com"]
```

---

### Find Row by Name
```java
public WebElement findRowByName(String name) {
    String xpath = String.format(
        "//table[contains(@class, 'table-striped')]//tbody//tr[contains(., '%s')]",
        name
    );
    return driver.findElement(By.xpath(xpath));
}

// Usage:
WebElement johnRow = findRowByName("John Doe");
```

---

### Get Email by Name
```java
public String getEmailByName(String name) {
    WebElement row = findRowByName(name);
    List<WebElement> cells = row.findElements(By.xpath(".//td"));
    return cells.get(2).getText();  // Email is in 3rd column (index 2)
}

// Usage:
String johnEmail = getEmailByName("John Doe");  // Returns "john@example.com"
```

---

## 7️⃣ Real-World Complete Example

```java
package com.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.ArrayList;
import java.util.List;

public class UserTablePage {
    private WebDriver driver;
    
    // Find the table
    @FindBy(css = "table.table-striped.table-bordered.table-hover")
    private WebElement userTable;
    
    // Find all rows
    @FindBy(xpath = "//table[contains(@class, 'table-striped')]//tbody//tr")
    private List<WebElement> allRows;
    
    public UserTablePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    // Get cell by row and column
    public WebElement getCell(int row, int column) {
        String xpath = String.format(
            "//table[contains(@class, 'table-striped')]//tbody//tr[%d]//td[%d]",
            row, column
        );
        return driver.findElement(By.xpath(xpath));
    }
    
    // Get cell text
    public String getCellText(int row, int column) {
        return getCell(row, column).getText();
    }
    
    // Get all data in a row
    public List<String> getRowData(int row) {
        String xpath = String.format(
            "//table[contains(@class, 'table-striped')]//tbody//tr[%d]//td",
            row
        );
        List<WebElement> cells = driver.findElements(By.xpath(xpath));
        
        List<String> data = new ArrayList<>();
        for (WebElement cell : cells) {
            data.add(cell.getText());
        }
        return data;
    }
    
    // Find row by user name
    public WebElement findRowByName(String name) {
        String xpath = String.format(
            "//table[contains(@class, 'table-striped')]//tbody//tr[contains(., '%s')]",
            name
        );
        return driver.findElement(By.xpath(xpath));
    }
    
    // Get user email by name
    public String getEmailByName(String name) {
        WebElement row = findRowByName(name);
        List<WebElement> cells = row.findElements(By.xpath(".//td"));
        return cells.get(2).getText();  // Email is in column 3 (index 2)
    }
    
    // Get total row count
    public int getRowCount() {
        return allRows.size();
    }
    
    // Check if user exists
    public boolean userExists(String name) {
        try {
            findRowByName(name);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    // Get all user names
    public List<String> getAllNames() {
        List<String> names = new ArrayList<>();
        for (int i = 1; i <= getRowCount(); i++) {
            names.add(getCellText(i, 2));  // Name is in column 2
        }
        return names;
    }
}
```

---

## 8️⃣ Usage in Test

```java
@Test
public void testUserTable() {
    UserTablePage tablePage = new UserTablePage(driver);
    
    // Get total rows
    int totalUsers = tablePage.getRowCount();
    System.out.println("Total users: " + totalUsers);  // Output: 2
    
    // Get specific cell
    String name = tablePage.getCellText(1, 2);
    System.out.println("First user: " + name);  // Output: John Doe
    
    // Get row data
    List<String> johnData = tablePage.getRowData(1);
    System.out.println("John's data: " + johnData);
    // Output: [1, John Doe, john@example.com]
    
    // Find by name
    String janeEmail = tablePage.getEmailByName("Jane Smith");
    System.out.println("Jane's email: " + janeEmail);
    // Output: jane@example.com
    
    // Check if user exists
    boolean exists = tablePage.userExists("John Doe");
    System.out.println("John exists: " + exists);  // Output: true
    
    // Get all names
    List<String> allNames = tablePage.getAllNames();
    System.out.println("All users: " + allNames);
    // Output: [John Doe, Jane Smith]
}
```

---

## 🎯 Visual Reference

```
Table Structure:
┌─────────────────────────────────────────────────┐
│ table.table-striped.table-bordered.table-hover  │
├─────────────────────────────────────────────────┤
│ thead                                           │
│  ├─ tr                                          │
│  │   ├─ th (ID)                                 │
│  │   ├─ th (Name)                               │
│  │   └─ th (Email)                              │
├─────────────────────────────────────────────────┤
│ tbody                                           │
│  ├─ tr[1] ─ Row 1                               │
│  │   ├─ td[1] = "1"                             │
│  │   ├─ td[2] = "John Doe"                      │
│  │   └─ td[3] = "john@example.com"              │
│  │                                               │
│  └─ tr[2] ─ Row 2                               │
│      ├─ td[1] = "2"                             │
│      ├─ td[2] = "Jane Smith"                    │
│      └─ td[3] = "jane@example.com"              │
└─────────────────────────────────────────────────┘

XPath Examples:
//table[contains(@class, 'table-striped')]           ← Find table
//table[contains(@class, 'table-striped')]//tbody    ← Find body
//table[contains(@class, 'table-striped')]//tr[1]    ← Find row 1
//table[contains(@class, 'table-striped')]//tr[1]//td[2]  ← "John Doe"
```

---

**Copy these examples into your tests and they'll work immediately!** ✅


