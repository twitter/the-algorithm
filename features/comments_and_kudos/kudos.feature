Feature: Kudos
  In order to show appreciation
  As a reader
  I want to leave kudos

  Background:

    Given the following activated users exist
        | login          | email           |
        | myname1        | myname1@foo.com |
        | myname2        | myname2@foo.com |
        | myname3        | myname3@foo.com |
      And I am logged in as "myname1"
      And I post the work "Awesome Story"
      And I log out

  Scenario: Leaving kudos

    Given I am logged in as "myname2"
      And all emails have been delivered
      And I view the work "Awesome Story"
    Then I should not see "left kudos on this work"

    # Note: this step cannot be put into the steps file because of the heart character
    When I press "Kudos ♥"
    Then I should see "myname2 left kudos on this work!"
      # make sure no emails go out until notifications are sent
      And 0 emails should be delivered
    When kudos are sent
    Then 1 email should be delivered to "myname1@foo.com"
      And the email should contain "myname2"
      And the email should contain "left kudos"
      And the email should contain "."
      And all emails have been delivered
    When I press "Kudos ♥"
    Then I should see "You have already left kudos here. :)"
      And I should not see "myname2 and myname2 left kudos on this work!"

    When I am logged out
      And I view the work "Awesome Story"
      And I press "Kudos ♥"
    Then I should see "Thank you for leaving kudos!"
      And I should see "myname2 as well as 1 guest left kudos on this work!"
    When I press "Kudos ♥"
    Then I should see "You have already left kudos here. :)"
    When kudos are sent
    Then 1 email should be delivered to "myname1@foo.com"
      And the email should contain "A guest"
      And the email should contain "left kudos"
      And the email should contain "."

    When I am logged in as "myname3"
      And I view the work "Awesome Story"
      And I press "Kudos ♥"
    Then I should see "myname3 and myname2 as well as 1 guest left kudos on this work!"
    When I am logged in as "myname1"
      And I view the work "Awesome Story"
    Then I should not see "Kudos ♥"

    When I go to the work kudos page for "Awesome Story"
    Then I should see "2 Users Who Left Kudos on Awesome Story"
      And I should see "1 guest has also left kudos"
      And I should see "myname3 and myname2 left kudos on this work!"

  @javascript
  Scenario: Leaving kudos with JavaScript enabled

    # As a registered user
    When I am logged in as "myname2"
      And I view the work "Awesome Story"
      And I press "Kudos ♥"
    Then I should see "Thank you for leaving kudos!"
      And I should see "myname2 left kudos on this work!"
    When I press "Kudos ♥"
    Then I should see "You have already left kudos here. :)"
      And I should not see "myname2 and myname2 left kudos on this work!"

    # As another registered user
    When I am logged in as "myname3"
      And I view the work "Awesome Story"
      And I press "Kudos ♥"
    Then I should see "Thank you for leaving kudos!"
      And I should see "myname3 and myname2 left kudos on this work!"
    When I press "Kudos ♥"
    Then I should see "You have already left kudos here. :)"

    # As a guest user
    When I am logged out
      And I view the work "Awesome Story"
      And I press "Kudos ♥"
    Then I should see "Thank you for leaving kudos!"
      And I should see "myname3 and myname2 as well as 1 guest left kudos on this work!"
    When I press "Kudos ♥"
    Then I should see "You have already left kudos here. :)"

  Scenario: Leaving kudos on a multi-chapter work

    Given I am logged in as "myname1"
      And I post the chaptered work "Epic Saga"
      And a draft chapter is added to "Epic Saga"
    When I am logged in as "myname3"
      And I view the work "Epic Saga"
      And I press "Kudos ♥"
    Then I should see kudos on every chapter
    When I am logged in as "myname1"
      And I view the work "Epic Saga"
    Then I should see kudos on every chapter but the draft

  Scenario: Deleting user after leaving kudos should orphan them

    Given I am logged in as "myname3"
    When "myname3" creates the default pseud "foobar"
      And I view the work "Awesome Story"
      And I press "Kudos ♥"
    Then I should see "myname3 left kudos on this work!"
    When "myname3" deletes their account
      And I view the work "Awesome Story"
      And "AO3-2195" is fixed
    # Then I should see "1 guest left kudos on this work!"

  Scenario: Redirection when leaving kudos on a middle chapter, with default preferences

    Given the chaptered work setup
      And I am logged in as a random user
    When I view the work "BigBang"
      And I view the 2nd chapter
      And I press "Kudos ♥"
    Then I should see "Chapter 2" within "div#chapters"
      And I should not see "Chapter 1" within "div#chapters"

  Scenario: Redirection when leaving kudos in temporary "Entire Work" mode, with default preferences

    Given the chaptered work setup
      And I am logged in as a random user
    When I view the work "BigBang" in full mode
      And I press "Kudos ♥"
    Then I should see "Chapter 2" within "div#chapters"
      And I should see "Chapter 3" within "div#chapters"

  Scenario: Batched kudos email

    Given I am logged in as "myname1"
      And I post the work "Another Awesome Story"
      And I post the work "Meh Story"
      And I post the work "Okay Story"
      And all emails have been delivered
      And the kudos queue is cleared
      And I am logged in as "myname2"
      And I leave kudos on "Awesome Story"
      And I leave kudos on "Another Awesome Story"
      And I leave kudos on "Okay Story"
      And I am logged in as "someone_else"
      And I leave kudos on "Awesome Story"
      And I am logged out
      And I leave kudos on "Awesome Story"
      And I leave kudos on "Another Awesome Story"
      And I leave kudos on "Meh Story"
    When kudos are sent
    Then 1 email should be delivered to "myname1@foo.com"
      And the email should have "You've got kudos!" in the subject
      And the email should contain "myname2"
      And the email should contain "someone_else"
      And the email should contain "a guest"
      And the email should contain "A guest"
      And the email should contain "Awesome Story"
      And the email should contain "Another Awesome Story"
      And the email should contain "Meh Story"
      And the email should not contain "0 guests"
      And the email should not contain "translation missing"
