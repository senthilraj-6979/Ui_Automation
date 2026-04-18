@PraticeForm @Regression
Feature: Form Fill up

  Scenario: Fill practice form with valid data
    Given Lanuch the URL "https://demoqa.com/automation-practice-form"
    When Verify "Practice Form" page loaded
    Then User enters first name "Senthil"
    And User enters last name "Raj"
    And Select gender as "Male"
    And User enters mobile number "9876541234"
    And User enters date of birth "23 February 2027"
   And User clicks upload picture "/Users/senthilraj/Documents/Carrum Health-insurance.pdf"
    And User selects hobby "Sports"
    And User selects state "Rajasthan"
    And User selects city "Jaipur"
