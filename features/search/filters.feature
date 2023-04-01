@users
Feature: Filters
  In order to ensure filtering works on works and bookmarks
  As a humble user
  I want to filter on a user's works and bookmarks

  Background:
    Given a canonical fandom "The Hobbit"
      And a canonical fandom "Harry Potter"
      And a canonical fandom "Legend of Korra"
      And I am logged in as "meatloaf"
      And I post the work "A Hobbit's Meandering" with fandom "The Hobbit"
      And I post the work "Bilbo Does the Thing" with fandom "The Hobbit, Legend of Korra"
      And I post the work "Roonal Woozlib and the Ferrets of Nimh" with fandom "Harry Potter"

  @javascript
  Scenario: You can filter through a user's works using inclusion filters
    When I go to meatloaf's user page
      And I follow "Works (3)"
    Then I should see "A Hobbit's Meandering"
      And I should see "Bilbo Does the Thing"
      And I should see "Roonal Woozlib and the Ferrets of Nimh"
      And I should see "Include"
      And I should see "Exclude"
    When I press "Fandoms" within "dd.include"
    Then I should see "The Hobbit (2)" within "#include_fandom_tags"
      And I should see "Harry Potter (1)" within "#include_fandom_tags"
      And I should see "Legend of Korra (1)" within "#include_fandom_tags"
    When I check "The Hobbit (2)" within "#include_fandom_tags"
      And I press "Sort and Filter"
    Then I should see "A Hobbit's Meandering"
      And I should see "Bilbo Does the Thing"
      And I should not see "Roonal Woozlib and the Ferrets of Nimh"
    When I press "Fandoms" within "dd.include"
    Then I should see "The Hobbit (2)" within "#include_fandom_tags"
      And I should see "Legend of Korra (1)" within "#include_fandom_tags"
      And I should not see "Harry Potter (1)" within "#include_fandom_tags"
    When I check "Legend of Korra (1)" within "#include_fandom_tags"
      And press "Sort and Filter"
    Then I should see "Bilbo Does the Thing"
      And I should not see "A Hobbit's Meandering"
      And I should not see "Roonal Woozlib and the Ferrets of Nimh"
    When I follow "Clear Filters"
    Then I should see "3 Works by meatloaf"
      And I should see "A Hobbit's Meandering"
      And I should see "Bilbo Does the Thing"
      And I should see "Roonal Woozlib and the Ferrets of Nimh"
    When I press "Fandoms" within "dd.include"
    Then the "The Hobbit (2)" checkbox within "#include_fandom_tags" should not be checked
      And the "Legend of Korra (1)" checkbox within "#include_fandom_tags" should not be checked

  @javascript
  Scenario: You can filter through a user's works using exclusion filters
    When I go to meatloaf's user page
      And I follow "Works (3)"
    When I press "Fandoms" within "dd.exclude"
    Then I should see "The Hobbit (2)" within "#exclude_fandom_tags"
      And I should see "Harry Potter (1)" within "#exclude_fandom_tags"
      And I should see "Legend of Korra (1)" within "#exclude_fandom_tags"
    When I check "Harry Potter (1)" within "#exclude_fandom_tags"
      And I press "Sort and Filter"
    Then I should see "Bilbo Does the Thing"
      And I should see "A Hobbit's Meandering"
      And I should not see "Roonal Woozlib and the Ferrets of Nimh"
    When I press "Fandoms" within "dd.exclude"
    Then I should see "The Hobbit (2)" within "#exclude_fandom_tags"
      And I should see "Legend of Korra (1)" within "#exclude_fandom_tags"
      And I should see "Harry Potter (0)" within "#exclude_fandom_tags"
    When I check "Legend of Korra (1)" within "#exclude_fandom_tags"
      And I press "Sort and Filter"
    Then I should see "A Hobbit's Meandering"
      And I should not see "Bilbo Does the Thing"
      And I should not see "Roonal Woozlib and the Ferrets of Nimh"
    When I follow "Clear Filters"
    Then I should see "3 Works by meatloaf"
      And I should see "A Hobbit's Meandering"
      And I should see "Bilbo Does the Thing"
      And I should see "Roonal Woozlib and the Ferrets of Nimh"
    When I press "Fandoms" within "dd.exclude"
    Then the "Legend of Korra (1)" checkbox within "#exclude_fandom_tags" should not be checked
      And the "Harry Potter (1)" checkbox within "#exclude_fandom_tags" should not be checked

  @javascript
  Scenario: Filter through a user's works with non-existent tags
    Given the tag "legend korra" does not exist

    When I go to meatloaf's works page
      And I fill in "Other tags to include" with "legend korra"
      And I press "Sort and Filter"
    Then I should see "1 Work by meatloaf"
      And I should see "Bilbo Does the Thing"
      And I should not see "A Hobbit's Meandering"
      And I should not see "Roonal Woozlib and the Ferrets of Nimh"

    When I go to meatloaf's works page
      And I fill in "Other tags to exclude" with "legend korra"
      And I press "Sort and Filter"
    Then I should see "2 Works by meatloaf"
      And I should not see "Bilbo Does the Thing"
      And I should see "A Hobbit's Meandering"
      And I should see "Roonal Woozlib and the Ferrets of Nimh"

  @javascript
  Scenario: You can filter through a user's bookmarks using inclusion filters
    Given I am logged in as "recengine"
      And I bookmark the work "Bilbo Does the Thing"
      And I bookmark the work "A Hobbit's Meandering"
      And I bookmark the work "Roonal Woozlib and the Ferrets of Nimh"
    When I go to recengine's user page
      And I follow "Bookmarks (3)"
    Then I should see "Bilbo Does the Thing"
      And I should see "A Hobbit's Meandering"
      And I should see "Roonal Woozlib and the Ferrets of Nimh"
      And I should see "Include"
      And I should see "Exclude"
    When I press "Fandoms" within "dd.include"
    Then I should see "The Hobbit (2)" within "#include_fandom_tags"
      And I should see "Harry Potter (1)" within "#include_fandom_tags"
      And I should see "Legend of Korra (1)" within "#include_fandom_tags"
    When I check "The Hobbit (2)" within "#include_fandom_tags"
      And I press "Sort and Filter"
    Then I should see "A Hobbit's Meandering"
      And I should see "Bilbo Does the Thing"
      And I should not see "Roonal Woozlib and the Ferrets of Nimh"
    When I press "Fandoms" within "dd.include"
    Then I should see "The Hobbit (2)" within "#include_fandom_tags"
      And I should see "Legend of Korra (1)" within "#include_fandom_tags"
      And I should not see "Harry Potter (1)" within "#include_fandom_tags"
    When I check "Legend of Korra (1)" within "#include_fandom_tags"
      And press "Sort and Filter"
    Then I should see "Bilbo Does the Thing"
      And I should not see "A Hobbit's Meandering"
      And I should not see "Roonal Woozlib and the Ferrets of Nimh"

  @javascript
  Scenario: You can filter through a user's bookmarks using exclusion filters
    Given I am logged in as "recengine"
      And I bookmark the work "Bilbo Does the Thing"
      And I bookmark the work "A Hobbit's Meandering"
      And I bookmark the work "Roonal Woozlib and the Ferrets of Nimh"
    When I go to recengine's user page
      And I follow "Bookmarks (3)"
    When I press "Fandoms" within "dd.exclude"
    Then the "The Hobbit (2)" checkbox within "#exclude_fandom_tags" should not be checked
      And the "Harry Potter (1)" checkbox within "#exclude_fandom_tags" should not be checked
      And the "Legend of Korra (1)" checkbox within "#exclude_fandom_tags" should not be checked
    When I check "Harry Potter (1)" within "#exclude_fandom_tags"
      And I press "Sort and Filter"
    Then I should see "Bilbo Does the Thing"
      And I should see "A Hobbit's Meandering"
      And I should not see "Roonal Woozlib and the Ferrets of Nimh"
    When I press "Fandoms" within "dd.exclude"
    Then the "The Hobbit (2)" checkbox within "#exclude_fandom_tags" should not be checked
      And the "Legend of Korra (1)" checkbox within "#exclude_fandom_tags" should not be checked
      And the "Harry Potter (0)" checkbox within "#exclude_fandom_tags" should be checked
    When I check "Legend of Korra (1)" within "#exclude_fandom_tags"
      And I press "Sort and Filter"
    Then I should see "A Hobbit's Meandering"
      And I should not see "Bilbo Does the Thing"
      And I should not see "Roonal Woozlib and the Ferrets of Nimh"

  @javascript
  Scenario: Filter a user's bookmarks by "Search within results" and "Search bookmarker's tags and notes"
    Given I am logged in as "recengine"
      And I bookmark the work "Bilbo Does the Thing" with the tags "hobbit"
      And I bookmark the work "A Hobbit's Meandering" with the tags "bilbo"

    When I go to my bookmarks page
      And I fill in "Search within results" with "bilbo"
      And I press "Sort and Filter"
    Then I should see "1 Bookmark found by recengine"
      And I should see "Bilbo Does the Thing"
      And I should not see "A Hobbit's Meandering"

    When I go to my bookmarks page
      And I fill in "Search bookmarker's tags and notes" with "bilbo"
      And I press "Sort and Filter"
    Then I should see "1 Bookmark found by recengine"
      And I should see "A Hobbit's Meandering"
      And I should not see "Bilbo Does the Thing"

  @javascript
  Scenario: Filter a user's bookmarks by bookmarker's tags
    Given I am logged in as "recengine"
      And I bookmark the work "Bilbo Does the Thing" with the tags "to read,been here"
      And I bookmark the work "A Hobbit's Meandering" with the tags "to read"
      And I bookmark the work "Roonal Woozlib and the Ferrets of Nimh" with the tags "been here"

    # Use an include checkbox
    When I go to my bookmarks page
      And I press "Bookmarker's Tags" within "dd.include"
    Then the "to read (2)" checkbox within "#include_tag_tags" should not be checked
      And the "been here (2)" checkbox within "#include_tag_tags" should not be checked
    When I check "to read (2)" within "#include_tag_tags"
      And I press "Sort and Filter"
    Then I should see "2 Bookmarks by recengine"
      And the "to read (2)" checkbox within "#include_tag_tags" should be checked
      And I should see "Bilbo Does the Thing"
      And I should see "A Hobbit's Meandering"
      And I should not see "Roonal Woozlib and the Ferrets of Nimh"

    # Use a second include checkbox for bookmarks with both tags
    When I check "been here (1)" within "#include_tag_tags"
      And I press "Sort and Filter"
    Then I should see "1 Bookmark by recengine"
      And I should see "Bilbo Does the Thing"

    # Use an exclude checkbox
    When I go to my bookmarks page
      And I press "Bookmarker's Tags" within "dd.exclude"
    Then the "to read (2)" checkbox within "#exclude_tag_tags" should not be checked
      And the "been here (2)" checkbox within "#exclude_tag_tags" should not be checked
    When I check "to read (2)" within "#exclude_tag_tags"
      And I press "Sort and Filter"
    Then I should see "1 Bookmark by recengine"
      And the "to read (0)" checkbox within "#exclude_tag_tags" should be checked
      And I should not see "Bilbo Does the Thing"
      And I should not see "A Hobbit's Meandering"
      And I should see "Roonal Woozlib and the Ferrets of Nimh"

    # Use a second exclude checkbox for bookmarks with neither tags
    When I check "been here (1)" within "#exclude_tag_tags"
      And I press "Sort and Filter"
    Then I should see "0 Bookmarks by recengine"

    # Use include field
    When I go to my bookmarks page
      And I fill in "Other bookmarker's tags to include" with "to read"
      And I press "Sort and Filter"
    Then I should see "2 Bookmarks by recengine"
      And I should see "Bilbo Does the Thing"
      And I should see "A Hobbit's Meandering"
      And I should not see "Roonal Woozlib and the Ferrets of Nimh"

    # Use exclude field
    When I go to my bookmarks page
      And I fill in "Other bookmarker's tags to exclude" with "to read"
      And I press "Sort and Filter"
    Then I should see "1 Bookmark by recengine"
      And I should not see "Bilbo Does the Thing"
      And I should not see "A Hobbit's Meandering"
      And I should see "Roonal Woozlib and the Ferrets of Nimh"

  Scenario: Filter bookmarks by a tag that appears both on bookmarked works and in bookmarker's tags
    Given I am logged in as "recengine"
      And I bookmark the work "Bilbo Does the Thing"
      And I bookmark the work "Roonal Woozlib and the Ferrets of Nimh" with the tags "The Hobbit"

    # Exclude a tag as a work tag but not as a bookmarker's tag
    When I go to my bookmarks page
    Then the "The Hobbit (1)" checkbox within "#exclude_fandom_tags" should not be checked
      And the "The Hobbit (1)" checkbox within "#exclude_tag_tags" should not be checked

    When I check "The Hobbit (1)" within "#exclude_fandom_tags"
      And I press "Sort and Filter"
    Then I should see "1 Bookmark by recengine"
      And I should not see "Bilbo Does the Thing"
      And I should see "Roonal Woozlib and the Ferrets of Nimh"
      And the "The Hobbit (0)" checkbox within "#exclude_fandom_tags" should be checked
      And the "The Hobbit (1)" checkbox within "#exclude_tag_tags" should not be checked

    # Exclude a tag as a bookmarker's tag but not as a work tag
    When I go to my bookmarks page
      And I check "The Hobbit (1)" within "#exclude_tag_tags"
      And I press "Sort and Filter"
    Then I should see "1 Bookmark by recengine"
      And I should see "Bilbo Does the Thing"
      And I should not see "Roonal Woozlib and the Ferrets of Nimh"
      And the "The Hobbit (0)" checkbox within "#exclude_tag_tags" should be checked
      And the "The Hobbit (1)" checkbox within "#exclude_fandom_tags" should not be checked

    # Exclude a tag as a bookmarker's tag AND as a work tag
    When I go to my bookmarks page
      And I check "The Hobbit (1)" within "#exclude_fandom_tags"
      And I check "The Hobbit (1)" within "#exclude_tag_tags"
      And I press "Sort and Filter"
    Then I should see "0 Bookmarks by recengine"
      And I should not see "Bilbo Does the Thing"
      And I should not see "Roonal Woozlib and the Ferrets of Nimh"
      And the "The Hobbit (0)" checkbox within "#exclude_fandom_tags" should be checked
      And the "The Hobbit (0)" checkbox within "#exclude_tag_tags" should be checked

  @javascript
  Scenario: Filter a user's bookmarks by non-existent tags
    Given the tag "legend korra" does not exist
      And the tag "fun crossover" does not exist
      And I am logged in as "recengine"
      And I bookmark the work "A Hobbit's Meandering" with the tags "fun"
      And I bookmark the work "Bilbo Does the Thing" with the tags "fun little crossover"

    When I go to my bookmarks page
      And I fill in "Other work tags to include" with "legend korra"
      And I press "Sort and Filter"
    Then I should see "1 Bookmark by recengine"
      And I should not see "A Hobbit's Meandering"
      And I should see "Bilbo Does the Thing"

    When I go to my bookmarks page
      And I fill in "Other work tags to exclude" with "legend korra"
      And I press "Sort and Filter"
    Then I should see "1 Bookmark by recengine"
      And I should see "A Hobbit's Meandering"
      And I should not see "Bilbo Does the Thing"

    When I go to my bookmarks page
      And I fill in "Other bookmarker's tags to include" with "fun crossover"
      And I press "Sort and Filter"
    Then I should see "1 Bookmark by recengine"
      And I should not see "A Hobbit's Meandering"
      And I should see "Bilbo Does the Thing"

    When I go to my bookmarks page
      And I fill in "Other bookmarker's tags to exclude" with "fun crossover"
      And I press "Sort and Filter"
    Then I should see "1 Bookmark by recengine"
      And I should see "A Hobbit's Meandering"
      And I should not see "Bilbo Does the Thing"

  @javascript
  Scenario: Tag bookmark pages should display bookmarked items instead of bookmarks, and the sidebar counts should reflect that.
    Given I am logged in as "meatloaf"
      And I bookmark the work "Bilbo Does the Thing"
      And I bookmark the work "A Hobbit's Meandering"
      And I am logged out
      And I am logged in as "anothermeatloaf"
      And I bookmark the work "Bilbo Does the Thing"
      And I bookmark the work "A Hobbit's Meandering"
      And all indexing jobs have been run
    When I go to the bookmarks tagged "The Hobbit"
    Then I should see "2 Bookmarked Items in The Hobbit"
    When I follow "Fandoms"
    Then I should see "The Hobbit (2)"
