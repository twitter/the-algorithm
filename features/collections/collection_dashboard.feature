@collections
Feature: Collection
  In order to browse a collection
  As a humble user
  I want to see a collection dashboard

  Scenario: When a collection has more works or bookmarks than the maximum displayed on dashboards (5), the listbox for that type of item should contain a link to the collection's page for that type of item (e.g. Works (6) or Bookmarks (10)).

  Given I have a collection "Dashboard Light" with name "dashboard_light"
    And I am logged in as "user"
  When I post the work "Work 1" in the collection "Dashboard Light"
    And I post the work "Work 2" in the collection "Dashboard Light"
    And I post the work "Work 3" in the collection "Dashboard Light"
    And I post the work "Work 4" in the collection "Dashboard Light"
    And I post the work "Work 5" in the collection "Dashboard Light"
    And I post the work "Work 6" in the collection "Dashboard Light"
  When I go to "Dashboard Light" collection's page
  Then I should see "Works (6)" within "#collection-works"
  When I follow "Works (6)" within "#collection-works"
    And I follow "Work 1"
    And I follow "Bookmark"
    And I fill in "bookmark_collection_names" with "dashboard_light"
    And I press "Create"
  Then I should see "Bookmark was successfully created."
    And I should see "Dashboard Light"
  When I follow "Dashboard Light"
    And I follow "Works (6)"
    And I follow "Work 2"
    And I follow "Bookmark"
    And I fill in "bookmark_collection_names" with "dashboard_light"
    And I press "Create"
  Then I should see "Bookmark was successfully created."
  When I go to "Dashboard Light" collection's page
    And I follow "Works (6)"
    And I follow "Work 3"
    And I follow "Bookmark"
    And I fill in "bookmark_collection_names" with "dashboard_light"
    And I press "Create"
  Then I should see "Bookmark was successfully created."
  When I go to "Dashboard Light" collection's page
    And I follow "Works (6)"
    And I follow "Work 4"
    And I follow "Bookmark"
    And I fill in "bookmark_collection_names" with "dashboard_light"
    And I press "Create"
    Then I should see "Bookmark was successfully created."
  When I go to "Dashboard Light" collection's page
    And I follow "Works (6)"
    And I follow "Work 5"
    And I follow "Bookmark"
    And I fill in "bookmark_collection_names" with "dashboard_light"
    And I press "Create"
  Then I should see "Bookmark was successfully created."
  When I go to "Dashboard Light" collection's page
    And I follow "Works (6)"
    And I follow "Work 6"
    And I follow "Bookmark"
    And I fill in "bookmark_collection_names" with "dashboard_light"
    And I press "Create"
  Then I should see "Bookmark was successfully created."
  When I go to "Dashboard Light" collection's page
  Then I should see "Dashboard Light"
    And I should see "Recent bookmarks"
    And I should see "Bookmarks (6)" within "#collection-bookmarks"
