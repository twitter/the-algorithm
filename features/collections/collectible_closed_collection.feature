@bookmarks @collections @works
Feature: Collectible items in closed collections
  As a moderator
  I want users to be unable to add items to my closed collection

  Background:
    Given I have a closed collection "Various Penguins"
      And I am logged in as a random user

  Scenario: Add my work to a closed collection
    When I post the work "Blabla" in the collection "Various Penguins"
    Then I should see "is not currently open"
    When the collection counts have expired
      And I go to "Various Penguins" collection's page
    Then I should see "Works (0)" within "#dashboard"
      And I should not see "Blabla"

  Scenario: Add my bookmark to a closed collection
    Given I have a bookmark for "Tundra penguins"
    When I add my bookmark to the collection "Various_Penguins"
    Then I should see "is closed"
    When the collection counts have expired
      And I go to "Various Penguins" collection's page
    Then I should see "Bookmarked Items (0)" within "#dashboard"
      And I should not see "Tundra penguins"
