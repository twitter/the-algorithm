@users
Feature: Pseuds

Scenario: creating pseud with unicode characters

  Given I am logged in as "myself"
    And I go to myself's user page
    And I follow "Profile" within "div#dashboard ul.navigation.actions"
    And I follow "Manage My Pseuds" within "div.user"
    And I follow "New Pseud" within "div#main.pseuds-index"
    And I fill in "Name" with "Àlice and Bôb"
    And I fill in "Description" with "special character name"
    And I fill in "Icon alt text:" with "special Alice"
    And I press "Create"
  Then I should see "Pseud was successfully created."
  When I follow "Edit Pseud"
  Then I should see "Àlice and Bôb"
    And I should not see "Alice"

Scenario: creating pseud with chinese characters

  Given I am logged in as "myself" with password "password"
    And I go to myself's user page
    And I follow "Profile" within "div#dashboard ul.navigation.actions"
    And I follow "Manage My Pseuds" within "div.user"
    And I follow "New Pseud" within "div#main.pseuds-index"
    And I fill in "Name" with "爱丽丝"
    And I press "Create"
  Then I should see "Pseud was successfully created."
  When I follow "Edit Pseud"
  Then I should see "爱丽丝"

Scenario: creating pseud with pinyin characters

  Given I am logged in as "myself" with password "password"
    And I go to myself's user page
    And I follow "Profile" within "div#dashboard ul.navigation.actions"
    And I follow "Manage My Pseuds" within "div.user"
    And I follow "New Pseud" within "div#main.pseuds-index"
    And I fill in "Name" with "Aì lì sī"
    And I press "Create"
  Then I should see "Pseud was successfully created."
  When I follow "Edit Pseud"
  Then I should see "Aì lì sī"

Scenario: creating pseud with japanese characters

  Given I am logged in as "myself" with password "password"
    And I go to myself's user page
    And I follow "Profile" within "div#dashboard ul.navigation.actions"
    And I follow "Manage My Pseuds" within "div.user"
    And I follow "New Pseud" within "div#main.pseuds-index"
    And I fill in "Name" with "アリス"
    And I press "Create"
  Then I should see "Pseud was successfully created."
  When I follow "Edit Pseud"
  Then I should see "アリス"

Scenario: creating pseud with russian characters

  Given I am logged in as "myself" with password "password"
    And I go to myself's user page
    And I follow "Profile" within "div#dashboard ul.navigation.actions"
    And I follow "Manage My Pseuds" within "div.user"
    And I follow "New Pseud" within "div#main.pseuds-index"
    And I fill in "Name" with "Алиса"
    And I press "Create"
  Then I should see "Pseud was successfully created."
  When I follow "Edit Pseud"
  Then I should see "Алиса"


Scenario: not creating pseuds with characters which break urls

  Given I am logged in as "myself" with password "password"
    And I go to myself's user page
    And I follow "Profile" within "div#dashboard ul.navigation.actions"
    And I follow "Manage My Pseuds" within "div.user"
    And I follow "New Pseud" within "div#main.pseuds-index"
    And I fill in "Name" with "Alice/Bob"
    And I press "Create"
  Then I should not see "Pseud was successfully created."
    And I should see "can contain letters, numbers, spaces, underscores, and dashes"

  Given I am logged in as "myself" with password "password"
    And I go to myself's user page
    And I follow "Profile" within "div#dashboard ul.navigation.actions"
    And I follow "Manage My Pseuds" within "div.user"
    And I follow "New Pseud" within "div#main.pseuds-index"
    And I fill in "Name" with "Alice & Bob"
    And I press "Create"
  Then I should not see "Pseud was successfully created."
    And I should see "can contain letters, numbers, spaces, underscores, and dashes"

  Given I am logged in as "myself" with password "password"
    And I go to myself's user page
    And I follow "Profile" within "div#dashboard ul.navigation.actions"
    And I follow "Manage My Pseuds" within "div.user"
    And I follow "New Pseud" within "div#main.pseuds-index"
    And I fill in "Name" with "Alice."
    And I press "Create"
  Then I should not see "Pseud was successfully created."
    And I should see "can contain letters, numbers, spaces, underscores, and dashes"

  Given I am logged in as "myself" with password "password"
    And I go to myself's user page
    And I follow "Profile" within "div#dashboard ul.navigation.actions"
    And I follow "Manage My Pseuds" within "div.user"
    And I follow "New Pseud" within "div#main.pseuds-index"
    And I fill in "Name" with "Alice?"
    And I press "Create"
  Then I should not see "Pseud was successfully created."
    And I should see "can contain letters, numbers, spaces, underscores, and dashes"

  Given I am logged in as "myself" with password "password"
    And I go to myself's user page
    And I follow "Profile" within "div#dashboard ul.navigation.actions"
    And I follow "Manage My Pseuds" within "div.user"
    And I follow "New Pseud" within "div#main.pseuds-index"
    And I fill in "Name" with "Alice#"
    And I press "Create"
  Then I should not see "Pseud was successfully created."
    And I should see "can contain letters, numbers, spaces, underscores, and dashes"

Scenario: not creating pseuds with other characters we don't allow

  Given I am logged in as "myself" with password "password"
    And I go to myself's user page
    And I follow "Profile" within "div#dashboard ul.navigation.actions"
    And I follow "Manage My Pseuds" within "div.user"
    And I follow "New Pseud" within "div#main.pseuds-index"
    And I fill in "Name" with "Alice + Bob"
    And I press "Create"
  Then I should not see "Pseud was successfully created."
    And I should see "can contain letters, numbers, spaces, underscores, and dashes"

Scenario: not creating pseuds with more characters we don't allow

  Given I am logged in as "myself" with password "password"
    And I go to myself's user page
    And I follow "Profile" within "div#dashboard ul.navigation.actions"
    And I follow "Manage My Pseuds" within "div.user"
    And I follow "New Pseud" within "div#main.pseuds-index"
    And I fill in "Name" with "Alice 'Bob'"
    And I press "Create"
  Then I should not see "Pseud was successfully created."
    And I should see "can contain letters, numbers, spaces, underscores, and dashes"

Scenario: not creating pseuds with even more characters we don't allow

  Given I am logged in as "myself" with password "password"
    And I go to myself's user page
    And I follow "Profile" within "div#dashboard ul.navigation.actions"
    And I follow "Manage My Pseuds" within "div.user"
    And I follow "New Pseud" within "div#main.pseuds-index"
    And I fill in "Name" with "Alice (Bob)"
    And I press "Create"
  Then I should not see "Pseud was successfully created."
    And I should see "can contain letters, numbers, spaces, underscores, and dashes"



