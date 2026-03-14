@Regression
Feature: To perform end to end booking


  Scenario Outline: Perform end to end booking
    Given Lanuch the amtrak URL "<URL>"
    When User click on AcceptAll cookie button
    #Then User click guest rewards option
    #Then User click signInbutton
    Then User click one-way option
    Then User enters from station "<FROM>"
    Then User enters to station "<TO>"
    Then User clicks departure date
    Then User selects departure date
    Then Close calender clicking done button
    Then Click Find Trains

    Examples:
      |URL|FROM|TO|
      |https://amtrak.com/home.html|NYP|WAS|



  Scenario Outline: Perform end to end booking
    Given Lanuch the amtrak URL "<URL>"
    When User click on AcceptAll cookie button
    Then User clicks "<language>" language dropdown

    Examples:
      |URL|language|
      |https://amtrak.com/home.html|spanish|


  Scenario Outline: Select trip type
    Given Lanuch the amtrak URL "<URL>"
    When User click on AcceptAll cookie button
    Then Verify AGR popup is getting display
    Then User clicks tripType dropdown
    Then User selects "<tripType>" trip
    Then User clicks use points option
    Then User click Need Assistance checkbox

    Examples:
      | URL                          | tripType   |
      | https://amtrak.com/home.html | Multi-City |