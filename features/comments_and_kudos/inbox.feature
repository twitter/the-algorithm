Feature: Get messages in the inbox
  In order to stay informed about activity concerning my works and comments
  As a user
  I'd like to get messages in my inbox

  Scenario: I should not receive comments in my inbox if I have set my preferences to "Turn off messages to your inbox about comments."
    Given I am logged in as "boxer" with password "10987tko"
      And I post the work "Another Round"
      And I set my preferences to turn off messages to my inbox about comments
    When I am logged in as "cutman"
      And I post the comment "You should not receive this in your inbox." on the work "Another Round"
    When I am logged in as "boxer" with password "10987tko"
      And I go to my inbox page
    Then I should not see "cutman on Another Round"
      And I should not see "You should not receive this in your inbox."

  Scenario: I should receive comments in my inbox if I haven't set my preferences to "Turn off messages to your inbox about comments."
    Given I am logged in as "boxer" with password "10987tko"
      And I post the work "The Fight"
      And I set my preferences to turn on messages to my inbox about comments
    When I am logged in as "cutman"
      And I post the comment "You should receive this in your inbox." on the work "The Fight"
    When I am logged in as "boxer" with password "10987tko"
      And I go to my inbox page
    Then I should see "cutman on The Fight"
      And I should see "You should receive this in your inbox."

  Scenario: Logged in comments in my inbox should have timestamps
    Given I am logged in as "boxer" with password "10987tko"
      And I post the work "Down for the Count"
    When I am logged in as "cutman"
      And I post the comment "It was a right hook... with a bit of a jab. (And he did it with his left hand.)" on the work "Down for the Count"
    When I am logged in as "boxer" with password "10987tko"
      And I go to my inbox page
    Then I should see "cutman on Down for the Count"
      And I should see "less than 1 minute ago"

  Scenario: Comments in my inbox should be filterable
    Given I am logged in as "boxer" with password "10987tko"
      And I post the work "Down for the Count"
    When I post the comment "The fight game's complex." on the work "Down for the Count" as a guest
    When I am logged in as "boxer" with password "10987tko"
      And I go to my inbox page
      And I choose "Show unread"
      And I press "Filter"
    Then I should see "guest on Down for the Count"
      And I should see "less than 1 minute ago"
    When I choose "Show read"
      And I press "Filter"
    Then I should not see "guest on Down for the Count"

  Scenario: I can bulk edit comments in my inbox by clicking 'Select'
    Given I am logged in as "boxer"
      And I post the work "The Fight"
    When I am logged in as "cutman"
      And I post the comment "You should receive this in your inbox." on the work "The Fight"
      And I post the comment "A second message for your inbox!" on the work "The Fight"
    When I am logged in as "boxer"
      And I go to my inbox page
    Then I should see "cutman on The Fight"
      And I should see "You should receive this in your inbox."
      And I should see "A second message for your inbox!"
      And I should see "Unread" within "li.comment:first-child"
      And I should see "Unread" within "li.comment:last-child"
    When I check "Select" within "li.comment:first-child"
      And I check "Select" within "li.comment:last-child"
      And I press "Mark Read"
    Then I should not see "Unread"

  Scenario: A user can see some of their unread comments on the homepage
    Given I am logged in as "boxer" with password "10987tko"
      And I post the work "Pre-Fight Coverage"
    When I am logged in as "cutman"
      And I post the comment "That's a haymaker? I actually never knew that." on the work "Pre-Fight Coverage"
    When I am logged in as "boxer" with password "10987tko"
      And I go to the homepage
    Then I should see "Unread messages"
      And I should see "My Inbox"
      And I should see "The latest unread items from your inbox."
      And I should see "cutman on Pre-Fight Coverage"
      And I should see "That's a haymaker? I actually never knew that."

  Scenario: A user can delete an unread comment on the homepage
    Given I am logged in as "boxer" with password "10987tko"
      And I post the work "The Gladiators of Old"
    When I am logged in as "cutman"
      And I post the comment "I can still make you cry, you know." on the work "The Gladiators of Old"
    When I am logged in as "boxer" with password "10987tko"
      And I go to the homepage
    Then I should see "cutman on The Gladiators of Old"
      And I should see "I can still make you cry, you know."
      And I should see a "Delete" button
    When I press "Delete"
    Then I should see "Inbox successfully updated."
      And I should be on the homepage
      And I should not see "Unread messages"
      And I should not see "My Inbox"
      And I should not see "The latest unread items from your inbox."
      And I should not see "cutman on the Gladiators of Old"
      And I should not see "I can still make you cry, you know."

  Scenario: A user can mark an unread comment read on the homepage
    Given I am logged in as "boxer" with password "10987tko"
      And I post the work "Special Coverage"
    When I am logged in as "cutman"
      And I post the comment "Is there anything we can do to make the fight go longer?" on the work "Special Coverage"
    When I am logged in as "boxer" with password "10987tko"
      And I go to the homepage
    Then I should see "cutman on Special Coverage"
      And I should see "Is there anything we can do to make the fight go longer?"
      And I should see a "Mark Read" button
    When I press "Mark Read"
    Then I should see "Inbox successfully updated."
      And I should be on the homepage
      And I should not see "Unread messages"
      And I should not see "My Inbox"
      And I should not see "The latest unread items from your inbox."
      And I should not see "cutman on Special Coverage"
      And I should not see "Is there anything we can do to make the fight go longer?"

  Scenario: A user can reply to a comment from the home page without JavaScript
    Given I am logged in as "sewwiththeflo"
      And I post the work "Cat Thor's Bizarre Adventure"
      And I am logged in as "unbeatablesg"
      And I post the comment "dude this is super great!!" on the work "Cat Thor's Bizarre Adventure"
    When I am logged in as "sewwiththeflo"
      And I go to the homepage
    Then I should see "unbeatablesg on Cat Thor's Bizarre Adventure"
      And I should see "dude this is super great!!"
      And I should see a link "Reply"
    When I reply to a comment with "Thank you! Please go to bed."
      And I go to the homepage
    Then I should not see "Unread messages"
      And I should not see "dude this is super great!!"
    When I am logged in as "unbeatablesg"
      And I go to the homepage
    Then I should see "sewwiththeflo on Cat Thor's Bizarre Adventure"
      And I should see "Thank you! Please go to bed."

  @javascript
  Scenario: A user can reply to a comment from the home page
    Given I am logged in as "sewwiththeflo"
      And I post the work "Cat Thor's Bizarre Adventure"
      And I am logged in as "unbeatablesg"
      And I post the comment "dude this is super great!!" on the work "Cat Thor's Bizarre Adventure"
    When I am logged in as "sewwiththeflo"
      And I go to the homepage
    Then I should see "unbeatablesg on Cat Thor's Bizarre Adventure"
      And I should see "dude this is super great!!"
      And I should see a link "Reply"
    When I follow "Reply" within ".latest.messages.module"
      And I fill in "Comment" with "Thank you! Please go to bed." within "#reply-to-comment"
      And I press "Comment" within "#reply-to-comment"
      And "AO3-5877" is fixed
    # Then I should be on the homepage
      # And I should not see "Unread messages"
      # And I should not see "dude this is super great!!"
    When I am logged in as "unbeatablesg"
      And I go to the homepage
    Then I should see "sewwiththeflo on Cat Thor's Bizarre Adventure"
      And I should see "Thank you! Please go to bed."
