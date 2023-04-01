Feature: Some admins can see IP addresses and emails for comments
  Scenario: Admin info for comments isn't visible to logged-out users
    Given the work "Random Work"
      And a guest comment on the work "Random Work"
    When I am a visitor
      And I view the work "Random Work" with comments
    Then I should not see "IP Address:" within ".work.meta"
      And I should not see "IP Address:" within ".comment.group"
      And I should not see "Email:" within ".comment.group"

  Scenario: Admin info for comments isn't visible to the work's owner
    Given the work "Random Work"
      And a guest comment on the work "Random Work"
    When I am logged in as the author of "Random Work"
      And I view the work "Random Work" with comments
    Then I should not see "IP Address:" within ".work.meta"
      And I should not see "IP Address:" within ".comment.group"
      And I should not see "Email:" within ".comment.group"

  Scenario Outline: Only certain admins can see IP addresses and email addresses for comments
    Given the work "Random Work"
      And a guest comment on the work "Random Work"
    When I am logged in as a "<role>" admin
      And I view the work "Random Work" with comments
    Then I <should_ip> see "IP Address:" within ".work.meta"
      And I <should_ip> see "IP Address:" within ".comment.group"
      And I <should_email> see "Email:" within ".comment.group"

  Examples:
    | role             | should_ip  | should_email |
    | superadmin       | should     | should       |
    | policy_and_abuse | should     | should       |
    | support          | should not | should       |
    | board            | should not | should not   |
    | communications   | should not | should not   |
    | docs             | should not | should not   |
    | open_doors       | should not | should not   |
    | tag_wrangling    | should not | should not   |
    | translation      | should not | should not   |

  Scenario: No one can see email addresses for comments by registered users
    Given the work "Random Work"
      And I am logged in as "commenter"
      And I post the comment "Hello" on the work "Random Work"
    When I am logged in as a "superadmin" admin
      And I view the work "Random Work" with comments
    Then I should see "IP Address:" within ".work.meta"
      And I should see "IP Address:" within ".comment.group"
      But I should not see "Email:" within ".comment.group"
