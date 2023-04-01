Feature: Muting
  Scenario: Users can mute from my muted users page
    Given the user "pest" exists and is activated
      And I am logged in as "muter"
    When I go to my muted users page
      And I fill in "muted_id" with "pest"
      And I press "Mute"
      And I press "Yes, Mute User"
    Then I should see "You have muted the user pest."
      And the user "muter" should have a mute for "pest"

  Scenario Outline: Users can mute from various user-related pages
    Given the user "pest" exists and is activated
      And I am logged in as "muter"
    When I go to <page>
      And I follow "Mute"
      And I press "Yes, Mute User"
    Then I should see "You have muted the user pest."
      And the user "muter" should have a mute for "pest"

    Examples:
      | page                                                 |
      | pest's user page                                     |
      | pest's profile page                                  |
      | the dashboard page for user "pest" with pseud "pest" |

  Scenario: Users cannot mute official users
    Given the user "pest" exists and has the role "official"
      And I am logged in as "muter"
    When I go to pest's user page
      And I follow "Mute"
    Then I should see "Sorry, you can't mute an official user."
      And I should not see a "Yes, Mute User" button

  Scenario: Users cannot mute themselves
    Given the user "pest" exists and is activated
      And I am logged in as "pest"
    When I go to pest's user page
    Then I should not see a link "Mute"
    When I go to my muted users page
      And I fill in "muted_id" with "pest"
      And I press "Mute"
    Then I should see "Sorry, you can't mute yourself."
      And I should not see a "Yes, Mute User" button

  Scenario: Users can unmute from the muted users page
    Given the user "unmuter" has muted the user "improving"
      And I am logged in as "unmuter"
    When I go to my muted users page
      And I follow "Unmute"
      And I press "Yes, Unmute User"
    Then I should see "You have unmuted the user improving."
      And the user "unmuter" should not have a mute for "improving"

  Scenario Outline: Users can unmute from various user-related pages
    Given the user "unmuter" has muted the user "improving"
      And I am logged in as "unmuter"
    When I go to <page>
      And I follow "Unmute"
      And I press "Yes, Unmute User"
    Then I should see "You have unmuted the user improving."
      And the user "unmuter" should not have a mute for "improving"

    Examples:
      | page                                                           |
      | improving's user page                                          |
      | improving's profile page                                       |
      | the dashboard page for user "improving" with pseud "improving" |

  Scenario: The muted users page is paginated
    Given there are 2 muted users per page
      And the user "muter" has muted the user "pest1"
      And the user "muter" has muted the user "pest2"
      And the user "muter" has muted the user "pest3"
      And the user "muter" has muted the user "pest4"
    When I am logged in as "muter"
      And I go to my muted users page
    Then I should see "pest4" within "ul.pseud li:nth-child(1)"
      And I should see "pest3" within "ul.pseud li:nth-child(2)"
      And I should not see "pest2"
      And I should not see "pest1"
    When I follow "2" within ".pagination"
    Then I should see "pest2" within "ul.pseud li:nth-child(1)"
      And I should see "pest1" within "ul.pseud li:nth-child(2)"

  Scenario Outline: Authorized admins can see the muted users page
    Given the user "muter" has muted the user "pest"
    When I am logged in as a "<role>" admin
      And I go to the muted users page for "muter"
    Then I should see "pest"
      And I should see a link "Unmute"
    When I follow "Unmute"
    Then I should see "Sorry, you don't have permission to access the page you were trying to reach."
      And the user "muter" should have a mute for "pest"

    Examples:
      | role             |
      | superadmin       |
      | policy_and_abuse |
      | support          |

  @javascript
  Scenario: Users cannot see works by a muted user
    Given the user "muter" has muted the user "pest"
      And the work "Annoying Work" by "pest"
    When I am logged in as "muter"
      And I go to pest's works page
    Then I should not see "Annoying Work"
    When I am logged out
      And I go to pest's works page
    Then I should see "Annoying Work"

  @javascript
  Scenario: Users cannot see series by a muted user
    Given the user "muter" has muted the user "pest"
      And the work "Annoying Work" by "pest"
      And I am logged in as "pest"
      And I edit the work "Annoying Work"
      And I add the series "Annoying Series"
      And I press "Post"
    When I am logged in as "muter"
      And I go to pest's series page
    Then I should not see "Annoying Series"
    When I am logged out
      And I go to pest's series page
    Then I should see "Annoying Series"

  @javascript
  Scenario: Users cannot see bookmarks by a muted user
    Given the user "muter" has muted the user "pest"
      And the work "Good Work" by "muter"
      And I am logged in as "pest"
      And I have a bookmark for "Good Work"
    When I am logged in as "muter"
      And I go to pest's bookmarks page
    Then I should not see "Good Work"
    When I am logged out
      And I go to pest's bookmarks page
    Then I should see "Good Work"

  @javascript
  Scenario: Users cannot see comments by a muted user
    Given the user "muter" has muted the user "pest"
      And the work "Good Work" by "muter"
      And I am logged in as "pest"
      And I post the comment "fxxk you" on the work "Good Work"
    When I am logged in as "muter"
      And I view the work "Good Work"
    Then I should not see "fxxk you"
    When I am logged out
      And I view the work "Good Work"
    Then I should see "Good Work"
