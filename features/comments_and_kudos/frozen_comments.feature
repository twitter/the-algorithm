@comments
Feature: Comment freezing

  Scenario: Freezing a comment removes the reply option and adds an indicator
  the comment is frozen. Unfreezing returns the reply option and removes the
  indicator. This behavior is present on the comment itself, the inbox, and the
  inbox module on the homepage.

    Given I am logged in as "author"
      And I post the work "Popular Fic"
      And I am logged out
      And I am logged in as "commenter"
      And I post the comment "My test comment!" on the work "Popular Fic"
      And I am logged out

    When I am logged in as "author"
      And I view the work "Popular Fic" with comments
      And I press "Freeze Thread"
    Then I should see "Comment thread successfully frozen!"
      And I should see "Frozen" within "#comments_placeholder .comment ul.actions"
      And I should not see "Reply" within "#comments_placeholder .comment ul.actions"

    When I go to the homepage
    Then I should see "My test comment!" within "div.messages .comment"
      And I should see "Frozen" within "div.messages .comment ul.actions"
      And I should not see "Reply" within "div.messages .comment ul.actions"

    When I go to my inbox page
    Then I should see "My test comment!" within "#inbox-form .comment"
      And I should see "Frozen" within "#inbox-form .comment ul.actions"
      And I should not see "Reply" within "#inbox-form .comment ul.actions"

    When I view the work "Popular Fic" with comments
      And I press "Unfreeze Thread"
    Then I should see "Comment thread successfully unfrozen!"
      And I should see "Reply" within "#comments_placeholder .comment ul.actions"
      And I should not see "Frozen" within "#comments_placeholder .comment ul.actions"

    When I go to the homepage
    Then I should see "My test comment!" within "div.messages .comment"
      And I should see "Reply" within "div.messages .comment ul.actions"
      And I should not see "Frozen" within "div.messages .comment ul.actions"

    When I go to my inbox page
    Then I should see "My test comment!" within "#inbox-form .comment"
      And I should see "Reply" within "#inbox-form .comment ul.actions"
      And I should not see "Frozen" within "#inbox-form .comment ul.actions"
