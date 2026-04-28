@ExcelDataReading @DataDriven
Feature: Read Test Data - URL, First Name, Last Name from Excel

  Scenario: Display all test data from Excel

    Given User reads test data from "TestData.xlsx"
    And User reads row "0" from "TestData.xlsx"
#    Then Print retrieved test data
    And User navigates to the URL
#    Then Display all test data in table format
