Feature: Create tweet

  Background: Validate that I can send and then read a tweet

  Scenario: Send and then read Tweet

    Given I am using user sfinneganauto
    And I send a tweet with a timestamp
    When I read the latest text
    Then the timestamp is from today
    Then I open website
