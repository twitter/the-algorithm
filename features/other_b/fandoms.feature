@fandoms
Feature: There is a list of unassigned Fandoms

  Scenario: A user can see the list of fandoms and filter it

    Given I have a canonical "TV Shows" fandom tag named "Steven Universe"
      And I have a canonical "Movies" fandom tag named "High School Musical"
      And I am logged in as "author"
      And I post the work "Stronger than you" with fandom "Steven Universe"
      And I post the work "Breaking free" with fandom "High School Musical"
      And I am logged in as a tag wrangler
    When I go to the unassigned fandoms page
    Then I should see "Steven Universe"
      And I should see "High School Musical"
    When I select "TV Shows" from "media_id"
      And I press "Sort and Filter"
    Then I should see "Steven Universe"
      And I should not see "High School Musical"
    When I select "Movies" from "media_id"
      And I press "Sort and Filter"
    Then I should see "High School Musical"
    When I follow "High School Musical"
    Then I should see "This tag belongs to the Fandom Category."

  Scenario: A user can see the list of relationships in a fandom

    Given the following typed tags exists
        | name                            | type         | canonical |
        | Steven Universe                 | Fandom       | true      |
        | Ruby/Sapphire (Steven Universe) | Relationship | true      |
        | Ruby (Steven Universe)          | Character    | true      |
        | Sapphire (Steven Universe)      | Character    | true      |
      And I add the fandom "Steven Universe" to the character "Ruby (Steven Universe)"
      And I add the fandom "Steven Universe" to the character "Sapphire (Steven Universe)"
      And I am logged in as "author"
      And I post the work "Stronger than you" with fandom "Steven Universe" with character "Ruby (Steven Universe)" with second character "Sapphire (Steven Universe)" with relationship "Ruby/Sapphire (Steven Universe)"
    When I go to the "Steven Universe" fandom relationship page
    Then I should see "Ruby (Steven Universe)"
      And I should see "Sapphire (Steven Universe)"
