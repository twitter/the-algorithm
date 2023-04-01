@admin
Feature: Invite queue management

  Background:
    Given I have no users
    And the following users exist
      | login | password |
      | user1 | password |

  Scenario: Can turn queue off in Admin Settings and it displays as off

    Given I am logged in as a "policy_and_abuse" admin
      And I go to the admin-settings page
      And I uncheck "Invite from queue enabled (People can add themselves to the queue and invitations are sent out automatically)"
      And I press "Update"
    When I log out
      And I am on the homepage
    Then I should not see "Get an Invite"
      And I should see "Archive of Our Own"

  Scenario: Can turn queue on in Admin Settings and it displays as on

    Given I am logged in as a "policy_and_abuse" admin
      And account creation requires an invitation
      And I go to the admin-settings page
      And I check "Invite from queue enabled (People can add themselves to the queue and invitations are sent out automatically)"
      And I press "Update"
    When I log out
      And I am on the homepage
    Then I should see "Get an Invitation"
    When I follow "Get an Invitation"
    Then I should see "Request an invitation"

  Scenario: An admin can delete people from the queue

    Given an invitation request for "invitee@example.org"
      And I am logged in as a "policy_and_abuse" admin
    When I go to the manage invite queue page
      And I press "Delete"
    Then I should see "Request for invitee@example.org was removed from the queue."
      And I should be on the manage invite queue page

  Scenario: Visitors can join the queue and check status when invitations are required and the queue is enabled

    # join queue
    Given I am a visitor
      And account creation requires an invitation
      And the invitation queue is enabled
    When I am on the homepage
      And all emails have been delivered
      And I follow "Get an Invitation"
    Then I should see "We are sending out 10 invitations per day."
    When I fill in "invite_request_email" with "test@archiveofourown.org"
      And I press "Add me to the list"
    Then I should see "You've been added to our queue"

    # check your place in the queue - invalid address
    When I check how long "testttt@archiveofourown.org" will have to wait in the invite request queue
    Then I should see "Invitation Request Status"
      And I should see "If you can't find it, your invitation may have already been emailed to that address; please check your email spam folder as your spam filters may have placed it there."
      And I should not see "You are currently number"

    # check your place in the queue - correct address
    When I check how long "test@archiveofourown.org" will have to wait in the invite request queue
    Then I should see "Invitation Status for test@archiveofourown.org"
      And I should see "You are currently number 1 on our waiting list! At our current rate, you should receive an invitation on or around"

  Scenario: Can't add yourself to the queue when queue is off

    Given the invitation queue is disabled
    When I go to the invite_requests page
    Then I should not see "Request an invitation"
      And I should not see "invite_request_email"
      And I should see "New invitation requests are currently closed."
      And I should not see "Add me to the list"

  Scenario: Can still check status when queue is off

    Given the invitation queue is disabled
    When I go to the invite_requests page
      And I follow "check your position on the waiting list"
    Then I should see the page title "Invitation Request Status"
      And I should see "There are currently 0 people on the waiting list."
      And I should not see "We are currently sending out"

  Scenario: The queue sends out invites and user can create and activate an account

    Given account creation is enabled
      And the invitation queue is enabled
      And account creation requires an invitation
      And the invite_from_queue_at is yesterday
    When I am on the homepage
      And all emails have been delivered
      And I follow "Get an Invitation"
    When I fill in "invite_request_email" with "test@archiveofourown.org"
      And I press "Add me to the list"
      And the scheduled check_invite_queue job is run
    Then 1 email should be delivered to test@archiveofourown.org
    When I check how long "test@archiveofourown.org" will have to wait in the invite request queue
    Then I should see "Invitation Request Status"
      And I should see "If you can't find it, your invitation may have already been emailed to that address;"

    # invite can be used
    When I am logged in as an admin
      And I follow "Invitations"
      And I fill in "track_invitation_invitee_email" with "test@archiveofourown.org"
      And I press "Go"
    Then I should see "Sender queue"
    When I follow "copy and use"
    Then I should see "You are already logged in!"

    # user uses email invite
    Given I am a visitor
    # "You've" removed from test due to escaping on apostrophes
    Then the email should contain "been invited to join the Archive of Our Own"
      And the email should contain "fanart"
      And the email should contain "podfic"
      And the email should contain "If you do not receive this email after 48 hours"
      And the email should contain "With an account, you can post fanworks"

    When I follow "follow this link to sign up" in the email
      And I fill in the sign up form with valid data
      And I fill in the following:
        | user_registration_login                 | newuser                  |
        | user_registration_email                 | test@archiveofourown.org |
        | user_registration_password              | password1                |
        | user_registration_password_confirmation | password1                |
      And all emails have been delivered
    When I press "Create Account"
    Then I should see "Almost Done!"
    Then 1 email should be delivered
      And the email should contain "Welcome to the Archive of Our Own,"
      And the email should contain "newuser"
      And the email should contain "activate your account"
      And the email should not contain "translation missing"

    # user activates account
    When all emails have been delivered
      And I click the first link in the email
    Then I should be on the login page
      And I should see "Account activation complete! Please log in."

    When I am logged in as "newuser" with password "password1"
    Then I should see "Successfully logged in."

  Scenario: You can't request an invitation with an email address that is
  already attached to an account
    Given account creation requires an invitation
      And the invitation queue is enabled
      And the following activated users exist
      | login | password    | email            |
      | fred  | yabadabadoo | fred@bedrock.com |
    When I am on the homepage
      And I follow "Get an Invitation"
      And I fill in "invite_request_email" with "fred@bedrock.com"
      And I press "Add me to the list"
    Then I should see "Email is already being used by an account holder."
