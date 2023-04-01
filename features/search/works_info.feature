@works @search
Feature: Search works by work info
  As a user
  I want to search works by work info

  Scenario: Inputting bad queries
    When I am on the homepage
    When I fill in "site_search" with "bad~query!!!"
      And I press "Search"
    Then I should see "Your search failed because of a syntax error"

  Scenario: Search by language
    Given I have the Battle set loaded
    When I am on the search works page
      And I select "Deutsch" from "Language"
      And I press "Search" within "#new_work_search"
    Then I should see "You searched for: Language: Deutsch"
      And I should see "1 Found"
      And the 1st result should contain "Language: Deutsch"
    When I follow "Edit Your Search"
    Then "Deutsch" should be selected within "Language"

  Scenario: Search by date and then refine by word count
    Given I have the Battle set loaded
    When I am on the search works page
      And I fill in "Date" with "> 2 years ago"
      And I press "Search" within "#new_work_search"
    Then I should see "You searched for: revised at: > 2 years ago"
      And I should see "6 Found"
      And I should see "First work"
      And I should see "second work"
      And I should see "third work"
      And I should see "fourth"
      And I should see "fifth"
      And I should see "I am <strong>er Than Yesterday & Other Lies"
    When I follow "Edit Your Search"
    Then I should be on the search works page
      And the field labeled "Date" should contain "> 2 years ago"
    When I fill in "Word Count" with ">15000"
      And I press "Search" within "#new_work_search"
    Then I should see "You searched for: word count: >15000 revised at: > 2 years ago"
      And I should see "No results found"

  Scenario: Search with the header search field and then refine by author/artist
    Given I have the Battle set loaded
    When I fill in "site_search" with "testuser2"
      And I press "Search"
    Then I should see "You searched for: testuser2"
      And I should see "3 Found"
      And I should see "I am <strong>er Than Yesterday & Other Lies"
      And I should see "fourth"
      And I should see "fifth"
    When I follow "Edit Your Search"
    Then I should be on the search works page
      And the field labeled "Any Field" should contain "testuser2"
    When I fill in "Any Field" with ""
      And I fill in "Author/Artist" with "testuser2"
      And I press "Search" within "#new_work_search"
    Then I should see "You searched for: Author/Artist: testuser2"
      And I should see "3 Found"
      And I should see "fourth"
      And I should see "fifth"
      And I should see "I am <strong>er Than Yesterday & Other Lies"

  Scenario: Search by status
    Given I have loaded the fixtures
    When I am on the search works page
      And I choose "Complete works only"
      And I press "Search" within "#new_work_search"
    Then I should see "You searched for: Complete"
      And I should see "4 Found"
      And I should see "First work"
      And I should see "second work"
      And I should see "third work"
      And I should see "fourth"
    When I am on the search works page
      And I choose "Works in progress only"
      And I press "Search" within "#new_work_search"
    Then I should see "You searched for: Incomplete"
      And I should see "2 Found"
      And I should see "fifth"
      And I should see "I am <strong>er Than Yesterday & Other Lies"

  Scenario: Search by crossovers
    Given I have loaded the fixtures
    When I am on the search works page
      And I choose "Exclude crossovers"
      And I press "Search" within "#new_work_search"
    Then I should see "You searched for: No Crossovers"
      And I should see "6 Found"
      And I should see "First work"
      And I should see "second work"
      And I should see "third work"
      And I should see "fourth"
      And I should see "fifth"
      And I should see "I am <strong>er Than Yesterday & Other Lies"
    When I am on the search works page
      And I choose "Only crossovers"
      And I press "Search" within "#new_work_search"
    Then I should see "You searched for: Only Crossovers"
      And I should see "No results found"

  Scenario: Search by single chapter
    Given I have the Battle set loaded
    When I am on the search works page
      And I check "Single Chapter"
      And I press "Search" within "#new_work_search"
    Then I should see "You searched for: Single Chapter"
      And I should see "4 Found"
      And I should see "First work"
      And I should see "second work"
      And I should see "fourth"
      And I should see "Fulfilled Story-thing"
    When I follow "Edit Your Search"
    Then the "Single Chapter" checkbox should be checked

  Scenario: Search and sort by title
    Given I have loaded the fixtures
    When I am on the search works page
      And I fill in "Title" with "work"
      And I select "Title" from "Sort by"
      And I press "Search" within "#new_work_search"
    Then I should see "You searched for: Title: work sort by: title descending"
      And I should see "3 Found"
      And the 1st result should contain "third work"
      And the 2nd result should contain "second work"
      And the 3rd result should contain "First work"
    When I follow "Edit Your Search"
    Then the field labeled "Title" should contain "work"
      And "Title" should be selected within "Sort by"
    When I select "Ascending" from "Sort direction"
      And I press "Search" within "#new_work_search"
    Then I should see "You searched for: Title: work sort by: title ascending"
      And I should see "3 Found"
      And the 1st result should contain "First work"
      And the 2nd result should contain "second work"
      And the 3rd result should contain "third work"
    When I follow "Edit Your Search"
    Then the field labeled "Title" should contain "work"
      And "Title" should be selected within "Sort by"
      And "Ascending" should be selected within "Sort direction"
