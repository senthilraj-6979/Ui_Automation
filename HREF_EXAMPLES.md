# Practical href Locator Examples

## Quick Reference

### For `<a href="/alerts">Alerts</a>`

| What You Want | Locator | Code |
|---------------|---------|------|
| **Click the link** | XPath | `By.xpath("//a[@href='/alerts']")` |
| **Click (more flexible)** | XPath | `By.xpath("//a[contains(@href, '/alerts')]")` |
| **Click by text** | Link Text | `By.linkText("Alerts")` |
| **Click (CSS)** | CSS | `By.cssSelector("a[href='/alerts']")` |

---

## Real-World Examples for DemoQA

### Example 1: Alerts & Windows Page
```html
<a href="/alerts">Alerts</a>
```

**Page Object:**
```java
@FindBy(xpath = "//a[@href='/alerts']")
WebElement alertsLink;

public void clickAlerts() {
    UIActionUtility.waitAndGetClickableElement(driver, By.xpath("//a[@href='/alerts']")).click();
}
```

---

### Example 2: Multiple Links with Different hrefs
```html
<nav>
    <a href="/forms">Forms</a>
    <a href="/alerts">Alerts</a>
    <a href="/windows">Windows</a>
</nav>
```

**Page Object:**
```java
@FindBy(xpath = "//a[@href='/forms']")
WebElement formsLink;

@FindBy(xpath = "//a[@href='/alerts']")
WebElement alertsLink;

@FindBy(xpath = "//a[@href='/windows']")
WebElement windowsLink;

public void clickLink(String href) {
    By linkLocator = By.xpath("//a[@href='" + href + "']");
    UIActionUtility.waitAndGetClickableElement(driver, linkLocator).click();
}
```

**Usage:**
```java
clickLink("/alerts");
clickLink("/forms");
clickLink("/windows");
```

---

### Example 3: Links with Query Parameters
```html
<a href="/practice?tab=forms">Practice Forms</a>
<a href="/practice?tab=alerts">Practice Alerts</a>
```

**Use `contains()` for flexibility:**
```java
// Matches both /practice?tab=forms and /practice?tab=alerts
@FindBy(xpath = "//a[contains(@href, '/practice')]")
WebElement practiceLink;

// More specific - only forms
@FindBy(xpath = "//a[contains(@href, '/practice') and contains(@href, 'tab=forms')]")
WebElement practiceFormsLink;
```

---

### Example 4: Links with Specific Class
```html
<a href="/alerts" class="nav-link active">Alerts</a>
<a href="/alerts" class="nav-link">Alerts (Inactive)</a>
```

**Page Object:**
```java
// Only the active alerts link
@FindBy(xpath = "//a[@href='/alerts' and @class='nav-link active']")
WebElement activeAlertsLink;

// Any alerts link
@FindBy(xpath = "//a[@href='/alerts']")
WebElement anyAlertsLink;
```

---

### Example 5: Links within Containers
```html
<nav id="main-nav">
    <a href="/alerts">Alerts</a>
</nav>

<footer id="footer-nav">
    <a href="/alerts">Alerts</a>
</footer>
```

**Page Object:**
```java
// Only in main nav
@FindBy(xpath = "//nav[@id='main-nav']//a[@href='/alerts']")
WebElement mainNavAlertsLink;

// Only in footer
@FindBy(xpath = "//footer[@id='footer-nav']//a[@href='/alerts']")
WebElement footerAlertsLink;

// In any navigation
@FindBy(xpath = "//nav//a[@href='/alerts']")
WebElement navAlertsLink;
```

---

### Example 6: Dynamic href Construction
```java
public class NavigationPage {
    
    private WebDriver driver;
    
    public NavigationPage(WebDriver driver) {
        this.driver = driver;
    }
    
    /**
     * Click any navigation link by href path
     * @param hrefPath e.g., "/alerts", "/forms", "/windows"
     */
    public void clickNavLink(String hrefPath) {
        By locator = By.xpath("//a[@href='" + hrefPath + "']");
        UIActionUtility.waitAndGetClickableElement(driver, locator).click();
    }
    
    /**
     * Click link containing href path
     * @param hrefPart e.g., "alerts", "forms"
     */
    public void clickNavLinkContains(String hrefPart) {
        By locator = By.xpath("//a[contains(@href, '" + hrefPart + "')]");
        UIActionUtility.waitAndGetClickableElement(driver, locator).click();
    }
}
```

**Step Definition:**
```java
@When("User navigates to {string}")
public void user_navigates_to(String page) {
    navigationPage.clickNavLink("/" + page.toLowerCase());
    // Usage: "User navigates to alerts" -> clicks /alerts link
}
```

---

### Example 7: Method with Click and Wait
```java
public class AlertsPage {
    
    private WebDriver driver;
    
    // Constructor
    public AlertsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    /**
     * Safe method to click alerts link
     * Waits for element to be clickable before clicking
     */
    public void clickAlertsLink() {
        By alertsLocator = By.xpath("//a[@href='/alerts']");
        WebElement alertsLink = UIActionUtility.waitAndGetClickableElement(driver, alertsLocator);
        alertsLink.click();
    }
    
    /**
     * Alternative: Using CSS Selector
     */
    public void clickAlertsLinkCSS() {
        By alertsLocator = By.cssSelector("a[href='/alerts']");
        WebElement alertsLink = UIActionUtility.waitAndGetClickableElement(driver, alertsLocator);
        alertsLink.click();
    }
    
    /**
     * Alternative: Using Link Text
     */
    public void clickAlertsLinkByText() {
        By alertsLocator = By.linkText("Alerts");
        WebElement alertsLink = UIActionUtility.waitAndGetClickableElement(driver, alertsLocator);
        alertsLink.click();
    }
}
```

---

## XPath Cheat Sheet for href

```java
// Exact match
By.xpath("//a[@href='/alerts']")

// Partial match
By.xpath("//a[contains(@href, '/alerts')]")

// Starts with
By.xpath("//a[starts-with(@href, '/alerts')]")

// With text content
By.xpath("//a[@href='/alerts' and text()='Alerts']")

// With class
By.xpath("//a[@href='/alerts' and @class='nav-link']")

// Multiple conditions (href AND class)
By.xpath("//a[@href='/alerts' and contains(@class, 'active')]")

// In specific container
By.xpath("//nav[@id='main']//a[@href='/alerts']")

// Case insensitive
By.xpath("//a[contains(translate(@href, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '/alerts')]")

// Button with href (not just <a> tags)
By.xpath("//button[@href='/alerts']")

// Any element with href attribute
By.xpath("//*[@href='/alerts']")
```

---

## CSS Selector Cheat Sheet for href

```java
// Exact match
By.cssSelector("a[href='/alerts']")

// Partial match (contains)
By.cssSelector("a[href*='/alerts']")

// Starts with
By.cssSelector("a[href^='/alerts']")

// Ends with
By.cssSelector("a[href$='alerts']")

// Ends with specific extension
By.cssSelector("a[href$='.pdf']")

// With class
By.cssSelector("a.nav-link[href='/alerts']")

// Multiple conditions
By.cssSelector("a.nav-link.active[href='/alerts']")

// Child selector
By.cssSelector("nav > a[href='/alerts']")

// Descendant selector
By.cssSelector("nav a[href='/alerts']")
```

---

## Testing Your Locator

### Browser Console Test
```javascript
// XPath test
$x("//a[@href='/alerts']")

// CSS Selector test
$$("a[href='/alerts']")

// Check count
$x("//a[@href='/alerts']").length
```

### Selenium Test
```java
@Test
public void testAlertsLocator() {
    driver.get("https://demoqa.com");
    
    // Test XPath
    List<WebElement> links = driver.findElements(By.xpath("//a[@href='/alerts']"));
    System.out.println("Found " + links.size() + " alerts links");
    
    assertTrue(links.size() > 0, "Alerts link not found!");
    
    // Click the first one
    links.get(0).click();
    
    // Wait for page
    new WebDriverWait(driver, 10).until(
        ExpectedConditions.titleContains("Alerts")
    );
}
```

---

## Summary

| Scenario | Locator | Code |
|----------|---------|------|
| Simple href | XPath exact | `//a[@href='/alerts']` |
| Variable href | XPath contains | `//a[contains(@href, '/alerts')]` |
| By link text | Link Text | `linkText("Alerts")` |
| By CSS | CSS Selector | `a[href='/alerts']` |
| Dynamic construction | Java concat | `"//a[@href='" + path + "']"` |

**Best Practice:** Always use `UIActionUtility.waitAndGetClickableElement()` before interacting with elements! ✅

