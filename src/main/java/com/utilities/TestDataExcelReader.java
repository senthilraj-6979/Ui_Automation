package com.utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * Simple utility to read specific columns from TestData.xlsx
 * Reads: URL, FirstName, LastName from the Excel file
 */
public class TestDataExcelReader {

    private Workbook workbook;
    private String filePath;

    public TestDataExcelReader(String filePath) throws IOException {
        this.filePath = filePath;
        initializeWorkbook();
    }

    private void initializeWorkbook() throws IOException {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new IOException("Excel file not found: " + filePath);
            }
            FileInputStream fis = new FileInputStream(file);
            workbook = new XSSFWorkbook(fis);
            System.out.println("✓ Excel file loaded: " + filePath);
        } catch (IOException e) {
            System.out.println("✗ Error loading Excel file: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Get value from cell
     */
    private String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    double numValue = cell.getNumericCellValue();
                    if (numValue == Math.floor(numValue)) {
                        return String.valueOf((long) numValue);
                    }
                    return String.valueOf(numValue);
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }

    /**
     * Get column index by header name
     */
    private int getColumnIndex(Sheet sheet, String headerName) {
        Row headerRow = sheet.getRow(0);
        if (headerRow == null) {
            return -1;
        }

        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            Cell cell = headerRow.getCell(i);
            if (cell != null && getCellValue(cell).equalsIgnoreCase(headerName)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Read URL, FirstName, LastName from a specific sheet
     */
    public Map<String, String> readTestData(String sheetName, int rowNumber) {
        Map<String, String> testData = new LinkedHashMap<>();

        try {
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet '" + sheetName + "' not found");
            }

            // Get column indices
            int urlColIndex = getColumnIndex(sheet, "URL");
            int firstNameColIndex = getColumnIndex(sheet, "FirstName");
            int lastNameColIndex = getColumnIndex(sheet, "LastName");

            // Validate columns exist
            if (urlColIndex == -1) System.out.println("⚠ 'URL' column not found");
            if (firstNameColIndex == -1) System.out.println("⚠ 'FirstName' column not found");
            if (lastNameColIndex == -1) System.out.println("⚠ 'LastName' column not found");

            // Get data row (rowNumber + 1 because first row is header)
            Row dataRow = sheet.getRow(rowNumber + 1);
            if (dataRow == null) {
                throw new IllegalArgumentException("Row " + (rowNumber + 1) + " not found in sheet");
            }

            // Read values
            if (urlColIndex >= 0) {
                testData.put("URL", getCellValue(dataRow.getCell(urlColIndex)));
            }
            if (firstNameColIndex >= 0) {
                testData.put("FirstName", getCellValue(dataRow.getCell(firstNameColIndex)));
            }
            if (lastNameColIndex >= 0) {
                testData.put("LastName", getCellValue(dataRow.getCell(lastNameColIndex)));
            }

            System.out.println("✓ Test data read successfully from row " + rowNumber);

        } catch (Exception e) {
            System.out.println("✗ Error reading test data: " + e.getMessage());
        }

        return testData;
    }

    /**
     * Read all test data from sheet
     */
    public List<Map<String, String>> readAllTestData(String sheetName) {
        List<Map<String, String>> allData = new ArrayList<>();

        try {
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet '" + sheetName + "' not found");
            }

            int lastRowNum = sheet.getLastRowNum();
            for (int i = 0; i < lastRowNum; i++) {
                Map<String, String> rowData = readTestData(sheetName, i);
                if (!rowData.isEmpty()) {
                    allData.add(rowData);
                }
            }

            System.out.println("✓ Total rows read: " + allData.size());

        } catch (Exception e) {
            System.out.println("✗ Error reading all test data: " + e.getMessage());
        }

        return allData;
    }

    /**
     * Get specific value
     */
    public String getValue(String sheetName, int rowNumber, String columnName) {
        Map<String, String> rowData = readTestData(sheetName, rowNumber);
        return rowData.getOrDefault(columnName, "");
    }

    /**
     * Print all data in table format
     */
    public void printAllData(String sheetName) {
        List<Map<String, String>> allData = readAllTestData(sheetName);

        if (allData.isEmpty()) {
            System.out.println("No data found");
            return;
        }

        System.out.println("\n========== TEST DATA FROM EXCEL ==========");
        System.out.println(String.format("%-5s %-50s %-20s %-20s", "Row", "URL", "FirstName", "LastName"));
        System.out.println("=========================================");

        for (int i = 0; i < allData.size(); i++) {
            Map<String, String> row = allData.get(i);
            System.out.println(String.format("%-5d %-50s %-20s %-20s",
                i,
                row.getOrDefault("URL", ""),
                row.getOrDefault("FirstName", ""),
                row.getOrDefault("LastName", "")));
        }
        System.out.println("=========================================\n");
    }

    /**
     * Close workbook
     */
    public void closeWorkbook() throws IOException {
        if (workbook != null) {
            workbook.close();
            System.out.println("✓ Workbook closed");
        }
    }
}

