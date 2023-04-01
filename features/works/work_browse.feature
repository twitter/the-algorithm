@works @browse
Feature: Browsing works from various contexts

Scenario: Browsing works with incorrect page params in query string
  Given a canonical fandom "Johnny Be Good"
    And I am logged in
    And I post the work "Whatever" with fandom "Johnny Be Good"
  When I browse the "Johnny Be Good" works with page parameter ""
  Then I should see "1 Work"

Scenario: If works in a listing exceed the maximum search result count,
  display a notice on the last page of results

  Given a canonical fandom "Aggressive Retsuko"
    And the max search result count is 4
    And 2 items are displayed per page
    And I am logged in
    And I post the work "Whatever 1" with fandom "Aggressive Retsuko"
    And I post the work "Whatever 2" with fandom "Aggressive Retsuko"
    And I post the work "Whatever 3" with fandom "Aggressive Retsuko"
    And I post the work "Whatever 4" with fandom "Aggressive Retsuko"

  When I browse the "Aggressive Retsuko" works with page parameter "2"
  Then I should see "3 - 4 of 4 Works"
    And I should not see "Please use the filters"

  When I post the work "Whatever 5" with fandom "Aggressive Retsuko"
    And I browse the "Aggressive Retsuko" works
  Then I should see "1 - 2 of 5 Works"
    And I should not see "Please use the filters"
  When I follow "Next"
  Then I should see "3 - 4 of 5 Works"
    And I should see "Displaying 4 results out of 5. Please use the filters"

  When I browse the "Aggressive Retsuko" works with page parameter "3"
  Then I should see "3 - 4 of 5 Works"
    And I should see "Displaying 4 results out of 5. Please use the filters"
  When I follow "Previous"
  Then I should see "1 - 2 of 5 Works"
    And I should not see "Please use the filters"

Scenario: The recent chapter link should point to the last posted chapter even
if there is a draft chapter

  Given I am logged in as a random user
    And a canonical fandom "Canonical Fandom"
    And I post the 2 chapter work "My WIP" with fandom "Canonical Fandom"
  When I browse the "Canonical Fandom" works
    And I follow the recent chapter link for the work "My WIP"
  Then I should be on the 2nd chapter of the work "My WIP"
  When a draft chapter is added to "My WIP"
    And I browse the "Canonical Fandom" works
    And I follow the recent chapter link for the work "My WIP"
  Then I should be on the 2nd chapter of the work "My WIP"

Scenario: The recent chapter link in a work's blurb should show the adult
content notice to visitors who are not logged in

  Given I am logged in as a random user
    And a canonical fandom "Canonical Fandom"
    And I post the 3 chapter work "WIP" with fandom "Canonical Fandom" with rating "Mature"
  When I am logged out
    And I browse the "Canonical Fandom" works
    And I follow the recent chapter link for the work "WIP"
  Then I should see "adult content"
  When I follow "Proceed"
  Then I should be on the 3rd chapter of the work "WIP"

Scenario: The recent chapter link in a work's blurb should honor the logged-in
user's "Show me adult content without checking" preference

  Given I am logged in as a random user
    And a canonical fandom "Canonical Fandom"
    And I post the 2 chapter work "WIP" with fandom "Canonical Fandom" with rating "Mature"
  When I am logged in as "adultuser"
    And I set my preferences to show adult content without warning
    And I browse the "Canonical Fandom" works
    And I follow the recent chapter link for the work "WIP"
  Then I should not see "adult content"
    And I should be on the 2nd chapter of the work "WIP"
  When I set my preferences to warn before showing adult content
    And I browse the "Canonical Fandom" works
    And I follow the recent chapter link for the work "WIP"
  Then I should see "adult content"
  When I follow "Proceed"
  Then I should be on the 2nd chapter of the work "WIP"

Scenario: The recent chapter link in a work's blurb should point to
chapter-by-chapter mode even if the logged-in user's preference is "Show the
whole work by default"

  Given I am logged in as a random user
    And a canonical fandom "Canonical Fandom"
    And I post the 2 chapter work "WIP" with fandom "Canonical Fandom" with rating "Mature"
  When I am logged in as "fullworker"
    And I set my preferences to View Full Work mode by default
    And I browse the "Canonical Fandom" works
    And I follow the recent chapter link for the work "WIP"
  Then I should be on the 2nd chapter of the work "WIP"

Scenario: The recent chapter link in a work's blurb points to the last posted
chapter when the chapters are reordered.

  Given I am logged in as a random user
    And a canonical fandom "Canonical Fandom"
    And I post the 2 chapter work "My WIP" with fandom "Canonical Fandom"
  When I browse the "Canonical Fandom" works
    And I follow the recent chapter link for the work "My WIP"
  Then I should be on the 2nd chapter of the work "My WIP"
  When I follow "Edit"
    And I follow "Manage Chapters"
    And I fill in "chapters_1" with "2"
    And I fill in "chapters_2" with "1"
    And I press "Update Positions"
  Then I should see "Chapter order has been successfully updated."
  When I browse the "Canonical Fandom" works
    And I follow the recent chapter link for the work "My WIP"
  Then I should be on the 2nd chapter of the work "My WIP"

  Scenario: Kudos link from from work browsing leads to full work page
  Given the chaptered work with 2 chapters "Awesome Work"
  When I am logged in as "reader"
    And I go to the works page
  Then I should not see "Kudos: 1" within the work blurb of "Awesome Work"
  When I view the work "Awesome Work"
    And I leave kudos on "Awesome Work"
  Then I should see "reader left kudos on this work!"
  When I am logged out
    And the cache for the work "Awesome Work" is cleared
    And I go to the works page
  Then I should see "Kudos: 1" within the work blurb of "Awesome Work"
  When I follow the kudos link for the work "Awesome Work"
  Then I should be on the work "Awesome Work"
    And I should see "reader left kudos on this work!"

  Scenario: Comments link from from work browsing leads to full work page
  Given the chaptered work with 2 chapters "Awesome Work"
  When I am logged in as "reader"
    And I go to the works page
  Then I should not see "Comments: 1" within the work blurb of "Awesome Work"
  When I post the comment "Bravo!" on the work "Awesome Work"
  Then I should see "Bravo!"
  When I am logged out
    And the cache for the work "Awesome Work" is cleared
    And I go to the works page
  Then I should see "Comments: 1" within the work blurb of "Awesome Work"
  When I follow the comments link for the work "Awesome Work"
  Then I should be on the work "Awesome Work"
    And I should see "Bravo!"
