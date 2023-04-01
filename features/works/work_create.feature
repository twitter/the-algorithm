@works @tags
Feature: Create Works
  In order to have an archive full of works
  As an author
  I want to create new works

  Scenario: You can't create a work unless you're logged in
  When I go to the new work page
  Then I should see "Please log in"

  Scenario: Creating a new minimally valid work
    Given basic tags
      And I am logged in as "newbie"
    When I go to the new work page
    Then I should see "Post New Work"
      And I select "Not Rated" from "Rating"
      And I check "No Archive Warnings Apply"
      And I select "English" from "Choose a language"
      And I fill in "Fandoms" with "Supernatural"
      And I fill in "Work Title" with "All Hell Breaks Loose 🤬💩"
      And I fill in "content" with "Bad things happen, etc. 🤬💩"
    When I press "Preview"
    Then I should see "Preview"
    When I press "Post"
    Then I should see "Work was successfully posted."
      And I should see "Bad things happen, etc. 🤬💩"
    When I go to the works page
    Then I should see "All Hell Breaks Loose 🤬💩"

  Scenario: Creating a new minimally valid work and posting without preview
    Given I am logged in as "newbie"
    When I set up the draft "All Hell Breaks Loose"
      And I fill in "content" with "Bad things happen, etc."
      And I press "Post"
    Then I should see "Work was successfully posted."
      And I should see "Bad things happen, etc."
    When I go to the works page
    Then I should see "All Hell Breaks Loose"

  Scenario: Creating a new minimally valid work when you have more than one pseud
    Given I am logged in as "newbie"
      And "newbie" creates the pseud "Pointless Pseud"
    When I set up the draft "All Hell Breaks Loose"
      And I unselect "newbie" from "Creator/Pseud(s)"
      And I select "Pointless Pseud" from "Creator/Pseud(s)"
      And I press "Post"
    Then I should see "Work was successfully posted."
    When I go to the works page
    Then I should see "All Hell Breaks Loose"
      And I should see "by Pointless Pseud"

  @javascript
  Scenario: Creating a new work with everything filled in, and we do mean everything
    Given basic tags
      And the following activated users exist
        | login          | email                 |
        | coauthor       | coauthor@example.org  |
        | cosomeone      | cosomeone@example.org |
        | giftee         | giftee@example.org    |
        | recipient      | recipient@example.org |
      And the user "coauthor" allows co-creators
      And the user "cosomeone" allows co-creators
      And the user "giftee" allows gifts
      And the user "recipient" allows gifts
      And I have a collection "Collection 1" with name "collection1"
      And I have a collection "Collection 2" with name "collection2"
      And I am logged in as "thorough"
      And "thorough" creates the pseud "Pseud2"
      And "thorough" creates the pseud "Pseud3"
      And all emails have been delivered
    When I go to the new work page
      And I select "Not Rated" from "Rating"
      And I check "No Archive Warnings Apply"
      And I select "English" from "Choose a language"
      And I check "F/M"
      And I fill in "Fandoms" with "Supernatural"
      And I fill in "Work Title" with "All Something Breaks Loose"
      And I fill in "content" with "Bad things happen, etc."
      And I check "at the beginning"
      And I fill in "Notes" with "This is my beginning note"
      And I fill in "End Notes" with "This is my endingnote"
      And I fill in "Summary" with "Have a short summary"
      And I fill in "Characters" with "Sam Winchester, Dean Winchester,"
      And I fill in "Relationships" with "Harry/Ginny"
      And I fill in "Additional Tags" with "An extra tag"
      And I fill in "Gift this work to" with "Someone else, recipient"
      And I check "This work is part of a series"
      And I fill in "Or create and use a new one:" with "My new series"
      And I select "Pseud2" from "Creator/Pseud(s)"
      And I select "Pseud3" from "Creator/Pseud(s)"
      And I fill in "pseud_byline_autocomplete" with "coauthor"
      And I fill in "Post to Collections / Challenges" with "collection1, collection2"
      And I press "Preview"
    Then I should see "Draft was successfully created"
    When I press "Post"
    Then I should see "Work was successfully posted."
      And 1 email should be delivered to "coauthor@example.org"
      And the email should contain "The user thorough has invited your pseud coauthor to be listed as a co-creator on the following work"
      And the email should not contain "translation missing"
      And 1 email should be delivered to "recipient@example.org"
      And the email should contain "A gift work has been posted for you"
    When I go to the works page
    Then I should see "All Something Breaks Loose"
    When I follow "All Something Breaks Loose"
    Then I should see "All Something Breaks Loose"
      And I should see "Fandom: Supernatural"
      And I should see "Rating: Not Rated"
      And I should see "No Archive Warnings Apply"
      And I should not see "Choose Not To Use Archive Warnings"
      And I should see "Category: F/M"
      And I should see "Characters: Sam Winchester Dean Winchester"
      And I should see "Relationship: Harry/Ginny"
      And I should see "Additional Tags: An extra tag"
      And I should see "For Someone else, recipient"
      And I should see "Collections: Collection 1, Collection 2"
      And I should see "Notes"
      And I should see "This is my beginning note"
      And I should see "See the end of the work for more notes"
      And I should see "This is my endingnote"
      And I should see "Summary"
      And I should see "Have a short summary"
      And I should see "My new series"
      And I should see "Bad things happen, etc."
      And I should see "Pseud2" within ".byline"
      And I should see "Pseud3" within ".byline"
      But I should not see "coauthor" within ".byline"
    When the user "coauthor" accepts all co-creator requests
      And I view the work "All Something Breaks Loose"
    Then I should see "coauthor" within ".byline"
    When I follow "Add Chapter"
      And I fill in "Chapter Title" with "This is my second chapter"
      And I fill in "content" with "Let's write another story"
      And I press "Preview"
    Then I should see "Chapter 2: This is my second chapter"
      And I should see "Let's write another story"
    When I press "Post"
    Then I should see "All Something Breaks Loose"
      And I should not see "Bad things happen, etc."
      And I should see "Let's write another story"
    When I follow "Previous Chapter"
      And I should see "Bad things happen, etc."
    When I follow "Entire Work"
    Then I should see "Bad things happen, etc."
      And I should see "Let's write another story"
    When I follow "Edit"
      And I check "Add co-creators?"
      And I fill in "pseud_byline_autocomplete" with "Does_not_exist"
      And I press "Preview"
    Then I should see "Invalid creator: Could not find a pseud Does_not_exist."
    When all emails have been delivered
      And I choose "cosomeone" from the "pseud_byline_autocomplete" autocomplete
      And I press "Preview"
      And I press "Update"
    Then I should see "Work was successfully updated"
      And I should see "coauthor" within ".byline"
      And I should see "Pseud2" within ".byline"
      And I should see "Pseud3" within ".byline"
      But I should not see "cosomeone" within ".byline"
      And 1 email should be delivered to "cosomeone@example.org"
    When the user "cosomeone" accepts all co-creator requests
      And I view the work "All Something Breaks Loose"
    Then I should see "cosomeone" within ".byline"
    When all emails have been delivered
      And I follow "Edit"
      And I remove selected values from the autocomplete field within "dd.recipient"
      And I give the work to "giftee"
      And I press "Preview"
      And I press "Update"
    Then I should see "Work was successfully updated"
      And I should see "For giftee"
      And 1 email should be delivered to "giftee@example.org"
    When I go to giftee's user page
    Then I should see "Gifts (1)"

  Scenario: Creating a new work with some maybe-invalid things
  # TODO: needs some more actually invalid things as well
    Given basic tags
      And the following activated users exist
        | login          | password    | email                   |
        | coauthor       | something   | coauthor@example.org |
        | badcoauthor    | something   | badcoauthor@example.org |
      And I am logged in as "thorough" with password "something"
      And user "badcoauthor" is banned
      And the user "coauthor" allows co-creators
    When I set up the draft "Bad Draft"
      And I fill in "Fandoms" with "Invalid12./"
      And I fill in "Work Title" with "/"
      And I fill in "content" with "T"
      And I check "chapters-options-show"
      And I fill in "work_wip_length" with "text"
      And I press "Preview"
    Then I should see "Content must be at least 10 characters long."
    When I fill in "content" with "Text and some longer text"
      And I fill in "work_collection_names" with "collection1, collection2"
      And I press "Preview"
    Then I should see "Sorry! We couldn't save this work because:"
      And I should see a collection not found message for "collection1"
    # Collections are now parsed by collectible.rb which only shows the first failing collection and nothing else
    # And I should see a collection not found message for "collection2"
    When I fill in "work_collection_names" with ""
      And I fill in "pseud_byline" with "badcoauthor"
      And I press "Preview"
    Then I should see "badcoauthor is currently banned"
    When I fill in "pseud_byline" with "coauthor"
      And I fill in "Additional Tags" with "this is a very long tag more than one hundred characters in length how would this normally even be created"
      And I press "Preview"
    Then I should see "try using less than 100 characters or using commas to separate your tags"
    When I fill in "Additional Tags" with "this is a shorter tag"
      And I press "Preview"
    Then I should see "Draft was successfully created"
      And I should see "Chapter"
      And I should see "1/?"

  Scenario: Creating a new work in a new series with some invalid things should return to the new work page with an error message and the newly created series selected
    Given basic tags
      And I am logged in as "thorough" with password "something"
    When I set up the draft "Bad Draft"
      And I fill in "Fandoms" with "Invalid12./"
      And I fill in "Work Title" with "/"
      And I fill in "content" with "T"
      And I check "This work has multiple chapters"
      And I fill in "Post to Collections / Challenges" with "collection1, collection2"
      And I check "This work is part of a series"
      And I fill in "Or create and use a new one:" with "My new series"
      And I press "Preview"
    Then I should see "Sorry! We couldn't save this work because:"
      And I should see a collection not found message for "collection1"
      And I should see "My new series" in the "Or create and use a new one:" input
      And I should not see "Remove Work From Series"

  Scenario: Creating a new work in an existing series with some invalid things should return to the new work page with an error message and series information still filled in
    Given basic tags
      And I am logged in as "thorough" with password "something"
      And I post the work "Work one" as part of a series "My existing series"
    When I set up the draft "Bad Draft"
      And I fill in "Fandoms" with "Invalid12./"
      And I fill in "Work Title" with "/"
      And I fill in "content" with "T"
      And I check "This work has multiple chapters"
      And I fill in "Post to Collections / Challenges" with "collection1, collection2"
      And I check "This work is part of a series"
      And I select "My existing series" from "Choose one of your existing series:"
      And I press "Preview"
    Then I should see "Sorry! We couldn't save this work because:"
      And I should see a collection not found message for "collection1"
      And "My existing series" should be selected within "Choose one of your existing series:"
      And I should not see "Remove Work From Series"

  Scenario: test for integer title and multiple fandoms
    Given I am logged in
    When I set up the draft "02138"
      And I fill in "Fandoms" with "Supernatural, Smallville"
    When I press "Post"
    Then I should see "Work was successfully posted."
      And I should see "Supernatural"
      And I should see "Smallville"
      And I should see "02138" within "h2.title"

  Scenario: test for < and > in title
    Given I am logged in
    When I set up the draft "4 > 3 and 2 < 5"
    When I press "Post"
    Then I should see "Work was successfully posted."
      And I should see "4 > 3 and 2 < 5" within "h2.title"

  Scenario: posting a chapter without preview
    Given I am logged in as "newbie" with password "password"
      And I post the work "All Hell Breaks Loose"
    When I follow "Add Chapter"
      And I fill in "Chapter Title" with "This is my second chapter"
      And I fill in "content" with "Let's write another story"
      And I press "Post"
    Then I should see "Chapter 2: This is my second chapter"
      And I should see "Chapter has been posted!"
      And I should not see "This is a preview"

  Scenario: RTE and HTML buttons are separate
  Given the default ratings exist
    And I am logged in as "newbie"
  When I go to the new work page
  Then I should see "Post New Work"
    And I should see "Rich Text" within ".rtf-html-switch"
    And I should see "HTML" within ".rtf-html-switch"

  Scenario: posting a backdated work
  Given I am logged in as "testuser" with password "testuser"
    And I post the work "This One Stays On Top"
    And I set up the draft "Backdated"
    And I check "backdate-options-show"
    And I select "1" from "work_chapter_attributes_published_at_3i"
    And I select "January" from "work_chapter_attributes_published_at_2i"
    And I select "1990" from "work_chapter_attributes_published_at_1i"
    And I press "Preview"
  When I press "Post"
  Then I should see "Published:1990-01-01"
  When I go to the works page
  Then "This One Stays On Top" should appear before "Backdated"

  Scenario: Users must set something as a warning and Author Chose Not To Use Archive Warnings should not be added automatically
    Given basic tags
      And I am logged in
    When I go to the new work page
      And I select "English" from "Choose a language"
      And I fill in "Fandoms" with "Dallas"
      And I fill in "Work Title" with "I Shot J.R.: Kristin's Story"
      And I fill in "content" with "It wasn't my fault, you know."
      And I press "Post"
    Then I should see "We couldn't save this work"
      And I should see "Please select at least one warning."
    When I check "No Archive Warnings Apply"
      And I press "Post"
    Then I should see "Work was successfully posted."
      And I should see "No Archive Warnings Apply"
      And I should not see "Author Chose Not To Use Archive Warnings"
      And I should see "It wasn't my fault, you know."

  Scenario: Users can co-create a work with a co-creator who has multiple pseuds
    Given basic tags
      And "myself" has the pseud "Me"
      And "herself" has the pseud "Me"
      And the user "myself" allows co-creators
      And the user "herself" allows co-creators
    When I am logged in as "testuser" with password "testuser"
      And I go to the new work page
      And I fill in the basic work information for "All Hell Breaks Loose"
      And I check "Add co-creators?"
      And I fill in "pseud_byline" with "Me"
      And I check "This work is part of a series"
      And I fill in "Or create and use a new one:" with "My new series"
      And I press "Post"
    Then I should see "There's more than one user with the pseud Me."
      And I select "myself" from "Please choose the one you want:"
      And I press "Preview"
    Then I should see "Draft was successfully created."
      And I press "Post"
    Then I should see "Work was successfully posted. It should appear in work listings within the next few minutes."
      And I should not see "Me (myself)"
      And I should see "My new series"
    When the user "myself" accepts all co-creator requests
      And I view the work "All Hell Breaks Loose"
    Then I should see "Me (myself), testuser"

  Scenario: Users can only create a work with a co-creator who allows it.
    Given basic tags
      And "Burnham" has the pseud "Michael"
      And "Pike" has the pseud "Christopher"
      And the user "Burnham" allows co-creators
    When I am logged in as "testuser" with password "testuser"
      And I go to the new work page
      And I fill in the basic work information for "Thats not my Spock"
      And I check "Add co-creators?"
      And I fill in "pseud_byline" with "Michael,Christopher"
      And I press "Post"
    Then I should see "Christopher (Pike) does not allow others to invite them to be a co-creator."
    When I fill in "pseud_byline" with "Michael"
      And I press "Preview"
    Then I should see "Draft was successfully created."
    When I press "Post"
    Then I should see "Work was successfully posted. It should appear in work listings within the next few minutes."
      But I should not see "Michael (Burnham)"
    When the user "Burnham" accepts all co-creator requests
      And I view the work "Thats not my Spock"
    Then I should see "Michael (Burnham), testuser"

  Scenario: Users can't set a publication date that is in the future, e.g. set
  the date to April 30 when it is April 26
    Given I am logged in
      And it is currently Wed Apr 26 22:00:00 UTC 2017
      And I set up a draft "Futuristic"
    When I check "Set a different publication date"
      And I select "30" from "work[chapter_attributes][published_at(3i)]"
      And I press "Post"
    Then I should see "Publication date can't be in the future."
    When I jump in our Delorean and return to the present

  Scenario: Inviting a co-author adds the co-author to all existing chapters when they accept the invite
    Given the user "foobar" exists and is activated
      And the user "barbaz" exists and is activated

    When I am logged in as "foobar"
      And I post the chaptered work "Chaptered Work"
      And I edit the work "Chaptered Work"
      And I invite the co-author "barbaz"
      And I press "Post"
    Then I should not see "barbaz"
      But 1 email should be delivered to "barbaz"
    When I am logged in as "barbaz"
      And I view the work "Chaptered Work"
    Then I should not see "Edit"
    # Delay to make sure that the cache expires when we accept the request:
    When it is currently 1 second from now
      And I follow "Co-Creator Requests page"
      And I check "selected[]"
      And I press "Accept"
    Then I should see "You are now listed as a co-creator on Chaptered Work."
    When I follow "Chaptered Work"
    Then I should see "Edit"
      And I should see "barbaz, foobar"
      And I should not see "Chapter by"
    When I follow "Next Chapter"
    Then I should see "barbaz, foobar"
      And I should not see "Chapter by"

  Scenario: You cannot create a work with too many tags
    Given the user-defined tag limit is 7
      And I am logged in as a random user
    When I set up the draft "Over the Limit"
      And I fill in "Fandoms" with "Fandom 1, Fandom 2"
      And I fill in "Characters" with "Character 1, Character 2"
      And I fill in "Relationships" with "Relationship 1, Relationship 2"
      And I fill in "Additional Tags" with "Additional Tag 1, Additional Tag 2"
      And I press "Post"
    Then I should see "Fandom, relationship, character, and additional tags must not add up to more than 7. Your work has 8 of these tags, so you must remove 1 of them."

  @javascript
  Scenario: "Please wait..." message disappears when validation errors are fixed
    Given basic tags
      And I am logged in as "test_user"
    When I go to the new work page
      And I fill in "Work Title" with "Unicorns Abound"
      And I select "English" from "Choose a language"
      And I fill in "Fandoms" with "Dallas"
      And I press "Post"
    Then I should see "Brevity is the soul of wit, but your content does have to be at least 10 characters long."
      And I should see a button with text "Please wait..."
    When I fill in "content" with "help there are unicorns everywhere"
    Then I should see a button with text "Post"
