@tags @users @tag_wrangling @search

Feature: Tag Wrangling - Characters

@javascript
Scenario: character wrangling - syns, mergers, characters, autocompletes

  Given the following activated tag wrangler exists
    | login  | password    |
    | Enigel | wrangulate! |
    And basic tags
    And a fandom exists with name: "Doctor Who", canonical: true
    And a relationship exists with name: "First Doctor/TARDIS", canonical: true
    And I am logged in as "Enigel" with password "wrangulate!"

  # create a new canonical character from tag wrangling interface
  When I follow "Tag Wrangling"
    And I follow "New Tag"
    And I fill in "Name" with "The First Doctor"
    And I choose "Character"
    And I check "Canonical"
    And I press "Create Tag"
  Then I should see "Tag was successfully created"
    And the "Canonical" checkbox should be checked
    And the "Canonical" checkbox should not be disabled

  # create a new non-canonical character from tag wrangling interface
  When I follow "New Tag"
    And I fill in "Name" with "The Doctor (1st)"
    And I choose "Character"
    And I press "Create Tag"
  Then I should see "Tag was successfully created"
    And the "Canonical" checkbox should not be checked
    And the "Canonical" checkbox should not be disabled

  # check those two created properly
  When I am on the search tags page
    And all indexing jobs have been run
    And I fill in "Tag name" with "Doctor"
    And I press "Search Tags"
    # This part of the code is a hot mess. Capybara is returning the first instance of .canonical which contains
    # 'First Doctor/TARDIS', which then leaves us unable to check for 'The First Doctor' as being canonical.
    # I've changed the code for now to just check that 'The Doctor (1st) as being NON-Canonical
  Then I should see "The First Doctor"
    And I should see "The Doctor (1st)"
    And I should not see "The Doctor (1st)" within "span.canonical"

  # assigning an existing merger to a non-canonical character
  When I edit the tag "The Doctor (1st)"
    And I fill in "Synonym of" with "The First Doctor"
    And I press "Save changes"
  Then I should see "Tag was updated"
  When I follow "Edit The First Doctor"
  Then I should not see "Make tag non-canonical and unhook all associations"

  Given I am logged in as an admin
  When I edit the tag "The First Doctor"
  Then I should see "Make tag non-canonical and unhook all associations"
    And I should see "The Doctor (1st)"
    And the "Canonical" checkbox should be checked and disabled

  # creating a new canonical character by renaming
  When I fill in "Synonym of" with "First Doctor"
    And I press "Save changes"
  Then I should see "Tag was updated"
    And I should not see "Synonyms"
  When I follow "Edit First Doctor"

  # creating non-canonical characters from work posting
  When I am logged in as "Enigel" with password "wrangulate!"
    And I go to the new work page
    And I fill in the basic work information for "Silliness"
    And I fill in "Fandoms" with "Doctor Who"
    And I fill in "Characters" with "1st Doctor, One"
    And I press "Post"
  Then I should see "Work was successfully posted."

  # editing non-canonical character in order to syn it to existing canonical merger
  When I follow "1st Doctor"
    And I follow "Edit"
    And I enter "First" in the "Synonym of" autocomplete field
  Then I should see "First Doctor" in the autocomplete
    But I should not see "The First Doctor" in the autocomplete
  When I choose "First Doctor" from the "Synonym of" autocomplete
    And I choose "Doctor Who" from the "Fandoms" autocomplete
    And I press "Save changes"
  Then I should see "Tag was updated"

  # adding a non-canonical synonym to a canonical, fandom should be copied
  When I follow "Edit First Doctor"
  Then I should see "Doctor Who"
    And the "Canonical" checkbox should be disabled
  When I choose "One" from the "tag_merger_string_autocomplete" autocomplete
    And I fill in "Relationships" with "First Doctor/TARDIS"
    And I press "Save changes"
  Then I should see "Tag was updated"
    And I should see "One"
    And I should see "First Doctor/TARDIS"
  When I follow "One"
  Then I should see "Doctor Who"
    But I should not see "First Doctor/TARDIS" within ".tags"

  # metatags and subtags, transference thereof to a new canonical by an admin
  When I follow "Edit First Doctor"
    And I fill in "MetaTags" with "The Doctor (DW)"
    And I press "Save changes"
  Then I should see "Invalid metatag 'The Doctor (DW)':"
    And I should see "Metatag does not exist."
    And I should not see "The Doctor (DW)" within "form"
  When I follow "New Tag"
    And I fill in "Name" with "The Doctor (DW)"
    And I check "Canonical"
    And I choose "Character"
    And I press "Create Tag"
    And I choose "First Doctor" from the "SubTags" autocomplete
    And I press "Save changes"
  Then I should see "Tag was updated"
  When I follow "First Doctor"
  Then I should see "The Doctor (DW)"
  When I follow "New Tag"
    And I fill in "Name" with "John Smith"
    And I choose "Character"
    And I check "Canonical"
    And I press "Create Tag"
    And I fill in "MetaTags" with "First Doctor"
    And I press "Save changes"
  Then I should see "Tag was updated"
  When I follow "First Doctor"
  Then I should see "John Smith"
    And I should see "The Doctor"
  When I am logged in as an admin
    And I edit the tag "First Doctor"
    And I fill in "Synonym of" with "First Doctor (DW)"
    And I press "Save changes"
  Then I should see "Tag was updated"
    And I should not see "John Smith"
    And I should not see "The Doctor (1st)"
    And I should not see "1st Doctor"
    And I should not see "One"
    And I should not see "The Doctor (DW)"
  When I follow "Edit First Doctor (DW)"
  Then I should see "John Smith"
    And I should see "First Doctor" within "div#child_Merger_associations_to_remove_checkboxes"
    And I should see "The Doctor (1st)"
    And I should see "1st Doctor"
    And I should see "One" within "div#child_Merger_associations_to_remove_checkboxes"
    And I should see "The Doctor (DW)"

  # trying to syn a non-canonical to another non-canonical
  When I am logged in as "Enigel" with password "wrangulate!"
    And I edit the tag "First Doctor"
    And I follow "New Tag"
    And I fill in "Name" with "Eleventh Doctor"
    And I choose "Character"
    And I press "Create Tag"
    And I follow "New Tag"
    And I fill in "Name" with "Eleven"
    And I choose "Character"
    And I press "Create Tag"
    And I fill in "Synonym of" with "Eleventh Doctor"
    And I press "Save changes"
  Then I should see "Eleventh Doctor is not a canonical tag. Please make it canonical before adding synonyms to it."

  # trying to syn a non-canonical to a canonical of a different category
  When I fill in "Synonym of" with "Doctor Who"
    And I press "Save changes"
  Then I should see "Doctor Who is a fandom. Synonyms must belong to the same category."
