@collections
Feature: Collection
  In order to run some fanfic festivals
  As a humble user
  I want to create a collection with anonymity and hidden-until-reveal works

  Scenario: Works in a hidden collection should be visible to the mod and author but not other users
    Given I have the hidden collection "Hidden Treasury"
    When I am logged in as "first_user"
      And I set up the draft "Old Snippet" in collection "Hidden Treasury"
      And I press "Preview"
    Then I should see "Collections: Hidden Treasury"
      And I should see "Draft was successfully created."
    When I press "Post"
    Then the work "Old Snippet" should be visible to me
      And I should see "part of an ongoing challenge"
    When I am logged in as "moderator"
    Then the work "Old Snippet" should be visible to me
      And I should see "part of an ongoing challenge"
    When I am logged in as "second_user"
    Then the work "Old Snippet" should be hidden from me
    When I am logged out
    Then the work "Old Snippet" should be hidden from me

  Scenario: The moderator can reveal all the works in a hidden collection
    Given I have the hidden collection "Hidden Treasury"
      And "second_user" subscribes to author "first_user"
      And the user "third_user" exists and is activated
      And the user "third_user" allows gifts
      And all emails have been delivered
    When I am logged in as "first_user"
      And I post the work "New Snippet" to the collection "Hidden Treasury" as a gift for "third_user"
      And subscription notifications are sent
    Then 0 emails should be delivered
    When I reveal works for "Hidden Treasury"
    Then "third_user" should be emailed
      # not anonymous
      And the email to "third_user" should contain "first_user"
    When subscription notifications are sent
    Then "second_user" should be emailed
      And the email to "second_user" should contain "first_user"
    When I am logged out
    Then the work "New Snippet" should be visible to me
    When I view the collection "Hidden Treasury"
    Then I should see "New Snippet"
      And I should not see "Mystery Work"
    When I am logged in as "second_user"
    Then the work "New Snippet" should be visible to me
    When I view the collection "Hidden Treasury"
    Then I should see "New Snippet"
      And I should not see "Mystery Work"

  Scenario: The moderator can reveal a single work in a hidden collection
    Given I have the hidden collection "Hidden Treasury"
      And "second_user" subscribes to author "first_user"
      And the user "third_user" exists and is activated
      And the user "fourth_user" exists and is activated
      And the user "third_user" allows gifts
      And the user "fourth_user" allows gifts
      And all emails have been delivered
    When I am logged in as "first_user"
      And I post the work "First Snippet" to the collection "Hidden Treasury" as a gift for "third_user"
      And I post the work "Second Snippet" to the collection "Hidden Treasury" as a gift for "fourth_user"
      And subscription notifications are sent
    Then 0 emails should be delivered
    When I am logged in as "moderator"
      And I view the approved collection items page for "Hidden Treasury"
      # items listed in date order so checking the second will reveal the older work
      And I uncheck the 2nd checkbox with id matching "collection_items_\d+_unrevealed"
      And I submit
    Then "fourth_user" should not be emailed
    When "AO3-2240: gift notifications not sent for individual reveals" is fixed
    # Then "third_user" should be emailed
    #   And the email to "third_user" should contain "first_user"
    When subscription notifications are sent
    Then "second_user" should be emailed
      And 1 emails should be delivered
      And the email to "second_user" should contain "first_user"
      And the email to "second_user" should contain "First Snippet"
      And the email to "second_user" should not contain "Second Snippet"
    When I am logged out
    Then the work "First Snippet" should be visible to me
      And the work "Second Snippet" should be hidden from me
    When I view the collection "Hidden Treasury"
    Then I should see "First Snippet"

  Scenario: Bookmarks for hidden works should not reveal the work to others
    Given I have the hidden collection "Hidden Treasury"
      And I am logged in as "first_user"
      And I post the work "Hiding Work" to the collection "Hidden Treasury"
    When I am logged in as "moderator"
      And I bookmark the work "Hiding Work"
    When I am logged out
      And I go to the bookmarks page
    Then I should see "List of Bookmarks"
      And I should see "Mystery Work"
      And I should not see "Hiding Work"
    When I reveal works for "Hidden Treasury"
      And I am logged out
      And I go to the bookmarks page
    Then I should not see "Mystery Work"
      And I should see "Hiding Work"

  Scenario: The authors in an anonymous collection should only be visible to themselves and admins
    Given I have the anonymous collection "Anonymous Hugs"
      And I am logged in as "first_user"
      And I post the work "Old Snippet" to the collection "Anonymous Hugs"
    When I view the work "Old Snippet"
    Then the author of "Old Snippet" should be visible to me on the work page
    When I am logged out
    Then the author of "Old Snippet" should be hidden from me
    When I am logged in as "second_user"
    Then the author of "Old Snippet" should be hidden from me
    When I am logged in as an admin
    Then the author of "Old Snippet" should be visible to me on the work page
    # special case for moderator: can't see name on the work (to avoid unwanted spoilers)
    # but can see names + titles on in the collection items management area
    When I am logged in as "moderator"
    Then the author of "Old Snippet" should be hidden from me
    When I view the approved collection items page for "Anonymous Hugs"
    Then I should see "Old Snippet"
      And I should see "first_user"

  Scenario: Bookmarks should not reveal the authors of anonymous works
    Given I have the anonymous collection "Anonymous Hugs"
      And I am logged in as "first_user"
      And I post the work "Old Snippet" to the collection "Anonymous Hugs"
    When I am logged in as "second_user"
      And I bookmark the work "Old Snippet"
      And I go to the bookmarks page
    Then I should see "Old Snippet by Anonymous"
      And I should not see "first_user"

  Scenario: The moderator can reveal all the authors in an anonymous collection
    Given I have the anonymous collection "Anonymous Hugs"
      And "second_user" subscribes to author "first_user"
      And the user "third_user" exists and is activated
      And the user "third_user" allows gifts
      And all emails have been delivered
    When I am logged in as "first_user"
      And I post the work "Old Snippet" to the collection "Anonymous Hugs" as a gift for "third_user"
    Then "third_user" should be emailed
      And the email to "third_user" should not contain "first_user"
      And the email to "third_user" should contain "Anonymous"
    When subscription notifications are sent
    Then "second_user" should not be emailed
    When all emails have been delivered
      And I am logged in as "moderator"
      And I reveal authors for "Anonymous Hugs"
    Then the author of "Old Snippet" should be publicly visible
    When subscription notifications are sent
    Then "second_user" should be emailed
      And the email to "second_user" should contain "first_user"
      And "third_user" should not be emailed

  Scenario: The moderator can reveal a single author in an anonymous collection
    Given I have the anonymous collection "Anonymous Hugs"
      And "second_user" subscribes to author "first_user"
      And the user "third_user" exists and is activated
      And the user "third_user" allows gifts
      And I am logged in as "first_user"
      And I post the work "First Snippet" to the collection "Anonymous Hugs" as a gift for "third_user"
      And I post the work "Second Snippet" to the collection "Anonymous Hugs" as a gift for "not a user"
    When subscription notifications are sent
    Then "second_user" should not be emailed
    When I am logged in as "moderator"
      And I view the approved collection items page for "Anonymous Hugs"
      # items listed in date order so checking the second will reveal the older work
      And I uncheck the 2nd checkbox with id matching "collection_items_\d+_anonymous"
      And I submit
    Then the author of "First Snippet" should be publicly visible
    When subscription notifications are sent
    Then "second_user" should be emailed
      And the email to "second_user" should contain "first_user"
      And the email to "second_user" should contain "First Snippet"
      And the email to "second_user" should not contain "Second Snippet"
    When I am logged out
    Then the author of "First Snippet" should be publicly visible
      And the author of "Second Snippet" should be hidden from me

  Scenario: Works should not be visible in series if unrevealed
    Given I have the hidden collection "Hidden Treasury"
      And I am logged in as "first_user"
      And I post the work "Before"
      And I add the work "Before" to series "New series"
      And I post the work "Hiding Work" to the collection "Hidden Treasury"
      And I add the work "Hiding Work" to series "New series"
      And I post the work "After"
      And I add the work "After" to series "New series"
    When "AO3-1250: series anonymity issues" is fixed
    ### even the author should not see the work listed within the series
    # Then the work "Hiding Work" should be part of the "New series" series in the database
    #   And the work "Hiding Work" should not be visible on the "New series" series page
    #   And the series "New series" should not be visible on the "Hiding Work" work page
    #   And the neighbors of "Hiding Work" in the "New series" series should link over it
    When I am logged out
    Then the work "Hiding Work" should be part of the "New series" series in the database
      And the work "Hiding Work" should not be visible on the "New series" series page
      And the series "New series" should not be visible on the "Hiding Work" work page
    When "AO3-1250: series anonymity issues" is fixed
    #  And I should not see "Mystery Work"
    #  And the neighbors of "Hiding Work" in the "New series" series should link over it
    When I reveal works for "Hidden Treasury"
      And I am logged out
    Then the work "Hiding Work" should be visible on the "New series" series page
      And the series "New series" should be visible on the "Hiding Work" work page
      And the neighbors of "Hiding Work" in the "New series" series should link to it

  Scenario: Works should not be visible in series if anonymous
    Given I have the anonymous collection "Anon Treasury"
      And I am logged in as "first_user"
      And I post the work "Before"
      And I add the work "Before" to series "New series"
      And I post the work "Anon Work" to the collection "Anon Treasury"
      And I add the work "Anon Work" to series "New series"
      And I post the work "After"
      And I add the work "After" to series "New series"
    Then the work "Anon Work" should be part of the "New series" series in the database
    When "AO3-1250: series anonymity fixes" is fixed
      # even the author should not see the work in the series
      # And the work "Anon Work" should not be visible on the "New series" series page
      # And the series "New series" should not be visible on the "Anon Work" work page
      # And the neighbors of "Anon Work" in the "New series" series should link over it
    When I am logged out
    Then the work "Anon Work" should be part of the "New series" series in the database
    When "AO3-1250: series anonymity fixes" is fixed
      # And the work "Anon Work" should not be visible on the "New series" series page
      # And the series "New series" should not be visible on the "Anon Work" work page
      # And the neighbors of "Anon Work" in the "New series" series should link over it
    When I reveal authors for "Anon Treasury"
      And I am logged out
    Then the work "Anon Work" should be visible on the "New series" series page
      And the series "New series" should be visible on the "Anon Work" work page
      And the neighbors of "Anon Work" in the "New series" series should link to it

  Scenario: Adding a co-author to (one chapter of) an anonymous work should still keep it anonymous
    Given I have the anonymous collection "Various Penguins"
      And I am logged in as "Jessica"
      And I post the chaptered work "Cone of Silence" in the collection "Various Penguins"
    When I edit the work "Cone of Silence"
      And I follow "2" within "div#main.works-edit.region"
      And I invite the co-author "Amos"
      And I press "Post"
      And the user "Amos" accepts all co-creator requests
    Then the author of "Cone of Silence" should be visible to me on the work page
    When I am logged out
    Then the author of "Cone of Silence" should be hidden from me

  Scenario: A work is in two anonymous collections, and one is revealed
    Given I have the anonymous collection "Permanent Mice"
      And I have the anonymous collection "Temporary Mice"
      And I am logged in as "a_nonny_mouse"
      And I post the work "Cheesy Goodness"
      And I edit the work "Cheesy Goodness" to be in the collections "Permanent_Mice,Temporary_Mice"
      And "eager_fan" subscribes to author "a_nonny_mouse"

    When I am logged in as "moderator"
      And I reveal authors for "Temporary Mice"
      And subscription notifications are sent

    Then "eager_fan" should not be emailed

  Scenario: A work is in two unrevealed collections, and one is revealed
    Given I have the hidden collection "Super-Secret"
      And I have the hidden collection "Secret for Now"
      And I am logged in as "classified"
      And I post the work "Top-Secret Goodness"
      And I edit the work "Top-Secret Goodness" to be in the collections "Super-Secret,Secret_for_Now"
      And "eager_fan" subscribes to author "classified"

    When I am logged in as "moderator"
      And I reveal works for "Secret for Now"
      And subscription notifications are sent

    Then "eager_fan" should not be emailed

  Scenario: A work is in one anonymous and one hidden collection, and the anonymous collection is revealed
    Given I have the hidden collection "Triple-Secret"
      And I have the anonymous collection "Cheese Enthusiasts"
      And I am logged in as "classified"
      And I post the work "Half and Half"
      And I edit the work "Half and Half" to be in the collections "Triple-Secret,Cheese_Enthusiasts"
      And "eager_fan" subscribes to author "classified"

    When I am logged in as "moderator"
      And I reveal authors for "Cheese Enthusiasts"
      And subscription notifications are sent

    Then "eager_fan" should not be emailed

  Scenario: A work is in one anonymous and one hidden collection, and the hidden collection is revealed
    Given I have the hidden collection "Hidden Dreams"
      And I have the anonymous collection "Anons Anonymous"
      And I am logged in as "classified"
      And I post the work "Half and Half"
      And I edit the work "Half and Half" to be in the collections "Hidden_Dreams,Anons_Anonymous"
      And "eager_fan" subscribes to author "classified"

    When I am logged in as "moderator"
      And I reveal works for "Hidden Dreams"
      And subscription notifications are sent

    Then "eager_fan" should not be emailed

  Scenario: Creating a new work then immediately editing to add it to an
    anonymous collection should not trigger a subscriber email.

    Given I have the anonymous collection "Anon Forever"
      And the following activated users exist
        | login      | password | email              |
        | mysterious | password | mysterious@foo.com |
        | subscriber | password | subscriber@foo.com |
      And "subscriber" subscribes to author "mysterious"
      And all emails have been delivered

    When I am logged in as "mysterious"
      And I post the work "Anonymous Gift"
      And I edit the work "Anonymous Gift" to be in the collection "Anon_Forever"
      And subscription notifications are sent

    Then 0 emails should be delivered

  Scenario: When a creator views their own anonymous work, they should see a message explaining that their comment will be anonymous, and their comment should be anonymous.

    Given I have the anonymous collection "Anon Forever"
      And I am logged in as "shy_author"
      And I post the work "Hidden Masterpiece" to the collection "Anon Forever"

    When I view the work "Hidden Masterpiece"
    Then I should see "While this work is anonymous, comments you post will also be listed anonymously."

    When I post a comment "Reply from the author."
      And I am logged out
      And I view the work "Hidden Masterpiece" with comments
    Then I should see "Reply from the author."
      And I should see "Anonymous Creator"
      And I should not see "shy_author"

  Scenario: When a creator adds a work to an anonymous collection and previews the change, it should save correctly

    Given I have the anonymous collection "Anonymous Collection"
      And I am logged in as "creator"
      And I post the work "My Work"

    When I edit the work "My Work"
      And I fill in "Post to Collections / Challenges" with "anonymous_collection"
      And I press "Preview"

    Then I should see "Anonymous Collection"
      And I should see "Anonymous [creator]"

    When I press "Update"

    Then I should see "Anonymous Collection"
      And I should see "Anonymous [creator]"

  Scenario: When a creator adds a work to an anonymous collection and previews the change, it should cancel correctly

    Given I have the anonymous collection "Anonymous Collection"
      And I am logged in as "creator"
      And I post the work "My Work"

    When I edit the work "My Work"
      And I fill in "Post to Collections / Challenges" with "anonymous_collection"
      And I press "Preview"

    Then I should see "Anonymous Collection"
      And I should see "Anonymous [creator]"

    When I press "Cancel"

    Then I should see "The work was not updated."

    When I view the work "My Work"

    # This is not the desired behavior (AO3-5556), but we want to make sure it doesn't get broken worse
    Then I should see "Anonymous Collection"
      And I should see "Anonymous [creator]"

  Scenario: When an anonymous collection is deleted, works in the collection stop being anonymous.
    Given I have an anonymous collection "Anonymous Collection"
      And I am logged in as "creator"
      And I post the work "Secret Work" to the collection "Anonymous Collection"

    When I go to my works page
    Then I should not see "Secret Work"

    When I am logged in as the owner of "Anonymous Collection"
      And I go to "Anonymous Collection" collection edit page
      And I follow "Delete Collection"
      And I press "Yes, Delete Collection"
      And I go to creator's works page
    Then I should see "Secret Work"

  Scenario: When an unrevealed collection is deleted, works in the collection stop being unrevealed.
    Given I have a hidden collection "Hidden Collection"
      And I am logged in as "creator"
      And I post the work "Secret Work" to the collection "Hidden Collection"

    When I am logged out
    Then the work "Secret Work" should be hidden from me

    When I am logged in as the owner of "Hidden Collection"
      And I go to "Hidden Collection" collection edit page
      And I follow "Delete Collection"
      And I press "Yes, Delete Collection"
      And I am logged out
    Then the work "Secret Work" should be visible to me

  Scenario: When the moderator removes a work from an anonymous collection, the creator is revealed.
    Given I have an anonymous collection "Anonymous Collection"
      And I am logged in as "creator"
      And I post the work "Secret Work" to the collection "Anonymous Collection"

    When I go to my works page
    Then I should not see "Secret Work"

    When I am logged in as the owner of "Anonymous Collection"
      And I view the approved collection items page for "Anonymous Collection"
      And I check "Remove"
      And I submit
      And I go to creator's works page
    Then I should see "Secret Work"

  Scenario: When the moderator removes a work from an unrevealed collection, the work is revealed.
    Given I have a hidden collection "Hidden Collection"
      And I am logged in as "creator"
      And I post the work "Secret Work" to the collection "Hidden Collection"

    When I am logged out
    Then the work "Secret Work" should be hidden from me

    When I am logged in as the owner of "Hidden Collection"
      And I view the approved collection items page for "Hidden Collection"
      And I check "Remove"
      And I submit
      And I am logged out
    Then the work "Secret Work" should be visible to me

  Scenario: Moving a work with two collections from an anonymous collection to a non-anonymous collection should reveal the creator.
    Given an anonymous collection "Anonymizing"
      And a collection "Fluffy"
      And a collection "Holidays"

    When I am logged in as "creator"
      And I set up the draft "Secret Work"
      And I fill in "Collections" with "Anonymizing,Fluffy"
      And I press "Post"
      And I go to my works page
    Then I should not see "Secret Work"

    When I edit the work "Secret Work"
      And I fill in "Collections" with "Holidays,Fluffy"
      And I press "Post"
      And all indexing jobs have been run
      And I go to my works page
    Then I should see "Secret Work"

  Scenario: Changing a collection item to anonymous triggers a notification
    Given a collection "Changeable"
      And I am logged in as "creator"
      And I post the work "Lovely" in the collection "Changeable"
      And all emails have been delivered

    When I am logged in as the owner of "Changeable"
      And I go to "Changeable" collection edit page
      And I check "This collection is anonymous"
      And I press "Update"
      And I view the approved collection items page for "Changeable"
      And I check "Anonymous"
      And I submit
    Then "creator" should be emailed
      And the email should have "Your work was made anonymous" in the subject
      And the email should contain "Anonymous works are included in tag listings, but not on your works page."
      And the email should not contain "translation missing"

  Scenario: Changing a collection item to unrevealed triggers a notification
    Given a collection "Changeable"
      And I am logged in as "creator"
      And I post the work "Lovely" in the collection "Changeable"
      And all emails have been delivered

    When I am logged in as the owner of "Changeable"
      And I go to "Changeable" collection edit page
      And I check "This collection is unrevealed"
      And I press "Update"
      And I view the approved collection items page for "Changeable"
      And I check "Unrevealed"
      And I submit
    Then "creator" should be emailed
      And the email should have "Your work was made unrevealed" in the subject
      And the email should contain "Unrevealed works are not included in tag listings or on your works page."
      And the email should not contain "translation missing"

  Scenario: Changing a collection item to anonymous and unrevealed triggers a notification
    Given a collection "Changeable"
      And I am logged in as "creator"
      And I post the work "Lovely" in the collection "Changeable"
      And all emails have been delivered

    When I am logged in as the owner of "Changeable"
      And I go to "Changeable" collection edit page
      And I check "This collection is anonymous"
      And I check "This collection is unrevealed"
      And I press "Update"
      And I view the approved collection items page for "Changeable"
      And I check "Anonymous"
      And I check "Unrevealed"
      And I submit
    Then "creator" should be emailed
      And the email should have "Your work was made anonymous and unrevealed" in the subject
      And the email should contain "Unrevealed works are not included in tag listings or on your works page."
      And the email should contain "The collection maintainers may later reveal your work but leave it anonymous."
      And the email should not contain "translation missing"

  @javascript
  Scenario: Work share modal should not reveal anonymous authors
    Given I have the anonymous collection "Anonymous Hugs"
    When I am logged in as "first_user"
      And I post the work "Old Snippet" to the collection "Anonymous Hugs"
    When I am logged out
      And I view the work "Old Snippet"
    Then I should see "Share"
    When I follow "Share"
    Then I should see "by Anonymous" within "#modal textarea"

  Scenario: Work share button should not display for unrevealed works
    Given I have the hidden collection "Hidden Treasury"
    When I am logged in as "first_user"
      And I post the work "Old Snippet" to the collection "Hidden Treasury"
    Then the work "Old Snippet" should be visible to me
      And I should not see "Share"
    When I am logged in as "moderator"
    Then the work "Old Snippet" should be visible to me
      And I should not see "Share"

  Scenario: Works that are over the tag limit can be revealed
    Given the user-defined tag limit is 2
      And I have the hidden collection "Hidden Gems"
      And I am logged in as "creator"
      And I post the work "Over the Limit" in the collection "Hidden Gems"
      And the work "Over the Limit" has 3 fandom tags
    When I reveal works for "Hidden Gems"
      And I log out
      And I view the work "Over the Limit"
    Then I should see "Over the Limit"

  Scenario: Mystery blurb collections contain only unrevealed (approved or unmoderated) collections
    Given I have the hidden moderated collection "Hidden Moderated Approved"
      And I have the hidden moderated collection "Hidden Moderated Not Approved"
      And I have the hidden collection "Just Hidden"
      And I have the anonymous collection "Just Anonymous"
      And I have the hidden anonymous collection "Hidden and Anonymous"
      And I have the collection "Welcome"
      And I am logged in as "author"
      And I post the work "Work"
      And I edit the work "Work" to be in the collections "Welcome,Hidden_Moderated_Not_Approved"

    When I am logged out
     And I go to "Welcome" collection's page
    Then I should see "Mystery Work"
     And I should not see "Part of"

    When I am logged in as "author"
     And I edit the work "Work" to be in the collections "Welcome,Hidden_Moderated_Not_Approved,Hidden_Moderated_Approved,Just_Hidden,Just_Anonymous,Hidden_And_Anonymous"
     And I am logged in as "moderator"
     And I approve the work "Work" in the collection "Hidden Moderated Approved"
     And I submit

    When I am logged out
     And I go to "Welcome" collection's page
    Then I should see "Mystery Work"
     And I should see "Part of Hidden Moderated Approved, Just Hidden, Hidden and Anonymous"
     And I should not see "Hidden Moderated Not Approved"
     And I should not see "Just Anonymous"
     And I should not see "Welcome" within ".mystery"

  Scenario: Unrevealed work collection message mentions collections relevant to user
    Given I have the hidden moderated collection "Hidden Moderated 1"
      And I have the hidden moderated collection "Hidden Moderated 2"
      And I am logged in as "author"
      And I post the chaptered work "Work"
      And I edit the work "Work" to be in the collections "Hidden_Moderated_1,Hidden_Moderated_2"

     When I view the work "Work"
     Then I should see "You can find details here: Hidden Moderated 1, Hidden Moderated 2"
     When I view the work "Work" in full mode
     Then I should see "You can find details here: Hidden Moderated 1, Hidden Moderated 2"

     When I am logged out
      And I view the work "Work"
     Then I should not see "You can find details here"
     When I go to the work "Work" in full mode
      And I should not see "You can find details here"

     When I am logged in as "moderator"
      And I approve the work "Work" in the collection "Hidden Moderated 1"
      And I submit

     When I view the work "Work"
     Then I should see "You can find details here: Hidden Moderated 1"
      And I should not see "Hidden Moderated 2"
     When I go to the work "Work" in full mode
     Then I should see "You can find details here: Hidden Moderated 1"
      And I should not see "Hidden Moderated 2"

     When I am logged out
      And I view the work "Work"
     Then I should see "You can find details here: Hidden Moderated 1"
      And I should not see "Hidden Moderated 2"
     When I go to the work "Work" in full mode
     Then I should see "You can find details here: Hidden Moderated 1"
      And I should not see "Hidden Moderated 2"
