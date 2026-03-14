@Regression
Feature: To validate the color and image size


  Scenario Outline: Color andand image size validation
    Given Lanuch the URL "<URL>"
    When Click and verify the image size
    Then Verify the image color
    Then Verify the image location
    Then Verify the bgcolor matches "rgba(6, 106, 201, 0.1)"
    Examples:
    |URL|
    |https://www.tutorialspoint.com/index.htm|