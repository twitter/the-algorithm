@works
Feature: Orphan work
  In order to have an archive full of works
  As an author
  I want to orphan works


  Background:
    Given I have an orphan account
      And the following activated users exists
      | login             | password   | email                     |
      | orphaneer         | password   | orphaneer@foo.com         |
      | author_subscriber | password   | author_subscriber@foo.com |
      And "author_subscriber" subscribes to author "orphaneer"
      And all emails have been delivered
      And I am logged in as "orphaneer"


  Scenario: Orphan a single work, using the default orphan_account

    Given I post the work "Shenanigans"
      And I view the work "Shenanigans"
    Then I should see "Edit"
    When I follow "Edit"
    Then I should see "Edit Work"
      And I should see "Orphan Work"
    When I follow "Orphan Work"
    Then I should see "Read More About The Orphaning Process"
    When I choose "Take my pseud off as well"
      And I press "Yes, I'm sure"
      And all indexing jobs have been run
    Then I should see "Orphaning was successful."
      And I should see "Bookmarks (0)"
    When I follow "Works (0)"
    Then I should not see "Shenanigans"
    When I view the work "Shenanigans"
    Then I should see "orphan_account"
      And I should not see "Delete"


  Scenario: Orphan a single work and add a copy of the pseud to the orphan_account

    Given I post the work "Shenanigans2"
    When I view the work "Shenanigans2"
    Then I should see "Edit"
    When I follow "Edit"
    Then I should see "Edit Work"
      And I should see "Orphan Work"
    When I follow "Orphan Work"
    Then I should see "Read More About The Orphaning Process"
    When I choose "Leave a copy of my pseud on"
    And I press "Yes, I'm sure"
    Then I should see "Orphaning was successful."
    When I am on my works page
    Then I should not see "Shenanigans2"
    When I view the work "Shenanigans2"
    Then I should see "orphaneer (orphan_account)"
      And I should not see "Delete"


  Scenario: Orphan a work (remove pseud) and don't notify subscribers to my account

    Given I post the work "Doomed Story"
      And I follow "Edit"
      And I follow "Orphan Work"
      And I choose "Take my pseud off as well"
      And I press "Yes, I'm sure"
    When subscription notifications are sent
    Then 0 emails should be delivered


  Scenario: Orphan a work (leave pseud) and don't notify subscribers to my account

    Given I post the work "Awful Concoction"
      And I follow "Edit"
      And I follow "Orphan Work"
      And I choose "Leave a copy of my pseud on"
      And I press "Yes, I'm sure"
    When subscription notifications are sent
    Then 0 emails should be delivered


  Scenario: Orphan a work (leave pseud) and don't notify subscribers to the work

    Given the following activated user exists
      | login             | password   | email                    |
      | work_subscriber   | password   | work_subscriber@foo.com  |
      And I post the work "Torrid Idfic"
      And I am logged in as "work_subscriber"
      And I view the work "Torrid Idfic"
      And I press "Subscribe"
      And a chapter is added to "Torrid Idfic"
      And I follow "Edit"
      And I follow "Orphan Work"
      And I choose "Leave a copy of my pseud on"
      And I press "Yes, I'm sure"
    When subscription notifications are sent
    Then 0 emails should be delivered


  Scenario: Orphan a work (leave pseud) and don't notify subscribers to the work's series

    Given the following activated user exists
      | login             | password   | email                     |
      | series_subscriber | password   | series_subscriber@foo.com |
      And I add the work "Lazy Purple Sausage" to series "Shame Series"
      And I am logged in as "series_subscriber"
      And I view the series "Shame Series"
      And I press "Subscribe"
      And I am logged in as "orphaneer"
      And I view the work "Lazy Purple Sausage"
      And I follow "Edit"
      And I follow "Orphan Work"
      And I choose "Leave a copy of my pseud on"
      And I press "Yes, I'm sure"
    When subscription notifications are sent
    Then 0 emails should be delivered

  Scenario: I can orphan multiple works at once
  Given I am logged in as "author"
    And I post the work "Glorious" with fandom "SGA"
    And I post the work "Excellent" with fandom "Star Trek"
    And I post the work "Lovely" with fandom "Steven Universe"
    And I go to my works page
  When I follow "Edit Works"
  Then I should see "Edit Multiple Works"
  When I select "Glorious" for editing
    And I select "Excellent" for editing
    And I press "Delete"
  Then I should see "Are you sure you want to delete these works PERMANENTLY?"
    And I should see "Glorious"
    And I should see "Excellent"
    And I should not see "Lovely"
  When I follow "Orphan Works Instead"
  Then I should see "Orphaning a work removes it from your account and re-attaches it to the specially created orphan_account."
  When I press "Yes, I'm sure"
    And all indexing jobs have been run
  Then I should see "Orphaning was successful."
  When I go to my works page
  Then I should not see "Glorious"
    And I should not see "Excellent"
    And I should see "Lovely"

  Scenario: Orphaning a shared work should not affect chapters created solely by the other creator

    Given I am logged in as "keeper"
      And I post the work "Half-Orphaned"
      And I add the co-author "orphaneer" to the work "Half-Orphaned"
      And I post a chapter for the work "Half-Orphaned"
    # Verify that the authorship has been set up properly
    Then "orphaneer" should be a co-creator of Chapter 1 of "Half-Orphaned"
      But "orphaneer" should not be a co-creator of Chapter 2 of "Half-Orphaned"
    When I am logged in as "orphaneer"
      And I orphan the work "Half-Orphaned"
    Then "orphan_account" should be a co-creator of Chapter 1 of "Half-Orphaned"
      But "orphan_account" should not be a co-creator of Chapter 2 of "Half-Orphaned"
