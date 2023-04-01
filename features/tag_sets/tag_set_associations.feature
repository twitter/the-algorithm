# encoding: utf-8
@tag_sets
Feature: Reviewing tag set associations

  Scenario: If a nominated tag and its parent are approved they should appear on the associations page
  Given I nominate and approve fandom "Floobry" and character "Zarrr" in "Nominated Tags"
    And I am logged in as "tagsetter"
    And I go to the "Nominated Tags" tag set page
  Then I should see "don't seem to be associated"
  When I follow "Review Associations"
  Then I should see "Zarrr → Floobry"
  When I check "Zarrr → Floobry"
    And I submit
  Then I should see "Nominated associations were added"
    And I should not see "don't seem to be associated"

  Scenario: If a nominated tag and its parent are wrangled after approval it should still be possible to associate them
  Given I nominate and approve fandom "Floobry" and character "Zarrr" in "Nominated Tags"
    And a canonical character "Zarrr" in fandom "Floobry"
  When I review associations for "Nominated Tags"
  Then I should see "Zarrr → Floobry"
  When I check "Zarrr → Floobry"
    And I submit
  Then I should see "Nominated associations were added"
    And I should not see "don't seem to be associated"

  Scenario: If a tag set does not exist, no one should be able to see its associations
  Given I am logged in as "tagsetter"
  When I view associations for a tag set that does not exist
  Then I should see "What tag set did you want to look at?"
    And I should be on the tagsets page
  When I log out
    And I view associations for a tag set that does not exist
  Then I should see "What tag set did you want to look at?"
    And I should be on the tagsets page

  Scenario: Tags with brackets should work in associations
