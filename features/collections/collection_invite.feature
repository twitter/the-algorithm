@collection @works

Feature: Collection
  In order to have a collection full of curated works
  As a collection maintainer
  I want to add and invite works to my collection

  Scenario: Invite a work to a collection where a user approves inclusion
  Given I am logged in as "Scott" with password "password"
    And I set my preferences to allow collection invitations
    And I post the work "Murder in Milan" with fandom "Murder She Wrote"
  When I have the collection "scotts collection" with name "scotts_collection"
    And I am logged in as "moderator" with password "password"
    And I invite the work "Murder in Milan" to the collection "scotts collection"
  Then I should see "This work has been invited to your collection (scotts collection)."
    And 1 email should be delivered to "Scott"
  When I go to "scotts collection" collection's page
  Then I should see "Works (0)"
  When I follow "Manage Items"
    And I follow "Invited"
  Then I should see "Murder in Milan"
    And I should see "Works listed here have been invited to this collection. Once a work's creator has approved inclusion in this collection, the work will be moved to 'Approved'."
  When I am logged in as "Scott" with password "password"
    And I accept the invitation for my work in the collection "scotts collection"
    And I press "Submit"
  Then I should not see "Murder in Milan"
  When I follow "Approved"
  Then I should see "Murder in Milan"
  When I am logged in as "moderator"
    And I am on "scotts collection" collection's page
    And I follow "Manage Items"
  Then I should not see "Murder in Milan"
  When I follow "Approved"
  Then I should see "Murder in Milan"

  Scenario: Invite another's work to a anonymous collection should not be allowed.
  Given I am logged in
    And I set my preferences to allow collection invitations
    And I post the work "A Death in Hong Kong"
  When I have the hidden collection "anon collection" with name "anon_collection"
    And I am logged in as "moderator"
    And I invite the work "A Death in Hong Kong" to the collection "anon collection"
  Then I should see "because you don't own this item and the collection is anonymous or unrevealed"
    And 0 emails should be delivered
  When I view the approved collection items page for "anon collection"
  Then I should not see "A Death in Hong Kong"

  Scenario: Invite another's work to a hidden collection should not be allowed.
  Given I am logged in
    And I set my preferences to allow collection invitations
    And I post the work "A Death in Hong Kong"
  When I have the hidden collection "hidden collection" with name "hidden_collection"
    And I am logged in as "moderator"
    And I invite the work "A Death in Hong Kong" to the collection "hidden collection"
  Then I should see "because you don't own this item and the collection is anonymous or unrevealed"
    And 0 emails should be delivered
  When I view the approved collection items page for "hidden collection"
  Then I should not see "A Death in Hong Kong"

  Scenario: Invite another's work to a hidden anonymous collection should not be allowed.
  Given I am logged in
    And I set my preferences to allow collection invitations
    And I post the work "A Death in Hong Kong"
  When I have the hidden anonymous collection "anon hidden collection" with name "anon_hidden_collection"
    And I am logged in as "moderator"
    And I invite the work "A Death in Hong Kong" to the collection "anon hidden collection"
  Then I should see "because you don't own this item and the collection is anonymous or unrevealed"
    And 0 emails should be delivered
  When I view the approved collection items page for "anon hidden collection"
  Then I should not see "A Death in Hong Kong"

  Scenario: A work with too many tags can be invited to a collection, and the user can accept the invitation
    Given the user-defined tag limit is 2
      And the collection "Favorites"
      And the work "Over the Limit"
      And the work "Over the Limit" has 3 fandom tags
      And I am logged in as the author of "Over the Limit"
      And I set my preferences to allow collection invitations
    When I am logged in as "moderator"
      And I invite the work "Over the Limit" to the collection "Favorites"
    Then I should see "This work has been invited to your collection (Favorites)."
    When I am logged in as the author of "Over the Limit"
      And I accept the invitation for my work in the collection "Favorites"
      And I submit
    Then I should see "Collection status updated!"
      And I should not see "Over the Limit"
    When I view the work "Over the Limit"
    Then I should see "Favorites"
