Feature: Special co-creator behavior for archivists

  Background:
    Given I have an archivist "archivist"
      And I am logged in as "archivist"

  Scenario: Archivists can add users who allow co-creators to works
    Given the user "allow" exists and is activated
      And the user "allow" allows co-creators
    When I set up a draft "Imported"
      And I try to invite the co-author "allow"
      And I press "Post"
    Then I should see "allow, archivist" within ".byline"
      And 1 email should be delivered to "allow"
      And the email should contain "The user archivist has added your pseud allow as a co-creator on the following work:"
      And the email should not contain "translation missing"

  Scenario: Archivists can add users who don't allow co-creators to works
    Given the user "disallow" exists and is activated
      And the user "disallow" disallows co-creators
    When I set up a draft "Imported"
      And I try to invite the co-author "disallow"
      And I press "Post"
    Then I should see "archivist, disallow" within ".byline"
      And 1 email should be delivered to "disallow"
      And the email should contain "The user archivist has added your pseud disallow as a co-creator on the following work:"
      And the email should not contain "translation missing"

  Scenario: Archivists can add users who allow co-creators to chapters
    Given the user "allow" exists and is activated
      And the user "allow" allows co-creators
      And I post the work "Imported"
    When a chapter is set up for "Imported"
      And I try to invite the co-author "allow"
      And I press "Post"
    Then I should see "allow, archivist" within ".byline"
      And 1 email should be delivered to "allow"
      And the email should contain "The user archivist has added your pseud allow as a co-creator on the following chapter:"
      And the email should not contain "translation missing"

  Scenario: Archivists can add users who don't allow co-creators to chapters
    Given the user "disallow" exists and is activated
      And the user "disallow" disallows co-creators
      And I post the work "Imported"
    When a chapter is set up for "Imported"
      And I try to invite the co-author "disallow"
      And I press "Post"
    Then I should see "archivist, disallow" within ".byline"
      And 1 email should be delivered to "disallow"
      And the email should contain "The user archivist has added your pseud disallow as a co-creator on the following chapter:"
      And the email should not contain "translation missing"

  Scenario: Archivists can add users who allow co-creators to series
    Given the user "allow" exists and is activated
      And the user "allow" allows co-creators
      And I post the work "Imported" as part of a series "Imported Series"
    When I view the series "Imported Series"
      And I follow "Edit Series"
      And I try to invite the co-author "allow"
      And it is currently 1 second from now
      And I press "Update"
    Then "allow" should be a co-creator of the series "Imported Series"
      And 1 email should be delivered to "allow"
      And the email should contain "The user archivist has added your pseud allow as a co-creator on the following series:"
      And the email should not contain "translation missing"

  Scenario: Archivists can add users who don't allow co-creators to series
    Given the user "disallow" exists and is activated
      And the user "disallow" disallows co-creators
      And I post the work "Imported" as part of a series "Imported Series"
    When I view the series "Imported Series"
      And I follow "Edit Series"
      And I try to invite the co-author "disallow"
      And it is currently 1 second from now
      And I press "Update"
    Then "disallow" should be a co-creator of the series "Imported Series"
      And 1 email should be delivered to "disallow"
      And the email should contain "The user archivist has added your pseud disallow as a co-creator on the following series:"
      And the email should not contain "translation missing"
