@collection

  Feature: Collection

  Scenario: A collection owner can't remove the owner from a collection
  Given I have the collection "Such a nice collection"
    And I am logged in as the owner of "Such a nice collection"
  When I am on the "Such a nice collection" participants page
    And I press "Remove"
  Then I should see "You can't remove the only owner!"

  Scenario: A collection owner can invite, update, and remove a collection member
  Given a user exists with login: "sam"
    And I have the collection "Such a nice collection"
    And I am logged in as the owner of "Such a nice collection"
  When I am on the "Such a nice collection" participants page
    And I fill in "participants_to_invite" with "sam"
    And I press "Submit"
  Then I should see "New members invited: sam"
  When I select "Owner" from "sam_role"
     And I submit with the 5th button
  Then I should see "Updated sam."
  When I submit with the 6th button
  Then I should see "Removed sam from collection."

  Scenario: Owner can't invite a nonexistent user to the collection
  Given I have the collection "Such a nice collection"
    And I am logged in as the owner of "Such a nice collection"
  When I am on the "Such a nice collection" participants page
    And I fill in "participants_to_invite" with "sam"
    And I press "Submit"
  Then I should see "We couldn't find anyone new by that name to add."

  Scenario: Collection owner can't invite a banned user to a collection
  Given a user exists with login: "sam"
    And user "sam" is banned
    And I have the collection "Such a nice collection"
    And I am logged in as the owner of "Such a nice collection"
  When I am on the "Such a nice collection" participants page
    And I fill in "participants_to_invite" with "sam"
    And I press "Submit"
  Then I should see "sam is currently banned and cannot participate in challenges."

  Scenario: A user can ask to join a closed collection
  Given I have a moderated closed collection "Such a nice collection"
    And I am logged in as "sam"
  When I go to "Such a nice collection" collection's page
    And I follow "Join"
  Then I should see "You have applied to join Such a nice collection"

  Scenario: A collection owner can preapprove a user to join a closed collection
  Given I have a moderated closed collection "Such a nice collection"
    And I am in sam's browser
    And I am logged in as "sam"
  When I go to "Such a nice collection" collection's page
  When I am in the moderator's browser
    And I am logged in as the owner of "Such a nice collection"
    And I am on the "Such a nice collection" participants page
    And I fill in "participants_to_invite" with "sam"
    And I press "Submit"
  Then I should see "New members invited: sam"
  When I select "Invited" from "sam_role"
    And I submit with the 5th button
  Then I should see "Updated sam."
  When I am in sam's browser
    And I follow "Join"
  Then I should see "You are now a member of Such a nice collection"
  When I am in the default browser
