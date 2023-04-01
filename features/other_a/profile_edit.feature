@users
Feature: Edit profile
  In order to have a presence on the archive
  As a humble user
  I want to fill out and edit my profile

Background:
  Given the following activated user exists
    | login    | password   | email  	    |
    | editname | password   | bar@ao3.org |
    And I am logged in as "editname"
    And I want to edit my profile

Scenario: Add details

  When I fill in the details of my profile
  Then I should see "Your profile has been successfully updated"
    And 0 emails should be delivered

Scenario: Change details

  When I change the details in my profile
  Then I should see "Your profile has been successfully updated"
    And 0 emails should be delivered

Scenario: Remove details

  When I remove details from my profile
  Then I should see "Your profile has been successfully updated"
    And 0 emails should be delivered

Scenario: Change details as an admin

  Given I am logged in as a "policy_and_abuse" admin
    And an abuse ticket ID exists
  When I go to editname profile page
    And I follow "Edit Profile"
    And I fill in "About Me" with "is it merely thy habit, to talk to dolls?"
    And I fill in "Ticket ID" with "fine"
    And I press "Update"
  Then I should see "Ticket ID is not a number"
    And the field labeled "Ticket ID" should contain "fine"
  When I fill in "Ticket ID" with "480000"
    And I press "Update"
  Then I should see "Your profile has been successfully updated"
    And I should see "is it merely thy habit, to talk to dolls?"
  When I visit the last activities item
  Then I should see "edit profile"
    And I should see a link "Ticket #480000"

  # Skip logging admin activity if no change was actually made.
  When I go to editname profile page
    And I follow "Edit Profile"
    And I fill in "Ticket ID" with "480000"
    And I press "Update"
  Then I should see "Your profile has been successfully updated"
  When I go to the admin-activities page
  Then I should see 1 admin activity log entry

Scenario: Changing email address requires reauthenticating

  When I follow "Change Email"
    And I fill in "New Email" with "blah@a.com"
    And I fill in "Confirm New Email" with "blah@a.com"
    And I press "Change Email"
  Then I should see "You must enter your password"
    And 0 emails should be delivered

Scenario: Changing email address - entering an invalid email address

  When I enter an invalid email
  Then I should see "Email does not seem to be a valid address"
    And 0 emails should be delivered

Scenario: Changing email address - case-insensitive confirmation

  When I follow "Change Email"
    And I fill in "New Email" with "foo@example.com"
    And I fill in "Confirm New Email" with "FoO@example.com"
    And I fill in "Password" with "password"
    And I press "Change Email"
  Then I should see "Your email has been successfully updated"
    And 1 email should be delivered to "bar@ao3.org"
    And all emails have been delivered
    And the email should contain "the email associated with your account has been changed to"
    And the email should contain "foo@example.com"
    And the email should not contain "translation missing"
  When I change my preferences to display my email address
  Then I should see "My email address: foo@example.com"

Scenario: Changing email address - entering an incorrect password

  When I enter an incorrect password
  Then I should see "Your password was incorrect"
    And 0 emails should be delivered

Scenario: Changing email address - entering non-matching new email addresses

  When I enter non-matching emails
  Then I should see "Email addresses don't match!"
    And 0 emails should be delivered
    And I should see "bar@ao3.org"

Scenario: Changing email address and viewing

  When I change my email
  Then I should see "Your email has been successfully updated"
    And 1 email should be delivered to "bar@ao3.org"
    And all emails have been delivered
    And the email should contain "the email associated with your account has been changed to"
    And the email should contain "valid2@archiveofourown.org"
    And the email should not contain "translation missing"
  When I change my preferences to display my email address
  Then I should see "My email address: valid2@archiveofourown.org"

Scenario: Changing email address after requesting password reset

  When I am logged out
    And I follow "Forgot password?"
    And I fill in "Email address or user name" with "editname"
    And I press "Reset Password"
  Then 1 email should be delivered to "bar@ao3.org"
  When all emails have been delivered
    And I am logged in as "editname"
    And I want to edit my profile
    And I change my email
  Then I should see "Your email has been successfully updated"
    And 1 email should be delivered to "bar@ao3.org"
    And the email should contain "the email associated with your account has been changed to"
    And the email should contain "valid2@archiveofourown.org"
    And the email should not contain "translation missing"
  When I change my preferences to display my email address
  Then I should see "My email address: valid2@archiveofourown.org"

Scenario: Changing email address -- can't be the same as another user's

  When I enter a duplicate email
  Then I should see "Email has already been taken"
    And 0 emails should be delivered
    And I should not see "Email addresses don't match!"
    And I should not see "foo@ao3.org"
    And I should see "bar@ao3.org"

Scenario: Date of birth - under age

  When I enter a birthdate that shows I am under age
  Then I should see "You must be over 13"

Scenario: Entering date of birth and displaying

  When I fill in my date of birth
  Then I should see "Your profile has been successfully updated"
  When I change my preferences to display my date of birth
  Then I should see "My birthday: 1980-11-30"
    And 0 emails should be delivered

Scenario: Change password - mistake in typing old password

  When I make a mistake typing my old password
  Then I should see "Your old password was incorrect"

Scenario: Change password - mistake in typing new password confirmation

  When I make a typing mistake confirming my new password
  Then I should see "Password confirmation doesn't match new password."

Scenario: Change password

  When I change my password
  Then I should see "Your password has been changed. To protect your account, you have been logged out of all active sessions. Please log in with your new password."
    And 0 emails should be delivered
