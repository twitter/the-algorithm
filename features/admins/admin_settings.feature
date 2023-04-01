@admin
Feature: Admin Settings Page
  In order to improve performance
  As an admin
  I want to be able to control downloading and tag wrangling.

  Scenario: Turn off downloads
    Given downloads are off
      And I have a work "Storytime"
    When I log out
      And I view the work "Storytime"
    Then I should not see "Download"
    When I am logged in as "tester"
      And I view the work "Storytime"
    Then I should not see "Download"

  Scenario: Turn off tag wrangling
    Given tag wrangling is off
      And the following activated tag wrangler exists
        | login           |
        | dizmo           |
      And a canonical character "Ianto Jones"
    When I am logged in as "dizmo"
      And I edit the tag "Ianto Jones"
    Then I should see "Wrangling is disabled at the moment. Please check back later."
      And I should not see "Synonym of"
    
  Scenario: Turn off Support form
    Given the support form is disabled and its text field set to "Please don't contact us"
    When I am logged in as a random user
      And I go to the support page
    Then I should see "Please don't contact us"

  Scenario: Turn on Support form
    Given the support form is enabled
    When I am logged in as a random user
      And I go to the support page
    Then I should see "We can answer Support inquiries in"
