@works @search
Feature: Search restricted works
  As a user
  I want search results to only include works I can access

  Scenario: Search results for logged out users should contain only posted works
  that are public; they should not contain works that are drafts, restricted to
  registered users, or hidden by an admin
    Given a set of works with various access levels for searching
      And I am a visitor
    When I search for works containing "Work"
    Then I should see "You searched for: Work"
      And I should see "1 Found"
      And I should see "Posted Work"
      And I should not see "Restricted Work"
      And I should not see "Work Hidden by Admin"
      And I should not see "Draft Work"

  Scenario: Search results for logged in users should contain only posted works
  that are public or restricted to registered users; they should not contain
  drafts or works hidden by an admin
    Given a set of works with various access levels for searching
      And I am logged in as a random user
    When I search for works containing "Work"
    Then I should see "You searched for: Work"
      And I should see "2 Found"
      And I should see "Posted Work"
      And I should see "Restricted Work"
      And I should not see "Work Hidden by Admin"
      And I should not see "Draft Work"

  Scenario: Searching for restricted works only returns results for logged in
  users or admins
    Given a set of works with various access levels for searching
      And I am logged in as a random user
    When I search for works containing "restricted: true"
    Then I should see "You searched for: restricted: true"
      And I should see "1 Found"
      And the results should contain only the restricted work
    When I log out
      And I search for works containing "restricted: true"
    Then I should see "You searched for: restricted: true"
      And I should see "No results found."
    When I am logged in as an admin
      And I search for works containing "restricted: true"
    Then I should see "1 Found"
      And the results should contain only the restricted work
