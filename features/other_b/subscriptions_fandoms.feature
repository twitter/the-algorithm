Feature: Subscriptions
  In order to follow a fandom I like
  As a reader
  I want to subscribe to it

  Scenario: Subscribe to a test fandom when there are no works in it

  When I am logged in as "author"
    And I post a work "My Work Title" with category "F/M"
  When I am logged in as "reader"
    And I view the "F/F" works index
  Then I should see "RSS Feed"
  When I follow "RSS Feed"
  Then I should not see "My Work Title"
    And I should not see "Stargate SG-1"
    
  Scenario: Subscribe to a test fandom when there are works in it

  When I am logged in as "author"
    And I post a work "My Work Title" with category "F/F"
  When I am logged in as "reader"
    And I view the "F/F" works index
  Then I should see "RSS Feed"
  When I follow "RSS Feed"
  Then I should see "My Work Title"
    And I should see "Stargate SG-1"

  Scenario: Subscribe to a non-test fandom

  When I am logged in as "author"
    And I post a work "My Work Title" with category "Multi"
  When I am logged in as "reader"
    And I view the "Multi" works index
  Then I should not see "RSS Feed"

  Scenario: Mystery work is not shown in feed

  Given basic tags
    And I am logged in as "myname2"
  Given I have a hidden collection "Hidden Treasury" with name "hidden_treasury"
  When I am logged in as "myname1"
    And I post the work "Old Snippet"
    And I edit the work "Old Snippet"
    And I fill in "Post to Collections / Challenges" with "hidden_treasury"
    And I check "F/F"
    And I press "Post"
  Then I should see "This work is part of an ongoing challenge and will be revealed soon! You can find details here: Hidden Treasury"
  When I am logged in as "author"
    And I post a work "My Work Title" with category "F/F"
  When I view the "F/F" works index
  When I follow "RSS Feed"
  Then I should not see "Old Snippet"
    And I should not see "myname1"
    And I should see "author"

  Scenario: Author of anonymous work is not shown in feed

  Given basic tags
    And I am logged in as "myname2"
  Given I have an anonymous collection "Hidden Treasury" with name "hidden_treasury"
  When I am logged in as "myname1"
    And I post the work "Old Snippet"
    And I edit the work "Old Snippet"
    And I fill in "Post to Collections / Challenges" with "hidden_treasury"
    And I check "F/F"
    And I press "Post"
    And all indexing jobs have been run
  Then I should see "Anonymous"
    And I should see "Collections: Hidden Treasury"
  When I am logged in as "author"
    And I post a work "My Work Title" with category "F/F"
  When I view the "F/F" works index
  When I follow "RSS Feed"
  Then I should see "Old Snippet"
    And I should not see "myname1"
    And I should see "author"

  Scenario: A user can see a feed for non canonical tags

  Given I am logged in as "author"
    And I post the work "Glorious" with fandom "SGA"
  When I view the "SGA" works feed
  Then I should see "Glorious"
