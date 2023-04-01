@collections @challenges @promptmemes
Feature: Prompt Meme Challenge
  In order to have an archive full of works
  As a humble user
  I want to create a prompt meme and post to it

  Background:
  Given I have Battle 12 prompt meme fully set up

  Scenario: Mod can delete whole sign-ups

  Given "myname1" has signed up for Battle 12 with combination A
  When I am logged in as "mod1"
    And I view prompts for "Battle 12"
    And I follow "Delete Sign-up"
  Then I should see "Challenge sign-up was deleted."

  Scenario: Mod can delete a prompt provided the user's sign-up has more than
  the minimum number required for the meme

  Given "myname1" has signed up for Battle 12 with one more prompt than required
  When I am logged in as "mod1"
    And I view prompts for "Battle 12"
    And I follow "Delete Prompt"
  Then I should see "Prompt was deleted."
    And I should not see "Delete Prompt"
    And I should see "Delete Sign-up"

  Scenario: As a co-moderator I can delete whole sign-ups

  Given I have added a co-moderator "mod2" to collection "Battle 12"
    And "myname1" has signed up for Battle 12 with combination A
  When I am logged in as "mod2"
    And I view prompts for "Battle 12"
    And I follow "Delete Sign-up"
  Then I should see "Challenge sign-up was deleted."

  Scenario: As a co-moderator I can delete prompts provided the user's sign-up
  has more than the minimum number required for the meme

  Given I have added a co-moderator "mod2" to collection "Battle 12"
    And "myname1" has signed up for Battle 12 with one more prompt than required
  When I am logged in as "mod2"
    And I view prompts for "Battle 12"
    And I follow "Delete Prompt"
  Then I should see "Prompt was deleted."
    And I should not see "Delete Prompt"
    And I should see "Delete Sign-up"

  Scenario: User can't delete prompt if they don't have more than the minimum
  number required by the meme

  Given I am logged in as "myname1"
    And I sign up for Battle 12 with combination C
  When I view prompts for "Battle 12"
  Then I should not see "Delete Prompt"
    And I should see "Edit Sign-up"

  Scenario: User can delete one prompt provided their sign-up has more than the
  minimum number required for the meme

  Given "myname1" has signed up for Battle 12 with one more prompt than required
  When I am logged in as "myname1"
    And I view prompts for "Battle 12"
    And I follow "Delete Prompt"
  Then I should see "Prompt was deleted."
    And I should see "Sign-up for myname1"
    And I should see "Delete Sign-up"
    And I should not see "Delete Prompt"

  Scenario: When user deletes signup, its prompts disappear from the collection

  Given I am logged in as "myname1"
    And I sign up for Battle 12 with combination A
  When I delete my signup for the prompt meme "Battle 12"
    And I view prompts for "Battle 12"
  Then I should not see "myname1" within "ul.index"

  Scenario: When user deletes sign-up, the sign-up disappears from their
  dashboard

  Given I am logged in as "myname1"
    And I sign up for Battle 12 with combination A
  When I delete my signup for the prompt meme "Battle 12"
    And I go to my signups page
  Then I should see "Sign-ups (0)"
    And I should not see "Battle 12"

  Scenario: When user deletes signup, the work stays part of the collection,
  but no longer has the "In response to a prompt by" note

  Given "myname1" has signed up for Battle 12 with combination A
    And "myname2" has fulfilled a claim from Battle 12
    And "myname1" has deleted their sign up for the prompt meme "Battle 12"
  # Use the work creator account to avoid having to reveal the collection
  When I am logged in as "myname2"
    And I go to "Battle 12" collection's page
  Then I should see "Fulfilled Story"
  When I follow "Fulfilled Story"
  Then I should not see "In response to a prompt"
    And I should see "Battle 12"

  Scenario: When user deletes signup, the work creator can edit the work
  normally

  Given "myname1" has signed up for Battle 12 with combination A
    And "myname2" has fulfilled a claim from Battle 12
    And "myname1" has deleted their sign up for the prompt meme "Battle 12"
  When I am logged in as "myname2"
    And I edit the work "Fulfilled Story"
    And I fill in "Additional Tags" with "My New Tag"
    And I press "Post"
  Then I should see "Work was successfully updated."
    And I should see "My New Tag"

  Scenario: A mod can delete a prompt meme without needing Javascript and all the
  claims and sign-ups will be deleted with it, but the collection will remain

  Given everyone has signed up for Battle 12
    And "myname4" has claimed a prompt from Battle 12
  When I am logged in as "mod1"
    And I delete the challenge "Battle 12"
  Then I should see "Are you sure you want to delete the challenge from the collection Battle 12? All sign-ups, assignments, and settings will be lost. (Works and bookmarks will remain in the collection.)"
  When I press "Yes, Delete Challenge"
  Then I should see "Challenge settings were deleted."
    And I should not see the prompt meme dashboard for "Battle 12"
    And no one should have a claim in "Battle 12"
    And no one should be signed up for "Battle 12"
  When I go to the collections page
  Then I should see "Battle 12"

  Scenario: A user can still access their Sign-ups page after a prompt meme they
  were signed up for has been deleted

  Given everyone has signed up for Battle 12
    And the challenge "Battle 12" is deleted
  When I am logged in as "myname1"
    And I go to my signups page
  Then I should see "Challenge Sign-ups for myname1"
    And I should not see "Battle 12"

  Scenario: A user can still access their Claims page after a prompt meme they
  had an unfulfilled claim in has been deleted

  Given everyone has signed up for Battle 12
    And "myname1" has claimed a prompt from Battle 12
    And the challenge "Battle 12" is deleted
  When I am logged in as "myname1"
    And I go to my signups page
  Then I should see "Challenge Sign-ups for myname1"
    And I should not see "Battle 12"

  Scenario: A user can still access their Claims page after a prompt meme they
  had a fulfilled claim in has been deleted

  Given everyone has signed up for Battle 12
    And "myname4" has fulfilled a claim from Battle 12
    And the challenge "Battle 12" is deleted
  When I am logged in as "myname4"
    And I go to my claims page
  Then I should see "My Claims"
  When I follow "Fulfilled Claims"
  Then I should not see "Battle 12"

  Scenario: The prompt line should not show on claim fills after the prompt meme
  has been deleted

  Given everyone has signed up for Battle 12
    And "myname1" has fulfilled a claim from Battle 12
    And the challenge "Battle 12" is deleted
  When I am logged out
    And I view the work "Fulfilled Story"
  Then I should not see "In response to a prompt"

  Scenario: A mod can delete a prompt meme collection and all the claims and
  sign-ups will be deleted with it

  Given everyone has signed up for Battle 12
    And "myname1" has fulfilled a claim from Battle 12
    And the challenge "Battle 12" is deleted
  When I am logged in as "mod1"
    And I go to "Battle 12" collection's page
    And I follow "Collection Settings"
    And I follow "Delete"
  Then I should see "Are you sure you want to delete the collection Battle 12?"
  When I press "Yes, Delete Collection"
  Then I should see "Collection was successfully deleted."
    And no one should have a claim in "Battle 12"
    And no one should be signed up for "Battle 12"
  When I go to the collections page
  Then I should not see "Battle 12"

  Scenario: Claim fills should still be accessible even after the prompt meme
  collection has been deleted

  Given "AO3-4693" is fixed
  # Given I have Battle 12 prompt meme fully set up
    # And everyone has signed up for Battle 12
    # And "myname1" has fulfilled a claim from Battle 12
    # And the collection "Battle 12" is deleted
  # When I view the work "Fulfilled Story"
  # Then I should see "Fulfilled Story"
    # TODO: Make an issue
    # And I should not see "In response to a prompt"
    # And I should not see "Battle 12"

  Scenario: Delete a signup, claims should also be deleted from the prompt
  meme's Claims list

  Given "myname1" has signed up for Battle 12 with combination B
    And "myname4" has claimed a prompt from Battle 12
    And "myname1" has deleted their sign up for the prompt meme "Battle 12"
  When I am logged in as "myname4"
    And I go to my claims page
  Then I should see "Claims (0)"
