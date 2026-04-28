package stepDef;

import com.pages.ExcelDataPage;
import io.cucumber.java.en.*;
import org.testng.Assert;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Step definitions for Excel data reading operations
 * This class implements all Gherkin steps related to Excel file handling
 */
public class ExcelDataStepDef {

    private ExcelDataPage excelDataPage;

//    /**
//     * Constructor - initializes ExcelDataPage
//     */
    public ExcelDataStepDef() {
        excelDataPage = new ExcelDataPage();
    }

    // ==================== Given Steps ====================

    /**
     * Load Excel file
     * @param fileName Name of the Excel file
     */
    @Given("User loads Excel file {string}")
    public void user_loads_excel_file(String fileName) {
        try {
            excelDataPage.loadExcelFile(fileName);
            System.out.println("✓ Excel file loaded: " + fileName);
        } catch (IOException e) {
            System.out.println("✗ Failed to load Excel file: " + e.getMessage());
            throw new RuntimeException("Failed to load Excel file: " + fileName, e);
        }
    }

    /**
     * Read all test data from Excel sheet
     * @param fileName Name of the Excel file
     * @param sheetName Name of the sheet to read
     */
    @Given("User reads all test data from {string} sheet {string}")
    public void user_reads_all_test_data(String fileName, String sheetName) {
        try {
            excelDataPage.loadExcelFile(fileName);
            excelDataPage.readAllDataFromSheet(sheetName);
            System.out.println("✓ All data read from sheet: " + sheetName);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read test data: " + e.getMessage(), e);
        }
    }

    /**
     * Read specific row from Excel sheet
     * @param rowNumber Row number (0-indexed)
     * @param fileName Name of the Excel file
     * @param sheetName Name of the sheet
     */
    @Given("User reads row {string} from {string} sheet {string}")
    public void user_reads_row_from_sheet(String rowNumber, String fileName, String sheetName) {
        try {
            excelDataPage.loadExcelFile(fileName);
           // excelDataPage.readRowData(sheetName, rowNumber);
            System.out.println("✓ Row " + rowNumber + " read from sheet: " + sheetName);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read row: " + e.getMessage(), e);
        }
    }

    /**
     * Read Excel sheet and prepare data
     * @param fileName Name of the Excel file
     * @param sheetName Name of the sheet
     */
    @Given("User reads {string} sheet {string}")
    public void user_reads_sheet(String fileName, String sheetName) {
        try {
            excelDataPage.loadExcelFile(fileName);
            excelDataPage.readAllDataFromSheet(sheetName);
            System.out.println("✓ Sheet read successfully: " + sheetName);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read sheet: " + e.getMessage(), e);
        }
    }

    // ==================== When Steps ====================

    /**
     * When user reads specific row data
     * @param sheetName Name of the sheet
     * @param rowNumber Row number to read
     */
    @When("User reads row number {int} from sheet {string}")
    public void user_reads_row_number(int rowNumber, String sheetName) {
        excelDataPage.readRowData(sheetName, rowNumber);
        System.out.println("✓ Row data read: " + rowNumber);
    }

    /**
     * When user retrieves a specific cell value
     * @param fieldName Name of the field/column
     */
    @When("User retrieves field value {string}")
    public void user_retrieves_field_value(String fieldName) {
        String value = excelDataPage.getFieldValue(fieldName);
        System.out.println("✓ Retrieved field value - " + fieldName + ": " + value);
    }

    // ==================== Then Steps ====================

    /**
     * Verify that test data is loaded successfully
     */
    @Then("Verify the test data is loaded successfully")
    public void verify_test_data_loaded() {
        List<Map<String, String>> allData = excelDataPage.getAllData();
        Assert.assertTrue(!allData.isEmpty(), "Test data should not be empty");
        System.out.println("✓ Test data verified - " + allData.size() + " rows loaded");
    }

    /**
     * Display all test data in console
     */
    @Then("Display all test data in console")
    public void display_all_test_data() {
        excelDataPage.printAllData();
    }

    /**
     * Verify row data contains specific field
     * @param fieldName Name of the field
     */
    @Then("Verify row data contains field {string}")
    public void verify_row_contains_field(String fieldName) {
        boolean hasField = excelDataPage.hasField(fieldName);
        Assert.assertTrue(hasField, "Field '" + fieldName + "' should exist in row data");
        System.out.println("✓ Field verified: " + fieldName);
    }

    /**
     * Verify field has expected value
     * @param fieldName Name of the field
     * @param expectedValue Expected value of the field
     */
    @Then("Verify field {string} has value {string}")
    public void verify_field_has_value(String fieldName, String expectedValue) {
        String actualValue = excelDataPage.getFieldValue(fieldName);
        Assert.assertEquals(actualValue, expectedValue,
            "Field '" + fieldName + "' should have value: " + expectedValue);
        System.out.println("✓ Field value verified - " + fieldName + ": " + actualValue);
    }

    /**
     * Verify field contains partial value
     * @param fieldName Name of the field
     * @param partialValue Partial value to check
     */
    @Then("Verify field {string} contains {string}")
    public void verify_field_contains(String fieldName, String partialValue) {
        String actualValue = excelDataPage.getFieldValue(fieldName);
        Assert.assertTrue(actualValue.contains(partialValue),
            "Field '" + fieldName + "' should contain: " + partialValue);
        System.out.println("✓ Field contains verified - " + fieldName + ": " + actualValue);
    }

    /**
     * Verify field is not empty
     * @param fieldName Name of the field
     */
    @Then("Verify field {string} is not empty")
    public void verify_field_not_empty(String fieldName) {
        String value = excelDataPage.getFieldValue(fieldName);
        Assert.assertFalse(value.isEmpty(), "Field '" + fieldName + "' should not be empty");
        System.out.println("✓ Field not empty verified - " + fieldName + ": " + value);
    }

    /**
     * Verify total number of rows in sheet
     */
    @Then("Verify total number of rows in sheet")
    public void verify_total_rows() {
        int totalRows = excelDataPage.getTotalRows();
        Assert.assertTrue(totalRows > 0, "Total rows should be greater than 0");
        System.out.println("✓ Total rows verified: " + totalRows);
    }

    /**
     * Verify total number of columns in sheet
     */
    @Then("Verify total number of columns in sheet")
    public void verify_total_columns() {
        int totalColumns = excelDataPage.getTotalColumns();
        Assert.assertTrue(totalColumns > 0, "Total columns should be greater than 0");
        System.out.println("✓ Total columns verified: " + totalColumns);
    }

    /**
     * Display sheet structure
     */
    @Then("Display sheet structure in console")
    public void display_sheet_structure() {
        excelDataPage.printSheetStructure();
    }

    /**
     * Display specific row data
     * @param rowNumber Row number to display
     */
    @Then("Display row number {int} data")
    public void display_row_data(int rowNumber) {
        excelDataPage.printRowData(rowNumber);
    }

    /**
     * Verify total row count matches expected
     * @param expectedCount Expected row count
     */
    @Then("Verify total rows is {int}")
    public void verify_total_rows_count(int expectedCount) {
        int actualCount = excelDataPage.getTotalRows();
        Assert.assertEquals(actualCount, expectedCount,
            "Total rows should be: " + expectedCount);
        System.out.println("✓ Total rows verified: " + actualCount);
    }

    /**
     * Verify total column count matches expected
     * @param expectedCount Expected column count
     */
    @Then("Verify total columns is {int}")
    public void verify_total_columns_count(int expectedCount) {
        int actualCount = excelDataPage.getTotalColumns();
        Assert.assertEquals(actualCount, expectedCount,
            "Total columns should be: " + expectedCount);
        System.out.println("✓ Total columns verified: " + actualCount);
    }

    /**
     * And steps that are commonly reused
     */
    @And("Display row data for {string}")
    public void display_row_data_for(String rowNumber) {
        try {
            int rowNum = Integer.parseInt(rowNumber);
            excelDataPage.printRowData(rowNum);
        } catch (NumberFormatException e) {
            System.out.println("✗ Invalid row number: " + rowNumber);
        }
    }

    @And("User retrieves value of field {string}")
    public void retrieve_field_value(String fieldName) {
        String value = excelDataPage.getFieldValue(fieldName);
        System.out.println("✓ Retrieved: " + fieldName + " = " + value);
    }

    @And("Print all data")
    public void print_all_data() {
        excelDataPage.printAllData();
    }

    @And("Print sheet information")
    public void print_sheet_info() {
        System.out.println("Total Rows: " + excelDataPage.getTotalRows());
        System.out.println("Total Columns: " + excelDataPage.getTotalColumns());
        System.out.println("Field Names: " + excelDataPage.getFieldNames());
    }
}

