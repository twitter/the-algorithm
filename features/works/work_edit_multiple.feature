@works @tags
Feature: Edit Multiple Works
  In order to change settings on my works more easily
  As an author
  I want to edit multiple works at once

  Scenario: I can delete multiple works at once
    Given I am logged in as "author"
      And I post the work "Glorious" with fandom "SGA"
      And I post the work "Excellent" with fandom "Star Trek"
      And I post the work "Lovely" with fandom "Steven Universe"
      And I go to my works page
    When I follow "Edit Works"
    Then I should see the page title "Edit Multiple Works"
      And I should see "Edit Multiple Works"
    When I select "Glorious" for editing
      And I select "Excellent" for editing
      And I press "Delete"
    Then I should see "Are you sure you want to delete these works PERMANENTLY?"
      And I should see "Glorious"
      And I should see "Excellent"
      And I should not see "Lovely"
    When I press "Yes, Delete Works"
    Then I should see "Your works Glorious, Excellent were deleted."
    When all indexing jobs have been run
      And I go to my works page
    Then I should not see "Glorious"
      And I should not see "Excellent"
      And I should see "Lovely"

  Scenario: I can edit multiple works at once
    Given I am logged in as "author"
      And I post the work "Glorious" with fandom "SGA"
      And I post the work "Excellent" with fandom "Star Trek"
      And I go to my works page
    When I follow "Edit Works"
    Then I should see the page title "Edit Multiple Works"
      And I should see "Edit Multiple Works"
      And I should see "All"
      And I should see "None"
    When I select "Glorious" for editing
      And I select "Excellent" for editing
      And I press "Edit"
    Then I should see "Your edits will be applied to all of the following works"
      And I should see "Glorious"
      And I should see "Excellent"
    When I set the fandom to "Random"
     And I press "Update All Works"
    Then I should see "Your edits were put through"
      And I should see "Random"
      And I should not see "SGA"
      And I should not see "Star Trek"
    When I view the work "Glorious"
    Then I should see "Random"
      And I should not see "SGA"
    When I view the work "Excellent"
    Then I should see "Random"
      And I should not see "Star Trek"

  Scenario: I can disable anon commenting on multiple works at once
    Given I am logged in as "author"
      And I edit the multiple works "Glorious" and "Excellent"
    When I choose "Only registered users can comment"
      And I press "Update All Works"
      And I am logged out
      And I view the work "Glorious"
    Then I should see "doesn't allow non-Archive users to comment"
    When I view the work "Excellent"
    Then I should see "doesn't allow non-Archive users to comment"

  Scenario: I can disable commenting on multiple works at once
    Given I am logged in as "author"
      And I edit the multiple works "Glorious" and "Excellent"
    When I choose "No one can comment"
      And I press "Update All Works"
      And I am logged out
      And I view the work "Glorious"
    Then I should see "Sorry, this work doesn't allow comments."
    When I view the work "Excellent"
    Then I should see "Sorry, this work doesn't allow comments."

  Scenario: I can enable comment moderation on multiple works at once
    Given I am logged in as "author"
      And I edit the multiple works "Glorious" and "Excellent"
      And I choose "Enable comment moderation"
      And I press "Update All Works"
    When I am logged in as "commenter"
      And I view the work "Glorious"
    Then I should see "has chosen to moderate comments"
    When I view the work "Excellent"
    Then I should see "has chosen to moderate comments"

  Scenario: I can enable anon commenting on multiple works at once
    Given I am logged in as "author"
      And I edit the multiple works "Glorious" and "Excellent"
      And I choose "Only registered users can comment"
      And I press "Update All Works"
      And I edit the multiple works "Glorious" and "Excellent"
      And I choose "Registered users and guests can comment"
      And I press "Update All Works"
    When I am logged out
      And I view the work "Glorious"
    Then I should not see "doesn't allow non-Archive users to comment"

  Scenario: I can enable commenting on multiple works at once
    Given I am logged in as "author"
      And I edit the multiple works "Glorious" and "Excellent"
      And I choose "No one can comment"
      And I press "Update All Works"
      And I edit the multiple works "Glorious" and "Excellent"
      And I choose "Registered users and guests can comment"
      And I press "Update All Works"
    When I am logged out
      And I view the work "Glorious"
    Then I should not see "Sorry, this work doesn't allow comments."

  Scenario: I can disable comment moderation on multiple works at once
    Given I am logged in as "author"
      And I edit the multiple works "Glorious" and "Excellent"
      And I choose "Enable comment moderation"
      And I press "Update All Works"
      And I edit the multiple works "Glorious" and "Excellent"
      And I choose "Disable comment moderation"
      And I press "Update All Works"
    When I am logged out
      And I view the work "Glorious"
    Then I should not see "has chosen to moderate comments"

  Scenario: I can keep different comment moderation settings on different works when I edit them at once
    Given I am logged in as "author"
      And I edit multiple works with different comment moderation settings
    When I set the fandom to "Random"
      And I choose "Keep current comment moderation settings"
      And I press "Update All Works"
    When I am logged out
      And I view the work "Work with Comment Moderation Enabled"
    Then I should see "has chosen to moderate comments"
    When I view the work "Work with Comment Moderation Disabled"
    Then I should not see "has chosen to moderate comments"

  Scenario: I can keep different commenting settings on different works when I edit them at once
    Given I am logged in as "author"
      And I edit multiple works with different commenting settings
      And I set the fandom to "Random"
      And I choose "Keep current comment settings"
      And I press "Update All Works"

    When I view the work "Work with All Commenting Disabled"
    Then I should see "Sorry, this work doesn't allow comments."

    When I am logged out
      And I view the work "Work with Anonymous Commenting Disabled"
    Then I should see "Sorry, this work doesn't allow non-Archive users to comment."

    When I view the work "Work with All Commenting Enabled"
    Then I should not see "Sorry, this work doesn't allow comments."
      And I should not see "Sorry, this work doesn't allow non-Archive users to comment."

  Scenario: User can change the pseud on multiple works at once
    Given I am logged in as "author"
      And I add the pseud "My New Pseud"
      And I edit the multiple works "First" and "Second"
      And it is currently 1 second from now
    When I select "My New Pseud" from "Creator/Pseud(s)"
      And I press "Update All Works"
    Then I should see "Your edits were put through"
    When I view the work "First"
    Then I should see "My New Pseud" within ".byline"
    When I view the work "Second"
    Then I should see "My New Pseud" within ".byline"

  Scenario: User can invite a co-creator to multiple works at once
    Given the following activated users exist
      | login       |
      | lead_author |
      | coauthor    |
      And the user "coauthor" allows co-creators
      And I am logged in as "lead_author"
      And I edit the multiple works "First Shared" and "Second Shared"
    When I fill in "Add Co-Creators" with "coauthor"
      And I press "Update All Works"
    Then I should see "Your edits were put through"
      And 2 emails should be delivered to "coauthor"
    When I view the work "First Shared"
    Then I should not see "coauthor" within ".byline"
    When I view the work "First Shared"
    Then I should not see "coauthor" within ".byline"
    When the user "coauthor" accepts all co-creator requests
      And I view the work "First Shared"
    Then I should see "coauthor" within ".byline"
    When I view the work "Second Shared"
    Then I should see "coauthor" within ".byline"

  Scenario: User can remove themselves from multiple works at once
    Given the following activated users exist
      | login       |
      | lead_author |
      | coauthor    |
      And the user "coauthor" allows co-creators
      And I am logged in as "lead_author"
      And I edit multiple works coauthored as "lead_author" with "coauthor"
    When I check "Remove Me As Co-Creator"
      And I press "Update All Works"
    Then I should see "Your edits were put through"
    When I view the work "Shared Work 1"
    Then I should not see "lead_author" within ".byline"
    When I view the work "Shared Work 2"
    Then I should not see "lead_author" within ".byline"

  Scenario: User can remove themselves from one work even if they're the only creator on the other
    Given the user "lead_creator" exists and is activated
      And the user "co_creator" exists and is activated
      And I am logged in as "lead_creator"
      And I post the work "Solo"
      And I coauthored the work "Shared" as "lead_creator" with "co_creator"
    When I go to my edit multiple works page
      And I select "Solo" for editing
      And I select "Shared" for editing
      And I press "Edit"
      And I check "Remove Me As Co-Creator"
      And I press "Update All Works"
    Then I should see "You cannot remove yourself as co-creator of the work Solo because you are the only listed creator."
    When I view the work "Solo"
    Then I should see "lead_creator" within ".byline"
    When I view the work "Shared"
    Then I should not see "lead_creator" within ".byline"

  Scenario: User applies a private work skin to multiple coauthored works
    Given the following activated users with private work skins
      | login       |
      | lead_author |
      | coauthor    |
      And the user "coauthor" allows co-creators
      And I am logged in as "lead_author"
      And I edit multiple works coauthored as "lead_author" with "coauthor"
    Then I should see "Lead Author's Work Skin" within "#work_work_skin_id"
      And I should not see "Coauthor's Work Skin" within "#work_work_skin_id"
    When I select "Lead Author's Work Skin" from "Select Work Skin"
      And I press "Update All Works"
    Then I should see "Your edits were put through"

  Scenario: I can add several works to a collection at once
    Given I am logged in as "author"
      And I create the collection "MyCollection"
      And I edit the multiple works "Glorious" and "Excellent"
    When I fill in "Add To Collections" with "MyCollection"
      And I press "Update All Works"
    Then I should see "Your edits were put through"
    When I view the work "Glorious"
      Then I should see "MyCollection"
    When I view the work "Excellent"
      Then I should see "MyCollection"

  Scenario: I can remove several works from a collection at once
    Given I am logged in as "author"
      And I create the collection "MyCollection"
      And I post the work "Glorious" to the collection "MyCollection"
      And I post the work "Excellent" to the collection "MyCollection"
      And I edit the multiple works "Glorious" and "Excellent"
    When I check "MyCollection"
      And I press "Update All Works"
    Then I should see "Your edits were put through"
    When I view the work "Glorious"
      Then I should not see "MyCollection"
    When I view the work "Excellent"
      Then I should not see "MyCollection"
