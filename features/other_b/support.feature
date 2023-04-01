Feature: Filing a support request
  In order to get help
  As a confused user
  I want to file a support request

  Scenario: Filling a support request
  
  Given I am logged in as "puzzled"
  And basic languages
  When time is frozen at 14/3/2022
  When I follow "Support & Feedback"
  When I select "Deutsch" from "feedback_language"
    And I fill in "Brief summary" with "Just a brief note"
    And I fill in "Your question or problem" with "Men have their old boys' network, but we have the OTW. You guys rock!"
    And all emails have been delivered
    And I press "Send"
  Then I should see "Your message was sent to the Archive team - thank you!"
    And 1 email should be delivered
    And the email should contain "We're working hard to reply to everyone, and we'll respond to you as soon as we can."
    And the email should contain "If you have additional questions or information"
    And the email should contain "Sent at Mon, 14 Mar 2022 12:00:00 \+0000"
  When I follow "Support & Feedback"
    And I fill in "Brief summary" with "you suck"
    And I fill in "Your question or problem" with "blah blah blah"
    And I fill in "Your email (required)" with "test@archiveofourown.org"
    And I select "Deutsch" from "feedback_language"
    And all emails have been delivered
    And I press "Send"
  Then I should see "Your message was sent to the Archive team - thank you!"
    And 1 email should be delivered

  Scenario: Not logged in, with and without email
  
  When I am on the home page
    And basic languages
    And I follow "Support & Feedback"
  When I select "Deutsch" from "feedback_language"
    And I fill in "Brief summary" with "Just a brief note"
    And I fill in "Your question or problem" with "Men have their old boys' network, but we have the OTW. You guys rock!"
    And I fill in "Your email (required)" with ""
    And all emails have been delivered
    And I press "Send"
  Then I should see "Email should look like an email address."
    And "Deutsch" should be selected within "Select language (required)"
    And I fill in "Your email (required)" with "test@archiveofourown.org"
    And I press "Send"
  Then I should see "Your message was sent to the Archive team - thank you!"
    And 1 email should be delivered
    
