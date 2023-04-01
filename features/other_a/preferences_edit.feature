@users
Feature: Edit preferences
  In order to have an archive full of users
  As a humble user
  I want to fill out my preferences


  Scenario: Ensure all Preference options are available

  Given the following activated user exists
    | login         | password   |
    | scott         | password   |

  When I am logged in as "scott" with password "password"
    And I go to scott's user page
    And I follow "Preferences"
  Then I should see "Set My Preferences"
    And I should see "Show my email address to other people."
    And I should see "Show my date of birth to other people."
    And I should see "Hide my work from search engines when possible."
    And I should see "Hide the share buttons on my work."
    And I should see "Show me adult content without checking."
    And I should see "Show the whole work by default."
    And I should see "Hide warnings (you can still choose to show them)."
    And I should see "Hide additional tags (you can still choose to show them)."
    And I should see "Hide work skins (you can still choose to show them)."
    And I should see "Your site skin"
    And I should see "Your time zone"
    And I should see "Browser page title format"
    And I should see "Turn off emails about comments."
    And I should see "Turn off messages to your inbox about comments."
    And I should see "Turn off copies of your own comments."
    And I should see "Turn off emails about kudos."
    And I should see "Allow others to invite my works to collections."
    And I should see "Turn off emails from collections."
    And I should see "Turn off inbox messages from collections."
    And I should see "Turn off emails about gift works."
    And I should see "Turn on History."
    And I should see "Turn the new user help banner back on."
    And I should see "Turn off the banner showing on every page."


  Scenario: View and edit preferences for history, personal details, view entire work

  Given the following activated user exists
    | login         | password   |
    | editname      | password   |
  When I go to editname's user page
    And I follow "Profile"
  Then I should not see "My email address"
    And I should not see "My birthday"
  When I am logged in as "editname" with password "password"
  Then I should see "Hi, editname!"
    And I should see "Log Out"
  When I post the work "This has two chapters"
  And I follow "Add Chapter"
    And I fill in "content" with "Secondy chapter"
    And I press "Preview"
    And I press "Post"
  Then I should see "Secondy chapter"
    And I follow "Previous Chapter"
  Then I should not see "Secondy chapter"
  When I follow "editname"
  Then I should see "Dashboard" within "div#dashboard"
    And I should see "History" within "div#dashboard"
    And I should see "Preferences" within "div#dashboard"
    And I should see "Profile" within "div#dashboard"
  When I follow "Preferences" within "div#dashboard"
  Then I should see "Set My Preferences"
    And I should see "Orphan My Works"
  When I follow "Edit My Profile"
  Then I should see "Password"
  # TODO: figure out why pseud switcher doesn't show up in cukes
  # When I follow "editname" within "#pseud_switcher"
  When I follow "Dashboard"
    And I follow "Profile"
  Then I should see "Set My Preferences"
  When I follow "Set My Preferences"
  Then I should see "Edit My Profile"
  When I uncheck "Turn on History"
    And I check "Show the whole work by default."
    And I check "Show my email address to other people."
    And I check "Show my date of birth to other people."
    And I press "Update"
  Then I should see "Your preferences were successfully updated"
  And I should not see "History" within "div#dashboard"
  When I go to the works page
    And I follow "This has two chapters"
  Then I should see "Secondy chapter"
  When I log out
    And I go to editname's user page
    And I follow "Profile"
  Then I should see "My email address"
    And I should see "My birthday"
  When I go to the works page
    And I follow "This has two chapters"
  Then I should not see "Secondy chapter"

  @javascript
  Scenario: User can hide warning and freeform tags and reveal them on a case-
  by-case basis.

  Given I limit myself to the Archive
    And a canonical freeform "Scary tag"
    And I am logged in as "someone_else"
    And I post the work "Someone Else's Work" as part of a series "A Series"
    And I am logged in as "tester"
    And I post the work "My Work"
    And I bookmark the work "Someone Else's Work"

  # Change tester's preferences to hide warnings.
  When I set my preferences to hide warnings
  Then I should see "Your preferences were successfully updated"

  # Warnings are hidden on work meta, except on user's own works.
  # We use a selector so it doesn't pick up the info in the Share box.
  When I view the work "Someone Else's Work"
  Then I should not see "No Archive Warnings Apply" within "dl.work.meta"
    And I should see "Show warnings"
    And I should see "Scary tag" within "dl.work.meta"
    And I should not see "Show additional tags"
  When I follow "Show warnings"
  Then I should see "No Archive Warnings Apply" within "dl.work.meta"
  When I follow "No Archive Warnings Apply" within "dl.work.meta"
  Then I should be on the works tagged "No Archive Warnings Apply"
  When I view the work "My Work"
  Then I should see "No Archive Warnings Apply" within "dl.work.meta"
    And I should not see "Show warnings"
    And I should see "Scary tag" within "dl.work.meta"
    And I should not see "Show additional tags"

  # Warnings are hidden in work blurbs, except on user's own works.
  When I go to someone_else's works page
  Then I should see "Someone Else's Work" 
    And I should not see "No Archive Warnings Apply" within "li.warnings"
    And I should see "Show warnings"
    And I should see "Scary tag" within "li.freeforms"
    And I should not see "Show additional tags"
  When I follow "Show warnings"
  Then I should see "No Archive Warnings Apply" within "li.warnings"
  When I follow "No Archive Warnings Apply" within "li.warnings"
  Then I should be on the works tagged "No Archive Warnings Apply"
  When I go to my works page
  Then I should see "My Work"
    And I should see "No Archive Warnings Apply" within "li.warnings"
    And I should not see "Show warnings"
    And I should see "Scary tag" within "li.freeforms"
    And I should not see "Show additional tags"

  # Warnings are hidden in series blurbs.
  When I go to someone_else's series page
  Then I should see "A Series"
    And I should not see "No Archive Warnings Apply" within "li.warnings"
    And I should see "Show warnings"
    And I should see "Scary tag" within "li.freeforms"
    And I should not see "Show additional tags"
  When I follow "Show warnings"
  Then I should see "No Archive Warnings Apply" within "li.warnings"
  When I follow "No Archive Warnings Apply" within "li.warnings"
  Then I should be on the works tagged "No Archive Warnings Apply"

  # Warnings are hidden in bookmark blurbs.
  # This is slightly excessive -- bookmarks use the work blurb -- but we'll
  # check in case that ever changes.
  When I go to my bookmarks page
  Then I should see "Someone Else's Work"
    And I should not see "No Archive Warnings Apply" within "li.warnings"
    And I should see "Show warnings"
    And I should see "Scary tag" within "li.freeforms"
    And I should not see "Show additional tags"
  When I follow "Show warnings"
  Then I should see "No Archive Warnings Apply" within "li.warnings"
  When I follow "No Archive Warnings Apply" within "li.warnings"
  Then I should be on the works tagged "No Archive Warnings Apply"

  # Change tester's preferences to hide freeforms as well as warnings.
  When I go to my preferences page
    And I check "Hide additional tags"
    And I press "Update"
  Then I should see "Your preferences were successfully updated"

  # Freeforms and warnings are hidden on work meta, except for user's own works.
  When I view the work "Someone Else's Work"
  Then I should not see "No Archive Warnings Apply" within "dl.work.meta"
    And I should see "Show warnings"
    And I should not see "Scary tag" within "dl.work.meta"
    And I should see "Show additional tags"
  When I follow "Show warnings"
  Then I should see "No Archive Warnings Apply" within "dl.work.meta"
    And I should not see "Scary tag" within "dl.work.meta"
  When I follow "Show additional tags"
  Then I should see "Scary tag" within "dl.work.meta"
  When I view the work "My Work"
  Then I should see "No Archive Warnings Apply" within "dl.work.meta"
    And I should not see "Show warnings"
    And I should see "Scary tag" within "dl.work.meta"
    And I should not see "Show additional tags"

  # Freeforms and warnings are hidden in work blurbs, except on user's own
  # works.
  When I go to someone_else's works page
  Then I should see "Someone Else's Work" 
    And I should not see "No Archive Warnings Apply" within "li.warnings"
    And I should see "Show warnings"
    And I should not see "Scary tag" within "li.freeforms"
    And I should see "Show additional tags"
  When I follow "Show warnings"
  Then I should see "No Archive Warnings Apply" within "li.warnings"
    And I should not see "Scary tag" within "li.freeforms"
  When I follow "Show additional tags"
  Then I should see "Scary tag" within "li.freeforms"
  When I go to my works page
  Then I should see "My Work"
    And I should see "No Archive Warnings Apply" within "li.warnings"
    And I should not see "Show warnings"
    And I should see "Scary tag" within "li.freeforms"
    And I should not see "Show additional tags"

  # Freeforms and warnings are hidden in series blurbs.
  When I go to someone_else's series page
  Then I should see "A Series"
    And I should not see "No Archive Warnings Apply" within "li.warnings"
    And I should see "Show warnings"
    And I should not see "Scary tag" within "li.freeforms"
    And I should see "Show additional tags"
  When I follow "Show warnings"
  Then I should see "No Archive Warnings Apply" within "li.warnings"
    And I should not see "Scary tag" within "li.freeforms"
  When I follow "Show additional tags"
  Then I should see "Scary tag" within "li.freeforms"

  # Freeforms and warnings are hidden in bookmark blurbs.
  When I go to my bookmarks page
  Then I should see "Someone Else's Work"
    And I should not see "No Archive Warnings Apply" within "li.warnings"
    And I should see "Show warnings"
    And I should not see "Scary tag" within "li.freeforms"
    And I should see "Show additional tags"
  When I follow "Show warnings"
  Then I should see "No Archive Warnings Apply" within "li.warnings"
    And I should not see "Scary tag" within "li.freeforms"
  When I follow "Show additional tags"
  Then I should see "Scary tag" within "li.freeforms"

  # Change tester's preferences to show warnings but keep freeforms hidden.
  When I go to my preferences page
    And I uncheck "Hide warnings"
    And I press "Update"
  Then I should see "Your preferences were successfully updated"

  # Freeforms are hidden on work meta, except on user's own works.
  When I view the work "Someone Else's Work"
  Then I should see "No Archive Warnings Apply" within "dl.work.meta"
    And I should not see "Show warnings"
    And I should see "Show additional tags"
    And I should not see "Scary tag" within "dl.work.meta"
  When I follow "Show additional tags"
  Then I should see "Scary tag" within "dl.work.meta"
  When I follow "Scary tag" within "dl.work.meta"
  Then I should be on the works tagged "Scary tag"
  When I view the work "My Work"
  Then I should see "No Archive Warnings Apply" within "dl.work.meta"
    And I should not see "Show warnings"
    And I should see "Scary tag" within "dl.work.meta"
    And I should not see "Show additional tags"

  # Freeforms are hidden in work blurbs, except on user's own works.
  When I go to someone_else's works page
  Then I should see "Someone Else's Work" 
    And I should see "No Archive Warnings Apply" within "li.warnings"
    And I should not see "Show warnings"
    And I should not see "Scary tag" within "li.freeforms"
    And I should see "Show additional tags"
  When I follow "Show additional tags"
  Then I should see "Scary tag" within "li.freeforms"
  When I follow "Scary tag" within "li.freeforms"
  Then I should be on the works tagged "Scary tag"
  When I go to my works page
  Then I should see "My Work"
    And I should see "No Archive Warnings Apply" within "li.warnings"
    And I should not see "Show warnings"
    And I should see "Scary tag" within "li.freeforms"
    And I should not see "Show additional tags"

  # Freeforms are hidden in series blurbs.
  When I go to someone_else's series page
  Then I should see "A Series"
    And I should see "No Archive Warnings Apply" within "li.warnings"
    And I should not see "Show warnings"
    And I should not see "Scary tag" within "li.freeforms"
    And I should see "Show additional tags"
  When I follow "Show additional tags"
  Then I should see "Scary tag" within "li.freeforms"
  When I follow "Scary tag" within "li.freeforms"
  Then I should be on the works tagged "Scary tag"

  # Freeforms are hidden in bookmark blurbs.
  When I go to my bookmarks page
  Then I should see "Someone Else's Work"
    And I should see "No Archive Warnings Apply" within "li.warnings"
    And I should not see "Show warnings"
    And I should not see "Scary tag" within "li.freeforms"
    And I should see "Show additional tags"
  When I follow "Show additional tags"
  Then I should see "Scary tag" within "li.freeforms"
  When I follow "Scary tag" within "li.freeforms"
  Then I should be on the works tagged "Scary tag"

  Scenario: User can hide warning and freeform tags on work blurbs and meta with
  JavaScript disabled, but gets an error if they attempt to reveal them.

    Given I am logged in as "first_user"
      And I post the work "Asteroid Blues" with fandom "Cowboy Bebop" with freeform "Ed is a sweetie"
    When I am logged in
      And I set my preferences to hide both warnings and freeforms
      And I go to first_user's works page

    # Check hidden tags on the blurb
    Then I should see "Asteroid Blues"
      And I should not see "No Archive Warnings Apply" within "li.warnings"
      And I should not see "Ed is a sweetie"
    When I follow "Show additional tags"
    Then I should see "Sorry, you need to have JavaScript enabled for this."
      And I should see "Show additional tags"
    When I follow "Show warnings"
    Then I should see "Sorry, you need to have JavaScript enabled for this."
      And I should see "Show warnings"

    # Check hidden tags in the meta
    When I view the work "Asteroid Blues"
      And I follow "Show additional tags"
    Then I should see "Sorry, you need to have JavaScript enabled for this."
      And I should see "Show additional tags"
    When I follow "Show warnings"
    Then I should see "Sorry, you need to have JavaScript enabled for this."
      And I should see "Show warnings"

  Scenario: User can hide warning and freeform tags on series blurbs with
  JavaScript disabled, but gets an error if they attempt to reveal them.

    Given I am logged in as "first_user"
      And I post the work "Asteroid Blues" with fandom "Cowboy Bebop" with freeform "Ed is a sweetie" as part of a series "Cowboy Bebop Blues"
      And I post the work "Wild Horses" with fandom "Cowboy Bebop" with freeform "Faye Valentine is a sweetie" as part of a series "Cowboy Bebop Blues"
    When I am logged in
      And I set my preferences to hide both warnings and freeforms
      And I go to first_user's series page
    Then I should see "Cowboy Bebop Blues"
      And I should not see "No Archive Warnings Apply" within "li.warnings"
      And I should not see "Ed is a sweetie"
      And I should not see "Faye Valentine is a sweetie"
    When I follow "Show additional tags"
    Then I should see "Sorry, you need to have JavaScript enabled for this."
      And I should see "Show additional tags"
    When I follow "Show warnings"
    Then I should see "Sorry, you need to have JavaScript enabled for this."
      And I should see "Show warnings"
