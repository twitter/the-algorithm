Feature: The All Fandoms page.
  Users should be able to see a list of the most popular canonical fandoms for
  each category, with correct filter counts.

  Scenario: Fandoms with more works should appear before fandoms with fewer.
    Given a media exists with name: "Movies", canonical: true
      And a canonical "Movies" fandom "Marvel Cinematic Universe" with 3 works
      And a canonical "Movies" fandom "Star Wars" with 2 works

    When I go to the media page
    Then I should see "Marvel Cinematic Universe (3)"
      And I should see "Star Wars (2)"
      And "Marvel Cinematic Universe" should appear before "Star Wars"

  Scenario: Only the top 5 fandoms of each type should appear.
    Given a media exists with name: "TV Shows", canonical: true
      And a canonical "TV Shows" fandom "Doctor Who" with 2 works
      And a canonical "TV Shows" fandom "Sherlock" with 2 works
      And a canonical "TV Shows" fandom "Star Trek" with 2 works
      And a canonical "TV Shows" fandom "Supernatural" with 2 works
      And a canonical "TV Shows" fandom "Teen Wolf" with 2 works
      And a canonical "TV Shows" fandom "The Forgotten" with 1 works

    When I go to the media page
    Then I should see "Doctor Who (2)"
      And I should see "Sherlock (2)"
      And I should see "Star Trek (2)"
      And I should see "Supernatural (2)"
      And I should see "Teen Wolf (2)"
      But I should not see "The Forgotten"

    When I follow "TV Shows"
    Then I should see "The Forgotten (1)"

  Scenario: Adding or removing works in a fandom should change the count.
    Given I have a canonical "Books" fandom tag named "Lord of the Rings"
      And I am logged in as "Tolkien"
      And I post a work "Fellowship of the Ring" with fandom "Lord of the Rings"
      And I post a work "The Two Towers" with fandom "Lord of the Rings"
      And I post a work "Return of the King" with fandom "Lord of the Rings"

    When I go to the media page
    Then I should see "Lord of the Rings (3)"

    When I delete the work "Return of the King"
      And the periodic filter count task is run
      And I go to the media page
    Then I should see "Lord of the Rings (2)"

    When I lock the work "The Two Towers"
      And the periodic filter count task is run
      And I go to the media page
    Then I should see "Lord of the Rings (2)"

    When I am logged out
      And I go to the media page
    Then I should see "Lord of the Rings (1)"

    When I am logged in as a "policy_and_abuse" admin
      And I go to the media page
    Then I should see "Lord of the Rings (2)"

    When I view the work "The Two Towers"
      And I follow "Hide Work"
      And the periodic filter count task is run
      And I go to the media page
    Then I should see "Lord of the Rings (1)"

  Scenario: Adding or removing a metatag changes the metatag's count.
    Given I have a canonical "Books" fandom tag named "Harry Potter"
      And I have a canonical "Books" fandom tag named "Wizarding World"
      And I am logged in as "Rowling"
      And I post a work "Philosopher's Stone" with fandom "Harry Potter"
      And I post a work "Fantastic Beasts" with fandom "Wizarding World"

    When I go to the media page
    Then I should see "Harry Potter (1)"
      And I should see "Wizarding World (1)"

    # Adding a metatag.
    When I am logged in as a tag wrangler
      And I subtag the tag "Harry Potter" to "Wizarding World"
      And the periodic filter count task is run
      And I go to the media page
    Then I should see "Harry Potter (1)"
      And I should see "Wizarding World (2)"

    # Removing the metatag.
    When I remove the metatag "Wizarding World" from "Harry Potter"
      And the periodic filter count task is run
      And I go to the media page
    Then I should see "Harry Potter (1)"
      And I should see "Wizarding World (1)"

  Scenario: Making a tag canonical and adding synonyms adjusts the counts.
    Given a media exists with name: "Books", canonical: true
      And I am logged in as "Asimov"
      And I post a work "I, Robot" with fandom "Robots"
      And I post a work "Caves of Steel" with fandom "R. Daneel Olivaw"

    # Make the tag canonical
    When I am logged in as a tag wrangler
      And I edit the tag "Robots"
      And I fill in "tag_media_string" with "Books"
      And I check "Canonical"
      And I press "Save changes"
    Then I should see "Tag was updated."

    When the periodic filter count task is run
      And I go to the media page
    Then I should see "Robots (1)"

    # Add the synonym
    When I edit the tag "R. Daneel Olivaw"
      And I fill in "Synonym" with "Robots"
      And I press "Save changes"
    Then I should see "Tag was updated."

    When the periodic filter count task is run
      And I go to the media page
    Then I should see "Robots (2)"

    # Remove the synonym
    When I edit the tag "R. Daneel Olivaw"
      And I fill in "Synonym" with ""
      And I press "Save changes"
    Then I should see "Tag was updated."

    When the periodic filter count task is run
      And I go to the media page
    Then I should see "Robots (1)"
