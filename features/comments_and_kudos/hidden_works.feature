Feature: Comments on Hidden Works

  Scenario: When a work is hidden, admins and the creator can see (but not edit or add) comments, while everyone else is redirected.
    Given I am logged in as "creator"
      And I post the work "To Be Hidden"
      And I post the comment "Can I change this?" on the work "To Be Hidden"
      And I am logged in as "commenter"
      And I post the comment "Do you see?" on the work "To Be Hidden"
      And I am logged in as a "policy_and_abuse" admin
      And I view the work "To Be Hidden"
      And I follow "Hide Work"

    # As an admin
    When I go to the work comments page for "To Be Hidden"
    Then I should see "Do you see?"
      But I should not see "Reply"
      And I should not see "Post Comment"
      And I should see "Sorry, you can't add or edit comments on a hidden work."

    When I am logged in as "creator"
      And I go to the work comments page for "To Be Hidden"
    Then I should see "Do you see?"
      And I should see "Can I change this?"
      But I should not see "Reply"
      And I should not see "Post Comment"
      And I should not see "Edit"
      And I should see "Sorry, you can't add or edit comments on a hidden work."

    When I am logged in as "commenter"
      And I go to the work comments page for "To Be Hidden"
    Then I should not see "Do you see?"
      And I should not see "Sorry, you can't add or edit comments on a hidden work."
      But I should see "Sorry, you don't have permission to access the page you were trying to reach."

    When I am logged out
      And I go to the work comments page for "To Be Hidden"
    Then I should not see "Do you see?"
      And I should not see "Sorry, you can't add or edit comments on a hidden work."
      But I should see "Sorry, you don't have permission to access the page you were trying to reach."

  Scenario: When a work is unrevealed, admins and the creator can see (but not edit or add) comments, while everyone else is redirected.
    Given I am logged in as "creator"
      And I post the work "Murder, She Wrote"
      And I post the comment "Can I change this?" on the work "Murder, She Wrote"
      And I am logged in as "commenter"
      And I post the comment "Do you see?" on the work "Murder, She Wrote"
      And I am logged out
      And I have the hidden collection "Dreamboat"
      And I am logged in as "creator"
      And I edit the work "Murder, She Wrote" to be in the collection "Dreamboat"

    # As the work's creator
    When I go to the work comments page for "Murder, She Wrote"
    Then I should see "Do you see?"
      And I should see "Can I change this?"
      But I should not see "Reply"
      And I should not see "Post Comment"
      And I should not see "Edit"
      And I should see "Sorry, you can't add or edit comments on an unrevealed work."

    When I am logged in as an admin
      And I go to the work comments page for "Murder, She Wrote"
    Then I should see "Do you see?"
      But I should not see "Reply"
      And I should not see "Post Comment"
      And I should see "Sorry, you can't add or edit comments on an unrevealed work."

    When I am logged in as "commenter"
      And I go to the work comments page for "Murder, She Wrote"
    Then I should not see "Do you see?"
      And I should not see "Sorry, you can't add or edit comments on an unrevealed work."
      But I should see "Sorry, you don't have permission to access the page you were trying to reach."

    When I am logged out
      And I go to the work comments page for "Murder, She Wrote"
    Then I should not see "Do you see?"
      And I should not see "Sorry, you can't add or edit comments on an unrevealed work."
      But I should see "Sorry, you don't have permission to access the page you were trying to reach."
