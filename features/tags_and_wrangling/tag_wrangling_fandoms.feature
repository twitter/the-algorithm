@tags @users @tag_wrangling

Feature: Tag Wrangling - Fandoms

@javascript
Scenario: fandoms wrangling - syns, mergers, autocompletes, metatags

  Given the following activated tag wrangler exists
    | login  | password    |
    | Enigel | wrangulate! |
    And basic tags
    And a media exists with name: "TV Shows", canonical: true
    And a character exists with name: "Neal Caffrey", canonical: true
    And I am logged in as "Enigel" with password "wrangulate!"

  # create a new canonical fandom from tag wrangling interface
  When I follow "Tag Wrangling"
    And I follow "New Tag"
    And I fill in "Name" with "Stargate SG-1"
    And I choose "Fandom"
    And I check "Canonical"
    And I press "Create Tag"
  Then I should see "Tag was successfully created"
    And the "Canonical" checkbox should be checked
    And the "Canonical" checkbox should not be disabled

  # create a new non-canonical fandom from tag wrangling interface
  When I follow "New Tag"
    And I fill in "Name" with "SGA"
    And I choose "Fandom"
    And I press "Create Tag"
  Then I should see "Tag was successfully created"
    And the "Canonical" checkbox should not be checked
    And the "Canonical" checkbox should not be disabled

  # creating a new canonical fandom by synning
  When I fill in "Synonym of" with "Stargate Atlantis"
    And I press "Save changes"
  Then I should see "Tag was updated"
    And I should not see "Synonyms"
  When I follow "Edit Stargate Atlantis"
    And I should see "SGA"
  Then the "Canonical" checkbox should be checked and disabled

  # creating non-canonical fandoms from work posting
  When I go to the new work page
    And I fill in the basic work information for "Silliness"
    And I fill in "Fandoms" with "SG1, the whole Stargate franchise, Stargates SG-1"
    And I press "Post"
  Then I should see "Work was successfully posted."

  # editing non-canonical fandom in order to syn it to existing canonical merger
  When I follow "SG1"
    And I follow "Edit"
    And I enter "Stargate" in the "Synonym of" autocomplete field
  Then I should see "Stargate Atlantis" in the autocomplete
    And I should see "Stargate SG-1" in the autocomplete
  When I choose "Stargate SG-1" from the "Synonym of" autocomplete
    And I choose "TV Shows" from the "tag_media_string_autocomplete" autocomplete
    And I press "Save changes"
  Then I should see "Tag was updated"

  # adding a non-canonical synonym to a canonical, fandom should be copied
  When I follow "Edit Stargate SG-1"
  Then I should see "TV Shows"
    And I should see "SG1"
    And the "Canonical" checkbox should be disabled
  When I enter "Stargate" in the "tag_merger_string_autocomplete" autocomplete field
  Then I should see "Stargates SG-1" in the autocomplete
    And I should see "the whole Stargate franchise" in the autocomplete
    And I should not see "Stargate SG-1" in the autocomplete
  When I choose "Stargates SG-1" from the "tag_merger_string_autocomplete" autocomplete
    And I press "Save changes"
  Then I should see "Tag was updated"
    And I should see "Stargates SG-1"
  When I follow "Stargates SG-1"
  Then I should see "TV Shows"

  # metatags and subtags, transference thereof to a new canonical by an admin
  When I edit the tag "Stargate Atlantis"
    And I fill in "MetaTags" with "Stargate Franchise"
    And I press "Save changes"
  Then I should see "Invalid metatag 'Stargate Franchise':"
    And I should see "Metatag does not exist."
    And I should not see "Stargate Franchise" within "form"
  When I follow "New Tag"
    And I fill in "Name" with "Stargate Franchise"
    And I check "Canonical"
    And I choose "Fandom"
    And I press "Create Tag"
    And I choose "TV Shows" from the "Add:" autocomplete
    And I choose "Stargate Atlantis" from the "Add SubTags" autocomplete
    And I press "Save changes"
  Then I should see "Tag was updated"
    And I should see "TV Shows"
    And the "Canonical" checkbox should be checked
  When I follow "Stargate Atlantis"
  Then I should see "Stargate Franchise" within "div#parent_MetaTag_associations_to_remove_checkboxes"
  When I edit the tag "Stargate SG-1"
    And I choose "Stargate Franchise" from the "Add MetaTags" autocomplete
    And I press "Save changes"
  Then I should see "Tag was updated"
  When I follow "New Tag"
    And I fill in "Name" with "Stargate SG-1: Ark of Truth"
    And I check "Canonical"
    And I choose "Fandom"
    And I press "Create Tag"
    And I fill in "MetaTags" with "Stargate SG-1"
    And I press "Save changes"
    And I follow "Stargate SG-1"
  Then I should see "Stargate SG-1: Ark of Truth" within "div#child_SubTag_associations_to_remove_checkboxes"
    And I should see "Stargate Franchise" within "div#parent_MetaTag_associations_to_remove_checkboxes"
  When I am logged in as an admin
    And I edit the tag "Stargate SG-1"
    And I fill in "Synonym of" with "Stargate SG-1: Greatest Show in the Universe"
    And I press "Save changes"
  Then I should see "Tag was updated"
    And I should not see "Stargate SG-1: Ark of Truth"
    And I should not see "Stargates SG-1"
    And I should not see "SG1"
    And I should not see "Stargate Franchise"
  When I follow "Edit Stargate SG-1: Greatest Show in the Universe"
  Then I should see "Stargate SG-1: Ark of Truth"
    And I should see "Stargates SG-1"
    And I should see "SG1"
    And I should see "Stargate Franchise"

  # trying to syn a non-canonical to another non-canonical
  When I am logged in as "Enigel" with password "wrangulate!"
    And I edit the tag "Stargate SG-1: Greatest Show in the Universe"
    And I follow "New Tag"
    And I fill in "Name" with "White Collar"
    And I choose "Fandom"
    And I press "Create Tag"
    And I follow "New Tag"
    And I fill in "Name" with "WhiCo"
    And I choose "Fandom"
    And I press "Create Tag"
    And I fill in "Synonym of" with "White Collar"
    And I press "Save changes"
  Then I should see "White Collar is not a canonical tag. Please make it canonical before adding synonyms to it."

  # trying to syn a non-canonical to a canonical of a different category
  When I fill in "Synonym of" with "Neal Caffrey"
    And I press "Save changes"
  Then I should see "Neal Caffrey is a character. Synonyms must belong to the same category."

Scenario: Checking the media pages

  Given basic tags
    And a media exists with name: "TV Shows", canonical: true
    And a media exists with name: "Video Games", canonical: true
    And a media exists with name: "Books", canonical: true
    And a canonical fandom "Stargate"
    And a canonical fandom "Lord of the Rings"
    And a canonical fandom "Final Fantasy"
    And a canonical fandom "Yuletide RPF"
    And a canonical fandom "A weird thing"
    And a canonical fandom "Be another thing"
    And a canonical fandom "Be a second B fandom"
    And a canonical fandom "Can sort alphabetically"
    And the following activated tag wrangler exists
    | login  | password    |
    | Enigel | wrangulate! |
  When I am logged in as "Enigel" with password "wrangulate!"
    And I edit the tag "Stargate"
    And I fill in "tag_media_string" with "TV Shows"
    And I press "Save changes"
  Then I should see "Tag was updated"
  When I edit the tag "Lord of the Rings"
    And I fill in "tag_media_string" with "Books"
    And I press "Save changes"
  Then I should see "Tag was updated"
  When I edit the tag "Final Fantasy"
    And I fill in "tag_media_string" with "Video Games"
    And I press "Save changes"
  Then I should see "Tag was updated"

  Given I post the work "Test" with fandom "Yuletide RPF"
    And I post the work "Test Ring" with fandom "Lord of the Rings"
  When I go to the fandoms page
  Then I should see "Fandoms" within "h2"
    And I should see "Books" within "div#main .media"
    And I should see "TV Shows" within "div#main .media"
    And I should see "Video Games" within "div#main .media"
    And I should see "Uncategorized Fandoms" within "div#main .media"
    And I should see "Yuletide RPF" within "div#main .media"

  # Tags will show up if there's at least a work
  When I follow "Books"
  Then I should see "Fandoms > Books"
    And I should not see "No fandoms found"
    And I should see "Lord of the Rings"
    And I should not see "Stargate"
    And I should not see "Yuletide RPF"

  # Tags will not show up if there are no works
  When I go to the fandoms page
    And I follow "Video Games"
  Then I should see "No fandoms found"
    And I should not see "Final Fantasy"

  When I go to the media page
  Then I should see "Fandoms" within "h2"
  When I follow "Uncategorized Fandoms"
  Then I should see "B C N W Y" within ".alphabet"
    And I should see "A weird thing" within "#letter-W .tags"
    And I should see "Be a second B fandom" within "#letter-B .tags"
    And I should see "Be another thing" within "#letter-B .tags"
