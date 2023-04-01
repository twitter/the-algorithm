@comments
Feature: Commenting on admin posts
  As a user
  I want to comment on admin posts
  In order to communicate with admins and other users

  Scenario: Random user comments on an admin post
    Given I have posted an admin post
      And I am logged in as "regular"
      And all emails have been delivered
    When I comment on an admin post
    Then "regular" should not be emailed

  Scenario: A user who receives copies of their own comments comments on an admin post
    Given I have posted an admin post
      And I am logged in as "narcis"
      And I set my preferences to turn on copies of my own comments
      And all emails have been delivered
    When I comment on an admin post
    Then 1 email should be delivered to "narcis"

  Scenario: Random user edits a comment on an admin post
    Given I have posted an admin post
      And I am logged in as "regular"
      And I comment on an admin post
      And all emails have been delivered
    When I edit a comment
    Then "regular" should not be emailed

  Scenario: A user who receives copies of their own comments edits a comment on an admin post
    Given I have posted an admin post
      And I am logged in as "narcis"
      And I set my preferences to turn on copies of my own comments
      And I comment on an admin post
      And all emails have been delivered
    When I edit a comment
    Then 1 email should be delivered to "narcis"

  Scenario: Admin post with comments disabled
    Given I have posted an admin post
      And I am logged in as a "communications" admin
    When I go to the admin-posts page
      And I follow "Edit"
    Then I should see "Who can comment on this"
      And I should see "No one can comment"
    When I choose "No one can comment"
      And I press "Post"
    Then I should see "successfully updated"
    When I follow "Edit Post"
    Then the "No one can comment" radio button should be checked
    When I am logged out
      And I go to the admin-posts page
      And I follow "Default Admin Post"
    Then I should see "Sorry, this news post doesn't allow comments."
    When I am logged in as "regular"
      And I go to the admin-posts page
      And I follow "Default Admin Post"
    Then I should see "Sorry, this news post doesn't allow comments."

  Scenario: Admin post with comments restricted to Archive users
    Given I have posted an admin post
      And I am logged in as a "communications" admin
    When I go to the admin-posts page
      And I follow "Edit"
    Then I should see "Who can comment on this"
      And I should see "Only registered users can comment"
    When I choose "Only registered users can comment"
      And I press "Post"
    Then I should see "successfully updated"
    When I follow "Edit Post"
    Then the "Only registered users can comment" radio button should be checked
    When I am logged out
      And I go to the admin-posts page
      And I follow "Default Admin Post"
    Then I should see "Sorry, this news post doesn't allow non-Archive users to comment."
      And I should see "You can however contact Support with any feedback or questions."
    When I follow "contact Support"
      Then I should be on the support page
    When I am logged in as "regular"
      And I go to the admin-posts page
      And I follow "Default Admin Post"
      And I fill in "comment[comment_content]" with "zug zug"
      And I press "Comment"
    Then I should see "Comment created!"
      And I should see "zug zug"

  Scenario: Admin post with all comments enabled
    Given I have posted an admin post
      And I am logged in as a "communications" admin
    When I go to the admin-posts page
      And I follow "Edit"
    Then I should see "Who can comment on this"
      And I should see "Registered users and guests can comment"
    When I choose "Registered users and guests can comment"
      And I press "Post"
    Then I should see "successfully updated"
    When I follow "Edit Post"
    Then the "Registered users and guests can comment" radio button should be checked
    When I am logged out
      And I go to the admin-posts page
      And I follow "Default Admin Post"
      And I fill in "comment[name]" with "tester"
      And I fill in "comment[email]" with "tester@example.com"
      And I fill in "comment[comment_content]" with "guz guz"
      And I press "Comment"
    Then I should see "Comment created!"
      And I should see "guz guz"
    When I am logged in as "regular"
      And I go to the admin-posts page
      And I follow "Default Admin Post"
      And I fill in "comment[comment_content]" with "zug zug"
      And I press "Comment"
    Then I should see "Comment created!"
      And I should see "zug zug"

  Scenario: Modifying the comment permissions of an admin post with translations
    Given I have posted an admin post
      And basic languages
      And I am logged in as a "translation" admin
    When I make a translation of an admin post
      And I follow "Back to AO3 News Index"
      And I follow "Edit"
      And I choose "Only registered users can comment"
      And I press "Post"
    Then I should see "Sorry, this news post doesn't allow non-Archive users to comment."
    When I follow "Deutsch"
    Then I should see "Sorry, this news post doesn't allow non-Archive users to comment."

  Scenario: Translation of admin post with comments disabled
    Given I have posted an admin post with comments disabled
      And basic languages
      And I am logged in as a "translation" admin
    When I make a translation of an admin post
    Then I should see "Sorry, this news post doesn't allow comments."
    When I follow "Edit Post"
    Then I should see "No one can comment"
    # TODO: Test that the other options aren't available/selected in a non-brittle way
