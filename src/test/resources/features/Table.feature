@Table @Regression
Feature: Table Handling

  Scenario: Table Handling Scenarios
    Given User launches the URL "https://demoqa.com/webtables"
    When Click on the Add button
    Then Verify "Registration Form" displayed
    And User enters first name "test"
    And User enters last name "user"
    And User enters email "test@gmail.com"
    And User enters age "30"
    And User enters salary "50000"
    And User enters department "IT"
    And Click on the Submit button
    And Verify "test" added to the table
    And Click on the Delete button for "Alden"