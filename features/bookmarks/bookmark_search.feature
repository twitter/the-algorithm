@bookmarks @search
Feature: Search Bookmarks
  In order to test search
  As a humble coder
  I have to use cucumber with elasticsearch

  Background:
    Given I am on the search bookmarks page

  Scenario: Search bookmarks by tag
    Given I have bookmarks to search

    # Only on bookmarks
    When I fill in "Bookmarker's tags" with "rare"
      And I press "Search Bookmarks"
    Then I should see the page title "Search Bookmarks"
      And I should see "You searched for: Tags: rare"
      And I should see "1 Found"
      And I should see "second work"
    When I follow "Edit Your Search"
    Then the field labeled "Bookmarker's tags" should contain "rare"

    # Only on bookmarkables
    When I am on the search bookmarks page
      And I fill in "Work tags" with "rare"
      And I press "Search Bookmarks"
    Then I should see the page title "Search Bookmarks"
      And I should see "You searched for: Tags: rare"
      And I should see "2 Found"
      And I should see "First work"
      And I should see "First Series"
    When I follow "Edit Your Search"
    Then the field labeled "Work tags" should contain "rare"

    # On bookmarks and bookmarkables, results should match both
    When I am on the search Bookmarks page
      And I fill in "Bookmarker's tags" with "rare"
      And I fill in "Work tags" with "rare"
      And I press "Search Bookmarks"
    Then I should see the page title "Search Bookmarks"
      And I should see "You searched for: Tags: rare"
      And I should see "No results found."

  Scenario: Search bookmarks by date bookmarked
    Given I have bookmarks to search by dates
    When I fill in "Date bookmarked" with "> 900 days ago"
      And I press "Search Bookmarks"
    Then I should see the page title "Search Bookmarks"
      And I should see "You searched for: Date bookmarked: > 900 days ago"
      And I should see "3 Found"
      And I should see "Old bookmark of old work"
      And I should see "Old bookmark of old series"
      And I should see "Old bookmark of old external work"
    When I follow "Edit Your Search"
    Then the field labeled "Date bookmarked" should contain "> 900 days ago"

    When I fill in "Date bookmarked" with "< 900 days ago"
      And I press "Search Bookmarks"
    Then I should see "You searched for: Date bookmarked: < 900 days ago"
      And I should see "6 Found"
      And I should see "New bookmark of old work"
      And I should see "New bookmark of new work"
      And I should see "New bookmark of old series"
      And I should see "New bookmark of new series"
      And I should see "New bookmark of old external work"
      And I should see "New bookmark of new external work"

  Scenario: Search bookmarks by date updated
    Given I have bookmarks to search by dates
    When I fill in "Date updated" with "> 900 days ago"
      And I press "Search Bookmarks"
    Then I should see the page title "Search Bookmarks"
      And I should see "You searched for: Date updated: > 900 days ago"
      And I should see "6 Found"
      And I should see "Old bookmark of old work"
      And I should see "New bookmark of old work"
      And I should see "Old bookmark of old series"
      And I should see "New bookmark of old series"
      And I should see "Old bookmark of old external work"
      And I should see "New bookmark of old external work"
    When I follow "Edit Your Search"
    Then the field labeled "Date updated" should contain "> 900 days ago"

    When I fill in "Date updated" with "< 900 days ago"
      And I press "Search Bookmarks"
    Then I should see "You searched for: Date updated: < 900 days ago"
      And I should see "3 Found"
      And I should see "New bookmark of new work"
      And I should see "New bookmark of new series"
      And I should see "New bookmark of new external work"

  Scenario: Search bookmarks for recs
    Given I have bookmarks to search
    When I check "Rec"
      And I press "Search Bookmarks"
    Then I should see the page title "Search Bookmarks"
      And I should see "You searched for: Rec"
      And I should see "2 Found"
      And I should see "First work"
      And I should see "Second Series"
    When I follow "Edit Your Search"
    Then the "Rec" checkbox should be checked

  Scenario: Search bookmarks by any field
    Given I have bookmarks to search by any field

    # Only on bookmarks
    When I fill in "Any field on bookmark" with "more please"
      And I press "Search Bookmarks"
    Then I should see the page title "Bookmarks Matching 'more please'"
      And I should see "You searched for: more please"
      And I should see "6 Found"
      And I should see "Hurt and that's it"
      And I should see "Fluff"
      And I should see "H/C Series"
      And I should see "Ouchless Series"
      And I should see "External Fix-It"
      And I should see "External Whump"
    When I follow "Edit Your Search"
    Then the field labeled "Any field on bookmark" should contain "more please"

    # Only on bookmarkables
    When I am on the search bookmarks page
      And I fill in "Any field on work" with "hurt"
      And I press "Search Bookmarks"
    Then I should see the page title "Bookmarks Matching 'hurt'"
      And I should see "You searched for: hurt"
      And I should see "4 Found"
      And I should see "Comfort"
      And I should see "Hurt and that's it"
      And I should see "H/C Series"
      And I should see "External Whump"
    When I follow "Edit Your Search"
    Then the field labeled "Any field on work" should contain "hurt"

    # On bookmarks and bookmarkables, results should match both
    When I am on the search bookmarks page
      And I fill in "Any field on bookmark" with "more please"
      And I fill in "Any field on work" with "hurt"
      And I press "Search Bookmarks"
    Then I should see the page title "Bookmarks Matching 'hurt, more please'"
      And I should see "You searched for: hurt, more please"
      And I should see "3 Found"
      And I should see "Hurt and that's it"
      And I should see "H/C Series"
      And I should see "External Whump"

  Scenario: Search bookmarks by type
    Given I have bookmarks to search
    When I select "External Work" from "Type"
      And I press "Search Bookmarks"
    Then I should see the page title "Search Bookmarks"
      And I should see "You searched for: Type: External Work"
      And I should see "1 Found"
      And I should see "Skies Grown Darker"
    When I follow "Edit Your Search"
    Then "External Work" should be selected within "Type"
    When I select "Series" from "Type"
      And I press "Search Bookmarks"
    Then I should see "You searched for: Type: Series"
      And I should see "2 Found"

  Scenario: Search for bookmarks with notes, and then edit search to narrow
  results by the note content
    Given I have bookmarks to search
    When I check "With notes"
      And I press "Search Bookmarks"
    Then I should see the page title "Search Bookmarks"
      And I should see "You searched for: With Notes"
      And I should see "3 Found"
      And I should see "fifth"
      And I should see "Skies Grown Darker"
      And I should see "Second Series"
    When I follow "Edit Your Search"
    Then the "With notes" checkbox should be checked
    When I fill in "Notes" with "broken heart"
      And I press "Search Bookmarks"
    Then I should see the page title "Search Bookmarks"
      And I should see "You searched for: Notes: broken heart, With Notes"
    When "AO3-3943" is fixed
      # And I should see "1 Found"
      # And I should see "fifth"
    When I follow "Edit Your Search"
    Then the field labeled "Notes" should contain "broken heart"
      And the "With notes" checkbox should be checked

  Scenario: If testuser has the pseud tester_pseud, searching for bookmarks by
  the bookmarker testuser returns all of tester_pseud's bookmarks
    Given I have bookmarks to search
    When I fill in "Bookmarker" with "testuser"
      And I press "Search Bookmarks"
    Then I should see the page title "Search Bookmarks"
      And I should see "You searched for: Bookmarker: testuser"
      And I should see "8 Found"
      And I should see "First work"
      And I should see "second work"
      And I should see "third work"
      And I should see "tester_pseud"
      And I should see "fifth"
      And I should see "Skies Grown Darker"
      And I should see "First Series"
      And I should see "Second Series"
    When I follow "Edit Your Search"
    Then the field labeled "Bookmarker" should contain "testuser"

  Scenario: Search for bookmarks by the bookmarkable item's completion status
    Given I have bookmarks of various completion statuses to search
    When I fill in "Any field on work" with "complete: true"
      And I press "Search Bookmarks"
    Then I should see "You searched for: complete: true"
      And I should see "2 Found"
      And I should see "Finished Work"
      And I should see "Complete Series"
      And I should not see "Incomplete Work"
      And I should not see "Incomplete Series"
      And I should not see "External Work"
    When I follow "Edit Your Search"
    Then the field labeled "Any field on work" should contain "complete: true"
    When I fill in "Any field on work" with "complete: false"
      And I press "Search Bookmarks"
    Then I should see "You searched for: complete: false"
      And I should see "2 Found"
      And I should see "Incomplete Work"
      And I should see "Incomplete Series"
      And I should not see "Finished Work"
      And I should not see "Complete Series"
      And I should not see "External Work"

  Scenario: Search bookmarks by work language
    Given "someuser" has bookmarks of works in various languages
    # reload search page to bring new language-id mappings for dropdown
    When I reload the page
      And I select "Deutsch" from "Work language"
      And I press "Search Bookmarks"
    Then I should see the page title "Search Bookmarks"
      And I should see "You searched for: Work language: Deutsch"
      And I should see "1 Found"
      And I should see "german work"
      And I should not see "english work"

  Scenario: Inputting bad queries
    Given I have bookmarks to search
    When I fill in "Any field on work" with "bad~query~~!!!"
      And I press "Search Bookmarks"
    Then I should see "Your search failed because of a syntax error. Please try again."
