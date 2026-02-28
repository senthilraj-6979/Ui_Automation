@Alert
Feature: Alert Handling

  Scenario: Handle simple alert and confirm alert and prompt alert
    Given User launches the URL "https://demoqa.com/alerts"
    When Click on the Alert option
    Then Click on the first alert button
    And Click Ok and accept the alert
    And Click on the confirm alert button
    And Click Ok and accept the confirm alert
    And Click on the prompt alert button
    And User enters "John Doe" in prompt and accepts

