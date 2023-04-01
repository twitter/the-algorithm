@bookmarks
Feature: Private bookmarks
  In order to have an archive full of bookmarks
  As a humble user
  I want to bookmark some works privately

  @disable_caching
  Scenario: private bookmarks on public and restricted works

    Given dashboard counts expire after 10 seconds
      And a canonical fandom "Stargate SG-1"
      And I am logged in as "workauthor"
      And I post the locked work "Secret Masterpiece"
      And I post the work "Public Masterpiece"
      And I post the work "Another Masterpiece"
    When I am logged in as "avid_bookmarker"
      And I add the pseud "infrequent_bookmarker"
      And I start a new bookmark for "Secret Masterpiece"
      And I check "Rec"
      And I check "Private bookmark"
      And I press "Create"
    Then I should see "Bookmark was successfully created"
      And I should see the image "title" text "Restricted"
      And I should not see "Rec"
      And I should see "Private Bookmark"
      And I should see "0" within ".count"
    When I start a new bookmark for "Public Masterpiece"
      And I check "Rec"
      And I check "Private bookmark"
      And I press "Create"
    Then I should see "Bookmark was successfully created"
      And I should not see the image "title" text "Restricted"
      And I should not see "Rec"
      And I should see "Private Bookmark"
      And I should see "0" within ".count"
    When I start a new bookmark for "Another Masterpiece"
      And I select "infrequent_bookmarker" from "bookmark_pseud_id"
      And I check "Private bookmark"
      And I press "Create"
      And all indexing jobs have been run
    Then I should see "Bookmark was successfully created"
      And I should not see the image "title" text "Restricted"
      And I should not see "Rec"
      And I should see "Private Bookmark"
      And I should see "0" within ".count"

    # Private bookmarks should not show on the main bookmark page, but should show on your own bookmark page

    When I go to the bookmarks page
    Then I should not see "Secret Masterpiece"
      And I should not see "Public Masterpiece"
      And I should not see "Another Masterpiece"
    When I am on avid_bookmarker's bookmarks page
    Then I should see "3 Bookmarks by avid_bookmarker"
      And I should see "Bookmarks (0)"
      And I should see "Public Masterpiece"
      And I should see "Secret Masterpiece"
      And I should see "Another Masterpiece"
    When I wait 11 seconds
      And I reload the page
    Then I should see "Bookmarks (3)"
    When I go to the bookmarks page for user "avid_bookmarker" with pseud "infrequent_bookmarker"
    Then I should see "1 Bookmark by infrequent_bookmarker (avid_bookmarker)"
      And I should see "Bookmarks (1)"
      And I should see "Another Masterpiece"
      But I should not see "Secret Masterpiece"
      And I should not see "Public Masterpiece"

    # Private bookmarks should not be visible when logged out

    When I log out
      And I go to the bookmarks page
    Then I should not see "Secret Masterpiece"
      And I should not see "Public Masterpiece"
      And I should not see "Another Masterpiece"
      And I should not see "avid_bookmarker"
    When I go to avid_bookmarker's bookmarks page
    Then I should see "Bookmarks (0)"
      And I should not see "Secret Masterpiece"
      And I should not see "Public Masterpiece"
      And I should not see "Another Masterpiece"
    When I go to the bookmarks page for user "avid_bookmarker" with pseud "infrequent_bookmarker"
    Then I should see "Bookmarks (0)"
      And I should not see "Secret Masterpiece"
      And I should not see "Public Masterpiece"
      And I should not see "Another Masterpiece"
    When I go to the works page
    Then I should not see "Secret Masterpiece"
      And I should see "Public Masterpiece"
      And I should not see "Bookmarks:"
      And I should not see "Bookmarks: 1"
    When I view the work "Public Masterpiece"
    Then I should not see "Bookmarks:"
      And I should not see "Bookmarks:1"

    # Private bookmarks should not be visible to other users

    When I am logged in as "otheruser"
      And I go to the bookmarks page
    Then I should not see "Secret Masterpiece"
      And I should not see "Public Masterpiece"
      And I should not see "Another Masterpiece"
    When I go to avid_bookmarker's bookmarks page
    Then I should see "Bookmarks (0)"
      And I should not see "Secret Masterpiece"
      And I should not see "Public Masterpiece"
      And I should not see "Another Masterpiece"
    When I go to the bookmarks page for user "avid_bookmarker" with pseud "infrequent_bookmarker"
    Then I should see "Bookmarks (0)"
      And I should not see "Secret Masterpiece"
      And I should not see "Public Masterpiece"
      And I should not see "Another Masterpiece"
    When I go to the works page
    Then I should see "Public Masterpiece"
      And I should see "Another Masterpiece"
      And I should not see "Secret Masterpiece"
      And I should not see "Bookmarks:"
      And I should not see "Bookmarks: 1"

    # Private bookmarks should not be visible even to the author

    When I am logged in as "workauthor"
      And I go to the bookmarks page
    Then I should not see "Secret Masterpiece"
      And I should not see "Public Masterpiece"
      And I should not see "Another Masterpiece"
    When I go to avid_bookmarker's bookmarks page
    Then I should see "Bookmarks (0)"
      And I should not see "Secret Masterpiece"
      And I should not see "Public Masterpiece"
      And I should not see "Another Masterpiece"
    When I go to the bookmarks page for user "avid_bookmarker" with pseud "infrequent_bookmarker"
    Then I should see "Bookmarks (0)"
      And I should not see "Secret Masterpiece"
      And I should not see "Public Masterpiece"
      And I should not see "Another Masterpiece"

    # Private bookmarks should not be visible when logged out, even if there are other bookmarks on that work

    When I am logged in as "otheruser"
      And I view the work "Public Masterpiece"
      And I rec the current work
      And all indexing jobs have been run
    When I log out
      And I go to the bookmarks page
    Then I should not see "Secret Masterpiece"
      And I should not see "Another Masterpiece"
      And I should see "Public Masterpiece"
      And I should not see "avid_bookmarker"
      And I should see "otheruser"
    When I go to avid_bookmarker's bookmarks page
    Then I should not see "Secret Masterpiece"
      And I should not see "Public Masterpiece"
      And I should not see "Another Masterpiece"
    When I go to the bookmarks page for user "avid_bookmarker" with pseud "infrequent_bookmarker"
    Then I should see "Bookmarks (0)"
      And I should not see "Secret Masterpiece"
      And I should not see "Public Masterpiece"
      And I should not see "Another Masterpiece"
    When I go to the works page
    Then I should not see "Secret Masterpiece"
      And I should see "Public Masterpiece"
      And I should not see "Bookmarks: 2"
      And I should see "Bookmarks: 1"
      And I should see "Another Masterpiece"
    When I view the work "Public Masterpiece"
    Then I should not see "Bookmarks:2"
      And I should see "Bookmarks:1"
    When I follow "1"
    Then I should see "List of Bookmarks"
      And I should see "Public Masterpiece"
      And I should see "otheruser"
      And I should not see "avid_bookmarker"

    # Private bookmarks should not show on tag's page

    When I go to the bookmarks tagged "Stargate SG-1"
    Then I should not see "Secret Masterpiece"
      And I should see "Public Masterpiece"
      And I should not see "Another Masterpiece"
      And I should not see "avid_bookmarker"
      And I should see "otheruser"
      # This *should* be 1, because there's no way for a bookmark to appear on
      # a tag bookmark page if the bookmarkable has a public_bookmark_count of
      # 0. However, caching means that this is actually 0:
      And I should see "0" within ".count"
      And I should not see "2" within ".count"

    # Private bookmarks should not be visible to admins, but the admin
    # should be able to see how many private bookmarks the user has

    When I am logged in as an admin
      And I go to avid_bookmarker's bookmarks page
    Then I should see "Bookmarks (3)"
      But I should not see "Secret Masterpiece"
      And I should not see "Public Masterpiece"
      And I should not see "Another Masterpiece"
    When I go to the bookmarks page for user "avid_bookmarker" with pseud "infrequent_bookmarker"
    Then I should see "Bookmarks (1)"
      But I should not see "Another Masterpiece"
