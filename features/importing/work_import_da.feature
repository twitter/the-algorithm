# TODO: Enable tests after AO3-5716.
@wip
@works @import
Feature: Import Works from deviantart
  In order to have an archive full of works
  As an author
  I want to create new works by importing them from deviantart

  @import_da_title_link
  Scenario: Creating a new art work from a deviantart title link with automatic metadata
    Given basic tags
      And I am logged in as "cosomeone"
    When I go to the import page
      And I fill in "urls" with "http://bingeling.deviantart.com/art/Flooded-45971613"
      And I select "English" from "Choose a language"
      And I press "Import"
    Then I should see "Preview"
      # And I should see the image "src" text "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/intermediary/f/917f216b-24af-41f8-9802-7ab80f56d2f2/drdbx9-adee7105-ed30-4e62-a66d-4f78dfa36879.jpg"
      And I should see the image "src" text "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/917f216b-24af-41f8-9802-7ab80f56d2f2/drdbx9-adee7105-ed30-4e62-a66d-4f78dfa36879.jpg?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7InBhdGgiOiJcL2ZcLzkxN2YyMTZiLTI0YWYtNDFmOC05ODAyLTdhYjgwZjU2ZDJmMlwvZHJkYng5LWFkZWU3MTA1LWVkMzAtNGU2Mi1hNjZkLTRmNzhkZmEzNjg3OS5qcGcifV1dLCJhdWQiOlsidXJuOnNlcnZpY2U6ZmlsZS5kb3dubG9hZCJdfQ.rcJ-cfuf5xgyl97ztUJVOYQ2PmHgc6P_FWCirRiUKFU"
      And I should see "Digital Art" within "dd.freeform"
      And I should see "People" within "dd.freeform"
      And I should see "Vector" within "dd.freeform"
      And I should see "Published:2007-01-04"
      # Importer picks up artist name as title instead of actual title
      And I should not see "Flooded" within "h2.title"
      And I should see "bingeling" within "h2.title"
      And I should see "done with Photoshop 7" within "div.notes"
      And I should see "but they were definitely helpful" within "div.notes"
      And I should not see "deviant"
      And I should not see "Visit the Artist"
      And I should not see "Download Image"
    When I press "Post"
    Then I should see "Work was successfully posted."
    When I am on cosomeone's user page
    Then I should see "bingeling"

  @import_da_gallery_link
  Scenario: Creating a new art work from a deviantart gallery link fails - it needs the direct link
    Given basic tags
      And I am logged in as "cosomeone"
    When I go to the import page
      And I fill in "urls" with "http://bingeling.deviantart.com/gallery/#/drdbx9"
      And I select "English" from "Choose a language"
      And I press "Import"
    Then I should not see "Preview"
      And I should see "Chapter 1 of"
      And I should see "is blank."

  @import_da_fic
  Scenario: Creating a new fic from deviantart import
    Given basic tags
      And I am logged in as "cosomeone"
    When I go to the import page
      And I fill in "urls" with "http://cesy12.deviantart.com/art/AO3-testing-text-196158032"
      And I select "English" from "Choose a language"
      And I press "Import"
    Then I should see "Preview"
      And I should see "Scraps"
      And I should see "Published:2011-02-04"
      # Importer picks up artist name as title instead of actual title
      And I should not see "AO3 testing text" within "h2.title"
      And I should see "cesy12" within "h2.title"
      And I should see "This is the description of the story above." within "div.notes"
      And I should see "This is a text, like a story or something."
      And I should see "Complete with some paragraphs."
      And I should not see "deviant"
      And I should not see "AO3 testing text" within "#chapters"
      And I should not see "Visit the Artist"
      And I should not see "Download File"
    When I press "Post"
    Then I should see "Work was successfully posted."
    When I am on cosomeone's user page
    Then I should see "cesy12"
