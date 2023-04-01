@works @search
Feature: Search works by stats
  As a user
  I want to search works by hits, kudos, comments, and bookmarks

  Scenario: Search by range of hits
    Given I have the Battle set loaded
    When I am on the search works page
      And I fill in "Hits" with "10000-20000"
      And I press "Search" within "#new_work_search"
    Then I should see "You searched for: hits: 10000-20000"
      And I should see "1 Found"
      And the 1st result should contain "Hits: 10000"
    When I follow "Edit Your Search"
    Then the field labeled "Hits" should contain "10000-20000"

  Scenario: Search by > hits
    Given I have the Battle set loaded
    When I am on the search works page
      And I fill in "Hits" with "> 100"
      And I press "Search" within "#new_work_search"
    Then I should see "You searched for: hits: > 100"
      And I should see "2 Found"
      And I should see "First work"
      And I should see "third work"
    When I follow "Edit Your Search"
    Then the field labeled "Hits" should contain "> 100"

  Scenario: Search and sort by kudos
    Given I have the Battle set loaded
    When I am on the search works page
      And I fill in "Kudos" with ">0"
      And I select "Kudos" from "Sort by"
      And I press "Search" within "#new_work_search"
    Then I should see "You searched for: kudos count: >0 sort by: kudos descending"
      And I should see "2 Found"
      And the 1st result should contain "Kudos: 4"
      And the 2nd result should contain "Kudos: 1"
    When I follow "Edit Your Search"
    Then the field labeled "Kudos" should contain ">0"
      And "Kudos" should be selected within "Sort by"
    When I fill in "Kudos" with "5"
      And I press "Search" within "#new_work_search"
    Then I should see "You searched for: kudos count: 5 sort by: kudos descending"
      And I should see "No results found"
    When I follow "Edit Your Search"
    Then the field labeled "Kudos" should contain "5"
    When I fill in "Kudos" with "4"
      And I press "Search" within "#new_work_search"
    Then I should see "You searched for: kudos count: 4 sort by: kudos descending"
      And I should see "1 Found"
      And the 1st result should contain "Kudos: 4"
    When I follow "Edit Your Search"
    Then the field labeled "Kudos" should contain "4"
    When I fill in "Kudos" with "<2"
      And I select "Ascending" from "Sort direction"
      And I press "Search" within "#new_work_search"
    Then I should see "You searched for: kudos count: <2 sort by: kudos ascending"
      And I should see "6 Found"
      And I should see "second work"
      And I should see "third work"
      And I should see "fourth"
      And I should see "fifth"
      And I should see "I am <strong>er Than Yesterday & Other Lies"
      And I should see "Fulfilled Story-thing"
      And the 6th result should contain "Kudos: 1"
    When I follow "Edit Your Search"
    Then the field labeled "Kudos" should contain "<2"
      And "Kudos" should be selected within "Sort by"
      And "Ascending" should be selected within "Sort direction"
    When I choose "Complete works only"
      And I press "Search" within "#new_work_search"
    Then I should see "You searched for: Complete kudos count: <2 sort by: kudos ascending"
      And I should see "4 Found"
      And I should see "second work"
      And I should see "third work"
      And I should see "fourth"
      And I should see "Fulfilled Story-thing"
      And the 4th result should contain "Kudos: 1"
    When I follow "Edit Your Search"
    Then the field labeled "Kudos" should contain "<2"
      And the "Complete works only" checkbox should be checked
      And "Ascending" should be selected within "Sort direction"

  Scenario: Search by exact number of comments
    Given a set of works with comments for searching
    When I am on the search works page
      And I fill in "Comments" with "1"
      And I press "Search" within "#new_work_search"
    Then I should see "You searched for: comments count: 1"
      And I should see "3 Found"
    When I follow "Edit Your Search"
    Then the field labeled "Comments" should contain "1"

  Scenario: Search by a range of comments
    Given a set of works with comments for searching
    When I am on the search works page
      And I fill in "Comments" with "1-5"
      And I press "Search" within "#new_work_search"
    Then I should see "You searched for: comments count: 1-5"
      And I should see "5 Found"
    When I follow "Edit Your Search"
    Then the field labeled "Comments" should contain "1-5"

  Scenario: Search by > a number of comments and sort in ascending order by
  comments
    Given a set of works with comments for searching
    When I am on the search works page
      And I fill in "Comments" with "> 0"
      And I select "Comments" from "Sort by"
      And I select "Ascending" from "Sort direction"
      And I press "Search" within "#new_work_search"
    Then I should see "You searched for: comments count: > 0 sort by: comments ascending"
      And I should see "6 Found"
      And the 1st result should contain "Comments: 1"
      And the 2nd result should contain "Comments: 1"
      And the 3rd result should contain "Comments: 1"
      And the 4th result should contain "Comments: 3"
      And the 5th result should contain "Comments: 3"
      And the 6th result should contain "Comments: 10"
    When I follow "Edit Your Search"
    Then the field labeled "Comments" should contain "> 0"
      And "Comments" should be selected within "Sort by"
      And "Ascending" should be selected within "Sort direction"

 Scenario: Search by < a number of comments and sort in descending order by
 comments
    Given a set of works with comments for searching
    When I am on the search works page
      And I fill in "Comments" with "<20"
      And I select "Comments" from "Sort by"
      And I select "Descending" from "Sort direction"
      And I press "Search" within "#new_work_search"
    Then I should see "You searched for: comments count: <20 sort by: comments descending"
      And I should see "7 Found"
      And the 1st result should contain "Comments: 10"
      And the 2nd result should contain "Comments: 3"
      And the 3rd result should contain "Comments: 3"
      And the 4th result should contain "Comments: 1"
      And the 5th result should contain "Comments: 1"
      And the 6th result should contain "Comments: 1"
    When I follow "Edit Your Search"
    Then the field labeled "Comments" should contain "<20"
      And "Comments" should be selected within "Sort by"
      And "Descending" should be selected within "Sort direction"

  Scenario: Search by > a number of comments and sort in ascending order by
    title using the header search
    Given a set of works with comments for searching
    When I am on the home page
      And I fill in "site_search" with "comments: > 2 sort: title ascending"
      And I press "Search"
    Then I should see "You searched for: comments count: > 2 sort by: title ascending"
      And I should see "3 Found"
      And the 1st result should contain "Work 5"
      And the 2nd result should contain "Work 6"
      And the 3rd result should contain "Work 7"
    When I follow "Edit Your Search"
    Then the field labeled "Comments" should contain "> 2"
      And "Title" should be selected within "Sort by"
      And "Ascending" should be selected within "Sort direction"

  Scenario: Search by exact number of bookmarks
    Given a set of works with bookmarks for searching
    When I am on the search works page
      And I fill in "Bookmarks" with "1"
      And I press "Search" within "#new_work_search"
    Then I should see "You searched for: bookmarks count: 1"
      And I should see "2 Found"
    When I follow "Edit Your Search"
    Then the field labeled "Bookmarks" should contain "1"

  Scenario: Search by a range of bookmarks
    Given a set of works with bookmarks for searching
    When I am on the search works page
      And I fill in "Bookmarks" with "2 - 5"
      And I press "Search" within "#new_work_search"
    Then I should see "You searched for: bookmarks count: 2 - 5"
      And I should see "3 Found"
    When I follow "Edit Your Search"
    Then the field labeled "Bookmarks" should contain "2 - 5"

  Scenario: Search by > a number of bookmarks and sort in ascending order by
  bookmarks
    Given a set of works with bookmarks for searching
    When I am on the search works page
      And I fill in "Bookmarks" with ">1"
      And I select "Bookmarks" from "Sort by"
      And I select "Ascending" from "Sort direction"
      And I press "Search" within "#new_work_search"
    Then I should see "You searched for: bookmarks count: >1 sort by: bookmarks ascending"
      And I should see "4 Found"
      And the 1st result should contain "Bookmarks: 2"
      And the 2nd result should contain "Bookmarks: 2"
      And the 3rd result should contain "Bookmarks: 4"
      And the 4th result should contain "Bookmarks: 10"
    When I follow "Edit Your Search"
    Then the field labeled "Bookmarks" should contain ">1"
      And "Bookmarks" should be selected within "Sort by"
      And "Ascending" should be selected within "Sort direction"

  Scenario: Search by < a number of bookmarks and sort in descending order by
  bookmarks
    Given a set of works with bookmarks for searching
    When I am on the search works page
      And I fill in "Bookmarks" with "< 20"
      And I select "Bookmarks" from "Sort by"
      And I select "Descending" from "Sort direction"
      And I press "Search" within "#new_work_search"
    Then I should see "You searched for: bookmarks count: < 20 sort by: bookmarks descending"
      And I should see "7 Found"
      And the 1st result should contain "Bookmarks: 10"
      And the 2nd result should contain "Bookmarks: 4"
      And the 3rd result should contain "Bookmarks: 2"
      And the 4th result should contain "Bookmarks: 2"
      And the 5th result should contain "Bookmarks: 1"
      And the 6th result should contain "Bookmarks: 1"
    When I follow "Edit Your Search"
    Then the field labeled "Bookmarks" should contain "< 20"
      And "Bookmarks" should be selected within "Sort by"
      And "Descending" should be selected within "Sort direction"

  Scenario: Search by > a number of bookmarks and sort in ascending order by
  title using the header search
    Given a set of works with bookmarks for searching
    When I am on the home page
      And I fill in "site_search" with "bookmarks: > 2 sort by: title ascending"
      And I press "Search"
    Then I should see "You searched for: bookmarks count: > 2 sort by: title ascending"
      And I should see "2 Found"
      And the 1st result should contain "Work 6"
      And the 2nd result should contain "Work 7"
    When I follow "Edit Your Search"
    Then the field labeled "Bookmarks" should contain "> 2"
      And "Title" should be selected within "Sort by"
      And "Ascending" should be selected within "Sort direction"
