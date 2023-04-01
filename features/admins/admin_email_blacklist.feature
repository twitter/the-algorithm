@admin
Feature: Admin email blacklist
  In order to prevent the use of certain email addresses in guest comments
  As an an admin
  I want to be able to manage a list of banned email addresses

Scenario: Ban email address
  Given I am logged in as a "policy_and_abuse" admin
  Then I should see "Banned Emails"
  When I follow "Banned Emails"
  Then I should see "Find banned emails"
    And I should see "Ban email address"
    And I should see "Email to ban"
    And I should see "Email to find"
  When I fill in "Email to ban" with "foo@bar.com"
    And I press "Ban Email"
  Then I should see "Email address foo@bar.com banned."
    And the address "foo@bar.com" should be banned

Scenario: Remove email address from banned emails list
  Given I am logged in as a "policy_and_abuse" admin
    And I have banned the address "foo@bar.com"
  When I follow "Banned Emails"
    And I fill in "Email to find" with "bar"
    And I press "Search Banned Emails"
  Then I should see "email found"
    And I should see "foo@bar.com"
  When I follow "Remove"
  Then I should see "Email address foo@bar.com removed from banned emails list."
    And the address "foo@bar.com" should not be banned

Scenario: Banned email addresses should not be usable in guest comments
  Given I am logged in as a "policy_and_abuse" admin
    And I have banned the address "foo@bar.com"
    And I am logged in as "author"
    And I post the work "New Work"
  When I post the comment "I loved this" on the work "New Work" as a guest with email "foo@bar.com"
  Then I should see "has been blocked at the owner's request"
    And I should not see "Comments (1)"
  When I fill in "Guest email" with "someone@bar.com"
    And I press "Comment"
  Then I should see "Comments (1)"

Scenario: Variants of banned email addresses should not be usable
  Given I am logged in as a "policy_and_abuse" admin
  When I have banned the address "foo.bar+gloop@googlemail.com"
  Then the address "foobar@gmail.com" should be banned
  When I am logged out
  Then I should not be able to comment with the address "foobar@gmail.com"
    And I should not be able to comment with the address "foobar+baz@gmail.com"
    And I should not be able to comment with the address "foo.bar@gmail.com"
    And I should be able to comment with the address "whee@gmail.com"

Scenario: Banning a user's email should not affect their ability to post comments
  Given the user "author" exists and is activated
    And I am logged in as a "policy_and_abuse" admin
    And I have banned the address for user "author"
  When I am logged in as "author"
    And I post the work "New Work"
    And I post a comment "here's a great comment"
  Then I should see "Comment created!"
