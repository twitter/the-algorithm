@tags @tag_wrangling
Feature: Tag Wrangling - special cases

  Scenario: Create a new tag that differs from an existing tag by accents or other markers
    Rename a tag to change accents

  Given the following activated tag wrangler exists
    | login          |
    | wranglerette   |
    And I am logged in as "wranglerette"
    And a fandom exists with name: "Amelie", canonical: false
    And a character exists with name: "Romania", canonical: true
  When I edit the tag "Amelie"
    And I fill in "Synonym of" with "Amélie"
    And I press "Save changes"
  Then I should see "Amélie is considered the same as Amelie by the database"
    And I should not see "Tag was successfully updated."
  When I fill in "Name" with "Amélie"
    And I press "Save changes"
  Then I should see "Tag was updated"
    And I should see "Amélie"
    And I should not see "Amelie"
  When I follow "New Tag"
    And I fill in "Name" with "România"
    And I check "Canonical"
    And I choose "Additional Tag"
    And I press "Create Tag"
  Then I should see "Tag was successfully created."
    But I should see "România - Freeform"

  Scenario: Create a new tag that differs by more than just accents - user cannot change name

  Given the following activated tag wrangler exists
    | login          |
    | wranglerette   |
    And I am logged in as "wranglerette"
    And a fandom exists with name: "Amelie", canonical: false
  When I edit the tag "Amelie"
  When I fill in "Name" with "Amelia"
    And I press "Save changes"
  Then I should see "Name can only be changed by an admin."

  Scenario: Change capitalisation of a tag

  Given the following activated tag wrangler exists
    | login          |
    | wranglerette   |
    And I am logged in as "wranglerette"
    And a fandom exists with name: "amelie", canonical: false
  When I edit the tag "amelie"
    And I fill in "Synonym of" with "Amelie"
    And I press "Save changes"
  Then I should see "Amelie is considered the same as amelie by the database"
    And I should not see "Tag was successfully updated."
  When I fill in "Name" with "Amelie"
    And I press "Save changes"
  Then I should see "Tag was updated"
  
  Scenario: Works should be updated when capitalisation is changed
    See AO3-4230 for a bug with the caching of this
  
  Given the following activated tag wrangler exists
    | login          |
    | wranglerette   |
    And a fandom exists with name: "amelie", canonical: false
    And I am logged in as "author"
    And I post the work "wrong" with fandom "amelie"
  When I am logged in as "wranglerette"
    And I edit the tag "amelie"
    And I fill in "Name" with "Amelie"
    And I press "Save changes"
  Then I should see "Tag was updated"
  When I view the work "wrong"
  Then I should see "Amelie"
    And I should not see "amelie"
  When I am on the works page
  Then I should see "Amelie"
    And I should not see "amelie"
  
  Scenario: Works should be updated when accents are changed
    See AO3-4230 for a bug with the caching of this
  
  Given the following activated tag wrangler exists
    | login          |
    | wranglerette   |
    And a fandom exists with name: "Amelie", canonical: false
    And I am logged in as "author"
    And I post the work "wrong" with fandom "Amelie"
  When I am logged in as "wranglerette"
    And I edit the tag "Amelie"
    And I fill in "Name" with "Amélie"
    And I press "Save changes"
  Then I should see "Tag was updated"
  When I view the work "wrong"
  Then I should see "Amélie"
    And I should not see "Amelie"
  When I am on the works page
  Then I should see "Amélie"
    And I should not see "Amelie"

  Scenario: Tags with non-standard characters in them - question mark and period
  
  Given basic tags
    And the following activated tag wrangler exists
      | login           |
      | workauthor      |
    And a character exists with name: "Evan ?", canonical: true
    And a character exists with name: "James T. Kirk", canonical: true
  When I am logged in as "workauthor"
  When I post the work "Epic sci-fi"
    And I follow "Edit"
    And I fill in "Characters" with "Evan ?, James T. Kirk"
    And I press "Preview"
    And I press "Update"
  Then I should see "Work was successfully updated"
    And all indexing jobs have been run
  When I view the tag "Evan ?"
    And I follow "filter works"
  Then I should see "1 Work in Evan ?"
  When I view the tag "James T. Kirk"
    And I follow "filter works"
  Then I should see "1 Work in James T. Kirk"

  Scenario: Adding a noncanonical tag with "a.k.a.", and viewing works for that tag.

    Given basic tags
      And I am logged in as a random user

    When I post the work "Escape Attempt" with fandom "a.k.a. Jessica Jones"
      And I follow "a.k.a. Jessica Jones"

    Then I should see "This tag belongs to the Fandom Category"
      And I should see "a.k.a. Jessica Jones" within "h2.heading"
      And I should see "Escape Attempt"

  Scenario: Wranglers can edit a tag with "a.k.a." in the name.

    Given a noncanonical fandom "a.k.a. Jessica Jones"
      And a canonical fandom "Jessica Jones (TV)"

    When I am logged in as a tag wrangler
      And I edit the tag "a.k.a. Jessica Jones"
    Then I should see "Edit a.k.a. Jessica Jones Tag"

    When I fill in "Synonym of" with "Jessica Jones (TV)"
      And I press "Save changes"
    Then I should see "Tag was updated"

  Scenario Outline: Tag with a, d, h, q, or s between special characters.

    Given a canonical fandom "<char>a<char>d<char>h<char>q<char>s<char>"

    When I am logged in as a tag wrangler
      And I view the tag "<char>a<char>d<char>h<char>q<char>s<char>"
    Then I should see "This tag belongs to the Fandom Category"
      And I should see "Edit"

    When I follow "Edit"
    Then I should see "Edit <char>a<char>d<char>h<char>q<char>s<char> Tag"

    Examples:
      | char |
      | /    |
      | #    |
      | .    |
      | &    |
      | ?    |

  Scenario: Error messages show correct links even if tags contain special characters

    Given the following activated tag wrangler exists
      | login  | password    |
      | Enigel | wrangulate! |
      And a character exists with name: "Evelyn \"Evie\" Carnahan", canonical: false
      And a character exists with name: "Evelyn \"Evy\" Carnahan", canonical: false
      And I am logged in as "Enigel" with password "wrangulate!"
    When I edit the tag 'Evelyn "Evy" Carnahan'
      And I fill in "Synonym of" with 'Evelyn "Evie" Carnahan'
      And I press "Save changes"
    Then I should see "is not a canonical tag. Please make it canonical before adding synonyms to it."
    When I follow 'Evelyn "Evie" Carnahan'
    Then I should be on the 'Evelyn "Evie" Carnahan' tag edit page
