@works @search

Feature: Locking works to archive users only
  In order to keep my works under the radar
  As a registered archive user
  I should be able to make my works visible only to other registered users

Scenario: Posting locked work
    Given I am logged in as "fandomer" with password "password"
      And basic tags
      And I go to the new work page
      And I select "Not Rated" from "Rating"
      And I check "No Archive Warnings Apply"
      And I select "English" from "Choose a language"
      And I fill in "Fandoms" with "Supernatural"
      And I fill in "Characters" with "Sammy"
      And I fill in "Work Title" with "Awesomeness"
      And I fill in "content" with "The story of how they met and how they got into trouble"
      And I lock the work
    When I press "Preview"
    
    # shows as restricted
    Then I should see the image "title" text "Restricted" within "h2.title"
    When I post the work
    Then I should see the image "alt" text "(Restricted)" within "h2.title"
    When I go to the works tagged "Supernatural"
    Then I should see "Awesomeness" within "h4"
    And I should see the image "alt" text "(Restricted)" within "h4"
    When all indexing jobs have been run
      And I fill in "site_search" with "Awesomeness"
      And I press "Search"
    Then I should see "1 Found"
      And I should see "fandomer" within "#main"
      
    # doesn't show when logged out
    When I am logged out
      And I go to the works tagged "Supernatural"
    Then I should not see "Awesomeness"
      And I should not see the image "alt" text "(Restricted)"
    When I am on fandomer's works page
    Then I should not see "Awesomeness"
    When I fill in "site_search" with "Awesomeness"
      And I press "Search"
    Then I should see "No results found"
      And I should not see "fandomer"
    
    # shows again if you log in as another user
    When I am logged in as "testuser" with password "password"
      And I am on fandomer's works page
    Then I should see "Awesomeness"

Scenario: Editing posted work
    Given I am logged in as "fandomer" with password "password"
      And I post the work "Sad generic work"
      And all indexing jobs have been run
    When I am logged out
      And I go to fandomer's works page
    Then I should see "Sad generic work"
    When I am logged in as "fandomer" with password "password"
      And I edit the work "Sad generic work"
      And I lock the work
      And I fill in "Fandoms" with "Supernatural"
    When I press "Preview"
    Then I should see the image "title" text "Restricted" within "h2.title"
    When I update the work
    Then I should see the image "alt" text "(Restricted)" within "h2.title"
    When I go to the works tagged "Supernatural"
    Then I should see "Sad generic work" within "h4"
      And I should see the image "alt" text "(Restricted)" within "h4"
    When I am logged out
      And I go to the works page
    Then I should not see "Sad generic work"
      And I should not see the image "alt" text "(Restricted)"
    When I am logged in as "fandomer" with password "password"
      And I edit the work "Sad generic work"
      And I fill in "Notes" with "Random blather"
      And I press "Preview"
    Then I should see the image "alt" text "(Restricted)" within "h2.title"
    When I update the work
    Then I should see "Work was successfully updated."
      And I should see the image "alt" text "(Restricted)" within "h2.title"
    When I edit the work "Sad generic work"
      And I unlock the work
      And I press "Preview"
    Then I should not see the image "alt" text "(Restricted)"
    When I update the work
    Then I should see "Work was successfully updated."
      And I should not see the image "alt" text "(Restricted)"
    When I am logged out
      And I go to the works page
    Then I should see "Sad generic work"
