@series
Feature: Locked and partially locked series
  In order to keep my works under the radar
  As a registered archive user
  I should be able to make my serial works visible only to other registered users

  Scenario: Post a series with a restricted work, then add a draft, then make the draft public and post it
    Given I am logged in as "fandomer"
      And I set up the draft "Humbug" as part of a series "Antiholidays"
      And I lock the work
      And I press "Post"
    Then I should see "Part 1 of Antiholidays"
    When I go to fandomer's series page
    Then I should see "Antiholidays"
    When I am logged out
     And I go to fandomer's series page
    Then I should not see "Antiholidays"
    When I am logged in as "reccer"
      And I go to fandomer's series page
    Then I should see "Antiholidays"
    When I view the series "Antiholidays"
    Then I should see "Humbug"
    When I am logged in as "fandomer"
      And I set up the draft "Antivalentine" as part of a series "Antiholidays"
      And I check "work_restricted"
      And I press "Preview"
    Then I should see "Draft was successfully created."
      And I should see "Part 2 of Antiholidays"
    When I view the series "Antiholidays"
    Then I should see "Works: 1"
      And I should not see "Antivalentine"
    When I view the work "Humbug"
    Then I should not see "Next Work →" within "dd"
    When I edit the work "Antivalentine"
      And I unlock the work
      And I press "Preview"
    Then I should see "Part 2 of Antiholidays"
    When I press "Post"
    Then I should see "Part 2 of Antiholidays"
      And I should not see the image "title" text "Restricted"
      And I should see "← Previous Work" within "dd.series"
    When I am logged out
      And I view the series "Antiholidays"
    Then I should see "Antivalentine"
      But I should not see "Humbug"
    When I view the work "Antivalentine"
    Then I should see "Part 1 of Antiholidays"
      And I should not see "Next Work →" within "dd"
    When I am logged in as "reccer"
      And I go to fandomer's series page
    Then I should see "Antiholidays"
    When I view the series "Antiholidays"
    Then I should see "Works: 2"
      And I should see "Humbug"
      And I should see "Antivalentine"

  Scenario: edit a locked work to add it to a series
    Given I am logged in as "fandomer"
      And I post the locked work "Boohoo"
    When I edit the work "Boohoo"
      And I uncheck "work_restricted"
      And I fill in "work_series_attributes_title" with "Antiholidays"
      And I press "Preview"
    Then I should see "Preview"
      And I should see "Part 1 of Antiholidays"
    When I press "Update"
    Then I should see "Work was successfully updated"
