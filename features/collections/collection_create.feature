@collections
Feature: Collection
  In order to have an archive full of collections
  As a humble user
  I want to create a collection and post to it

Scenario: Create a collection

  Given I am logged in as "first_user"
  When I go to the collections page
  Then I should see "Collections in the "
    And I should not see "My Collection Thing"
  When I follow "New Collection"
    And I fill in "Display title" with "My Collection Thing"
    And I fill in "Collection name" with "collection_thing"
    And I fill in "Introduction" with "Welcome to the collection"
    And I fill in "FAQ" with "<dl><dt>What is this thing?</dt><dd>It's a collection</dd></dl>"
    And I fill in "Rules" with "Be nice to people"
    And I check all the collection settings checkboxes
    And I submit
  Then I should see "Collection was successfully created"
  When I follow "Profile"
  Then I should see "Welcome to the collection" within "#intro"
    And I should see "What is this thing?" within "#faq"
    And I should see "It's a collection" within "#faq"
    And I should see "Be nice to people" within "#rules"
  When I follow "Collection Settings"
    And I fill in "Collection name" with " "
    And I submit
    And I should see "Please enter a name for your collection"
  When I fill in "Collection name" with "collection_thing2"
    And I submit
    And I should see "Collection was successfully updated"
    And the name of the collection "My Collection Thing" should be "collection_thing2"

Scenario: Post to collection from the work edit page
  Given the collection "My Collection Thing" with name "collection_thing"
    And I am logged in as "first_user"
  When I post the work "collect-y work"
    And I go to first_user's user page
  Then I should see "collect-y work"
  When I edit the work "collect-y work"
    And I fill in "work_collection_names" with "collection_thing"
    And I press "Preview"
    And I press "Update"
  Then I should see "collect-y work"
    And I should see "Collections: My Collection Thing"

Scenario: Post to collection from the collection home page

  Given I have the collection "My Collection Thing" with name "collection_thing"
    And basic tags
    And I am logged in as "first_user"
  When I go to the collections page
    And I follow "My Collection Thing"
    And I follow "Post to Collection"
  Then I should see "Post New Work"
    And I should see "collection_thing" in the "Post to Collections / Challenges" input
  When I fill in the basic work information for "My Collected Work"
    And I press "Preview"
  Then I should see "My Collection Thing" within "dd.collections"
  When I press "Post"
  Then I should see "My Collected Work"
    And I should see "Collections: My Collection Thing"

Scenario: Create a subcollection

  Given I am logged in as "first_user"
    And I create the collection "My Collection Thing" with name "collection_thing"
  When I go to the collections page
    And I follow "New Collection"
    And I fill in "collection_parent_name" with "collection_thing"
    And I fill in "Display title" with "My SubCollection"
    And I fill in "Collection name" with "subcollection_thing"
    And I submit
  Then I should see "Collection was successfully created"

Scenario: Fill out new collection form with faulty data

   Given I am logged in as a random user
   And I am on the collections page

   When I follow "New Collection"
   And I fill in the following:
   | Collection name                 | faulty name         |
   | Display title                   | Awesome Collection  |
   | Collection email                | fangirl@example.org |
   | Brief description               | My Description      |
   | Introduction                    | My Introduction     |
   | FAQ                             | My FAQ              |
   | Rules                           | My Rules            |
   | Assignment notification message | My Message          |
   | Gift notification message       | My Other Message    |

   And I check "This collection is closed"
   And I select "Gift Exchange" from "Type of challenge, if any"
   And I submit

   Then I should see a save error message
   And I should see "faulty name" in the "Collection name" input
   And I should see "Awesome Collection" in the "Display title" input
   And I should see "fangirl@example.org" in the "Collection email" input
   And I should see "My Description" in the "Brief description" input
   And I should see "My Introduction" in the "Introduction" input
   And I should see "My FAQ" in the "FAQ" input
   And I should see "My Rules" in the "Rules" input
   And I should see "My Message" in the "Assignment notification message" input
   And I should see "My Other Message" in the "Gift notification message" input
   And the "This collection is closed" checkbox should not be disabled
   And "Gift Exchange" should be selected within "Type of challenge, if any"

Scenario: Create a collection with a malformed header URL

Given I have the collection "Scotts Collection" with name "scotts_collection"
  And I am logged in as "moderator"
  And I am on "Scotts Collection" collection's page
  And I follow "Collection Settings"
  And I fill in "collection_header_image_url" with "fc00.deviantart.net/fs13/f/2007/004/a/7/Flooded_by_bingeling.jpg"
  And I press "Update"
  And I should see "Collection was successfully updated"

  Scenario: Delete a subcollection and then its parent collection

  Given I am logged in as "collector"
    And I create the collection "Temporary Top" with name "temporary_top_collection"
  When I go to the collections page
    And I follow "New Collection"
    And I fill in "collection_parent_name" with "temporary_top_collection"
    And I fill in "Display title" with "Temporary Subcollection"
    And I fill in "Collection name" with "temporary_subcollection"
    And I press "Submit"
  Then I should see "Collection was successfully created"
  When I follow "Collection Settings"
    And I follow "Delete Collection"
    And I press "Yes, Delete Collection"
  Then I should see "Collection was successfully deleted."
    And I should see "Temporary Top"
  When I follow "Temporary Top"
    And I follow "Collection Settings"
  When I follow "Delete Collection"
    And I press "Yes, Delete Collection"
  Then I should see "Collection was successfully deleted."
    And I should not see "Temporary Top"

  Scenario: Delete a collection that has subcollections

  Given I am logged in as "collector"
    And I create the collection "Parent" with name "parent_collection"
  When I go to the collections page
    And I follow "New Collection"
    And I fill in "collection_parent_name" with "parent_collection"
    And I fill in "Display title" with "Child"
    And I fill in "Collection name" with "child_collection"
    And I press "Submit"
  Then I should see "Collection was successfully created"
  When I go to the collections page
    And I follow "Parent"
    And I follow "Collection Settings"
  When I follow "Delete Collection"
    And I press "Yes, Delete Collection"
  Then I should see "Collection was successfully deleted."
    And I should not see "Parent"

  Scenario: Moderator cannot list one collection's subcollection as another
  collection's parent

  Given I am logged in as "collector"
    And I add the subcollection "Subcollection" to the parent collection named "parent_collection"
  When I go to the new collection page 
    And I fill in "Parent collection (that you maintain)" with "subcollection"
    And I fill in "Display title" with "Sub-Subcollection"
    And I fill in "Collection name" with "sub_subcollection"
    And I press "Submit"
  Then I should see "Sorry, but Subcollection is a subcollection, so it can't also be a parent collection."

  Scenario: Moderator cannot specify a parent collection that does not exist

  Given I am logged in
  When I go to the new collection page 
    And I fill in "Parent collection (that you maintain)" with "nonexistent_collection"
    And I fill in "Display title" with "Collection"
    And I fill in "Collection name" with "Collection"
    And I press "Submit"
  Then I should see "We couldn't find a collection with name nonexistent_collection."

  Scenario: Moderator cannot make a collection its own parent

  Given I am logged in
    And I create the collection "Collection" with name "collection"
  When I go to "Collection" collection edit page
    And I fill in "Parent collection (that you maintain)" with "collection"
    And I press "Update"
  Then I should see "You can't make a collection its own parent."

  Scenario: Moderator cannot list a parent collection they do not own

  Given I am logged in as "collector"
    And I create the collection "Collection" with name "collection"
  When I am logged in as "other_collector"
    And I go to the new collection page
    And I fill in "Display title" with "Other Collection"
    And I fill in "Collection name" with "other_collection"
    And I fill in "Parent collection (that you maintain)" with "collection"
    And I press "Submit"
  Then I should see "You have to be a maintainer of collection to make a subcollection."

  Scenario: Collection display title can't contain commas

  Given I am logged in
    And I am on the new collection page
  When I fill in "Display title" with "Hey, You"
    And I fill in "Collection name" with "hey_you"
    And I press "Submit"
  Then I should see "Sorry, the ',' character cannot be in a collection Display Title."
