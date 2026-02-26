Feature: Key Board Validation

  Scenario Outline: Keyboard validation

    Given Lanuch the URL "<URL>"
    When Verify the keyboard event
    Examples:
      | URL |
      |https://demoqa.com/text-box|