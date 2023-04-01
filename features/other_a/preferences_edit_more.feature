@users
Feature: Preferences

  Scenario: View and edit preferences - show/hide mature content warning

  Given I am logged in as "mywarning1"
    And I post the work "Adult Work by mywarning1" with rating "Mature"
  When I am logged in as "mywarning2"
    And I go to my preferences page
    And I check "Show me adult content without checking."
    And I press "Update"
  Then I should see "Your preferences were successfully updated"
  When I go to the works page
    And I follow "Adult Work by mywarning1"
  Then I should not see "adult content"
    And I should see "Rating: Mature"
  When I go to my preferences page
    And I uncheck "Show me adult content without checking."
    And I press "Update"
  Then I should see "Your preferences were successfully updated"
  When I go to the works page
    And I follow "Adult Work by mywarning1"
  Then I should see "adult content"
    And I should not see "Rating: Mature"

  Scenario: set preference to hide custom css on stories
  Given basic tags
    And basic skins
    And I am logged in as "tasteless"
  When I set up the draft "Big and Loud"
    And I select "Basic Formatting" from "work_work_skin_id"
    And I press "Preview"
    And I press "Post"
    And I go to the "Big and Loud" work page
  Then I should see "#workskin .font-murkyyellow" in the page style
    And I should see "Hide Creator's Style"
  When I go to my preferences page
  Then the "Hide work skins (you can still choose to show them)." checkbox should not be checked
  When I check "Hide work skins (you can still choose to show them)."
    And I press "Update"
  When I go to the "Big and Loud" work page
  Then I should not see "#workskin .font-murkyyellow"
    And I should not see "Hide Creator's Style"
    And I should see "Show Creator's Style"
  When I follow "Creator's Style"
  Then I should see "#workskin .font-murkyyellow" in the page style
    And I should see "Hide Creator's Style"
  Given I am logged out
    And I am logged in as "tasteful"
    And I go to the "Big and Loud" work page

  Scenario: Hidden users' user, works, and series pages are disallowed for search engine indexing
    Given I am logged in as "hidden"
      And the user "hidden" is hidden from search engines
      And I post the work "Hidden Work" as part of a series "Hidden Series"
      And I am logged out
    When I go to hidden's user page
      Then the page should be hidden from search engines
    When I view the work "Hidden Work"
      Then the page should be hidden from search engines
    When I view the series "Hidden Series"
      Then the page should be hidden from search engines

  Scenario: Unhidden users' user, works, and series pages are allowed for search engine indexing
    Given I am logged in as "unhidden"
      And I post the work "Unhidden Work" as part of a series "Unhidden Series"
      And I am logged out
    When I go to unhidden's user page
      Then the page should not be hidden from search engines
    When I view the work "Unhidden Work"
      Then the page should not be hidden from search engines
    When I view the series "Unhidden Series"
      Then the page should not be hidden from search engines