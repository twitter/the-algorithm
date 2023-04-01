@skins
Feature: Admin manage skins

  Scenario: Users should not be able to see the admin skins page
  Given I am logged in as "skinner"
  When I go to admin's skins page
  Then I should see "I'm sorry, only an admin can look at that area"

  Scenario: Admin can cache and uncache a public skin
  Given basic skins
    And the approved public skin "public skin"
    And I am logged in as a "superadmin" admin
  When I follow "Approved Skins"
    And I check "Cache"
  Then I press "Update"
    And I should see "The following skins were updated: public skin"
  When I follow "Approved Skins"
    And I check "Uncache"
    And I press "Update"
  Then I should see "The following skins were updated: public skin"

  Scenario: Admin can add a public skin to the chooser and then remove it
  Given the approved public skin "public skin"
    And the skin "public skin" is cached
  When I am logged in as a "superadmin" admin
  Then I should not see the skin chooser
  When I follow "Approved Skins"
    And I check "Chooser"
    And I press "Update"
  Then I should see "The following skins were updated: public skin"
    And I should see the skin "public skin" in the skin chooser
  When I follow "Approved Skins"
    And I check "Not In Chooser"
    And I press "Update"
  Then I should see "The following skins were updated: public skin"
    And I should not see the skin chooser

  Scenario: An admin can reject and unreject a skin
  Given the unapproved public skin "public skin"
    And I am logged in as a "superadmin" admin
  When I go to admin's skins page
    And I check "make_rejected_public_skin"
    And I submit
  Then I should see "The following skins were updated: public skin"
  When I follow "Rejected Skins"
  Then I should see "public skin"
  When I check "make_unrejected_public_skin"
    And I submit
  Then I should see "The following skins were updated: public skin"
  When I follow "Rejected Skins"
  Then I should not see "public skin"

  Scenario: An admin can feature and unfeature skin
  Given the approved public skin "public skin"
    And I am logged in as a "superadmin" admin
  When I follow "Approved Skins"
    And I check "Feature"
    And I submit
  Then I should see "The following skins were updated: public skin"
  When I follow "Approved Skins"
    And I check "Unfeature"
    And I submit
  Then I should see "The following skins were updated: public skin"

  Scenario: Admins should be able to see public skins in the admin skins page
  Given the unapproved public skin "public skin"
    And I am logged in as a "superadmin" admin
  When I go to admin's skins page
  Then I should see "public skin" within "table#unapproved_skins"

  Scenario: Admins should not be able to edit unapproved skins
  Given the unapproved public skin "public skin"
    And I am logged in as a "superadmin" admin
  When I go to "public skin" skin page
  Then I should not see "Edit"
    And I should not see "Delete"
  When I go to "public skin" edit skin page
  Then I should see "Sorry, you don't have permission"

  Scenario: Admins should be able to approve public skins
  Given the unapproved public skin "public skin"
    And I am logged in as a "superadmin" admin
  When I go to admin's skins page
    And I check "public skin"
    And I submit
  Then I should see "The following skins were updated: public skin"
  When I follow "Approved Skins"
  Then I should see "public skin" within "table#approved_skins"

  Scenario: Admins should be able to edit but not delete public approved skins
  Given the approved public skin "public skin" with css "#title { text-decoration: blink;}"
    And I am logged in as a "superadmin" admin
  When I go to "public skin" skin page
  Then I should see "Edit"
    But I should not see "Delete"
  When I follow "Edit"
    And I fill in "CSS" with "#greeting.logged-in { text-decoration: blink;}"
    And I fill in "Description" with "Blinky love (admin modified)"
    And I submit
  Then I should see an update confirmation message
    And I should see "(admin modified)"
    And I should see "#greeting.logged-in"
    And I should not see "#title"
  Then the cache of the skin on "public skin" should expire after I save the skin

  Scenario: Admins should be able to unapprove public skins, which should also
  remove them from preferences
  Given "skinuser" is using the approved public skin "public skin" with css "#title { text-decoration: blink;}"
  When I unapprove the skin "public skin"
  Then I should see "The following skins were updated: public skin"
    And I should see "public skin" within "table#unapproved_skins"
  When I am logged in as "skinuser"
    And I am on skinuser's preferences page
  Then "Default" should be selected within "preference_skin_id"
    And I should not see "#title"
    And I should not see "text-decoration: blink;"

  Scenario: Admin can change the default skin
  Given basic skins
    And the approved public skin "strange skin" with css "#title { text-decoration: underline;}"
    And the approved public skin "public skin" with css "#title { text-decoration: blink;}"
    And I am logged in as "skinner"
    And the user "KnownUser" exists and is activated
  When I am on skinner's preferences page
    And I select "strange skin" from "preference_skin_id"
    And I submit
  Then I should see "{ text-decoration: underline; }" in the page style
  When I am logged in as a "superadmin" admin
  Then I should not see "{ text-decoration: blink; }" in the page style
  When I follow "Approved Skins"
    And I fill in "set_default" with "public skin"
    And I press "Update"
  Then I should see "Default skin changed to public skin"
    And I should see "{ text-decoration: blink; }" in the page style
  When I am logged in as "skinner"
  Then I should see "{ text-decoration: underline; }" in the page style
  # A user created before changing the default skin will still have the same skin
  When I am logged in as "KnownUser"
  Then I should not see "{ text-decoration: blink; }" in the page style
