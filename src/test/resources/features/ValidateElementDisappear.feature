Feature: Element Disappear Validation

  Scenario Outline: Keyboard validation

    Given Lanuch the URL "<URL>"
    When Verify the element is disappeared
    Examples:
      | URL |
      |https://www.tutorialspoint.com/index.htm|