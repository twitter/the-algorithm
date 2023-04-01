@tags @tag_wrangling @comments
Feature: Comment on tag
As a tag wrangler
I'd like to comment on a tag'

  @disable_caching
  Scenario: Comment on a tag and get taken to right page and see right date

    Given the following activated tag wranglers exist
        | login     |
        | dizmo     |
      And a fandom exists with name: "Stargate Atlantis", canonical: true
      And it is currently Mon Mar 27 22:00:00 UTC 2017
    When I am logged in as "dizmo"
    When I view the tag "Stargate Atlantis"
    Then I should see "0 comments"
    When I post the comment "Shouldn't this be a metatag with Stargate?" on the tag "Stargate Atlantis" via web
    Then I should see "Shouldn't this be a metatag with Stargate?"
      And the comment's posted date should be nowish
      And I jump in our Delorean and return to the present

  Scenario: Edit a comment on a tag

    Given the following activated tag wranglers exist
        | login     |
        | dizmo     |
      And a fandom exists with name: "Stargate Atlantis", canonical: true
      And it is currently Mon Mar 27 22:00:00 UTC 2017
    When I am logged in as "dizmo"
    When I post the comment "Shouldn't this be a metatag with Stargate?" on the tag "Stargate Atlantis"
    When it is currently 1 second from now
      And I follow "Edit"
    Then the "Comment" field should contain "Shouldn't this be a metatag with Stargate?"
      And I should see "Cancel"
    When I fill in "Comment" with "Yep, we should have a Stargate franchise metatag."
      And I press "Update"
    Then I should see "Comment was successfully updated."
      And I should see "Yep, we should have a Stargate franchise metatag."
      And I should not see "Shouldn't this be a metatag with Stargate?"
      And I should see Last Edited nowish
    When I jump in our Delorean and return to the present

  Scenario: Multiple comments on a tag increment correctly

    Given the following activated tag wranglers exist
        | login     |
        | dizmo     |
      And a fandom exists with name: "Stargate Atlantis", canonical: true
    When I am logged in as "dizmo"
    When I post the comment "Yep, we should have a Stargate franchise metatag." on the tag "Stargate Atlantis"
    When I am logged in as an admin
    When I post the comment "Important policy decision" on the tag "Stargate Atlantis"
    When I view the tag "Stargate Atlantis"
    Then I should see "2 comments"

    Scenario: admin can also comment on tags, issue 1428

    Given a fandom exists with name: "Stargate Atlantis", canonical: true
    When I am logged in as an admin
    When I post the comment "Important policy decision" on the tag "Stargate Atlantis" via web
    When I view the tag "Stargate Atlantis"
    Then I should see "1 comment"

  Scenario: Issue 2185: email notifications for tag commenting; TO DO: replies to comments

    Given the following activated tag wranglers exist
        | login       | password      | email              |
        | dizmo       | wrangulator   | dizmo@example.org  |
        | Enigel      | wrangulator   | enigel@example.org |
        | Cesy        | wrangulator   | cesy@example.org   |
      And a canonical fandom "Eroica"
      And a canonical fandom "Doctor Who"
      And the tag wrangler "Enigel" with password "wrangulator" is wrangler of "Eroica"
      And the tag wrangler "Cesy" with password "wrangulator" is wrangler of "Doctor Who"
      And the tag wrangler "dizmo" with password "wrangulator" is wrangler of "Doctor Who"

    # receive copies of own comments
    When I am logged in as "Enigel" with password "wrangulator"
      And I go to Enigel's user page
      And I follow "Preferences"
      And I uncheck "Turn off copies of your own comments"
      And I press "Update"
      And I log out

    # fellow wrangler leaves a comment on a wrangler's fandom
    When I am logged in as "Cesy" with password "wrangulator"
      And I go to Cesy's user page
      And I follow "Preferences"
      And I check "Turn off copies of your own comments"
      And I press "Update"
      And all emails have been delivered
      And I view the tag "Eroica"
      And I follow "0 comments"
      And I fill in "Comment" with "really clever stuff"
      And I press "Comment"
    Then I should see "Comment created"
      And 1 email should be delivered to "enigel@example.org"
      And the email should contain "really clever stuff"
      And the email should contain "Cesy"
      And the email should contain "left the following comment on"
      And the email should contain "the tag"

    When I am logged in as "Enigel" with password "wrangulator"
      And I follow "Go to the thread starting from this comment" in the email
    Then I should see "Comment on Eroica"
      And I should see "really clever stuff"
    When I follow "Read all comments on Eroica" in the email
    Then I should see "Reading Comments on Eroica"
      And I should see "really clever stuff"
    When I follow "Reply to this comment" in the email
    Then I should see "Comment on Eroica"
      And I should see "really clever stuff"
      And all emails have been delivered

    When I view the tag "Doctor Who"
      And I follow "0 comments"
      And I fill in "Comment" with "really clever stuff"
      And I press "Comment"
    Then I should see "Comment created"
      And 1 email should be delivered to "cesy@example.org"
      And 1 email should be delivered to "dizmo@example.org"
      And 1 email should be delivered to "enigel@example.org"
    When I follow "Edit"
      And all emails have been delivered
      And I press "Update"
    Then I should see "Comment was successfully updated"
      And 3 emails should be delivered

  Scenario: Email notifications for tag comments should ignore work comments settings

    Given the following activated tag wranglers exist
        | login       | password      | email             |
        | dizmo       | wrangulator   | dizmo@example.org |
        | Enigel      | wrangulator   | enigel@example.org|
      And a fandom exists with name: "Doctor Who", canonical: true
      And the tag wrangler "Enigel" with password "wrangulator" is wrangler of "Doctor Who"
      And I am logged in as "Enigel"
      And I set my preferences to turn off notification emails for comments
    When I am logged in as "dizmo" with password "wrangulator"
      And I post the comment "Heads up" on the tag "Doctor Who"
    Then 1 email should be delivered to "enigel@example.org"

  Scenario: comments on synonym fandoms should be received by the wrangler of the canonical merger

    Given the following activated tag wranglers exist
        | login       | password      | email             |
        | dizmo       | wrangulator   | dizmo@example.org |
        | Enigel      | wrangulator   | enigel@example.org|
      And a canonical fandom "Doctor Who"
      And the tag wrangler "Enigel" with password "wrangulator" is wrangler of "Doctor Who"
      And a synonym "Dr Who" of the tag "Doctor Who"
    When I am logged in as "dizmo" with password "wrangulator"
      And I post the comment "Heads up" on the tag "Dr Who"
    Then 1 email should be delivered to "enigel@example.org"

  Scenario: Comments pagination for a regular tag

    Given a tag "No Punctuation Here" with 34 comments
      And I am logged in as a tag wrangler
    When I view the tag "No Punctuation Here"
    # link to comments should exist
    Then I should see "34 comments"
    When I follow "34 comments"
    # link to the next page of comments should exist
    Then I should see "Next" within ".pagination"
    When I follow "Next" within ".pagination"
      And I post a comment "Checking redirect after commenting on a tag"
    # should redirect to the same page you were on before commenting
    Then I should see "Comment created"
      And I should see "Checking redirect after commenting on a tag"

  Scenario: Comments pagination for a tag with slashes in the name

    Given a tag "hack/sign" with 34 comments
      And I am logged in as a tag wrangler
    When I post the comment "And now things should not break!" on the tag "hack/sign"
    Then I should see "Comment created"
    # all it checks is that the pagination links aren't broken
    When I follow "Next" within ".pagination"
    Then I should see "And now things should not break!"

   Scenario: Comments pagination for a tag with periods in the name

    Given a period-containing tag "sign.me" with 34 comments
      And I am logged in as a tag wrangler
    When I post the comment "And now things should not break!" on the tag "sign.me"
    Then I should see "Comment created"
    # all it checks is that the pagination links aren't broken
    When I follow "Next" within ".pagination"
    Then I should see "And now things should not break!"

  Scenario: Comments pagination for a tag with slashes and periods in the name

    Given a tag "hack/sign.me" with 34 comments
      And I am logged in as a tag wrangler
    When I post the comment "And now things should not break!" on the tag "hack/sign.me"
    Then I should see "Comment created"
    # all it checks is that the pagination links aren't broken
    When I follow "Next" within ".pagination"
    Then I should see "And now things should not break!"

  Scenario: Comments on a tag should not be visible to non-wranglers.

    Given a canonical fandom "World Domination"
      And I am logged in as a tag wrangler
      And I post the comment "Top-secret plans." on the tag "World Domination"
      And I am logged out

    When I view the latest comment

    Then I should not see "Top-secret plans."

  Scenario: Comments replying to a comment on a tag should not be visible to non-wranglers.

    Given a canonical fandom "World Domination"
      And I am logged in as a tag wrangler
      And I post the comment "Anyone have a plan?" on the tag "World Domination"
      And I reply to a comment with "Top-secret plans."
      And I am logged out

    When I view the latest comment

    Then I should not see "Top-secret plans."
