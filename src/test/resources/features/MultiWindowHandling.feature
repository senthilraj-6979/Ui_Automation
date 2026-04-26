Feature: Window Handling - Open Multiple Windows

  Scenario: Open Google and then open Amtrak in new window
    Given User launches Google homepage
    Then User opens Amtrak in a new window
    Then User verifies both windows are open
    Then User switches back to Google window
    Then User closes Amtrak window

