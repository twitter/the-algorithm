@admin @comments
Feature: Admin Actions to Post News
  In order to post news items
  As an an admin
  I want to be able to use the Admin Posts screen

  Scenario: Must be authorized to post
    Given I am logged in as a "tag_wrangling" admin
    When I go to the admin-posts page
    Then I should not see "Post AO3 News"

  Scenario: Make an admin post
    Given I am logged in as a "communications" admin
    When I make an admin post
    Then I should see "Admin Post was successfully created."

  Scenario: Receive comment notifications for comments posted to an admin post
    Given I have posted an admin post

    # regular user replies to admin post
    When I am logged in as "happyuser"
      And I go to the admin-posts page
    When all emails have been delivered
      And I follow "Default Admin Post"
      And I fill in "Comment" with "Excellent, my dear!"
      And I press "Comment"
    # notification to the admin list for admin post
    Then 1 email should be delivered to "admin@example.org"
      And the email should contain "Excellent"

    # regular user edits their comment
    When all emails have been delivered
      And I follow "Edit"
      And I press "Update"
    # notification to the admin list for admin post
    Then 1 email should be delivered to "admin@example.org"

  Scenario: Evil user can impersonate admin in comments
  # However, they can't use an icon, so the admin's icon is the guarantee that they're real
  # also their username will be plain text and not a link

    Given I have posted an admin post
    When I am logged in as "happyuser"
      And I go to the admin-posts page
    When I follow "Default Admin Post"
      And I fill in "Comment" with "Excellent, my dear!"
      And I press "Comment"
    When I log out
      And I go to the admin-posts page
      And I follow "Default Admin Post"
      And I fill in "Comment" with "Behold, ye mighty, and despair!"
      And I fill in "Guest name" with "admin"
      And I fill in "Guest email" with "admin@example.com"
      And I press "Comment"
    Then I should see "Comment created!"
      And I should see "admin"
      And I should see "Behold, ye mighty, and despair!"

  Scenario: User views RSS of admin posts

    Given I have posted an admin post
    When I am logged in
      And I go to the admin-posts page
    Then I should see "RSS Feed"
    When I follow "RSS Feed"
    Then I should see "Default Admin Post"

  Scenario: User views RSS of translated admin posts
    Given I have posted an admin post
      And basic languages
      And I am logged in as a "translation" admin
    When I make a translation of an admin post
      And I am logged in as "ordinaryuser"
      And I go to the admin-posts page
      And I select "Deutsch" from "Language:"
      And I press "Go" within "div#inner.wrapper"
      And I follow "RSS Feed"
    Then I should see "Deutsch Ankuendigung"

  Scenario: Make a translation of an admin post
    Given I have posted an admin post
      And basic languages
      And I am logged in as a "translation" admin
    When I make a translation of an admin post
      And I am logged in as "ordinaryuser"
    Then I should see a translated admin post

  Scenario: Make a translation of an admin post that doesn't exist
    Given basic languages
      And I am logged in as a "translation" admin
    When I make a translation of an admin post
    Then I should see "Sorry! We couldn't save this admin post because:"
      And I should see "Translated post does not exist"
      And the translation information should still be filled in

  Scenario: Make a translation of an admin post stop being a translation
    Given I have posted an admin post
      And basic languages
      And I am logged in as a "translation" admin
      And I make a translation of an admin post
    When I follow "Edit Post"
      And I fill in "Translation of" with ""
      And I press "Post"
    When I am logged in as "ordinaryuser"
    Then I should not see a translated admin post

  Scenario: Log in as an admin and create an admin post with tags
    Given I am logged in as a "communications" admin
    When I follow "Admin Posts"
      And I follow "Post AO3 News"
    Then I should see "New AO3 News Post"
      And I should see "Comment permissions from the selected post will replace any permissions selected on this page."
      And I should see "Tags from the selected post will replace any tags entered on this page."
    When I fill in "admin_post_title" with "Good news, everyone!"
      And I fill in "content" with "I've taught the toaster to feel love."
      And I fill in "Tags" with "quotes, futurama"
      And I choose "No one can comment"
      And I press "Post"
    Then I should see "Admin Post was successfully created."
      And I should see "toaster" within "div.admin.home"
      And I should see "futurama" within "dd.tags"

  Scenario: Admin posts can be filtered by tags and languages
    Given I have posted an admin post with tags "quotes, futurama"
      And basic languages
      And I am logged in as a "translation" admin
    When I make a translation of an admin post
      And I am logged in as "ordinaryuser"
    Then I should see a translated admin post with tags "quotes, futurama"

    When I follow "News"
    Then "futurama" should be an option within "Tag"
      And "quotes" should be an option within "Tag"
      And "Deutsch" should be an option within "Language"
      And "English" should be selected within "Language"

    # No tag selected
    When I press "Go"
    Then I should see "Content of the admin post"
      And I should not see "Deutsch Woerter"
      And "English" should be selected within "Language"

    When I select "quotes" from "Tag"
      And I select "Deutsch" from "Language"
      And I press "Go"
    Then I should not see "Content of the admin post"
      And I should see "Deutsch Woerter"
      And "quotes" should be selected within "Tag"
      And "Deutsch" should be selected within "Language"

  Scenario: Translation of an admin post keeps tags of original post
    Given I have posted an admin post with tags "original1, original2"
      And basic languages
      And I am logged in as a "translation" admin
    When I make a translation of an admin post with tags "ooops"
    Then I should see "original1 original2" within "dd.tags"
     And I should not see "ooops"
    When I follow "Edit Post"
    Then I should not see the input with id "admin_post_tag_list"
     And I should not see "Tags from the selected post will replace any tags entered on this page."
    When I go to the admin-posts page
    Then "ooops" should not be an option within "Tag"
    When I follow "Edit"
    Then I should see the input with id "admin_post_tag_list"
    When I fill in "Tags" with "updated1, updated2"
     And I press "Post"
     And I am logged in as "ordinaryuser"
    Then I should see a translated admin post with tags "updated1, updated2"

  Scenario: If an admin post has characters like & and < and > in the title, the escaped version will not show on the various admin post pages
    Given I am logged in as a "communications" admin
    When I follow "Admin Posts"
      And I follow "Post AO3 News"
      And I fill in "admin_post_title" with "App News & a <strong> Warning"
      And I fill in "content" with "We're delaying it a week for every question we get."
    When I press "Post"
    Then I should see the page title "App News & a <strong> Warning"
      And I should not see "App News &amp; a &lt;strong&gt; Warning"
    When I go to the admin-posts page
    Then I should see "App News & a <strong> Warning"
      And I should not see "App News &amp; a &lt;strong&gt; Warning"
    When I go to the home page
    Then I should see "App News & a <strong> Warning"
      And I should not see "App News &amp; a &lt;strong&gt; Warning"
    When I log out
      And I go to the admin-posts page
    Then I should see "App News & a <strong> Warning"
      And I should not see "App News &amp; a &lt;strong&gt; Warning"

  Scenario: Admin post should be shown on the homepage
    Given I have posted an admin post
    When I am on the homepage
    Then I should see "News"
      And I should see "All News"
      And I should see "Default Admin Post"
      And I should see "Published:"
      And I should see "Comments:"
      And I should see "Content of the admin post."
      And I should see "Read more..."
    When I follow "Read more..."
    Then I should see "Default Admin Post"
      And I should see "Content of the admin post."

  Scenario: Admin posts without paragraphs should have placeholder preview text on the homepage
    Given I have posted an admin post without paragraphs
    When I am on the homepage
    Then I should see "Admin Post Without Paragraphs"
      And I should see "No preview is available for this news post."

  Scenario: Edits to an admin post should appear on the homepage
    Given I have posted an admin post without paragraphs
      And I am logged in as a "communications" admin
    When I go to the admin-posts page
      And I follow "Edit"
      And I fill in "admin_post_title" with "Edited Post"
      And I fill in "content" with "<p>Look! A preview!</p>"
      And I press "Post"
    When I am on the homepage
    Then I should see "Edited Post"
      And I should see "Look! A preview!"
      And I should not see "Admin Post Without Paragraphs"
      And I should not see "No preview is available for this news post."

  Scenario: A deleted admin post should be removed from the homepage
    Given I have posted an admin post
      And I am logged in as a "communications" admin
    When I go to the admin-posts page
      And I follow "Delete"
    When I go to the homepage
    Then I should not see "Default Admin Post"

  Scenario: Log in as an admin and create an admin post in a rtl (right-to-left) language
    Given I am logged in as a "communications" admin
      And Persian language
    When I follow "Admin Posts"
      And I follow "Post AO3 News"
      Then I should see "New AO3 News Post"
    When I fill in "admin_post_title" with "فارسی"
      And I fill in "content" with "چیزهایی هست که باید در حین ایجاد یک گزارش از آنها آگاه باشید"
      And I select "Persian" from "Choose a language"
      And I press "Post"
    Then I should see "Admin Post was successfully created."
      And I should see "باشید" within "div.admin.home div.userstuff"
      And the user content should be shown as right-to-left
