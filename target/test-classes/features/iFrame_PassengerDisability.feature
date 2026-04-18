@iFrame @Regression
Feature: To perform iFramehanding for passenger disability
  Scenario: Perform frame handling functionality for passenger disability
    Given User launches the URL "https://aemtest.amtrak.com/home.html"
    When User click on AcceptAll cookie button
    Then User clicks on Passenger Disability link
    Then User switch to iFrame and switch to frame
    Then Verify heading of the page is Passenger with Disability or Assistance Needed?
    Then Close the popup
    Then Click Traveller dropdown
    Then Click and increment the number of passengers
    Then Click on trip type dropdown
    Then Click on "Round-Trip"
    Then Click on "One-Way"
    Then Click on "Multi-City"
    Then Click on "Group Travel"
#    Then Click on "Hotels & Cars"


