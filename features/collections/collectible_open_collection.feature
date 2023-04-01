@bookmarks @collections @works
Feature: Collectible items
  As a user
  I want to add my items to collections

  Background:
    Given I have a collection "Various Penguins"
      And I am logged in as a random user

  Scenario: Post my work to a collection
    Given I post the work "Blabla" in the collection "Various Penguins"
    When I go to "Various Penguins" collection's page
    Then I should see "Works (0)" within "#dashboard"
      And I should see "Blabla"
    When the collection counts have expired
      And I reload the page
    Then I should see "Works (1)" within "#dashboard"
      And I should see "Blabla"

  Scenario: Add my chaptered work to a collection
    Given I post the chaptered work "Blabla" in the collection "Various Penguins"
    When I go to "Various Penguins" collection's page
    Then I should see "Works (0)" within "#dashboard"
      And I should see "Blabla"
    When the collection counts have expired
      And I reload the page
    Then I should see "Works (1)" within "#dashboard"
      And I should see "Blabla"

  Scenario: Add my bookmark to a collection
    Given I have a bookmark for "Tundra penguins"
    When I add my bookmark to the collection "Various_Penguins"
    Then I should see "Added"
    When I go to "Various Penguins" collection's page
    Then I should see "Bookmarked Items (0)" within "#dashboard"
      And I should see "Tundra penguins"
    When the collection counts have expired
      And I reload the page
    Then I should see "Bookmarked Items (1)" within "#dashboard"
      And I should see "Tundra penguins"

  Scenario: Bookmarks of deleted items are included on the collection's Manage
  Items page
    Given I have a bookmark of a deleted work
      And I add my bookmark to the collection "Various_Penguins"
    When I am logged in as the owner of "Various Penguins"
      And I view the approved collection items page for "Various Penguins"
    Then I should see "Bookmark of deleted item"
      And I should see "This has been deleted, sorry!"

  Scenario: Bookmarks of deleted items are included on the user's Manage
  Collected Works page
    Given I have a bookmark of a deleted work
    When I add my bookmark to the collection "Various_Penguins"
    Then I should see "Added"
    When I go to my collection items page
      And I follow "Approved"
    Then I should see "Bookmark of deleted item"
      And I should see "This has been deleted, sorry!"

  Scenario: Deleted works are not included on the user's Manage Collected Works
  page
    Given I post the work "Emperor Penguins" to the collection "Various Penguins"
      And I delete the work "Emperor Penguins"
    When I go to my collection items page
      And I follow "Approved"
    Then I should not see "Emperor Penguins"

  Scenario: Deleted works are not included on the collection's Manage Items page
    Given I post the work "Emperor Penguins" to the collection "Various Penguins"
      And I delete the work "Emperor Penguins"
    When I am logged in as the owner of "Various Penguins"
      And I view the approved collection items page for "Various Penguins"
    Then I should not see "Emperor Penguins"

  Scenario: Drafts are included on the user's Manage Collected Works page
    Given the draft "Sweater Penguins" in the collection "Various Penguins"
    When I go to my collection items page
      And I follow "Approved"
    Then I should see "Sweater Penguins (Draft)"

  Scenario: Drafts are included on a collection's Manage Items page
    Given the draft "Sweater Penguins" in the collection "Various Penguins"
    When I am logged in as the owner of "Various Penguins"
      And I view the approved collection items page for "Various Penguins"
    Then I should see "Sweater Penguins (Draft)"
