@works
Feature: Parsing HTML

  # tests for parsing only are in spec/lib/html_cleaner_spec.rb

  Scenario: Editing a work and saving it without changes should preserve the same content 
  When I am logged in as "newbie" with password "password"
    And I set up the draft "My Awesome Story"
    And I fill in "content" with 
    """
    This is paragraph 1.

    This is paragraph 2.
    """
    And I press "Preview"
  Then I should see "Preview"
    And I should see the text with tags "<p>This is paragraph 1.</p><p>This is paragraph 2.</p>"
  When I press "Post"
   And I follow "Edit"
   And I press "Preview"
  Then I should see the text with tags "<p>This is paragraph 1.</p><p>This is paragraph 2.</p>"

  Scenario: HTML Parser should kick in
  When I am logged in as "newbie" with password "password"
    And I set up the draft "My Awesome Story"
    And I fill in "content" with 
    """
    A paragraph

    Another paragraph.
    """
    And I press "Preview"
  Then I should see "Preview"
    And I should see the text with tags "<p>A paragraph</p><p>Another paragraph.</p>" 

  Scenario: Work notes and content HTML can have classes and they are kept when editing after previewing
  Given I am logged in as a random user
    And I set up the draft "Classy Work"
  When I fill in "Summary" with "<p class='myclass'>Text</p>"
    And I fill in "Notes" with "<p class='note'>Text</p>"
    And I fill in "End Notes" with "<span class='keep-me'>Text</span>"
    And I fill in "content" with "<p class='size-10'><img src='britney.gif' alt='Britney Spears' />You better work</p>"
    And I press "Preview"
  Then I should see "Draft was successfully created."
    And I should see the image "src" text "britney.gif"
    And I should see the image "alt" text "Britney Spears"
  When I press "Edit"
  Then the "Summary" field should not contain "myclass"
    And the "Notes" field should contain "note"
    And the "End Notes" field should contain "keep-me"
    And the "content" field should contain "size-10"
  When I press "Post"
  Then I should see "Work was successfully posted."
    And I should see the image "src" text "britney.gif"

  Scenario: Chapter notes and content HTML keep classes when previewing before posting
  Given I am logged in as a random user
    And I post the work "Classy Multichapter Work"
    And a chapter is set up for "Classy Multichapter Work"
  When I fill in "Chapter Summary" with "<p class='summary classes'>Text</p>"
    And I fill in "Notes" with "<p class='note'>Text</p>"
    And I fill in "End Notes" with "<span class='keep-me'>Text</span>"
    And I fill in "content" with "<div class='elaborate formatting'><p>The continuation of my <a href='works/123'>masterpiece</a></p></div>"
    And I press "Preview"
  Then I should see "This is a draft chapter in a posted work."
  When I press "Post"
  Then I should see "Chapter was successfully posted."
    And I should see the text with tags '<p>The continuation of my <a href="works/123" rel="nofollow">masterpiece</a></p>'
  When I follow "Edit Chapter"
  Then the "Chapter Summary" field should not contain "summary classes"
    And the "Notes" field should contain "note"
    And the "End Notes" field should contain "keep-me"
    And the "content" field should contain "elaborate formatting"

  Scenario: Can't use classes in comment content
  Given the work "Generic Work"
    And I am logged in as "commenter"
    And I view the work "Generic Work"
  When I fill in "Comment" with "<p class='strip me'>Hi there!</p>"
    And I press "Comment"
  Then I should see "Comment created!"
  When I follow "Edit"
  Then the "Comment" field should not contain "strip me"

Scenario: Can't use classes in a bookmark note
  Given the work "Really Good Thing"
    And I am logged in as "bookmarker"
  When I bookmark the work "Really Good Thing" with the note "My <span='remove-me'>best yet</span>"
    And I edit the bookmark for "Really Good Thing"
  Then the "Notes" field should not contain "remove-me"
