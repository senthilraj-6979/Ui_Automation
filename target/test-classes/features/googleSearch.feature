Feature: To validate mouse action class

  @GoogleSearch

  Scenario Outline: Mouse over action class
    Given Lanuch the URL "<URL>"
    When Enter search text "Automation Testing"
    Then Click on search button
    Examples:
    |URL|
    |https://www.google.com|

