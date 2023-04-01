@series
Feature: Orphan series
  As an author
  I want to orphan a series full of works

  Background:
    Given I have an orphan account
      And I am logged in as "orphaneer"

  Scenario: Orphaning a series (remove pseud) should remove all references to the user from the series

    Given I add the work "Work to be Rued" to the series "My Biggest Mistakes"
      And I add the work "Regrettable Work" to the series "My Biggest Mistakes"

    When I orphan and take my pseud off the series "My Biggest Mistakes"
      And I am logged out
      And I view the series "My Biggest Mistakes"

    Then I should not see "orphaneer"

  Scenario: Orphaning a series (leave pseud) should change the authorship to the correct pseudonym of orphan_account

    Given I add the work "Work to be Rued" to the series "My Biggest Mistakes"
      And I add the work "Regrettable Work" to the series "My Biggest Mistakes"

    When I orphan and leave my pseud on the series "My Biggest Mistakes"
      And I view the series "My Biggest Mistakes"

    Then I should see "orphaneer (orphan_account)" within ".series.meta"
      And I should not see "Edit"

  Scenario: Orphaning a series should remove it from the user's series page

    Given I add the work "Work to be Rued" to the series "My Biggest Mistakes"
      And I add the work "Regrettable Work" to the series "My Biggest Mistakes"

    When I orphan and take my pseud off the series "My Biggest Mistakes"
      And I am on orphaneer's series page

    Then I should not see "My Biggest Mistakes"

  Scenario: Orphaning a work in a series with only one work should cause the series to be orphaned

    Given I add the work "Work to be Rued" to the series "My Biggest Mistakes"

    When I orphan the work "Work to be Rued"
      And I am logged out
      And I view the series "My Biggest Mistakes"

    Then I should see "orphan_account"
      And I should not see "orphaneer"

  Scenario: Orphaning one but not all of the works in a series should make the series co-created by orphan_account

    Given I add the work "Work to be Rued" to the series "My Biggest Mistakes"
      And I add the work "Work to be Kept" to the series "My Biggest Mistakes"

    When I orphan the work "Work to be Rued"

    Then "orphaneer" should be a co-creator of the series "My Biggest Mistakes"
      And "orphan_account" should be a co-creator of the series "My Biggest Mistakes"

  Scenario: When a user orphans a shared series, it should not change the byline for works that they didn't co-create

    # Set up a shared series where orphaneer is not listed as a creator on the second work
    Given I am logged in as "keeper"
      And I add the work "Shared Beginnings" to the series "Shared Series"
      And I add the co-author "orphaneer" to the work "Shared Beginnings"
      And I add the work "Keeper's Solo" to the series "Shared Series"

    # Double-check to make sure that we've set up the authorships correctly.
    Then "orphaneer" should be a co-creator of the series "Shared Series"
      And "orphaneer" should be a co-creator of the work "Shared Beginnings"
      But "orphaneer" should not be a co-creator of the work "Keeper's Solo"

    When I am logged in as "orphaneer"
      And I orphan the series "Shared Series"

    Then "orphan_account" should be a co-creator of the series "Shared Series"
      And "orphan_account" should be a co-creator of the work "Shared Beginnings"
      But "orphan_account" should not be a co-creator of the work "Keeper's Solo"
