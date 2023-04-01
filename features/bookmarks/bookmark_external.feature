@bookmarks
Feature: Create bookmarks of external works
  In order to have an archive full of bookmarks
  As a humble user
  I want to bookmark some works

  Scenario: A user can bookmark an external work using all the Creator's Tags fields (fandoms, rating, category, relationships, character)
    Given basic tags
      And mock websites with no content
      And I am logged in as "bookmarker"
      And I am on the new external work page
    When I fill in "URL" with "http://example.org/200"
      And I fill in "Creator" with "ao3testing"
      And I fill in "Title" with "Some External Work"
      And I fill in "Fandoms" with "Test Fandom"
      And I select "General Audiences" from "Rating"
      And I check "M/M"
      And I fill in "Relationships" with "Character 1/Character 2"
      And I fill in "Characters" with "Character 3, Character 4"
      And I press "Create"
    Then I should see "Bookmark was successfully created."
      And I should see "Some External Work"
      And I should see "ao3testing"
      And I should see "Test Fandom"
      And I should see "General Audiences"
      And I should see "M/M"
      And I should see "Character 1/Character 2"
      And I should see "Character 3"
      And I should see "Character 4"

  Scenario: A user must enter a fandom to create a bookmark on an external work
    Given basic tags
      And mock websites with no content
      And I am logged in as "first_bookmark_user"
    When I go to first_bookmark_user's bookmarks page
    Then I should not see "Stuck with You"
    When I follow "Bookmark External Work"
      And I fill in "Creator" with "Sidra"
      And I fill in "Title" with "Stuck with You"
      And I fill in "URL" with "http://example.org/200"
      And I press "Create"
    Then I should see "Fandom tag is required"
    When I fill in "Fandoms" with "Popslash"
      And I press "Create"
      And all indexing jobs have been run
    Then I should see "This work isn't hosted on the Archive"
    When I go to first_bookmark_user's bookmarks page
    Then I should see "Stuck with You"

  Scenario: A user must enter a valid URL to create a bookmark on an external work
    Given I am logged in as "first_bookmark_user"
      And the default ratings exist
      And mock websites with no content
    When I go to first_bookmark_user's bookmarks page
    Then I should not see "Stuck with You"
    When I follow "Bookmark External Work"
      And I fill in "Creator" with "Sidra"
      And I fill in "Title" with "Stuck with You"
      And I fill in "Fandoms" with "Popslash"
      And I press "Create"
    Then I should see "does not appear to be a valid URL"
    When I fill in "URL" with "http://example.org/200"
      And I press "Create"
      And all indexing jobs have been run
    Then I should see "This work isn't hosted on the Archive"
    When I go to first_bookmark_user's bookmarks page
    Then I should see "Stuck with You"

    # edit external bookmark
    When I follow "Edit"
    Then I should see "Editing bookmark for Stuck with You"
    When I fill in "Notes" with "I wish this author would join AO3"
      And I fill in "Your tags" with "WIP"
      And I press "Update"
    Then I should see "Bookmark was successfully updated"

    # delete external bookmark
    When I follow "Delete"
    Then I should see "Are you sure you want to delete"
      And I should see "Stuck with You"
    When I press "Yes, Delete Bookmark"
    Then I should see "Bookmark was successfully deleted."
      And I should not see "Stuck with You"

  Scenario Outline: A user can enter a valid non-ASCII URL to create a bookmark on an external work
    Given I am logged in as "first_bookmark_user"
      And the default ratings exist
      And all pages on the host "<url>" return status 200
    When I am on the new external work page
      And I fill in "URL" with "<url>"
      And I fill in "Creator" with "foo"
      And I fill in "Title" with "<title>"
      And I fill in "Fandoms" with "Popslash"
      And I press "Create"
    Then I should see "Bookmark was successfully created."

    Examples:
    | url                         | title |
    | https://example.com/ö       | Ö     |
    | https://example.com/á       | á     |
    | https://example.com/?utf8=✓ | check |
    | https://example.com/a,b,c   | comma |

  Scenario: Bookmark External Work link should be available to logged in users, but not logged out users
    Given a fandom exists with name: "Testing BEW Button", canonical: true
      And I am logged in as "markie" with password "theunicorn"
      And I create the collection "Testing BEW Collection"
    When I go to my bookmarks page
    Then I should see "Bookmark External Work"
    When I go to the bookmarks page
    Then I should see "Bookmark External Work"
    When I go to the bookmarks in collection "Testing BEW Collection"
    Then I should see "Bookmark External Work"
    When I log out
      And I go to markie's bookmarks page
    Then I should not see "Bookmark External Work"
    When I go to the bookmarks page
    Then I should not see "Bookmark External Work"
    When I go to the bookmarks tagged "Testing BEW Button"
    Then I should not see "Bookmark External Work"
    When I go to the bookmarks in collection "Testing BEW Collection"
    Then I should not see "Bookmark External Work"

  Scenario: Users can see external works, admins can also see only duplicates
    Given basic tags
      And I am logged in
      # Bookmark the same URL twice
      And I bookmark the external work "External Title"
      And I bookmark the external work "Alternate Title"

    When I go to the external works page
    Then I should see "External Title"
      And I should see "Alternate Title"
      And I should not see "Show duplicates"

    When I go to the external works page with only duplicates
    Then I should see "Sorry, you don't have permission to access the page you were trying to reach."

    When I am logged in as an admin
      And I go to the external works page
    Then I should see "External Title"
      And I should see "Alternate Title"
      And I should see "Show duplicates (1)"
    When I follow "Show duplicates (1)"
    Then I should see "External Title"
      And I should not see "Alternate Title"

  @javascript
  Scenario: When using the bookmarklet on a URL that has already been bookmarked, the work information will be automatically filled in
    Given "first_user" has bookmarked an external work
      And I am logged in as "second_user"
    When I use the bookmarklet on a previously bookmarked URL
      And I press "Create"
    Then I should see "Bookmark was successfully created."
      And the work info for my new bookmark should match the original

  @javascript
  Scenario: When using the autocomplete to select a URL that has already been bookmarked, the work information will be automatically filled in
    Given "first_user" has bookmarked an external work
      And I am logged in as "second_user"
    When I go to the new external work page
      And I choose a previously bookmarked URL from the autocomplete
      And I press "Create"
    Then I should see "Bookmark was successfully created."
      And the work info for my new bookmark should match the original

  @javascript
  Scenario: When getting an error (e.g. because the title is blank) after using the autocomplete to select a previously-bookmarked URL, any changes you made to the work information are not overridden on the edit page
    Given "first_user" has bookmarked an external work
      And I am logged in as "second_user"
    When I go to the new external work page
      And I choose a previously bookmarked URL from the autocomplete
      And I fill in "Title" with ""
      And I fill in "Creator" with "Different Author"
      And I press "Create"
    Then I should see "Sorry! We couldn't save this bookmark because:"
      And I should see "Title can't be blank"
      And the field labeled "Title" should contain ""
      And the field labeled "Creator" should contain "Different Author"
    When I fill in "Title" with "Different Title"
      And I press "Create"
    Then I should see "Bookmark was successfully created."
      And the title and creator info for my new bookmark should vary from the original
      And the summary and tag info for my new bookmark should match the original

  @javascript
  Scenario: When using the autocomplete to select a URL that has already been bookmarked, any information you have entered will be overwritten
    Given "first_user" has bookmarked an external work
      And I am logged in as "second_user"
    When I go to the new external work page
      And I fill in "Creator" with "ao3testing"
      And I fill in "Title" with "Some External Work"
      And I fill in "Fandoms" with "Test Fandom"
      And I select "General Audiences" from "Rating"
      And I check "M/M"
      And I fill in "Relationships" with "Character 1/Character 2"
      And I fill in "Characters" with "Character 3, Character 4"
      And I choose a previously bookmarked URL from the autocomplete
      And I press "Create"
    Then I should see "Bookmark was successfully created."
      And the work info for my new bookmark should match the original
