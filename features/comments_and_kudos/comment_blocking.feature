Feature: Comment Blocking
  Scenario: Blocked users cannot comment on their blocker's works, or edit existing comments
    Given the work "Aftermath" by "creator"
      And a comment "Ugh." by "pest" on the work "Aftermath"
      And the user "creator" has blocked the user "pest"
    When I am logged in as "pest"
      And I view the work "Aftermath" with comments
    Then I should see "Sorry, you have been blocked by one or more of this work's creators."
      And I should not see a "Comment" button
      And I should not see a link "Edit"

  Scenario: Blocked users cannot reply to others on their blocker's works
    Given the work "Aftermath" by "creator"
      And a comment "OMG!" by "commenter" on the work "Aftermath"
      And the user "creator" has blocked the user "pest"
    When I am logged in as "pest"
      And I view the work "Aftermath" with comments
    Then I should not see a link "Reply"

  Scenario Outline: Blocked users cannot reply to their blocker
    Given <commentable>
      And a comment "OMG!" by "commenter" on <commentable>
      And the user "commenter" has blocked the user "pest"
    When I am logged in as "pest"
      And I view <commentable> with comments
    Then I should see a "Comment" button
      But I should not see a link "Reply"

    Examples:
      | commentable                 |
      | the work "Aftermath"        |
      | the admin post "Change Log" |

  Scenario Outline: Blocked users cannot edit existing replies to their blocker
    Given <commentable>
      And a comment "OMG!" by "commenter" on <commentable>
      And a reply "Ugh." by "pest" on <commentable>
      And the user "commenter" has blocked the user "pest"
    When I am logged in as "pest"
      And I view <commentable> with comments
    Then I should not see a link "Edit"

    Examples:
      | commentable                 |
      | the work "Aftermath"        |
      | the admin post "Change Log" |

  Scenario: Blocked users can reply to their blocker on tags
    Given the following activated tag wranglers exist
        | login     |
        | commenter |
        | pest      |
      And a canonical fandom "Controversial"
      And a comment "OMG!" by "commenter" on the tag "Controversial"
      And the user "commenter" has blocked the user "pest"
    When I am logged in as "pest"
      And I view the tag "Controversial" with comments
      And I follow "Reply"
      And I fill in "Comment" with "Ugh." within ".odd"
      And I press "Comment" within ".odd"
    Then I should see "Comment created!"

  Scenario: Blocked users can delete their existing comments on their blocker's works
    Given the work "Aftermath" by "creator"
      And a comment "Ugh." by "pest" on the work "Aftermath"
      And the user "creator" has blocked the user "pest"
    When I am logged in as "pest"
      And I view the work "Aftermath" with comments
      And I follow "Delete"
      And I follow "Yes, delete!"
    Then I should see "Comment deleted."

  Scenario: Blocked users can comment on works shared with their blocker
    Given the user "creator" has blocked the user "nemesis"
      And the work "Aftermath" by "creator" and "nemesis"
    When I am logged in as "nemesis"
      And I view the work "Aftermath"
      And I fill in "Comment" with "I can still do this."
      And I press "Comment"
    Then I should see "Comment created!"

  Scenario: Blocked users can't reply to their blocker on works shared with their blocker
    Given the user "creator" has blocked the user "nemesis"
      And the work "Aftermath" by "creator" and "nemesis"
      And a comment "OMG!" by "creator" on the work "Aftermath"
    When I am logged in as "nemesis"
      And I view the work "Aftermath" with comments
    Then I should not see a link "Reply"

  Scenario: When a user is blocked, they cannot reply to their blocker on the homepage or the inbox
    Given the work "Aftermath" by "pest"
      And a comment "OMG!" by "commenter" on the work "Aftermath"
      And the user "commenter" has blocked the user "pest"
    When I am logged in as "pest"
      And I go to the homepage
    Then I should see "OMG!"
      But I should not see a link "Reply"
    When I go to my inbox page
    Then I should see "OMG!"
      But I should not see a link "Reply"

  Scenario: When a user is blocked by the work creator, they cannot reply on the homepage or the inbox
    Given the work "Aftermath" by "creator"
      And a comment "Ugh." by "pest" on the work "Aftermath"
      And a reply "OMG!" by "commenter" on the work "Aftermath"
      And the user "creator" has blocked the user "pest"
    When I am logged in as "pest"
      And I go to the homepage
    Then I should see "OMG!"
      But I should not see a link "Reply"
    When I go to my inbox page
    Then I should see "OMG!"
      But I should not see a link "Reply"
