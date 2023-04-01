@admin @faqs
Feature: Rearrange Archive FAQs
  In order to manage the Archive FAQs
  As an admin
  I want to be able to reorder the FAQs

  Scenario: Rearrange FAQs
    Given I am logged in as an admin
      And 3 Archive FAQs exist
    When I go to the FAQ reorder page
      And I fill in "archive_faqs_1" with "3"
      And I fill in "archive_faqs_2" with "1"
      And I fill in "archive_faqs_3" with "2"
      And I press "Update Positions"
    Then I should see "Archive FAQs order was successfully updated"
    When I follow "Reorder FAQs"
    Then I should see "1. The 2 FAQ"
      And I should see "2. The 3 FAQ"
      And I should see "3. The 1 FAQ"
