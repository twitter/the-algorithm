@works @search
Feature: Work Drafts

  Scenario: Creating a work draft
  Given I am logged in as "Scott" with password "password"
  When the draft "scotts draft"
    And I press "Cancel"
  Then I should see "The work was not posted. It will be saved here in your drafts for one month, then deleted from the Archive."

  Scenario: Creating a work draft, editing it, and saving the changes without posting or previewing and then double check that it is saved and I didn't get the success message erroneously
  Given basic tags
    And I am logged in as "persnickety" with password "editingisfun"
  When I go to the new work page
  Then I should see "Post New Work"
    And I select "General Audiences" from "Rating"
    And I check "No Archive Warnings Apply"
    And I select "English" from "Choose a language"
    And I fill in "Fandoms" with "MASH (TV)"
    And I fill in "Work Title" with "Draft Dodging"
    And I fill in "content" with "Klinger lay under his porch."
    And I press "Preview"
  Then I should see "Draft was successfully created. It will be automatically deleted on"
  When I press "Edit"
  Then I should see "Edit Work"
    And I fill in "content" with "Klinger, in Uncle Gus's Aunt Gussie dress, lay under his porch."
    And I press "Save As Draft"
  Then I should see "This work is a draft and has not been posted."
    And I should see "Klinger, in Uncle Gus's Aunt Gussie dress, lay under his porch."
  When I am on persnickety's works page
    Then I should not see "Draft Dodging"
    And I should see "Drafts (1)"
  When I follow "Drafts (1)"
    Then I should see "Draft Dodging"
  When I follow "Draft Dodging"
    Then I should see "Klinger, in Uncle Gus's Aunt Gussie dress, lay under his porch."

  Scenario: Creating an draft Chapter on a draft Work
  Given I am logged in as "Scott" with password "password"
    And the draft "scotts other draft"
    And I press "Cancel"
    And I edit the work "scotts other draft"
    And I follow "Add Chapter"
    And I fill in "content" with "this is second chapter content"
    And I press "Preview"
  Then I should see "This is a draft chapter in an unposted work. The work will be automatically deleted on"

  Scenario: Purging old drafts
  Given I am logged in as "drafter" with password "something"
    When the work "old draft work" was created 31 days ago
    And the work "new draft work" was created 2 days ago
    When I am on drafter's works page
    Then I should see "Drafts (2)"
    When the purge_old_drafts rake task is run
      And I reload the page
    Then I should see "Drafts (1)"

  Scenario: Drafts cannot be found by search
  Given I am logged in as "drafter" with password "something"
    And the draft "draft to post"
  Given all indexing jobs have been run
  When I fill in "site_search" with "draft"
    And I press "Search"
  Then I should see "No results found"

  Scenario: Posting drafts from drafts page
    Given I am logged in as "drafter" with password "something"
      And the draft "draft to post"
    When I am on drafter's works page
    Then I should see "Drafts (1)"
    When I follow "Drafts (1)"
    Then I should see "draft to post"
      And I should see "Post Draft" within "#main .own.work.blurb .actions"
      And I should see "Delete Draft" within "#main .own.work.blurb .actions"
    When I follow "Post Draft"
    Then I should see "draft to post"
      And I should see "drafter"
      And I should not see "Preview"

  Scenario: Deleting drafts from drafts page
    Given I am logged in as "drafter" with password "something"
      And the draft "draft to delete"
    When I am on drafter's works page
    Then I should see "Drafts (1)"
    When I follow "Drafts (1)"
    Then I should see "draft to delete"
      And I should see "Post Draft" within "#main .own.work.blurb .actions"
      And I should see "Delete Draft" within "#main .own.work.blurb .actions"
    When I follow "Delete Draft"
    Then I should not see "All bookmarks, comments, and kudos will be lost."
      And I should not see "Orphan Work Instead"
    When I press "Yes, Delete Draft"
    Then I should see "Your work draft to delete was deleted"

  Scenario: Saving changes to an existing draft without posting and then double check that it is saved and I didn't get the success message erroneously
    Given I am logged in as "drafty" with password "breezeinhere"
      And the draft "Windbag"
    When I am on drafty's works page
    Then I should see "Drafts (1)"
    When I follow "Drafts (1)"
    Then I should see "Windbag"
      And I should see "Edit" within "#main .own.work.blurb .actions"
    When I follow "Edit"
      Then I should see "Edit Work"
    When I fill in "content" with "My draft has changed!"
      And I press "Save As Draft"
    Then I should see "This work is a draft and has not been posted"
      And I should see "My draft has changed!"
    When I am on drafty's works page
    Then I should see "Drafts (1)"
    When I follow "Drafts (1)"
    Then I should see "Windbag"
    When I follow "Windbag"
    Then I should see "My draft has changed!"

  Scenario: Editing a draft and previewing it should warn that it has not been saved.
    Given I am logged in as "ringadingding"
      And the draft "Walking Into Mordor"
    When I edit the draft "Walking Into Mordor"
      And I press "Preview"
    Then I should see "Please post your work or save as draft if you want to keep them."

  Scenario: A chaptered draft should be able to have beginning and end notes, and it should display them.
    Given I am logged in as "composer"
      And I post the chaptered draft "Epic in Progress"
    When I edit the draft "Epic in Progress"
      And I add the beginning notes "Some beginning notes."
      And I add the end notes "Some end notes."
      And I press "Save As Draft"
    Then I should see "Some beginning notes."
      And I should see "See the end of the work for more notes."
    When I follow "more notes"
    Then I should see "Some end notes."

  Scenario: If a chaptered draft belongs to a series, the series should be listed on the draft
    Given I am logged in as "two_can_sam"
      And I post the chaptered draft "Cereal Serial"
    When I add the draft "Cereal Serial" to series "Aisle 5"
      And I follow "Next Chapter"
    Then I should see "Series this work belongs to:"
      And I should see "Aisle 5"

    Scenario: Word count should appear after creating single chapter work, saving as draft, and posting draft from user's drafts page
      Given I am logged in as "test_user"
        And I set up the draft "Unicorns are everywhere"
        And I fill in "content" with "Help there are unicorns everywhere"
        And I press "Preview"
        And I press "Save As Draft"
      When I go to my drafts page
        And I follow "Post Draft"
      Then I should be on the work "Unicorns are everywhere"
        And I should see "Words:5"

    Scenario: Word count should equal all draft chapters' word counts if work isn't posted
      Given I am logged in as "test_user"
        And I set up the draft "Unicorns are everywhere"
        And I fill in "content" with "Help there are unicorns everywhere"
        And I press "Preview"
        And I press "Save As Draft"
      When a chapter is set up for "Unicorns are everywhere"
        And I press "Preview"
        And I press "Save As Draft"
      Then I should see "Words:16"
      When a chapter is set up for "Unicorns are everywhere"
        And I press "Preview"
      When I press "Save As Draft"
      Then I should see "Words:27"

      Scenario: When posting chapter(s) in unpublished multichapter work, word count should equal posted chapter(s) word count
      Given I am logged in as "test_user"
        And I set up the draft "Unicorns are everywhere"
        And I fill in "content" with "Help there are unicorns everywhere"
        And I press "Preview"
        And I press "Save As Draft"
      When a chapter is set up for "Unicorns are everywhere"
        And I press "Preview"
        And I press "Save As Draft"
      Then I should see "Words:16"
      When a chapter is set up for "Unicorns are everywhere"
        And I press "Preview"
        And I press "Save As Draft"
      Then I should see "Words:27"
      When I view the work "Unicorns are everywhere"
        And I press "Post Chapter"
      Then I should see "Words:5"
      When I follow "Next Chapter"
        And I press "Post Chapter"
      Then I should see "Words:16"
