Feature: Sign Up for a new account
  In order to add works to the Archive.
  As an unregistered user.
  I want to be able to create a new account.

  Background:
    Given account creation is enabled
      And account creation requires an invitation
      And I am a visitor
      And I use an invitation to sign up

  Scenario Outline: The user should see validation errors when signing up with invalid data.
    When I fill in the sign up form with valid data
      And I fill in "<field>" with "<value>"
      And I press "Create Account"
    Then I should see "<error>"
      And I should not see "Almost Done!"
    Examples:
      | field                      | value          | error                                           |
      | user_registration_login                 | xx             | User name is too short (minimum is 3 characters)|
      | user_registration_login                 | 87151d8ae964d55515cb986d40394f79ca5c8329c07a8e59f2f783cbfbe401f69a780f27277275b7b2 | User name is too long (maximum is 40 characters)    |
      | user_registration_password              | pass           | Password is too short (minimum is 6 characters) |
      | user_registration_password              | 87151d8ae964d55515cb986d40394f79ca5c8329c07a8e59f2f783cbfbe401f69a780f27277275b7b2 | Password is too long (maximum is 40 characters)    |
      | user_registration_password_confirmation | password2      | Password confirmation doesn't match             |
      | user_registration_email                 |                | Email does not seem to be a valid address.      |
      | user_registration_email                 | fake@fake@fake | Email does not seem to be a valid address       |

  Scenario Outline: The user should see validation errors when signing up without filling in required fields.
    When I press "Create Account"
    Then I should see "<error>"
      And I should not see "Almost Done!"
    Examples:
      | field                 | error                                                               |
      | user_registration_age_over_13      | Sorry, you have to be over 13!                                      |
      | user_registration_terms_of_service | Sorry, you need to accept the Terms of Service in order to sign up. |

  Scenario: The user should be able to sign up after fixing form errors.
    When I fill in the sign up form with valid data
      And I fill in "Valid email" with "lyingrobot@example.com"
      And I uncheck "Yes, I have read the Terms of Service and agree to them."
      And I press "Create Account"
    Then I should see "Sorry, you need to accept the Terms of Service in order to sign up."
      And I should not see "Sorry, you have to be over 13!"
      # Email should be what the user filled in, not the invitee email on the invitation
      And I should see "lyingrobot@example.com" in the "Valid email" input

    When I check "Yes, I have read the Terms of Service and agree to them."
      And I fill in "Password" with "password"
      And I fill in "Confirm password" with "password"
      And all emails have been delivered
      And I press "Create Account"
    Then I should see "Almost Done!"
      And 1 email should be delivered to "lyingrobot@example.com"
      And I should get a new user activation email
      And a new user account should exist

  Scenario: The user should not be able to sign up with a login that is already in use
    Given the following users exist
      | login | password |
      | user1 | password |
    When I fill in the sign up form with valid data
      And I fill in "user_registration_login" with "user1"
      And I press "Create Account"
    Then I should see "User name has already been taken"
      And I should not see "Almost Done!"

  Scenario: The user should not be able to sign up with a login that is already in use, no matter the case
    Given the following users exist
      | login | password |
      | user1 | password |
    When I fill in the sign up form with valid data
      And I fill in "user_registration_login" with "USER1"
      And I press "Create Account"
    Then I should see "User name has already been taken"
      And I should not see "Almost Done!"

  Scenario: The user should be able to create a new account with a valid email and password
    When I fill in the sign up form with valid data
      And all emails have been delivered
      And I press "Create Account"
    Then I should see "Almost Done!"
      And I should get a new user activation email
      And a new user account should exist
