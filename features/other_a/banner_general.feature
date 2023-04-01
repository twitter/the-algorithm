@users
Feature: General notice banner

Scenario: Banner is blank until admin sets it
  Given there are no banners
  Then a logged-in user should not see a banner
    And a logged-out user should not see a banner

Scenario: Admin can set a banner
  Given there are no banners
  When an admin creates an active banner
  Then a logged-in user should see the banner
    And a logged-out user should see the banner

Scenario: Admin can set an alert banner
  Given there are no banners
    And an admin creates an active "alert" banner
  When I am logged in as "whatever"
  Then a logged-in user should see the "alert" banner
    And a logged-out user should see the "alert" banner

Scenario: Admin can set an event banner
  Given there are no banners
  When an admin creates an active "event" banner
  Then a logged-in user should see the "event" banner
    And a logged-out user should see the "event" banner

Scenario: Admin can edit an active banner
  Given there are no banners
    And an admin creates an active banner
  When an admin edits the active banner
  Then a logged-in user should see the edited active banner
    And a logged-out user should see the edited active banner

Scenario: Admin can deactivate a banner
  Given there are no banners
    And an admin creates an active banner
  When an admin deactivates the banner
  Then a logged-in user should not see a banner
    And a logged-out user should not see a banner

Scenario: User can turn off banner using "×" button
  Given there are no banners
    And an admin creates an active banner
  When I turn off the banner
  Then the page should not have a banner

Scenario: Banner stays off when logging out and in again
  Given there are no banners
    And an admin creates an active banner
    And I turn off the banner
  When I am logged out
    And I am logged in as "newname"
  Then the page should not have a banner
  
Scenario: Logged out user can turn off banner
  Given there are no banners
    And an admin creates an active banner
    And I am logged out
  When I follow "×"
  Then the page should not have a banner
   
Scenario: User can turn off banner in preferences
  Given there are no banners
    And an admin creates an active banner
    And I am logged in as "banner_tester"
    And I set my preferences to turn off the banner showing on every page
  When I go to my user page
  Then the page should not have a banner

Scenario: User can turn off banner in preferences, but will still see a banner when an admin deactivates the existing banner and sets a new banner
  Given there are no banners
    And an admin creates an active banner
    And I am logged in as "banner_tester_2"
  When I set my preferences to turn off the banner showing on every page
    And I go to my user page
  Then the page should not have a banner
  When an admin deactivates the banner
    And an admin creates a different active banner
  When I am logged in as "banner_tester_2"
  Then the page should have the different banner
  
Scenario: Admin can delete a banner and it will no longer be shown to users
  Given there are no banners
    And an admin creates an active banner
  When I am logged in as a "communications" admin
    And I am on the admin_banners page
    And I follow "Delete"
    And I press "Yes, Delete Banner"
  Then I should see "Banner successfully deleted."
    And a logged-in user should not see a banner
    And a logged-out user should not see a banner

Scenario: Admin should not have option to make minor updates on a new banner
  Given there are no banners
    And I am logged in as a "communications" admin
  When I am on the new_admin_banner page
  Then I should not see "This is a minor update (Do not turn the banner back on for users who have dismissed it)"

Scenario: Admin should not have option to make minor updates on banner that is not active
  Given there are no banners
    And an admin creates a banner
  When I am logged in as a "communications" admin
    And I am on the admin_banners page
    And I follow "Edit"
  Then I should not see "This is a minor update (Do not turn the banner back on for users who have dismissed it)"

Scenario: Admin can make minor changes to the text of an active banner without turning it back on for users who have already dismissed it
  Given there are no banners
    And an admin creates an active banner
    And I am logged in as "banner_tester_3"
    And I set my preferences to turn off the banner showing on every page
    And an admin makes a minor edit to the active banner
  When I am logged in as "banner_tester_3"
  Then I should not see the banner with minor edits
    And the page should not have a banner
  When I am logged out
  Then I should see the banner with minor edits
  When I am logged in as "banner_tester_4"
  Then I should see the banner with minor edits
