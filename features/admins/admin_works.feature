@admin
Feature: Admin Actions for Works, Comments, Series, Bookmarks
  As an admin
  I should be able to perform special actions

  Scenario: Can troubleshoot works
    Given I am logged in as "regular_user"
      And I post the work "Just a work you know"
    When I am logged in as a "support" admin
      And I view the work "Just a work you know"
      And I follow "Troubleshoot"
      And I check "Reindex Work"
      And I press "Troubleshoot"
    Then I should see "Work sent to be reindexed."

  Scenario: Can hide works
    Given I am logged in as "regular_user"
      And I post the work "ToS Violation"
    When I am logged in as a "policy_and_abuse" admin
      And all emails have been delivered
      And I view the work "ToS Violation"
      And I follow "Hide Work"
    Then I should see "Item has been hidden."
      And logged out users should not see the hidden work "ToS Violation" by "regular_user"
      And logged in users should not see the hidden work "ToS Violation" by "regular_user"
      And "regular_user" should see their work "ToS Violation" is hidden
      And 1 email should be delivered
      And the email should contain "you will be required to take action to correct the violation"

  Scenario: Can unhide works
    Given I am logged in as "regular_user"
      And I post the work "ToS Violation"
    When I am logged in as a "policy_and_abuse" admin
      And I view the work "ToS Violation"
      And I follow "Hide Work"
      And all indexing jobs have been run
      And all emails have been delivered
    Then I should see "Item has been hidden."
    When I follow "Make Work Visible"
      And all indexing jobs have been run
    Then I should see "Item is no longer hidden."
      And logged out users should see the unhidden work "ToS Violation" by "regular_user"
      And logged in users should see the unhidden work "ToS Violation" by "regular_user"
      And 0 emails should be delivered

  Scenario: Can delete works
    Given I am logged in as "regular_user"
      And I post the work "ToS Violation"
    When I am logged in as a "policy_and_abuse" admin
      # Don't let the admin password email mess up the count.
      And all emails have been delivered
      And I view the work "ToS Violation"
      And I follow "Delete Work"
      And all indexing jobs have been run
    Then I should see "Item was successfully deleted."
      And 1 email should be delivered
      And the email should contain "deleted from the Archive by a site admin"
      And the email should not contain "translation missing"
    When I log out
      And I am on regular_user's works page
    Then I should not see "ToS Violation"
    When I am logged in
      And I am on regular_user's works page
    Then I should not see "ToS Violation"

  Scenario: Can hide bookmarks
    Given basic tags
      And I am logged in as "regular_user" with password "password1"
      And I post the work "A Nice Work"
    When I am logged in as "bad_user"
      And I view the work "A Nice Work"
    When I follow "Bookmark"
      And I fill in "bookmark_notes" with "Rude comment"
      And I press "Create"
      And all indexing jobs have been run
    Then I should see "Bookmark was successfully created"
    When I am logged in as a "policy_and_abuse" admin
      And I am on bad_user's bookmarks page
    When I follow "Hide Bookmark"
      And all indexing jobs have been run
    Then I should see "Item has been hidden."
    When I am logged in as "regular_user" with password "password1"
      And I am on bad_user's bookmarks page
    Then I should not see "Rude comment"

  Scenario: Can edit tags on works
    Given basic tags
      And I am logged in as "regular_user"
      And I post the work "Changes" with fandom "User-Added Fandom" with freeform "User-Added Freeform" with category "M/M"
    When I am logged in as a "policy_and_abuse" admin
      And I view the work "Changes"
      And I follow "Edit Tags and Language"
    When I select "Mature" from "Rating"
      And I uncheck "No Archive Warnings Apply"
      And I check "Choose Not To Use Archive Warnings"
      And I fill in "Fandoms" with "Admin-Added Fandom"
      And I fill in "Relationships" with "Admin-Added Relationship"
      And I fill in "Characters" with "Admin-Added Character"
      And I fill in "Additional Tags" with "Admin-Added Freeform"
      And I uncheck "M/M"
      And I check "Other"
    When I press "Post"
    Then I should not see "User-Added Fandom"
      And I should see "Admin-Added Fandom"
      And I should not see "User-Added Freeform"
      And I should see "Admin-Added Freeform"
      And I should not see "M/M"
      And I should see "Other"
      And I should not see "No Archive Warnings Apply"
      And I should see "Creator Chose Not To Use Archive Warnings"
      And I should not see "Not Rated"
      And I should see "Mature"
      And I should see "Admin-Added Relationship"
      And I should see "Admin-Added Character"
     When I follow "Activities"
     Then I should see "View Admin Activity"
     When I visit the last activities item
     Then I should see "No Archive Warnings Apply"
      And I should see "Old tags"
      And I should see "User-Added Fandom"
      And I should not see "Admin-Added Fandom"

  Scenario: Can edit external works
    Given basic tags
      And basic languages
      And I am logged in as "regular_user"
      And I bookmark the external work "External Changes"
    When I am logged in as a "policy_and_abuse" admin
      And I view the external work "External Changes"
      And I follow "Edit External Work"
    When I fill in "Creator" with "Admin-Added Creator"
      And I fill in "Title" with "Admin-Added Title"
      And I fill in "Creator's Summary" with "Admin-added summary"
      And I select "Mature" from "Rating"
      And I check "No Archive Warnings Apply"
      And I fill in "Fandoms" with "Admin-Added Fandom"
      And I fill in "Relationships" with "Admin-Added Relationship"
      And I fill in "Characters" with "Admin-Added Character"
      And I fill in "Additional Tags" with "Admin-Added Freeform"
      And I check "M/M"
      And I select "Deutsch" from "Language"
      And it is currently 1 second from now
    When I press "Update External work"
    Then I should see "Admin-Added Creator"
      And I should see "Admin-Added Title"
      And I should see "Admin-added summary"
      And I should see "Mature"
      And I should see "No Archive Warnings"
      And I should see "Admin-Added Fandom"
      And I should see "Admin-Added Character"
      And I should see "Admin-Added Freeform"
      And I should see "M/M"
      And I should see "Language: Deutsch"

  Scenario: Can delete external works
    Given basic tags
      And I am logged in as "regular_user"
      And I bookmark the external work "External Changes"
    When I am logged in as a "policy_and_abuse" admin
      And I view the external work "External Changes"
      And I follow "Delete External Work"
    Then I should see "Item was successfully deleted."

  Scenario: Can mark a comment as spam
    Given I have no works or comments
      And the following activated users exist
      | login         | password   |
      | author        | password   |
      | commenter     | password   |

    # set up a work with a genuine comment

    When I am logged in as "author"
      And I post the work "The One Where Neal is Awesome"
    When I am logged in as "commenter"
      And I view the work "The One Where Neal is Awesome"
      And I fill in "Comment" with "I loved this!"
      And I press "Comment"
    Then I should see "Comment created!"

    # comment from registered user cannot be marked as spam.
    # If registered user is spamming, this goes to Abuse team as ToS violation
    When I am logged in as a "policy_and_abuse" admin
    Then I should see "Successfully logged in"
    When I view the work "The One Where Neal is Awesome"
      And I follow "Comments (1)"
    Then I should not see "Spam" within "#feedback"

    # now mark a comment as spam
    When I post the comment "Would you like a genuine rolex" on the work "The One Where Neal is Awesome" as a guest
      And I am logged in as a "policy_and_abuse" admin
      And I view the work "The One Where Neal is Awesome"
      And I follow "Comments (2)"
    Then I should see "rolex"
      And I should see "Spam" within "#feedback"
      And it is currently 1 second from now
    When I follow "Spam" within "#feedback"
    # Can see link to unmark
    Then I should see "Not Spam"
      And I should see "Hide Comments (1)"
      # Admin can still see spam comment
      And I should see "rolex"
      And I should see "This comment has been marked as spam."
      # proper content should still be there
      And I should see "I loved this!"

    # user can't see spam comment
    When I log out
      And I view the work "The One Where Neal is Awesome"
    Then I should see "Comments (1)"
    When I follow "Comments (1)"
    Then I should not see "rolex"
      And I should see "I loved this!"

    # author can't see spam comment
    When I am logged in as "author" with password "password"
      And I view the work "The One Where Neal is Awesome"
    Then I should see "Comments (1)"
    When I follow "Comments (1)"
    Then I should not see "rolex"
      And I should see "I loved this!"

    # now mark comment as not spam
    When I am logged in as a "policy_and_abuse" admin
      And I view the work "The One Where Neal is Awesome"
      And I follow "Comments (1)"
      And I follow "Not Spam"
    Then I should see "Hide Comments (2)"
      And I should not see "Not Spam"
      And I should not see "This comment has been marked as spam."

    # user can see comment again
    When I log out
      And I view the work "The One Where Neal is Awesome"
    Then I should see "Comments (2)"
    When I follow "Comments (2)"
    Then I should see "rolex"
      And I should not see "This comment has been marked as spam."

    # author can see comment again
    When I am logged in as "author" with password "password"
      And I view the work "The One Where Neal is Awesome"
    Then I should see "Comments (2)"
    When I follow "Comments (2)"
    Then I should see "rolex"
      And I should not see "This comment has been marked as spam."

  Scenario: Admin can edit language on works when posting without previewing
    Given basic tags
      And basic languages
      And I am logged in as "regular_user"
      And I post the work "Wrong Language"
    When I am logged in as a "policy_and_abuse" admin
      And I view the work "Wrong Language"
      And I follow "Edit Tags and Language"
    Then I should see "Edit Work Tags and Language for "
    When I select "Deutsch" from "Choose a language"
      And I press "Post"
    Then I should see "Deutsch"
      And I should not see "English"

  Scenario: Admin can edit language on works when previewing first
    Given basic tags
      And basic languages
      And I am logged in as "regular_user"
      And I post the work "Wrong Language"
    When I am logged in as a "policy_and_abuse" admin
      And I view the work "Wrong Language"
      And I follow "Edit Tags and Language"
    When I select "Deutsch" from "Choose a language"
      And I press "Preview"
    Then I should see "Preview Tags and Language"
    When I press "Update"
    Then I should see "Deutsch"
      And I should not see "English"

  Scenario: can mark a work as spam
  Given the work "Spammity Spam"
    And I am logged in as a "policy_and_abuse" admin
    And I view the work "Spammity Spam"
  Then I should see "Mark As Spam"
  When I follow "Mark As Spam"
  Then I should see "marked as spam and hidden"
    And I should see "Mark Not Spam"
    And the work "Spammity Spam" should be marked as spam
    And the work "Spammity Spam" should be hidden

  Scenario: can mark a spam work as not-spam
  Given the spam work "Spammity Spam"
    And I am logged in as a "policy_and_abuse" admin
    And I view the work "Spammity Spam"
  Then I should see "Mark Not Spam"
  When I follow "Mark Not Spam"
  Then I should see "marked not spam and unhidden"
    And I should see "Mark As Spam"
    And the work "Spammity Spam" should not be marked as spam
    And the work "Spammity Spam" should not be hidden

  Scenario: Admin can hide a series (e.g. if the series description or notes contain a TOS Violation)
    Given I am logged in as "tosser"
      And I add the work "Legit Work" to series "Violation"
    When I am logged in as a "policy_and_abuse" admin
      And I view the series "Violation"
      And I follow "Hide Series"
    Then I should see "Item has been hidden."
      And I should see the image "title" text "Hidden by Administrator"
      And I should see "Make Series Visible"
    When I log out
      And I go to tosser's series page
    Then I should see "Series (0)"
      And I should not see "Violation"
    When I view the series "Violation"
    Then I should see "Sorry, you don't have permission to access the page you were trying to reach."
    When I am logged in as "other_user"
      And I go to tosser's series page
    Then I should see "Series (0)"
      And I should not see "Violation"
    When I view the series "Violation"
    Then I should see "Sorry, you don't have permission to access the page you were trying to reach."
    When I am logged in as "tosser"
      And I go to tosser's series page
    Then I should see "Series (0)"
      And I should not see "Violation"
    When I view the series "Violation"
    Then I should see the image "title" text "Hidden by Administrator"

  Scenario: Admin can un-hide a series
    Given I am logged in as "tosser"
      And I add the work "Legit Work" to series "Violation"
      And I am logged in as a "policy_and_abuse" admin
      And I view the series "Violation"
      And I follow "Hide Series"
    When I follow "Make Series Visible"
    Then I should see "Item is no longer hidden."
      And I should not see the image "title" text "Hidden by Administrator"
      And I should see "Hide Series"
    When I log out
      And I go to tosser's series page
    Then I should see "Series (1)"
      And I should see "Violation"
    When I view the series "Violation"
    Then I should see "Violation"
    When I am logged in as "other_user"
      And I go to tosser's series page
    Then I should see "Series (1)"
      And I should see "Violation"
    When I view the series "Violation"
    Then I should see "Violation"
    When I am logged in as "tosser"
      And I go to tosser's series page
    Then I should see "Series (1)"
      And I should see "Violation"
    When I view the series "Violation"
    Then I should see "Violation"

  Scenario: Admins can see when a work has too many tags
    Given the user-defined tag limit is 7
      And the work "Under the Limit"
      And the work "Over the Limit"
      And the work "Over the Limit" has 2 fandom tags
      And the work "Over the Limit" has 2 character tags
      And the work "Over the Limit" has 2 relationship tags
      And the work "Over the Limit" has 2 freeform tags
    When I am logged in as an admin
      And I view the work "Under the Limit"
    Then I should see "Over Tag Limit: No"
    When I view the work "Over the Limit"
    Then I should see "Over Tag Limit: Yes"

  Scenario: Policy abuse admins can see original work creators
    Given a work "Orphaned" with the original creator "orphaneer"
    When I am logged in as a "policy_and_abuse" admin
      And I view the work "Orphaned"
    Then I should see the original creator "orphaneer"
