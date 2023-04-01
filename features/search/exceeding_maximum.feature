Feature: Large searches
  As a user
  I want to see how many results my search returns if it's more than the max

  Scenario: Work search should show the correct number of results
    Given a set of Kirk/Spock works for searching
      And the max search result count is 2
      And 1 item is displayed per page
    When I search for works containing "James T. Kirk"
    Then I should see "4 Found"

  Scenario: Bookmark search should show the correct number of results
    Given I have bookmarks to search
      And the max search result count is 2
      And 1 item is displayed per page
    When I am on the search bookmarks page
      And I select "Work" from "Type"
      And I press "Search Bookmarks"
    Then I should see "5 Found"

  Scenario: People search should show the correct number of results
    Given the following activated users exist
      | login      |
      | test_alice |
      | test_betsy |
      | test_carol |
      | test_diana |
      And the max search result count is 2
      And 1 item is displayed per page
      And all indexing jobs have been run
    When I go to the search people page
      And I fill in "Search all fields" with "test"
      And I press "Search People"
    Then I should see "4 Found"

  Scenario: Tag search should show the correct number of results
    Given a canonical freeform "Fluff"
      And a canonical freeform "Angst"
      And a canonical freeform "Smut"
      And a canonical freeform "Alternate Universe"
      And the max search result count is 2
      And 1 tag is displayed per search page
    When I go to the search tags page
      And I choose "Freeform"
      And I press "Search Tags"
    Then I should see "4 Found"
