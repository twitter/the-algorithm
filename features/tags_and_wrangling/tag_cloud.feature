@tags

Feature: Tag Cloud
  In order to browse by the most used additional tags
  As an archive user or visitor
  I should be able to view some tags in a tag cloud

Scenario: tag cloud should only contain top-level canonical freeforms in "No Fandom"
  I want to check that:
    non-canonical used freeforms do not show up in cloud, whether unwrangled, fandomish or no fandomish
    canonical freeforms within No Fandom but with no uses at all do not show
    canonical freeforms that are fandomish or unwrangled do not show up, even used
    canonical freeforms within No Fandom, with no uses but with used mergers show up TODO
    canonical freeforms within No Fandom, with uses, show up TODO
    metatag freeforms with uses show up and their subtags do not anymore TODO
    metatag freeforms with no uses do not show and neither do their subtags (which I think is bad) TODO

  Given the following activated tag wrangler exists
    | login  | password    |
    | Enigel | wrangulate! |
    And basic tags
    And a fandom exists with name: "Firefly", canonical: true
    And a freeform exists with name: "Non-canonical NoFandom", canonical: false
    And a freeform exists with name: "Non-canonical Fandomish", canonical: false
    And a freeform exists with name: "Non-canonical unwrangled", canonical: false
    And a freeform exists with name: "Canonical unused NoFandom", canonical: true
    And a freeform exists with name: "Canonical Fandomish", canonical: true
    And a freeform exists with name: "Canonical unwrangled", canonical: true

  # post a work with some fun tags and some boring tags

  When I am logged in as "author" with password "password"
    And I go to the new work page
    And I select "Not Rated" from "Rating"
    And I check "No Archive Warnings Apply"
    And I select "English" from "Choose a language"
    And I fill in "Fandoms" with "Firefly"
    And I fill in "Work Title" with "Silliness"
    And I fill in "Additional Tags" with "100 words, five things, objects in space, Sentient Serenity, Episode Tag, Non-canonical NoFandom, Non-canonical Fandomish, Non-canonical unwrangled, Canonical Fandomish, Canonical unwrangled"
    And I fill in "content" with "And then everyone was kidnapped by an alien bus."
    And I press "Preview"
    And I press "Post"
  Then I should see "Work was successfully posted."

  # test the tags with fun names

  When I am logged out
    And I follow "Tags" within "ul.navigation"
  Then I should not see "100 words"
    And I should not see "five things"

  When I am logged in as "Enigel" with password "wrangulate!"
    And I edit the tag "five things"
    And I fill in "Fandoms" with "No Fandom"
    And I press "Save changes"
    And I follow "New Tag"
    And I fill in "Name" with "5 Things"
    And I choose "Additional Tag"
    And I check "Canonical"
    And I press "Create Tag"
    And I follow "New Tag"
    And I fill in "Name" with "N Things"
    And I choose "Additional Tag"
    And I check "Canonical"
    And I press "Create Tag"
    And I follow "Tags"
  Then I should not see "Five Things"
    And I should not see "5 Things"
    And I should not see "N Things"
  When I post the work "Test"
    And I edit the work "Test"
    And I fill in "Additional Tags" with "Five Things"
    And I press "Preview"
    And I press "Update"
    And I follow "Tags"
  Then I should not see "Five Things"

  # set up boringly-named tags

  When I edit the tag "Non-canonical NoFandom"
    And I fill in "Fandoms" with "No Fandom"
    And I press "Save changes"
    And I edit the tag "Non-canonical Fandomish"
    And I fill in "Fandoms" with "Firefly"
    And I press "Save changes"
    And I edit the tag "Canonical unused NoFandom"
    And I fill in "Fandoms" with "No Fandom"
    And I press "Save changes"
    And I edit the tag "Canonical Fandomish"
    And I fill in "Fandoms" with "Firefly"
    And I press "Save changes"
  Then I should see "Tag was updated"

  # check the cloud for the boring tags

  When I follow "Tags"
  Then I should not see "Non-canonical NoFandom"
    And I should not see "Non-canonical Fandomish"
    And I should not see "Non-canonical unwrangled"
    And I should not see "Canonical unused NoFandom"
    And I should not see "Canonical Fandomish"
    And I should not see "Canonical unwrangled"

  When I follow "Random"
  Then I should not see "Non-canonical NoFandom"
    And I should not see "Non-canonical Fandomish"
    And I should not see "Non-canonical unwrangled"
    And I should not see "Canonical unused NoFandom"
    And I should not see "Canonical Fandomish"
    And I should not see "Canonical unwrangled"
