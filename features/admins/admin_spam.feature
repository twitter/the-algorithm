@admin
Feature: Admin spam management
  In order to manage spam works
  As an an admin
  I want to be able to view and update works marked as spam

Scenario: Review spam
  Given the spam work "Spammity Spam"
    And the spam work "Totally Legit"
    And I am logged in as a "superadmin" admin
  Then I should see "Spam"
  When I follow "Spam"
  Then I should see "Works Marked as Spam"
    And I should see "Spammity"
    And I should see "Totally Legit"
  When I check "spam_1"
    And I check "ham_2"
    And I press "Update Works"
  Then I should not see "Spammity"
    And I should not see "Totally Legit"
    And the work "Spammity Spam" should be hidden
    And the work "Totally Legit" should not be hidden
