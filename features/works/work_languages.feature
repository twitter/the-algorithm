@works

Feature: Create Works
  In order to have an archive full of works
  As an author
  I want to create new works

  Scenario: Creating a new work with international characters

  Given basic tags
    And basic languages
      And I am logged in as "germanfan" with password "password"
    When I go to the new work page
    Then I should see "Post New Work"
      And I select "Not Rated" from "Rating"
      And I check "No Archive Warnings Apply"
      And I fill in "Fandoms" with "Weiß Kreuz"
      And I fill in "Work Title" with "Überraschende Überraschung"
      And I fill in "content" with "Dies ist eine Fanfic in Deutsch."
      And I select "Deutsch" from "Choose a language"
      And I press "Preview"
    Then I should see "Preview"
    When I press "Post"
    Then I should see "Work was successfully posted."
      And I should see "Deutsch" within "dd.language"
    When I go to the works page
    Then I should see "Überraschende Überraschung"
    When I follow "Weiß Kreuz"
    Then I should see "Überraschende Überraschung"
    When I follow "Überraschende Überraschung"
    Then I should see "Dies ist eine Fanfic in Deutsch."
    When I log out
    Then I should see "Successfully logged out"

    # another example with a different character set (language not in fixtures)

    Given I am logged in as "finnishfan" with password "password"
    When I go to the new work page
    Then I should see "Post New Work"
      And I select "Not Rated" from "Rating"
      And I check "No Archive Warnings Apply"
      And I select "English" from "Choose a language"
      And I fill in "Fandoms" with "A fandom"
      And I fill in "Work Title" with "Ennen päivänlaskua ei voi"
      And I fill in "content" with "A story that is long enough to count"
      And I press "Preview"
    Then I should see "Preview"
    When I press "Post"
    Then I should see "Work was successfully posted."

  Scenario: Previewing a work after changing the language should show the new language
    Given basic languages
      And I am logged in as a random user
      And I post the work "Incorrect"
    When I edit the work "Incorrect"
      And I select "Deutsch" from "Choose a language"
      And I press "Preview"
    Then I should see "Language: Deutsch"
