@works
Feature: Import Works from LJ
  In order to have an archive full of works
  As an author
  I want to create new works by importing them from LJ
  @import_lj
  Scenario: Creating a new work from an LJ story with automatic metadata
    Given basic tags
      And a fandom exists with name: "Lewis", canonical: true
      And I am logged in as "cosomeone"
    When I go to the import page
      And I fill in "urls" with "http://rebecca2525.livejournal.com/3562.html"
      And I select "English" from "Choose a language"
      And I press "Import"
    Then I should see "Preview"
      And I should see "Lewis" within "dd.fandom"
      And I should see "General Audiences" within "dd.rating"
      And I should see "Lewis/Hathaway" within "dd.relationship"
      And I should see "Published:2000-01-10"
      And I should see "Importing Test" within "h2.title"
      And I should not see "[FIC]" within "h2.title"
      And I should see "Something I made for testing purposes." within "div.summary"
      And I should see "Yes, this is really only for testing. :)" within "div.notes"
      And I should see "My first paragraph."
      And I should see "My second paragraph."
      And I should not see the image "alt" text "Add to memories!"
      And I should not see the image "alt" text "Next Entry"
      And I should not see "location"
      And I should not see "music"
      And I should not see "mood"
      And I should not see "Entry tags"
    When I press "Post"
    Then I should see "Work was successfully posted."
    When I am on cosomeone's user page
    Then I should see "Importing Test"

  @import_lj_tables
  Scenario: Creating a new work from an LJ story that has tables
  # This is to make sure that we don't accidentally strip other tables than
  # LJ metadata tables esp. when there's no LJ metadata table

    Given basic tags
      And a fandom exists with name: "Lewis", canonical: true
      And I am logged in as "cosomeone"
    When I go to the import page
      And I fill in "urls" with "http://rebecca2525.livejournal.com/3591.html"
      And I select "English" from "Choose a language"
      And I press "Import"
    Then I should see "Preview"
      And I should see "Lewis" within "dd.fandom"
      And I should see "General Audiences" within "dd.rating"
      And I should see "Lewis/Hathaway" within "dd.relationship"
      And I should see "Published:2000-01-10"
      And I should see "Importing Test" within "h2.title"
      And I should not see "[FIC]" within "h2.title"
      And I should see "Something I made for testing purposes." within "div.summary"
      And I should see "Yes, this is really only for testing. :)" within "div.notes"
      And I should see "My first paragraph."
      And I should see "My second paragraph."
      And I should not see the image "alt" text "Add to memories!"
      And I should not see the image "alt" text "Next Entry"
      And I should see "My location"
      And I should see "My music"
      And I should see "My mood"
      And I should see "My tags"
    When I press "Post"
    Then I should see "Work was successfully posted."
    When I am on cosomeone's user page
    Then I should see "Importing Test"

  @import_lj_no_backdate
  Scenario: Creating a new work from an LJ story without backdating it
    Given basic tags
      And I am logged in as a random user
    When I go to the import page
      And I fill in "urls" with "http://rebecca2525.livejournal.com/3562.html"
      And I select "English" from "Choose a language"
      And I press "Import"
    Then I should see "Preview"
      And I should see "Importing Test"
    When I press "Edit"
    Then I should see "* Required information"
      And I should see "Importing Test"
    When I set the publication date to today
      And I check "No Archive Warnings Apply"
      And I press "Preview"
    Then I should see "Importing Test"
    When I press "Post"
    Then I should see "Work was successfully posted."
      And I should see "Importing Test" within "h2.title"
      And I should not see the image "alt" text "Add to memories!"
      And I should not see the image "alt" text "Next Entry"

  @import_lj_comm
  Scenario: Creating a new work from an LJ story that is posted to a community
    Given basic tags
      And I am logged in as "cosomeone"
    When "AO3-4179" is fixed
    #When I go to the import page
    #  And I fill in "urls" with "http://community.livejournal.com/rarelitslash/271960.html"
    #  And I select "English" from "Choose a language"
    #  And I press "Import"
    #Then I should see "Preview"
    #  And I should see "Poirot - Agatha Christie" within "dd.fandom"
    #  And I should see "General Audiences" within "dd.rating"
    #  And I should see "Published:2010-10-23"
    #  And I should see "Mrs Stanwood's Birthday Party" within "h2.title"
    #  And I should not see "[Poirot]" within "h2.title"
    #  And I should see "Mrs Stanwood, famous medical researcher" within "div.summary"
    #  And I should see "more to their friendship than he'd thought." within "div.summary"
    #  And I should see "Thanks to Tevildo and phantomphan1990 for beta-reading!"
    #  And I should see the image "src" text "http://www.rbreu.de/fan/stanwood_title_400.png"
    #  And I should see "Follow me to AO3"
    #  And I should not see "rarelitslash"
    #  And I should not see "rebecca2525"
    #  And I should not see the image "alt" text "Add to memories!"
    #  And I should not see the image "alt" text "Next Entry"
    #  And I should not see "mood"
    #  And I should not see "Entry tags"
    #When I press "Post"
    #Then I should see "Work was successfully posted."
    #When I am on cosomeone's user page
    #Then I should see "Mrs Stanwood's Birthday Party"

  @import_lj_underscores
  Scenario: Importing from a journal with underscores in the name
    Given basic tags
      And I am logged in as "cosomeone"
    When I go to the import page
      And I fill in "urls" with "http://ao3_testing.livejournal.com/557.html"
      And I select "English" from "Choose a language"
      And I press "Import"
    Then I should see "Preview"

  @import_lj_multi_chapter
  Scenario: Creating a new multichapter work from an LJ story
    Given basic tags
      And I am logged in as "cosomeone"
      And I set my time zone to "UTC"
    When I go to the import page
      And I fill in "urls" with
        """
        http://rebecca2525.livejournal.com/3562.html
        http://rebecca2525.livejournal.com/4024.html
        """
      And I select "English" from "Choose a language"
      And I choose "import_multiple_chapters"
      And I press "Import"
    Then I should see "Preview"
      And I should see "Lewis" within "dd.fandom"
      And I should see "General Audiences" within "dd.rating"
      And I should see "Importing Test" within "h2.title"
      And I should not see "[FIC]" within "h2.title"
      And I should see "Something I made for testing purposes." within "div.summary"
      And I should see "Yes, this is really only for testing. :)" within "div.notes"
      And I should see "My first paragraph."
      And I should see "My second paragraph."
    When I press "Post"
    Then I should see "Work was successfully posted."
      And I should see "Chapters:2/2"
      And I should see "Published:2000-01-10"
      And I should see "Completed:2000-01-22"
      And I should see "My first paragraph."
      And I should see "My second paragraph."
    When I follow "Next Chapter"
    Then I should see "The long awaited second part."
      And I should see "And another paragraph."
      And I should see "The plot thickens." within "div.summary"
      And I should see "MOAR TESTING! :)" within "div.notes"
      And I should see "Importing Test Part 2" within "h3.title"
    When I am on cosomeone's user page
    Then I should see "Importing Test"
