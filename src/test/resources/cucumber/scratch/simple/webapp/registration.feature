Feature: Registration
  A a user I would like to register a new account so that I can use the signed in functionality on the web application.

  Scenario: Can register a new account.
    Given I am a new user
    When I register a new account
    Then the registration should have succeeded
    And I can navigate to my profile