@help
Feature: Help
  In order to get help
  As a humble user
  I want to read help links

Scenario: clicking the help popup for moderated collection
  
  Given I am logged in as "first_user"
  When I go to the collections page
  When I follow "New Collection"
    And I follow "Collection moderated"
  Then I should see "By default, collections are not moderated"
  
Scenario: view the help popup for chapter title

  Given the following activated user exists
    | login         | password   |
    | epicauthor    | password   |
    And basic tags
  When I am logged in as "epicauthor"
    And I go to epicauthor's user page
    And I follow "New Work"
    And I select "Not Rated" from "Rating"
    And I check "No Archive Warnings Apply"
    And I fill in "Fandoms" with "New Fandom"
    And I fill in "Work Title" with "New Epic Work"
    And I select "English" from "Choose a language"
    And I fill in "content" with "Well, maybe not so epic."
    And I press "Post"
    And I follow "Add Chapter"
    And I follow "Chapter title"
  Then I should see "You can add a chapter title"