@users
Feature:
  In order to ensure all users can login
  No matter what strategy was originally used to encrypt their password
  As a registered user
  I should be able to login

  Scenario: Using Devise's default encryption strategy
    Given the following activated users exist
      | login | password |
      | user1 | password |
    When I am on the homepage
      And I fill in "User name or email:" with "user1"
      And I fill in "Password:" with "password"
      And I press "Log In"
    Then I should see "Successfully logged in."
      And I should see "Hi, user1!"

  Scenario: Using Authlogic's BCrypt encryption
    Given the following users exist with BCrypt encrypted passwords
      | login | password |
      | user1 | password |
    When I am on the homepage
      And I fill in "User name or email:" with "user1"
      And I fill in "Password:" with "password"
      And I press "Log In"
    Then I should see "Successfully logged in."
      And I should see "Hi, user1!"

  Scenario: Using Authlogic's SHA-512 encryption
    Given the following users exist with SHA-512 encrypted passwords
      | login | password |
      | user1 | password |
    When I am on the homepage
      And I fill in "User name or email:" with "user1"
      And I fill in "Password:" with "password"
      And I press "Log In"
    Then I should see "Successfully logged in."
      And I should see "Hi, user1!"
