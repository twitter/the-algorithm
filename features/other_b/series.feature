@series
Feature: Create and Edit Series
  In order to view series created by a user
  As a reader
  The index needs to load properly, even for authors with more than ArchiveConfig.ITEMS_PER_PAGE series

  Scenario: Creator manually enters a series name to add a work to a new series when the work is first posted
    Given I am logged in as "author"
      And I set up the draft "Sweetie Belle"
    When I fill in "Or create and use a new one:" with "Ponies"
    When I press "Post"
    Then I should see "Part 1 of Ponies" within "div#series"
      And I should see "Part 1 of Ponies" within "dd.series"
    When I view the series "Ponies"
    Then I should see "Sweetie Belle"

  Scenario: Creator selects an existing series name to add a work to an existing series when the work is first posted
    Given I am logged in as "author"
      And I post the work "Sweetie Belle" as part of a series "Ponies"
      And I set up the draft "Starsong"
    When I select "Ponies" from "Choose one of your existing series:"
      And I press "Post"
    Then I should see "Part 2 of Ponies" within "div#series"
      And I should see "Part 2 of Ponies" within "dd.series"
    When I view the series "Ponies"
    Then I should see "Sweetie Belle"
      And I should see "Starsong"

  Scenario: Creator adds a work to an existing series by editing the work
    Given I am logged in as "author"
      And I post the work "Sweetie Belle" as part of a series "Ponies"
      And I post the work "Rainbow Dash"
    When I view the series "Ponies"
    Then I should not see "Rainbow Dash"
    When I edit the work "Rainbow Dash"
      And I select "Ponies" from "Choose one of your existing series:"
      And I press "Post"
    Then I should see "Part 2 of Ponies" within "div#series"
      And I should see "Part 2 of Ponies" within "dd.series"
    When I view the series "Ponies"
    Then I should see "Sweetie Belle"
      And I should see "Rainbow Dash"

  Scenario: Works in a series have series navigation
    Given I am logged in as "author"
      And I post the work "Sweetie Belle" as part of a series "Ponies"
      And I post the work "Starsong" as part of a series "Ponies"
      And I post the work "Rainbow Dash" as part of a series "Ponies"
    When I view the series "Ponies"
      And I follow "Rainbow Dash"
    Then I should see "Part 3 of Ponies"
    When I follow "← Previous Work"
    Then I should see "Starsong"
    When I follow "← Previous Work"
    Then I should see "Sweetie Belle"
    When I follow "Next Work →"
    Then I should see "Starsong"

  Scenario: Creator can add series information
    Given I am logged in as "author"
      And I post the work "Sweetie Belle" as part of a series "Ponies"
    When I view the series "Ponies"
      And I follow "Edit Series"
      And I fill in "Series Description" with "This is a series about ponies. Of course"
      And I fill in "Series Notes" with "I wrote this under the influence of coffee! And pink chocolate."
      And I press "Update"
    Then I should see "Series was successfully updated."
      And I should see "This is a series about ponies. Of course" within "blockquote.userstuff"
      And I should see "I wrote this under the influence of coffee! And pink chocolate." within "dl.series"
      And I should see "Complete: No"
    When I follow "Edit Series"
      And I check "This series is complete"
      And I press "Update"
    Then I should see "Complete: Yes"

  Scenario: A work can be in two series
    Given I am logged in as "author"
      And I post the work "Sweetie Belle" as part of a series "Ponies"
      And I post the work "Rainbow Dash" as part of a series "Ponies"
    When I edit the work "Rainbow Dash"
    Then the "This work is part of a series" checkbox should be checked
      And "Ponies" should be an option within "Choose one of your existing series:"
    When I fill in "Or create and use a new one:" with "Black Beauty"
      And I press "Preview"
    Then I should see "Part 2 of Ponies" within "dd.series"
      And I should see "Part 1 of Black Beauty" within "dd.series"
    When I press "Update"
      And all indexing jobs have been run
    Then I should see "Part 1 of Black Beauty" within "dd.series"
      And I should see "Part 2 of Ponies" within "dd.series"
      And I should see "Part 1 of Black Beauty" within "div#series"
      And I should see "Part 2 of Ponies" within "div#series"

  Scenario: Creator with multiple pseuds adds a work to a new series when the work is first posted
    Given I am logged in as "author"
      And I add the pseud "Pointless Pseud"
      And I set up the draft "Sweetie Belle" using the pseud "Pointless Pseud"
    When I fill in "Or create and use a new one:" with "Ponies"
      And I press "Post"
    Then I should see "Pointless Pseud"
      And I should see "Part 1 of Ponies" within "div#series"
      And I should see "Part 1 of Ponies" within "dd.series"
    When I view the series "Ponies"
    Then I should see "Sweetie Belle"

  Scenario: Creator with multiple pseuds adds a work to an existing series when the work is first posted
    Given I am logged in as "author"
      And I add the pseud "Pointless Pseud"
      And I post the work "Sweetie Belle" as part of a series "Ponies" using the pseud "Pointless Pseud"
    When I set up the draft "Starsong" as part of a series "Ponies" using the pseud "Pointless Pseud"
      And I press "Post"
    Then I should see "Pointless Pseud"
      And I should see "Part 2 of Ponies"
    When I view the series "Ponies"
    Then I should see "Sweetie Belle"
      And I should see "Starsong"

  Scenario: Creator with multiple pseuds adds a work to an existing series by editing the work
    Given I am logged in as "author"
      And I add the pseud "Pointless Pseud"
      And I post the work "Sweetie Belle" as part of a series "Ponies" using the pseud "Pointless Pseud"
      And I post the work "Rainbow Dash" using the pseud "Pointless Pseud"
    When I view the series "Ponies"
    Then I should not see "Rainbow Dash"
    When I edit the work "Rainbow Dash"
      And I select "Ponies" from "Choose one of your existing series:"
      And I press "Post"
    Then I should see "Part 2 of Ponies" within "div#series"
      And I should see "Part 2 of Ponies" within "dd.series"
    When I view the series "Ponies"
    Then I should see "Sweetie Belle"
      And I should see "Rainbow Dash"

  Scenario: A pseud's series page contains the pseud in the page title
    Given I am logged in as "author"
      And I add the pseud "Pointless Pseud"
      And I post the work "Sweetie Belle" as part of a series "Ponies" using the pseud "Pointless Pseud"
    When I follow "Pointless Pseud"
      And I follow "Series (1)"
    Then the page title should include "by Pointless Pseud"

  Scenario: Rename a series
    Given I am logged in as a random user
    When I add the work "WALL-E" to series "Robots"
    Then I should see "Part 1 of Robots" within "div#series"
      And I should see "Part 1 of Robots" within "dd.series"
    When I view the series "Robots"
      And I follow "Edit Series"
      And I fill in "Series Title" with "Many a Robot"
      And I wait 2 seconds
      And I press "Update"
    Then I should see "Series was successfully updated."
      And I should see "Many a Robot"
    # Work blurbs should be updated.
    When I go to my user page
    Then I should see "Part 1 of Many a Robot" within "#user-works"
    # Work metas should be updated.
    When I view the work "WALL-E"
    Then I should see "Part 1 of Many a Robot" within "div#series"
      And I should see "Part 1 of Many a Robot" within "dd.series"

  Scenario: Post
    Given I am logged in as "whoever" with password "whatever"
      And I add the work "public" to series "be_public"
    When I follow "be_public"
    Then I should not see the image "title" text "Restricted" within "h2"

  Scenario: View user's series index
    Given I am logged in as "whoever" with password "whatever"
      And I add the work "grumble" to series "polarbears"
    When I go to whoever's series page
    Then I should see "1 Series by whoever"
      And I should see "polarbears"

  Scenario: Series index for maaany series
    Given I am logged in as "whoever" with password "whatever"
      And I add the work "grumble" to "32" series "penguins"
    When I go to whoever's series page
    Then I should see "penguins30"
    When I follow "Next"
    Then I should see "penguins0"

  Scenario: Removing self as co-creator from co-created series when you are the only creator of a work in the series.
    Given I am logged in as "sun"
      And the user "moon" allows co-creators
      And I post the work "Sweetie Bell" as part of a series "Ponies"
    When I view the series "Ponies"
      And I follow "Edit Series"
      And I try to invite the co-author "moon"
      And I press "Update"
    Then I should see "Series was successfully updated."
      But I should not see "moon"
    When the user "moon" accepts all co-creator requests
    Then "moon" should be a creator of the series "Ponies"
    When I view the series "Ponies"
      And I follow "Remove Me As Co-Creator"
    Then I should see "Sorry, we can't remove all creators of a work."

  Scenario: Removing self as co-creator from co-created series
    Given basic tags
      And the user "son" allows co-creators
    When I am logged in as "moon" with password "testuser"
      And I coauthored the work "Sweetie Bell" as "moon" with "son"
      And I edit the work "Sweetie Bell"
      And I fill in "work_series_attributes_title" with "Ponies"
      And I post the work
    Then I should see "Work was successfully updated."
      And "moon" should be a creator of the series "Ponies"
      And "son" should be a creator on the series "Ponies"
    When I follow "Remove Me As Co-Creator"
    Then I should see "You have been removed as a creator from the series and its works."
      And "moon" should not be the creator of the series "Ponies"
      And "son" should be a creator on the series "Ponies"
    When I go to my works page
    Then I should not see "Sweetie Bell"

  Scenario: Delete a series
    Given I am logged in as "cereal" with password "yumyummy"
      And I add the work "Snap" to series "Krispies"
    When I view the series "Krispies"
      And I follow "Delete Series"
      And I press "Yes, Delete Series"
    Then I should see "Series was successfully deleted."

  Scenario: A work's series information is visible and up to date when previewing the work while posting or editing
    Given I am logged in as "author"
      And I add the pseud "Pointless Pseud"
      And I set up the draft "Sweetie Belle" as part of a series "Ponies"
    When I press "Preview"
    Then I should see "Part 1 of Ponies"
    When I press "Post"
      And I set up the draft "Rainbow Dash" as part of a series "Ponies" using the pseud "Pointless Pseud"
      And I press "Preview"
    Then I should see "Pointless Pseud"
      And I should see "Part 2 of Ponies"
    When I edit the work "Rainbow Dash"
      And I fill in "Or create and use a new one:" with "Black Beauty"
      And I wait 2 seconds
      And I press "Preview"
    Then I should see "Part 2 of Ponies" within "dd.series"
      And I should see "Part 1 of Black Beauty" within "dd.series"

  Scenario: A series's metadata is visible when viewing the series
    Given I am logged in as a random user
      And I post the work "Story" as part of a series "Excellent Series"
      And I bookmark the series "Excellent Series"
    When I view the series "Excellent Series"
    Then I should see "Words: 6" within ".series.meta"
      And I should see "Bookmarks: 1" within ".series.meta"
      And I should see "Works: 1" within ".series.meta"

  Scenario: When editing a series, the title field should not escape HTML
    Given I am logged in as "whoever"
      And I post the work "whatever" as part of a series "What a title! :< :& :>"
      And I go to whoever's series page
      And I follow "What a title! :< :& :>"
      And I follow "Edit Series"
    Then I should see "What a title! :< :& :>" in the "Series Title" input

  Scenario: You can edit a series to add someone as a co-creator if their preferences are set to permit it.
    Given I am logged in as "foobar"
      And the user "barbaz" exists and is activated
      And the user "barbaz" allows co-creators
      And I post the work "Behind her back she’s Gentleman Jack" as part of a series "Gentleman Jack"
    When I view the series "Gentleman Jack"
      And I follow "Edit Series"
      And I try to invite the co-author "barbaz"
      And I press "Update"
    Then I should see "Series was successfully updated."
      But I should not see "barbaz"
      And 1 email should be delivered to "barbaz"
      And the email should contain "The user foobar has invited your pseud barbaz to be listed as a co-creator on the following series"
    When I am logged in as "barbaz"
      And I follow "Gentleman Jack" in the email
    Then I should not see "Edit Series"
    When I follow "Co-Creator Requests page"
      And I check "selected[]"
      And I wait 2 seconds
      And I press "Accept"
    Then I should see "You are now listed as a co-creator on Gentleman Jack."
    When I follow "Gentleman Jack"
    Then I should see "Edit Series"
      And "barbaz" should be a co-creator of the series "Gentleman Jack"

  Scenario: You cannot edit a series to add someone as a co-creator if their preferences don't permit it.
    Given I am logged in as "foobar"
      And the user "barbaz" exists and is activated
      And the user "barbaz" disallows co-creators
      And I post the work "Behind her back she’s Gentleman Jack" as part of a series "Gentleman Jack"
    When I view the series "Gentleman Jack"
      And I follow "Edit Series"
      And I try to invite the co-author "barbaz"
      And I press "Update"
    Then I should see "Invalid creator: barbaz does not allow others to invite them to be a co-creator."
    When I press "Update"
    Then I should see "Series was successfully updated."
      And "barbaz" should not be the creator of the series "Gentleman Jack"

  Scenario: If you edit a series to add a co-creator with an ambiguous pseud, you will be prompted to clarify which user you mean.
    Given "myself" has the pseud "Me"
      And "herself" has the pseud "Me"
      And the user "myself" allows co-creators
      And the user "herself" allows co-creators
    When I am logged in as "testuser" with password "testuser"
      And I post the work "Behind her back she’s Gentleman Jack" as part of a series "Gentleman Jack"
      And I view the series "Gentleman Jack"
      And I follow "Edit Series"
      And I try to invite the co-author "Me"
      And I press "Update"
    Then I should see "There's more than one user with the pseud Me."
    When I select "myself" from "Please choose the one you want:"
      And I press "Update"
    Then I should see "Series was successfully updated."
      And I should not see "Me (myself)"
      And 1 email should be delivered to "myself"
      And the email should contain "The user testuser has invited your pseud Me to be listed as a co-creator on the following series"
    When the user "myself" accepts all co-creator requests
      And I view the series "Gentleman Jack"
    Then "testuser" should be the creator of the series "Gentleman Jack"
      And "Me (myself)" should be the creator of the series "Gentleman Jack"
      And I should see "Me (myself), testuser"
