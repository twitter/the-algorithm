@collections
Feature: Gift Exchange Challenge
  In order to have more fics for my fandom
  As a humble user
  I want to run a gift exchange

  Scenario: Create a collection to house a gift exchange
    Given I am logged in as "mod1"
      And I have standard challenge tags setup
    When I set up the collection "My Gift Exchange"
      And I select "Gift Exchange" from "challenge_type"
      And I submit
    Then "My Gift Exchange" gift exchange should be correctly created

  Scenario: Enter settings for a gift exchange
    Given I am logged in as "mod1"
      And I have set up the gift exchange "My Gift Exchange"
    When I fill in gift exchange challenge options
      And I submit
    Then "My Gift Exchange" gift exchange should be fully created

  Scenario: Open signup in a gift exchange
    Given I am logged in as "mod1"
      And I have created the gift exchange "My Gift Exchange"
      And I am on "My Gift Exchange" gift exchange edit page
    When I check "Sign-up open?"
      And I submit
    Then I should see "Challenge was successfully updated"
      And I should see "Sign-up: Open" within ".collection .meta"
      And I should see "Sign-up Closes:"

  Scenario: Gift exchange appears in list of open challenges
    Given I am logged in as "mod1"
      And I have created the gift exchange "My Gift Exchange"
      And I am on "My Gift Exchange" gift exchange edit page
    When I check "Sign-up open?"
      And I submit
    When I view open challenges
    Then I should see "My Gift Exchange"

  Scenario: Gift exchange also appears in list of open gift exchange challenges
    Given I am logged in as "mod1"
      And I have created the gift exchange "My Gift Exchange"
      And I am on "My Gift Exchange" gift exchange edit page
    When I check "Sign-up open?"
      And I submit
    When I view open challenges
      And I follow "Gift Exchange Challenges"
    Then I should see "My Gift Exchange"

  Scenario: Change timezone for a gift exchange
    Given time is frozen at 1/1/2019
      And the gift exchange "My Gift Exchange" is ready for signups
    When I go to "My Gift Exchange" gift exchange edit page
      And I select "(GMT-08:00) Pacific Time (US & Canada)" from "Time zone"
      And I submit
    Then I should see "Challenge was successfully updated"
      And I should see the correct time zone for "Pacific Time (US & Canada)"
      And I jump in our Delorean and return to the present

  Scenario: Add a co-mod
    Given the following activated users exist
      | login   |
      | comod   |
      And I am logged in as "mod1"
      And I have created the gift exchange "Awesome Gift Exchange"
      And I open signups for "Awesome Gift Exchange"
    When I go to "Awesome Gift Exchange" collection's page
      And I follow "Membership"
      And I fill in "participants_to_invite" with "comod"
      And I press "Submit"
    Then I should see "New members invited: comod"

  Scenario: Sign up for a gift exchange
    Given the gift exchange "Awesome Gift Exchange" is ready for signups
      And I am logged in as "myname1"
    When I sign up for "Awesome Gift Exchange" with combination A
    Then I should see "Sign-up was successfully created"
    # Invalid signup should warn the user
    When I create an invalid signup in the gift exchange "Awesome Gift Exchange"
      And I reload the page
    Then I should see "sign-up is invalid"

  Scenario: I cannot sign up with a pseud that I don't own
    Given the gift exchange "Awesome Gift Exchange" is ready for signups
    When I attempt to sign up for "Awesome Gift Exchange" with a pseud that is not mine
    Then I should not see "Sign-up was successfully created"
      And I should see "You can't sign up with that pseud"

  Scenario: I cannot edit in a pseud that I don't own
    Given the gift exchange "Awesome Gift Exchange" is ready for signups
    When I attempt to update my signup for "Awesome Gift Exchange" with a pseud that is not mine
    Then I should not see "Sign-up was successfully updated"
      And I should see "You can't sign up with that pseud"

  Scenario: Optional tags should be saved when editing a signup (gcode issue #2729)
    Given the gift exchange "Awesome Gift Exchange" is ready for signups
      And I edit settings for "Awesome Gift Exchange" challenge
      And I check "Optional Tags?"
      And I submit
    When I am logged in as "myname1"
      And I sign up for "Awesome Gift Exchange" with combination A
      And I follow "Edit Sign-up"
      And I fill in "Optional Tags:" with "My extra tag, Something else weird"
      And I submit
    Then I should see "Something else weird"
    When I follow "Edit Sign-up"
      And I submit
    Then I should see "Something else weird"

  Scenario: Sign-ups can be seen in the dashboard
    Given the gift exchange "Awesome Gift Exchange" is ready for signups
    When I am logged in as "myname1"
      And I sign up for "Awesome Gift Exchange" with combination A
    When I am on my user page
    Then I should see "Sign-ups (1)"
    When I follow "Sign-ups (1)"
    Then I should see "Awesome Gift Exchange"

  Scenario: Ordinary users cannot see other signups
    Given the gift exchange "Awesome Gift Exchange" is ready for signups
      And I am logged in as "myname1"
    When I sign up for "Awesome Gift Exchange" with combination A
      And I go to the collections page
      And I follow "Awesome Gift Exchange"
    Then I should not see "Sign-ups" within "#dashboard"

  Scenario: Mod can view signups
   Given the gift exchange "Awesome Gift Exchange" is ready for signups
     And everyone has signed up for the gift exchange "Awesome Gift Exchange"
   When I am logged in as "mod1"
     And I go to "Awesome Gift Exchange" collection's page
     And I follow "Sign-ups"
   Then I should see "myname4" within "#main"
     And I should see "myname3" within "#main"
     And I should see "myname2" within "#main"
     And I should see "myname1" within "#main"
     And I should see "Something else weird"
     And I should see "Alternate Universe - Historical"

   Scenario: Mod can search signups by pseud
   Given the gift exchange "Awesome Gift Exchange" is ready for signups
     And everyone has signed up for the gift exchange "Awesome Gift Exchange"
   When I am logged in as "mod1"
     And I go to "Awesome Gift Exchange" collection's page
     And I follow "Sign-ups"
     And I fill in "query" with "3"
     And I press "Search By Pseud"
   Then I should see "myname3" within "#main"
     And I should not see "myname4" within "#main"

  Scenario: Cannot generate matches while signup is open
    Given the gift exchange "Awesome Gift Exchange" is ready for signups
      And everyone has signed up for the gift exchange "Awesome Gift Exchange"
    When I am logged in as "mod1"
      And I go to "Awesome Gift Exchange" collection's page
      And I follow "Matching"
    Then I should see "You can't generate matches while sign-up is still open."
      And I should not see "Generate Potential Matches"

  Scenario: Matches can be generated
    Given the gift exchange "Awesome Gift Exchange" is ready for matching
      And I close signups for "Awesome Gift Exchange"
    When I follow "Matching"
      And I follow "Generate Potential Matches"
    Then I should see "Beginning generation of potential matches. This may take some time, especially if your challenge is large."
    Given the system processes jobs
      And I wait 3 seconds
    When I reload the page
    Then I should see "Reviewing Assignments"
      And I should see "Complete"

  Scenario: Invalid signups are caught before generation
    Given the gift exchange "Awesome Gift Exchange" is ready for matching
      And I create an invalid signup in the gift exchange "Awesome Gift Exchange"
    When I close signups for "Awesome Gift Exchange"
      And I follow "Matching"
      And I follow "Generate Potential Matches"
      And the system processes jobs
      And I wait 3 seconds
    Then 1 email should be delivered to "mod1"
      And the email should contain "invalid sign-up"
    When I go to "Awesome Gift Exchange" gift exchange matching page
    Then I should see "Generate Potential Matches"
      And I should see "invalid sign-ups"

  Scenario: Assignments can be updated and cannot be sent out until everyone is assigned
    Given the gift exchange "Awesome Gift Exchange" is ready for matching
      And I have generated matches for "Awesome Gift Exchange"
    When I remove a recipient
      And I press "Save Assignment Changes"
    Then I should see "Assignments updated"
      And I should see "No Recipient"
      And I should see "No Giver"
    When I follow "Send Assignments"
    Then I should see "aren't assigned"
    When I follow "No Giver"
      And I assign a pinch hitter
      And I press "Save Assignment Changes"
    Then I should see "Assignments updated"
      And I should not see "No Giver"
    When I follow "No Recipient"
      And I assign a pinch recipient
      And I press "Save Assignment Changes"
      And I should not see "No Recipient"
    When I follow "Send Assignments"
    Then I should see "Assignments are now being sent out"

  Scenario: Issues with assignments
    Given the gift exchange "Awesome Gift Exchange" is ready for matching
      And I have generated matches for "Awesome Gift Exchange"
    When I assign a recipient to herself
      And I press "Save Assignment Changes"
    Then I should not see "Assignments updated"
      And I should see "do not match"
    When I manually destroy the assignments for "Awesome Gift Exchange"
      And I go to "Awesome Gift Exchange" gift exchange matching page
    Then I should see "Regenerate Assignments"
      And I should see "Regenerate All Potential Matches"
      And I should see "try regenerating assignments"
    When I follow "Regenerate Assignments"
      And the system processes jobs
      And I wait 3 seconds
      And I reload the page
    Then I should see "Reviewing Assignments"
      And I should see "Complete"

  Scenario: Matches can be regenerated for a single signup
    Given the gift exchange "Awesome Gift Exchange" is ready for matching
      And I am logged in as "Mismatch"
      And I sign up for "Awesome Gift Exchange" with a mismatched combination
    When I am logged in as "mod1"
      And I have generated matches for "Awesome Gift Exchange"
    Then I should see "No Potential Givers"
      And I should see "No Potential Recipients"
    When I follow "No Potential Givers"
      Then I should see "Regenerate Matches For Mismatch"
    When I follow "Edit"
      And I check the 1st checkbox with the value "Stargate Atlantis"
      And I uncheck the 1st checkbox with the value "Bad Choice"
      And I check the 2nd checkbox with the value "Stargate Atlantis"
      And I uncheck the 2nd checkbox with the value "Bad Choice"
      And I submit
      And I follow "Matching"
      And I follow "No Potential Recipients"
      And I follow "Regenerate Matches For Mismatch"
    Then I should see "Matches are being regenerated for Mismatch"
    When the system processes jobs
      And I wait 3 seconds
      And I reload the page
    Then I should not see "No Potential Givers"
      And I should not see "No Potential Recipients"
    When I follow "Regenerate Assignments"
      And the system processes jobs
      And I wait 3 seconds
      And I reload the page
    Then I should not see "No Potential Givers"
      And I should not see "No Potential Recipients"
      And I should see "Complete"

  Scenario: Assignments can be sent
    Given the gift exchange "Awesome Gift Exchange" is ready for matching
      And I have generated matches for "Awesome Gift Exchange"
    When I follow "Send Assignments"
    Then I should see "Assignments are now being sent out"
    Given the system processes jobs
      And I wait 3 seconds
    When I reload the page
    Then I should not see "Assignments are now being sent out"
    # 4 users and the mod should get emails :)
      And 1 email should be delivered to "mod1"
      And the email should have "Assignments sent" in the subject
      And the email should contain "You have received a message about your collection"
      And the email should not contain "translation missing"
      And 1 email should be delivered to "myname1"
      And the email should contain "You have been assigned the following request"
      And the email should contain "Fandom:"
      And the email should contain "Stargate SG-1"
      # Ratings and warnings don't show unless they've been selected to be something other than the default
      And the email should not contain "Rating:"
      And the email should not contain "Choose Not To Use Archive Warnings"
      And the email should contain "Additional Tag"
      And the email should contain "Something else weird"
      And the email should not contain "translation missing"
      And 1 email should be delivered to "myname2"
      And 1 email should be delivered to "myname3"
      And 1 email should be delivered to "myname4"
      And the email should link to "Awesome Gift Exchange" collection's url
      And the email should link to myname1's user url
      And the email html body should link to the works tagged "Stargate Atlantis"

  Scenario: User signs up for two gift exchanges at once and can use the Fulfill
  link to fulfill one assignment at a time
    Given everyone has their assignments for "Awesome Gift Exchange"
      And everyone has their assignments for "Second Challenge"
    When I am logged in as "myname1"
      And I start to fulfill my assignment
    Then the "Awesome Gift Exchange (myname3)" checkbox should be checked
      And the "Second Challenge (myname3)" checkbox should not be checked

  Scenario: User has more than one pseud on signup form
    Given "myname1" has the pseud "othername"
    Given I am logged in as "mod1"
      And I have created the gift exchange "Sensitive Gift Exchange"
      And I open signups for "Sensitive Gift Exchange"
    When I am logged in as "myname1"
    When I start to sign up for "Sensitive Gift Exchange"
    Then I should see "othername"

  Scenario: User tries to change pseud on a challenge signup and should not be able to, as it would break matching
    Given "myname1" has the pseud "othername"
    Given I am logged in as "mod1"
      And I have created the gift exchange "Sensitive Gift Exchange"
      And I open signups for "Sensitive Gift Exchange"
    When I am logged in as "myname1"
    When I sign up for "Sensitive Gift Exchange" with combination A
    Then I should see "Sign-up was successfully created"
      And I should see "Sign-up for myname1"
    When I edit my signup for "Sensitive Gift Exchange"
    Then I should not see "othername"

  Scenario: Mod can see everyone's assignments, includind users' emails
    Given I am logged in as "mod1"
      And everyone has their assignments for "Awesome Gift Exchange"
    When I go to the "Awesome Gift Exchange" assignments page
      Then I should see "Assignments for Awesome"
    When I follow "Open"
    Then I should see "Open Assignments"
      And I should see "myname1"
      And I should see the image "alt" text "email myname1"

  Scenario: User can see their assignment, but no email links
    Given everyone has their assignments for "Awesome Gift Exchange"
    When I am logged in as "myname1"
      And I go to my user page
      And I follow "Assignments"
    Then I should see "Awesome Gift Exchange"
    When I follow "Awesome Gift Exchange"
      Then I should see "Requests by myname3"
      But I should not see the image "alt" text "email myname3"
      And I should see "Offers by myname1"
      But I should not see the image "alt" text "email myname1"

  Scenario: User fulfills their assignment and it shows on their assigments page as fulfilled

    Given everyone has their assignments for "Awesome Gift Exchange"
    When I am logged in as "myname1"
      And I fulfill my assignment
    When I go to my user page
      And I follow "Assignments"
    Then I should see "Awesome Gift Exchange"
      And I should not see "Not yet posted"
      And I should see "Fulfilled Story"
    When I am logged in as "mod1"
      And I go to the "Awesome Gift Exchange" assignments page
      And I follow "Complete"
    Then I should see "myname1"
      And I should see "Fulfilled Story"

  Scenario: A mod can default all incomplete assignments

    Given everyone has their assignments for "Awesome Gift Exchange"
      And I am logged in as "myname1"
      And I fulfill my assignment
    When I am logged in as "mod1"
      And I go to the "Awesome Gift Exchange" assignments page
      And I follow "Default All Incomplete"
    Then I should see "All unfulfilled assignments marked as defaulting."
      And I should see "Undefault myname2"
      And I should see "Undefault myname3"
      And I should see "Undefault myname4"
      And I should not see "Undefault myname1"

  Scenario: User can default and a mod can undefault on their assignment

    Given everyone has their assignments for "Awesome Gift Exchange"
    When I am logged in as "myname1"
      And I go to my assignments page
      And I follow "Default"
    Then I should see "We have notified the collection maintainers that you had to default on your assignment."
    When I am logged in as "mod1"
      And I go to the "Awesome Gift Exchange" assignments page
      And I check "Undefault myname1"
      And I press "Submit"
    Then I should see "Assignment updates complete!"
      And I should not see "Undefault"
    When I am logged in as "myname1"
      And I go to my assignments page
      And I should see "Default"

  Scenario: User can default and a mod can assign a pinch hitter

    Given everyone has their assignments for "Awesome Gift Exchange"
    When I am logged in as "myname1"
      And I go to my assignments page
      And I follow "Default"
    Then I should see "We have notified the collection maintainers that you had to default on your assignment."
    When I am logged in as "mod1"
      And I go to the "Awesome Gift Exchange" assignments page
      And I fill in "Pinch Hitter:" with "nonexistent"
      And I press "Submit"
    Then I should see "We couldn't find the user nonexistent to assign that to."
    When I fill in "Pinch Hitter:" with "myname1"
      And I press "Submit"
    Then I should see "No assignments to review!"
      And I should see "Assignment updates complete!"

  Scenario: Refused story should still fulfill the assignment

    Given an assignment has been fulfilled in a gift exchange
      And I reveal works for "Awesome Gift Exchange"
      And I refuse my gift story "Fulfilled Story"
      And I am logged in as "mod1"
      And I go to the "Awesome Gift Exchange" assignments page
      And I follow "Complete"
    Then I should see "myname1"
      And I should see "Fulfilled Story"

  Scenario: Download signups CSV
    Given I am logged in as "mod1"
    And I have created the gift exchange "My Gift Exchange"

    When I go to the "My Gift Exchange" signups page
    And I follow "Download (CSV)"
    Then I should download a csv file with the header row "Pseud Email Sign-up URL Request 1 Tags Request 1 Description Offer 1 Tags Offer 1 Description"

  Scenario: View a signup summary with no tags
    Given the following activated users exist
    | login   | password |
    | user1   | password |
    | user2   | password |
    | user3   | password |
    | user4   | password |
    | user5   | password |
    | user6   | password |
    When I am logged in as "mod1"
      And I have created the tagless gift exchange "My Gift Exchange"
      And I open signups for "My Gift Exchange"
    When I am logged in as "user1" with password "password"
      And I start to sign up for "My Gift Exchange" tagless gift exchange
    When I am logged in as "user2" with password "password"
      And I start to sign up for "My Gift Exchange" tagless gift exchange
    When I am logged in as "user3" with password "password"
      And I start to sign up for "My Gift Exchange" tagless gift exchange
    When I am logged in as "user4" with password "password"
      And I start to sign up for "My Gift Exchange" tagless gift exchange
    When I am logged in as "user5" with password "password"
      And I start to sign up for "My Gift Exchange" tagless gift exchange
    When I am logged in as "user6" with password "password"
      And I start to sign up for "My Gift Exchange" tagless gift exchange
    When I am logged in as "mod1"
      And I go to "My Gift Exchange" collection's page
      And I follow "Sign-up Summary"
    Then I should not see "Summary does not appear until at least"
      And I should see "Tags were not used in this Challenge, so there is no summary to display here."

  Scenario: Sign-up Form link shows up in sidebar of moderated collections
    Given I am logged in as "mod1"
      And I have created the gift exchange "Cabbot Cove"
      And I open signups for "Cabbot Cove"
    When  I am logged in as "Scott" with password "password"
      And I go to "Cabbot Cove" collection's page
      And I should see "Unmoderated"
      And I should see "Sign-up Form"
    Then  I am logged in as "mod1"
      And I go to "Cabbot Cove" collection's page
      And I follow "Collection Settings"
      And I check "This collection is moderated"
      And I press "Update"
    Then I am logged in as "Scott" with password "password"
      And I go to "Cabbot Cove" collection's page
      And I should see "Moderated"
      And I should see "Sign-up Form"

  Scenario: Mod deletes a user's sign-up and a user deletes their own sign-up without JavaScript
    Given I am logged in as "mod1"
      And I have created the gift exchange "Awesome Gift Exchange"
      And I open signups for "Awesome Gift Exchange"
      And everyone has signed up for the gift exchange "Awesome Gift Exchange"
    When I am logged in as "mod1"
      And I go to the "Awesome Gift Exchange" signups page
      And I delete the signup by "myname1"
    Then I should see "Challenge sign-up was deleted."
    When I am logged in as "myname2"
      And I delete my signup for the gift exchange "Awesome Gift Exchange"
    Then I should see "Challenge sign-up was deleted."

  Scenario: Assignment emails should contain all the information in the request
  # Note: tag names are lowercased for the test so we could borrow the potential
  # match steps, and due to the HTML, each tag must be looked for separate from
  # its label or other tags of its type
    Given I create the gift exchange "EmailTest" with the following options
        | value      | minimum | maximum | match |
        | prompts    | 1       | 1       | 1     |
        | fandoms    | 1       | 1       | 0     |
        | characters | 1       | 1       | 0     |
        | freeforms  | 0       | 2       | 0     |
        | ratings    | 0       | 1       | 0     |
        | categories | 0       | 1       | 0     |
      And the user "badgirlsdoitwell" signs up for "EmailTest" with the following prompts
        | type    | characters | fandoms  | freeforms | ratings | categories |
        | request | any        | the show | fic, art  | Mature  |            |
        | offer   | villain    | the show | fic       |         |            |
      And the user "sweetiepie" signs up for "EmailTest" with the following prompts
        | type    | characters | fandoms  | freeforms | ratings | categories |
        | request | protag     | the book |           |         | any        |
        | offer   | protag     | the book | fic       |         |            |
      When I have generated matches for "EmailTest"
        And I have sent assignments for "EmailTest"
      Then 1 email should be delivered to "sweetiepie"
        And the email should contain "Fandom:"
        And the email should contain "the show"
        And the email should contain "Additional Tags:"
        And the email should contain "fic"
        And the email should contain "art"
        And the email should contain "Character:"
        And the email should contain "Any"
        And the email should contain "Rating:"
        And the email should contain "Mature"
        And the email should not contain "Relationships:"
        And the email should not contain "Warnings:"
        And the email should not contain "Category:"
        And the email should not contain "Optional Tags:"
      Then 1 email should be delivered to "badgirlsdoitwell"
        And the email should contain "Fandom:"
        And the email should contain "the book"
        And the email should contain "Character:"
        And the email should contain "protag"
        And the email should contain "Category:"
        And the email should contain "Any"
        And the email should not contain "Additional Tags:"
        And the email should not contain "Relationships:"
        And the email should not contain "Rating:"
        And the email should not contain "Warnings:"
        And the email should not contain "Optional Tags:"

  Scenario: A mod can delete a gift exchange without needing Javascript and all the assignments and
  sign-ups will be deleted with it, but the collection will remain
    Given everyone has their assignments for "Bad Gift Exchange"
      And I am logged in as "mod1"
    When I delete the challenge "Bad Gift Exchange"
    Then I should see "Are you sure you want to delete the challenge from the collection Bad Gift Exchange? All sign-ups, assignments, and settings will be lost. (Works and bookmarks will remain in the collection.)"
    When I press "Yes, Delete Challenge"
    Then I should see "Challenge settings were deleted."
      And I should not see the gift exchange dashboard for "Bad Gift Exchange"
      And no one should have an assignment for "Bad Gift Exchange"
      And no one should be signed up for "Bad Gift Exchange"
    When I am on the collections page
    Then I should see "Bad Gift Exchange"

  Scenario: A user can still access their Sign-ups page after a gift exchange
  they were signed up for has been deleted
    Given I am logged in as "mod1"
      And I have created the gift exchange "Bad Gift Exchange"
      And I open signups for "Bad Gift Exchange"
      And everyone has signed up for the gift exchange "Bad Gift Exchange"
      And the challenge "Bad Gift Exchange" is deleted
    When I am logged in as "myname1"
      And I go to my signups page
    Then I should see "Challenge Sign-ups"
      And I should not see "Bad Gift Exchange"

  Scenario: A user can still access their Assignments page after a gift exchange
  they had an unfulfilled assignment in has been deleted
    Given everyone has their assignments for "Bad Gift Exchange"
      And the challenge "Bad Gift Exchange" is deleted
    When I am logged in as "myname1"
      And I go to my assignments page
    Then I should see "My Assignments"
      And I should not see "Bad Gift Exchange"

  Scenario: A user can still access their Assignments page after a gift exchange
  they had a fulfilled assignment in has been deleted
    Given an assignment has been fulfilled in a gift exchange
      And the challenge "Awesome Gift Exchange" is deleted
    When I am logged in as "myname1"
      And I go to my assignments page
    Then I should see "My Assignments"
      And I should not see "Awesome Gift Exchange"

  Scenario: A mod can purge assignments after they have been sent, but must
  first confirm the action
    Given everyone has their assignments for "Bad Gift Exchange"
      And I am logged in as "mod1"
    When I go to the "Bad Gift Exchange" assignments page
      And I follow "Purge Assignments"
    Then I should see "Are you sure you want to purge all assignments for Bad Gift Exchange?"
    When I press "Yes, Purge Assignments"
    Then I should see "Assignments purged!"

  Scenario: The My Assignments page that a user sees when they have multiple
  assignments in a single exchange does not include an email link.
    Given everyone has their assignments for "Bad Gift Exchange"
      And I am logged in as "write_in_giver"
      And "write_in_giver" has two pinchhit assignments in the gift exchange "Bad Gift Exchange"
    When I go to "Bad Gift Exchange" collection's page
      And I follow "My Assignments" within "#dashboard"
    Then I should not see the image "src" text "/images/envelope_icon.gif"

  Scenario: A user who disallows gift works is cautioned about signing up for
  an exchange, and a user who allows them is not.
    Given the gift exchange "Some Gift Exchange" is ready for signups
      And I am logged in as "participant"
      And the user "participant" disallows gifts
    When I go to "Some Gift Exchange" collection's page
      And I follow "Sign-up Form"
    Then I should see "assigned users to gift works to you regardless of your preference settings"
    When the user "participant" allows gifts
      And I go to "Some Gift Exchange" collection's page
      And I follow "Sign-up Form"
    Then I should not see "assigned users to gift works to you regardless of your preference settings"

  Scenario: If a work is connected to an assignment for a user who disallows
  gifts, user is still automatically added as a gift recipient. The recipient
  remains attached even if the work is later disconnected from the assignment.
    Given basic tags
      And the user "recip" exists and is activated
      And the user "recip" disallows gifts
      And I am logged in as "gifter"
      And I have an assignment for the user "recip" in the collection "exchange_collection"
    When I fulfill my assignment
    Then I should see "For recip."
    When I follow "Edit"
      And I uncheck "exchange_collection (recip)"
      And I press "Post"
    Then I should see "For recip."

  Scenario: A user can explicitly give a gift to a user who disallows gifts if
  the work is connected to an assignment. The recipient remains attached even if
  the work is later disconnected from the assignment.
    Given basic tags
      And the user "recip" exists and is activated
      And the user "recip" disallows gifts
      And I am logged in as "gifter"
      And I have an assignment for the user "recip" in the collection "exchange_collection"
    When I start to fulfill my assignment
      And I fill in "Gift this work to" with "recip"
      And I press "Post"
    Then I should see "For recip."
    When I follow "Edit"
      And I uncheck "exchange_collection (recip)"
      And I press "Post"
    Then I should see "For recip."
