@works @search
Feature: Search anonymous works
  As a creator of anonymous works
  I do not want searching for my name to ruin my anonymity
  But I do want my works to appear in searches

  Scenario: Works that are anonymous do not show up in searches for the
    creator's name
    Given I have the Battle set loaded
    When I search for works containing "mod1"
    Then I should see "You searched for: mod1"
      And I should see "No results found"
    When I search for works by "mod1"
    Then I should see "You searched for: creator: mod1"
      And I should see "No results found"

  Scenario: Works that are anonymous should show up in searches for the
  creator Anonymous
    Given I have the Battle set loaded
    When I search for works containing "Anonymous"
    Then I should see "You searched for: Anonymous"
      And I should see "1 Found"
      And I should see "Fulfilled Story-thing"
    When I search for works by "Anonymous"
    Then I should see "You searched for: creator: Anonymous"
      And I should see "1 Found"
      And I should see "Fulfilled Story-thing"
    When I go to the search works page
      And I fill in "Author/Artist" with "Anonymous"
      And I press "Search" within "#new_work_search"
    Then I should see "You searched for: Author/Artist: Anonymous"
      And I should see "1 Found"
      And I should see "Fulfilled Story-thing"

  Scenario: Works that used to be anonymous show up in searches for the
  creator's name once the creator is revealed
    Given I have the Battle set loaded
      And I reveal the authors of the "Battle 12" challenge
    When I search for works containing "mod1"
    Then I should see "You searched for: mod1"
      And I should see "1 Found"
      And I should see "Fulfilled Story-thing"
    When I search for works by "mod1"
    Then I should see "You searched for: creator: mod1"
      And I should see "1 Found"
      And I should see "Fulfilled Story-thing"
    When I go to the search works page
      And I fill in "Author/Artist" with "mod1"
      And I press "Search" within "#new_work_search"
    Then I should see "You searched for: Author/Artist: mod1"
      And I should see "1 Found"
      And I should see "Fulfilled Story-thing"
