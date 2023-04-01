@bookmarks
Feature: Share Bookmarks
  Testing the "Share" button on bookmarks, with JavaScript emulation

  @javascript
  Scenario: Share a bookmark
    Given I am logged in as "tess"
      And I have a bookmark for "Damp Gravel"
    When I go to the first bookmark for the work "Damp Gravel"
    Then I should see "Damp Gravel"
      And I should see "Share"
    When I follow "Share"
    Then I should see "Copy and paste the following code to link back to this work" within "#share"
      And I should see "or use the Tweet or Tumblr links to share the work" within "#share"
      And I should see '<strong>Damp Gravel</strong></a> (6 words)' within "#share textarea"
      And I should see 'by <a href="http://www.example.com/users/tess"><strong>tess</strong></a>' within "#share textarea"
      And I should see 'Fandom: <a href="http://www.example.com/tags/Stargate%20SG-1">Stargate SG-1</a>' within "#share textarea"
      And I should see "Rating: Not Rated" within "#share textarea"
      And I should see "Warnings: No Archive Warnings Apply" within "#share textarea"
      And the share modal should contain social share buttons
      And I should not see "Series:" within "#share textarea"
      And I should not see "Relationships:" within "#share textarea"
      And I should not see "Characters:" within "#share textarea"
      And I should not see "Summary:" within "#share textarea"

  @javascript
  Scenario: Share option is unavailable if bookmarkable is unrevealed.
    Given there is a work "Hidden Figures" in an unrevealed collection "Backlist"
      And I am logged in as the author of "Hidden Figures"
    When I view the work "Hidden Figures"
    Then I should see "Bookmark"
    When I follow "Bookmark"
      And I press "Create"
      And all indexing jobs have been run
    Then I should see "Bookmark was successfully created"
    When I go to the first bookmark for the work "Hidden Figures"
    Then I should see "Hidden Figures"
      And I should see "Add To Collection"
      And I should not see "Share"

  @javascript
  Scenario: Sharing a bookmark is not possible when logged out
    Given I am logged in as "tess"
      And I have a bookmark for "Damp Gravel"
    When I am logged out
      And I go to the first bookmark for the work "Damp Gravel"
    Then I should see "Damp Gravel"
      And I should not see "Share"
