@works, @users
Feature: Orphan account
  In order to have an archive full of works
  As an author
  I want to orphan all works in my account

Scenario: Orphan all works belonging to a user
  Given I have an orphan account
  And the following activated user exists
    | login         | password   |
    | orphaneer     | password   |
    And I am logged in as "orphaneer" with password "password"
  When I post the work "Shenanigans"
  And I post the work "Shenanigans 2"
  And I post the work "Shenanigans - the early years"
  When I go to orphaneer's user page
  Then I should see "Recent works"
    And I should see "Shenanigans"
    And I should see "Shenanigans 2"
    And I should see "Shenanigans - the early years"
  When I follow "Preferences"
  When I follow "Orphan My Works"
  Then I should see "Orphan All Works"
    And I should see "Are you really sure you want to"
  When I choose "Take my pseud off as well"
	And I press "Yes, I'm sure"
  Then I should see "Orphaning was successful."
  When I view the work "Shenanigans"
  Then I should see "orphan_account"
    And I should not see "orphaneer" within ".userstuff"
  When I view the work "Shenanigans 2"
  Then I should see "orphan_account"
    And I should not see "orphaneer" within ".userstuff"
  When I view the work "Shenanigans - the early years"
  Then I should see "orphan_account"
    And I should not see "orphaneer" within ".userstuff"

Scenario: Orphan all works belonging to a user, add a copy of the pseud to the orphan_account
Given I have an orphan account
  And the following activated user exists
  | login         | password   |
  | orphaneer     | password   |
  And I am logged in as "orphaneer" with password "password"
  When I post the work "Shenanigans"
  When I post the work "Shenanigans 2"
  When I post the work "Shenanigans - the early years"
  When I go to orphaneer's user page
  Then I should see "Recent works"
    And I should see "Shenanigans"
    And I should see "Shenanigans 2"
    And I should see "Shenanigans - the early years"
  When I follow "Preferences"
  When I follow "Orphan My Works"
  Then I should see "Orphan All Works"
    And I should see "Are you really sure you want to"
  When I choose "Leave a copy of my pseud on"
	And I press "Yes, I'm sure"
  Then I should see "Orphaning was successful."
  When I view the work "Shenanigans"
  Then I should see "orphaneer (orphan_account)"
    And I should not see "orphaneer" within ".userstuff"
  When I view the work "Shenanigans 2"
  Then I should see "orphaneer (orphan_account)"
    And I should not see "orphaneer" within ".userstuff"
  When I view the work "Shenanigans - the early years"
  Then I should see "orphaneer (orphan_account)"
    And I should not see "orphaneer" within ".userstuff"
