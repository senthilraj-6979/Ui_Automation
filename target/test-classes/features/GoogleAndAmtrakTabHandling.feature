Feature: Window/Tab Handling - Google and Amtrak

  @Regression
  @GoogleAndAmtrakTabs
  Scenario: Open Google then open Amtrak in a new tab on same window
    Given User launches Google in first tab
    When User opens Amtrak website in a new tab
    Then User verifies Google and Amtrak tabs are both open
    And User validates Google tab URL contains google domain
    And User validates Amtrak tab URL contains amtrak domain
    Then User closes the Amtrak tab and returns to Google
    And User confirms only Google tab remains open

