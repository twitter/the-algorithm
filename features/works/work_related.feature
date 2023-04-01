@works
Feature: Inspirations, remixes and translations
  In order to reflect the connections between some fanworks
  As a fan author, part of a fan community
  I want to be able to list related works

Scenario: Posting a remix / related work emails the creator of the original work and lists the parent work in the proper location on the remix / related work

  Given I have related works setup
  When I post a related work as remixer
  Then a parent related work should be seen
    And the original author should be emailed

Scenario: Remixer can see their remix / related work on their related works page

  Given I have related works setup
  When I post a related work as remixer
  When I go to my user page
  Then I should see "Related Works (1)"
  When I follow "Related Works"
  Then I should see "Works that inspired remixer"
    And I should see "Worldbuilding by inspiration"

Scenario: Creator of original work can see a remix on their related works page

  Given I have related works setup
    And a related work has been posted
  When I am logged in as "inspiration"
    And I view my related works
  Then I should see "Works inspired by inspiration"
    And I should see "Followup by remixer"

Scenario: Posting a translation emails the creator of the original work and lists the parent work in the proper location on the translation

  Given I have related works setup
  When I post a translation as translator
  Then a parent translated work should be seen
    And the original author should be emailed

Scenario: Translator can see their translation on their related works page

  Given I have related works setup
  When I post a translation as translator
  When I go to my user page
  Then I should see "Related Works (1)"
  When I follow "Related Works"
  Then I should see "Works translated by translator"
    And I should see "Worldbuilding by inspiration"
    And I should see "From English to Deutsch"

Scenario: Creator of original work can see a translation on their related works page

  Given I have related works setup
    And a translation has been posted
  When I am logged in as "inspiration"
    And I view my related works
  Then I should see "Translations of inspiration's works"
    And I should see "Worldbuilding Translated by translator"
    And I should see "From English to Deutsch"

Scenario: Unapproved translations do not appear or produce an associations list on the original work

  Given I have related works setup
  When I post a translation as translator
  When I view the work "Worldbuilding"
  Then I should not see the translation listed on the original work
    And I should not find a list for associations

Scenario: Unapproved related works do not appear or produce an associations list on the original work

  Given I have related works setup
  When I post a related work as remixer
  When I view the work "Worldbuilding"
  Then I should not see the related work listed on the original work
    And I should not find a list for associations

Scenario: The creator of the original work can approve a related work that is NOT a translation and see it referenced in the beginning notes and linked in the end notes

  Given I have related works setup
    And a related work has been posted
  When I am logged in as "inspiration"
    And I view my related works
  When I follow "Approve"
  Then I should see "Approve Link"
  When I press "Yes, link me!"
  Then I should see "Link was successfully approved"
    And I should see a beginning note about related works
    And I should see the related work in the end notes
    And I should not find a list for associations

Scenario: The creator of the original work can approve a translation and see it linked in an associations list in the beginning notes, and there should not be a list of "works inspired by this one"

  Given I have related works setup
    And a translation has been posted
  When I approve a related work
  Then I should see "Link was successfully approved"
    And I should see the translation in the beginning notes
    And I should not see "Works inspired by this one:"
    And I should find a list for associations

Scenario: Translation, related work, and parent work links appear in the right places even when viewing a multi-chapter work with draft chapters in chapter-by-chapter mode

  Given I have related works setup
    And a translation has been posted and approved
    And a related work has been posted and approved
    And an inspiring parent work has been posted
  When I am logged in as "inspiration"
    And I edit the work "Worldbuilding"
    And I list the work "Parent Work" as inspiration
    And I press "Post"
    And a chapter is added to "Worldbuilding"
    And a draft chapter is added to "Worldbuilding"
  When I view the work "Worldbuilding"
  Then I should find a list for associations
    And I should see a beginning note about related works
    And I should see the translation in the beginning notes
    And I should see the inspiring parent work in the beginning notes
  When I follow "other works inspired by this one"
  Then I should see the related work in the end notes
    And I should not see the translation in the end notes

Scenario: The creator of the original work can see approved and unapproved relationships on their related works page

  Given I have related works setup
    And a translation has been posted
    And a related work has been posted
  When I approve a related work
  When I view my related works
  Then I should see "Worldbuilding Approve"
    And I should see "Deutsch Remove"

Scenario: The creator of the original work can remove a previously approved related work

  Given I have related works setup
    And a related work has been posted and approved
  When I view my related works
    And I follow "Remove"
  Then I should see "Remove Link"
  When I press "Remove link"
  Then I should see "Link was successfully removed"
    And I should not see the related work listed on the original work

Scenario: The creator of the original work can remove a previously approved translation

  Given I have related works setup
    And a translation has been posted and approved
  When I view my related works
    And I follow "Remove" within "#translationsofme"
  Then I should see "Remove Link"
  When I press "Remove link"
  Then I should see "Link was successfully removed"
    And I should not see the translation listed on the original work

Scenario: Editing an existing work to add an inspiration (parent work) should send email to the creator of the original work

  Given I have related works setup
  When I post a related work as remixer
    And I edit the work "Followup"
    And all emails have been delivered
    And I list the work "Worldbuilding Two" as inspiration
    And I press "Preview"
  When I press "Update"
  Then I should see "Work was successfully updated"
    And I should see "Inspired by Worldbuilding Two by inspiration"
    And "AO3-1506" is fixed
    # And 1 email should be delivered

Scenario: Remixer receives comments on remix, creator of original work doesn't

  Given I have related works setup
    And a related work has been posted
    And all emails have been delivered
  When I am logged in as "commenter"
  When I post the comment "Blah" on the work "Followup"
  Then "remixer" should be emailed
    And "inspiration" should not be emailed

Scenario: Translator receives comments on translation, creator of original work doesn't

  Given I have related works setup
    And a translation has been posted
    And all emails have been delivered
  When I am logged in as "commenter"
  When I post the comment "Blah" on the work "Worldbuilding Translated"
  Then "translator" should be emailed
    And "inspiration" should not be emailed

Scenario: Creator of original work chooses to receive comments on translation

  #Given I have related works setup
  #  And a translation has been posted
  #  And all emails have been delivered
  #When I am logged in as "inspiration"
  #  And I approve a related work
  #  And I set my preferences to receive comments on translated works
  #When I am logged in as "commenter"
  #  And I post the comment "Blah" on the work "Worldbuilding Translated"
  #Then "translator" should be emailed
  #  And "inspiration" should be emailed

Scenario: Creator of original work doesn't receive comments if they haven't approved the translation

  #Given I have related works setup
  #  And a translation has been posted
  #  And all emails have been delivered
  #When I am logged in as "inspiration"
  #  And I set my preferences to receive comments on translated works
  #When I am logged in as "commenter"
  #When I post the comment "Blah" on the work "Worldbuilding Translated"
  #Then "inspiration" should not be emailed

Scenario: Can post a translation of a mystery work

Scenario: Posting a translation of a mystery work should not allow you to see the work

Scenario: Can post a translation of an anonymous work

Scenario: Posting a translation of an anonymous work should not allow you to see the author

Scenario: Translate your own work

  Given I have related works setup
  When I post a translation of my own work
    And I approve a related work
  Then approving the related work should succeed

Scenario: Draft works should not show up on related works

  Given I have related works setup
    And I am logged in as "translator"
    And I draft a translation
  When I am logged in as "inspiration"
    And I go to my user page
  Then I should not see "Related Works (1)"
  When I view my related works
  Then I should not see "Worldbuilding Translated"

Scenario: Listing external works as inspirations

  Given basic tags
    And mock websites with no content
  When I am logged in as "remixer" with password "password"
    And I set up the draft "Followup"
    And I check "parent-options-show"
    And I fill in "URL" with "http://example.org/200"
    And I press "Preview"
  Then I should see a save error message
    And I should see "The title of a parent work outside the archive can't be blank"
    And I should see "The author of a parent work outside the archive can't be blank"
  When I fill in "Title" with "Worldbuilding"
    And I fill in "Author" with "BNF"
    And I check "This is a translation"
    And I press "Preview"
  Then I should see "Draft was successfully created"
  When I press "Post"
  Then I should see "Work was successfully posted"
    And I should see "A translation of Worldbuilding by BNF"
  When I edit the work "Followup"
    And I check "parent-options-show"
    And I fill in "URL" with "http://example.org/301"
    And I press "Preview"
  Then I should see a save error message
    And I should see "The title of a parent work outside the archive can't be blank"
    And I should see "The author of a parent work outside the archive can't be blank"
  When I fill in "Title" with "Worldbuilding Two"
    And I fill in "Author" with "BNF"
    And I press "Preview"
  Then I should see "Preview"
  When I press "Update"
  Then I should see "Work was successfully updated"
    And I should see "A translation of Worldbuilding by BNF"
    And I should see "Inspired by Worldbuilding Two by BNF"
  When I view my related works
  Then I should see "From N/A to English"
  # inactive URL should give a helpful message (AO3-1783)
  # unreachable URL should give a more helpful message (A03-3536)
  When I edit the work "Followup"
    And I check "parent-options-show"
    And I fill in "URL" with "http://example.org/404"
    And I fill in "Title" with "Worldbuilding Two"
    And I fill in "Author" with "BNF"
    And I press "Preview"
  Then I should see "Parent work URL could not be reached. If the URL is correct and the site is currently down, please try again later."

Scenario: External work language

  Given basic tags
    And basic languages
    And mock websites with no content
  When I am logged in as "remixer" with password "password"
    And I go to the new work page
    And I select "Not Rated" from "Rating"
    And I check "No Archive Warnings Apply"
    And I select "English" from "Choose a language"
    And I fill in "Fandoms" with "Stargate"
    And I fill in "Work Title" with "Followup 4"
    And I fill in "content" with "That could be an amusing crossover."
    And I check "parent-options-show"
    And I fill in "URL" with "http://example.org/200"
    And I fill in "Title" with "German Worldbuilding"
    And I fill in "Author" with "BNF"
    And I select "Deutsch" from "Language"
    And I check "This is a translation"
    And I press "Preview"
  Then I should see "Draft was successfully created"
  When I press "Post"
  Then I should see "Work was successfully posted"
    And I should see "A translation of German Worldbuilding by BNF"
  When I view my related works
  Then I should see "From Deutsch to English"
    And I should not see "From N/A to English"

# TODO after issue 1741 is resolved
# Scenario: Test that I can remove relationships that I initiated from my own works
# especially during posting / editing / previewing a work
# especially from the related_works page, which works but redirects to a non-existant page right now

Scenario: Restricted works listed as Inspiration show up [Restricted] for guests
  Given I have related works setup
    And a related work has been posted and approved
  When I am logged in as "remixer"
    And I lock the work "Followup"
  When I am logged out
    And I view the work "Worldbuilding"
  Then I should see "A [Restricted Work] by remixer"
  When I am logged in as "remixer"
    And I unlock the work "Followup"
  When I am logged out
    And I view the work "Followup"
  Then I should see "Inspired by Worldbuilding by inspiration"
  When I am logged in as "inspiration"
    And I lock the work "Worldbuilding"
  When I am logged out
    And I view the work "Followup"
  Then I should see "Inspired by [Restricted Work] by inspiration"

Scenario: Anonymous works listed as inspiration should have links to the authors,
  but only for the authors themselves and admins
  Given I have related works setup
    And I have the anonymous collection "Muppets Anonymous"
    And a related work has been posted and approved

  When I am logged in as "remixer"
    And I edit the work "Followup" to be in the collection "Muppets_Anonymous"
    And I view the work "Worldbuilding"
  Then I should see "Works inspired by this one: Followup by Anonymous [remixer]"
  When I follow "remixer" within ".afterword .children"
  Then I should be on my "remixer" pseud page

  When I am logged in as an admin
    And I view the work "Worldbuilding"
  Then I should see "Works inspired by this one: Followup by Anonymous [remixer]"
  When I follow "remixer" within ".afterword .children"
  Then I should be on remixer's "remixer" pseud page

  When I am logged out
    And I view the work "Worldbuilding"
  Then I should see "Works inspired by this one: Followup by Anonymous"
    And I should not see "remixer" within ".afterword .children"

Scenario: When a user is notified that a co-authored work has been inspired by a work they posted,
  the e-mail should link to each author's URL instead of showing escaped HTML
  Given I have related works setup
    And the user "misterdeejay" exists and is activated
    And the user "misterdeejay" allows co-creators
    And I am logged in as "inspiration"
    And I post the work "Seed of an Idea"
  When I am logged in as "inspired"
    And I set up the draft "Seedling of an Idea"
    And I invite the co-author "misterdeejay"
    And I preview the work
  Then I should not see "misterdeejay"
    But 1 email should be delivered to "misterdeejay"
    And the email should contain "The user inspired has invited your pseud misterdeejay to be listed as a co-creator on the following work"
  When the user "misterdeejay" accepts all co-creator requests
    And I edit the work "Seedling of an Idea"
    And I list the work "Seed of an Idea" as inspiration
    And I preview the work
    And I post the work
  Then 1 email should be delivered to "inspiration"
    And the email should link to inspired's user url
    And the email should not contain "&lt;a href=&quot;http://archiveofourown.org/users/inspired/pseuds/inspired&quot;"
    And the email should link to misterdeejay's user url
    And the email should not contain "&lt;a href=&quot;http://archiveofourown.org/users/misterdeejay/pseuds/misterdeejay&quot;"

  Scenario: When using a URL on the site to cite a parent work, the URL can't be
  for something that isn't a work
  Given I am logged in
    And I set up a draft "Inspired"
  When I list a series as inspiration
    And I press "Post"
  Then I should see "Only a link to a work can be listed as an inspiration."

  Scenario: When using a URL on the site to cite a parent work, the URL must be
  for a work that exists
  Given I am logged in
    And I set up a draft "Inspired"
  When I list a nonexistent work as inspiration
    And I press "Post"
  Then I should see "The work you listed as an inspiration does not seem to exist."

  Scenario: Protected users cannot have their works cited as related works
  Given I have related works setup
    And the user "inspiration" is a protected user
  When I post a related work as remixer
  Then I should see "You can't use the related works function to cite works by the protected user inspiration."

  Scenario: When editing a work with an existing citation of a protected user's work, the citation remains
  Given I have related works setup
    And a related work has been posted and approved
  When the user "inspiration" is a protected user
    And I am logged in as "remixer"
    And I edit the work "Followup"
    And I fill in "Fandoms" with "I forgot about the witches"
    And I press "Post"
  Then I should see "Work was successfully updated."
    And I should see "Inspired by Worldbuilding by inspiration"

  Scenario: Protected users can approve existing citations of their works
  Given I have related works setup
    And I post a related work as remixer
  When the user "inspiration" is a protected user
    And I am logged in as "inspiration"
    And I go to inspiration's related works page
  Then I should see "inspiration's Related Works"
    And I should see "Followup by remixer"
    And I should see "Approve"
  When I follow "Approve"
    And I press "Yes, link me!"
  Then I should see "Link was successfully approved"
    And I should see a beginning note about related works
    And I should see the related work in the end notes
    And I should not find a list for associations

  Scenario: Protected users can remove existing citations of their works
  Given I have related works setup
    And a related work has been posted and approved
  When the user "inspiration" is a protected user
    And I am logged in as "inspiration"
    And I go to inspiration's related works page
  Then I should see "inspiration's Related Works"
    And I should see "Followup by remixer"
    And I should see "Remove"
  When I follow "Remove"
    And I press "Remove link"
  Then I should see "Link was successfully removed"
    And I should not see the related work listed on the original work

  Scenario: Citing an anonymous work by a protected user does not break anonymity
  Given an anonymous collection "Anonymous"
    And I have related works setup
    And the user "inspiration" is a protected user
  When I am logged in as "inspiration"
    And I edit the work "Worldbuilding" to be in the collection "Anonymous"
  When I post a related work as remixer
  Then I should not see "You can't use the related works function to cite works by the protected user inspiration."
  When I am logged in as "remixer"
    And I go to remixer's related works page
  Then I should see "Works that inspired remixer"
    And I should see "Worldbuilding by Anonymous"
    And I should not see "inspiration"

  Scenario: Citing an unrevealed work by a protected user does not break anonymity
  Given a hidden collection "Hidden"
    And I have related works setup
    And the user "inspiration" is a protected user
  When I am logged in as "inspiration"
    And I edit the work "Worldbuilding" to be in the collection "Hidden"
  When I post a related work as remixer
  Then I should not see "You can't use the related works function to cite works by the protected user inspiration."
  When I am logged in as "remixer"
    And I go to remixer's related works page
  Then I should see "Works that inspired remixer"
    And I should see "A work in an unrevealed collection"
    And I should not see "inspiration"

  Scenario: When a remix is anonymous, it's visible on the original creator's related works page, but not on the remixer's related works page
    Given an anonymous collection "Anonymous"
      And I have related works setup
      And I post a related work as remixer
    When I am logged in as "remixer"
      And I edit the work "Followup" to be in the collection "Anonymous"
      And I go to remixer's related works page
    Then I should see "Works that inspired remixer"
      And I should see "Worldbuilding by inspiration"
    When I go to inspiration's related works page
    Then I should see "Works inspired by inspiration"
      And I should see "Followup by Anonymous [remixer]"
    When I am logged in as "inspiration"
      And I go to remixer's related works page
    Then I should not see "Works that inspired remixer"
      And I should not see "Worldbuilding by inspiration"
    When I go to inspiration's related works page
    Then I should see "Works inspired by inspiration"
      And I should see "Followup by Anonymous"
      And I should not see "remixer"

  Scenario: When a remix is unrevealed, it's visible on the original creator's related works page, but not on the remixer's related works page
    Given a hidden collection "Hidden"
      And I have related works setup
      And I post a related work as remixer
    When I am logged in as "remixer"
      And I edit the work "Followup" to be in the collection "Hidden"
      And I go to remixer's related works page
    Then I should see "Works that inspired remixer"
      And I should see "Worldbuilding by inspiration"
    When I go to inspiration's related works page
    Then I should see "Works inspired by inspiration"
      And I should see "A work in an unrevealed collection"
    When I am logged in as "inspiration"
      And I go to remixer's related works page
    Then I should not see "Works that inspired remixer"
      And I should not see "A work in an unrevealed collection"
      And I should not see "Worldbuilding by inspiration"
    When I go to inspiration's related works page
    Then I should see "Works inspired by inspiration"
      And I should see "A work in an unrevealed collection"
      And I should not see "remixer"

  Scenario: A remix of an anonymous work is shown on the remixer's related works page, but not on the original creator's related works page
    Given an anonymous collection "Anonymous"
      And I have related works setup
      And I post a related work as remixer
    When I am logged in as "inspiration"
      And I edit the work "Worldbuilding" to be in the collection "Anonymous"
      And I go to remixer's related works page
    Then I should see "Works that inspired remixer"
      And I should see "Worldbuilding by Anonymous [inspiration]"
    When I go to inspiration's related works page
    Then I should see "Works inspired by inspiration"
      And I should see "Followup by remixer"
    When I am logged in as "remixer"
      And I go to remixer's related works page
    Then I should see "Works that inspired remixer"
      And I should see "Worldbuilding by Anonymous"
      And I should not see "inspiration"
    When I go to inspiration's related works page
    Then I should not see "Works inspired by inspiration"
      And I should not see "Followup"

  Scenario: A remix of an unrevealed work is shown on the remixer's related works page, but not on the original creator's related works page
    Given a hidden collection "Hidden"
      And I have related works setup
      And I post a related work as remixer
    When I am logged in as "inspiration"
      And I edit the work "Worldbuilding" to be in the collection "Hidden"
      And I go to remixer's related works page
    Then I should see "Works that inspired remixer"
      And I should see "A work in an unrevealed collection"
    When I go to inspiration's related works page
    Then I should see "Works inspired by inspiration"
      And I should see "Followup by remixer"
    When I am logged in as "remixer"
      And I go to remixer's related works page
    Then I should see "Works that inspired remixer"
      And I should see "A work in an unrevealed collection"
      And I should not see "inspiration"
    When I go to inspiration's related works page
    Then I should not see "Works inspired by inspiration"
      And I should not see "A work in an unrevealed collection"
      And I should not see "Followup"

  Scenario: When a translation is anonymous, it's visible on the original creator's related works page, but not on the translator's related works page
    Given an anonymous collection "Anonymous"
      And I have related works setup
      And I post a translation as translator
    When I am logged in as "translator"
      And I edit the work "Worldbuilding Translated" to be in the collection "Anonymous"
      And I go to translator's related works page
    Then I should see "Works translated by translator"
      And I should see "Worldbuilding by inspiration"
      And I should see "From English to Deutsch"
    When I go to inspiration's related works page
    Then I should see "Translations of inspiration's works"
      And I should see "Worldbuilding Translated by Anonymous [translator]"
      And I should see "From English to Deutsch"
    When I am logged in as "inspiration"
      And I go to translator's related works page
    Then I should not see "Works translated by translator"
      And I should not see "Worldbuilding by inspiration"
      And I should not see "From English to Deutsch"
    When I go to inspiration's related works page
    Then I should see "Translations of inspiration's works"
      And I should see "Worldbuilding Translated by Anonymous"
      And I should see "From English to Deutsch"
      And I should not see "translator"

  Scenario: When a translation is unrevealed, it's visible on the original creator's related works page, but not on the translator's related works page
    Given a hidden collection "Hidden"
      And I have related works setup
      And I post a translation as translator
    When I am logged in as "translator"
      And I edit the work "Worldbuilding Translated" to be in the collection "Hidden"
      And I go to translator's related works page
    Then I should see "Works translated by translator"
      And I should see "Worldbuilding by inspiration"
      And I should see "From English to Deutsch"
    When I go to inspiration's related works page
    Then I should see "Translations of inspiration's works"
      And I should see "A work in an unrevealed collection"
    When I am logged in as "inspiration"
      And I go to translator's related works page
    Then I should not see "Works translated by translator"
      And I should not see "Worldbuilding by inspiration"
      And I should not see "From English to Deutsch"
    When I go to inspiration's related works page
    Then I should see "Translations of inspiration's works"
      And I should see "A work in an unrevealed collection"
      And I should not see "translator"

  Scenario: A translation of an anonymous work is shown on the translator's related works page, but not on the original creator's related works page
    Given an anonymous collection "Anonymous"
      And I have related works setup
      And I post a translation as translator
    When I am logged in as "inspiration"
      And I edit the work "Worldbuilding" to be in the collection "Anonymous"
      And I go to translator's related works page
    Then I should see "Works translated by translator"
      And I should see "Worldbuilding by Anonymous [inspiration]"
      And I should see "From English to Deutsch"
    When I go to inspiration's related works page
    Then I should see "Translations of inspiration's works"
      And I should see "Worldbuilding Translated by translator"
      And I should see "From English to Deutsch"
    When I am logged in as "translator"
      And I go to translator's related works page
    Then I should see "Works translated by translator"
      And I should see "Worldbuilding by Anonymous"
      And I should see "From English to Deutsch"
      And I should not see "inspiration"
    When I go to inspiration's related works page
    Then I should not see "Translations of inspiration's works"
      And I should not see "Worldbuilding Translated by translator"
      And I should not see "From English to Deutsch"

  Scenario: A translation of an unrevealed work is shown on the translator's related works page, but not on the original creator's related works page
    Given a hidden collection "Hidden"
      And I have related works setup
      And I post a translation as translator
    When I am logged in as "inspiration"
      And I edit the work "Worldbuilding" to be in the collection "Hidden"
      And I go to translator's related works page
    Then I should see "Works translated by translator"
      And I should see "A work in an unrevealed collection"
      And I should see "From English to Deutsch"
    When I go to inspiration's related works page
    Then I should see "Translations of inspiration's works"
      And I should see "Worldbuilding Translated by translator"
      And I should see "From English to Deutsch"
    When I am logged in as "translator"
      And I go to translator's related works page
    Then I should see "Works translated by translator"
      And I should see "A work in an unrevealed collection"
      And I should see "From English to Deutsch"
      And I should not see "inspiration"
    When I go to inspiration's related works page
    Then I should not see "Translations of inspiration's works"
      And I should not see "A work in an unrevealed collection"
      And I should not see "Worldbuilding Translated by translator"
      And I should not see "From English to Deutsch"
