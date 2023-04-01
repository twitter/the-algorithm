@works
Feature: Orphan pseud
  In order to have an archive full of works
  As an author
  I want to orphan all works under one pseud
  # TODO: Expand this to cover a user who has more than one pseud, and check that works on the other pseud don't get orphaned

  Scenario: Orphan all works belonging to one pseud
    Given I have an orphan account
    And the following activated user exists
      | login         | password   |
      | orphanpseud   | password   |
      And I am logged in as "orphanpseud" with password "password"
    When I post the work "Shenanigans"
      And I post the work "Shenanigans 2"
    When I follow "orphanpseud" within ".byline"
    Then I should see "Shenanigans 2 by orphanpseud"
    When I follow "Back To Pseuds"
    Then I should see "orphanpseud"
      And I should see "2 works"
    When I follow "Orphan Works"
    Then I should see "Orphan All Works by orphanpseud"
    When I choose "Take my pseud off as well"
      And I press "Yes, I'm sure"
    Then I should see "Orphaning was successful."
    When I view the work "Shenanigans"
    Then I should see "orphan_account"
      And I should not see "orphanpseud" within ".userstuff"
    When I view the work "Shenanigans 2"
    Then I should see "orphan_account"
      And I should not see "orphanpseud" within ".userstuff"

  Scenario: Orphan all works belonging to one pseud, add a copy of the pseud to the orphan_account
    Given I have an orphan account
    And the following activated user exists
      | login         | password   |
      | orphanpseud   | password   |
      And I am logged in as "orphanpseud" with password "password"
    When I post the work "Shenanigans"
    When I post the work "Shenanigans 2"
    When I follow "orphanpseud" within ".byline"
    Then I should see "Shenanigans by orphanpseud"
      And I should see "Shenanigans 2 by orphanpseud"
    When I follow "Back To Pseuds"
    Then I should see "orphanpseud"
      And I should see "2 works"
    When I follow "Orphan Works"
    Then I should see "Orphan All Works by orphanpseud"
    When I choose "Leave a copy of my pseud on"
      And I press "Yes, I'm sure"
    Then I should see "Orphaning was successful."
    When I view the work "Shenanigans"
    Then I should see "orphanpseud (orphan_account)"
      And I should not see "orphanpseud" within ".userstuff"
    When I view the work "Shenanigans 2"
    Then I should see "orphanpseud (orphan_account)"
      And I should not see "orphanpseud" within ".userstuff"

  Scenario: Orphan a pseud with works co-created by another pseud
    Given I have an orphan account
      And I am logged in as "halfandhalf"
      And I add the pseud "To Be Kept"
      And I add the pseud "To Be Orphaned"

    When I set up the draft "Treasure"
      And I unselect "halfandhalf" from "Creator/Pseud(s)"
      And I select "To Be Kept" from "Creator/Pseud(s)"
      And I press "Post"
    Then I should see "To Be Kept" within ".byline"
    When I set up the draft "Trash"
      And I unselect "halfandhalf" from "Creator/Pseud(s)"
      And I select "To Be Orphaned" from "Creator/Pseud(s)"
      And I press "Post"
    Then I should see "To Be Orphaned" within ".byline"
    When I set up the draft "Half and Half"
      And I unselect "halfandhalf" from "Creator/Pseud(s)"
      And I select "To Be Kept" from "Creator/Pseud(s)"
      And I select "To Be Orphaned" from "Creator/Pseud(s)"
      And I press "Post"
    Then I should see "To Be Kept" within ".byline"
      And I should see "To Be Orphaned" within ".byline"

    When I go to my pseuds page
      And I follow "Orphan Works by To Be Orphaned"
    Then I should see "Orphan All Works by To Be Orphaned"
    When I choose "Take my pseud off as well"
      And I wait 2 seconds
      And I press "Yes, I'm sure"
    Then I should see "Orphaning was successful."

    When I view the work "Trash"
    Then I should see "orphan_account" within ".byline"
      But I should not see "halfandhalf" within ".byline"
    When I view the work "Treasure"
    Then I should see "To Be Kept (halfandhalf)" within ".byline"
      But I should not see "orphan_account" within ".byline"
    When I view the work "Half and Half"
    Then I should see "To Be Kept (halfandhalf)" within ".byline"
      And I should see "orphan_account" within ".byline"
      But I should not see "To Be Orphaned (halfandhalf)" within ".byline"
