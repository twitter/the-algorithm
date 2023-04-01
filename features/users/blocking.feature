Feature: Blocking
  Scenario: Users can block from my blocked users page
    Given the user "pest" exists and is activated
      And I am logged in as "blocker"
    When I go to my blocked users page
      And I fill in "blocked_id" with "pest"
      And I press "Block"
      And I press "Yes, Block User"
    Then I should see "You have blocked the user pest."
      And the user "blocker" should have a block for "pest"

  Scenario Outline: Users can block from various user-related pages
    Given the user "pest" exists and is activated
      And I am logged in as "blocker"
    When I go to <page>
      And I follow "Block"
      And I press "Yes, Block User"
    Then I should see "You have blocked the user pest."
      And the user "blocker" should have a block for "pest"

    Examples:
      | page                                                 |
      | pest's user page                                     |
      | pest's profile page                                  |
      | the dashboard page for user "pest" with pseud "pest" |

  Scenario: Users can block from the comments
    Given the work "Aftermath"
      And a comment "Ugh." by "pest" on the work "Aftermath"
      And I am logged in as "blocker"
    When I view the work "Aftermath" with comments
      And I follow "Block"
      And I press "Yes, Block User"
    Then I should see "You have blocked the user pest."
      And the user "blocker" should have a block for "pest"

  Scenario: Users cannot block official users
    Given the user "pest" exists and has the role "official"
      And I am logged in as "blocker"
    When I go to pest's user page
      And I follow "Block"
    Then I should see "Sorry, you can't block an official user."
      And I should not see a "Yes, Block User" button

  Scenario: Users cannot block themselves
    Given the user "pest" exists and is activated
      And I am logged in as "pest"
    When I go to pest's user page
    Then I should not see a link "Block"
    When I go to my blocked users page
      And I fill in "blocked_id" with "pest"
      And I press "Block"
    Then I should see "Sorry, you can't block yourself."
      And I should not see a "Yes, Block User" button

  Scenario: Users can unblock from the blocked users page
    Given the user "unblocker" has blocked the user "improving"
      And I am logged in as "unblocker"
    When I go to my blocked users page
      And I follow "Unblock"
      And I press "Yes, Unblock User"
    Then I should see "You have unblocked the user improving."
      And the user "unblocker" should not have a block for "improving"

  Scenario Outline: Users can unblock from various user-related pages
    Given the user "unblocker" has blocked the user "improving"
      And I am logged in as "unblocker"
    When I go to <page>
      And I follow "Unblock"
      And I press "Yes, Unblock User"
    Then I should see "You have unblocked the user improving."
      And the user "unblocker" should not have a block for "improving"

    Examples:
      | page                                                           |
      | improving's user page                                          |
      | improving's profile page                                       |
      | the dashboard page for user "improving" with pseud "improving" |

  Scenario: Users can unblock from the comments
    Given the user "unblocker" has blocked the user "improving"
      And the work "Aftermath"
      And a comment "Wonderful!" by "improving" on the work "Aftermath"
      And I am logged in as "unblocker"
    When I view the work "Aftermath" with comments
      And I follow "Unblock"
      And I press "Yes, Unblock User"
    Then I should see "You have unblocked the user improving."
      And the user "unblocker" should not have a block for "improving"

  Scenario: The blocked users page is paginated
    Given there are 2 blocked users per page
      And the user "blocker" has blocked the user "pest1"
      And the user "blocker" has blocked the user "pest2"
      And the user "blocker" has blocked the user "pest3"
      And the user "blocker" has blocked the user "pest4"
    When I am logged in as "blocker"
      And I go to my blocked users page
    Then I should see "pest4" within "ul.pseud li:nth-child(1)"
      And I should see "pest3" within "ul.pseud li:nth-child(2)"
      And I should not see "pest2"
      And I should not see "pest1"
    When I follow "2" within ".pagination"
    Then I should see "pest2" within "ul.pseud li:nth-child(1)"
      And I should see "pest1" within "ul.pseud li:nth-child(2)"

  Scenario Outline: Authorized admins can see the blocked users page
    Given the user "blocker" has blocked the user "pest"
    When I am logged in as a "<role>" admin
      And I go to the blocked users page for "blocker"
    Then I should see "pest"
      And I should see a link "Unblock"
    When I follow "Unblock"
    Then I should see "Sorry, you don't have permission to access the page you were trying to reach."
      And the user "blocker" should have a block for "pest"

    Examples:
      | role             |
      | superadmin       |
      | policy_and_abuse |
      | support          |
