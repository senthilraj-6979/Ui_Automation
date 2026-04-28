Feature: Windows TabS witching

  @WindowTabSwitching

  Scenario Outline: Windows TabS witching
    Given Lanuch the URL "<URL>"
    When Enter search text "Automation Testing"
    Then Click on search button
    Then Switch to new window "<NEWURL>"
    Then User click on AcceptAll cookie button
    Then User click one-way option
    Then Switch to old window
    Examples:
      |URL                   |NEWURL|
      |https://www.google.com|https://amtrak.com/home.html|

