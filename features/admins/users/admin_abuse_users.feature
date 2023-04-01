@admin
Feature: Admin Abuse actions
  In order to manage user accounts
  As an admin
  I want to be able to manage abusive users

  Background:
    Given the user "mrparis" exists and is activated
      And I have an orphan account
      And I am logged in as a "policy_and_abuse" admin
    When I go to the abuse administration page for "mrparis"

  Scenario: An admin adds a note to a user
    Given I choose "Record note"
      And I fill in "Notes" with "This user is suspicious."
    When I press "Update"
    Then I should see "Note was recorded."
      And I should see "Note Added"
      And I should see "This user is suspicious."

  Scenario: A user is given a warning with a note
    Given I choose "Record warning"
      And I fill in "Notes" with "Next time, the brig."
    When I press "Update"
    Then I should see "Warning was recorded."
      And I should see "Warned"
      And I should see "Next time, the brig."

  Scenario: A user cannot be given a warning without a note
    Given I choose "Record warning"
    When I press "Update"
    Then I should see "You must include notes in order to perform this action."

  Scenario: orphan_account cannot get a note
    When I go to the abuse administration page for "orphan_account"
      And I choose "Record note"
      And I fill in "Notes" with "This user is suspicious."
    When I press "Update"
    Then I should see "orphan_account cannot be warned, suspended, or banned."

  Scenario: orphan_account cannot be warned
    When I go to the abuse administration page for "orphan_account"
      And I choose "Record warning"
      And I fill in "Notes" with "Next time, the brig."
    When I press "Update"
    Then I should see "orphan_account cannot be warned, suspended, or banned."

  Scenario: orphan_account cannot be suspended
    When I go to the abuse administration page for "orphan_account"
      And I choose "Suspend: enter a whole number of days"
      And I fill in "suspend_days" with "30"
      And I fill in "Notes" with "Disobeyed orders."
    When I press "Update"
    Then I should see "orphan_account cannot be warned, suspended, or banned."

  Scenario: orphan_account cannot be banned
    When I go to the abuse administration page for "orphan_account"
      And I choose "Suspend permanently (ban user)"
      And I fill in "Notes" with "To the New Zealand penal colony with you."
    When I press "Update"
    Then I should see "orphan_account cannot be warned, suspended, or banned."

  Scenario: orphan_account cannot be spambanned
    When I go to the abuse administration page for "orphan_account"
      And I choose "Spammer: ban and delete all creations"
    When I press "Update"
    Then I should see "orphan_account cannot be warned, suspended, or banned."

  Scenario: A user is given a suspension with a note and number of days
    Given I choose "Suspend: enter a whole number of days"
      And I fill in "suspend_days" with "30"
      And I fill in "Notes" with "Disobeyed orders."
    When I press "Update"
    Then I should see "User has been temporarily suspended."
      And I should see "Suspended until"
      And I should see "Disobeyed orders."

  Scenario: A user cannot be given a suspension without a number of days
    Given I choose "Suspend: enter a whole number of days"
      And I fill in "Notes" with "Disobeyed orders."
    When I press "Update"
    Then I should see "Please enter the number of days for which the user should be suspended."

  Scenario: A user cannot be given a suspension with a date but without a note
    Given I choose "Suspend: enter a whole number of days"
      And I fill in "suspend_days" with "30"
    When I press "Update"
    Then I should see "You must include notes in order to perform this action."

  Scenario: A user is banned with a note
    Given I choose "Suspend permanently (ban user)"
      And I fill in "Notes" with "To the New Zealand penal colony with you."
    When I press "Update"
    Then I should see "User has been permanently suspended."
      And I should see "Suspended Permanently"
      And I should see "To the New Zealand penal colony with you."
    When I follow "Manage Users"
      And I fill in "Name" with "mrparis"
      And I press "Find"
    Then I should see "1 user found"
    When I follow "Details"
    Then I should see "To the New Zealand penal colony with you."

  Scenario: A user cannot be banned without a note
    Given the user "mrparis" exists and is activated
      And I am logged in as a "policy_and_abuse" admin
    When I go to the abuse administration page for "mrparis"
      And I choose "Suspend permanently (ban user)"
    When I press "Update"
    Then I should see "You must include notes in order to perform this action."

  Scenario: A user's suspension is lifted with a note
    Given the user "mrparis" is suspended
      And I am logged in as a "policy_and_abuse" admin
    When I go to the abuse administration page for "mrparis"
      And I choose "Lift temporary suspension, effective immediately."
      And I fill in "Notes" with "Good behavior."
    When I press "Update"
    Then I should see "Suspension has been lifted."
      And I should see "Suspension Lifted"
      And I should see "Good behavior."

  Scenario: A user's suspension cannot be lifted without a note
    Given the user "mrparis" is suspended
      And I am logged in as a "policy_and_abuse" admin
    When I go to the abuse administration page for "mrparis"
      And I choose "Lift temporary suspension, effective immediately."
    When I press "Update"
    Then I should see "You must include notes in order to perform this action."

  Scenario: A user's ban is lifted with a note
    Given the user "mrparis" is banned
      And I am logged in as a "policy_and_abuse" admin
    When I go to the abuse administration page for "mrparis"
      And I choose "Lift permanent suspension, effective immediately."
      And I fill in "Notes" with "Need him to infiltrate the Maquis."
    When I press "Update"
    Then I should see "Suspension has been lifted."
      And I should see "Suspension Lifted"
      And I should see "Need him to infiltrate the Maquis."

  Scenario: A user's ban cannot be lifted without a note
    Given the user "mrparis" is banned
      And I am logged in as a "policy_and_abuse" admin
    When I go to the abuse administration page for "mrparis"
      And I choose "Lift permanent suspension, effective immediately."
    When I press "Update"
    Then I should see "You must include notes in order to perform this action."

  Scenario: A spammer can be permabanned and all their creations destroyed
    Given I have a work "Not Spam"
      And I am logged in as "Spamster"
      And I post the work "Loads of Spam"
      And I post the work "Even More Spam"
      And I post the work "Spam 3: Tokyo Drift"
      And I create the collection "Spam Collection"
      And I bookmark the work "Not Spam"
      And I add the work "Loads of Spam" to series "One Spam After Another"
      And I post the comment "I like spam" on the work "Not Spam"
      And I am logged in as a "policy_and_abuse" admin
    When I go to the abuse administration page for "Spamster"
      And I choose "Spammer: ban and delete all creations"
      And I press "Update"
    Then I should see "permanently suspended"
      And the user "Spamster" should be permanently banned
      And I should see "Are you sure you want to delete"
      And I should see "1 bookmarks"
      And I should see "1 collections"
      And I should see "1 series"
      And I should see "Loads of Spam"
      And I should see "Even More Spam"
      And I should see "Spam 3: Tokyo Drift"
      And I should see "I like spam"
    When I press "Yes, Delete All Spammer Creations"
    Then I should see "All creations by user Spamster have been deleted."
      And the work "Loads of Spam" should be deleted
      And the work "Even More Spam" should be deleted
      And the work "Spam 3: Tokyo Drift" should be deleted
      And "Spamster" should receive 3 emails
      And the collection "Spam Collection" should be deleted
      And the series "One Spam After Another" should be deleted
      And the work "Not Spam" should not be deleted
      And there should be no bookmarks on the work "Not Spam"
      And there should be no comments on the work "Not Spam"

  Scenario: A user's works cannot be destroyed unless they are banned
    Given I am logged in as "Spamster"
      And I post the work "Loads of Spam"
      And I am logged in as a "policy_and_abuse" admin
      And I go to the abuse administration page for "Spamster"
      And I choose "Spammer: ban and delete all creations"
      And I press "Update"
      And the user "Spamster" is unbanned in the background
      And I press "Yes, Delete All Spammer Creations"
    Then I should see "That user is not banned"
      And the work "Loads of Spam" should not be deleted

  Scenario: An already-banned user can have their works destroyed
    Given the user "Spamster" is banned
      And I am logged in as a "policy_and_abuse" admin
    When I go to the abuse administration page for "Spamster"
      And I choose "Spammer: ban and delete all creations"
      And I press "Update"
    Then I should see "Are you sure you want to delete"
    When I press "Yes, Delete All Spammer Creations"
    Then I should see "All creations by user Spamster have been deleted."
