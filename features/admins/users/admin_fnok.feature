@admin
Feature: Admin Fannish Next Of Kind actions
  In order to manage user accounts
  As an an admin
  I want to be able to manage fannish next of kin for users

  Scenario: A valid Fannish Next of Kin is added for a user
    Given the following activated users exist
      | login    | password   |
      | harrykim | diesalot   |
      | libby    | stillalive |
      And I am logged in as a "support" admin
    When I go to the abuse administration page for "harrykim"
      And I fill in "Fannish next of kin's username" with "libby"
      And I fill in "Fannish next of kin's email" with "testy@foo.com"
      And I press "Update Fannish Next of Kin"
    Then I should see "Fannish next of kin was updated."

    When I go to the manage users page
      And I fill in "Name" with "harrykim"
      And I press "Find"
    Then I should see "libby"

    When I follow "libby"
    Then I should be on libby's user page

  Scenario: An invalid Fannish Next of Kin username is added
    Given the fannish next of kin "libby" for the user "harrykim"
      And I am logged in as a "support" admin
    When I go to the abuse administration page for "harrykim"
      And I fill in "Fannish next of kin's username" with "userididnotcreate"
      And I press "Update Fannish Next of Kin"
    Then I should see "Kin can't be blank"

  Scenario: A blank Fannish Next of Kin username can't be added
    Given the fannish next of kin "libby" for the user "harrykim"
      And I am logged in as a "support" admin
    When I go to the abuse administration page for "harrykim"
      And I fill in "Fannish next of kin's username" with ""
      And I press "Update Fannish Next of Kin"
    Then I should see "Kin can't be blank"

  Scenario: A blank Fannish Next of Kin email can't be added
    Given the fannish next of kin "libby" for the user "harrykim"
      And I am logged in as a "support" admin
    When I go to the abuse administration page for "harrykim"
      And I fill in "Fannish next of kin's email" with ""
      And I press "Update Fannish Next of Kin"
    Then I should see "Kin email can't be blank"

  Scenario: A Fannish Next of Kin is edited
    Given the fannish next of kin "libby" for the user "harrykim"
      And the user "newlibby" exists and is activated
      And I am logged in as a "support" admin
    When I go to the abuse administration page for "harrykim"
      And I fill in "Fannish next of kin's username" with "newlibby"
      And I fill in "Fannish next of kin's email" with "newlibby@foo.com"
      And I press "Update Fannish Next of Kin"
    Then I should see "Fannish next of kin was updated."

  Scenario: A Fannish Next of Kin is removed
    Given the fannish next of kin "libby" for the user "harrykim"
      And I am logged in as a "support" admin
    When I go to the abuse administration page for "harrykim"
      And I fill in "Fannish next of kin's username" with ""
      And I fill in "Fannish next of kin's email" with ""
      And I press "Update Fannish Next of Kin"
    Then I should see "Fannish next of kin was removed."

  Scenario: A Fannish Next of Kin updates when the next of kin user changes their username
    Given the fannish next of kin "libby" for the user "harrykim"
      And I am logged in as "libby"
    When I visit the change username page for libby
      And I fill in "New user name" with "newlibby"
      And I fill in "Password" with "password"
      And I press "Change User Name"
    Then I should get confirmation that I changed my username
    When I am logged in as a "support" admin
      And I go to the manage users page
      And I fill in "Name" with "harrykim"
      And I press "Find"
    Then I should see "newlibby"

  Scenario: A Fannish Next of Kin stays with the user when the user changes their username
    Given the fannish next of kin "libby" for the user "harrykim"
      And I am logged in as "harrykim"
    When I visit the change username page for harrykim
      And I fill in "New user name" with "harrykim2"
      And I fill in "Password" with "password"
      And I press "Change User Name"
    Then I should get confirmation that I changed my username
    When I am logged in as a "support" admin
      And I go to the manage users page
      And I fill in "Name" with "harrykim2"
      And I press "Find"
    Then I should see "libby"

  Scenario: A Fannish Next of Kin can update even after an invalid user is entered
    Given the fannish next of kin "libby" for the user "harrykim"
      And the user "harrysmom" exists and is activated
      And I am logged in as a "support" admin
    When I go to the abuse administration page for "harrykim"
      And I fill in "Fannish next of kin's username" with "libbylibby"
      And I fill in "Fannish next of kin's email" with "libbylibby@example.com"
      And I press "Update Fannish Next of Kin"
    Then I should see "Kin can't be blank"
      And the "Fannish next of kin's username" input should be blank
      And I should see "libbylibby@example.com" in the "Fannish next of kin's email" input
    When I go to the abuse administration page for "harrykim"
      And I should see "libby" in the "Fannish next of kin's username" input
      And I should see "fnok@example.com" in the "Fannish next of kin's email" input
    When I fill in "Fannish next of kin's username" with "harrysmom"
      And I fill in "Fannish next of kin's email" with "harrysmom@example.com"
      And I press "Update Fannish Next of Kin"
    Then I should see "Fannish next of kin was updated."
      And the "Fannish next of kin's username" field should contain "harrysmom"
      And the "Fannish next of kin's email" field should contain "harrysmom@example.com"
