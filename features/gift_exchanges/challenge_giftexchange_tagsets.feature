@collections @tag_sets
Feature: Gift Exchange Challenge with Tag Sets
  In order to have more fics for my fandom
  As a humble user
  I want to run a gift exchange with tag sets so I can make it single-fandom

  Scenario: Tagsets show up in Challenge metadata
    Given I am logged in as "mod1"
      And I have created the gift exchange "Cabbot Cove Remixes"
      And I go to the tagsets page
      And I follow the add new tagset link
      And I fill in "Title" with "Angela Lansbury"
      And I submit
      And I go to "Cabbot Cove Remixes" collection's page
      And I follow "Profile"
      And I should see "Tag Set:"
      And I should see "Standard Challenge Tags"
    When I edit settings for "Cabbot Cove Remixes" challenge
      And I fill in "Tag Sets To Use:" with "Angela Lansbury"
      And I press "Update"
    Then I should see "Tag Sets:"
      And I should see "Standard Challenge Tags"
      And I should see "Angela Lansbury"
    When I edit settings for "Cabbot Cove Remixes" challenge
      And I check "Standard Challenge Tags"
      And I check "Angela Lansbury"
      And I press "Update"
    Then I should not see "Tag Sets:"
      And I should not see "Tag Set:"
      And I should not see "Standard Challenge Tags"
      And I should not see "Angela Lansbury"

  @javascript
  Scenario: Run a single-fandom exchange

  Given basic tags
    And I am logged in as "mod1"
    And I have a canonical "Celebrities & Real People" fandom tag named "Hockey RPF"
    And I have a canonical "Celebrities & Real People" fandom tag named "Bandom"
    And a canonical character "Alexander Ovechkin" in fandom "Hockey RPF"
    And a canonical character "Gerard Way" in fandom "Bandom"
    And I set up the tag set "HockeyExchangeTags" with the fandom tags "Hockey RPF"
  When I go to the "HockeyExchangeTags" tag set page
  Then I should see "About HockeyExchangeTags"
    And I should see "Celebrities & Real People" within ".index"
  When I follow "↓" within ".index"
  Then I should see "Hockey RPF"
  When I have set up the gift exchange "My Hockey Exchange"
    And I fill in single-fandom gift exchange challenge options
    And I fill in "Tag Sets To Use:" with "HockeyExchangeTags"
    And I submit
  Then I should see "Challenge was successfully created"
  When I follow "Challenge Settings"
  Then I should see "HockeyExchangeTags"
  When I follow "Sign-up Form"
    And I check the 1st checkbox with the value "Hockey RPF"
    And I check the 2nd checkbox with the value "Hockey RPF"
    And I enter "Gerard Way" in the "Characters" autocomplete field
  Then I should see "No suggestions found" in the autocomplete
  # "Gerard Way" from a different fandom cannot appear in autocomplete; let's force it anyway.
  When I fill in the 1st field with id matching "character_tagnames" with "Gerard Way"
    And I submit
  Then I should see "Sorry! We couldn't save this challenge signup because"
    And I should see "These character tags in your request are not in the selected fandom(s), Hockey RPF: Gerard Way (Your moderator may be able to fix this.)"
    And I should not see "Sign-up was successfully created."
  When I follow "remove Gerard Way"
    And I choose "Alexander Ovechkin" from the "Characters" autocomplete
    And I submit
  Then I should see "Sign-up was successfully created."
