@bookmarks
Feature: Create bookmarks
  In order to have an archive full of bookmarks
  As a humble user
  I want to bookmark some works

Scenario: Create a bookmark
  Given I am logged in as "first_bookmark_user"
    When I am on first_bookmark_user's user page
      Then I should see "have anything posted under this name yet"
    When I am logged in as "another_bookmark_user"
      And I post the work "Revenge of the Sith"
      When I go to the bookmarks page
      Then I should not see "Revenge of the Sith"
    When I am logged in as "first_bookmark_user"
      And I go to the works page
      And I follow "Revenge of the Sith"
    Then I should see "Bookmark"
    When I follow "Bookmark"
      And I fill in "bookmark_notes" with "I liked this story"
      And I fill in "bookmark_tag_string" with "This is a tag, and another tag,"
      And I check "bookmark_rec"
      And I press "Create"
      And all indexing jobs have been run
    Then I should see "Bookmark was successfully created"
      And I should see "My Bookmarks"
    When I am logged in as "another_bookmark_user"
      And I go to the bookmarks page
    Then I should see "Revenge of the Sith"
      And I should see "This is a tag"
      And I should see "and another tag"
      And I should see "I liked this story"
    When I am logged in as "first_bookmark_user"
      And I go to first_bookmark_user's user page
    Then I should not see "You don't have anything posted under this name yet"
      And I should see "Revenge of the Sith"
    When I edit the bookmark for "Revenge of the Sith"
      And I check "bookmark_private"
      And I press "Update"
      And all indexing jobs have been run
    Then I should see "Bookmark was successfully updated"
    When I go to the bookmarks page
    Then I should not see "I liked this story"
    When I go to first_bookmark_user's bookmarks page
    Then I should see "I liked this story"

    # privacy check for the private bookmark '
    When I am logged in as "another_bookmark_user"
      And I go to the bookmarks page
    Then I should not see "I liked this story"
    When I go to first_bookmark_user's user page
    Then I should not see "I liked this story"

  Scenario: Create bookmarks and recs on restricted works, check how they behave from various access points
    Given the following activated users exist
      | login           |
      | first_bookmark_user   |
      | another_bookmark_user |
      And a fandom exists with name: "Stargate SG-1", canonical: true
      And I am logged in as "first_bookmark_user"
      And I post the locked work "Secret Masterpiece"
      And I post the locked work "Mystery"
      And I post the work "Public Masterpiece"
      And I post the work "Publicky"
    When I am logged in as "another_bookmark_user"
      And I view the work "Secret Masterpiece"
      And I follow "Bookmark"
      And I check "bookmark_rec"
      And I press "Create"
    Then I should see "Bookmark was successfully created"
      And I should see the image "title" text "Restricted"
      And I should see "Rec" within ".rec"
    When I view the work "Public Masterpiece"
      And I follow "Bookmark"
      And I check "bookmark_rec"
      And I press "Create"
    Then I should see "Bookmark was successfully created"
      And I should not see the image "title" text "Restricted"
    When I view the work "Mystery"
      And I follow "Bookmark"
      And I press "Create"
    Then I should see "Bookmark was successfully created"
      And I should not see "Rec"
    When I view the work "Publicky"
      And I follow "Bookmark"
      And I press "Create"
      And all indexing jobs have been run
    Then I should see "Bookmark was successfully created"
    When I log out
      And I go to the bookmarks page
    Then I should not see "Secret Masterpiece"
      And I should not see "Mystery"
      But I should see "Public Masterpiece"
      And I should see "Publicky"
    When I go to another_bookmark_user's bookmarks page
    Then I should not see "Secret Masterpiece"
    When I am logged in as "first_bookmark_user"
      And I go to another_bookmark_user's bookmarks page
    Then I should see "Bookmarks (4)"
      And I should see "Secret Masterpiece"

Scenario: extra commas in bookmark form (Issue 2284)

  Given I am logged in as "bookmarkuser"
    And I post the work "Some Work"
  When I follow "Bookmark"
    And I fill in "Your tags" with "Good tag, ,, also good tag, "
    And I press "Create"
  Then I should see "created"

Scenario: bookmark added to moderated collection has flash notice only when not approved
  Given the following activated users exist
    | login      | password |
    | workauthor | password |
    | bookmarker | password |
    | otheruser  | password |
    And I have a moderated collection "Five Pillars" with name "five_pillars"
    And I am logged in as "workauthor" with password "password"
    And I post the work "Fire Burn, Cauldron Bubble"
  When I log out
    And I am logged in as "bookmarker" with password "password"
    And I view the work "Fire Burn, Cauldron Bubble"
    And I follow "Bookmark"
    And I fill in "bookmark_collection_names" with "five_pillars"
    And I press "Create"
    And all indexing jobs have been run
  Then I should see "Bookmark was successfully created"
    And I should see "The collection Five Pillars is currently moderated."
  When I go to bookmarker's bookmarks page
    Then I should see "The collection Five Pillars is currently moderated."
  When I log out
    And I am logged in as "moderator" with password "password"
    And I approve the first item in the collection "Five Pillars"
    And all indexing jobs have been run
    And I am logged in as "bookmarker" with password "password"
    And I go to bookmarker's bookmarks page
  Then I should not see "The collection Five Pillars is currently moderated."

Scenario: bookmarks added to moderated collections appear correctly
  Given the following activated users exist
    | login      | password |
    | workauthor | password |
    | bookmarker | password |
    | otheruser  | password |
    And I have a moderated collection "JBs Greatest" with name "jbs_greatest"
    And I have a moderated collection "Bedknobs and Broomsticks" with name "beds_and_brooms"
    And I have a moderated collection "Death by Demographics" with name "death_by_demographics"
    And I have a moderated collection "Murder a la Mode" with name "murder_a_la_mode"
    And I have the collection "Mrs. Pots" with name "mrs_pots"
    And I am logged in as "workauthor" with password "password"
    And I post the work "The Murder of Sherlock Holmes"
  When I log out
    And I am logged in as "bookmarker" with password "password"
    And I view the work "The Murder of Sherlock Holmes"
    And I follow "Bookmark"
    And I fill in "bookmark_collection_names" with "jbs_greatest"
    And I press "Create"
    And all indexing jobs have been run
  Then I should see "Bookmark was successfully created"
    And I should see "The collection JBs Greatest is currently moderated. Your bookmark must be approved by the collection maintainers before being listed there."
    # UPDATE the bookmark and add it to a second MODERATED collection and
    # recheck all the things
  When I follow "Edit"
    And I fill in "bookmark_collection_names" with "jbs_greatest,beds_and_brooms"
    And I press "Update"
    And all indexing jobs have been run
  Then I should see "Bookmark was successfully updated."
    And I should see "to the moderated collection 'Bedknobs and Broomsticks'."
  When I follow "Edit"
    And I fill in "bookmark_collection_names" with "jbs_greatest,beds_and_brooms,death_by_demographics,murder_a_la_mode"
    And I press "Update"
    And all indexing jobs have been run
  Then I should see "You have submitted your bookmark to moderated collections (Death by Demographics, Murder a la Mode)."
  When I go to bookmarker's bookmarks page
    And I should see "The Murder of Sherlock Holmes"
    And I should see "Bookmarker's Collections: JBs Greatest"
    And I should see "The collection JBs Greatest is currently moderated. Your bookmark must be approved by the collection maintainers before being listed there."
  When I go to the bookmarks page
    And I should see "The Murder of Sherlock Holmes"
    And I should see "Bookmarker's Collections: JBs Greatest"
    And I should see "The collection JBs Greatest is currently moderated. Your bookmark must be approved by the collection maintainers before being listed there."
  When I log out
  # Users who do not own the bookmark should not see the notice, or see that it
  # has been submitted to a specific collection
    And I am logged in as "otheruser" with password "password"
    And I go to bookmarker's bookmarks page
  Then I should see "The Murder of Sherlock Holmes"
    And I should not see "Bookmarker's Collections: JBs Greatest"
    And I should not see "The collection JBs Greatest is currently moderated. Your bookmark must be approved by the collection maintainers before being listed there."
  When I go to the bookmarks page
    Then I should see "The Murder of Sherlock Holmes"
    And I should not see "Bookmarker's Collections: JBs Greatest"
    And I should not see "The collection JBs Greatest is currently moderated. Your bookmark must be approved by the collection maintainers before being listed there."
  # Edit the bookmark and add it to a second, unmoderated collection, and recheck
  # all the things
  When I log out
    And I am logged in as "bookmarker" with password "password"
    And I view the work "The Murder of Sherlock Holmes"
    And I follow "Edit Bookmark"
    And I fill in "bookmark_collection_names" with "jbs_greatest,beds_and_brooms,mrs_pots"
    And I press "Update" within "div#bookmark-form"
    And all indexing jobs have been run
  Then I should see "Bookmark was successfully updated."
    And I should see "The collection JBs Greatest is currently moderated."
  When I go to bookmarker's bookmarks page
    Then I should see "The Murder of Sherlock Holmes"
    And I should see "JBs Greatest" within "ul.meta"
    And I should see "Mrs. Pots" within "ul.meta"
    And I should see "The collection JBs Greatest is currently moderated."
  When I go to the bookmarks page
    Then I should see "The Murder of Sherlock Holmes"
    And I should see "JBs Greatest" within "ul.meta"
    And I should see "Mrs. Pots" within "ul.meta"
    And I should see "The collection JBs Greatest is currently moderated."
  When I log out
    And I am logged in as "otheruser" with password "password"
    And I go to bookmarker's bookmarks page
  Then I should see "The Murder of Sherlock Holmes"
    And I should not see "JBs Greatest" within "ul.meta"
    And I should see "Bookmarker's Collections: Mrs. Pots"
    And I should not see "The collection JBs Greatest is currently moderated."
  When I go to the bookmarks page
    Then I should see "The Murder of Sherlock Holmes"
    And I should not see "JBs Greatest" within "ul.meta"
    And I should see "Bookmarker's Collections: Mrs. Pots"
    And I should not see "The collection JBs Greatest is currently moderated."

Scenario: Adding bookmark to non-existent collection (AO3-4338)
  Given I am logged in as "moderator" with password "password"
    And I post the work "Programmed for Murder"
    And I view the work "Programmed for Murder"
    And I follow "Bookmark"
    And I press "Create"
    And I should see "Bookmark was successfully created"
  Then I follow "Edit"
    And I fill in "bookmark_collection_names" with "some_nonsense_collection"
    And I press "Update"
    And I should see "does not exist."

Scenario: Adding bookmarks to closed collections (Issue 3083)
  Given I am logged in as "moderator"
    And I have a closed collection "Unsolved Mysteries" with name "unsolved_mysteries"
    And I have a closed collection "Rescue 911" with name "rescue_911"
    And I am logged in as "moderator"
    And I post the work "Hooray for Homicide"
    And I post the work "Sing a Song of Murder"
    And I go to "Unsolved Mysteries" collection's page
    # As a moderator, create a bookmark in a closed collection
  When I view the work "Hooray for Homicide"
    And I follow "Bookmark"
    And I fill in "bookmark_collection_names" with "unsolved_mysteries"
    And I press "Create"
  Then I should see "Bookmark was successfully created"
    # Now, with the exising bookmark, as a mod, add it to a different closed collection
  When I follow "Edit"
    And I fill in "bookmark_collection_names" with "rescue_911"
    And I press "Update"
  Then I should see "Bookmark was successfully updated"
  When I view the work "Sing a Song of Murder"
    And I follow "Bookmark"
    And I press "Create"
  Then I should see "Bookmark was successfully created"
    # Use the 'Add To Collections' button to add the bookmark to a closed collection AFTER creating said bookmark
  When I follow "Add To Collection"
    And I fill in "collection_names" with "unsolved_mysteries"
    And I press "Add"
  Then I should see "Added to collection(s): Unsolved Mysteries"
    # Still as the moderator, try to edit the bookmark which is IN a closed collection already
  When I follow "Edit"
    And I fill in "bookmark_notes" with "This is my edited bookmark"
    And I press "Update"
  Then I should see "Bookmark was successfully updated."
    # Log in as a regular (totally awesome!) user
  When I am logged in as "RobertStack"
    And I view the work "Sing a Song of Murder"
    And I follow "Bookmark"
    And I fill in "bookmark_collection_names" with "rescue_911"
    And I press "Create"
  Then I should see "Sorry! We couldn't save this bookmark because:"
    And I should see "The collection rescue_911 is not currently open."
  When I view the work "Hooray for Homicide"
    And I follow "Bookmark"
    And I press "Create"
  Then I should see "Bookmark was successfully created"
  Then I follow "Add To Collection"
    And I fill in "collection_names" with "rescue_911"
    And I press "Add"
  Then I should see "We couldn't add your submission to the following collection(s): Rescue 911 is closed to new submissions."
    # Now, as a regular user try to add that existing bookmark to a closed collection from the 'Edit' page of a bookmark
  When I follow "Edit"
    And I fill in "bookmark_collection_names" with "rescue_911"
    And I press "Update"
  Then I should see "We couldn't add your submission to the following collections: Rescue 911 is closed to new submissions."
  # Create a collection, put a bookmark in it, close the collection, then try
  # to edit that bookmark
  When I open the collection with the title "Rescue 911"
    And I am logged in as "Scott"
    And I view the work "Sing a Song of Murder"
    And I follow "Bookmark"
    And I fill in "bookmark_collection_names" with "rescue_911"
    And I press "Create"
  Then I should see "Bookmark was successfully created"
  When I close the collection with the title "Rescue 911"
    And I am logged in as "Scott"
    And I view the work "Sing a Song of Murder"
    And I follow "Edit Bookmark"
    And I fill in "bookmark_notes" with "This is a user editing a closed collection bookmark"
    And I press "Update"
  Then I should see "Bookmark was successfully updated."

Scenario: Delete bookmarks of a work and a series
  Given the following activated users exist
    | login           | password   |
    | wahlly   | password   |
    | markymark   | password   |
    And I am logged in as "wahlly"
    And I add the work "A Mighty Duck" to series "The Funky Bunch"
    And I add the work "A Mighty Duck2 the sequel" to series "The Funky Bunch"
  When I log out
    And I am logged in as "markymark"
    And I view the work "A Mighty Duck2 the sequel"
    And I follow "Bookmark"
    And I press "Create"
    And I view the work "A Mighty Duck"
    And I follow "Bookmark"
    And I press "Create"
    And all indexing jobs have been run
  Then I should see "Bookmark was successfully created."
    And I should see "Delete"
  When I follow "Delete"
    And I press "Yes, Delete Bookmark"
    And all indexing jobs have been run
  Then I should see "Bookmark was successfully deleted."
  When I view the series "The Funky Bunch"
    And I follow "Bookmark Series"
    And I press "Create"
    And all indexing jobs have been run
  Then I should see "Bookmark was successfully created."
  When I follow "Delete"
  And I press "Yes, Delete Bookmark"
    And all indexing jobs have been run
  Then I should see "Bookmark was successfully deleted."
  When I go to my bookmarks page
  Then I should see "A Mighty Duck2 the sequel"
  When I log out
    And I am logged in as "wahlly"
    And I delete the work "A Mighty Duck2 the sequel"
    And all indexing jobs have been run
  Then I should see "A Mighty Duck2 the sequel was deleted."
  When I log out
    And I am logged in as "markymark"
    And I go to my bookmarks page
  Then I should see "This has been deleted, sorry!"
    And I follow "Edit"
    And I check "bookmark_private"
    And I press "Update"
    And all indexing jobs have been run
  Then I should see "Bookmark was successfully updated"
  When I follow "Delete"
    And I press "Yes, Delete Bookmark"
  Then I should see "Bookmark was successfully deleted."

Scenario: Editing a bookmark's tags should expire the bookmark cache
  Given I am logged in as "some_user"
    And I post the work "Really Good Thing"
  When I am logged in as "bookmarker"
    And I view the work "Really Good Thing"
    And I follow "Bookmark"
    And I fill in "bookmark_notes" with "I liked this story"
    And I fill in "bookmark_tag_string" with "Tag 1, Tag 2"
    And I press "Create"
  Then I should see "Bookmark was successfully created"
    And the cache of the bookmark on "Really Good Thing" should not expire if I have not edited the bookmark
    And the cache of the bookmark on "Really Good Thing" should expire after I edit the bookmark tags

Scenario: User can't bookmark same work twice
  Given the work "Haven"
    And I am logged in as "Mara"
    And I add the pseud "Audrey"
    And I bookmark the work "Haven" as "Mara"
  When I bookmark the work "Haven" as "Mara" from new bookmark page
  Then I should see "You have already bookmarked that."
  When I bookmark the work "Haven" as "Audrey" from new bookmark page
  Then I should see "You have already bookmarked that."

Scenario: I cannot create a bookmark that I don't own
  Given the work "Random Work"
  When I attempt to create a bookmark of "Random Work" with a pseud that is not mine
  Then I should not see "Bookmark was successfully created"
    And I should see "You can't bookmark with that pseud."

Scenario: I cannot edit an existing bookmark to transfer it to a pseud I don't own
  Given I am logged in as "original_bookmarker"
    And I have a bookmark for "Random Work"
  When I attempt to transfer my bookmark of "Random Work" to a pseud that is not mine
  Then I should not see "Bookmark was successfully updated"
    And I should see "You can't bookmark with that pseud."

@javascript
Scenario: Can use "Show Most Recent Bookmarks" from the bookmarks page
  Given the work "Popular Work"
    And I am logged in as "bookmarker1"
    And I bookmark the work "Popular Work" with the note "Love it"
    And I log out
    And I am logged in as "bookmarker2"
    And I bookmark the work "Popular Work"
    And the statistics for the work "Popular Work" are updated
  When I am on the bookmarks page
    # Follow the link for bookmarker2's bookmark, which is more recent.
    And I follow "Show Most Recent Bookmarks" within ".bookmark.blurb:first-child"
  Then I should see "bookmarker1" within ".bookmark.blurb:first-child .recent"
    And I should see "Love it" within ".bookmark.blurb:first-child .recent"
    And I should see "Hide Most Recent Bookmarks" within ".bookmark.blurb:first-child .recent"
  When I follow "Hide Most Recent Bookmarks" within ".bookmark.blurb:first-child .recent"
  # .recent has been hidden, we should not see its contents anymore.
  Then I should not see "bookmarker1" within ".bookmark.blurb:first-child"
    And I should not see "Love it" within ".bookmark.blurb:first-child"
    And I should see "Show Most Recent Bookmarks" within ".bookmark.blurb:first-child"

Scenario: A bookmark with duplicate tags other than capitalization has only first version of tag saved
  Given I am logged in as "bookmark_user"
  When I post the work "Revenge of the Sith"
    And I follow "Bookmark"
    And I fill in "Your tags" with "my tags,My Tags"
    And I press "Create"
  Then I should see "Bookmark was successfully created"
    And I should see "Bookmarker's Tags: my tags"
    And I should not see "Bookmarker's Tags: My Tags"

  Scenario: Users can bookmark a work with too many tags
    Given the user-defined tag limit is 2
      And the work "Over the Limit"
      And the work "Over the Limit" has 3 fandom tags
      And I am logged in as "bookmarker"
    When I bookmark the work "Over the Limit"
    Then I should see "Bookmark was successfully created"

  Scenario: Users can bookmark a pre-existing external work with too many tags
    Given the user-defined tag limit is 2
      And I am logged in as "bookmarker1"
      And I bookmark the external work "Over the Limit"
      And the external work "Over the Limit" has 3 fandom tags
      And I am logged in as "bookmarker2"
    When I go to bookmarker1's bookmarks page
      And I follow "Save"
      And I press "Create"
    Then I should see "Bookmark was successfully created"

  Scenario: Users cannot bookmark a new external work with too many tags
    Given the user-defined tag limit is 5
      And I am logged in as "bookmarker"
    When I set up an external work
      And I fill in "Fandoms" with "Fandom 1, Fandom 2"
      And I fill in "Characters" with "Character 1, Character 2"
      And I fill in "Relationships" with "Relationship 1, Relationship 2"
      And I press "Create"
    Then I should see "Fandom, relationship, and character tags must not add up to more than 5. You have entered 6 of these tags, so you must remove 1 of them."
