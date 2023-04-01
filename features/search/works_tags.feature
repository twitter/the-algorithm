@works @search
Feature: Search works by tag
  As a user
  I want to search works by tags

  Scenario: Searching for a fandom in the header search returns works with (a)
  the exact tag, (b) the tag's syns, (c) the tag's subtags and _their_ syns, and
  (d) any other tags or text matching the search term; refining the search with
  the fandom field returns only works with (a), (b), or (c)
    Given a set of Star Trek works for searching
    When I search for works containing "Star Trek"
    Then I should see "You searched for: Star Trek"
      And I should see "6 Found"
      And the results should contain the fandom tag "Star Trek"
      And the results should contain the subtags of "Star Trek"
      # A synonym of one of the Star Trek subtags
      And the results should contain the fandom tag "ST: TOS"
      And the results should contain a freeform mentioning "Star Trek"
    When I follow "Edit Your Search"
    Then the field labeled "Any Field" should contain "Star Trek"
    When I fill in "Fandoms" with "Star Trek"
      And I press "Search" within "#new_work_search"
    Then I should see "You searched for: Star Trek Tags: Star Trek"
      And I should see "5 Found"
      And the results should contain the fandom tag "Star Trek"
      And the results should contain the subtags of "Star Trek"
      # A synonym of one of the Star Trek subtags
      And the results should contain the fandom tag "ST: TOS"
      And the results should not contain a freeform mentioning "Star Trek"
    When I follow "Edit Your Search"
    Then the field labeled "Any Field" should contain "Star Trek"
      And the field labeled "Fandoms" should contain "Star Trek"

  Scenario: Searching by fandom for a tag that does not exist returns 0 results
    Given a set of Star Trek works for searching
    When I am on the search works page
      And I fill in "Fandoms" with "Star Trek: The Next Generation"
      And I press "Search" within "#new_work_search"
    Then I should see "You searched for: Tags: Star Trek: The Next Generation"
      And I should see "No results found."
      And I should see "You may want to edit your search to make it less specific."

  # We use JavaScript here because otherwise there is a minor spacing issue with
  # "You searched for" on the results page and the coder who wrote this test was
  # offended by it
  @javascript
  Scenario: Searching by fandom for two fandoms returns only works tagged with
  both fandoms (or syns or subtags of those fandoms)
    Given a set of Star Trek works for searching
    When I am on the search works page
      And I fill in "Fandoms" with "Star Trek, Battlestar Galactica (2003)"
      And I press "Search" within "#new_work_search"
    Then I should see "You searched for: Tags: Star Trek, Battlestar Galactica (2003)"
      And I should see "1 Found"
      # A synonym of one of the Star Trek subtags
      And the results should contain the fandom tag "ST: TOS"
      And the results should contain the fandom tag "Battlestar Galactica (2003)"
    When I follow "Edit Your Search"
      Then "Star Trek" should already be entered in the work search fandom autocomplete field
      And "Battlestar Galactica (2003)" should already be entered in the work search fandom autocomplete field

  Scenario: Searching by rating returns only works using that rating
    Given a set of works with various ratings for searching
    When I am on the search works page
      And I select "Teen And Up Audiences" from "Rating"
      And I press "Search" within "#new_work_search"
    Then I should see "You searched for: Tags: Teen And Up Audiences"
      And I should see "1 Found"
      And the results should contain the rating tag "Teen And Up Audiences"
    When I follow "Edit Your Search"
    Then "Teen And Up Audiences" should be selected within "Rating"

  Scenario: Searching for Explicit or Mature in the header returns works with
  (a) either rating or (b) other tags or text matching either rating; editing
  the search to use the ratings' filter_ids returns only (a)
    Given a set of works with various ratings for searching
    When I search for works containing "Mature || Explicit"
    Then I should see "You searched for: Mature || Explicit"
      And I should see "3 Found"
      And the results should contain the rating tag "Mature"
      And the results should contain the rating tag "Explicit"
      And the results should contain a summary mentioning "explicit"
    When I follow "Edit Your Search"
    Then the field labeled "Any Field" should contain "Mature || Explicit"
    When I exclude the tags "Mature" and "Explicit" by filter_id
      And I press "Search" within "#new_work_search"
    Then the search summary should include the filter_id for "Mature"
      And the search summary should include the filter_id for "Explicit"
      And the results should not contain the rating tag "Mature"
      And the results should not contain the rating tag "Explicit"

  Scenario: Using Any Field to exclude works using (a) one of the two ratings or (b) other tags or text matching either rating
    Given a set of works with various ratings for searching
    When I am on the search works page
      And I fill in "Any Field" with "-Mature -Explicit"
      And I press "Search" within "#new_work_search"
    Then I should see "You searched for: -Mature -Explicit"
      And I should see "3 Found"
      And the results should contain the rating tag "General Audiences"
      And the results should contain the rating tag "Teen And Up Audiences"
      And the results should contain the rating tag "Not Rated"
      And the results should not contain a summary mentioning "explicit"
    When I follow "Edit Your Search"
    Then the field labeled "Any Field" should contain "-Mature -Explicit"

  Scenario: Searching by warning returns all works using that warning tag
    Given a set of works with various warnings for searching
    When I am on the search works page
      And I check "No Archive Warnings Apply"
      And I press "Search" within "#new_work_search"
    Then I should see "You searched for: Tags: No Archive Warnings Apply"
      And I should see "2 Found"
      And the 1st result should contain "No Archive Warnings Apply"
      And the 2nd result should contain "No Archive Warnings Apply"
    When I follow "Edit Your Search"
    Then the "No Archive Warnings Apply" checkbox should be checked

  Scenario: Using the header search to exclude works with certain warnings using the warnings' filter_ids
    Given a set of works with various warnings for searching
    When I search for works without the "Rape/Non-Con" and "Underage" filter_ids
    Then the search summary should include the filter_id for "Rape/Non-Con"
      And the search summary should include the filter_id for "Underage"
      And I should see "5 Found"
      And the results should not contain the warning tag "Underage"
      And the results should not contain the warning tag "Rape/Non-Con"

  Scenario: Searching by category returns all works using that category; search
  can be refined using Any Field to return works using only that category
    Given a set of works with various categories for searching
    When I am on the search works page
      And I check "F/F"
      And I press "Search" within "#new_work_search"
    Then I should see "You searched for: Tags: F/F"
      And I should see "2 Found"
      And the results should contain the category tag "F/F"
      And the results should contain the category tag "M/M, F/F"
    When I follow "Edit Your Search"
    Then the "F/F" checkbox should be checked
    When I fill in "Any Field" with "-M/M"
      And I press "Search" within "#new_work_search"
    Then I should see "You searched for: -M/M Tags: F/F"
      And I should see "1 Found"

  Scenario: Searching by category for Multi only returns works tagged with the
  Multi category, not works tagged with multiple categories
    Given a set of works with various categories for searching
    When I am on the search works page
      And I check "Multi"
      And I press "Search" within "#new_work_search"
    Then I should see "You searched for: Tags: Multi"
      And I should see "1 Found"
      And the results should contain the category tag "Multi"
      And the results should not contain the category tag "M/M, F/F"
    When I follow "Edit Your Search"
    Then the "Multi" checkbox should be checked

  Scenario: Searching for a character in the header search returns works with
  (a) the exact tag, (b) the tag's syns, or (c) any other tags or text matching
  the search term
    Given a set of Steve Rogers works for searching
    When I search for works containing "Steve Rogers"
    Then I should see "You searched for: Steve Rogers"
      And I should see "6 Found"
      And the results should contain the character tag "Steve Rogers"
      And the results should contain a synonym of "Steve Rogers"
      And the results should contain a relationship mentioning "Steve Rogers"
      And the results should contain a summary mentioning "Steve Rogers"
    When I follow "Edit Your Search"
    Then the field labeled "Any Field" should contain "Steve Rogers"

  Scenario: Searching by character for a tag with synonyms returns works using
  the exact tag or its synonyms
    Given a set of Steve Rogers works for searching
    When I am on the search works page
      And I fill in "Characters" with "Steve Rogers"
      And I press "Search" within "#new_work_search"
    Then I should see "You searched for: Tags: Steve Rogers"
      And I should see "4 Found"
      And the results should contain the character tag "Steve Rogers"
      And the results should contain a synonym of "Steve Rogers"
      And the results should not contain a relationship mentioning "Steve Rogers"
      And the results should not contain a summary mentioning "Steve Rogers"
    When I follow "Edit Your Search"
    Then the field labeled "Characters" should contain "Steve Rogers"

  Scenario: Searching for a relationship in the header search returns works
  with (a) the exact tag and (b) the tag's syns, and (c) any other tags or text
  matching the search term (e.g. a threesome); refining the search with the
  relationship field returns only (a) or (b)
    Given a set of Spock/Uhura works for searching
    When I search for works containing "Spock/Nyota Uhura"
    Then I should see "You searched for: Spock/Nyota Uhura"
      And I should see "3 Found"
      And the results should contain the relationship tag "Spock/Nyota Uhura"
      And the results should contain a synonym of "Spock/Nyota Uhura"
      And the results should contain the relationship tag "James T. Kirk/Spock/Nyota Uhura"
    When I follow "Edit Your Search"
    Then the field labeled "Any Field" should contain "Spock/Nyota Uhura"
    When I fill in "Relationships" with "Spock/Nyota Uhura"
      And I press "Search" within "#new_work_search"
    Then I should see "You searched for: Spock/Nyota Uhura Tags: Spock/Nyota Uhura"
      And I should see "2 Found"
      And the results should contain the relationship tag "Spock/Nyota Uhura"
      And the results should contain a synonym of "Spock/Nyota Uhura"
      And the results should not contain the relationship tag "James T. Kirk/Spock/Nyota Uhura"

  Scenario: Searching by relationship returns works using the exact tag or its
  synonyms
    Given a set of Kirk/Spock works for searching
    When I am on the search works page
      And I fill in "Relationships" with "James T. Kirk/Spock"
      And I press "Search" within "#new_work_search"
    Then I should see "You searched for: Tags: James T. Kirk/Spock"
      And I should see "4 Found"
      And the results should contain the relationship tag "James T. Kirk/Spock"
      And the results should contain the synonyms of "James T. Kirk/Spock"
    When I follow "Edit Your Search"
    Then the field labeled "Relationships" should contain "James T. Kirk/Spock"

  Scenario: Searching by relationship and category returns only works using the
  category and the exact relationship tag or its synonyms
    Given a set of Kirk/Spock works for searching
    When I am on the search works page
      And I fill in "Relationships" with "James T. Kirk/Spock"
      And I check "F/M"
      And I press "Search" within "#new_work_search"
    Then I should see "You searched for: Tags: F/M, James T. Kirk/Spock"
      And I should see "1 Found"
      And the results should contain the category tag "F/M"
      And the results should contain the relationship tag "Spirk"
    When I follow "Edit Your Search"
    Then the field labeled "Relationships" should contain "James T. Kirk/Spock"
      And the "F/M" checkbox should be checked

  Scenario: Searching by additional tags (freeforms) for a metatag with synonyms
  and subtags should return works using (a) the exact tag, (b) its synonyms, (c)
  its subtags, and (d) its subtags' synonyms
    Given a set of alternate universe works for searching
    When I am on the search works page
      And I fill in "Additional Tags" with "Alternate Universe"
      And I press "Search" within "#new_work_search"
    Then I should see "You searched for: Tags: Alternate Universe"
      And I should see "4 Found"
      And the results should contain the freeform tag "Alternate Universe"
      And the results should contain a synonym of "Alternate Universe"
      And the results should contain the freeform tag "High School AU"
      And the results should contain the freeform tag "Alternate Universe - Coffee Shops & Cafés"
      And the results should not contain the freeform tag "Coffee Shop AU"
    When I follow "Edit Your Search"
    Then the field labeled "Additional Tags" should contain "Alternate Universe"

  Scenario: Searching by additional tags (freeforms) for a synonym of a metatag
  returns only works using the exact tag
    Given a set of alternate universe works for searching
    When I am on the search works page
      And I fill in "Additional Tags" with "AU"
      And I press "Search" within "#new_work_search"
    Then I should see "You searched for: Tags: AU"
      And I should see "1 Found"
      And the results should contain the freeform tag "AU"
      And the results should not contain the freeform tag "High School AU"
      And the results should not contain the freeform tag "Coffee Shop AU"
      And the results should not contain a character mentioning "AU"
    When I follow "Edit Your Search"
    Then the field labeled "Additional Tags" should contain "AU"

  Scenario: Searching by additional tags (freeforms) for a tag with no direct
  uses returns works using the tag's synonyms
    Given a set of alternate universe works for searching
    When I am on the search works page
      And I fill in "Additional Tags" with "Alternate Universe - High School"
      And I press "Search" within "#new_work_search"
    Then I should see "You searched for: Tags: Alternate Universe - High School"
      And I should see "1 Found"
      And the results should contain a synonym of "Alternate Universe - High School"
    When I follow "Edit Your Search"
    Then the field labeled "Additional Tags" should contain "Alternate Universe - High School"

  Scenario: Searching by additional tags (freeforms) for a tag that has not been
  wrangled returns only works using tags containing the search term (not
  summaries, titles, etc)
    Given a set of alternate universe works for searching
    When I am on the search works page
      And I fill in "Additional Tags" with "Coffee Shop AU"
      And I press "Search" within "#new_work_search"
    Then I should see "You searched for: Tags: Coffee Shop AU"
      And I should see "1 Found"
      And the results should contain the freeform tag "Coffee Shop AU"
      And the results should not contain a summary mentioning "Coffee Shop AU"
    When I follow "Edit Your Search"
    Then the field labeled "Additional Tags" should contain "Coffee Shop AU"
