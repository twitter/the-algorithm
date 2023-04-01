@archivist_import
Feature: Archivist bulk imports

  Background:
    Given I have an archivist "archivist"
      And the default ratings exist
      And all warnings exist
      And I am logged in as "archivist"

  Scenario: Non-archivist cannot import for others
    Given I am logged in as a random user
    When I go to the import page
    Then I should not see "Import for others ONLY with permission"

  Scenario: Make a user an archivist
    Given I have pre-archivist setup for "not_archivist"
      And I am logged in as an "open_doors" admin
    When I make "not_archivist" an archivist
    Then I should see "User was successfully updated"

  Scenario: Archivist can see link to import for others
    When I go to the import page
    Then I should see "Import for others ONLY with permission"

  Scenario: Importing for others without an email address should give an error
    Given I am logged in as "archivist"
    When I start importing "http://import-site-with-tags" with a mock website as an archivist
      And I check "Import for others ONLY with permission"
      And I fill in "Author Name*" with "Name"
      And I press "Import"
    Then I should see "We couldn't successfully import that work, sorry: No author email specified"

  Scenario: Importing for others without a name should give an error
    Given I am logged in as "archivist"
    When I start importing "http://import-site-with-tags" with a mock website as an archivist
      And I check "Import for others ONLY with permission"
      And I fill in "Author Email Address*" with "foo@example.com"
      And I press "Import"
    Then I should see "We couldn't successfully import that work, sorry: No author name specified"

  Scenario: Importing for others without a name or email address should give an error
    Given I am logged in as "archivist"
    When I start importing "http://import-site-with-tags" with a mock website as an archivist
      And I check "Import for others ONLY with permission"
      And I press "Import"
    Then I should see "We couldn't successfully import that work, sorry: No external author name or email specified"

  Scenario: Importing for an author without an account should have the correct byline and email
    When I import the work "http://rebecca2525.livejournal.com/3562.html"
    Then I should see "We have notified the author(s) you imported works for"
      And I should see "rebecca2525 [archived by archivist]"
      And 1 email should be delivered to "rebecca2525@livejournal.com"
      And the email should contain invitation warnings from "archivist" for work "Importing Test" in fandom "Lewis"

  # TODO: Enable after AO3-6353.
  @wip
  Scenario: Import a work for multiple authors without accounts should display all in the byline
    When I go to the import page
      And I import the work "http://ao3testing.dreamwidth.org/593.html" by "name1" with email "a@ao3.org" and by "name2" with email "b@ao3.org"
    Then I should see "Story"
      And I should see "name1 [archived by archivist]"
      And I should see "name2 [archived by archivist]"

  # TODO: Enable after AO3-6353.
  @wip
  Scenario: Import a work for multiple authors without accounts should send emails to all authors
    When I go to the import page
      And I import the work "http://ao3testing.dreamwidth.org/593.html" by "name1" with email "a@ao3.org" and by "name2" with email "b@ao3.org"
    Then 1 email should be delivered to "a@ao3.org"
      And 1 email should be delivered to "b@ao3.org"

  # TODO: Enable after AO3-6353.
  @wip
  Scenario: Import a work for multiple authors with and without accounts should display all in the byline
    Given the following activated users exist
      | login | email |
      | user1 | a@ao3.org |
    When I go to the import page
      And I import the work "http://ao3testing.dreamwidth.org/593.html" by "name1" with email "a@ao3.org" and by "name2" with email "b@ao3.org"
    Then I should see "Story"
      And I should see "user1"
      And I should see "name2 [archived by archivist]"

  # TODO: Enable after AO3-6353.
  @wip
  Scenario: Import a work for multiple authors with and without accounts should send emails to all authors
    Given the following activated users exist
      | login | email |
      | user1 | a@ao3.org |
    When I go to the import page
      And I import the work "http://ao3testing.dreamwidth.org/593.html" by "name1" with email "a@ao3.org" and by "name2" with email "b@ao3.org"
    Then 1 email should be delivered to "a@ao3.org"
      And 1 email should be delivered to "b@ao3.org"

  # TODO: Enable after AO3-6353.
  @wip
  Scenario: Import a work for multiple authors with accounts should not display the archivist
    Given the following activated users exist
      | login | email |
      | user1 | a@ao3.org |
      | user2 | b@ao3.org |
    When I go to the import page
    And I import the work "http://ao3testing.dreamwidth.org/593.html" by "name1" with email "a@ao3.org" and by "name2" with email "b@ao3.org"
    Then I should see "Story"
      And I should see "user1"
      And I should see "user2"
      But I should not see "archivist" within ".byline"

  # TODO: Enable after AO3-6353.
  @wip
  Scenario: Import multiple works as an archivist
    When I import the works "http://ao3testing.dreamwidth.org/593.html, http://ao3testing.dreamwidth.org/325.html"
    Then I should see multi-story import messages
      And I should see "Story"
      And I should see "Test entry"
      And I should see "We have notified the author(s) you imported works for. If any were missed, you can also add co-authors manually."

  # TODO: Enable after AO3-6353.
  @wip
  Scenario: Importing only sends one email even if there are many works
    When I import the works "http://ao3testing.dreamwidth.org/593.html, http://ao3testing.dreamwidth.org/325.html"
    Then 1 email should be delivered to "ao3testing@dreamwidth.org"

  # TODO: Enable after AO3-6353.
  @wip
  Scenario: Importing for an existing Archive author should have correct byline and email
    Given the following activated user exists
      | login | email                     |
      | ao3   | ao3testing@dreamwidth.org |
    When I import the work "http://ao3testing.dreamwidth.org/593.html"
      And all indexing jobs have been run
    Then I should see import confirmation
      And I should see "ao3"
      And I should not see "[archived by archivist]"
      And 1 email should be delivered to "ao3testing@dreamwidth.org"
      And the email should contain claim information
    When I go to ao3's works page
    Then I should see "Story"

  # TODO: Enable after AO3-6353.
  @wip
  Scenario: Importing for an email address that's not associated with an existing Archive account, but that does belong to a user, allows the user to claim the works and add them to their account
    Given the user "creator" exists and is activated
    When I import the work "http://ao3testing.dreamwidth.org/593.html" by "creator" with email "not_creators_account_email@example.com"
      And all indexing jobs have been run
    Then 1 email should be delivered to "not_creators_account_email@example.com"
    When I am logged in as "creator"
      # Use the URL because we get logged out if we follow the link in the email
      And I go to the claim page for "not_creators_account_email@example.com"
    Then I should see "Claim your works with your logged-in account."
    When I press "Add these works to my currently-logged-in account"
      And all indexing jobs have been run
    Then I should see "Author Identities for creator"
      And I should see "We have added the stories imported under not_creators_account_email@example.com to your account."
    When I go to creator's works page
    Then I should see "Story"

  # TODO: Enable after AO3-6353.
  @wip
  Scenario: Importing sends an email to a guessed address if it can't find the author
    When I import the work "http://ao3testing.dreamwidth.org/593.html"
    Then I should see import confirmation
      And I should see "Story"
      # Importer assumes dreamwidth email for works from there
      And 1 email should be delivered to "ao3testing@dreamwidth.org"
      And the email should contain invitation warnings from "archivist" for work "Story" in fandom "Testing"

  # TODO: Enable after AO3-6353.
  @wip
  Scenario: Import a single work as an archivist specifying an external author
    When I go to the import page
      And I import the work "http://ao3testing.dreamwidth.org/593.html" by "randomtestname" with email "random@example.com"
    Then I should not see multi-story import messages
      And I should see "Story"
      And I should see "randomtestname"
      And I should see "We have notified the author(s) you imported works for. If any were missed, you can also add co-authors manually."
      And 1 email should be delivered to "random@example.com"

  # TODO: Enable after AO3-6353.
  @wip
  Scenario: Import a single work as an archivist specifying an external author with an invalid name
    When I import the work "http://ao3testing.dreamwidth.org/593.html" by "ra_ndo!m-t??est n@me." with email "random@example.com"
    Then I should see import confirmation
    And I should see "ra_ndom-test n@me."
    And 1 email should be delivered to "random@example.com"

  # TODO: Enable after AO3-6353.
  @wip
  Scenario: Claim a work and create a new account in response to an invite
    Given account creation is enabled
    When I import the work "http://ao3testing.dreamwidth.org/593.html" by "randomtestname" with email "random@example.com"
    Then 1 email should be delivered to "random@example.com"
      And the email should contain "Claim or remove your works"
    When I am logged out
      And I follow "Claim or remove your works" in the email
    Then I should see "Claiming Your Imported Works"
    And I should see "An archive including some of your work(s) has been moved to the Archive of Our Own."
    When I press "Sign me up and give me my works!"
    Then I should see "Create Account"
    When I fill in the sign up form with valid data
    And I press "Create Account"
    Then I should see "Almost Done!"

  # TODO: Enable after AO3-6353.
  @wip
  Scenario: Orphan a work in response to an invite, leaving name on it
    Given I have an orphan account
    When I import the work "http://ao3testing.dreamwidth.org/593.html" by "randomtestname" with email "random@example.com"
    Then 1 email should be delivered to "random@example.com"
      And the email should contain "Claim or remove your works"
    When I am logged out
      And I follow "Claim or remove your works" in the email
    Then I should see "Claiming Your Imported Works"
      And I should see "An archive including some of your work(s) has been moved to the Archive of Our Own."
    When I choose "Orphan my works and take my email address off them, but keep my name."
      And I wait 2 seconds
      And I press "Update"
    Then I should see "Your imported stories have been orphaned. Thank you for leaving them in the archive! Your preferences have been saved."
    When I am logged in
      And I view the work "Story"
    Then I should see "randomtestname (orphan_account)"

  # TODO: Enable after AO3-6353.
  @wip
  Scenario: Orphan a work in response to an invite, taking name off it
    Given I have an orphan account
    When I import the work "http://ao3testing.dreamwidth.org/593.html" by "randomtestname" with email "random@example.com"
    Then 1 email should be delivered to "random@example.com"
      And the email should contain "Claim or remove your works"
    When I am logged out
      And I follow "Claim or remove your works" in the email
    Then I should see "Claiming Your Imported Works"
      And I should see "An archive including some of your work(s) has been moved to the Archive of Our Own."
    When I choose "Orphan my works and take my email address off them, but keep my name."
      And I check "Assign my works to the AO3 orphan_account, removing both my name and email address."
      And I wait 2 seconds
      And I press "Update"
    Then I should see "Your imported stories have been orphaned. Thank you for leaving them in the archive! Your preferences have been saved."
    When I am logged in
      And I view the work "Story"
    Then I should not see "randomtestname"
      And I should see "orphan_account"

  # TODO: Enable after AO3-6353.
  @wip
  Scenario: Delete an imported work and choose not to be notified of future imports of your works
    When I import the work "http://ao3testing.dreamwidth.org/593.html" by "randomtestname" with email "random@example.com"
    Then 1 email should be delivered to "random@example.com"
      And the email should contain "Claim or remove your works"
    When I am logged out
      And I follow "Claim or remove your works" in the email
    Then I should see "Claiming Your Imported Works"
      And I should see "An archive including some of your work(s) has been moved to the Archive of Our Own. Please let us know what you'd like us to do with them."
    When I choose "Please remove my works from the archive entirely."
      And I check "Do not email me in the future when works are imported with this email address."
      And I press "Update"
    Then I should be on the homepage
      And I should see "Your imported stories have been deleted. Your preferences have been saved."
    When the email queue is clear
      And I am logged in as "archivist"
      And I import the work "http://ao3testing.dreamwidth.org/325.html" by "randomtestname" with email "random@example.com"
     Then 0 emails should be delivered to "random@example.com"

  # TODO: Enable after AO3-6353.
  @wip
  Scenario: Leave an imported work in the archivist's care
    When I import the work "http://ao3testing.dreamwidth.org/593.html" by "randomtestname" with email "random@example.com"
    Then 1 email should be delivered to "random@example.com"
      And the email should contain "Claim or remove your works"
    When I am logged out
      And I follow "Claim or remove your works" in the email
    Then I should see "Claiming Your Imported Works"
      And I should see "An archive including some of your work(s) has been moved to the Archive of Our Own. Please let us know what you'd like us to do with them."
    When I choose "Leave my works in the care of the archivist."
      And I press "Update"
    Then I should be on the homepage
      And I should see "Okay, we'll leave things the way they are! You can use the email link any time if you change your mind. Your preferences have been saved."

  # TODO: Enable after AO3-6353.
  @wip
  Scenario: Leave an imported work in the archivist's care and choose not to be notified of future imports of your works
    When I import the work "http://ao3testing.dreamwidth.org/593.html" by "randomtestname" with email "random@example.com"
    Then 1 email should be delivered to "random@example.com"
      And the email should contain "Claim or remove your works"
    When I am logged out
      And I follow "Claim or remove your works" in the email
    Then I should see "Claiming Your Imported Works"
      And I should see "An archive including some of your work(s) has been moved to the Archive of Our Own. Please let us know what you'd like us to do with them."
    When I choose "Leave my works in the care of the archivist."
      And I check "Do not email me in the future when works are imported with this email address."
      And I press "Update"
    Then I should be on the homepage
      And I should see "Okay, we'll leave things the way they are! You can use the email link any time if you change your mind. Your preferences have been saved."
    When the email queue is clear
      And I am logged in as "archivist"
      And I import the work "http://ao3testing.dreamwidth.org/325.html" by "randomtestname" with email "random@example.com"
     Then 0 emails should be delivered to "random@example.com"

  # TODO: Enable after AO3-6353.
  @wip
  Scenario: Leave an imported work in the archivist's care and do not allow future imports with your email address
    When I import the work "http://ao3testing.dreamwidth.org/593.html" by "randomtestname" with email "random@example.com"
    Then 1 email should be delivered to "random@example.com"
      And the email should contain "Claim or remove your works"
    When I am logged out
      And I follow "Claim or remove your works" in the email
    Then I should see "Claiming Your Imported Works"
      And I should see "An archive including some of your work(s) has been moved to the Archive of Our Own. Please let us know what you'd like us to do with them."
    When I choose "Leave my works in the care of the archivist."
      And I check "From now on, do not import works with this email address."
      And I press "Update"
    Then I should be on the homepage
      And I should see "Okay, we'll leave things the way they are! You can use the email link any time if you change your mind. Your preferences have been saved."
    When I am logged in as "archivist"
      And I import the work "http://ao3testing.dreamwidth.org/325.html" by "randomtestname" with email "random@example.com"
    Then I should see "We couldn't successfully import that work, sorry: Author randomtestname at random@example.com does not allow importing their work to this archive."

  # TODO: Enable after AO3-6353.
  @wip
  Scenario: Importing straight into a collection
    Given I have a collection "Club"
      And I am logged in as "archivist"
    When I start to import the work "http://ao3testing.dreamwidth.org/593.html" by "randomtestname" with email "random@example.com"
      And I press "Import"
    Then I should see "We have notified the author(s) you imported works for. If any were missed, you can also add co-authors manually."
    When I press "Edit"
      And I fill in "work_collection_names" with "Club"
      And I press "Post"
    Then I should see "Story"
      And I should see "randomtestname"
      And I should see "Club"
      And 1 email should be delivered to "random@example.com"

  # TODO: Enable after AO3-6353.
  @wip
  Scenario: Should not be able to import for others unless the box is checked
    When I go to the import page
      And I fill in "URLs*" with "http://ao3testing.dreamwidth.org/593.html"
      And I select "English" from "Choose a language"
      And I fill in "Author Name*" with "ao3testing"
      And I fill in "Author Email Address*" with "ao3testing@example.com"
      And I press "Import"
    Then I should see /You have entered an external author name or e-mail address but did not select "Import for others."/
    When I check the 1st checkbox with id matching "importing_for_others"
      And I press "Import"
    Then I should see "We have notified the author(s) you imported works for. If any were missed, you can also add co-authors manually."

  Scenario: Archivist can't see Open Doors tools
    When I go to the Open Doors tools page
    Then I should see "Sorry, you don't have permission to access the page you were trying to reach."

  Scenario: Open Doors committee members can update the redirect URL of a work
    Given the work "My Immortal"
      And I have an Open Doors committee member "OpenDoors"
      And I am logged in as "OpenDoors"
    When I go to the Open Doors tools page
    Then I should see "Update Redirect URL"
    When I fill in "imported_from_url" with "http://example.com/my-immortal"
      And I fill in "work_url" with the path to the "My Immortal" work page
      And I submit with the 2nd button
    Then I should see "Updated imported-from url for My Immortal to http://example.com/my-immortal"
    When I follow "http://example.com/my-immortal"
    Then I should be on the "My Immortal" work page

  # TODO: Enable after AO3-6353.
  @wip
  Scenario: Open Doors committee members can block an email address from having imports
    Given I have an Open Doors committee member "OpenDoors"
      And I have an archivist "archivist"
      And the default ratings exist
      And I am logged in as "OpenDoors"
    When I go to the Open Doors tools page
      And I fill in "external_author_email" with "random@example.com"
      And I submit with the 3rd button
    Then I should see "We have saved and blocked the email address random@example.com"
    When I am logged in as "archivist"
      And I import the work "http://ao3testing.dreamwidth.org/593.html" by "ao3testing" with email "random@example.com"
    Then I should see "Author ao3testing at random@example.com does not allow importing their work to this archive."

  # TODO: Enable after AO3-6353.
  @wip
  Scenario: Open Doors committee members can supply a new email address for an already imported work.
    Given I have an Open Doors committee member "OpenDoors"
      And I have an archivist "archivist"
      And the default ratings exist
      And I am logged in as "archivist"
    When I import the work "http://ao3testing.dreamwidth.org/593.html" by "randomtestname" with email "random@example.com"
      And I am logged in as "OpenDoors"
      And I go to the Open Doors external authors page
    Then I should see "random@example.com"
    When I fill in "email" with "random_person@example.com"
      And I submit
    Then I should see "Claim invitation for random@example.com has been forwarded to random_person@example.com"
      And 1 email should be delivered to "random_person@example.com"
