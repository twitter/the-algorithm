@bookmarks @search
Feature: Bookmark Indexing

  Scenario: Adding a new, out-of-fandom work to a series
    Given I am logged in as "author"
      And a canonical fandom "Veronica Mars"
      And a canonical fandom "X-Files"
      And I post the work "The Story Telling: Beginnings" with fandom "Veronica Mars" as part of a series "Telling Stories"
      And I post the work "Unrelated Story" with fandom "X-Files"
      And I am logged in as "bookmarker"
      And I bookmark the work "Unrelated Story"
      And I bookmark the series "Telling Stories"
    When I go to the bookmarks tagged "X-Files"
      And I select "Date Updated" from "Sort by"
      And I press "Sort and Filter"
    Then the 1st bookmark result should contain "Unrelated Story"
    When I am logged in as "author"
      And I post the work "The Story Returns" with fandom "X-Files" as part of a series "Telling Stories"
      And I go to the bookmarks tagged "X-Files"
    Then I should see "Telling Stories"
      And I should not see "This tag has not been marked common and can't be filtered on (yet)."
    When I select "Date Updated" from "Sort by"
      And I press "Sort and Filter"
    Then the 1st bookmark result should contain "Telling Stories"
      And the 2nd bookmark result should contain "Unrelated Story"
    When I go to bookmarker's user page
      And I follow "Bookmarks (2)"
      And I select "Date Updated" from "Sort by"
      And I press "Sort and Filter"
    Then the 1st bookmark result should contain "Telling Stories"
      And the 2nd bookmark result should contain "Unrelated Story"
    When I go to the bookmarks tagged "X-Files"
    Then I should see "Telling Stories"
      And I should not see "This tag has not been marked common and can't be filtered on (yet)."
    When I select "Date Updated" from "Sort by"
      And I press "Sort and Filter"
    Then the 1st bookmark result should contain "Telling Stories"
      And the 2nd bookmark result should contain "Unrelated Story"

  Scenario: When a work in a series is updated with a new tag, bookmarks of the
  series should appear on the tag's bookmark listing; when a tag is removed, the
  bookmarks should disappear from the tag listing
    Given a canonical freeform "New Tag"
      And I am logged in
      And I post the work "Work" as part of a series "Series"
      And I bookmark the series "Series"
    When I edit the work "Work"
      And I fill in "Additional Tags" with "New Tag"
      And I press "Post"
      And all indexing jobs have been run
      And I go to the bookmarks tagged "New Tag"
    Then the 1st bookmark result should contain "Series"
    When I edit the work "Work"
      And I fill in "Additional Tags" with ""
      And I press "Post"
      And all indexing jobs have been run
      And I go to the bookmarks tagged "New Tag"
    Then I should not see "Series"

  Scenario: Synning a canonical tag used on bookmarked series and external works
  should move the bookmarks to the new canonical's bookmark listings; de-synning
  should remove them
    Given a canonical fandom "Veronica Mars"
      And a canonical fandom "Veronica Mars (TV)"
      And bookmarks of external works and series tagged with the fandom tag "Veronica Mars"
      And I am logged in as an admin
    When I syn the tag "Veronica Mars" to "Veronica Mars (TV)"
      And I go to the bookmarks tagged "Veronica Mars (TV)"
    Then I should see "BookmarkedExternalWork"
      And I should see "BookmarkedSeries"
    When I de-syn the tag "Veronica Mars" from "Veronica Mars (TV)"
      And the tag "Veronica Mars" is canonized
      And I go to the bookmarks tagged "Veronica Mars (TV)"
    Then I should not see "BookmarkedExternalWork"
      And I should not see "BookmarkedSeries"
    When I go to the bookmarks tagged "Veronica Mars"
    Then I should see "BookmarkedExternalWork"
      And I should see "BookmarkedSeries"
    When I syn the tag "Veronica Mars" to "Veronica Mars (TV)"
      And I go to the bookmarks tagged "Veronica Mars (TV)"
    Then I should see "BookmarkedSeries"
      And I should see "BookmarkedExternalWork"

  Scenario: Subtagging a tag used on bookmarked series and external works should
  make the bookmarks appear in the metatag's bookmark listings; de-subbing
  should remove them
    Given a canonical character "Laura"
      And a canonical character "Laura Roslin"
      And bookmarks of external works and series tagged with the character tag "Laura Roslin"
      And I am logged in as a tag wrangler
    When I subtag the tag "Laura Roslin" to "Laura"
      And I go to the bookmarks tagged "Laura"
    Then I should see "BookmarkedExternalWork"
      And I should see "BookmarkedSeries"
    When I remove the metatag "Laura" from "Laura Roslin"
      And I go to the bookmarks tagged "Laura"
    Then I should not see "BookmarkedExternalWork"
      And I should not see "BookmarkedSeries"
    When I go to the bookmarks tagged "Laura Roslin"
    Then I should see "BookmarkedExternalWork"
      And I should see "BookmarkedSeries"

  Scenario: A bookmark of an external work should show on a tag's bookmark
  listing once the tag is made canonical
    Given basic tags
      And I am logged in as "bookmarker"
      And I bookmark the external work "Outside Story" with character "Mikki Mendoza"
    When the tag "Mikki Mendoza" is canonized
      And I go to the bookmarks tagged "Mikki Mendoza"
    Then I should see "Outside Story"

  Scenario: New bookmarks of external works should appear in the bookmark listings for its tag's existing metatag, and removing the tag should remove the bookmark from both the tag's and metatag's bookmark listings
    Given basic tags
      And a canonical character "Ann"
      And a canonical character "Ann Ewing"
      And "Ann" is a metatag of the character "Ann Ewing"
    When I am logged in
      And I bookmark the external work "The Big D" with character "Ann Ewing"
      And I go to the bookmarks tagged "Ann"
    Then I should see "The Big D"
    When the character "Ann Ewing" is removed from the external work "The Big D"
      And I go to the bookmarks tagged "Ann Ewing"
    Then I should not see "The Big D"
    When I go to the bookmarks tagged "Ann"
    Then I should not see "The Big D"

  Scenario: Adding a chapter to a work in a series should update the series, as
  should deleting a chapter from a work in a series
    Given I have bookmarks of old series to search
    When a chapter is added to "WIP in a Series"
      And I go to the search bookmarks page
      And I select "Series" from "Type"
      And I select "Date Updated" from "Sort by"
      And I press "Search Bookmarks"
    Then the 1st bookmark result should contain "Older WIP Series"
      And the 2nd bookmark result should contain "Newer Complete Series"
    When I delete chapter 2 of "WIP in a Series"
      And I go to the search bookmarks page
      And I select "Series" from "Type"
      And I select "Date Updated" from "Sort by"
      And I press "Search Bookmarks"
    Then the 1st bookmark result should contain "Newer Complete Series"
      And the 2nd bookmark result should contain "Older WIP Series"

  Scenario: When a wrangler edits a tag's merger using the "Synonym of" field,
  the tag's bookmarks should be transfered to the new merger's bookmark listings
    Given a canonical character "Ellie Ewing"
      And a canonical character "Ellie Farlow"
      And a synonym "Miss Ellie" of the tag "Ellie Ewing"
      And bookmarks of all types tagged with the character tag "Miss Ellie"
      And I am logged in as a tag wrangler
    When I go to the bookmarks tagged "Ellie Ewing"
    Then I should see "BookmarkedWork"
      And I should see "BookmarkedSeries"
      And I should see "BookmarkedExternalWork"
    When I edit the tag "Miss Ellie"
      And I fill in "Synonym of" with "Ellie Farlow"
      And I press "Save changes"
      And all indexing jobs have been run
      And I go to the bookmarks tagged "Ellie Ewing"
    Then I should not see "BookmarkedWork"
      And I should not see "BookmarkedSeries"
      And I should not see "BookmarkedExternalWork"
    When I go to the bookmarks tagged "Ellie Farlow"
    Then I should see "BookmarkedWork"
      And I should see "BookmarkedSeries"
      And I should see "BookmarkedExternalWork"
