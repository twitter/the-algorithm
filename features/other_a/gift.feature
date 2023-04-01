Feature: Create Gifts
  In order to make friends and influence people
  As an author
  I want to create works for other people


  Background:
    Given the following activated users exist
      | login      | password    | email            |
      | gifter     | something   | gifter@example.com   |
      | gifter2    | something   | gifter2@example.com  |
      | giftee1    | something   | giftee1@example.com  |
      | giftee2    | something   | giftee2@example.com  |
      | associate  | something   | associate@example.com |
      And "giftee1" has the pseud "g1"
      And the user "giftee1" allows gifts
      And the user "giftee2" allows gifts
      And the user "associate" allows gifts
      And I am logged in as "gifter" with password "something"
      And I set up the draft "GiftStory1"

  Scenario: Gifts page for recipient should show recipient's gifts
    When I give the work to "giftee1"
      And I press "Post"
      And I go to the gifts page for the recipient giftee1
    Then I should see "GiftStory1 by gifter for giftee1"

  Scenario: Gifts page for recipient when logged out should show recipient's gifts if visible to all
    When I give the work to "giftee1"
      And I press "Post"
      And I set up the draft "GiftStory2" as a gift to "giftee1"
      And I lock the work
      And I press "Post"
      And I log out
      And I go to the gifts page for the recipient giftee1
    Then I should see "GiftStory1 by gifter for giftee1"
      And I should not see "GiftStory2 by gifter for giftee1"

  Scenario: Gifts page for user should show gifts given to their pseud
    Given I give the work to "g1 (giftee1)"
      And I press "Post"
    When I go to giftee1's gifts page
    Then I should see "GiftStory1 by gifter for g1 (giftee1)"

  Scenario: Gifts page for recipient without account should show their gifts
    Given I give the work to "g1"
      And I press "Post"
    When I go to the gifts page for the recipient g1
    Then I should see "GiftStory1 by gifter for g1"

  Scenario: When logged out, gifts page for recipient without account should show gifts visible to all
    When I give the work to "g1"
      And I press "Post"
      And I set up the draft "GiftStory2" as a gift to "g1"
      And I lock the work
      And I press "Post"
      And I log out
    When I go to the gifts page for the recipient g1
    Then I should see "GiftStory1 by gifter for g1"
      And I should not see "GiftStory2 by gifter for g1"

  Scenario: Giving a work as a gift when posting directly
    Given I give the work to "giftee1"
    When I press "Post"
    Then I should see "For giftee1"
      And "giftee1@example.com" should be notified by email about their gift "GiftStory1"

  Scenario: Giving a work as a gift when posting after previewing
    Given I give the work to "giftee1"
      And I press "Preview"
      And I should see "For giftee1"
      And 0 emails should be delivered
    When I press "Post"
    Then I should see "For giftee1"
      And "giftee1@example.com" should be notified by email about their gift "GiftStory1"

  Scenario: Edit a draft to add a recipient, then post after previewing
    Given I press "Preview"
      And I press "Edit"
      And I give the work to "giftee1"
      And I press "Preview"
      And 0 emails should be delivered
    When I press "Post"
    Then I should see "For giftee1"
      And "giftee1@example.com" should be notified by email about their gift "GiftStory1"

  Scenario: Edit an existing work to add a recipient, then post directly
    Given I press "Post"
      And I follow "Edit"
      And I give the work to "giftee1"
    When I press "Post"
    Then I should see "For giftee1"
      And "giftee1@example.com" should be notified by email about their gift "GiftStory1"

  Scenario: Edit an existing work to add a recipient, then post after previewing
    Given I press "Post"
      And I follow "Edit"
      And I give the work to "giftee1"
    When I press "Preview"
    # this next thing is broken on beta currently, will settle for not breaking it worse
    Then 0 emails should be delivered
    When I press "Edit"
    Then "giftee1" should be listed as a recipient in the form
    When I press "Preview"
    Then 0 emails should be delivered
    When I press "Update"
    Then I should see "For giftee1"
      And "giftee1@example.com" should be notified by email about their gift "GiftStory1"

  Scenario: Give two gifts to the same recipient
    Given I give the work to "giftee1"
      And I press "Post"
      And I set up the draft "GiftStory2"
      And I give the work to "giftee1"
    When I press "Post"
      And I follow "giftee1"
    Then I should see "Gifts for giftee1"
      And I should see "GiftStory1"
      And I should see "GiftStory2"

  Scenario: Add another recipient to a posted gift
    Given I give the work to "giftee1"
      And I press "Post"
      And I should see "For giftee1"
      And "giftee1@example.com" should be notified by email about their gift "GiftStory1"
      And all emails have been delivered
      And I follow "Edit"
      And I give the work to "giftee1, giftee2"
    When I press "Post"
    Then I should see "For giftee1, giftee2"
      And 0 emails should be delivered to "giftee1@example.com"
      And "giftee2@example.com" should be notified by email about their gift "GiftStory1"

  Scenario: Add another recipient to a draft gift
    Given I give the work to "giftee1"
      And I press "Preview"
      And I should see "For giftee1"
      And 0 emails should be delivered to "giftee1@example.com"
      And I press "Edit"
      And I give the work to "giftee1, giftee2"
    When I press "Post"
    Then I should see "For giftee1, giftee2"
      And "giftee1@example.com" should be notified by email about their gift "GiftStory1"
      And "giftee2@example.com" should be notified by email about their gift "GiftStory1"

  Scenario: Add two recipients, post, then remove one
    Given I give the work to "giftee1, giftee2"
      And I press "Post"
      And I should see "For giftee1, giftee2"
      And "giftee1@example.com" should be notified by email about their gift "GiftStory1"
      And "giftee2@example.com" should be notified by email about their gift "GiftStory1"
      And all emails have been delivered
      And I follow "Edit"
      And I give the work to "giftee1"
    When I press "Post"
    Then I should see "For giftee1"
      And I should not see "giftee2"
      And 0 emails should be delivered to "giftee1@example.com"
      And 0 emails should be delivered to "giftee2@example.com"

  Scenario: Add two recipients, preview, then remove one
    Given I give the work to "giftee1, giftee2"
      And I press "Preview"
      And I should see "For giftee1, giftee2"
      And 0 emails should be delivered
      And I press "Edit"
      And I give the work to "giftee1"
    When I press "Post"
    Then I should see "For giftee1"
      And I should not see "giftee2"
      And "giftee1@example.com" should be notified by email about their gift "GiftStory1"
      And 0 emails should be delivered to "giftee2@example.com"

  Scenario: Edit a posted work to replace one recipient with another
    Given I give the work to "giftee1"
      And I press "Post"
      And I should see "For giftee1"
      And "giftee1@example.com" should be notified by email about their gift "GiftStory1"
      And all emails have been delivered
      And I follow "Edit"
      And I give the work to "giftee2"
    When I press "Post"
    Then I should see "For giftee2"
      And I should not see "giftee1"
      And 0 emails should be delivered to "giftee1@example.com"
      And "giftee2@example.com" should be notified by email about their gift "GiftStory1"

  Scenario: Edit a draft to replace one recipient with another
    Given I give the work to "giftee1"
      And I press "Preview"
      And I should see "For giftee1"
      And 0 emails should be delivered
      And I press "Edit"
      And I give the work to "giftee2"
    When I press "Post"
    Then I should see "For giftee2"
      And I should not see "giftee1"
      And 0 emails should be delivered to "giftee1@example.com"
      And "giftee2@example.com" should be notified by email about their gift "GiftStory1"

  Scenario: When a user is notified that a co-authored work has been given to them as a gift, the e-mail should link to each author's URL instead of showing escaped HTML
    Given I invite the co-author "gifter2"
      And I give the work to "giftee1"
      And I preview the work
    Then 1 email should be delivered to "gifter2"
      And the email should contain "The user gifter has invited your pseud gifter2 to be listed as a co-creator on the following work"
      And the email should not contain "translation missing"
    When all emails have been delivered
      And the user "gifter2" accepts all co-creator requests
      And I press "Post"
    Then 1 email should be delivered to "giftee1"
      And the email should link to gifter's user url
      And the email should not contain "&lt;a href=&quot;http://archiveofourown.org/users/gifter/pseuds/gifter&quot;"
      And the email should link to gifter2's user url
      And the email should not contain "&lt;a href=&quot;http://archiveofourown.org/users/gifter2/pseuds/gifter2&quot;"

  Scenario: A gift work should have an associations list
    Given I give the work to "associate"
    When I press "Post"
    Then I should find a list for associations
      And I should see "For associate"

  Scenario: A user should not be able to gift a work twice to the same person
    Given "associate" has the pseud "associate2"
      And I am logged in as "troll"
      And I set up the draft "Yuck"
      And I have given the work to "associate, associate2 (associate)"
    Then I should not see "For associate, associate2"
      And I should see "For associate"
      And 1 email should be delivered to "associate@example.com"
    When all emails have been delivered
      And I edit the work "Yuck"
      And I give the work to "associate, associate2 (associate)"
      And I post the work without preview
    Then I should see "You seem to already have given this work to that user."
      And I should not see "For associate, associate2"
      And 0 emails should be delivered to "associate@example.com"

  Scenario: A user should be able to refuse a gift
    Given I have given the work to "associate"
      And I am logged in as "someone_else"
      And I am on associate's gifts page
    Then I should not see "Refuse Gift"
      And I should not see "Refused Gifts"
    When I am logged in as "associate" with password "something"
      And I go to my gifts page
    Then I should see "GiftStory1"
      And I should see "Refuse Gift"
      And I should see "Refused Gifts"
    When I follow "Refuse Gift"
    Then I should see "This work will no longer be listed among your gifts."
      And I should not see "GiftStory1"
    When I follow "Refused Gifts"
    Then I should see "GiftStory1"
      And I should not see "by gifter for associate"
    When I view the work "GiftStory1"
    Then I should not see "For associate"
      And I should not see "For ."

  Scenario: A user should be able to re-accept a gift
    Given I have refused the work
      And I am on my gifts page
      And I follow "Refused Gifts"
    Then I should see "Accept Gift"
      And I should not see "by gifter for giftee1"
    # Delay to make sure the cache is expired when re-accepting the gift:
    When it is currently 1 second from now
      And I follow "Accept Gift"
    Then I should see "This work will now be listed among your gifts."
      And I should see "GiftStory1"
      And I should see "by gifter for giftee1"
    When I view the work "GiftStory1"
    Then I should see "For giftee1"

  Scenario: An admin should see that a gift has been refused
    Given I have refused the work
      And I am logged in as an admin
      And I view the work "GiftStory1"
    Then I should see "Refused As Gift: giftee1"

  Scenario: Can't remove a recipient who has refused the gift
    Given I have refused the work
      And I am logged in as "gifter"
    When I edit the work "GiftStory1"
    Then "giftee1" should not be listed as a recipient in the form
      And the gift for "giftee1" should still exist on "GiftStory1"
    When I have removed the recipients
    Then the gift for "giftee1" should still exist on "GiftStory1"

  Scenario: Opt to disable notifications, then receive a gift (with no collection)
    Given I am logged in as "giftee1" with password "something"
      And I set my preferences to turn off notification emails for gifts
    When I am logged in as "gifter" with password "something"
      And I post the work "QuietGift" as a gift for "giftee1, giftee2"
    Then 0 emails should be delivered to "giftee1@example.com"
      And "giftee2@example.com" should be notified by email about their gift "QuietGift"

  Scenario: Opt to disable notifications, then receive a gift posted to a non-hidden collection
    Given I am logged in as "giftee1" with password "something"
      And I set my preferences to turn off notification emails for gifts
      And I have the collection "Open Skies"
    When I am logged in as "gifter" with password "something"
      And I post the work "QuietGift" in the collection "Open Skies" as a gift for "giftee1, giftee2"
    Then 0 emails should be delivered to "giftee1@example.com"
      And "giftee2@example.com" should be notified by email about their gift "QuietGift"

  Scenario: Opt to disable notifications, then receive a gift posted to a hidden collection and later revealed
    Given I am logged in as "giftee1" with password "something"
      And I set my preferences to turn off notification emails for gifts
      And I have the hidden collection "Hidden Treasures"
    When I am logged in as "gifter" with password "something"
      And I post the work "QuietGift" in the collection "Hidden Treasures" as a gift for "giftee1, giftee2"
      And I reveal works for "Hidden Treasures"
    Then 0 emails should be delivered to "giftee1@example.com"
      And "giftee2@example.com" should be notified by email about their gift "QuietGift"

  Scenario: Can't give a gift to a user who disallows them
    Given the user "giftee1" disallows gifts
    When I am logged in as "gifter"
      And I post the work "Rude Gift" as a gift for "giftee1"
    Then I should see "Sorry! We couldn't save this work because: giftee1 does not accept gifts."
      And 0 emails should be delivered to "giftee1@example.com"

  Scenario: A user who disallows gifts can refuse existing ones
    Given I am logged in as "gifter"
      And I post the work "Rude Gift" as a gift for "giftee1"
      And the user "giftee1" disallows gifts
    When I am logged in as "giftee1"
      And I go to my gifts page
      # Delay to make sure the cache is expired when the gift is refused:
      And it is currently 1 second from now
      And I follow "Refuse Gift"
    Then I should see "This work will no longer be listed among your gifts."
      And I should not see "Rude Gift"
    When I follow "Refused Gifts"
    Then I should see "Rude Gift"
      And I should not see "by gifter for giftee1"
    When I view the work "Rude Gift"
    Then I should not see "For giftee1."
