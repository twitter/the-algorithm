@users
Feature:
  In order to correct mistakes or reflect my evolving personality
  As a registered user
  I should be able to change my user name

  Scenario: The user should not be able to change username without a password
    Given I am logged in as "testuser" with password "password"
    When I visit the change username page for testuser
    And I fill in "New user name" with "anothertestuser"
      And I press "Change User Name"
    # TODO - better written error message
    Then I should see "Your password was incorrect"

  Scenario: The user should not be able to change their username with an incorrect password
    Given I am logged in as "testuser" with password "password"
    When I visit the change username page for testuser
      And I fill in "New user name" with "anothertestuser"
      And I fill in "Password" with "wrongpwd"
      And I press "Change User Name"
    Then I should see "Your password was incorrect"

  Scenario: The user should not be able to change their username to another user's name
    Given I have no users
      And the following activated user exists
      | login     | password |
      | otheruser | secret   |
      And I am logged in as "downthemall" with password "password"
    When I visit the change username page for downthemall
      And I fill in "New user name" with "otheruser"
      And I fill in "Password" with "password"
    When I press "Change"
      Then I should see "User name has already been taken"

  Scenario: The user should not be able to change their username to another user's name even if the capitalization is different
    Given I have no users
      And the following activated user exists
      | login     | password |
      | otheruser | secret   |
      And I am logged in as "downthemall" with password "password"
    When I visit the change username page for downthemall
      And I fill in "New user name" with "OtherUser"
      And I fill in "Password" with "password"
      And I press "Change User Name"
    Then I should see "User name has already been taken"

  Scenario: The user should be able to change their username if username and password are valid
    Given I am logged in as "downthemall" with password "password"
    When I visit the change username page for downthemall
      And I fill in "New user name" with "DownThemAll"
      And I fill in "Password" with "password"
      And I press "Change"
    Then I should get confirmation that I changed my username
      And I should see "Hi, DownThemAll!"

  Scenario: The user should be able to change their username to a similar version with underscores
    Given I am logged in as "downthemall" with password "password"
    When I visit the change username page for downthemall
      And I fill in "New user name" with "Down_Them_All"
      And I fill in "Password" with "password"
      And I press "Change User Name"
    Then I should get confirmation that I changed my username
      And I should see "Hi, Down_Them_All!"

  Scenario: Changing my user name with one pseud changes that pseud
    Given I have no users
      And I am logged in as "oldusername" with password "password"
    When I visit the change username page for oldusername
      And I fill in "New user name" with "newusername"
      And I fill in "Password" with "password"
      And I press "Change User Name"
    Then I should get confirmation that I changed my username
      And I should see "Hi, newusername"
    When I go to my pseuds page
      Then I should not see "oldusername"
    When I follow "Edit"
    Then I should see "You cannot change the pseud that matches your user name"
    Then the "pseud_is_default" checkbox should be checked and disabled

  Scenario: Changing only the capitalization of my user name with one pseud changes that pseud's capitalization
    Given I have no users
      And I am logged in as "uppercrust" with password "password"
    When I visit the change username page for uppercrust
      And I fill in "New user name" with "Uppercrust"
      And I fill in "Password" with "password"
      And I press "Change User Name"
    Then I should get confirmation that I changed my username
      And I should see "Hi, Uppercrust"
    When I go to my pseuds page
      Then I should not see "uppercrust"
    When I follow "Edit"
    Then I should see "You cannot change the pseud that matches your user name"
    Then the "pseud_is_default" checkbox should be checked and disabled

  Scenario: Changing my user name with two pseuds, one same as new, doesn't change old
    Given I have no users
      And the following activated user exists
      | login         | password | id |
      | oldusername   | secret   | 1  |
      And a pseud exists with name: "newusername", user_id: 1
      And I am logged in as "oldusername" with password "secret"
    When I visit the change username page for oldusername
      And I fill in "New user name" with "newusername"
      And I fill in "Password" with "secret"
      And I press "Change User Name"
    Then I should get confirmation that I changed my username
      And I should see "Hi, newusername"
    When I follow "Pseuds (2)"
      Then I should see "Edit oldusername"
      And I should see "Edit newusername"

  Scenario: Changing username updates search results (bug AO3-3468)
    Given I have no users
      And I am logged in as "oldusername" with password "password"
      And I post a work "Epic story"
      And I wait 1 second
    When I visit the change username page for oldusername
      And I fill in "New user name" with "newusername"
      And I fill in "Password" with "password"
      And I press "Change User Name"
      And all indexing jobs have been run
    Then I should get confirmation that I changed my username
    When I am on the works page
    Then I should see "newusername"
      And I should see "Epic story"
      And I should not see "oldusername"
    When I search for works containing "oldusername"
    Then I should see "No results found"
      And I should not see "Epic story"
    When I search for works containing "newusername"
    Then I should see "Epic story"

  Scenario: Comments reflect username changes after the cache expires in a week
    Given the work "Interesting"
      And I am logged in as "before" with password "password"
      And I add the pseud "mine"
    When I set up the comment "Wow!" on the work "Interesting"
      And I select "mine" from "comment[pseud_id]"
      And I press "Comment"
      And I view the work "Interesting" with comments
    Then I should see "mine (before)"
    When I visit the change username page for before
      And I fill in "New user name" with "after"
      And I fill in "Password" with "password"
      And I press "Change User Name"
      And I view the work "Interesting" with comments
    Then I should see "mine (before)"
    When it is currently 7 days from now
      And I view the work "Interesting" with comments
    Then I should not see "mine (before)"
