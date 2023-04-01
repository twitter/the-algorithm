@comments
Feature: Read official comments
  In order to tell genuine official accounts from impersonators
  As a user
  I'd like to see the "official" sign

Scenario: View official comments in homepage, inbox and works
  Given I am logged in as "normal_user"
    And I post the work "My very meta work about AO3"
    And the user "official_account" exists and has the role "official"
    And I am logged in as "official_account"
  When I post the comment ":)))))))" on the work "My very meta work about AO3"
  Then I should see "(Official)"
  When I am logged in as "normal_user"
    And I go to the home page
  Then I should see "(Official)"
  When I follow "My Inbox"
  Then I should see "(Official)"
  When I view the work "My very meta work about AO3" with comments
  Then I should see "(Official)"

Scenario: View fake official comments in homepage, inbox and works
  Given I am logged in as "normal_user"
    And I post the work "My very meta work about AO3"
    And I am logged in as "fake_official_account"
  When I post the comment ":)))))))" on the work "My very meta work about AO3"
  Then I should not see "(Official)"
  When I am logged in as "normal_user"
    And I go to the home page
  Then I should not see "(Official)"
  When I follow "My Inbox"
  Then I should not see "(Official)"
  When I view the work "My very meta work about AO3" with comments
  Then I should not see "(Official)"
