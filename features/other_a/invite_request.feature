@admin
Feature: Invite requests

  Scenario: Request an invite for a friend

    Given invitations are required
      And I am logged in as "user1"
    When I try to invite a friend from my user page
      And I follow "Request invitations"
    When I fill in "How many invitations would you like? (max 10)" with "3"
      And I fill in "Please specify why you'd like them:" with "I want them for a friend"
      And I press "Send Request"
    Then I should see a create confirmation message

  Scenario: Requests are not instantly granted

    Given invitations are required
      And I am logged in as "user1"
      And I request some invites
    When I follow "Invitations"
      Then I should see "Sorry, you have no unsent invitations right now."

  Scenario: Admin sees the request

    Given invitations are required
      And I am logged in as "user1"
      And I request some invites
    When I view requests as an admin
    Then I should see "user1"
      And the "requests[user1]" field should contain "3"
      And I should see "I want them for a friend"

  Scenario: Admin can refuse request

    Given invitations are required
      And I am logged in as "user1"
      And I request some invites
    When I view requests as an admin
      And I fill in "requests[user1]" with "0"
      And I press "Update"
    Then I should see "Requests were successfully updated."
      And I should not see "user1"

  Scenario: Admin can grant request

    Given invitations are required
      And I am logged in as "user1"
      And I request some invites
    When I view requests as an admin
      And I fill in "requests[user1]" with "2"
      And I press "Update"
    Then I should see "Requests were successfully updated."

  Scenario: User is granted invites

    Given invitations are required
      And I am logged in as "user1"
      And I request some invites
      And an admin grants the request
    When I try to invite a friend from my user page
    Then I should see "Invite a friend"
      And I should not see "Sorry, you have no unsent invitations right now."
      And I should see "You have 2 open invitations and 0 that have been sent but not yet used."

  Scenario: User can send out invites they have been granted, and the recipient can sign up

    Given invitations are required
      And I am logged in as "user1"
      And I request some invites
      And an admin grants the request
      And I try to invite a friend from my user page
    When all emails have been delivered
      And I fill in "Email address" with "test@archiveofourown.org"
      And I press "Send Invitation"
    Then 1 email should be delivered to test@archiveofourown.org
      And the email should contain "has invited you to join the Archive of Our Own!"
      And the email should contain "If you do not receive this email after 48 hours"
      And the email should contain "With an account, you can post fanworks"

    Given I am a visitor
    When I follow "follow this link to sign up" in the email
      And I fill in the sign up form with valid data
      And I fill in the following:
        | user_registration_login                  | user2     |
        | user_registration_password               | password1 |
        | user_registration_password_confirmation  | password1 |
      And I press "Create Account"
    Then I should see "You should soon receive a confirmation email at the address you gave us"
      And I should see how long I have to activate my account
      And I should see "If you haven't received this email within 24 hours"

  Scenario: Banned users cannot access their invitations page

    Given I am logged in as a banned user
    When I go to my invitations page
    Then I should be on my user page
    And I should see "Your account has been banned."

  Scenario:  A user can manage their invitations

    Given I am logged in as "user1"
      And "user1" has "5" invitations
    When I go to my user page
     And I follow "Invitations"
     And I follow "Manage Invitations"
    Then I should see "Unsent (5)"
    When I follow "Unsent (5)"
    Then I should see "Unsent (5)"
    When I follow the link for "user1" first invite
    Then I should see "Enter an email address"
    When I fill in "invitation[invitee_email]" with "user6@example.org"
      And I press "Update Invitation"
    Then I should see "Invitation was successfully sent."

  Scenario: An admin can get to a user's invitations page
    Given I am logged in as a "support" admin
      And the user "steven" exists and is activated
    When I go to the abuse administration page for "steven"
      And I follow "Add User Invitations"
    Then I should be on steven's invitations page

  Scenario: An admin can get to a user's manage invitations page
    Given I am logged in as a "support" admin
      And the user "steven" exists and is activated
    When I go to the abuse administration page for "steven"
      And I follow "Manage User Invitations"
    Then I should be on steven's manage invitations page

  Scenario: An admin can create a user's invitations
    Given I am logged in as a "support" admin
      And the user "steven" exists and is activated
    When I go to steven's invitations page
    Then I should see "Create more invitations for this user"
    When I fill in "invitation[number_of_invites]" with "4"
     And press "Create"
    Then I should see "Invitations were successfully created."

  Scenario: An admin can delete a user's invitations
    Given the user "user1" exists and is activated
      And "user1" has "5" invitations
      And I am logged in as a "support" admin
    When I follow "Invite New Users"
      And I fill in "invitation[user_name]" with "user1"
      And I press "Go"
    Then I should see "Token"
    When I follow "Delete"
    Then I should see "Invitation successfully destroyed"
      And "user1" should have "4" invitations
