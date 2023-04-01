@admin
Feature: Admin Actions to Manage Invitations
  In order to manage user account creation
  As an an admin
  I want to be able to require invitations for new users

  Scenario: Admin can set invite from queue number to a number greater than or equal to 1
    Given I am logged in as a "policy_and_abuse" admin
      And I go to the admin-settings page
      And I fill in "Number of people to invite from the queue at once" with "0"
      And I press "Update"
    Then I should see "Invite from queue number must be greater than 0. To disable invites, uncheck the appropriate setting."
    When I fill in "Number of people to invite from the queue at once" with "1"
      And I press "Update"
    Then I should not see "Invite from queue number must be greater than 0."

  Scenario: Account creation enabled, invitations required, users can request invitations, and the queue is enabled
    Given I am logged in as a super admin
      And I go to the admin-settings page
      And I check "Account creation enabled"
      And I check "Account creation requires invitation"
      And I check "Users can request invitations"
      And I check "Invite from queue enabled (People can add themselves to the queue and invitations are sent out automatically)"
      And I press "Update"
      And I log out
    When I go to the home page
    Then I should see "Get Invited!"
      And I should see "You can join by getting an invitation from another user or from our automated invite queue. All fans and fanworks are welcome!"
      And I should not see "Create An Account"
      And I should not see "Joining the Archive currently requires an invitation; however, we are not accepting new invitation requests at this time."

  Scenario: Account creation enabled, invitations required, users can request invitations, and the queue is disabled
    Given I am logged in as a super admin
      And I go to the admin-settings page
      And I check "Account creation enabled"
      And I check "Account creation requires invitation"
      And I check "Users can request invitations"
      And I uncheck "Invite from queue enabled (People can add themselves to the queue and invitations are sent out automatically)"
      And I press "Update"
      And I log out
    When I go to the home page
    Then I should not see "Get Invited!"
      And I should not see "You can join by getting an invitation from another user or from our automated invite queue. All fans and fanworks are welcome!"
      And I should not see "Create an Account!"
      And I should see "Joining the Archive currently requires an invitation; however, we are not accepting new invitation requests at this time."

  Scenario: Account creation enabled, invitations required, users cannot request invitations, and the queue is enabled
    Given I am logged in as a super admin
      And I go to the admin-settings page
      And I check "Account creation enabled"
      And I check "Account creation requires invitation"
      And I uncheck "Users can request invitations"
      And I check "Invite from queue enabled (People can add themselves to the queue and invitations are sent out automatically)"
      And I press "Update"
      And I log out
    When I go to the home page
    Then I should see "Get Invited!"
      And I should see "You can join by getting an invitation from our automated invite queue. All fans and fanworks are welcome!"
      And I should not see "Create an Account!"
    When I go to account creation page
    Then I should be on invite requests page
      And I should see "To create an account, you'll need an invitation. One option is to add your name to the automatic queue below."
      And I should see "Forgot password? Get an Invitation" within "div#small_login"
      And I should not see "Joining the Archive currently requires an invitation; however, we are not accepting new invitation requests at this time."

  Scenario: Account creation enabled, invitations not required, users can request invitations, and the queue is enabled
    Given I am logged in as a super admin
      And I go to the admin-settings page
      And I check "Account creation enabled"
      And I uncheck "Account creation requires invitation"
      And I check "Users can request invitations"
      And I check "Invite from queue enabled (People can add themselves to the queue and invitations are sent out automatically)"
      And I press "Update"
      And I log out
    When I go to the home page
    Then I should not see "Get Invited!"
      And I should not see "You can join by getting an invitation from another user or from our automated invite queue. All fans and fanworks are welcome!"
      And I should not see "Joining the Archive currently requires an invitation; however, we are not accepting new invitation requests at this time."
      And I should see "Create an Account!"

  Scenario: Account creation disabled, invitations required, users can request invitations, and the queue is enabled
    Given I am logged in as a super admin
      And I go to the admin-settings page
      And I uncheck "Account creation enabled"
      And I check "Account creation requires invitation"
      And I check "Users can request invitations"
      And I check "Invite from queue enabled (People can add themselves to the queue and invitations are sent out automatically)"
      And I press "Update"
      And I log out
    When I go to the home page
    Then I should see "Get Invited!"
      And I should see "You can join by getting an invitation from another user or from our automated invite queue. All fans and fanworks are welcome!"
      And I should not see "Create an Account!"
      And I should not see "Joining the Archive currently requires an invitation; however, we are not accepting new invitation requests at this time."

  Scenario: Account creation enabled, invitations required, users cannot request invitations, and the queue is disabled
    Given I am logged in as a super admin
      And I go to the admin-settings page
      And I check "Account creation enabled"
      And I check "Account creation requires invitation"
      And I uncheck "Users can request invitations"
      And I uncheck "Invite from queue enabled (People can add themselves to the queue and invitations are sent out automatically)"
      And I press "Update"
      And I log out
    When I go to the home page
    Then I should not see "Get Invited!"
      And I should not see "You can join by getting an invitation from another user or from our automated invite queue. All fans and fanworks are welcome!"
      And I should not see "Create an Account!"
      And I should see "Joining the Archive currently requires an invitation; however, we are not accepting new invitation requests at this time."
    When I go to account creation page
    Then I should be on the home page
      And I should see "Account creation currently requires an invitation. We are unable to give out additional invitations at present, but existing invitations can still be used to create an account."
      And I should see "Forgot password?" within "div#small_login"
      And I should not see "Get an Invitation" within "div#small_login"

  Scenario: Account creation enabled, invitations not required, users cannot request invitations, and the queue is enabled
    Given I am logged in as a super admin
      And I go to the admin-settings page
      And I check "Account creation enabled"
      And I uncheck "Account creation requires invitation"
      And I uncheck "Users can request invitations"
      And I check "Invite from queue enabled (People can add themselves to the queue and invitations are sent out automatically)"
      And I press "Update"
      And I log out
    When I go to the home page
    Then I should not see "Get Invited!"
      And I should not see "You can join by getting an invitation from another user or from our automated invite queue. All fans and fanworks are welcome!"
      And I should see "Create an Account!"
      And I should not see "Joining the Archive currently requires an invitation; however, we are not accepting new invitation requests at this time."

  Scenario: Account creation disabled, invitations not required, users can request invitations, and the queue is enabled
    Given I am logged in as a super admin
      And I go to the admin-settings page
      And I uncheck "Account creation enabled"
      And I uncheck "Account creation requires invitation"
      And I check "Users can request invitations"
      And I check "Invite from queue enabled (People can add themselves to the queue and invitations are sent out automatically)"
      And I press "Update"
      And I log out
    When I go to the home page
    Then I should not see "Get Invited!"
      And I should not see "You can join by getting an invitation from another user or from our automated invite queue. All fans and fanworks are welcome!"
      And I should not see "Create an Account!"

  Scenario: Account creation enabled, invitations not required, users can request invitations, and the queue is disabled
    Given I am logged in as a super admin
      And I go to the admin-settings page
      And I check "Account creation enabled"
      And I uncheck "Account creation requires invitation"
      And I check "Users can request invitations"
      And I uncheck "Invite from queue enabled (People can add themselves to the queue and invitations are sent out automatically)"
      And I press "Update"
      And I log out
    When I go to the home page
    Then I should not see "Get Invited!"
      And I should not see "You can join by getting an invitation from another user or from our automated invite queue. All fans and fanworks are welcome!"
      And I should not see "Joining the Archive currently requires an invitation; however, we are not accepting new invitation requests at this time."
      And I should see "Create an Account!"

  Scenario: Account creation disabled, invitations required, users cannot request invitations, and the queue is enabled
    Given I am logged in as a super admin
      And I go to the admin-settings page
      And I uncheck "Account creation enabled"
      And I check "Account creation requires invitation"
      And I uncheck "Users can request invitations"
      And I check "Invite from queue enabled (People can add themselves to the queue and invitations are sent out automatically)"
      And I press "Update"
      And I log out
    When I go to the home page
    Then I should see "Get Invited!"
      And I should see "You can join by getting an invitation from our automated invite queue. All fans and fanworks are welcome!"
      And I should not see "Create an Account!"
      And I should not see "Joining the Archive currently requires an invitation; however, we are not accepting new invitation requests at this time."

  Scenario: Account creation enabled, invitations not required, users cannot request invitations, and the queue is disabled
    Given I am logged in as a super admin
      And I go to the admin-settings page
      And I check "Account creation enabled"
      And I uncheck "Account creation requires invitation"
      And I uncheck "Users can request invitations"
      And I uncheck "Invite from queue enabled (People can add themselves to the queue and invitations are sent out automatically)"
      And I press "Update"
      And I log out
    When I go to the home page
    Then I should not see "Get Invited!"
      And I should not see "You can join by getting an invitation from another user or from our automated invite queue. All fans and fanworks are welcome!"
      And I should not see "Joining the Archive currently requires an invitation; however, we are not accepting new invitation requests at this time."
      And I should see "Create an Account!"
    When I go to account creation page
    Then I should be on account creation page
      And I should see "Create Account"

  Scenario: Account creation disabled, invitations required, users cannot request invitations, and the queue is disabled
    Given I am logged in as a super admin
      And I go to the admin-settings page
      And I uncheck "Account creation enabled"
      And I check "Account creation requires invitation"
      And I uncheck "Users can request invitations"
      And I uncheck "Invite from queue enabled (People can add themselves to the queue and invitations are sent out automatically)"
      And I press "Update"
      And I log out
    When I go to the home page
    Then I should not see "Get Invited!"
      And I should not see "You can join by getting an invitation from another user or from our automated invite queue. All fans and fanworks are welcome!"
      And I should not see "Create an Account!"
      And I should see "Joining the Archive currently requires an invitation; however, we are not accepting new invitation requests at this time."

  Scenario: Account creation disabled, invitations not required, users can request invitations, and the queue is disabled
    Given I am logged in as a super admin
      And I go to the admin-settings page
      And I uncheck "Account creation enabled"
      And I uncheck "Account creation requires invitation"
      And I check "Users can request invitations"
      And I uncheck "Invite from queue enabled (People can add themselves to the queue and invitations are sent out automatically)"
      And I press "Update"
      And I log out
    When I go to the home page
    Then I should not see "Get Invited!"
      And I should not see "You can join by getting an invitation from another user or from our automated invite queue. All fans and fanworks are welcome!"
      And I should not see "Create an Account!"
      And I should not see "Joining the Archive currently requires an invitation; however, we are not accepting new invitation requests at this time."

  Scenario: Account creation disabled, invitations not required, users cannot request invitations, and the queue is enabled
    Given I am logged in as a super admin
      And I go to the admin-settings page
      And I uncheck "Account creation enabled"
      And I uncheck "Account creation requires invitation"
      And I uncheck "Users can request invitations"
      And I check "Invite from queue enabled (People can add themselves to the queue and invitations are sent out automatically)"
      And I press "Update"
      And I log out
    When I go to the home page
    Then I should not see "Get Invited!"
      And I should not see "You can join by getting an invitation from another user or from our automated invite queue. All fans and fanworks are welcome!"
      And I should not see "Create an Account!"
    When I go to account creation page
    Then I should be on the home page
      And I should see "Account creation is suspended at the moment. Please check back with us later."
      And I should see "Forgot password? Get an Invitation" within "div#small_login"
      And I should not see "Joining the Archive currently requires an invitation; however, we are not accepting new invitation requests at this time."

  Scenario: Account creation disabled, invitations not required, users cannot request invitations, and the queue is disabled
    Given I am logged in as a super admin
      And I go to the admin-settings page
      And I uncheck "Account creation enabled"
      And I uncheck "Account creation requires invitation"
      And I uncheck "Users can request invitations"
      And I uncheck "Invite from queue enabled (People can add themselves to the queue and invitations are sent out automatically)"
      And I press "Update"
      And I log out
    When I go to the home page
    Then I should not see "Get Invited!"
      And I should not see "You can join by getting an invitation from another user or from our automated invite queue. All fans and fanworks are welcome!"
      And I should not see "Create an Account!"
      And I should not see "Joining the Archive currently requires an invitation; however, we are not accepting new invitation requests at this time."

  Scenario: An admin can send an invitation to a user via email
    Given I am logged in as an admin
      And all emails have been delivered
    When I follow "Invite New Users"
      And I fill in "invitation[invitee_email]" with "fred@bedrock.com"
      And I press "Invite user"
    Then I should see "An invitation was sent to fred@bedrock.com"
      And 1 email should be delivered

  Scenario: An admin can't create an invite without an email address.
    Given I am logged in as an admin
      And all emails have been delivered
    When I follow "Invite New Users"
      And I press "Invite user"
    Then I should see "Please enter an email address"
      And 0 email should be delivered

  Scenario: An admin can send an invitation to all existing users
    Given the following activated users exist
      | login | password   |
      | dax   | lotsaspots |
      | odo   | mybucket9  |
      And "dax" has "0" invitations
      And "odo" has "3" invitations
      And I am logged in as an admin
    When I follow "Invite New Users"
      And I fill in "Number of invitations" with "2"
      And I select "All" from "Users"
      And I press "Generate invitations"
    Then "dax" should have "2" invitations
      And "odo" should have "5" invitations

  Scenario: An admin can send invitations to only existing users who don't have unused invitations
    Given the following activated users exist
      | login  | password    |
      | dax    | lotsaspots  |
      | bashir | heytheredoc |
      And "dax" has "5" invitations
      And "bashir" has "0" invitations
      And I am logged in as an admin
    When I follow "Invite New Users"
      And I fill in "Number of invitations" with "2"
      And I select "With no unused invitations" from "Users"
      And I press "Generate invitations"
    Then "dax" should have "7" invitations
      And "bashir" should have "2" invitations

  Scenario: An admin can see the invitation of an existing user via name or token
    Given the user "dax" exists and is activated
      And "dax" has "2" invitations
      And I am logged in as an admin
    When I follow "Invite New Users"
      And I fill in "Enter a user name" with "dax"
      And I press "Go"
    Then I should see "copy and use"
    When I follow "Invite New Users"
      And I fill in "Enter an invite token" with "dax's" invite code
      And I press "Go"
    Then I should see "copy and use"

  Scenario: An admin can't find a invitation for a nonexistent user
    Given I am logged in as an admin
      And I follow "Invite New Users"
    When I fill in "Enter a user name" with "dax"
      And I press "Go"
    Then I should see "No results were found. Try another search"

  Scenario: An admin can invite people from the queue
    Given I am logged in as an admin
      And an invitation request for "fred@bedrock.com"
      And an invitation request for "barney@bedrock.com"
      And all emails have been delivered
      And I follow "Invite New Users"
    Then I should see "There are 2 requests in the queue."
    When I fill in "Number of people to invite" with "1"
      And press "Invite from queue"
    Then I should see "There are 1 requests in the queue."
      And I should see "1 person from the invite queue is being invited"
      And 1 email should be delivered to "fred@bedrock.com"

  Scenario: When an admin invites from the queue, the invite is marked as being from the admin
    Given I am logged in as a "support" admin
      And an invitation request for "test@example.com"
      And I follow "Invite New Users"
    When I fill in "Number of people to invite" with "1"
      And press "Invite from queue"
    Then I should see "1 person from the invite queue is being invited"
    When I press "Go"
      And I fill in "Enter all or part of an email address:" with "test@example.com"
      And I press "Go"
    Then I should see "Sender testadmin-support"

  Scenario: An admin can edit an invitation
    Given the user "dax" exists and is activated
      And "dax" has "2" invitations
      And I am logged in as a "support" admin
    When I follow "Invite New Users"
      And I fill in "Enter a user name" with "dax"
      And I press "Go"
    Then I should see "copy and use"
    When I follow "Invite New Users"
      And I fill in "Enter an invite token" with "dax's" invite code
      And I press "Go"
    Then I should see "copy and use"
    When I fill in "invitation_invitee_email" with "oldman@ds9.com"
      And I press "Update Invitation"
    Then I should see "oldman@ds9.com" in the "invitation_invitee_email" input

  Scenario: An admin can search the invitation queue, and search parameters are
  kept even if deleting without JavaScript
    Given I am logged in as a "policy_and_abuse" admin
      And an invitation request for "streamtv@example.com"
      And an invitation request for "livetv@example.com"
      And an invitation request for "clearstream@example.com"
      And an invitation request for "stre.a.mer@example.com"
      And an invitation request for "dreamer@example.com"
    When I am on the manage invite queue page
      And I fill in "query" with "stream"
      And I press "Search Queue"
    Then I should see "streamtv@example.com"
      And I should see "clearstream@example.com"
      And I should see "stre.a.mer@example.com"
      But I should not see "livetv@example.com"
      And I should not see "dreamer@example.com"
    When I press "Delete"
    Then the "query" field should contain "stream"
      And I should not see "dreamer@example.com"
      And I should not see "livetv@example.com"

  Scenario: The invitations in the queue are paginated correctly
    Given I am logged in as a "policy_and_abuse" admin
      And there are 2 invite requests per page
      And an invitation request for "andy@example.com"
      And an invitation request for "beatrice@example.com"
      And an invitation request for "carla@example.com"
      And an invitation request for "devon@example.com"
      And an invitation request for "eliot@example.com"
    When I am on the manage invite queue page
    Then the invite queue should list the following:
      | position | email                |
      | 1        | andy@example.com     |
      | 2        | beatrice@example.com |
    When I follow "Next" within ".pagination"
    Then the invite queue should list the following:
      | position | email             |
      | 3        | carla@example.com |
      | 4        | devon@example.com |
    When I follow "Next" within ".pagination"
    Then the invite queue should list the following:
      | position | email             |
      | 5        | eliot@example.com |

  Scenario: The positions in the queue shift when an invitation is sent out
    Given I am logged in as a "policy_and_abuse" admin
      And there are 2 invite requests per page
      And an invitation request for "andy@example.com"
      And an invitation request for "beatrice@example.com"
      And an invitation request for "carla@example.com"
      And an invitation request for "devon@example.com"
      And an invitation request for "eliot@example.com"
    When I follow "Invite New Users"
      And I fill in "Number of people to invite" with "2"
      And press "Invite from queue"
    Then I should see "2 people from the invite queue are being invited"
    When I am on the manage invite queue page
    Then the invite queue should list the following:
      | position | email             |
      | 1        | carla@example.com |
      | 2        | devon@example.com |
    When I follow "Next" within ".pagination"
    Then the invite queue should list the following:
      | position | email             |
      | 3        | eliot@example.com |

  Scenario: The invitations in the queue are numbered correctly when searching
    Given I am logged in as a "policy_and_abuse" admin
      And an invitation request for "andy-jones@example.com"
      And an invitation request for "beatrice@example.com"
      And an invitation request for "carla@example.com"
      And an invitation request for "devon@example.com"
      And an invitation request for "eliot-jones@example.com"
    When I am on the manage invite queue page
    Then the invite queue should list the following:
      | position | email                   |
      | 1        | andy-jones@example.com  |
      | 2        | beatrice@example.com    |
      | 3        | carla@example.com       |
      | 4        | devon@example.com       |
      | 5        | eliot-jones@example.com |
    When I fill in "query" with "jones"
      And I press "Search Queue"
    Then the invite queue should list the following:
      | position | email                   |
      | 1        | andy-jones@example.com  |
      | 5        | eliot-jones@example.com |
