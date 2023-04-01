@skins
Feature: Non-public site and work skins

  Scenario: A user should be able to create a skin with CSS
  Given I am logged in as "skinner"
  When I am on the new skin page
    And I fill in "Title" with "my blinking skin"
    And I fill in "CSS" with "#title { text-decoration: blink;}"
    And I submit
  Then I should see "Skin was successfully created"
    And I should see "my blinking skin skin by skinner"
    And I should see "text-decoration: blink;"
    And I should see "(No Description Provided)"
    And I should see "by skinner"
    But I should see "Use"
    And I should see "Delete"
    And I should see "Edit"
    And I should not see "Stop Using"
    And I should not see "(Approved)"
    And I should not see "(Not yet reviewed)"

  Scenario: A logged-out user should not be able to create skins.
  Given I am a visitor
  When I go to the new skin page
    Then I should see "Sorry, you don't have permission"

  Scenario: A user should be able to select one of their own non-public skins to use in
  their preferences
  Given I am logged in as "skinner"
    And I create the skin "my blinking skin" with css "#title { text-decoration: blink;}"
  When I am on skinner's preferences page
    And I select "my blinking skin" from "preference_skin_id"
    And I submit
  Then I should see "Your preferences were successfully updated."
    And I should see "#title {" in the page style
    And I should see "text-decoration: blink;" in the page style

  Scenario: A user should be able to select one of their own non-public skins to use in
  their My Skins page
  Given I am logged in as "skinner"
    And I create the skin "my blinking skin" with css "#title { text-decoration: blink;}"
  Then I should see "my blinking skin"
    And I should see "Use"
  When I press "Use"
  Then I should see "#title {" in the page style
    And I should see "text-decoration: blink;" in the page style

  Scenario: Skin titles should be unique
  Given I am logged in as "skinner"
  When I am on the new skin page
    And I fill in "Title" with "Default"
    And I submit
  Then I should see "must be unique"

  Scenario: The user who creates a skin should be able to edit it
  Given I am logged in as "skinner"
    And I create the skin "my skin"
  When I follow "Edit"
    And I fill in "CSS" with "#greeting { text-decoration: blink;}"
    And I submit
  Then I should see an update confirmation message

  Scenario: Users should be able to create and use a work skin
  Given I am logged in as "skinner"
    And the default ratings exist
  When I am on the new skin page
    And I select "Work Skin" from "skin_type"
    And I fill in "Title" with "Awesome Work Skin"
    And I fill in "Description" with "Great work skin"
    And I fill in "CSS" with "p {color: purple;}"
    And I submit
  Then I should see "Skin was successfully created"
    And I should see "#workskin p"
  When I go to the new work page
  Then I should see "Awesome Work Skin"
  When I set up the draft "Story With Awesome Skin"
    And I select "Awesome Work Skin" from "work_work_skin_id"
    And I press "Preview"
  Then I should see "Preview"
    And I should see "color: purple" in the page style
  When I press "Post"
  Then I should see "Story With Awesome Skin"
    And I should see "color: purple" in the page style
    And I should see "Hide Creator's Style"
  When I follow "Hide Creator's Style"
  Then I should see "Story With Awesome Skin"
    And I should not see "color: purple"
    And I should not see "Hide Creator's Style"
    And I should see "Show Creator's Style"
  Then the cache of the skin on "Awesome Work Skin" should expire after I save the skin

  Scenario: log out from my skins page (Issue 2271)
  Given I am logged in as "skinner"
    And I am on my user page
  When I follow "Skins"
    And I log out
  Then I should be on the login page

  Scenario: Create a complex replacement skin using Archive skin components
  Given I have loaded site skins
    And I am logged in as "skinner"
  When I set up the skin "Complex"
    And I select "replace archive skin entirely" from "What it does:"
    And I check "Load Archive Skin Components"
    And I submit
  Then I should see a create confirmation message
    And I should see "We've added all the archive skin components as parents. You probably want to remove some of them now!"
  When I check "Load Archive Skin Components"
    And I submit
  Then I should see errors

  Scenario: Vendor-prefixed properties should be allowed
    Given basic skins
      And I am logged in as "skinner"
    When I am on the new skin page
      And I fill in "Title" with "skin with prefixed property"
      And I fill in "CSS" with ".myclass { -moz-box-sizing: border-box; -webkit-transition: opacity 2s; }"
      And I submit
    Then I should see "Skin was successfully created"
    Then the cache of the skin on "skin with prefixed property" should expire after I save the skin

  Scenario: #workskin selector prefixing
    Given basic skins
      And I am logged in as "skinner"
    When I am on the new skin page
      And I select "Work Skin" from "skin_type"
      And I fill in "Title" with "#worksin prefixing"
      And I fill in "CSS" with "#workskin, #workskin a, #workskin:hover, #workskin *, .prefixme, .prefixme:hover, * .prefixme { color: red; }"
      And I submit
    Then I should not see "#workskin #workskin,"
      And I should not see "#workskin #workskin a"
      And I should see ", #workskin a,"
      And I should not see "#workskin #workskin:hover"
      And I should see "#workskin .prefixme,"
      And I should see "#workskin .prefixme:hover"
      And I should see "#workskin * .prefixme"

  Scenario: New skin form should have the correct skin type pre-selected
    Given I am logged in as "skinner"
    When I am on the skins page
      And I follow "Create Site Skin"
    Then "Site Skin" should be selected within "skin_type"
    When I am on the skins page
      And I follow "My Work Skins"
      And I follow "Create Work Skin"
    Then "Work Skin" should be selected within "skin_type"

  Scenario: Skin type should persist and remain selectable if you encounter errors
  during creation
    Given I am logged in as "skinner"
    When I am on the skins page
      And I follow "My Work Skins"
      And I follow "Create Work Skin"
      And I fill in "Title" with "invalid skin"
      And I fill in "CSS" with "this is invalid css"
      And I submit
    Then I should see errors
      And "Work Skin" should be selected within "skin_type"
    When I select "Site Skin" from "skin_type"
      And I fill in "CSS" with "still invalid css"
      And I submit
    Then I should see errors
      And "Site Skin" should be selected within "skin_type"

  Scenario: View toggle buttons on skins (Issue 3197)
  Given basic skins
    And I am logged in as "skinner"
  When I am on skinner's skins page
  Then I should see "My Site Skins"
    And I should see "My Work Skins"
    And I should see "Public Site Skins"
    And I should see "Public Work Skins"

  Scenario: Toggle between user's work skins and site skins
  Given basic skins
    And I am logged in as "skinner"
    And I am on skinner's skins page
  When I follow "My Work Skins"
  Then I should see "My Work Skins"
    When I follow "My Site Skins"
  Then I should see "My Site Skins"

  Scenario: The cache should be flushed with a parent and not when unrelated
  Given I have loaded site skins
    And I am logged in as "skinner"
  When I set up the skin "Complex"
    And I select "replace archive skin entirely" from "What it does:"
    And I check "Load Archive Skin Components"
    And I submit
  Then I should see a create confirmation message
   When I am on the new skin page
    And I fill in "Title" with "my blinking skin"
    And I fill in "CSS" with "#title { text-decoration: blink;}"
    And I submit
  Then I should see "Skin was successfully created"
    And the cache of the skin on "my blinking skin" should not expire after I save "Complex"
    And the cache of the skin on "Complex" should expire after I save a parent skin

  Scenario: Users should be able to create skins using @media queries
  Given I am logged in as "skinner"
    And I set up the skin "Media Query Test Skin"
    And I check "only screen and (max-width: 42em)"
    And I check "only screen and (max-width: 62em)"
  When I press "Submit"
  Then I should see a create confirmation message
    And I should see "only screen and (max-width: 42em), only screen and (max-width: 62em)"
  When I press "Use"
  Then the page should have a skin with the media query "only screen and (max-width: 42em), only screen and (max-width: 62em)"

  Scenario: A user should be able to delete a skin
  Given I am logged in as "skinner"
    And I create the skin "Ugly Skin"
  When I go to "Ugly Skin" skin page
    And I follow "Delete"
    And I press "Yes, Delete Skin"
  Then I should see "The skin was deleted."
    And I should be on skinner's skins page
    And I should not see "Ugly Skin"

  Scenario: A user's skin should be reset to the default if they delete the skin they
  are using
  Given I am logged in as "skinner"
    And I create the skin "Ugly Skin"
    And I change my skin to "Ugly Skin"
  When I go to skinner's skins page
    And I follow "Delete"
    And I press "Yes, Delete Skin"
  Then I should see "The skin was deleted."
  When I go to skinner's preferences page
  Then "Default" should be selected within "preference_skin_id"

  Scenario: A user can't make a skin with "Archive" in the title
  Given I am logged in as "skinner"
    And I set up the skin "My Archive Skin" with some css
  When "AO3-4820" is fixed
    # And I press "Submit"
  # Then I should see "You can't use the word 'archive' in your skin title, sorry! (We have to reserve it for official skins.)"

  Scenario: A user can't look at another user's skins
  Given the user "scully" exists and is activated
    And I am logged in as "skinner"
  When I go to scully's skins page
  Then I should see "You can only browse your own skins and approved public skins."
    And I should be on the public skins page

  Scenario: A user can't use fixed positioning in a work skin
  Given I am logged in as "skinner"
  When I set up the skin "Work Skin" with css ".selector {position: fixed; top: 0;}"
    And I select "Work Skin" from "Type"
    And I submit
  Then I should see "The position property in .selector cannot have the value fixed in Work skins, sorry!"

  Scenario: User should be able to access their site and work skins from an
  individual skin's show page
  Given I am logged in as "skinner"
    And I create the skin "my skin"
  When I view the skin "my skin"
  Then I should see "My Site Skins"
    And I should see "My Work Skins"

  Scenario: User should be able to revert to the default skin from an individual
  skin's show page
  Given basic skins
    And I am logged in as "skinner"
    And I create the skin "my skin"
  When I view the skin "my skin"
  Then I should not see a "Revert to Default Skin" button
  When I press "Use"
    And I view the skin "my skin"
  Then I should see a "Revert to Default Skin" button
    And I should see "My Work Skins"

  Scenario: User should be able to access their site and work skins from an
  individual skin's edit page
  Given I am logged in as "skinner"
    And I create the skin "my skin"
  When I edit the skin "my skin"
  Then I should see "My Site Skins"
    And I should see "My Work Skins"

  Scenario: User should be able to revert to the default skin from an individual 
  skin's edit page
  Given basic skins
    And I am logged in as "skinner"
    And I create the skin "my skin"
  When I edit the skin "my skin"
  Then I should not see a "Revert to Default Skin" button
  When I change my skin to "my skin"
    And I edit the skin "my skin"
  Then I should see a "Revert to Default Skin" button

  Scenario: When a cached skin is the child of a cached skin, and the parent is updated, the child reflects the changes to the parent
    Given I am logged in as "skin_maker"
      And I have a skin "Child Skin" with a parent "Parent Skin"
      And the skin "Child Skin" is cached
      And the skin "Parent Skin" is cached
      And I change my skin to "Child Skin"
    # Only admins can edit cached skins:
    When I am logged in as a "superadmin" admin
      And I edit the skin "Parent Skin"
      And I fill in "CSS" with "body { background: cyan; }"
      And I press "Update"
    Then the filesystem cache of the skin "Child Skin" should include "background: cyan;"
    When I am logged in as "skin_maker"
    Then the page should have the cached skin "Child Skin"

  Scenario: When a cached skin is the child of an uncached skin, and the parent is updated, the child reflects the changes to the parent
    Given I am logged in as "skin_maker"
      And I have a skin "Child Skin" with a parent "Parent Skin"
      And the skin "Child Skin" is cached
      And I change my skin to "Child Skin"
    When I edit the skin "Parent Skin"
      And I fill in "CSS" with "body { background: cyan; }"
      And I press "Update"
    Then the filesystem cache of the skin "Child Skin" should include "background: cyan;"
      And the page should have the cached skin "Child Skin"

  Scenario: When an uncached skin is the child of a cached skin, and the parent is updated, the child reflects the changes to the parent
    Given I am logged in as "skin_maker"
      And I have a skin "Child Skin" with a parent "Parent Skin"
      And the skin "Parent Skin" is cached
      And I change my skin to "Child Skin"
    # Only admins can edit cached skins:
    When I am logged in as a "superadmin" admin
      And I edit the skin "Parent Skin"
      And I fill in "CSS" with "body { background: cyan; }"
      And I press "Update"
    Then the filesystem cache of the skin "Parent Skin" should include "background: cyan;"
    When I am logged in as "skin_maker"
    Then the page should have the cached skin "Parent Skin"

  Scenario: When an uncached skin is the child of an uncached skin, and the parent is updated, the child reflects the changes to the parent
    Given I am logged in as "skin_maker"
      And I have a skin "Child Skin" with a parent "Parent Skin"
      And I change my skin to "Child Skin"
    When I edit the skin "Parent Skin"
      And I fill in "CSS" with "body { background: cyan; }"
      And I press "Update"
    Then I should see "background: cyan;"
