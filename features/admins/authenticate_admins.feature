@admin
Feature: Authenticate Admin Users

  Scenario: Admin cannot log in as an ordinary user.
  Given the following admin exists
    | login | password      |
    | Zooey | adminpassword |
  When I go to the home page
    And I fill in "User name or email" with "Zooey"
    And I fill in "Password" with "adminpassword"
    And I press "Log In"
  Then I should see "The password or user name you entered doesn't match our records"

  Scenario: Ordinary user cannot log in or reset password as admin.
  Given the following activated user exists
    | login       | password      |
    | dizmo       | wrangulator   |
  When I go to the admin login page
    And I fill in "Admin user name" with "dizmo"
    And I fill in "Admin password" with "wrangulator"
    And I press "Log In as Admin"
  Then I should not see "Successfully logged in"
    And I should see "The password or admin user name you entered doesn't match our records."
  When I am logged in as "dizmo" with password "wrangulator"
    And I go to the new admin password page
  Then I should be on the homepage
    And I should see "Please log out of your user account first!"
  When I go to the edit admin password page
  Then I should be on the homepage
    And I should see "Please log out of your user account first!"

  Scenario: Admin gets email with password reset link on account creation.
  Given the following admin exists
    | login | email             |
    | admin | admin@example.com |
  Then 1 email should be delivered to admin@example.com
  When I follow "follow this link to set your password" in the email
  Then I should see "Set My Admin Password"
  When I fill in "New password" with "newpassword"
    And I fill in "Confirm new password" with "newpassword"
    And I press "Set Admin Password"
  Then I should see "Your password has been changed successfully. You are now signed in."
    And I should see "Hi, admin!"

  Scenario: Set password link expires.
  Given the following admin exists
    | login | password     | email             |
    | admin | testpassword | admin@example.com |
  Then 1 email should be delivered to "admin@example.com"
  When it is past the admin password reset token's expiration date
    And I follow "follow this link to set your password" in the email
  Then I should see "Set My Admin Password"
  When I fill in "New password" with "newpassword"
    And I fill in "Confirm new password" with "newpassword"
    And I press "Set Admin Password"
  Then I should see "Reset password token has expired, please request a new one"

  Scenario: Admin can log in.
  Given I have no users
    And the following admin exists
      | login | password      |
      | Zooey | adminpassword |
    And I have loaded the "roles" fixture
  When I go to the admin login page
    And I fill in "Admin user name" with "Zooey"
    And I fill in "Admin password" with "adminpassword"
    And I press "Log In as Admin"
  Then I should see "Successfully logged in"

  Scenario: Admin user name is case insensitive.
  Given the following admin exists
    | login       | password      |
    | TheMadAdmin | adminpassword |
  When I go to the admin login page
    And I fill in "Admin user name" with "themadadmin"
    And I fill in "Admin password" with "adminpassword"
    And I press "Log In as Admin"
  Then I should see "Successfully logged in"

  Scenario: Admin cannot log in with wrong password.
  Given the following admin exists
    | login | password      |
    | Zooey | adminpassword |
  When I go to the admin login page
    And I fill in "Admin user name" with "Zooey"
    And I fill in "Admin password" with "wrongpassword"
    And I press "Log In"
  Then I should see "The password or user name you entered doesn't match our records."

  Scenario: Admin resets password.
  Given the following admin exists
    | login | password     | email             |
    | admin | testpassword | admin@example.com |
    And all emails have been delivered
  When I go to the admin login page
    And I follow "Forgot admin password?"
  Then I should see "Forgotten your admin password?"
  When I fill in "Admin user name" with "admin"
    And I press "Reset Admin Password"
  Then I should see "Check your email for instructions on how to reset your password."
    And 1 email should be delivered to "admin@example.com"
  When I follow "Change my password" in the email
  Then I should see "Set My Admin Password"
  When I fill in "New password" with "newpassword"
    And I fill in "Confirm new password" with "newpassword"
    And I press "Set Admin Password"
  Then I should see "Your password has been changed successfully. You are now signed in."
    And I should see "Hi, admin!"

  Scenario: Reset password link expires.
  Given the following admin exists
    | login | password     | email             |
    | admin | testpassword | admin@example.com |
    And all emails have been delivered
  When I go to the admin login page
    And I follow "Forgot admin password?"
  Then I should see "Forgotten your admin password?"
  When I fill in "Admin user name" with "admin"
    And I press "Reset Admin Password"
  Then I should see "Check your email for instructions on how to reset your password."
    And 1 email should be delivered to "admin@example.com"
  When it is past the admin password reset token's expiration date
    And I follow "Change my password" in the email
  Then I should see "Set My Admin Password"
  When I fill in "New password" with "newpassword"
    And I fill in "Confirm new password" with "newpassword"
    And I press "Set Admin Password"
  Then I should see "Reset password token has expired, please request a new one"

  Scenario: Locked admin cannot sign in.
  Given the admin "admin" is locked
  When I go to the admin login page
    And I fill in "Admin user name" with "admin"
    And I fill in "Admin password" with "adminpassword"
    And I press "Log In as Admin"
  Then I should see "Your account is locked."
    And I should not see "Hi, admin!"

  Scenario: Locked admin is not automatically logged in after password change.
  Given the admin "admin" is locked
    And all emails have been delivered
    And I am on the admin login page
  When I follow "Forgot admin password?"
    And I fill in "Admin user name" with "admin"
    And I press "Reset Admin Password"
  Then I should see "Check your email for instructions on how to reset your password."
    And 1 email should be delivered
  When I follow "Change my password" in the email
  Then I should see "Set My Admin Password"
  When I fill in "New password" with "newpassword"
    And I fill in "Confirm new password" with "newpassword"
    And I press "Set Admin Password"
  Then I should see "Your password has been changed successfully. Your account is locked."
  When the admin "admin" is unlocked
    And I fill in "Admin user name" with "admin"
    And I fill in "Admin password" with "newpassword"
    And I press "Log In as Admin"
  Then I should see "Successfully logged in."
    And I should see "Hi, admin!"
