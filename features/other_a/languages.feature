@works
Feature: Languages
    
  Scenario: Browse works by language
  
  # Admin set up the language
  
  # Given I am logged in as an admin
  #   And I go to the admin-settings page
  # TODO: Then I should be able to add a language in the front end
  
  Scenario: post a work in another language
  
  Given the following activated users exist
      | login         | password   |
      | englishuser   | password   |
      | germanuser1   | password   |
      | germanuser2   | password   |
      | frenchuser    | password   |
      And basic tags
      And basic languages
  When I am logged in as "germanuser2" with password "password"
    And I post the work "Die Rache der Sith"
    And I follow "Edit"
    And I select "Deutsch" from "Choose a language"
    And I press "Preview"
    And I press "Update"
  Then I should see "Die Rache der Sith"
  
  # TODO: French including sedilla, other characters not in the ascii set.
  
  When I log out
    And I am logged in as "englishuser" with password "password"
    And I post the work "Revenge of the Sith"
  Then I should see "Revenge of the Sith"
    
  # Browse works in a language
  
  When I am on the languages page
  Then I should see "Deutsch"
  When I follow "Deutsch"
  Then I should see "1 works in 1 fandoms"
    And I should see "Die Rache der Sith"
    And I should not see "Revenge of the Sith"
    
  # cross-check in English
    
  When I am on the languages page
  Then I should see "English"
    And I should see "English" within "dl.language"
    # And I should see the text with tags "<a href=\"/works\">1</a>"
  # And I should see the text with tags '<a href="/works">1</a>'
