@admin
Feature: Admin Actions to Post FAQs
  As an an admin
  I want to be able to manage the archive FAQ

  Scenario: Post and edit a FAQ
    When I go to the archive_faqs page
    Then I should see "Some commonly asked questions about the Archive are answered here"
      And I should not see "Some text"
    When I am logged in as an admin
      And I follow "Admin Posts"
      And I follow "Archive FAQ" within "#header"
    Then I should not see "Some text"
    When I follow "New FAQ Category"
      And I fill in "Question*" with "What is AO3?"
      And I fill in "Answer*" with "Some text, that is sufficiently long to pass validation."
      And I fill in "Category name*" with "New subsection"
      And I fill in "Anchor name*" with "whatisao3"
      And I press "Post"
    Then I should see "ArchiveFaq was successfully created"
    When I go to the archive_faqs page
      And I follow "New subsection"
    Then I should see "Some text, that is sufficiently long to pass validation" within ".userstuff"
    When I follow "Edit"
      And I fill in "Answer*" with "New Content, yay"
      And I press "Post"
    Then I should see "New Content, yay"
      And I should not see "Some text"

  Scenario: Post a translated FAQ for a locale, then change the locale's code.
    Given basic languages
      And I am logged in as a "translation" admin

    # Post "en" FAQ
    When I go to the archive_faqs page
      And I follow "New FAQ Category"
      And I fill in "Question*" with "What is AO3?"
      And I fill in "Answer*" with "Some text, that is sufficiently long to pass validation."
      And I fill in "Category name*" with "New subsection"
      And I fill in "Anchor name*" with "whatisao3"
      And I press "Post"
    Then I should see "ArchiveFaq was successfully created"

    # Translate FAQ to "de"
    When I follow "Archive FAQ"
      And I select "Deutsch" from "Language:"
      And I press "Go" within "div#inner.wrapper"
      And I follow "Edit"
      And I fill in "Question*" with "Was ist AO3?"
      And I fill in "Answer*" with "Einige Text, das ist lang genug, um die Überprüfung bestanden."
      And I fill in "Category name*" with "Neuer Abschnitt"
      And I check "Question translated"
      And I press "Post"
    Then I should see "ArchiveFaq was successfully updated."
      And I should not see "New subsection"
      And I should see "Neuer Abschnitt"
      And I should see "Was ist AO3?"
      And I should see "Einige Text"

    # Change locale "de" to "ger"
    When I go to the locales page
      And I follow "Edit"
    Then I should see "Deutsch" in the "Name" input
    When I fill in "locale_iso" with "ger"
      And I press "Update Locale"
    Then I should see "Your locale was successfully updated."
      And I should see "Deutsch ger"

    # The session preference is "de", which no longer exists; the default locale should be used
    When I go to the archive_faqs page
    Then "English (US)" should be selected within "Language:"

    # Log out and view FAQs; the default locale should be used
    When I log out
      And I go to the archive_faqs page
      And I follow "New subsection"
    Then I should see "What is AO3?"
      And I should see "Some text"

    # Select "ger"
    When I go to the archive_faqs page
      And I select "Deutsch" from "Language:"
      And I press "Go" within "div#inner.wrapper"
      And I follow "Neuer Abschnitt"
    Then I should see "Was ist AO3?"
      And I should see "Einige Text"
