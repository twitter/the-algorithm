# encoding: utf-8
@tag_sets
Feature: Batch loading tags and associations for a tag set

  Scenario: Batch load character tags should successfully load characters that are canonical and return characters that are not
  Given a fandom exists with name: "MASH (TV)", canonical: true
    And a fandom exists with name: "Dallas (TV)", canonical: true
    And a character exists with name: "Hawkeye Pierce", canonical: true
    And a character exists with name: "Maxwell Klinger", canonical: true
    And a character exists with name: "Henry Blake", canonical: true
    And a character exists with name: "J. R. Ewing", canonical: true
    And a character exists with name: "Sue Ellen Ewing", canonical: true
  When I am logged in as "tagsetter"
    And I set up the tag set "Batch Loading Characters" with a visible tag list
    And I follow "Batch Load"
  When I fill in "Batch Load Tag Associations" with
    """
    MASH (TV),Hawkeye Pierce,Maxwell Klinger,Henry Blake
    Dallas (TV), J. R. Ewing, Sue Ellen Ewing, Pam Ewing
    """
    And I press "Submit"
  Then I should see "We couldn't add all the tags and associations you wanted -- the ones left below didn't work. See the help for suggestions!"
    And I should see "Dallas (TV),Pam Ewing"
    And I should not see "MASH (TV),Hawkeye Pierce,Maxwell Klinger,Henry Blake"
    And I should not see "Dallas (TV), J. R. Ewing, Sue Ellen Ewing, Pam Ewing"
  When I go to the "Batch Loading Characters" tag set page
  Then I should see "MASH (TV)"
    And I should see "Hawkeye Pierce"
    And I should see "Maxwell Klinger"
    And I should see "Henry Blake"
    And I should see "Dallas (TV)"
    And I should see "J. R. Ewing"
    And I should see "Sue Ellen Ewing"
    And I should not see "Pam Ewing"

  Scenario: Batch load relationship tags should successfully load relationships that are canonical and return characters that are not
  Given a fandom exists with name: "MASH (TV)", canonical: true
    And a fandom exists with name: "Dallas (TV)", canonical: true
    And a relationship exists with name: "BJ/Hawkeye", canonical: true
    And a relationship exists with name: "Hawkeye/Margaret Houlihan", canonical: true
    And a relationship exists with name: "Hawkeye & Radar", canonical: true
    And a relationship exists with name: "J. R. Ewing/Sue Ellen Ewing", canonical: true
    And a relationship exists with name: "Ann Ewing/Bobby Ewing", canonical: true
  When I am logged in as "tagsetter"
    And I set up the tag set "Batch Loading Relationships" with a visible tag list
    And I follow "Batch Load"
  When I fill in "Batch Load Tag Associations" with
    """
    MASH (TV), BJ/Hawkeye, Hawkeye/Margaret Houlihan, Hawkeye & Radar
    Dallas (TV),J. R. Ewing/Sue Ellen Ewing,Ann Ewing/Sue Ellen Ewing,Ann Ewing/Bobby Ewing
    """
    And I check "Relationships instead?"
    And I press "Submit"
  Then I should see "We couldn't add all the tags and associations you wanted -- the ones left below didn't work. See the help for suggestions!"
    And I should see "Dallas (TV),Ann Ewing/Sue Ellen Ewing"
    And I should not see "MASH (TV), BJ/Hawkeye, Hawkeye/Margaret Houlihan, Hawkeye & Radar"
    And I should not see "Dallas (TV),J. R. Ewing/Sue Ellen Ewing,Ann Ewing/Sue Ellen Ewing,Ann Ewing/Bobby Ewing"
  When I go to the "Batch Loading Relationships" tag set page
  Then I should see "MASH (TV)"
    And I should see "BJ/Hawkeye"
    And I should see "Hawkeye/Margaret Houlihan"
    And I should see "Hawkeye & Radar"
    And I should see "Dallas (TV)"
    And I should see "J. R. Ewing/Sue Ellen Ewing"
    And I should see "Ann Ewing/Bobby Ewing"
    And I should not see "Ann Ewing/Sue Ellen Ewing"

