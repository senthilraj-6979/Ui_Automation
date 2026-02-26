Feature: Login Page validation

  Scenario: Login with correct credientials
    Given User is on login page
    When User enters username "trsenthilraj@gmail.com"
    And User enters password "test12345"
    And User clicks on Login Button
    Then User gets the title of the page
#    And Page title should be "My Account"