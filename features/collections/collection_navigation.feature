@collections
Feature: Basic collection navigation

  @disable_caching
  Scenario: Create a collection and check the links
  When I am logged in as "mod" with password "password"
    And I go to the collections page
    And I follow "New Collection"
    And I fill in "Collection name" with "my_collection"
    And I fill in "Display title" with "My Collection"
    And I submit
  Then I should see "Collection was successfully created."
    And I should see "Works (0)"
    And I should see "Fandoms (0)"
  Given basic tags
    And I have a canonical "TV Shows" fandom tag named "New Fandom"
    And a freeform exists with name: "Free", canonical: true
  When I follow "New Work" within "ul.user.navigation.actions"
    And I select "Not Rated" from "Rating"
    And I check "No Archive Warnings Apply"
    And I fill in "Fandoms" with "New Fandom"
    And I fill in "Additional Tags" with "Free"
    And I fill in "Work Title" with "Work for my collection"
    And I select "English" from "Choose a language"
    And I fill in "content" with "First because I'm the mod"
    And I fill in "Post to Collections / Challenges" with "my_collection"
    And I press "Preview"
    And I press "Post"
    And the collection counts have expired
    And I follow "My Collection"
  When I follow "Profile"
  Then I should see "About My Collection (my_collection)"
    And I should see "Maintainers: mod"
  When I follow "Subcollections (0)"
  Then I should see "Challenges/Subcollections in My Collection"
    And I should see "Sorry, there were no collections found."
  When I follow "Fandoms (1)"
  Then I should see "New Fandom (1)"
  When I follow "Works (1)"
  Then I should see "Work for my collection by mod"
    And I should see "1 Work in My Collection"
  When I follow "Bookmarked Items" within "#dashboard"
  Then I should see "0 Bookmarked Items"
  When I follow "Random Items"
  Then I should see "Work for my collection by mod"
  When I follow "People" within "div#dashboard"
    Then I should see "Participants in My Collection"
    And I should see "mod"
  When I follow "Tags" within "div#dashboard"
    Then I should see "Free"
  When I follow "Collection Settings"
    Then I should see "Edit Collection"
  When I am logged out
    And I am on the collections page
    And I follow "My Collection"
  Then I should not see "Settings"

  Scenario: A Collection's Fandoms should be in alphabetical order
  Given I have the collection "My ABCs" with name "my_abcs"
    And a canonical fandom "A League of Their Own"
    And a canonical fandom "Merlin"
    And a canonical fandom "Teen Wolf"
    And a canonical fandom "The Borgias"
  When I am logged in as "Scott" with password "password"
    And I post the work "Sesame Street" in the collection "My ABCs"
    And I edit the work "Sesame Street"
    And I fill in "Fandoms" with "A League of Their Own, Merlin, Teen Wolf, The Borgias"
    And I press "Post"
    And the collection counts have expired
    And I go to "My ABCs" collection's page
    And I follow "Fandoms ("
  Then "The Borgias" should appear before "A League of Their Own"
    And "A League of Their Own" should appear before "Merlin"
    And "Merlin" should appear before "Teen Wolf"

  Scenario: Collections can be filtered by media type
    Given I have the collection "We all sing together"
      And I have a canonical "TV Shows" fandom tag named "Steven's Universe"
      And I have a canonical "Movies" fandom tag named "High School Musical"
    When I am logged in as "Brian" with password "They called him Brian"
      And I post the work "Stronger than you" with fandom "Steven's Universe" in the collection "We all sing together"
      And I post the work "Breaking Free" with fandom "High School Musical" in the collection "We all sing together"
      And I go to "We all sing together" collection's page
      And I follow "Fandoms ("
      And I select "Movies" from "medium_id"
      And I press "Show"
    Then I should see "High School Musical"
      And I should not see "Steven's Universe"
    When I select "TV Shows" from "medium_id"
      And I press "Show"
    Then I should not see "High School Musical"
      And I should see "Steven's Universe"

  Scenario: A collection's fandom count shouldn't include inherited metatags.
    Given I have the collection "MCU Party"
      And a canonical fandom "The Avengers"
      And a canonical fandom "MCU"
      And "MCU" is a metatag of the fandom "The Avengers"
      And I am logged in as "mcu_fan"
      And I post the work "Ensemble Piece" with fandom "The Avengers" in the collection "MCU Party"
      And the collection counts have expired

    When I go to the collections page
    Then I should see "Fandoms: 1"

    When I go to "MCU Party" collection's page
    Then I should see "Fandoms (1)"

  Scenario: Browse tags within a collection (or not)
    Given I have a collection "Randomness"
      And a canonical fandom "Naruto"
      And a canonical freeform "Crack"
      And I am logged in
      And I post the work "Has some tags" with fandom "Naruto" with freeform "Crack" in the collection "Randomness"
      And the collection counts have expired

    # Tag links from the work blurb in a collection should not be collection-scoped
    When I go to "Randomness" collection's page
      And I follow "Naruto" within "#collection-works"
    Then I should be on the works tagged "Naruto"

    # Tag links from the work meta in a collection should not be collection-scoped
    When I go to "Randomness" collection's page
      And I follow "Has some tags"
      And I follow "Naruto"
    Then I should be on the works tagged "Naruto"

    # Tag links from a collection's fandoms page should be collection-scoped
    When I go to "Randomness" collection's page
      And I follow "Fandoms (1)"
      And I follow "Naruto"
    Then I should be on the works tagged "Naruto" in collection "Randomness"

    # Tag links from a collection's tags page should be collection-scoped
    When I go to "Randomness" collection's page
      And I follow "Tags" within "#dashboard"
      And I follow "Crack"
    Then I should be on the works tagged "Crack" in collection "Randomness"
