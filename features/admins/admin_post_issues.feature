@admin
Feature: Admin Actions to Post Known Issues
  As an an admin
  I want to be able to report known issues

  Scenario: Post known issues
    When I am logged in as an admin
      And I follow "Admin Posts"
      And I follow "Known Issues" within "#header"
      And I follow "make a new known issues post"
      And I fill in "known_issue_title" with "First known problem"
      And I fill in "content" with "This is a bit of a problem"
      # Suspect related to issue 2458
      And I press "Post"
    Then I should see "Known issue was successfully created"
      And I should see "First known problem"
    When I follow "Admin Posts"
      And I follow "Known Issues" within "#header"
      And I follow "Show"
    Then I should see "First known problem"

  Scenario: Edit known issues
    Given I have posted known issues
    When I edit known issues
    Then I should see "Known issue was successfully updated"
      And I should not see "First known problem"
      And I should see "This is a bit of a problem, and this is too"

  Scenario: Delete known issues
    Given I have posted known issues
    When I delete known issues
    Then I should not see "First known problem"
