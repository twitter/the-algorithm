@works
Feature: Sanitizing HTML

  Scenario: Sanitizer should kick in

    When I am logged in as "newbie" with password "password"
    And I set up the draft "My Awesome Story"
    And I fill in "content" with 
    """
    The quick brown fox jumps over the lazy dog.
    <!-- #exec cmd=\"/bin/echo \" -->
    <script src=http://ha.ckers.org/xss.js></script>
    """
    And I press "Preview"
    Then I should see "Preview"
    And I should not see the text with tags '<!-- #exec cmd='
    And I should not see the text with tags '<script src=http://ha.ckers.org/xss.js></script>'


  Scenario: XSS hacks in works should be blocked by sanitizing

  # TODO
  #
  # I moved almost all of these to spec/lib/html_cleaner_spec.rb The
  # remaining two below should go there, too, but I'm not quite sure
  # what their intention is. The sanitiser doesn't change them, and I
  # don't get the reason behind the "should not find XSS" test. --
  # Rebecca

  Given basic tags
    And I am logged in as "newbie" with password "password"
  When I go to the new work page
  Then I should see "Post New Work"
    And I select "Not Rated" from "Rating"
    And I check "No Archive Warnings Apply"
    And I select "English" from "Choose a language"
    And I fill in "Fandoms" with "Supernatural"
    And I fill in "Work Title" with "All Hell Breaks Loose"
    And I fill in "content" with 'BODY{-moz-binding:url("http://ha.ckers.org/xssmoz.xml#xss")}'
    And I press "Preview"
  Then I should see "Preview"
    And I should not see "XSS"
    And I should see "BODY{-moz-binding:url("
  When I press "Edit"
    And I fill in "content" with "behavior: url(xss.htc);"
    And I press "Preview"
  Then I should see "Preview"
    And I should not see "XSS"
    And I should see "behavior: url(xss.htc);"

