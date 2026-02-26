Feature: To validate Train Status

  Scenario Outline: Validate Train Station
    Given Lanuch the amtrak URL "<URL>"
    When User click on AcceptAll cookie button
    Then User clicks facebook icon
    Then User navigate to facebook page
    Then User facebook loginId "<LoginID>"
    Then User closes facebook frame
    Then User closes facebook window and back to amtrak page
#    Then User clicks twitter icon
#    Then User navigate to twitter page
    Examples:
      | URL                          | LoginID            |
      | https://amtrak.com/home.html | testuser@gmail.com |