package com.pages;

import com.utilities.ExcelDataReader;
import com.utilities.TestDataExcelReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.util.*;

/**
 * Page Object for handling Excel data reading operations
 * This class manages all Excel-related interactions including:
 * - Reading test data from Excel files
 * - Managing URL, FirstName, LastName data
 * - Navigating to URLs
 * - Form interaction with Excel data
 */
public class ExcelDataPage {

    private ExcelDataReader excelReader;
    private TestDataExcelReader testDataExcelReader;
    private List<Map<String, String>> currentData;
    private Map<String, String> currentRowData;
    private Map<String, String> currentTestData;
    private String currentSheetName;
    private int rowCount;
    private int columnCount;
    private WebDriver driver;
    private final String EXCEL_FILE_PATH = "./src/test/resources/testdata/TestData.xlsx";

    @FindBy(xpath = "//input[@id='firstName']")
    WebElement firstName_txt;

    @FindBy(xpath = "//input[@id='lastName']")
    WebElement lastName_txt;

    public ExcelDataPage(WebDriver driver) {
        this.driver = driver;
        this.currentData = new ArrayList<>();
        this.currentRowData = new LinkedHashMap<>();
        this.currentTestData = new HashMap<>();
        PageFactory.initElements(driver, this);
    }

    public ExcelDataPage() {
        this.currentData = new ArrayList<>();
        this.currentRowData = new LinkedHashMap<>();
        this.currentTestData = new HashMap<>();
    }

    /**
     * Load Excel file
     * @param filePath Path to Excel file
     * @throws IOException if file not found
     */
    public void loadExcelFile(String filePath) throws IOException {
        try {
            excelReader = new ExcelDataReader("./src/test/resources/testdata/" + filePath);
            System.out.println("✓ Excel file loaded successfully: " + filePath);
        } catch (IOException e) {
            System.out.println("✗ Error loading Excel file: " + e.getMessage());
            throw e;
        }
    }


    public void enterFirstNameFromTestData() {
        String fName = currentTestData.getOrDefault("FirstName", "");
        if (!fName.isEmpty() && firstName_txt != null) {
            firstName_txt.sendKeys(fName);
            System.out.println("✓ FirstName entered: " + fName);
        }
    }

    public void enterLastNameFromTestData() {
        String lName = currentTestData.getOrDefault("LastName", "");
        if (!lName.isEmpty() && lastName_txt != null) {
            lastName_txt.sendKeys(lName);
            System.out.println("✓ LastName entered: " + lName);
        }
    }

    /**
     * Read all data from specific sheet
     * @param sheetName Name of the sheet
     */
    public void readAllDataFromSheet(String sheetName) {
        if (excelReader == null) {
            throw new IllegalStateException("Excel file not loaded. Call loadExcelFile() first");
        }

        try {
            currentSheetName = sheetName;
            currentData = excelReader.getAllData(sheetName);
            rowCount = excelReader.getRowCount(sheetName);
            columnCount = excelReader.getColumnCount(sheetName);
            System.out.println("✓ Successfully read all data from sheet: " + sheetName);
            System.out.println("  Total rows: " + rowCount + ", Total columns: " + columnCount);
        } catch (Exception e) {
            System.out.println("✗ Error reading data from sheet: " + e.getMessage());
        }
    }

    /**
     * Read specific row data by row number (for feature file with string row number)
     * @param sheetName Name of the sheet
     * @param rowNumberStr Row number as string (0-indexed)
     */
    public void readRowDataOldFormat(String sheetName, String rowNumberStr) {
        try {
            int rowNumber = Integer.parseInt(rowNumberStr.trim());
          //  readRowDataOldFormat(sheetName, rowNumber);
        } catch (NumberFormatException e) {
            System.out.println("✗ Invalid row number format: " + rowNumberStr);
        }
    }

    /**
     * Get value of specific field from current row
     * @param fieldName Name of the field/column
     * @return Field value
     */
    public String getFieldValue(String fieldName) {
        String value = currentRowData.get(fieldName);
        if (value == null) {
            System.out.println("✗ Field '" + fieldName + "' not found in current row");
            return "";
        }
        return value;
    }

    /**
     * Get value of specific field from specific row
     * @param rowIndex Row index (0-indexed)
     * @param fieldName Field name
     * @return Field value
     */
    public String getFieldValue(int rowIndex, String fieldName) {
        if (rowIndex < 0 || rowIndex >= currentData.size()) {
            System.out.println("✗ Invalid row index: " + rowIndex);
            return "";
        }

        Map<String, String> rowData = currentData.get(rowIndex);
        String value = rowData.get(fieldName);
        if (value == null) {
            System.out.println("✗ Field '" + fieldName + "' not found in row " + rowIndex);
            return "";
        }
        return value;
    }

    /**
     * Get all data loaded from sheet
     * @return List of maps containing row data
     */
    public List<Map<String, String>> getAllData() {
        return new ArrayList<>(currentData);
    }

    /**
     * Get all data for specific row
     * @param rowIndex Row index (0-indexed)
     * @return Map of field names and values
     */
    public Map<String, String> getRowData(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= currentData.size()) {
            System.out.println("✗ Invalid row index: " + rowIndex);
            return new LinkedHashMap<>();
        }
        return new LinkedHashMap<>(currentData.get(rowIndex));
    }

    /**
     * Get current row data
     * @return Map of current row data
     */
    public Map<String, String> getCurrentRowData() {
        return new LinkedHashMap<>(currentRowData);
    }

    /**
     * Get total row count of current sheet
     * @return Number of rows
     */
    public int getTotalRows() {
        return rowCount;
    }

    /**
     * Get total column count of current sheet
     * @return Number of columns
     */
    public int getTotalColumns() {
        return columnCount;
    }

    /**
     * Print all data to console
     */
    public void printAllData() {
        System.out.println("\n========== EXCEL DATA DUMP ==========");
        System.out.println("Sheet: " + currentSheetName);
        System.out.println("Total Rows: " + rowCount);
        System.out.println("Total Columns: " + columnCount);
        System.out.println("=====================================\n");

        if (currentData.isEmpty()) {
            System.out.println("No data available");
            return;
        }

        // Print headers
        Map<String, String> firstRow = currentData.get(0);
        System.out.print(String.format("%-15s", "Row No"));
        for (String header : firstRow.keySet()) {
            System.out.print(String.format("%-20s", header));
        }
        System.out.println();
        System.out.println("=====================================");

        // Print data
        for (int i = 0; i < currentData.size(); i++) {
            System.out.print(String.format("%-15s", i));
            Map<String, String> row = currentData.get(i);
            for (String value : row.values()) {
                System.out.print(String.format("%-20s", value != null ? value : ""));
            }
            System.out.println();
        }
        System.out.println("=====================================\n");
    }

    /**
     * Print specific row data
     * @param rowIndex Row index to print
     */
    public void printRowData(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= currentData.size()) {
            System.out.println("✗ Invalid row index: " + rowIndex);
            return;
        }

        System.out.println("\n========== ROW DATA ==========");
        System.out.println("Row Number: " + rowIndex);
        Map<String, String> rowData = currentData.get(rowIndex);
        for (String key : rowData.keySet()) {
            System.out.println(key + ": " + rowData.get(key));
        }
        System.out.println("==============================\n");
    }

    /**
     * Print sheet structure
     */
    public void printSheetStructure() {
        if (excelReader == null) {
            System.out.println("✗ Excel file not loaded");
            return;
        }

        System.out.println("\n========== SHEET STRUCTURE ==========");
        System.out.println("Sheet Name: " + currentSheetName);
        System.out.println("Total Rows: " + rowCount);
        System.out.println("Total Columns: " + columnCount);

        if (!currentData.isEmpty()) {
            System.out.println("Column Headers:");
            Map<String, String> firstRow = currentData.get(0);
            int colNum = 1;
            for (String header : firstRow.keySet()) {
                System.out.println("  Column " + colNum + ": " + header);
                colNum++;
            }
        }
        System.out.println("======================================\n");
    }

    /**
     * Verify if field exists in current row
     * @param fieldName Field name to verify
     * @return true if field exists, false otherwise
     */
    public boolean hasField(String fieldName) {
        return currentRowData.containsKey(fieldName);
    }

    /**
     * Get all field names from current data
     * @return List of field names
     */
    public List<String> getFieldNames() {
        if (currentData.isEmpty()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(currentData.get(0).keySet());
    }

    /**
     * Close Excel reader
     */
    public void closeExcelReader() {
        if (excelReader != null) {
            try {
                excelReader.closeWorkbook();
                System.out.println("✓ Excel reader closed successfully");
            } catch (IOException e) {
                System.out.println("✗ Error closing Excel reader: " + e.getMessage());
            }
        }
    }

    // ==================== TestData Excel Reader Methods ====================

    /**
     * Initialize TestData Excel reader with file
     * Reads: URL, FirstName, LastName
     */
    public void initializeExcelReader(String excelFileName) throws IOException {
        try {
            String filePath = "./src/test/resources/testdata/" + excelFileName;
            testDataExcelReader = new TestDataExcelReader(filePath);
            System.out.println("✓ Excel reader initialized for: " + excelFileName);
        } catch (IOException e) {
            System.out.println("✗ Failed to initialize Excel reader: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Read specific row from Excel file (TestData.xlsx format)
     * Reads: URL, FirstName, LastName
     */
    public void readRowData(String sheetName, int rowNumber) {
        if (testDataExcelReader == null) {
            try {
                initializeExcelReader("TestData.xlsx");
            } catch (IOException e) {
                throw new RuntimeException("Failed to initialize Excel reader", e);
            }
        }

        try {
            currentTestData = testDataExcelReader.readTestData(sheetName, rowNumber);
            System.out.println("✓ Row " + rowNumber + " read successfully");
            printCurrentTestData();
        } catch (Exception e) {
            System.out.println("✗ Error reading row: " + e.getMessage());
        }
    }

    /**
     * Load URL from Excel row
     */
    public void loadURLFromExcel(String rowNumber) {
        try {
            if (testDataExcelReader == null) {
                testDataExcelReader = new TestDataExcelReader(EXCEL_FILE_PATH);
            }

            int row = Integer.parseInt(rowNumber.trim());
            currentTestData = testDataExcelReader.readTestData("Sheet1", row);

            String url = currentTestData.getOrDefault("URL", "");
            if (url.isEmpty()) {
                throw new IllegalArgumentException("URL should not be empty");
            }
            System.out.println("✓ URL loaded from row " + row + ": " + url);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load Excel file: " + e.getMessage());
        }
    }

    /**
     * Navigate to URL from current test data
     */
    public void navigateToURL() {
        String url = currentTestData.getOrDefault("URL", "");
        if (url == null || url.trim().isEmpty()) {
            throw new IllegalArgumentException("URL should not be empty");
        }
        System.out.println("✓ Navigating to URL: " + url);
        if (driver != null) {
            driver.get(url);
        }
    }

    /**
     * Get URL from current test data
     */
    public String getURL() {
        return currentTestData.getOrDefault("URL", "");
    }

    /**
     * Get FirstName from current test data
     */
    public String getFirstName() {
        return currentTestData.getOrDefault("FirstName", "");
    }

    /**
     * Get LastName from current test data
     */
    public String getLastName() {
        return currentTestData.getOrDefault("LastName", "");
    }

    /**
     * Get all test data from current row
     */
    public Map<String, String> getCurrentTestData() {
        return new HashMap<>(currentTestData);
    }

    /**
     * Print current test data to console
     */
    public void printCurrentTestData() {
        System.out.println("\n========== CURRENT TEST DATA ==========");
        System.out.println("URL: " + currentTestData.getOrDefault("URL", "N/A"));
        System.out.println("FirstName: " + currentTestData.getOrDefault("FirstName", "N/A"));
        System.out.println("LastName: " + currentTestData.getOrDefault("LastName", "N/A"));
        System.out.println("=========================================\n");
    }

    /**
     * Get all test data rows from Excel
     */
    public List<Map<String, String>> getAllTestDataRows(String sheetName) {
        if (testDataExcelReader == null) {
            try {
                initializeExcelReader("TestData.xlsx");
            } catch (IOException e) {
                throw new RuntimeException("Failed to initialize Excel reader", e);
            }
        }
        return testDataExcelReader.readAllTestData(sheetName);
    }

    /**
     * Get total number of test data rows
     */
    public int getTotalTestDataRows(String sheetName) {
        List<Map<String, String>> allData = getAllTestDataRows(sheetName);
        return allData.size();
    }

    /**
     * Print all test data from Excel
     */
    public void printAllTestData(String sheetName) {
        if (testDataExcelReader != null) {
            testDataExcelReader.printAllData(sheetName);
        }
    }

    /**
     * Close TestData Excel reader
     */
    public void closeTestDataExcelReader() throws IOException {
        if (testDataExcelReader != null) {
            testDataExcelReader.closeWorkbook();
            System.out.println("✓ TestData Excel reader closed");
        }
    }

    /**
     * Verify that URL is not empty
     */
    public boolean isURLValid() {
        String url = currentTestData.getOrDefault("URL", "");
        return url != null && !url.trim().isEmpty();
    }

    /**
     * Verify that FirstName is not empty
     */
    public boolean isFirstNameValid() {
        String firstName = currentTestData.getOrDefault("FirstName", "");
        return firstName != null && !firstName.trim().isEmpty();
    }

    /**
     * Verify that LastName is not empty
     */
    public boolean isLastNameValid() {
        String lastName = currentTestData.getOrDefault("LastName", "");
        return lastName != null && !lastName.trim().isEmpty();
    }
}

