package com.utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * Utility class to read data from Excel files
 * Supports reading data by sheet name, row number, column index, and cell value
 *
 * Usage:
 *   ExcelDataReader reader = new ExcelDataReader("path/to/file.xlsx");
 *   Map<String, String> rowData = reader.getRowData("SheetName", 0);
 *   String cellValue = reader.getCellValue("SheetName", 0, 0);
 *   List<Map<String, String>> allData = reader.getAllData("SheetName");
 */
public class ExcelDataReader {

    private Workbook workbook;
    private String filePath;

    /**
     * Constructor to initialize Excel file
     * @param filePath Path to the Excel file
     * @throws IOException if file not found
     */
    public ExcelDataReader(String filePath) throws IOException {
        this.filePath = filePath;
        initializeWorkbook();
    }

    /**
     * Initialize workbook from Excel file
     * @throws IOException if file cannot be read
     */
    private void initializeWorkbook() throws IOException {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new IOException("Excel file not found: " + filePath);
            }
            FileInputStream fis = new FileInputStream(file);
            workbook = new XSSFWorkbook(fis);
        } catch (IOException e) {
            System.out.println("Error initializing workbook: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Get Sheet object by name
     * @param sheetName Name of the sheet
     * @return Sheet object
     * @throws IllegalArgumentException if sheet not found
     */
    private Sheet getSheet(String sheetName) {
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            throw new IllegalArgumentException("Sheet '" + sheetName + "' not found in workbook");
        }
        return sheet;
    }

    /**
     * Get cell value as String
     * @param cell Cell object
     * @return Cell value as String
     */
    private String getCellValueAsString(Cell cell) {
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
                    // Check if it's an integer
                    if (numValue == Math.floor(numValue)) {
                        return String.valueOf((long) numValue);
                    }
                    return String.valueOf(numValue);
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                return "";
        }
    }

    /**
     * Get single cell value
     * @param sheetName Name of the sheet
     * @param rowNum Row number (0-indexed)
     * @param colNum Column number (0-indexed)
     * @return Cell value as String
     */
    public String getCellValue(String sheetName, int rowNum, int colNum) {
        try {
            Sheet sheet = getSheet(sheetName);
            Row row = sheet.getRow(rowNum);
            if (row == null) {
                return "";
            }
            Cell cell = row.getCell(colNum);
            return getCellValueAsString(cell);
        } catch (Exception e) {
            System.out.println("Error reading cell value: " + e.getMessage());
            return "";
        }
    }

    /**
     * Get row data as a map with headers from first row
     * @param sheetName Name of the sheet
     * @param rowNum Row number (0-indexed, excluding header row)
     * @return Map with column headers as keys
     */
    public Map<String, String> getRowData(String sheetName, int rowNum) {
        Map<String, String> rowData = new LinkedHashMap<>();
        try {
            Sheet sheet = getSheet(sheetName);

            // Get header row (first row)
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new IllegalArgumentException("Header row not found in sheet: " + sheetName);
            }

            // Get data row
            Row dataRow = sheet.getRow(rowNum + 1); // +1 because first row is header
            if (dataRow == null) {
                throw new IllegalArgumentException("Row " + (rowNum + 1) + " not found in sheet: " + sheetName);
            }

            // Map data using headers
            int columnCount = headerRow.getLastCellNum();
            for (int i = 0; i < columnCount; i++) {
                String headerValue = getCellValueAsString(headerRow.getCell(i));
                String dataValue = getCellValueAsString(dataRow.getCell(i));
                rowData.put(headerValue, dataValue);
            }
        } catch (Exception e) {
            System.out.println("Error reading row data: " + e.getMessage());
        }
        return rowData;
    }

    /**
     * Get all data from sheet as list of maps
     * @param sheetName Name of the sheet
     * @return List of maps, each map represents a row
     */
    public List<Map<String, String>> getAllData(String sheetName) {
        List<Map<String, String>> allData = new ArrayList<>();
        try {
            Sheet sheet = getSheet(sheetName);

            // Get header row
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new IllegalArgumentException("Header row not found in sheet: " + sheetName);
            }

            // Get all rows except header
            int lastRowNum = sheet.getLastRowNum();
            for (int i = 1; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    Map<String, String> rowData = new LinkedHashMap<>();
                    int columnCount = headerRow.getLastCellNum();

                    for (int j = 0; j < columnCount; j++) {
                        String headerValue = getCellValueAsString(headerRow.getCell(j));
                        String cellValue = getCellValueAsString(row.getCell(j));
                        rowData.put(headerValue, cellValue);
                    }
                    allData.add(rowData);
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading all data: " + e.getMessage());
        }
        return allData;
    }

    /**
     * Get number of rows in sheet (excluding header)
     * @param sheetName Name of the sheet
     * @return Number of data rows
     */
    public int getRowCount(String sheetName) {
        try {
            Sheet sheet = getSheet(sheetName);
            return sheet.getLastRowNum(); // Returns number of rows excluding header
        } catch (Exception e) {
            System.out.println("Error getting row count: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Get number of columns in sheet
     * @param sheetName Name of the sheet
     * @return Number of columns
     */
    public int getColumnCount(String sheetName) {
        try {
            Sheet sheet = getSheet(sheetName);
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                return 0;
            }
            return headerRow.getLastCellNum();
        } catch (Exception e) {
            System.out.println("Error getting column count: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Get all sheet names in workbook
     * @return List of sheet names
     */
    public List<String> getAllSheetNames() {
        List<String> sheetNames = new ArrayList<>();
        try {
            int sheetCount = workbook.getNumberOfSheets();
            for (int i = 0; i < sheetCount; i++) {
                sheetNames.add(workbook.getSheetAt(i).getSheetName());
            }
        } catch (Exception e) {
            System.out.println("Error getting sheet names: " + e.getMessage());
        }
        return sheetNames;
    }

    /**
     * Close the workbook
     * @throws IOException if workbook cannot be closed
     */
    public void closeWorkbook() throws IOException {
        try {
            if (workbook != null) {
                workbook.close();
            }
        } catch (IOException e) {
            System.out.println("Error closing workbook: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Print sheet data for debugging
     * @param sheetName Name of the sheet
     */
    public void printSheetData(String sheetName) {
        try {
            List<String> headers = new ArrayList<>();
            List<Map<String, String>> data = getAllData(sheetName);

            if (data.isEmpty()) {
                System.out.println("No data found in sheet: " + sheetName);
                return;
            }

            // Print headers
            headers.addAll(data.get(0).keySet());
            System.out.println("\nSheet: " + sheetName);
            System.out.println("Headers: " + headers);

            // Print data
            for (int i = 0; i < data.size(); i++) {
                System.out.println("Row " + i + ": " + data.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error printing sheet data: " + e.getMessage());
        }
    }
}

