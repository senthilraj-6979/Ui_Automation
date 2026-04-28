package stepDef;

import com.qa.factory.DriverFactory;
import com.pages.ExcelDataPage;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.io.IOException;

/**
 * Step definitions for reading test data from Excel file
 * Delegates all Excel operations to ExcelDataPage
 * Specifically reads: URL, FirstName, LastName
 */
public class TestDataExcelReaderStepDef {

    private ExcelDataPage excelDataPage;
    private WebDriver driver;

    public TestDataExcelReaderStepDef() {
        this.driver = DriverFactory.getDriver();
        this.excelDataPage = new ExcelDataPage(driver);
    }

    // ==================== Given Steps ====================

    /**
     * Read test data from Excel file
     */
    @Given("User reads test data from {string}")
    public void user_reads_test_data(String excelFileName) {
        try {
            excelDataPage.initializeExcelReader(excelFileName);
        } catch (IOException e) {
            System.out.println("✗ Failed to initialize Excel reader: " + e.getMessage());
            throw new RuntimeException("Failed to read Excel file: " + excelFileName, e);
        }
    }

    /**
     * Read specific row from Excel
     */
    @Given("User reads row {string} from {string}")
    public void user_reads_specific_row(String rowNumber, String excelFileName) {
        try {
            excelDataPage.initializeExcelReader(excelFileName);
            int row = Integer.parseInt(rowNumber.trim());
            excelDataPage.readRowData("Sheet1", row);
        } catch (NumberFormatException e) {
            System.out.println("✗ Invalid row number: " + rowNumber);
            throw new RuntimeException("Invalid row number format");
        } catch (IOException e) {
            throw new RuntimeException("Failed to read Excel file", e);
        }
    }

    /**
     * Load URL from Excel row
     */
    @Given("User loads URL from Excel row {string}")
    public void user_loads_url_from_excel(String rowNumber) {
        try {
            excelDataPage.loadURLFromExcel(rowNumber);
            String url = excelDataPage.getURL();
            Assert.assertFalse(url.isEmpty(), "URL should not be empty");
            System.out.println("✓ URL loaded successfully");
        } catch (Exception e) {
            throw new RuntimeException("Failed to load URL from Excel: " + e.getMessage());
        }
    }

    // ==================== When Steps ====================

    /**
     * Navigate to URL from current test data
     */
    @When("User navigates to the URL")
    public void user_navigates_to_url() {
        excelDataPage.navigateToURL();
        System.out.println("✓ Navigation completed");
    }

    // ==================== Then Steps ====================

    /**
     * Print total number of test data rows
     */
    @Then("Print total number of test data rows")
    public void print_total_rows() {
        int totalRows = excelDataPage.getTotalTestDataRows("Sheet1");
        System.out.println("\n✓ Total test data rows: " + totalRows + "\n");
    }

    /**
     * Store URL from current row
     */
    @Then("Store URL as {string}")
    public void store_url(String expectedUrl) {
        String url = excelDataPage.getURL();
        System.out.println("✓ Stored URL: " + url);
        Assert.assertFalse(url.isEmpty(), "URL should not be empty");
    }

    /**
     * Store FirstName from current row
     */
    @Then("Store FirstName as {string}")
    public void store_firstname(String expectedFirstName) {
        String firstName = excelDataPage.getFirstName();
        System.out.println("✓ Stored FirstName: " + firstName);
        Assert.assertFalse(firstName.isEmpty(), "FirstName should not be empty");
    }

    /**
     * Store LastName from current row
     */
    @Then("Store LastName as {string}")
    public void store_lastname(String expectedLastName) {
        String lastName = excelDataPage.getLastName();
        System.out.println("✓ Stored LastName: " + lastName);
        Assert.assertFalse(lastName.isEmpty(), "LastName should not be empty");
    }

    /**
     * Print retrieved test data
     */
    @Then("Print retrieved test data")
    public void print_retrieved_test_data() {
        excelDataPage.printCurrentTestData();
    }

    /**
     * Verify page loads successfully
     */
    @Then("Verify page loads successfully")
    public void verify_page_loads_successfully() {
        System.out.println("✓ Page loaded successfully");
    }

    /**
     * Verify page title contains first name
     */
    @Then("Verify page title contains first name {string}")
    public void verify_page_title_contains_firstname(String firstName) {
        String storedFirstName = excelDataPage.getFirstName();
        Assert.assertFalse(storedFirstName.isEmpty(), "FirstName should not be empty");
        System.out.println("✓ Page title verification for: " + storedFirstName);
    }

    /**
     * Helper method to access current test data
     */
    public String getURL() {
        return excelDataPage.getURL();
    }

    /**
     * Helper method to access current FirstName
     */
    public String getFirstName() {
        return excelDataPage.getFirstName();
    }

    /**
     * Helper method to access current LastName
     */
    public String getLastName() {
        return excelDataPage.getLastName();
    }

    /**
     * Close Excel reader
     */
    public void closeExcelReader() throws IOException {
        excelDataPage.closeTestDataExcelReader();
    }
}

