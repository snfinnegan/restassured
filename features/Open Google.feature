@UI
Feature: Visit Github

  Background: Validate that I can navigate to Github homepage and view logo

  Scenario: Go to Github

    Given I open http://www.github.com website
    Then I find the Why Github? menu item