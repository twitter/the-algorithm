@bookmarks @search
Feature: Bookmarks of deleted items

  Scenario: Deleting a work shouldn't make its bookmarks disappear completely.
    Given I am logged in as "Alice"
      And I post the work "Mayfly"
      And I am logged in as "Beth"
      And I bookmark the work "Mayfly" with the note "The best yet!"
      And I am logged in as "Alice"
      And I delete the work "Mayfly"
      And all indexing jobs have been run
      And I am logged in as "Charlotte"
    When I go to the search people page
      And I fill in "Name" with "Beth"
      And I press "Search People"
    Then I should see "Beth" within "ol.pseud.group"
      And I should see "1 bookmark"
    When I follow "1 bookmark"
    Then I should see "Bookmarks (1)"
      And I should see "1 Bookmark by Beth"
      And I should see "This has been deleted, sorry!"
      And I should see "The best yet!"
      But I should not see "Mayfly"

  Scenario: Deleting a series shouldn't make its bookmarks disappear completely.
    Given I am logged in as "Alice"
      And I post the work "Mayfly" as part of a series "Shorts"
      And I am logged in as "Beth"
      And I bookmark the series "Shorts"
      And I am logged in as "Alice"
      And I delete the series "Shorts"
      And all indexing jobs have been run
      And I am logged in as "Charlotte"
    When I go to the search people page
      And I fill in "Name" with "Beth"
      And I press "Search People"
    Then I should see "Beth" within "ol.pseud.group"
      And I should see "1 bookmark"
    When I follow "1 bookmark"
    Then I should see "Bookmarks (1)"
      And I should see "1 Bookmark by Beth"
      And I should see "This has been deleted, sorry!"
      But I should not see "Shorts"

  Scenario: Deleting an external work shouldn't make its bookmarks disappear completely.
    Given basic tags
      And I am logged in as "Alice"
      And I bookmark the external work "Extremely Objectionable Content"
      And I am logged in as a "policy_and_abuse" admin
      And I view the external work "Extremely Objectionable Content"
      And I follow "Delete External Work"
      And all indexing jobs have been run
      And I am logged in as "Beth"
    When I go to the search people page
      And I fill in "Name" with "Alice"
      And I press "Search People"
    Then I should see "Alice" within "ol.pseud.group"
      And I should see "1 bookmark"
    When I follow "1 bookmark"
    Then I should see "Bookmarks (1)"
      And I should see "1 Bookmark by Alice"
      And I should see "This has been deleted, sorry!"
      And I should not see "Extremely Objectionable Content"

  Scenario: Deleting a restricted work with bookmarks makes the public bookmark count on all bookmarker's pseuds increase.
    Given I am logged in as "Alice"
      And I post the locked work "Mayfly"
      And I am logged in as "Beth"
      And I bookmark the work "Mayfly"
    When I am logged out
      And I go to the search people page
      And I fill in "Name" with "Beth"
      And I press "Search People"
    Then I should see "Beth" within "ol.pseud.group"
      And I should not see "1 bookmark"
    When I am logged in as "Alice"
      And I delete the work "Mayfly"
      And all indexing jobs have been run
      And I am logged out
      And I go to the search people page
      And I fill in "Name" with "Beth"
      And I press "Search People"
    Then I should see "Beth" within "ol.pseud.group"
      And I should see "1 bookmark"
