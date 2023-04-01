@comments
Feature: Marking comments as spam

  Scenario: Spam comments are not included in a work's comment count
    Given I am logged in as "author"
      And I post the work "Popular Fic"
      And I log out
      And I view the work "Popular Fic" with comments
      And I post a guest comment
      And I post a spam comment
      And all comments by "spammer" are marked as spam

    When I am logged in as "author"
      And I go to the home page
    Then I should see "This was really lovely!"
      And I should not see "Buy my product!"
    When I follow "My Inbox"
    Then I should see "(1 comments, 1 unread)"
      And I should see "This was really lovely!"
      And I should not see "Buy my product!"

    When I go to author's user page
    Then I should see "Popular Fic"
      And I should see "Comments: 1"

    When I follow "Popular Fic"
    Then I should see "Comments:1"
      And I should see "Comments (1)"

    When I am logged in as "author"
      And I go to my stats page
    Then I should see "Comment Threads: 1"

  Scenario: Spam comments are not included in an admin post's comment count
    Given I am logged in as a "communications" admin
      And I make an admin post
      And I log out
      And I go to the admin-posts page
      And I follow "Default Admin Post"
      And I post a guest comment
      And I post a spam comment
      And all comments by "spammer" are marked as spam

    When I go to the admin-posts page
    Then I should see "Default Admin Post (1)"

    When I follow "Default Admin Post"
    Then I should see "Comments (1)"
