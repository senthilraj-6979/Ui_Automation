Feature: Webtable handling

  Scenario Outline: Color andand image size validation
    Given Lanuch the URL "<URL>"
    Then Verify the total number of rows and columns
    Then Select the country "Algeria" by clicking check box

    Examples:
      |URL|
      |https://cosmocode.io/automation-practice-webtable/|