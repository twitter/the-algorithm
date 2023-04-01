@bookmarks @search
Feature: Browse Bookmarks

  Scenario: Bookmarks appear on both the user's bookmark page and on the bookmark page for the pseud they used create the bookmark

    Given I am logged in as "ethel"
      And I add the pseud "aka"
      And I bookmark the work "Bookmarked with Default Pseud"
      And I bookmark the work "Bookmarked with Other Pseud" as "aka"
    When I go to ethel's bookmarks page
    Then I should see "Bookmarked with Default Pseud"
      And I should see "Bookmarked with Other Pseud"
    When I go to the bookmarks page for user "ethel" with pseud "ethel"
    Then I should see "Bookmarked with Default Pseud"
      And I should not see "Bookmarked with Other Pseud"
    When I go to the bookmarks page for user "ethel" with pseud "aka"
    Then I should see "Bookmarked with Other Pseud"
      And I should not see "Bookmarked with Default Pseud"
