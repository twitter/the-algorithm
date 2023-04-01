@bookmarks @collections @works

Feature: Collectible items in multiple collections
  As a user
  I want to be unable to add items to more than one collection

  Scenario: Add a work that is already in a moderated collection to a second moderated collection
    Given I have the moderated collection "ModeratedCollection"
      And I have the moderated collection "ModeratedCollection2"
      And I am logged in as a random user
      And I set my preferences to allow collection invitations
      And I post the work "Blabla" to the collection "ModeratedCollection"
    When I edit the work "Blabla" to be in the collections "ModeratedCollection,ModeratedCollection2"
    Then I should see "Work was successfully updated. You have submitted your work to moderated collections (ModeratedCollection, ModeratedCollection2). It will not become a part of those collections until it has been approved by a moderator."

  Scenario: Add my work to both moderated and unmoderated collections by editing
  the work
    Given I have the moderated collection "ModeratedCollection"
      And I have the collection "UnModeratedCollection"
      And I am logged in as a random user
      And I post the work "RandomWork" to the collection "ModeratedCollection"
    When I go to "ModeratedCollection" collection's page
    Then I should not see "RandomWork"
    When I edit the work "RandomWork"
      # Fill in both the existing and new collection names or else this will
      # remove it from the original collection by replacing the text in the
      # field
      And I fill in "Post to Collections / Challenges" with "ModeratedCollection, UnModeratedCollection"
      And I press "Post"
    Then I should see "Work was successfully updated. You have submitted your work to the moderated collection 'ModeratedCollection'. It will not become a part of the collection until it has been approved by a moderator."
      And I should see "UnModeratedCollection"
    When I go to "UnModeratedCollection" collection's page
    Then I should see "RandomWork"
    When I go to "ModeratedCollection" collection's page
    Then I should not see "RandomWork"

  Scenario: Collection mod can't add an anonymous work to their collection using
  the Add to Collections option on the work
    Given I have the anonymous collection "AnonymousCollection"
      And I have the collection "MyCollection"
      And I am logged in as a random user
      And I set my preferences to allow collection invitations
      And I post the work "Some Work" to the collection "AnonymousCollection"
    When I am logged in as the owner of "MyCollection"
      And I view the work "Some Work"
      And I fill in "Collection name(s):" with "MyCollection"
      And I press "Invite"
    Then I should see "We couldn't add your submission to the following collection(s):"
      And I should see "MyCollection, because you don't own this item and the item is anonymous."

  Scenario: Work creator can add their own anonymous work to another collection
    Given I have the anonymous collection "AnonymousCollection"
      And I have the collection "OtherCollection"
      And I am logged in as a random user
      And I set my preferences to allow collection invitations
      And I post the work "Some Work" to the collection "AnonymousCollection"
    When I edit the work "Some Work" to be in the collections "AnonymousCollection,OtherCollection"
    Then I should see "Work was successfully updated."
      And I should see "OtherCollection"
