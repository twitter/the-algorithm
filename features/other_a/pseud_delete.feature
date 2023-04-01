@users
Feature: Delete pseud.
  In order to tidy some mess
  As a humble user
  I want to delete a pseud

  Scenario: Delete pseud, have option to move works, delete works, or orphan works. Test if those choices work.
    Given I have loaded the fixtures
    When I am logged in as "sad_user_with_no_pseuds"
      And I am on sad_user_with_no_pseuds's pseuds page
    Then I should not see "Delete"

    When I am logged out
      And I am logged in as "testuser"
      And I am on testuser's pseuds page
      And I follow "delete_tester_pseud"
      And I press "Cancel"
    Then I should see "The pseud was not deleted."
    When I am on testuser's pseuds page
      And I follow "tester_pseud"
    Then I should see "fifth by testuser2"

    When I am on testuser's pseuds page
      And I follow "delete_tester_pseud"
      And I choose "Delete this bookmark"
      And I press "Submit"
    Then I should see "The pseud was successfully deleted."
    When I am on testuser's pseuds page
    Then I should not see "tester_pseud"

    When I follow "delete_testy"
      And I choose "Transfer this bookmark to the default pseud"
      And I press "Submit"
    Then I should see "The pseud was successfully deleted."
    When I am on testuser's pseuds page
      And I follow "testymctesty"
    Then I should see "fourth by testuser2"

  Scenario: Deleting a pseud shouldn't break gift exchange signups.
    Given I am logged in as "moderator"
      And I set up the collection "Exchange1"
      And I select "Gift Exchange" from "Type of challenge"
      And I press "Submit"
      And I check "Sign-up open?"
      And I press "Submit"
      And I am logged in as "test"
      And I add the pseud "testpseud"

    When I start signing up for "Exchange1"
      And I select "testpseud" from "challenge_signup_pseud_id"
      And I fill in "Description:" with "Antidisestablishmentarianism."
      And I press "Submit"
    Then I should see "Sign-up was successfully created."

    When I view the collection "Exchange1"
      And I follow "My Sign-up"
    Then I should see "Antidisestablishmentarianism."

    When I am on test's pseuds page
      And I follow "Delete"
    Then I should see "The pseud was successfully deleted."

    When I view the collection "Exchange1"
      And I follow "My Sign-up"
    Then I should see "Antidisestablishmentarianism."

  Scenario: Deleting a pseud shouldn't break prompt meme signups.
    Given I am logged in as "moderator"
      And I set up the collection "PromptsGalore"
      And I select "Prompt Meme" from "challenge_type"
      And I press "Submit"
      And I press "Submit"
      And I am logged in as "test"
      And I add the pseud "testpseud"

    When I start signing up for "PromptsGalore"
      And I select "testpseud" from "challenge_signup_pseud_id"
      And I fill in "Description:" with "Antidisestablishmentarianism."
      And I press "Submit"
    Then I should see "Sign-up was successfully created."

    When I view the collection "PromptsGalore"
      And I follow "My Prompts"
    Then I should see "Antidisestablishmentarianism."

    When I am on test's pseuds page
      And I follow "Delete"
    Then I should see "The pseud was successfully deleted."

    When I view the collection "PromptsGalore"
      And I follow "My Prompts"
    Then I should see "Antidisestablishmentarianism."

  Scenario: Deleting a pseud should preserve approved creatorships even if the default pseud has a request for the same work.
    Given I am logged in as "original_pseud"
      And I add the pseud "other_pseud"
      And I am logged in as "coauthor"
      And the user "original_pseud" allows co-creators

    When I set up the draft "Original Invited"
      And I try to invite the co-authors "original_pseud, other_pseud"
      And I press "Post"
    Then I should see "Work was successfully posted."

    When I am logged in as "original_pseud"
      And I go to my co-creator requests page
    Then I should see "Co-Creator Requests (2)"

    When I check the 1st checkbox with id matching "selected"
      And I press "Accept"
    Then I should see "You are now listed as a co-creator on Original Invited."
      And I should see "original_pseud" within ".creatorships"
      And I should see "Original Invited" within ".creatorships"
      And I should see "Co-Creator Requests (1)"

    When I view the work "Original Invited"
    Then I should see "Edit"
      And I should see "You've been invited to become a co-creator of this work."

    When I go to my pseuds page
      And I follow "Delete"
    Then I should see "The pseud was successfully deleted."

    When I view the work "Original Invited"
    Then I should see "Edit"
      But I should not see "You've been invited to become a co-creator of this work."

  Scenario: Deleting a pseud should preserve co-creator requests.
    Given I am logged in as "original_pseud"
      And I add the pseud "other_pseud"
      And I am logged in as "coauthor"
      And the user "original_pseud" allows co-creators

    When I set up the draft "Other Invited"
      And I try to invite the co-author "other_pseud"
      And I press "Post"
    Then I should see "Work was successfully posted."

    When I am logged in as "original_pseud"
      And I go to my co-creator requests page
    Then I should see "other_pseud" within ".creatorships"
      And I should see "Other Invited" within ".creatorships"
      And I should see "Co-Creator Requests (1)"

    When I go to my pseuds page
      And I follow "Delete"
    Then I should see "The pseud was successfully deleted."

    # We should still have a request for Other Invited:
    When I go to my co-creator requests page
    Then I should see "Other Invited" within ".creatorships"
      And I should see "original_pseud" within ".creatorships"
      And I should see "Co-Creator Requests (1)"
      And I should not see "other_pseud" within ".creatorships"
