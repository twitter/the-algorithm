@collections @challenges @promptmemes
Feature: Prompt Meme Challenge
  In order to have an archive full of works
  As a humble user
  I want to create a prompt meme and post to it
  
  Scenario: Can create a collection to house a prompt meme
  
  Given I have standard challenge tags setup
  When I set up Battle 12 promptmeme collection
  Then I should be editing the challenge settings
  
  Scenario: Creating a prompt meme has different instructions from a gift exchange
  
  Given I have standard challenge tags setup
  When I set up Battle 12 promptmeme collection
  Then I should see prompt meme options
  
  Scenario: Create a prompt meme
  
  Given I have standard challenge tags setup
  When I create Battle 12 promptmeme
  Then Battle 12 prompt meme should be correctly created

  Scenario: User can see a prompt meme
  
  Given I have Battle 12 prompt meme fully set up
    And I am logged in as a random user
  When I go to the collections page
  Then I should see "Battle 12"
  
  Scenario: Prompt meme is in list of open challenges
  
  Given I have Battle 12 prompt meme fully set up
    And I am logged in as a random user
  When I view open challenges
  Then I should see "Battle 12"

  Scenario: Prompt meme is also in list of open prompt meme challenges
  
  Given I have Battle 12 prompt meme fully set up
    And I am logged in as a random user
  When I view open challenges
    And I follow "Prompt Meme Challenges"
  Then I should see "Battle 12"
  
  Scenario: Past challenge is not in list of open challenges
  
  Given I am logged in as "mod1"
    And I have standard challenge tags setup
  When I set up Battle 12 promptmeme collection
    And I fill in past challenge options
    And I am logged in as "myname1"
  When I view open challenges
  Then I should not see "Battle 12"
  
  Scenario: Future challenge is not in list of open challenges
  
  Given I am logged in as "mod1"
    And I have standard challenge tags setup
  When I set up Battle 12 promptmeme collection
    And I fill in future challenge options
    And I am logged in as "myname1"
  When I view open challenges
  Then I should not see "Battle 12"
  
  Scenario: Can access settings from profile navigation
  
  Given I have Battle 12 prompt meme fully set up
  When I go to "Battle 12" collection's page
    And I follow "Profile"
  Then I should see "Challenge Settings" within "div#dashboard"
  When I follow "Challenge Settings" within "div#dashboard"
  Then I should be editing the challenge settings
  
  Scenario: Can edit settings for a prompt meme
  
  Given I have Battle 12 prompt meme fully set up
    And I am logged in as "mod1"
  When I edit settings for "Battle 12" challenge
  Then I should be editing the challenge settings

  Scenario: Entering a greater number for required prompts than allowed prompts 
  automatically increases the number of allowed promps

  Given I set up Battle 12 promptmeme collection
  When I require 3 prompts
    And I allow 2 prompts
    And I press "Submit"
  Then I should see a success message
  When I edit settings for "Battle 12" challenge
  Then 3 prompts should be required
    And 3 prompts should be allowed

  Scenario: Sign-up being open is shown on profile
  
  Given I have Battle 12 prompt meme fully set up
    And I am logged in as a random user
  When I go to "Battle 12" collection's page
    And I follow "Profile"
  Then I should see "Sign-up: Open"
  
  Scenario: User can see profile descriptions
  
  Given I have Battle 12 prompt meme fully set up
    And I am logged in as "myname1"
  When I go to "Battle 12" collection's page
  When I follow "Profile"
  Then I should see Battle 12 descriptions
  
  Scenario: Sign up for a prompt meme
  
  Given I have Battle 12 prompt meme fully set up
  And I am logged in as "myname1"
  When I go to "Battle 12" collection's page
  Then I should see "Sign Up"
  When I sign up for Battle 12 with combination A
  Then I should see "Sign-up was successfully created"
    And I should see "Prompts (2)"
    And I should see the whole signup
  
  Scenario: Sign up for a prompt meme and miss out some fields
  
  Given I have Battle 12 prompt meme fully set up
    And I am logged in as "myname1"
  When I sign up for "Battle 12" with missing prompts
  Then I should see "Request: Your Request must include exactly 1 fandom tags, but you have included 0 fandom tags in your current Request"
  When I fill in the missing prompt
  Then I should see "Sign-up was successfully created"
  
  Scenario: Correct number of signups is shown in user sidebar
  
  Given I have Battle 12 prompt meme fully set up
    And I am logged in as "myname1"
  When I sign up for Battle 12 with combination A
  When I am on my user page
  Then I should see "Sign-ups (1)"
  
  Scenario: View signups in the dashboard
  
  Given I have Battle 12 prompt meme fully set up
    And I am logged in as "myname1"
  When I sign up for Battle 12 with combination A
  When I am on my signups page
  Then I should see "Battle 12"

  Scenario: Prompt count shows on profile

  Given I have Battle 12 prompt meme fully set up
    And I am logged in as "myname1"
  When I sign up for Battle 12 with combination A
  When I go to "Battle 12" collection's page
    And I follow "Profile"
  Then I should see "Prompts: 2"
    # TODO: Was the claimed prompts count intentionally removed from profile?
    # And I should see "Claimed prompts: 0"

  Scenario: Prompt count shows on collections index

  Given I have Battle 12 prompt meme fully set up
    And I am logged in as "myname1"
  When I sign up for Battle 12 with combination A
  When I go to the collections page
  Then I should see "Prompts: 2"

  Scenario: Sign-ups in the dashboard have correct controls
  
  Given I have Battle 12 prompt meme fully set up
    And I am logged in as "myname1"
  When I sign up for Battle 12 with combination A
  When I am on my signups page
  Then I should see "Edit"
    And I should see "Delete"
  
  Scenario: Edit individual prompt
  
  Given I have Battle 12 prompt meme fully set up
    And I am logged in as "myname1"
  When I sign up for Battle 12 with combination A
  When I view my signup for "Battle 12"
  When I follow "Edit Prompt"
  Then I should see single prompt editing
  And I should see "Edit Sign-up"
  When I uncheck "Stargate Atlantis"
    And I press "Update"
  Then I should see "Sorry! We couldn't save this request because:"
    And I should see "Your Request must include exactly 1 fandom tags"
 
 Scenario: Add one new prompt to existing signup
  
  Given I have Battle 12 prompt meme fully set up
    And I am logged in as "myname1"
  When I sign up for Battle 12 with combination A
    And I add a new prompt to my signup
  Then I should see "Prompt was successfully added"
    And I should see "Request 3"
    And I should see "My extra tag"
  
  Scenario: Sort prompts by date
  
  Given I have Battle 12 prompt meme fully set up
  When I am logged in as "myname1"
  When I sign up for Battle 12 with combination A
  And I am logged in as "myname2"
  When I sign up for Battle 12 with combination B
  When I view prompts for "Battle 12"
    And I follow "Date"
  Then I should see "Something else weird"
  
  Scenario: Sort prompts by fandom doesn't give error page
  
  Given I have Battle 12 prompt meme fully set up
  When I am logged in as "myname1"
  When I sign up for Battle 12 with combination A
  And I am logged in as "myname2"
  When I sign up for Battle 12 with combination B
  When I view prompts for "Battle 12"
    And I follow "Fandom 1"
  Then I should see "Something else weird"
  
  Scenario: Sign up for a prompt meme with no tags
  
  Given I have no-column prompt meme fully set up
  When I am logged in as "myname1"
  When I sign up for Battle 12 with combination E
  Then I should see "Sign-up was successfully created"
  
  Scenario: If there are no fandoms, prompt info on claims should show description or URL
  
  Given I have no-column prompt meme fully set up
  When I am logged in as "myname1"
  When I sign up for Battle 12 with combination E
  When I claim a prompt from "Battle 12"
  # TODO: check design: regular user doesn't get link to unposted claims anymore
  # When I view unposted claims for "Battle 12"
  Then I should see "Weird description"
  
  Scenario: Sort by fandom shouldn't show when there aren't any fandoms

  Given I have no-column prompt meme fully set up
  When I am logged in as "myname1"
    And I sign up for Battle 12 with combination E
    And I view prompts for "Battle 12"
  # TODO: We need to check the display for fandomless memes
  Then I should not see "Fandom 1"

  Scenario: Claim a prompt and view claims on main page and user page
  
  Given I have Battle 12 prompt meme fully set up
  When I am logged in as "myname1"
  When I sign up for Battle 12 with combination B
  And I am logged in as "myname4"
  And I claim a prompt from "Battle 12"
  Then I should see a prompt is claimed

  Scenario: Claim count shows on profile?

  Given I have Battle 12 prompt meme fully set up
    And I am logged in as "myname1"
  When I sign up for Battle 12 with combination A
    And I claim a prompt from "Battle 12"
  When I go to "Battle 12" collection's page
    And I follow "Profile"
  Then I should see "Prompts: 2"
    # TODO: have these been removed by design or by accident?
    # And I should see "Claimed prompts: 1"
  
  Scenario: Mod can view signups
  
  Given I have Battle 12 prompt meme fully set up
    And everyone has signed up for Battle 12
  When I am logged in as "mod1"
    And I go to "Battle 12" collection's page
    And I follow "Prompts (8)"
  Then I should see correct signups for Battle 12

  Scenario: Mod can delete signups

  Given I have Battle 12 prompt meme fully set up
  When I am logged in as "myname1"
    And I sign up for Battle 12 with combination B
  When I am logged in as "mod1"
    And I go to "Battle 12" collection's page
    And I follow "Prompts ("
    And I should see "Prompts for Battle 12"
  When I follow "Delete Sign-up"
  Then I should see "Challenge sign-up was deleted."
    And I should see "Prompts (0)"

  Scenario: Sign up with both prompts anon
  
  Given I have Battle 12 prompt meme fully set up
  When I am logged in as "myname1"
  When I sign up for Battle 12 with combination B
  Then I should see "Sign-up was successfully created"
  
  Scenario: Sign up with neither prompt anon
  
  Given I have Battle 12 prompt meme fully set up
  When I am logged in as "myname1"
  When I sign up for Battle 12 with combination A
  Then I should see "Sign-up was successfully created"
  
  Scenario: Sign up with one anon prompt and one not
  
  Given I have Battle 12 prompt meme fully set up
  When I am logged in as "myname1"
  When I sign up for Battle 12 with combination C
  Then I should see "Sign-up was successfully created"
  
  Scenario: User has more than one pseud on signup form
  
  Given "myname1" has the pseud "othername"
  Given I have Battle 12 prompt meme fully set up
  When I am logged in as "myname1"
  When I start to sign up for "Battle 12"
  Then I should see "othername"
  
  Scenario: User changes pseud on a challenge signup
  
  Given "myname1" has the pseud "othername"
  Given I have Battle 12 prompt meme fully set up
  When I am logged in as "myname1"
  When I sign up for Battle 12 with combination A
  Then I should see "Sign-up was successfully created"
    And I should see "Sign-up for myname1"
  When I edit my signup for "Battle 12"
  Then I should see "othername"
  When I select "othername" from "challenge_signup_pseud_id"
    # two forms in this page, must specify which button to press
    And I press "Update" 
  Then I should see "Sign-up was successfully updated"
  Then I should see "Sign-up for othername (myname1)"
  
  Scenario: Add more requests button disappears correctly from signup show page
  
  Given I am logged in as "mod1"
    And I have standard challenge tags setup
  When I set up a basic promptmeme "Battle 12"
    And I follow "Challenge Settings"
  When I fill in multi-prompt challenge options
  When I sign up for Battle 12 with combination D
    And I add prompt 3
  Then I should see "Add Prompt"
  When I add prompt 4
  Then I should not see "Add Prompt"
    
  Scenario: Remove prompt button shouldn't show on Sign-ups
  
  Given I have Battle 12 prompt meme fully set up
  When I am logged in as "myname1"
  When I sign up for Battle 12 with combination A
  When I am on my user page
  When I follow "Sign-ups"
  Then I should not see "Remove prompt"
  
  Scenario: Mod can't edit signups
  
  Given I have Battle 12 prompt meme fully set up
  When I am logged in as "myname1"
  When I sign up for Battle 12 with combination A
  When I am logged in as "mod1"
    And I view prompts for "Battle 12"
  Then I should not see "Edit Sign-up"
 
  Scenario: Mod cannot edit someone else's prompt
  
  Given I have Battle 12 prompt meme fully set up
  When I am logged in as "myname1"
  When I sign up for Battle 12 with combination C
  When I am logged in as "mod1"
    # The next step just takes you to the 'Prompts' page
  When I edit the first prompt
  Then I should not see "Edit Prompt"
  
  Scenario: Claim an anon prompt
  
  Given I have Battle 12 prompt meme fully set up
  When I am logged in as "myname4"
  When I sign up for Battle 12 with combination B
  When I go to "Battle 12" collection's page
    And I follow "Prompts ("
  When I press "Claim"
  Then I should see "New claim made."
    And I should see "by Anonymous"
    And I should not see "myname" within "#main"

  Scenario: Prompts are counted up correctly
  
  Given I have Battle 12 prompt meme fully set up
  When I am logged in as "myname1"
  When I sign up for Battle 12 with combination A
  Then I should see "Prompts (2)"
  When I am logged in as "myname2"
  When I sign up for Battle 12 with combination B
  Then I should see "Prompts (4)"
  
  Scenario: Claims are shown to mod
  
  Given I have Battle 12 prompt meme fully set up
  Given everyone has signed up for Battle 12
  When I claim a prompt from "Battle 12"
  When I close signups for "Battle 12"
  Then claims are shown
  
  Scenario: Claims are hidden from ordinary user
  
  Given I have Battle 12 prompt meme fully set up
  Given everyone has signed up for Battle 12
  When I claim a prompt from "Battle 12"
  When I close signups for "Battle 12"
  When I am logged in as "myname4"
  Then I should not see "Unposted Claims"
  # TODO: they got really hidden, since ordinary user can't get to that page at all
  # Then claims are hidden

  Scenario: User cannot see unposted claims to delete
  
  Given I have Battle 12 prompt meme fully set up
  Given everyone has signed up for Battle 12
  When I claim a prompt from "Battle 12"
  When I am logged in as "myname1"
  Then I should not see "Unposted Claims"
  
  Scenario: User can delete their own claim
  
  Given I have Battle 12 prompt meme fully set up
  Given everyone has signed up for Battle 12
  When I claim a prompt from "Battle 12"
    And I go to "Battle 12" collection's page
    And I follow "My Claims"
    And I follow "Drop Claim"
  Then I should see "Your claim was deleted."
  When I go to "Battle 12" collection's page
  Then I should not see "My Claims"
  
  Scenario: User can drop a claim from the prompts page
  
  Given I have Battle 12 prompt meme fully set up
  Given everyone has signed up for Battle 12
  When I claim a prompt from "Battle 12"
    And I go to "Battle 12" collection's page
    And I follow "Prompts"
  Then I should see "Drop Claim"

  Scenario: User can't delete another user's claim

  Given I have Battle 12 prompt meme fully set up
  Given everyone has signed up for Battle 12
  When I claim a prompt from "Battle 12"
  When I am logged in as "otheruser"
    And I go to "Battle 12" collection's page
    And I follow "Prompts"
  Then I should not see "Drop Claim"

  Scenario: User can delete their own claim from the user claims list

  Given I have Battle 12 prompt meme fully set up
  Given everyone has signed up for Battle 12
  When I claim a prompt from "Battle 12"
  When I am on my user page
    And I follow "Claims"
  Then I should see "Drop Claim"
  When I follow "Drop Claim"
  Then I should see "Your claim was deleted."
  # confirm claim no longer exists
  When I go to "Battle 12" collection's page
  Then I should not see "My Claims"

  Scenario: Mod or owner can delete a claim from the user claims list
  
  Given I have Battle 12 prompt meme fully set up
  Given everyone has signed up for Battle 12
  When I claim a prompt from "Battle 12"
  When I am logged in as "mod1"
    And I view unposted claims for "Battle 12"
  Then I should see "Delete"
  When I follow "Delete"
  Then I should see "The claim was deleted."

  Scenario: User can't claim the same prompt twice
  
  Given I have Battle 12 prompt meme fully set up
  When I am logged in as "myname1"
  When I sign up for Battle 12 with combination B
    And I am logged in as "myname4"
    And I claim two prompts from "Battle 12"
    And I view prompts for "Battle 12"
    # TODO: Refactor this test once we have a new Capybara version so that we look for .exact(Claim)
  Then I should see "Drop Claim"
