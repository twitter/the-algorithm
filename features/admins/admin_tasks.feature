@admin
Feature: Admin tasks
  Scenario: admin goes to the Support page

  Given I am logged in as an admin
  Then cookie "admin_credentials" should be like "1"
  When I go to the support page
  Then I should see "Support and Feedback"
    And I should see "testadmin@example.org" in the "feedback_email" input
  When I log out
    And I am on the home page
  Then cookie "admin_credentials" should be deleted
