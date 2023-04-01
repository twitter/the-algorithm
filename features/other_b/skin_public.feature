@skins
Feature: Public skins

  Scenario: A user's initial skin should be set to default
  Given basic skins
    And I am logged in as "skinner"
  When I am on skinner's preferences page
  Then "Default" should be selected within "preference_skin_id"

  Scenario: User can set a skin for a session and then unset it
  Given basic skins
    And the approved public skin "public skin" with css "#title { text-decoration: blink;}"
    And the skin "public skin" is cached
    And the skin "public skin" is in the chooser
  When I am logged in as "skinner"
    And I follow "public skin"
  Then I should see "The skin public skin has been set. This will last for your current session."
    And the page should have the cached skin "public skin"
  When I follow "Default"
  Then I should see "You are now using the default Archive skin again!"
    And the page should not have the cached skin "public skin"

  Scenario: A user can't set an uncached public skin for a session
  Given the approved public skin "Uncached Public Skin"
    And I am logged in as "skinner"
  When I set the skin "Uncached Public Skin" for this session
  Then I should see "Sorry, but only certain skins can be used this way (for performance reasons). Please drop a support request if you'd like Uncached Public Skin to be added!"

  Scenario: Only public skins should be on the main skins page
  Given basic skins
    And I am logged in as "skinner"
    And I create the skin "my skin"
  When I am on the skins page
  Then I should not see "my skin"
    And I should see "Default"

  Scenario: Newly created public skins should not appear on the main skins page until
  approved and should be marked as not-yet-approved
  Given I am logged in as "skinner"
    And the unapproved public skin "public skin"
  When I am on the skins page
    Then I should not see "public skin"
  When I am on skinner's skins page
  Then I should see "public skin"
    And I should see "(Not yet reviewed)"
    And I should not see "(Approved)"

  Scenario: Public skins should not be viewable by users until approved
  Given the unapproved public skin "public skin"
    And I log out
  When I go to "public skin" skin page
    Then I should see "Sorry, you don't have permission"
  When I go to "public skin" edit skin page
    Then I should see "Sorry, you don't have permission"
  When I go to admin's skins page
    Then I should see "I'm sorry, only an admin can"

  Scenario: Users should not be able to edit their public approved skins
  Given the approved public skin "public skin"
    And I am logged in as "skinner"
  When I go to "public skin" edit skin page
  Then I should see "Sorry, you don't have permission"
  When I am on the skins page
  Then I should see "public skin"
  When I follow "Site Skins"
  Then I should see "public skin"
    And I should see "(Approved)"
    And I should not see "Edit"

  Scenario: Users should be able to use public approved skins created by others
  Given the approved public skin "public skin" with css "#title { text-decoration: blink;}"
    And I am logged in as "skinuser"
    And I am on skinuser's preferences page
  When I select "public skin" from "preference_skin_id"
    And I submit
  Then I should see "Your preferences were successfully updated."
  When I am on skinuser's preferences page
    And "public skin" should be selected within "preference_skin_id"
    And I should see "#title {" in the page style
    And I should see "text-decoration: blink;" in the page style

  Scenario: Toggle between public site skins and public work skins
  Given I am logged in as "skinner"
    And I am on skinner's skins page
  When I follow "Public Work Skins"
  Then I should see "Public Work Skins" within "h2"
  When I follow "Public Site Skins"
  Then I should see "Public Site Skins" within "h2"

  Scenario: Reverting to default skin when a custom skin is selected
  Given the approved public skin "public skin" with css "#title { text-decoration: blink;}"
    And I am logged in as "skinner"
    And I am on skinner's preferences page
    And I select "public skin" from "preference_skin_id"
    And I submit
  When I am on skinner's preferences page
    Then "public skin" should be selected within "preference_skin_id"
  When I go to skinner's skins page
    And I press "Revert to Default Skin"
  When I am on skinner's preferences page
    Then "Default" should be selected within "preference_skin_id"

  Scenario: A logged out user only sees cached skins on the public skins page
  Given the approved public skin "Uncached skin"
    And the approved public skin "Cached skin"
    And the skin "Cached skin" is cached
  When I go to the public skins page
  Then I should see "Cached skin"
    And I should not see "Uncached skin"

  Scenario: A user can preview a cached public site skin, and it will take the 
  user to the works page for a canonical tag with between 10 and 20 works
  Given the approved public skin "Usable Skin"
    And the skin "Usable Skin" is cached
    And the canonical fandom "Dallas" with 2 works
    And the canonical fandom "Major Crimes" with 11 works
    And the canonical fandom "Rizzoli and Isles" with 21 works
    And I am logged in as "skinner"
  When I go to the public skins page
    And I follow "Preview"
  Then I should be on the works tagged "Major Crimes"
    And I should see "You are previewing the skin Usable Skin. This is a randomly chosen page."
    And I should see "Go back or click any link to remove the skin"
    And I should see "Tip: You can preview any archive page you want by tacking on '?site_skin=[skin_id]' like you can see in the url above."
  When I follow "Return To Skin To Use"
  Then I should be on "Usable Skin" skin page
