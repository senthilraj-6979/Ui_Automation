Feature: Brower Launch Validation

  Scenario Outline: Login to G-mail using Cucumber plugin

    Given Lanuch the URL "<URL>"
    When User select click and select the value from drop down
    Examples:
      | URL |
      |https://demo.guru99.com/test/newtours/register.php|

  Scenario Outline: Radio button feature

    Given Lanuch the URL "<URL>"
    When User select radio button from drop down
    Then User select checkboxes
    Then User zoom <Zoom> screen
    Examples:
      | URL |Zoom|
      |https://demo.guru99.com/test/radio.html|500|