@admin
Feature: Admin Actions to re-order questions in a FAQ Category
  As an an admin
  I want to be able to re-order the questions in a FAQ Category

Scenario: Re-order the questions in a FAQ Category
  Given I am logged in as an admin
    And I make a multi-question FAQ post
  When I go to the archive_faqs page
    And I follow "Standard FAQ Category"
    And I follow "Edit"
    And I follow "Reorder Questions"
    # First confirm the current order of the questions
  Then I should see "1. Number 1 Question."
    And I should see "2. Number 2 Question."
    And I should see "3. Number 3 Question."
    # Flip the order of the questions
    And I fill in "questions_1" with "3"
    And I fill in "questions_2" with "2"
    And I fill in "questions_3" with "1"
    And I press "Update Positions"
  When I follow "Edit"
    And I follow "Reorder Questions"
    # Confirm the questions are in the new reversed order
  Then I should see "1. Number 3 Question."
    And I should see "2. Number 2 Question."
    And I should see "3. Number 1 Question."

