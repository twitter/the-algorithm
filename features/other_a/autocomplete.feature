Feature: Display autocomplete for tags
  In order to facilitate posting
  I should be getting autocompletes for my tags

  @javascript
  Scenario: Only matching canonical tags should appear in autocomplete,
    and searching for the same data twice should produce same results
    Given I am logged in
      And a set of tags for testing autocomplete
      And I go to the new work page
    Then the tag autocomplete fields should list only matching canonical tags

  @javascript
  Scenario: If a fandom is entered, only characters/relationships in the fandom
    should appear in autocomplete
    Given I am logged in
      And a set of tags for testing autocomplete
      And I go to the new work page
    Then the fandom-specific tag autocomplete fields should list only fandom-specific canonical tags

  @javascript
  Scenario: Bookmark archive work form autocomplete should work
    Given I am logged in
      And a set of tags for testing autocomplete
    When I start a new bookmark
      And I enter text in the "Your tags" autocomplete field
    Then I should only see matching canonical tags in the autocomplete

  @javascript
  Scenario: Bookmark external work form autocomplete should work
    Given I am logged in
      And a set of tags for testing autocomplete
      And an external work
    When I go to the new external work page
    Then the tag autocomplete fields should list only matching canonical tags
      And the fandom-specific tag autocomplete fields should list only fandom-specific canonical tags
      And the external url autocomplete field should list the urls of existing external works

  @javascript
  Scenario: Work co-author and association autocompletes should work
    Given I am logged in
      And a set of collections for testing autocomplete
      And a set of users for testing autocomplete
      And basic tags
      And I go to the new work page
    Then the coauthor autocomplete field should list matching users
      And the gift recipient autocomplete field should list matching users
      And the collection item autocomplete field should list matching collections

  @javascript
  Scenario: Work co-author and association autocompletes should work with pseuds containing diacrictics
    Given basic tags
      And a set of users for testing autocomplete
      And "coauthor" has the pseud "çola"
      And I am logged in
      And I go to the new work page
    Then the coauthor autocomplete field should list matching users
    When I enter "c" in the "pseud_byline_autocomplete" autocomplete field
    Then the pseud autocomplete should contain "çola (coauthor)"
      And the pseud autocomplete should contain "coauthor"
    When I enter "ç" in the "pseud_byline_autocomplete" autocomplete field
    Then the pseud autocomplete should contain "çola (coauthor)"
      And the pseud autocomplete should contain "coauthor"


  @javascript
  Scenario: Collection autocomplete shows collection title and name
    Given I am logged in as "Scott" with password "password"
      And I post the work "All The Nice Things"
      And I set my preferences to allow collection invitations
      And I have the collection "Issue" with name "jb_fletcher"
      And I have the collection "Ïssue" with name "robert_stack"
      And I am logged in as "moderator"
    When I view the work "All The Nice Things"
      And I follow "Invite To Collections"
      And I enter "Issue" in the "Collection name(s)" autocomplete field
    Then I should see "jb_fletcher" in the autocomplete
      And I should see "robert_stack" in the autocomplete

  Scenario: Pseuds should be added and removed from autocomplete as they are changed
    Given I am logged in as "new_user"
    Then the pseud autocomplete should contain "new_user"
    When I add the pseud "extra"
    Then the pseud autocomplete should contain "extra (new_user)"
    When I change the pseud "extra" to "funny"
      And I go to my pseuds page
    Then I should not see "extra"
      And I should see "funny"
      And the pseud autocomplete should not contain "extra (new_user)"
      And the pseud autocomplete should contain "funny (new_user)"
    When I delete the pseud "funny"
    Then the pseud autocomplete should not contain "funny (new_user)"
      And the pseud autocomplete should contain "new_user"

  Scenario: Pseuds should be added and removed from autocomplete as usernames change
    Given I am logged in as "new_user"
      And I add the pseud "funny"
    When I change my username to "different_user"
    Then the pseud autocomplete should not contain "funny (new_user)"
      And the pseud autocomplete should not contain "new_user"
      And the pseud autocomplete should contain "different_user"
      And the pseud autocomplete should contain "funny (different_user)"
    When I try to delete my account as different_user
    Then a user account should not exist for "funny"
      And the pseud autocomplete should not contain "funny"
      And the pseud autocomplete should not contain "different_user (funny)"

  @javascript
  Scenario: Characters in a fandom with non-ASCII uppercase letters should appear in the autocomplete.

    Given basic tags
      And I am logged in
      And a canonical character "Bear" in fandom "Østenfor sol og vestenfor måne"
      And a canonical character "Beatrice" in fandom "Much Ado About Nothing"
      And I go to the new work page

    When I choose "Østenfor sol og vestenfor måne" from the "Fandoms" autocomplete
      And I enter "Bea" in the "Characters" autocomplete field
    Then I should see "Bear" in the autocomplete
      But I should not see "Beatrice" in the autocomplete

  @javascript
  Scenario: Accented uppercase letters should appear in the autocomplete.

    Given basic tags
      And I am logged in
      And a canonical character "Éowyn (Tolkien)"
      And a canonical character "Tybalt (Rómeó és Júlia)"
      And I go to the new work page

    When I enter "é" in the "Characters" autocomplete field
    Then I should see "Éowyn (Tolkien)" in the autocomplete
      And I should see "Tybalt (Rómeó és Júlia)" in the autocomplete
    When I enter "e" in the "Characters" autocomplete field
    Then I should see "Éowyn (Tolkien)" in the autocomplete
      And I should see "Tybalt (Rómeó és Júlia)" in the autocomplete


  @javascript
  Scenario: Other non-ASCII uppercase letters should appear in the autocomplete.

    Given basic tags
      And I am logged in
      And a canonical fandom "Østenfor sol og vestenfor måne"
      And I go to the new work page

    When I enter "ø" in the "Fandoms" autocomplete field
    Then I should see "Østenfor sol og vestenfor måne" in the autocomplete
    When I enter "ostenfor" in the "Fandoms" autocomplete field
    Then I should see "Østenfor sol og vestenfor måne" in the autocomplete

  @javascript
  Scenario: Characters with a non-ASCII uppercase letter will appear in fandom-specific autocompletes.

    Given basic tags
      And I am logged in
      And a canonical character "Éowyn" in fandom "Lord of the Rings"
      And I go to the new work page

    When I choose "Lord of the Rings" from the "Fandoms" autocomplete
      And I enter "É" in the "Characters" autocomplete field
    Then I should see "Éowyn" in the autocomplete
    When I enter "e" in the "Characters" autocomplete field
    Then I should see "Éowyn" in the autocomplete

  @javascript
  Scenario: Search terms are highlighted in autocomplete results
    Given I am logged in
      And basic tags
      And a canonical relationship "Cassian Andor & Jyn Erso"
      And a canonical character "Éowyn"
      And I go to the new work page

    When I enter "Jyn" in the "Relationships" autocomplete field
    Then I should see HTML "Cassian Andor &amp; <b>Jyn</b> Erso" in the autocomplete

    When I enter "Cass" in the "Relationships" autocomplete field
    Then I should see HTML "<b>Cass</b>ian Andor &amp; Jyn Erso" in the autocomplete

    When I enter "erso and" in the "Relationships" autocomplete field
    Then I should see HTML "Cassian <b>And</b>or &amp; Jyn <b>Erso</b>" in the autocomplete

    When I enter "Cassian Andor & Jyn Erso" in the "Relationships" autocomplete field
    Then I should see HTML "<b>Cassian</b> <b>Andor</b> &amp; <b>Jyn</b> <b>Erso</b>" in the autocomplete

    When I enter "é" in the "Characters" autocomplete field
    Then I should see HTML "<b>É</b>owyn" in the autocomplete

    # AO3-4976 There should not be stray semicolons if the query has...
    # ...trailing spaces
    When I enter "Jyn " in the "Relationships" autocomplete field
    Then I should see HTML "Cassian Andor &amp; <b>Jyn</b> Erso" in the autocomplete
    # ...leading spaces
    When I enter " Jyn" in the "Relationships" autocomplete field
    Then I should see HTML "Cassian Andor &amp; <b>Jyn</b> Erso" in the autocomplete
    # ...consecutive spaces
    When I enter "Jyn  Erso" in the "Relationships" autocomplete field
    Then I should see HTML "Cassian Andor &amp; <b>Jyn</b> <b>Erso</b>" in the autocomplete

  @javascript
  Scenario: Tags with symbols shouldn't match all other tags with symbols.

    Given basic tags
      And I am logged in
      And a canonical freeform "AU - Canon"
      And a canonical freeform "AU - Cats"
      And a canonical freeform "Science Fiction & Fantasy"
      And a canonical freeform "日月"
      And a canonical freeform "大小"
      And I go to the new work page

    When I enter "AU - Ca" in the "Additional Tags" autocomplete field
    Then I should see "AU - Canon" in the autocomplete
      And I should see "AU - Cats" in the autocomplete
      But I should not see "Science Fiction & Fantasy" in the autocomplete
    When I enter "日" in the "Additional Tags" autocomplete field
    Then I should see "日月" in the autocomplete
      But I should not see "大小" in the autocomplete

