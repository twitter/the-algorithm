@collections @challenges @promptmemes
Feature: Prompt Meme Challenge
  In order to have an archive full of works
  As a humble user
  I want to create a prompt meme and post to it

  Scenario: Claim two prompts by the same person in one challenge

  Given I have Battle 12 prompt meme fully set up
  When I am logged in as "myname2"
  When I sign up for Battle 12 with combination B
  # 1st prompt SG-1, 2nd prompt SGA, both anon
  When I am logged in as "myname1"
    And I claim two prompts from "Battle 12"
    And I view prompts for "Battle 12"
  # all prompts have been claimed - check it worked
  # TODO: find a better way to check that it worked, since 'Drop Claim' includes the word 'Claim', and there is no table anymore, so no tbody
  # Then I should not see "Claim" within "tbody"
  # TODO: check that they are not intermittent anymore
  When I start to fulfill my claim
  Then I should find a checkbox "High School AU SG1 in Battle 12 (Anonymous)"
    And I should find a checkbox "random SGA love in Battle 12 (Anonymous)"
    And the "High School AU SG1 in Battle 12 (Anonymous)" checkbox should not be checked
    And the "random SGA love in Battle 12 (Anonymous)" checkbox should be checked

  Scenario: Claim two prompts by different people in one challenge

  Given I have single-prompt prompt meme fully set up
  When I am logged in as "sgafan"
    And I sign up for "Battle 12" with combination SGA
  When I am logged in as "sg1fan"
    And I sign up for "Battle 12" with combination SG-1
  When I am logged in as "writer"
    And I claim two prompts from "Battle 12"
  When I start to fulfill my claim
  Then I should find a checkbox "SG1 love in Battle 12 (sg1fan)"
    And I should find a checkbox "SGA love in Battle 12 (sgafan)"
  # TODO: check that they are not intermittent anymore
    And the "SGA love in Battle 12 (sgafan)" checkbox should not be checked
    And the "SG1 love in Battle 12 (sg1fan)" checkbox should be checked

  Scenario: Claim two prompts by the same person in one challenge, one is anon

  Given I have Battle 12 prompt meme fully set up
  When I am logged in as "myname2"
  When I sign up for Battle 12
  # 1st prompt "something else weird" and titled "crack", 2nd prompt anon
  When I am logged in as "myname1"
    And I claim two prompts from "Battle 12"
    And I view prompts for "Battle 12"
  # anon as claims are in reverse date order
  When I start to fulfill my claim
  Then I should find a checkbox "Untitled Prompt in Battle 12 (Anonymous)"
    And I should find a checkbox "crack in Battle 12 (myname2)"
    And the "Untitled Prompt in Battle 12 (Anonymous)" checkbox should be checked
    And the "crack in Battle 12 (myname2)" checkbox should not be checked

  Scenario: User claims two prompts in one challenge and fulfills one of them
  # TODO: When SPRs get merged, make this check that 'prompt' is a link
  # and that it shows the correct prompt, or whatever
  Given I have Battle 12 prompt meme fully set up
  When I am logged in as "myname2"
  When I sign up for Battle 12 with combination B
  # 1st prompt SG-1, 2nd prompt SGA, both anon
  When I am logged in as "myname1"
    And I claim a prompt from "Battle 12"
    # SGA as it's in reverse order
    And I claim a prompt from "Battle 12"
    # SG-1
  # SGA seems to be the first consistently
  When I start to fulfill my claim
  Then the "High School AU SG1 in Battle 12 (Anonymous)" checkbox should not be checked
    And the "random SGA love in Battle 12 (Anonymous)" checkbox should be checked
  When I press "Preview"
    And I press "Post"
  When I view the work "Fulfilled Story"
  Then I should see "Stargate Atlantis"

  Scenario: User claims two prompts in one challenge and fufills both of them at once

  Given I have Battle 12 prompt meme fully set up
  When I am logged in as "myname2"
  When I sign up for Battle 12
  # 1st prompt anon, 2nd prompt non-anon
  When I am logged in as "myname1"
    And I claim a prompt from "Battle 12"
    And I claim a prompt from "Battle 12"
    And I view prompts for "Battle 12"
  When I start to fulfill my claim
  # the anon prompt will already by checked
    And I check "crack in Battle 12 (myname2)"
    And I press "Preview"
    And I press "Post"
  When I view the work "Fulfilled Story"
  # fandoms are not filled in automatically anymore, so we check that both prompts are marked as filled by having one anon and one non-anon
  Then I should see "In response to a prompt by Anonymous"
    And I should see "In response to a prompt by myname2"

  Scenario: User claims two prompts in different challenges and fulfills both of them at once
  # TODO

  Scenario: Sign up for several challenges and see Sign-ups are sorted

  Given I have Battle 12 prompt meme fully set up
  When I set up a basic promptmeme "Battle 13"
  When I set up an anon promptmeme "Battle 14" with name "anonmeme"
  When I am logged in as "prolific_writer"
  When I sign up for "Battle 12" fixed-fandom prompt meme
  When I sign up for "Battle 13" many-fandom prompt meme
  When I sign up for "Battle 14" many-fandom prompt meme
  When I am on my user page
    And I follow "Sign-ups"
  # TODO

  Scenario: User is participating in a prompt meme and a gift exchange at once, clicks "Post to fulfill" on the prompt meme and sees the right boxes ticked

  Given I have created the gift exchange "My Gift Exchange"
    And I open signups for "My Gift Exchange"
    And everyone has signed up for the gift exchange "My Gift Exchange"
    And I have generated matches for "My Gift Exchange"
    And I have sent assignments for "My Gift Exchange"
  Given I have Battle 12 prompt meme fully set up
    And everyone has signed up for Battle 12
  When I am logged in as "myname3"
    And I claim a prompt from "Battle 12"
  When I start to fulfill my claim
  Then the "canon SGA love in Battle 12 (myname4)" checkbox should be checked
    And the "My Gift Exchange (myname2)" checkbox should not be checked
    And the "canon SGA love in Battle 12 (myname4)" checkbox should not be disabled
    And the "My Gift Exchange (myname2)" checkbox should not be disabled

  Scenario: User posts to fulfill direct from Post New (New Work)

  Given I have Battle 12 prompt meme fully set up
    And everyone has signed up for Battle 12
  When I am logged in as "myname3"
    And I claim a prompt from "Battle 12"
    And I follow "New Work"
  Then the "canon SGA love in Battle 12 (myname4)" checkbox should not be checked
    And the "canon SGA love in Battle 12 (myname4)" checkbox should not be disabled

  Scenario: User is participating in a prompt meme and a gift exchange at once, clicks "Post to fulfill" on the prompt meme and then changes their mind and fulfills the gift exchange instead

  Given I have Battle 12 prompt meme fully set up
    And everyone has signed up for Battle 12
  Given I have created the gift exchange "My Gift Exchange"
    And I open signups for "My Gift Exchange"
    And everyone has signed up for the gift exchange "My Gift Exchange"
    And I have generated matches for "My Gift Exchange"
    And I have sent assignments for "My Gift Exchange"
  When I am logged in as "myname3"
    And I claim a prompt from "Battle 12"
  When I start to fulfill my claim
  When I check "My Gift Exchange (myname2)"
    And I uncheck "canon SGA love in Battle 12 (myname4)"
    And I fill in "Post to Collections / Challenges" with ""
    And I press "Post"
  Then I should see "My Gift Exchange"
    And I should not see "Battle 12"
    And I should not see "This work is part of an ongoing challenge and will be revealed soon! You can find details here: My Gift Exchange"

  Scenario: Mod can claim a prompt like an ordinary user

  Given I have Battle 12 prompt meme fully set up
  Given everyone has signed up for Battle 12
  When I am logged in as "mod1"
  When I claim a prompt from "Battle 12"
  Then I should see "New claim made."

  Scenario: Mod can still see anonymous claims after signup is closed

  Given I have Battle 12 prompt meme fully set up
  Given everyone has signed up for Battle 12
  When I am logged in as "myname4"
  When I claim a prompt from "Battle 12"
  When I fulfill my claim
  When I am logged in as "mod1"
  When I claim a prompt from "Battle 12"
  When I close signups for "Battle 12"
  When I am logged in as "mod1"
  When I am on "Battle 12" collection's page
    And I follow "Unposted Claims ("
  Then I should see "claimed by mod"
    And I should see "by myname4"
    And I should see "Stargate Atlantis"

  Scenario: check that claims can't be viewed even after challenge is revealed
  # TODO: Find a way to construct the link to a claim show page for someone who shouldn't be able to see it

  Scenario: Mod can reveal challenge

  Given I have Battle 12 prompt meme fully set up
  When I close signups for "Battle 12"
  When I go to "Battle 12" collection's page
    And I follow "Collection Settings"
    And I uncheck "This collection is unrevealed"
    And I press "Update"
  Then I should see "Collection was successfully updated"

  Scenario: Revealing challenge sends out emails

  Given I have Battle 12 prompt meme fully set up
  Given everyone has signed up for Battle 12
  When I am logged in as "myname4"
  When I claim a prompt from "Battle 12"
  When I close signups for "Battle 12"
  When I am logged in as "myname4"
  When I fulfill my claim
  When mod fulfills claim
  When I reveal the "Battle 12" challenge
  Then I should see "Collection was successfully updated"
  # 2 stories are now revealed, so notify the prompters
    And 2 emails should be delivered

  Scenario: Story is anon when challenge is revealed

  Given I have standard challenge tags setup
  Given I have Battle 12 prompt meme fully set up
  Given everyone has signed up for Battle 12
  When I am logged in as "myname4"
  When I claim a prompt from "Battle 12"
  When I close signups for "Battle 12"
  When I am logged in as "myname4"
  When I fulfill my claim
  When mod fulfills claim
  When I reveal the "Battle 12" challenge
  When I am logged in as "myname4"
  When I view the work "Fulfilled Story-thing"
  Then I should see "In response to a prompt by myname4"
    And I should see "Fandom: Stargate Atlantis"
    And I should see "Collections: Battle 12"
    And I should see "Anonymous" within ".byline"
    And I should not see "mod1" within ".byline"

  Scenario: Authors can be revealed

  Given I have Battle 12 prompt meme fully set up
  Given everyone has signed up for Battle 12
  When I am logged in as "myname4"
  When I claim a prompt from "Battle 12"
  When I close signups for "Battle 12"
  When I am logged in as "myname4"
  When I fulfill my claim
  When mod fulfills claim
  When I reveal the "Battle 12" challenge
  When I reveal the authors of the "Battle 12" challenge
  Then I should see "Collection was successfully updated"

  Scenario: Revealing authors doesn't send emails

  Given I have Battle 12 prompt meme fully set up
  Given everyone has signed up for Battle 12
  When I am logged in as "myname4"
  When I claim a prompt from "Battle 12"
  When I close signups for "Battle 12"
  When I am logged in as "myname4"
  When I fulfill my claim
  When mod fulfills claim
  When I reveal the "Battle 12" challenge
  Given all emails have been delivered
  When I reveal the authors of the "Battle 12" challenge
  Then I should see "Collection was successfully updated"
  Then 0 emails should be delivered

  Scenario: When challenge is revealed-authors, user can see claims

  Given I have Battle 12 prompt meme fully set up
  Given everyone has signed up for Battle 12
  When I am logged in as "myname4"
  When I claim a prompt from "Battle 12"
  When I close signups for "Battle 12"
  When I am logged in as "myname4"
  When I fulfill my claim
  When mod fulfills claim
  When I reveal the "Battle 12" challenge
  When I reveal the authors of the "Battle 12" challenge
  When I am logged in as "myname4"
  When I go to "Battle 12" collection's page
    And I follow "Prompts (8)"
  Then I should see "Fulfilled By"
    And I should see "Fulfilled Story by myname4" within "div.work"
    And I should see "Fulfilled Story-thing by mod1" within "div.work"

  Scenario: Anon prompts stay anon on claims index even if challenge is revealed

  Given I have Battle 12 prompt meme fully set up
  When I am logged in as "myname4"
  When I sign up for Battle 12 with combination B
  When I close signups for "Battle 12"
  When I am logged in as "myname2"
  When I claim a prompt from "Battle 12"
  When I fulfill my claim
  When I reveal the "Battle 12" challenge
  When I reveal the authors of the "Battle 12" challenge
  When I go to "Battle 12" collection's page
    And I follow "Prompts ("
  Then I should see "by Anonymous"
    And I should not see "by myname4"

  Scenario: Check that anon prompts are still anon on the prompts page after challenge is revealed

  Given I have Battle 12 prompt meme fully set up
  When I am logged in as "myname4"
  When I sign up for Battle 12 with combination B
  When I close signups for "Battle 12"
  When I am logged in as "myname2"
  When I claim a prompt from "Battle 12"
  When I fulfill my claim
  When I reveal the "Battle 12" challenge
  When I reveal the authors of the "Battle 12" challenge
  When I view prompts for "Battle 12"
  Then I should see "random SGA love by Anonymous"
  Then I should see "Fulfilled Story by myname2"
  Then I should see "High School AU SG1 by Anonymous "

  Scenario: Check that anon prompts are still anon on user claims index after challenge is revealed

  Given I have Battle 12 prompt meme fully set up
  When I am logged in as "myname4"
  When I sign up for Battle 12 with combination B
  When I close signups for "Battle 12"
  When I am logged in as "myname2"
  When I claim a prompt from "Battle 12"
  When I reveal the "Battle 12" challenge
  When I reveal the authors of the "Battle 12" challenge
  When I am logged in as "myname2"
  When I am on my user page
    And I follow "Claims"
    # note that user Claims page currently only shows unfulfilled claims
  Then I should not see "myname4"
    And I should see "Anonymous"

  Scenario: Check that anon prompts are still anon on claims show after challenge is revealed
  # note that only mod can see claims show now - users don't get linked to it

  Given I have Battle 12 prompt meme fully set up
  When I am logged in as "myname4"
  When I sign up for Battle 12 with combination B
  When I close signups for "Battle 12"
  When I am logged in as "myname2"
  When I claim a prompt from "Battle 12"
  When I reveal the "Battle 12" challenge
  When I reveal the authors of the "Battle 12" challenge
  When I am logged in as "mod1"
  When I am on "Battle 12" collection's page
    And I follow "Unposted Claims"
    And I follow "Anonymous"
  Then I should not see "myname4"
    And I should see "Anonymous"

  Scenario: check that anon prompts are still anon on the fulfilling work
  # TODO

  Scenario: work left in draft so claim is not yet totally fulfilled

  Given I have Battle 12 prompt meme fully set up
  Given an anon has signed up for Battle 12
  When I close signups for "Battle 12"
  When I reveal the "Battle 12" challenge
  Given all emails have been delivered
  When I reveal the authors of the "Battle 12" challenge
  When I am logged in as "myname4"
  When I claim a prompt from "Battle 12"
  When I start to fulfill my claim
    And I press "Preview"
  When I go to the "Battle 12" requests page
  Then I should see "Claimed By"
    And I should not see "Fulfilled By"
  When I am logged in as "mod1"
    And I go to "Battle 12" collection's page
    And I follow "Unposted Claims"
  Then I should see "myname4"
  When I am logged in as "myname4"
    And I go to my claims page
    # Draft not shown. Instead we see that there is a 'Fulfill' button which
    # we can use. Then use the 'Restore From Last Unposted Draft?' button
  When I follow "Fulfill"
    And I follow "Restore From Last Unposted Draft?"
  When I press "Post"
    And I should see "Work was successfully posted."
  Then I should see "Fulfilled Story"

  Scenario: Maintainers can download CSV from requests or sign-ups page

  Given I am logged in as "mod1"
    And I have standard challenge tags setup
    And I create Battle 12 promptmeme
  When I go to the "Battle 12" signups page
  Then I should see "Download (CSV)"
  When I go to the "Battle 12" requests page
    And I follow "Download (CSV)"
  Then I should download a csv file with the header row "Pseud Sign-up URL Tags Title Description"

  Scenario: Users can't download prompt CSV from requests page

  Given I have Battle 12 prompt meme fully set up
    And everyone has signed up for Battle 12
    And I am logged in
  When I go to the "Battle 12" requests page
  Then I should not see "Download (CSV)"

  Scenario: Validation error doesn't cause semi-anon ticky to lose state (Issue 2617)
  Given I set up an anon promptmeme "Scotts Prompt" with name "scotts_prompt"
    And I am logged in as "Scott" with password "password"
    And I go to "Scotts Prompt" collection's page
    And I follow "Prompt Form"
    And I check "Semi-anonymous Prompt"
    And I press "Submit"
  Then I should see "There were some problems with this submission. Please correct the mistakes below."
    And I should see "Your Request must include between 1 and 2 fandom tags, but you have included 0 fandom tags in your current Request."
    And the "Semi-anonymous Prompt" checkbox should be checked

  Scenario: Dates should be correctly set on PromptMemes
    Given it is currently 2015-09-21 12:40 AM
      And I am logged in as "mod1"
      And I have standard challenge tags set up
      And I have no prompts
    When I set up Battle 12 promptmeme collection
      And I check "Sign-up open?"
      And I fill in "Sign-up opens:" with "2010-09-20 12:40AM"
      And I fill in "Sign-up closes:" with "2010-09-22 12:40AM"
      And I submit
    Then I should see "If sign-ups are open, sign-up close date cannot be in the past."
    When I fill in "Sign-up opens:" with "2022-09-20 12:40AM"
      And I fill in "Sign-up closes:" with "2010-09-22 12:40AM"
      And I submit
    Then I should see "If sign-ups are open, sign-up open date cannot be in the future."
    When I fill in "Sign-up opens:" with "2010-09-22 12:40AM"
      And I fill in "Sign-up closes:" with "2010-09-20 12:40AM"
      And I submit
    Then I should see "Close date cannot be before open date."
    When I fill in "Sign-up opens:" with ""
      And I use tomorrow as the "Sign-up closes" date
      And I submit
    Then I should see "Challenge was successfully created."

  Scenario: A user who disallows gift works is cautioned about signing up for
  a prompt meme, and a user who allows them is not.
    Given I have Battle 12 prompt meme fully set up
      And I am logged in as "participant"
      And the user "participant" disallows gifts
    When I go to "Battle 12" collection's page
      And I follow "Prompt Form"
    Then I should see "any user who claims your prompt to gift you a work in response to your prompt regardless of your preference settings"
    When the user "participant" allows gifts
      And I go to "Battle 12" collection's page
      And I follow "Prompt Form"
    Then I should not see "any user who claims your prompt to gift you a work in response to your prompt regardless of your preference settings"
