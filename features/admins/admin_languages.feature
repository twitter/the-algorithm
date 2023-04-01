Feature: Manipulate languages on the Archive
  In order to be multicultural
  As as an admin
  I'd like to be able to manipulate languages on the Archive

Scenario: An admin can add a language

  Given basic languages
    And I am logged in as a "translation" admin
  When I go to the languages page
    And I follow "Add a Language"
    And I fill in "Name" with "Klingon"
    And I fill in "Abbreviation" with "tlh"
    And I press "Create Language"
  Then I should see "Language was successfully added."
    And I should see "The Archive supports these languages"
    And I should see "Klingon"

Scenario: Adding Abuse support for a language

  Given the following language exists
    | name        | short |
    | Arabic      | ar    |
    | Espanol     | es    |
  When I am logged in as a "translation" admin
    And I go to the languages page
    # Languages are sorted by short name, so the first "Edit" is for Arabic
    And I follow "Edit"
    And I check "Abuse support available"
    And I press "Update Language"
  Then I should see "Language was successfully updated."
  When I follow "Policy Questions & Abuse Reports"
  Then I should see "Arabic" within "select#abuse_report_language"
    And I should not see "Espanol" within "select#abuse_report_language"

Scenario: Adding a language to the Support form

  Given the following language exists
      | name     | short |
      | Sindarin | sj    |
      | Klingon  | tlh   |
  When I am logged in as a "translation" admin
    And I go to the languages page
    # Languages are sorted by short name, so the first "Edit" is for Sindarin
    And I follow "Edit"
    And I check "Support available"
    And I press "Update Language"
  Then I should see "Language was successfully updated."
  When I follow "Technical Support & Feedback"
  Then I should see "Sindarin" within "select#feedback_language"
    And I should not see "Klingon" within "select#feedback_language"
