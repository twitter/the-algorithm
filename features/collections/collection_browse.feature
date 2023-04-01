@collections
Feature: Collection
  In order to have an archive full of collections
  As a humble user
  I want to locate and browse an existing collection

  Scenario: Collections index should have different links for logged in and logged out users

  Given I am logged in as "onlooker"
  When I go to the collections page
  Then I should see "Open Challenges"
    And I should see "New Collection"
  When I log out
    And I go to the collections page
  Then I should see "Open Challenges"
    And I should not see "New Collection"

  Scenario: Filter collections index to only show prompt memes

  Given I have loaded the fixtures
  When I go to the collections page
    And I choose "Prompt Meme Challenge"
    And I press "Sort and Filter"
  Then I should see "On Demand"
    And I should not see "Some Test Collection"
    And I should not see "Some Other Collection"
    And I should not see "Another Plain Collection"
    And I should not see "Surprise Presents"
    And I should not see "Another Gift Swap"

  Scenario: Filter collections index to only show gift exchanges

  Given I have loaded the fixtures
  When I go to the collections page
    And I choose "Gift Exchange Challenge"
    And I press "Sort and Filter"
  Then I should see "Surprise Presents"
    And I should see "Another Gift Swap"
    And I should not see "On Demand"
    And I should not see "Some Test Collection"
    And I should not see "Some Other Collection"
    And I should not see "Another Plain Collection"

  Scenario: Filter collections index to only show non-challenge collections

  Given I have loaded the fixtures
  When I go to the collections page
    And I choose "No Challenge"
    And I press "Sort and Filter"
  Then I should see "Some Test Collection"
    And I should see "Some Other Collection"
    And I should see "Another Plain Collection"
    And I should not see "Surprise Presents"
    And I should not see "Another Gift Swap"
    And I should not see "On Demand"

  Scenario: Filter collections index to only show closed collections

  Given I have loaded the fixtures
  When I go to the collections page
    And I choose "collection_filters_closed_true"
    And I press "Sort and Filter"
  Then I should see "Another Plain Collection"
    And I should see "On Demand"
    And I should not see "Some Test Collection"
    And I should not see "Some Other Collection"
    And I should not see "Surprise Presents"
    And I should not see "Another Gift Swap"

  Scenario: Filter collections index to only show open collections

  Given I have loaded the fixtures
  When I go to the collections page
    And I choose "collection_filters_closed_false"
    And I press "Sort and Filter"
  Then I should see "Some Test Collection"
    And I should see "Some Other Collection"
    And I should see "Surprise Presents"
    And I should see "Another Gift Swap"
    And I should not see "Another Plain Collection"
    And I should not see "On Demand"

  Scenario: Filter collections index to only show moderated collections

  Given I have loaded the fixtures
  When I go to the collections page
    And I choose "collection_filters_moderated_true"
    And I press "Sort and Filter"
  Then I should see "Surprise Presents"
    And I should not see "Some Test Collection"
    And I should not see "Some Other Collection"
    And I should not see "Another Plain Collection"
    And I should not see "Another Gift Swap"
    And I should not see "On Demand"

  Scenario: Filter collections index to only show unmoderated collections

  Given I have loaded the fixtures
  When I go to the collections page
    And I choose "collection_filters_moderated_false"
    And I press "Sort and Filter"
  Then I should see "Some Test Collection"
    And I should see "Some Other Collection"
    And I should see "Another Plain Collection"
    And I should see "Another Gift Swap"
    And I should see "On Demand"
    And I should not see "Surprise Presents"

  Scenario: Filter collections index to show open, moderated gift exchanges

  Given I have loaded the fixtures
  When I go to the collections page
    And I choose "collection_filters_closed_false"
    And I choose "collection_filters_moderated_true"
    And I choose "Gift Exchange Challenge"
    And I press "Sort and Filter"
  Then I should see "Surprise Presents"
    And I should not see "Some Test Collection"
    And I should not see "Some Other Collection"
    And I should not see "Another Plain Collection"
    And I should not see "Another Gift Swap"
    And I should not see "On Demand"

 Scenario: Filter collections index by fandom

  Given I have the collection "Collection1"
    And I have the collection "Collection2"
    And a fandom exists with name: "Steven's Universe", canonical: true
    And I am logged in as a random user
    And I post the work "Stronger than you" with fandom "Steven's Universe" in the collection "Collection1"
  When I go to the collections page
    And I fill in "collection_filters_fandom" with "Steven's Universe"
    And I press "Sort and Filter"
  Then I should see "Collection1"
    And I should not see "Collection2"

  Scenario: Clear filters applied on collections

  Given I have loaded the fixtures
    And I am logged in as a random user
  When I go to the collections page
    And I choose "Gift Exchange Challenge"
    And I choose "collection_filters_closed_false"
    And I choose "collection_filters_moderated_true"
    And I press "Sort and Filter"
  Then I should see "1 Collection"
    And I should see "Surprise Presents"
  When I follow "Clear Filters"
  Then I should see "6 Collections"
    And the "Gift Exchange Challenge" checkbox within "#collection-filters" should not be checked
    And the "No" checkbox within "#collection-filters" should not be checked
    And the "Yes" checkbox within "#collection-filters" should not be checked

  Scenario: Look at a collection, see the rules and intro and FAQ

  Given I have loaded the fixtures
    And I am logged in as "testuser" with password "testuser"
  When I go to the collections page
  Then I should see "Collections in the "
    And I should see "Some Test Collection"
  When I follow "Some Test Collection"
  Then I should see "Some Test Collection"
    And I should see "There are no works or bookmarks in this collection yet."
  When I follow "Profile"
  Then I should see "Welcome to the test collection" within "#intro"
    And I should see "What is this test thing?" within "#faq"
    And I should see "It's a test collection" within "#faq"
    And I should see "Be nice to testers" within "#rules"
    And I should see "About Some Test Collection (sometest)"
