@works
Feature: Display work notes
  In order to provide information about my work
  As an author
  I want to be able to add notes to my work

  Scenario: User posts a work without notes
    Given basic tags
      And I am logged in as a random user
      And I post the work "Work Without Notes"
    When I view the work "Work Without Notes"
    Then I should not see "Notes:"

  Scenario: User posts a work with beginning notes
    Given basic tags
      And I am logged in as a random user
      And I post the work "Work with Beginning Notes"
    When I add the beginning notes "These are my beginning notes. There are no recipients, approved translations, inspirations, or claims here." to the work "Work with Beginning Notes"
      And I view the work "Work with Beginning Notes"
    Then I should see "Notes:"
      And I should see "These are my beginning notes. There are no recipients, approved translations, inspirations, or claims here."
      And I should not find a list for associations

  Scenario: User posts a work with end notes
    Given basic tags
      And I am logged in as a random user
      And I post the work "Work with End Notes"
    When I add the beginning notes "These are my end notes." to the work "Work with End Notes"
      And I view the work "Work with End Notes"
    Then I should see "Notes:"
      And I should see "These are my end notes."
