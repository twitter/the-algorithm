# encoding: utf-8
@tag_sets
Feature: Nominating and reviewing nominations for a tag set

  Scenario: A tag set should take nominations within the nomination limits
  Given I am logged in as "tagsetter"
    And I set up the nominated tag set "Nominated Tags" with 0 fandom noms and 3 character noms
  Then I should see "Nominate"
  When I follow "Nominate"
  Then I should see "You can nominate up to 3 characters"

  Scenario: Tag set nominations should nest characters under fandoms if fandoms are being nominated
  Given I am logged in as "tagsetter"
    And I set up the nominated tag set "Nominated Tags" with 3 fandom noms and 3 character noms
  Then I should see "Nominate"
  When I follow "Nominate"
  Then I should see "You can nominate up to 3 fandoms and up to 3 characters for each one"

  Scenario: You should be able to nominate tags
  Given I am logged in as "tagsetter"
    And I set up the nominated tag set "Nominated Tags" with 3 fandom noms and 3 character noms
  Given I nominate 3 fandoms and 3 characters in the "Nominated Tags" tag set as "nominator"
    And I submit
  Then I should see "Your nominations were successfully submitted"

  Scenario: You should be able to nominate characters when the tagset doesn't allow fandom nominations
  Given a canonical character "Common Character" in fandom "Canon"
    And I am logged in as "tagsetter"
    And I set up the nominated tag set "Nominated Tags" with 0 fandom noms and 3 character noms
  When I follow "Nominate"
    And I fill in "Character 1" with "Obscure Character"
    And I fill in "Character 2" with "Common Character"
    And I press "Submit"
  Then I should see "Sorry! We couldn't save this tag set nomination"
    And I should see "We need to know what fandom Obscure Character belongs in."
    But I should not see "We need to know what fandom Common Character belongs in."
  When I fill in "Fandom?" with "Canon"
    And I press "Submit"
  Then I should see "Your nominations were successfully submitted."
    And I should see "Obscure Character"
    And I should see "Common Character"

  Scenario: You should be able to nominate relationships when the tagset doesn't allow fandom nominations
  Given a canonical relationship "Common Pairing" in fandom "Canon"
    And I am logged in as "tagsetter"
    And I set up the nominated tag set "Nominated Tags" with 0 fandom noms and 3 relationship noms
  When I follow "Nominate"
    And I fill in "Relationship 1" with "Rare Pairing"
    And I fill in "Relationship 2" with "Common Pairing"
    And I press "Submit"
  Then I should see "Sorry! We couldn't save this tag set nomination"
    And I should see "We need to know what fandom Rare Pairing belongs in."
    But I should not see "We need to know what fandom Common Pairing belongs in."
  When I fill in "Fandom?" with "Canon"
    And I press "Submit"
  Then I should see "Your nominations were successfully submitted."
    And I should see "Rare Pairing"
    And I should see "Common Pairing"

  Scenario: You should be able to edit your nominated tag sets, but cannot delete them once they've been reviewed
  Given I am logged in as "tagsetter"
    And I set up the nominated tag set "Mayfly" with 3 fandom noms and 3 character noms
  When I nominate fandom "Floobry" and character "Barblah" in "Mayfly"
  Then I should see "Not Yet Reviewed (may be edited or deleted)"
    And I should see "Floobry"
  When I follow "Edit"
  Then I should see the input with id "tag_set_nomination_fandom_nominations_attributes_0_tagname" within "div#main"
  When I fill in "tag_set_nomination_fandom_nominations_attributes_0_tagname" with "Bloob"
  When I press "Submit"
  Then I should see "Your nominations were successfully updated"
  Given I am logged in as "tagsetter"
  When I review nominations for "Mayfly"
  Then I should see "Bloob" within ".tagset"
  When I check "fandom_approve_Bloob"
    And I press "Submit"
  Then I should see "Successfully added to set: Bloob"
  Given I am logged in as "nominator"
    And I go to the tagsets page
    And I follow "Mayfly"
    And I follow "My Nominations"
  Then I should see "Partially Reviewed (unreviewed nominations may be edited)"
  When I follow "Edit"
  Then I should not see the input with id "tag_set_nomination_fandom_nominations_attributes_0_tagname" within "div#main"

  Scenario: You should be able to edit your nominated characters when the tagset doesn't allow fandom nominations
  Given I am logged in as "tagsetter"
    And I set up the nominated tag set "Nominated Tags" with 0 fandom noms and 3 character noms
  When I follow "Nominate"
    And I fill in "Character 1" with "My Aforvite Character"
    And I fill in "Fandom?" with "My Favorite Fandom"
    And I press "Submit"
  Then I should see "Your nominations were successfully submitted."
    And I should see "My Aforvite Character"
  When I follow "Edit"
    And I fill in "Character 1" with "My Favorite Character"
    And I press "Submit"
  Then I should see "Your nominations were successfully updated."
    And I should see "My Favorite Character"
    But I should not see "My Aforvite Character"

  Scenario: You should be able to edit your nominated relationships when the tagset doesn't allow fandom nominations
  Given I am logged in as "tagsetter"
    And I set up the nominated tag set "Nominated Tags" with 0 fandom noms and 3 relationship noms
  When I follow "Nominate"
    And I fill in "Relationship 1" with "My Favorite Character & Their Best Friend"
    And I fill in "Fandom?" with "My Favorite Fandom"
    And I press "Submit"
  Then I should see "Your nominations were successfully submitted."
    And I should see "My Favorite Character & Their Best Friend"
  When I follow "Edit"
    And I fill in "Relationship 1" with "My Favorite Character/Their Worst Enemy"
    And I press "Submit"
  Then I should see "Your nominations were successfully updated."
    And I should see "My Favorite Character/Their Worst Enemy"
    But I should not see "Their Best Friend"

  Scenario: Owner of a tag set can clear all nominations
  Given I am logged in as "tagsetter"
    And I set up the nominated tag set "Nominated Tags" with 3 fandom noms and 3 character noms
  Given I nominate 3 fandoms and 3 characters in the "Nominated Tags" tag set as "nominator"
    And I submit
  Then I should see "Your nominations were successfully submitted"
  Given I am logged in as "tagsetter"
  When I review nominations for "Nominated Tags"
    And I follow "Clear Nominations"
    And I press "Yes, Clear Tag Set Nominations"
  Then I should see "All nominations for this Tag Set have been cleared"

  Scenario: Owner of a tag set with over 30 nominations sees a message that they can't all be displayed on one page
  Given I am logged in as "tagsetter"
    And I set up the nominated tag set "Nominated Tags" with 6 fandom noms and 6 character noms
  When there are 36 unreviewed nominations
  Given I am logged in as "tagsetter"
    And I review nominations for "Nominated Tags"
  Then I should see "There are too many nominations to show at once, so here's a randomized selection! Additional nominations will appear after you approve or reject some"

  Scenario: If a set has received nominations, a moderator should be able to review nominated tags
  Given I have the nominated tag set "Nominated Tags"
    And I am logged in as "tagsetter"
  When I go to the "Nominated Tags" tag set page
    And I follow "Review Nominations"
  Then I should see "left to review"

  Scenario: If a moderator approves a nominated tag it should no longer appear on the review page and should appear on the tag set page
  Given I am logged in as "tagsetter"
    And I set up the nominated tag set "Nominated Tags" with 3 fandom noms and 3 character noms
    And I nominate fandom "Floobry" and character "Barblah" in "Nominated Tags"
    And I review nominations for "Nominated Tags"
  Then I should see "Floobry" within ".tagset"
  When I check "fandom_approve_Floobry"
    And I check "character_approve_Barblah"
    And I submit
  Then I should see "Successfully added to set: Floobry"
    And I should see "Successfully added to set: Barblah"
  When I follow "Review Nominations"
  Then I should not see "Floobry"
    And I should not see "Barblah"

  Scenario: If a moderator rejects a nominated tag or its fandom it should no longer appear on the review page
  Given I am logged in as "tagsetter"
    And I set up the nominated tag set "Nominated Tags" with 3 fandom noms and 3 character noms
    And I nominate fandom "Floobry" and character "Barblah" in "Nominated Tags"
    And I review nominations for "Nominated Tags"
  When I check "fandom_reject_Floobry"
    And I submit
  Then I should see "Successfully rejected: Floobry"
    And I should not see "Floobry" within ".tagset"
    And I should not see "Barblah"

  Scenario: Tags with brackets should work with replacement
  Given I am logged in as "tagsetter"
    And I set up the nominated tag set "Nominated Tags" with 3 fandom noms and 3 character noms
    And I nominate fandoms "Foo [Bar], Bar [Foo]" and characters "Yar [Bar], Bat [Bar]" in "Nominated Tags"
    And I review nominations for "Nominated Tags"
  When I check "fandom_approve_Foo__LBRACKETBar_RBRACKET"
    And I check "character_reject_Yar__LBRACKETBar_RBRACKET"
    And I check "fandom_approve_Bar__LBRACKETFoo_RBRACKET"
    And I check "character_approve_Bat__LBRACKETBar_RBRACKET"
    And I submit
  Then I should see "Successfully added to set: Bar [Foo], Foo [Bar]"
    And I should see "Successfully added to set: Bat [Bar]"
    And I should see "Successfully rejected: Yar [Bar]"
  When I go to the "Nominated Tags" tag set page
  Then I should see "Foo [Bar]"
    And I should see "Bar [Foo]"
    And I should not see "Yar [Bar]"
  When I go to the "Nominated Tags" tag set page
    And I follow "Review Associations"
  Then I should see "Bat [Bar] → Bar [Foo]"
  When I check "Bat [Bar] → Bar [Foo]"
    And I submit
  Then I should see "Nominated associations were added"
    And I should not see "don't seem to be associated"

  Scenario: Tags with Unicode characters should work
  Given I nominate and approve tags with Unicode characters in "Nominated Tags"
    And I am logged in as "tagsetter"
    And I go to the "Nominated Tags" tag set page
  Then I should see the tags with Unicode characters

  # Note this is now testing the non-JS method for deleting your own nominations
  Scenario: You should be able to delete your nominations
    Given I am logged in as "tagsetter"
      And I set up the nominated tag set "Nominated Tags" with 3 fandom noms and 3 character noms
    Given I nominate 3 fandoms and 3 characters in the "Nominated Tags" tag set as "nominator"
      And I submit
    When I should see "Your nominations were successfully submitted"
      And I go to the "Nominated Tags" tag set page
      And I follow "My Nominations"
      And I should see "My Nominations for Nominated Tags"
      And I follow "Delete"
      And I should see "Delete Tag Set Nomination?"
    When I press "Yes, Delete Tag Set Nominations"
    Then I should see "Your nominations were deleted."

  Scenario: Two users cannot nominate the same tag for different parents (e.g.
  the same character in two fandoms)
    Given I am logged in as "tagsetter"
      And I set up the nominated tag set "Nominated Tags" with 1 fandom nom and 1 character nom
      And I nominate fandom "The Closer" and character "Sharon Raydor" in "Nominated Tags" as "nominator1"
    When I start to nominate fandom "Major Crimes" and character "Sharon Raydor" in "Nominated Tags" as "nominator2"
      And I submit
    Then I should see "Someone else has already nominated the tag Sharon Raydor for this set but in fandom The Closer. (All nominations have to be unique for the approval process to work.) Try making your nomination more specific, for instance tacking on (Major Crimes)."

  Scenario: If a tag already exists in the archive as one type of tag, it can't be
  nominated as another type of tag (e.g. Veronica Mars can't be nominated as a fandom if
  it exists as a character)
    Given a noncanonical character "Veronica Mars"
      And I am logged in as "tagsetter"
      And I set up the nominated tag set "Nominated Tags" with 1 fandom nom and 1 character nom
    When I start to nominate fandom "Veronica Mars" and character "Keith Mars" in "Nominated Tags"
      And I submit
    Then I should see "The tag Veronica Mars is already in the archive as a Character tag. (All tags have to be unique.) Try being more specific, for instance tacking on the medium or the fandom."

