Feature: To validate mouse action class

  @WindowTabSwitching

  Scenario Outline: Mouse over action class
    Given Lanuch the URL "<URL>"
    When Enter search text "Automation Testing"
    Then Click on search button
    Then Switch to new window "<NEWURL>"
    Examples:
      |URL                   |NEWURL|
      |https://www.google.com|https://amtrak.com/home.html|

