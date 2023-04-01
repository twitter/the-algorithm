@users
Feature: Pseud dashboard
  In order to have an archive full of users
  As a humble user
  I want to write some works and see my dashboard

  Scenario: Fandoms on pseud dashboard

  Given the following activated user exists
    | login           | password   |
    | myself          | password   |
  Given the following activated tag wrangler exists
    | login  | password    |
    | Enigel | wrangulate! |

  # set up metatag and synonym

  When I am logged in as "Enigel" with password "wrangulate!"
    And a fandom exists with name: "Stargate SG-1", canonical: true
    And a fandom exists with name: "Stargatte SG-oops", canonical: false
    And a fandom exists with name: "Stargate Franchise", canonical: true
    And I edit the tag "Stargate SG-1"
  Then I should see "Edit Stargate SG-1 Tag"
    And I should see "MetaTags"
  When I fill in "MetaTags" with "Stargate Franchise"
    And I press "Save changes"
  Then I should see "Tag was updated"
  When I edit the tag "Stargatte SG-oops"
    And I fill in "Synonym" with "Stargate SG-1"
    And I press "Save changes"
  Then I should see "Tag was updated"

  When I log out
  Then I should see "Sorry, you don't have permission to access the page you were trying to reach. Please log in."

  # set up pseuds

  When I am logged in as "myself" with password "password"
    And I go to myself's pseuds page
  Then I should see "Default Pseud" within "div#main.pseuds-index"
  When I follow "New Pseud"
    And I fill in "Name" with "Me"
    And I check "pseud_is_default"
    And I fill in "Description" with "Something's cute"
    And I press "Create"
  Then I should see "Pseud was successfully created."

  # view main dashboard - when posting a work with the canonical, metatag and synonym should not be seen

  When I follow "myself"
  Then I should see "Dashboard"
    And I should see "You don't have anything posted under this name yet"
    And I should not see "Revenge of the Sith"
    And I should not see "Stargate"
  When I post the work "Revenge of the Sith"
    And I follow "myself"
  Then I should see "Stargate"
    And I should see "SG-1" within "#user-fandoms"
    And I should not see "Stargate Franchise"
    And I should not see "Stargatte SG-oops"

  # check on pseud that posted the work

  When I follow "Me" within ".pseud .expandable li"
  Then I should see "Stargate"
    And I should see "SG-1" within "#user-fandoms"
    And I should not see "Stargate Franchise"
    And I should not see "Stargatte SG-oops"

  # check on pseud that didn't post the work
  When I follow "myself" within "div#dashboard ul.expandable.secondary"
  Then I should not see "Stargate"
    And I should not see "SG-1"
    And I should not see "Stargate Franchise"
    And I should not see "Stargatte SG-oops"

  # now using the synonym - canonical should be seen, but metatag still not seen

  When I edit the work "Revenge of the Sith"
    And I fill in "Fandoms" with "Stargatte SG-oops"
    And I press "Preview"
    And I press "Update"
  Then I should see "Work was successfully updated"
  When I follow "myself"
  Then I should see "Stargate"
    And I should see "SG-1" within "#user-fandoms"
    And I should not see "Stargate Franchise"
    And I should not see "Stargatte SG-oops" within "#user-fandoms"
    And I should see "Stargatte SG-oops"

  # check on pseud that posted the work

  When I follow "Me" within ".pseud .expandable li"
  Then I should see "Stargate"
    And I should see "SG-1" within "#user-fandoms"
    And I should not see "Stargate Franchise"
    And I should not see "Stargatte SG-oops" within "#user-fandoms"
    And I should see "Stargatte SG-oops"

  # check on pseud that didn't post the work

  When I follow "myself" within "div#dashboard ul.expandable.secondary"
  Then I should not see "Stargate"
    And I should not see "SG-1"
    And I should not see "Stargate Franchise"
    And I should not see "Stargatte SG-oops"

