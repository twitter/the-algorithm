@user @tag_wrangling
Feature: Brand new fandoms

  Background:
    # The external works form will error if we don't have basic tags, and since
    # we're trying to create a brand new tag, we just want to double-check that
    # it doesn't exist.
    Given basic tags
      And the tag "My Brand New Fandom" does not exist

  Scenario: Brand new fandoms should be visible on the Uncategorized Fandoms page.
    Given I am logged in as a random user
      And I post a work "My New Work" with fandom "My Brand New Fandom"
      And the periodic tag count task is run
    When I follow "Uncategorized Fandoms" within "#header"
    Then I should see "My Brand New Fandom"

  Scenario: Fandoms used only on external works should be visible on the Uncategorized Fandoms page.
    Given I am logged in as a random user
      And I set up an external work
      And I fill in "Fandoms" with "My Brand New Fandom"
      And I submit
      And the periodic tag count task is run
    When I follow "Uncategorized Fandoms" within "#header"
    Then I should see "My Brand New Fandom"

  Scenario: When the only work with a brand new fandom is destroyed, the fandom should not be visible on the Uncategorized Fandoms page.
    Given I am logged in as a random user
      And I post a work "My New Work" with fandom "My Brand New Fandom"
      And the periodic tag count task is run
    When I follow "Edit"
      And I follow "Delete Work"
      And I press "Yes"
    Then I should see "Your work My New Work was deleted."
    When the periodic tag count task is run
      And I follow "Uncategorized Fandoms" within "#header"
    Then I should not see "My Brand New Fandom"

  Scenario: When the only external work with a brand new fandom is destroyed, the fandom should not be visible on the Uncategorized Fandoms page.
    Given I am logged in as a random user
      And I set up an external work
      And I fill in "Title" with "External Work To Be Deleted"
      And I fill in "Fandoms" with "My Brand New Fandom"
      And I submit
      And the periodic tag count task is run
    When I am logged in as a "policy_and_abuse" admin
      And I view the external work "External Work To Be Deleted"
      And I follow "Delete External Work"
    Then I should see "Item was successfully deleted."
    When the periodic tag count task is run
      And I follow "Uncategorized Fandoms" within "#header"
    Then I should not see "My Brand New Fandom"

  Scenario: Brand new fandoms should be visible to wranglers.
    Given I am logged in as a tag wrangler
      And I post a work "My New Work" with fandom "My Brand New Fandom"
      And the periodic tag count task is run
    When I follow "Tag Wrangling" within "#header"
      And I follow "Fandoms by media"
    Then I should see "My Brand New Fandom"

  Scenario: Fandoms used only on external works should be visible to wranglers.
    Given I am logged in as a tag wrangler
      And I set up an external work
      And I fill in "Fandoms" with "My Brand New Fandom"
      And I submit
      And the periodic tag count task is run
    When I follow "Tag Wrangling" within "#header"
      And I follow "Fandoms by media"
    Then I should see "My Brand New Fandom"

  Scenario: When the only work with a brand new fandom is destroyed, the fandom should not be visible to tag wranglers.
    Given I am logged in as a tag wrangler
      And I post a work "My New Work" with fandom "My Brand New Fandom"
      And the periodic tag count task is run
    When I follow "Edit"
      And I follow "Delete Work"
      And I press "Yes"
    Then I should see "Your work My New Work was deleted."
    When the periodic tag count task is run
      And I follow "Tag Wrangling" within "#header"
      And I follow "Fandoms by media"
    Then I should not see "My Brand New Fandom"

  Scenario: When the only external work with a brand new fandom is destroyed, the fandom should not be visible to tag wranglers.
    Given I am logged in as a tag wrangler
      And I set up an external work
      And I fill in "Title" with "External Work To Be Deleted"
      And I fill in "Fandoms" with "My Brand New Fandom"
      And I submit
      And the periodic tag count task is run
    When I am logged in as a "policy_and_abuse" admin
      And I view the external work "External Work To Be Deleted"
      And I follow "Delete External Work"
    Then I should see "Item was successfully deleted."
    When the periodic tag count task is run
      And I am logged in as a tag wrangler
      And I follow "Tag Wrangling" within "#header"
      And I follow "Fandoms by media"
    Then I should not see "My Brand New Fandom"
