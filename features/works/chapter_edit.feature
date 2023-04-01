@works
Feature: Edit chapters
  In order to have an work full of chapters
  As a humble user
  I want to add and remove chapters

  Scenario: Add chapters to an existing work, delete chapters, edit chapters, post chapters in the wrong order, use rearrange page, create draft chapter

  Given the following activated user exists
    | login         | password   |
    | epicauthor    | password   |
    And basic tags
  When I go to epicauthor's user page
    Then I should see "There are no works"
  When I am logged in as "epicauthor" with password "password"

  # create a basic single-chapter work
  When I follow "New Work"
  Then I should see "Post New Work"
  When I select "Not Rated" from "Rating"
    And I check "No Archive Warnings Apply"
    And I select "English" from "Choose a language"
    And I fill in "Fandoms" with "New Fandom"
    And I fill in "Work Title" with "New Epic Work"
    And I fill in "content" with "Well, maybe not so epic."
    And I press "Preview"
  Then I should see "Draft was successfully created"
    And I should see "1/1"
  When I press "Post"
  Then I should not see "Chapter 1"
    And I should see "Well, maybe not so epic"
    And I should see "Words:5"

  # add chapters to a single-chapter work
  When I follow "Add Chapter"
    And I fill in "chapter_position" with "2"
    And I fill in "chapter_wip_length" with "100"
    And I fill in "content" with "original chapter two"
    And I press "Preview"
  Then I should see "This is a draft chapter in a posted work. It will be kept unless the work is deleted."
  When I press "Post"
    Then I should see "2/100"
    And I should see "Words:8"
  When I follow "Add Chapter"
    And I fill in "chapter_position" with "3"
    And I fill in "chapter_wip_length" with "50"
    And I fill in "content" with "entering chapter three"
    And I press "Preview"
  Then I should see "Chapter 3"
  When I press "Post"
  Then I should see "3/50"
    And I should see "Words:11"

  # add chapters in the wrong order
  When I follow "Add Chapter"
    And I fill in "chapter_position" with "17"
    And I fill in "chapter_wip_length" with "17"
    And I fill in "content" with "entering fourth chapter out of order"
    And I press "Preview"
  Then I should see "Chapter 4"
  When I press "Post"
    And I should see "4/17"
    And I should see "Words:17"

  # delete a chapter
  When I follow "Edit"
    And I follow "2"
    And I follow "Delete Chapter"
    And I press "Yes, Delete Chapter"
  Then I should see "The chapter was successfully deleted."
    And I should see "3/17"
    And I should see "Words:14"

  # fill in the missing chapter
  When I follow "Add Chapter"
    And I fill in "chapter_position" with "2"
    And I fill in "content" with "entering second chapter out of order"
    And I press "Preview"
  When I press "Post"
  Then I should see "4/17"
    And I should see "Words:20"

  # edit an existing chapter
  When I follow "Edit"
    And I follow "3"
    And I fill in "chapter_position" with "4"
    And I fill in "chapter_wip_length" with "4"
    And I fill in "content" with "last chapter"
    And I press "Preview"
  Then I should see "Chapter 4"
  When I press "Update"
  Then I should see "Chapter was successfully updated"
    And I should see "Chapter 4"
    And I should see "4/4"
    And I should see "Words:19"
  When I follow "Edit"
    And I follow "Manage Chapters"
  Then I should see "Drag chapters to change their order."

  # view chapters in the right order
  When I am logged out
    And all indexing jobs have been run
    And I go to epicauthor's works page
    And I follow "New Epic Work"
    And I follow "Entire Work"
  Then I should see "Chapter 1"
    And I should see "Well, maybe not so epic." within "#chapter-1"
    And I should see "Chapter 2"
    And I should see "entering second chapter out of order" within "#chapter-2"
    And I should see "Chapter 3"
    And I should see "entering fourth chapter out of order" within "#chapter-3"
    And I should see "Chapter 4"
    And I should see "last chapter" within "#chapter-4"
    And I should not see "original chapter two"
  When I follow "Chapter by Chapter"
    And I follow "Chapter Index"
  Then I should see "Chapter Index for New Epic Work by epicauthor"
    And I should see "Chapter 1"
    And I should see "Chapter 2"
    And I should see "Chapter 3"
    And I should see "Chapter 4"

  # move chapters around using rearrange page
  When I am logged in as "epicauthor" with password "password"
    And I view the work "New Epic Work"
    And I follow "Edit"
    And I follow "Manage Chapters"
  Then I should see "Drag chapters to change their order."
  When I fill in "chapters_1" with "4"
    And I fill in "chapters_2" with "3"
    And I fill in "chapters_3" with "2"
    And I fill in "chapters_4" with "1"
  And I press "Update Positions"
  Then I should see "Chapter order has been successfully updated."
  When I am logged out
    And I go to epicauthor's works page
    And I follow "New Epic Work"
    And I follow "Entire Work"
  Then I should see "Chapter 1"
    And I should see "Well, maybe not so epic." within "#chapter-4"
    And I should see "Chapter 2"
    And I should see "second chapter" within "#chapter-3"
    And I should see "Chapter 3"
    And I should see "fourth chapter" within "#chapter-2"
    And I should see "Chapter 4"
    And I should see "last chapter" within "#chapter-1"

  # create a draft chapter and post it, and verify it shows up on the
  # rearrange page
  When I am logged in as "epicauthor" with password "password"
    And a draft chapter is added to "New Epic Work"
  When I view the work "New Epic Work"
    And I follow "Edit"
  Then I should see "5 (Draft)"
  When I follow "Manage Chapters"
  Then I should see "Chapter 5 (Draft)"
  When I view the work "New Epic Work"
    Then I should see "4/5"
  When I select "5." from "selected_id"
    And I press "Go"
    Then I should see "This chapter is a draft and hasn't been posted yet!"
  When I press "Post"
    Then I should see "5/5"
  When I follow "Edit"
    Then I should not see "Draft"
    And I should not see "draft"
  When I view the work "New Epic Work"
    And I select "5." from "selected_id"
    And I press "Go"
    Then I should not see "Draft"
    And I should not see "draft"

  # create a draft chapter, preview it, edit it and then post it without preview
  When I am logged in as "epicauthor" with password "password"
    And I view the work "New Epic Work"
    And I follow "Add Chapter"
    And I fill in "Chapter Title" with "6(66) The Number of the Beast"
    And I fill in "content" with "Even more awesomely epic context"
    And I press "Preview"
  Then I should see "This is a draft chapter in a posted work. It will be kept unless the work is deleted."
  When I press "Edit"
    And I fill in "content" with "Even more awesomely epic context. Plus bonus epicness"
    And I press "Post"
    Then I should see "Chapter was successfully posted."
    And I should not see "This chapter is a draft and hasn't been posted yet!"

  # create a draft chapter, preview it, edit it, preview it again and then post it
  When I am logged in as "epicauthor" with password "password"
    And I view the work "New Epic Work"
    And I follow "Add Chapter"
    And I fill in "Chapter Title" with "6(66) The Number of the Beast"
    And I fill in "content" with "Even more awesomely epic context"
    And I press "Preview"
  Then I should see "This is a draft chapter in a posted work. It will be kept unless the work is deleted."
  When I press "Edit"
    And I fill in "content" with "Even more awesomely epic context. Plus bonus epicness"
    And I press "Preview"
  Then I should see "Even more awesomely epic context. Plus bonus epicness"
  When I press "Post"
    Then I should see "Chapter was successfully posted."
    And I should not see "This chapter is a draft and hasn't been posted yet!"


  Scenario: Create a work and add a draft chapter, edit the draft chapter, and save changes to the draft chapter without previewing or posting
  Given basic tags
    And I am logged in as "moose" with password "muffin"
  When I go to the new work page
  Then I should see "Post New Work"
    And I select "General Audiences" from "Rating"
    And I check "No Archive Warnings Apply"
    And I select "English" from "Choose a language"
    And I fill in "Fandoms" with "If You Give an X a Y"
    And I fill in "Work Title" with "If You Give Users a Draft Feature"
    And I fill in "content" with "They will expect it to work."
    And I press "Post"
  When I should see "Work was successfully posted."
    And I should see "They will expect it to work."
  When I follow "Add Chapter"
    And I fill in "content" with "And then they will request more features for it."
    And I press "Preview"
  Then I should see "This is a draft chapter in a posted work. It will be kept unless the work is deleted."
    And I should see "And then they will request more features for it."
  When I press "Edit"
    And I fill in "content" with "And then they will request more features for it. Like the ability to save easily."
    And I press "Save As Draft"
  Then I should see "Chapter was successfully updated."
    And I should see "This chapter is a draft and hasn't been posted yet!"
    And I should see "Like the ability to save easily."


  Scenario: Chapter drafts aren't updates; posted chapter drafts are
    Given I am logged in as "testuser" with password "testuser"
      And I post the work "Backdated Work"
      And I edit the work "Backdated Work"
      And I check "backdate-options-show"
      And I select "1" from "work_chapter_attributes_published_at_3i"
      And I select "January" from "work_chapter_attributes_published_at_2i"
      And I select "1990" from "work_chapter_attributes_published_at_1i"
      And I press "Post"
    Then I should see "Published:1990-01-01"
    When I follow "Add Chapter"
      And I fill in "content" with "this is my second chapter"
      And I set the publication date to today
      And I press "Preview"
      And I should see "This is a draft"
      And I press "Save As Draft"
    Then I should not see Updated today
      And I should not see Completed today
      And I should not see "Updated" within ".work.meta .stats"
      And I should not see "Completed" within ".work.meta .stats"
    When I follow "Edit Chapter"
      And I press "Post"
      Then I should see Completed today


  Scenario: Posting a new chapter without previewing should set the work's updated date to now

    Given I have loaded the fixtures
      And I am logged in as "testuser" with password "testuser"
    When I view the work "First work"
    Then I should not see Updated today
    When I follow "Add Chapter"
      And I fill in "content" with "this is my second chapter"
      And I set the publication date to today
      And I press "Post"
    Then I should see Completed today
    When I follow "Edit"
      And I fill in "work_wip_length" with "?"
      And I press "Post"
    Then I should see Updated today
    When I post the work "A Whole New Work"
      And I go to the works page
    Then "A Whole New Work" should appear before "First work"
    When I view the work "First work"
    When I follow "Add Chapter"
      And I fill in "content" with "this is my third chapter"
      And I set the publication date to today
      And I press "Post"
      And I go to the works page
    Then "First work" should appear before "A Whole New Work"

  Scenario: Posting a new chapter with a co-creator does not add them to previous or subsequent chapters

    Given I am logged in as "karma" with password "the1nonly"
      And the user "sabrina" allows co-creators
      And I post the work "Summer Friends"
    When a chapter is set up for "Summer Friends"
      And I invite the co-author "sabrina"
      And I post the chapter
    Then I should not see "sabrina"
    When the user "sabrina" accepts all co-creator requests
      And I view the work "Summer Friends"
    Then I should see "karma, sabrina"
      And I should see "Chapter by karma"
    When I follow "Next Chapter"
    Then I should not see "Chapter by"
    When a chapter is set up for "Summer Friends"
    Then I should see "Current co-creators"
      And the "sabrina" checkbox should not be checked
    When I post the chapter
    Then I should see "Chapter by karma"

  Scenario: You can edit a pre-existing chapter to invite a new co-creator

    Given I am logged in as "karma" with password "the1nonly"
      And the user "amy" allows co-creators
      And I post the work "Forever Friends"
      And a chapter is added to "Forever Friends"
    When I view the work "Forever Friends"
      And I view the 2nd chapter
      And I follow "Edit Chapter"
      And I invite the co-author "amy"
      And I post the chapter
    Then I should not see "amy, karma"
      And 1 email should be delivered to "amy"
      And the email should contain "The user karma has invited your pseud amy to be listed as a co-creator on the following chapter"
      And the email should not contain "translation missing"
    When the user "amy" accepts all co-creator requests
      And I view the work "Forever Friends"
    Then I should see "amy, karma"
      And I should see "Chapter by karma"
    When I follow "Next Chapter"
    Then I should not see "Chapter by"


  Scenario: You can edit a chapter to add (not invite) a co-creator who is
  already on the work

    Given I am logged in as "karma" with password "the1noly"
      And I post the work "Past Friends"
      And a chapter with the co-author "sabrina" is added to "Past Friends"
      And all emails have been delivered
      And a chapter is added to "Past Friends"
    When I view the work "Past Friends"
      And I view the 3rd chapter
    Then I should see "Chapter by karma"
    When I follow "Edit Chapter"
    Then the "sabrina" checkbox should not be checked
    When I check "sabrina"
      # Expire cached byline
      And it is currently 1 second from now
      And I post the chapter
    Then I should not see "Chapter by karma"
      And 1 email should be delivered to "sabrina"
      And the email should contain "The user karma has listed your pseud sabrina as a co-creator on the following chapter"
      And the email should contain "a co-creator on a work, you can be added to new chapters regardless of your co-creation settings. You will also be added to any series the work is added to."
      And the email should not contain "translation missing"


  Scenario: Editing a chapter with a co-creator does not allow you to remove the co-creator

    Given I am logged in as "karma" with password "the1noly"
      And I post the work "Camp Friends"
      And a chapter with the co-author "sabrina" is added to "Camp Friends"
    When I follow "Edit Chapter"
    Then the "sabrina" checkbox should be checked and disabled


  Scenario: Removing yourself as a co-creator from the chapter edit page when
  you've co-created multiple chapters on the work removes you only from that 
  specific chapter. Removing yourself as a co-creator from the chapter edit page
  of the last chapter you've co-created also removes you from the work.

    Given the work "OP's Work" by "originalposter" with chapter two co-authored with "opsfriend"
      And a chapter with the co-author "opsfriend" is added to "OP's Work"
      And I am logged in as "opsfriend"
    When I view the work "OP's Work"
      And I view the 3rd chapter
      And I follow "Edit Chapter"
    When I follow "Remove Me As Chapter Co-Creator"
    Then I should see "You have been removed as a creator from the chapter."
      And I should see "Chapter 1"
    When I view the 3rd chapter
    Then I should see "Chapter 3"
      And I should see "Chapter by originalposter"
    When I follow "Previous Chapter"
      And I follow "Edit Chapter"
      And I follow "Remove Me As Chapter Co-Creator"
    Then I should see "You have been removed as a creator from the work."
    When I view the work "OP's Work"
    Then I should not see "Edit Chapter"


  Scenario: Removing yourself as a co-creator from the chapter manage page

    Given the work "OP's Work" by "originalposter" with chapter two co-authored with "opsfriend"
      And a chapter with the co-author "opsfriend" is added to "OP's Work"
      And I am logged in as "opsfriend"
    When I view the work "OP's Work"
      And I follow "Edit"
      And I follow "Manage Chapters"
    When I follow "Remove Me As Chapter Co-Creator"
    Then I should see "You have been removed as a creator from the chapter."
      And I should see "Chapter 1"
    When I view the 2nd chapter
    Then I should see "Chapter by originalposter"


  Scenario: The option to remove yourself as a co-creator should only be
  included for chapters you are a co-creator of

    Given the work "OP's Work" by "originalposter" with chapter two co-authored with "opsfriend"
      And I am logged in as "opsfriend"
    When I view the work "OP's Work"
      And I follow "Edit"
      And I follow "Manage Chapters"
    Then the Remove Me As Chapter Co-Creator option should not be on the 1st chapter
      And the Remove Me As Chapter Co-Creator option should be on the 2nd chapter
    When I view the work "OP's Work"
      And I follow "Edit Chapter"
    Then I should not see "Remove Me As Chapter Co-Creator"
    When I view the work "OP's Work"
      And I view the 2nd chapter
      And I follow "Edit Chapter"
    Then I should see "Remove Me As Chapter Co-Creator"


  Scenario: You should be able to edit a chapter you are not already co-creator
  of, and you will be added to the chapter as a co-creator and your changes will
  be saved

    Given I am logged in as "originalposter"
      And the user "opsfriend" allows co-creators
      And I post the work "OP's Work"
      And a chapter with the co-author "opsfriend" is added to "OP's Work"
    When I am logged in as "opsfriend"
      And I view the work "OP's Work"
    Then I should see "Chapter 1"
      And I should see "Chapter by originalposter"
    When I follow "Edit Chapter"
    Then I should not see "You're not allowed to use that pseud."
    When I fill in "content" with "opsfriend was here"
      And I post the chapter
    Then I should see "opsfriend was here"
      And I should not see "Chapter by originalposter"


  Scenario: You should be able to add a chapter with two co-creators, one of
  whom is already on the work and the other of whom is not

    Given I am logged in as "rusty"
      And the user "sharon" allows co-creators
      And the user "brenda" allows co-creators
      And I set up the draft "Rusty Has Two Moms"
      And I invite the co-author "brenda"
      And I post the work without preview
      And the user "brenda" accepts all co-creator requests
    When a chapter is set up for "Rusty Has Two Moms"
      And I invite the co-author "sharon"
      And I check "brenda"
      And I post the chapter
    Then I should see "brenda, rusty"
      And I should not see "Chapter by"
    When the user "sharon" accepts all co-creator requests
      And I view the work "Rusty Has Two Moms"
    Then I should see "brenda, rusty, sharon"
      And I should see "Chapter by brenda, rusty"
    When I follow "Next Chapter"
    Then I should not see "Chapter by"


  Scenario: You should be able to add a chapter with two co-creators who are not
  on the work, one of whom has an ambiguous pseud

    Given "thebadmom" has the pseud "sharon"
      And "thegoodmom" has the pseud "sharon"
      And the user "brenda" allows co-creators
      And the user "thebadmom" allows co-creators
      And the user "thegoodmom" allows co-creators
      And I am logged in as "rusty"
      And I post the work "Rusty Has Two Moms"
    When a chapter is set up for "Rusty Has Two Moms"
      And I try to invite the co-authors "sharon, brenda"
      And I post the chapter
    Then I should see "The pseud sharon is ambiguous."
    When I select "thegoodmom" from "There's more than one user with the pseud sharon."
      And I press "Post"
    Then I should not see "brenda"
      And I should not see "sharon"
      But 1 email should be delivered to "brenda"
      And 1 email should be delivered to "thegoodmom"
    When the user "brenda" accepts all co-creator requests
      And the user "thegoodmom" accepts all co-creator requests
      And I view the work "Rusty Has Two Moms"
    Then I should see "brenda, rusty, sharon (thegoodmom)"


  Scenario: You should be able to add a chapter with two co-creators, one of
  whom is already on the work and the other of whom has an ambiguous pseud

    Given "thebadmom" has the pseud "sharon"
      And the user "thegoodmom" allows co-creators
      And the user "thebadmom" allows co-creators
      And "thegoodmom" has the pseud "sharon"
      And I am logged in as "rusty"
      And I set up the draft "Rusty Has Two Moms"
      And I invite the co-author "brenda"
      And I post the work without preview
      And the user "brenda" accepts all co-creator requests
    When a chapter is set up for "Rusty Has Two Moms"
      And I invite the co-author "sharon"
      And I check "brenda"
      And I post the chapter
    Then I should see "The pseud sharon is ambiguous."
    When I select "thegoodmom" from "There's more than one user with the pseud sharon."
      And I press "Post"
    Then I should see "brenda, rusty"
    When the user "thegoodmom" accepts all co-creator requests
      And I view the work "Rusty Has Two Moms"
    Then I should see "brenda, rusty, sharon (thegoodmom)"


  Scenario: Users can't set a chapter publication date that is in the future,
  e.g. set the date to April 30 when it is April 26

    Given I am logged in
      And it is currently Wed Apr 26 22:00:00 UTC 2017
      And I post the work "Futuristic"
      And a chapter is set up for "Futuristic"
    When I select "30" from "chapter[published_at(3i)]"
      And I press "Post"
    Then I should see "Publication date can't be in the future."
    When I jump in our Delorean and return to the present


  Scenario: The Post Draft option on your drafts page only posts the first
  chapter of a multi-chapter draft
    Given I have a multi-chapter draft
      And I am on my drafts page
    When I follow "Post Draft"
    Then I should see "Your work was successfully posted."
      And I should not see "This chapter is a draft and hasn't been posted yet!"
    When I follow "Next Chapter"
    Then I should see "This chapter is a draft and hasn't been posted yet!"

  Scenario: You should be able to invite a co-creator to a chapter if they allow it.

    Given the user "brenda" allows co-creators
      And I am logged in as "rusty"
      And I post the work "Rusty Has Two Moms"
    When a chapter is set up for "Rusty Has Two Moms"
      And I invite the co-author "brenda"
      And I press "Post"
    Then I should see "Chapter has been posted!"
      And I should not see "brenda"
      But 1 email should be delivered to "brenda"
      And the email should contain "The user rusty has invited your pseud brenda to be listed as a co-creator on the following chapter"
      And the email should not contain "translation missing"
    When I am logged in as "brenda"
      And I follow "Rusty Has Two Moms" in the email
    Then I should not see "Edit"
    When I follow "Co-Creator Requests page"
      And I check "selected[]"
      # Delay before accepting the request to make sure the cache is expired:
      And it is currently 1 second from now
      And I press "Accept"
    Then I should see "You are now listed as a co-creator on Chapter 2 of Rusty Has Two Moms."
    When I follow "Rusty Has Two Moms"
    Then I should see "brenda, rusty"
      And I should see "Edit"

  Scenario: You should not be able to invite a co-creator to a chapter if they do not allow it.

    Given the user "brenda" disallows co-creators
      And I am logged in as "rusty"
      And I post the work "Rusty Has Two Moms"
    When a chapter is set up for "Rusty Has Two Moms"
      And I try to invite the co-author "brenda"
      And I press "Post"
    Then I should see "brenda does not allow others to invite them to be a co-creator."
      And 0 emails should be delivered to "brenda"
    When I press "Preview"
    Then I should see "This is a draft chapter in a posted work. It will be kept unless the work is deleted."
    When I press "Post"
    Then I should see "Chapter was successfully posted."
      And I should see "rusty"
      And I should not see "brenda"

  Scenario: You should be able to add a co-creator to a chapter if they do not allow it, if they are a co-creator of the work.

    Given the user "thegoodmom" allows co-creators
      And I am logged in as "rusty"
      And I set up the draft "Rusty Has Two Moms"
      And I invite the co-author "thegoodmom"
      And I post the work without preview
    Then I should see "Work was successfully posted."
    When the user "thegoodmom" accepts all co-creator requests
      And I view the work "Rusty Has Two Moms"
    Then I should see "rusty, thegoodmom"
    When the user "thegoodmom" disallows co-creators
      And I post a chapter for the work "Rusty Has Two Moms"
    Then I should see "Chapter has been posted!"
      And I follow "Chapter 2"
      And I should see "Chapter by rusty"
      And I follow "Edit Chapter"
    When I check "Add co-creators?"
      And I fill in "pseud_byline" with "thegoodmom"
      And I press "Post"
    Then I should see "Chapter was successfully updated."
      And I follow "Chapter 2"
      And I follow "Edit Chapter"
      And I should see "Remove Me As Chapter Co-Creator"

  Scenario: You can't add a chapter to a work with too many tags
    Given the user-defined tag limit is 7
      And I am logged in as a random user
      And I post the work "Over the Limit"
      And the work "Over the Limit" has 2 fandom tags
      And the work "Over the Limit" has 2 character tags
      And the work "Over the Limit" has 2 relationship tags
      And the work "Over the Limit" has 2 freeform tags
    When I follow "Add Chapter"
      And I fill in "content" with "this is my second chapter"
      And I press "Post"
    Then I should see "Fandom, relationship, character, and additional tags must not add up to more than 7. Your work has 8 of these tags, so you must remove 1 of them."
    When I view the work "Over the Limit"
    Then I should see "1/1"
      And I should not see "Next Chapter"
