Feature: To validate Train Status

#  Scenario Outline: Validate Train Station
#    Given Lanuch the amtrak URL "<URL>"
#    When User click on AcceptAll cookie button
#    Then User clicks TrainStatus option
#    Then User enters FROM station "<FROM>" at TRAIN STATUS
#    Then User enters TO station "<TO>" at TRAIN STATUS
#    Then Select train status calender
#    Then Select train status date
#    Then Click Train Status button
#
#    Examples:
#      | URL                          | FROM | TO  |
#      | https://amtrak.com/home.html | WAS  | NYP |


  Scenario Outline: Validate Train Station
    Given Lanuch the amtrak URL "<URL>"
    When User click on AcceptAll cookie button
    Then User clicks TrainStatus option
    Then User clicks TrainNumber option
    Then Select TrainNumber option
    Then Enter TrainNumber "<TrainNumber>"
    Then Enter StationNumber "<StationNumber>"
    Then Click Train Status button
    Examples:
      | URL                          | FROM | TO  | TrainNumber |StationNumber|
      | https://amtrak.com/home.html | WAS  | NYP | 19          |WAS          |