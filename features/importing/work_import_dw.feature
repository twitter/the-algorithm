# TODO: Enable tests after AO3-6353.
@wip
@works
Feature: Import Works from DW
  In order to have an archive full of works
  As an author
  I want to create new works by importing them from DW

  @import_dw
  Scenario: Importing a new work from an DW story with automatic metadata
    Given basic tags
      And a fandom exists with name: "Testing", canonical: true
      And the following activated user exists
        | login          | password    |
        | cosomeone      | something   |
      And I am logged in as "cosomeone" with password "something"
    When I go to the import page
      And I fill in "urls" with "https://ao3testing.dreamwidth.org/1726.html"
      And I select "English" from "Choose a language"
      And I press "Import"
    Then I should see "Preview"
      And I should see "Testing" within "dd.fandom"
      And I should see "General Audiences" within "dd.rating"
      And I should see "Character A/Character B" within "dd.relationship"
      And I should see "Published:2017-07-03"
      And I should see "Importing Test" within "h2.title"
      And I should not see "Import Test" within "h2.title"
      And I should see "Something I made for testing purposes." within "div.summary"
      And I should see "THIS IS USED FOR AUTOMATED TESTS" within "div.notes"
      And I should see "This is the body of my single-chapter work."
      And I should not see the image "alt" text "Add to memories"
      And I should not see the image "alt" text "Next Entry"
      And I should not see "location"
      And I should not see "music"
      And I should not see "mood"
      And I should not see "Entry tags"
      And I should not see "Crossposts"
    When I press "Post"
    Then I should see "Work was successfully posted."
    When I am on cosomeone's user page
    Then I should see "Importing Test"

  @import_dw_tables
  Scenario: Creating a new work from an DW story that has tables
  # This is to make sure that we don't accidentally strip other tables than
  # DW metadata tables esp. when there's no DW metadata table

    Given basic tags
      And the following activated user exists
        | login          | password    |
        | cosomeone      | something   |
      And I am logged in as "cosomeone" with password "something"
    When I go to the import page
      And I fill in "urls" with "https://ao3testing.dreamwidth.org/1836.html"
      And I select "English" from "Choose a language"
      And I press "Import"
    Then I should see "Preview"
      And I should see "Testing" within "dd.fandom"
      And I should see "Teen And Up Audiences" within "dd.rating"
      And I should see "Character A/Character B" within "dd.relationship"
      And I should see "Published:2017-07-03"
      And I should see "Single Chapter Fic from DW" within "h2.title"
      And I should see "THIS IS USED FOR AUTOMATED TESTS" within "div.notes"
      And I should see "This is the body of my single-chapter work."
      And I should not see the image "alt" text "Add to memories"
      And I should not see the image "alt" text "Next Entry"
      And I should see "My location"
      And I should see "My music"
    When I press "Post"
    Then I should see "Work was successfully posted."
    When I am on cosomeone's user page
    Then I should see "Single Chapter Fic from DW"

  @import_dw_tables_no_backdate
  Scenario: Creating a new work from an DW story without backdating it
    Given basic tags
      And the following activated user exists
        | login          | password    |
        | cosomeone      | something   |
      And I am logged in as a random user
    When I go to the import page
      And I fill in "urls" with "https://ao3testing.dreamwidth.org/1726.html"
      And I select "English" from "Choose a language"
      And I press "Import"
    Then I should see "Preview"
      And I should see "Importing Test"
    When I press "Edit"
    Then I should see "* Required information"
      And I should see "Importing Test"
    When I set the publication date to today
      And I check "No Archive Warnings Apply"
    When I press "Preview"
    Then I should see "Importing Test"
    When I press "Post"
    Then I should see "Work was successfully posted."
      And I should see "Importing Test" within "h2.title"
      And I should not see "Created:2017-08-29"
      And I should not see the image "alt" text "Add to memories!"
      And I should not see the image "alt" text "Next Entry"

  @import_dw_comm
  Scenario: Creating a new work from an DW story that is posted to a community
    Given basic tags
      And I am logged in as "cosomeone"
    When I go to the import page
      And I fill in "urls" with "https://ao3testingcomm.dreamwidth.org/702.html"
      And I select "English" from "Choose a language"
      And I press "Import"
    Then I should see "Preview"
      And I should see "Testing" within "dd.fandom"
      And I should see "Explicit" within "dd.rating"
      And I should see "Published:2017-08-29"
      And I should see "Rails 5.1 Single Chapter from Comm (DW)" within "h2.title"
      And I should see "This is an imported work" within "div.summary"
      And I should see "This is a Rails 5.1 importing test."
      And I should see the image "src" text "https://ao3testing.dreamwidth.org/file/495.jpg"
      And I should not see "ao3testingcomm"
      And I should not see "ao3testing"
      And I should not see the image "alt" text "Add to memories"
      And I should not see the image "alt" text "Next Entry"
      And I should not see "mood"
      And I should not see "Entry tags"
    When I press "Post"
    Then I should see "Work was successfully posted."
    When I follow "Edit"
    Then the "content" field should contain "class"
      And the "content" field should contain "entry-content"
    When I go to cosomeone's user page
    Then I should see "Rails 5.1 Single Chapter from Comm (DW)"

  @import_dw_multi_chapter
  Scenario: Creating a new multichapter work from a DW story
    Given basic tags
      And the following activated user exists
        | login          | password    |
        | cosomeone      | something   |
      And I am logged in as "cosomeone" with password "something"
      And I set my time zone to "UTC"
    When I go to the import page
      And I fill in "urls" with
        """
        https://ao3testing.dreamwidth.org/2460.html
        https://ao3testing.dreamwidth.org/2664.html
        https://ao3testing.dreamwidth.org/2968.html
        """
      And I select "English" from "Choose a language"
      And I choose "import_multiple_chapters"
      And I press "Import"
    Then I should see "Preview"
      And I should see "Testing" within "dd.fandom"
      And I should see "General Audiences" within "dd.rating"
      And I should see "Rails 5.1 Chaptered (DW)" within "h2.title"
      And I should not see "[FIC]" within "h2.title"
      And I should see "Something I made for testing purposes." within "div.summary"
      And I should see "THIS IS USED FOR AUTOMATED TESTS" within "div.notes"
      And I should see "This is a Rails 5.1 importing test, chapter 1."
    When I press "Post"
    Then I should see "Work was successfully posted."
      And I should see "Chapters:3/3"
      And I should see "Published:2017-08-29"
      And I should see "Completed:2017-08-29"
      And I should see "This is a Rails 5.1 importing test, chapter 1."
    When I follow "Next Chapter"
    Then I should see "This is a Rails 5.1 importing test, chapter 2."
      And I should see "This is the summary for chapter 2." within "div.summary"
      And I should see "THIS IS USED FOR AUTOMATED TESTS" within "div.notes"
      And I should see "My note on chapter 2." within "div.notes"
      And I should see "Rails 5.1 Chaptered (DW)" within "h3.title"
    When I am on cosomeone's user page
    Then I should see "Rails 5.1 Chaptered (DW)"
