@collections @challenges @promptmemes
Feature: Prompt Meme Challenge
  In order to participate without inhibitions
  As a humble user
  I want to prompt, post and receive fills anonymously

  Scenario: Prompt anonymously and be notified of the fills without the writer knowing who I am
  Given basic tags
    And the following activated user exists
      | login   | password | email     |
      | myname1 | password | my1@e.org |
    And a fandom exists with name: "GhostSoup", canonical: true
    And I am logged in as "mod1"
    And I set up a basic promptmeme "The Kissing Game"
    And I log out
  When I am logged in as "myname1"
    And I go to "The Kissing Game" collection's page
    # And the apostrophe stops getting in the way of highlighting in notepad++ '
    And I follow "Sign Up"
    And I fill in "challenge_signup_requests_attributes_0_tag_set_attributes_fandom_tagnames" with "GhostSoup"
    And I check "challenge_signup_requests_attributes_0_anonymous"
    # there are two forms in this page, can't use I submit
    And I press "Submit"
  Then I should see "Sign-up was successfully created"
  When I log out
    And I am logged in as "myname2"
    And I go to "The Kissing Game" collection's page
    And I follow "Prompts (1)"
    And I press "Claim"
  Then I should see "New claim made"
    And I follow "Fulfill"
    And I check "No Archive Warnings Apply"
    And I select "English" from "Choose a language"
    And I fill in "Fandoms" with "GhostSoup"
    And I should see "promptcollection" in the "work_collection_names" input
    And the "Untitled Prompt in The Kissing Game (Anonymous)" checkbox should be checked
    And the "work_recipients" field should not contain "myname1"
    And I fill in "Work Title" with "Kinky Story"
    And I fill in "content" with "Story written for your kinks, oh mystery reader!"
  Given all emails have been delivered
    And I press "Post"
  Then I should see "Kinky Story"
    And I should find a list for associations
    And I should see "In response to a prompt by Anonymous in the promptcollection collection"
    And I should see a link "prompt"
    And 1 email should be delivered to "my1@e.org"
# TODO: when work_anonymous is implemented, test that the prompt filler can be anon too

  Scenario: Fulfilling a claim ticks the right boxes automatically

  Given I have Battle 12 prompt meme fully set up
  When I am logged in as "myname1"
  When I sign up for Battle 12 with combination B
    And I am logged in as "myname4"
    And I claim a prompt from "Battle 12"
  When I start to fulfill my claim
  Then the "Battle 12" checkbox should be checked
    And the "Battle 12" checkbox should not be disabled

  Scenario: User can fulfill a claim

  Given I have Battle 12 prompt meme fully set up
  When I am logged in as "myname1"
  When I sign up for Battle 12 with combination B
    And I am logged in as "myname4"
    And I claim a prompt from "Battle 12"
  When I fulfill my claim
  Then my claim should be fulfilled

  Scenario: User can fulfill a claim to their own prompt

  Given I have Battle 12 prompt meme fully set up
  When I am logged in as "myname1"
    And I sign up for Battle 12 with combination B
    And I claim a prompt from "Battle 12"
    And I fulfill my claim
  Then my claim should be fulfilled

  Scenario: Fulfilled claim shows correctly on my claims

  Given I have Battle 12 prompt meme fully set up
  When I am logged in as "myname1"
  When I sign up for Battle 12 with combination B
    And I am logged in as "myname4"
    And I claim a prompt from "Battle 12"
  When I fulfill my claim
  When I am on my user page
    And I follow "Claims"
		And I follow "Fulfilled Claims"
  Then I should see "Fulfilled Story"
   # TODO: should I? It's not there at all
    And I should not see "Not yet posted"

  Scenario: Claims count should be correct, shows fulfilled claims as well

  Given I have Battle 12 prompt meme fully set up
  When I am logged in as "myname1"
  When I sign up for Battle 12 with combination B
    And I am logged in as "myname4"
    And I claim a prompt from "Battle 12"
  When I fulfill my claim
  When I am on my user page
  # Then show me the sidebar # TODO: it has Claims (0) but why?
  Then I should see "Claims (0)"
  When I follow "Claims"
    And I follow "Fulfilled Claims"
  Then I should see "Fulfilled Story"

  Scenario: Claim shows as fulfilled to another user

  Given I have Battle 12 prompt meme fully set up
  When I am logged in as "myname1"
  When I sign up for Battle 12 with combination B
    And I am logged in as "myname4"
    And I claim a prompt from "Battle 12"
  When I fulfill my claim
  When I am logged in as "myname1"
  When I go to "Battle 12" collection's page
    And I follow "Prompts ("
  Then I should see "Fulfilled By"
    And I should see "Mystery Work"

  Scenario: Fulfilled claims are shown to mod
 # TODO: We need to figure out if we want to hide claims from mods in 100% anonymous prompt memes
#  Given I have Battle 12 prompt meme fully set up
#  Given everyone has signed up for Battle 12
#  When I am logged in as "myname4"
#  When I claim a prompt from "Battle 12"
#  When I close signups for "Battle 12"
#  When I am logged in as "myname4"
#  When I fulfill my claim
#  When mod fulfills claim
#  When I am on "Battle 12" collection's page
#  When I follow "Prompts"
#    And I follow "Show Claims"
#  Then I should not see "Claimed by: myname4"
#    And I should not see "Claimed by: mod1"
#    And I should not see "Claimed by: (Anonymous)"
#  When I follow "Show Filled"
#  Then I should see "Claimed by: myname4"
#    And I should see "Claimed by: mod1"
#    And I should not see "Claimed by: (Anonymous)"

  Scenario: Fulfilled claims are hidden from user

  Given I have Battle 12 prompt meme fully set up
  Given everyone has signed up for Battle 12
  When I am logged in as "myname4"
  When I claim a prompt from "Battle 12"
  When I close signups for "Battle 12"
  When I am logged in as "myname4"
  When I fulfill my claim
  When mod fulfills claim
  When I am logged in as "myname4"
  When I go to "Battle 12" collection's page
    And I follow "Prompts (8)"
  Then I should not see "myname4" within "h5"
    And I should not see "mod1" within "h5"
    And I should see "Fulfilled Story by Anonymous" within "div.work h4"

  Scenario: Sign-up can be deleted after response has been posted

  Given I have Battle 12 prompt meme fully set up
  When I am logged in as "myname1"
  When I sign up for Battle 12 with combination B
    And I am logged in as "myname4"
    And I claim a prompt from "Battle 12"
  When I fulfill my claim
  When I am logged in as "myname1"
    And I delete my signup for the prompt meme "Battle 12"
  Then I should see "Challenge sign-up was deleted."
  # work fulfilling is still fine
  When I view the work "Fulfilled Story"
  Then I should see "This work is part of an ongoing challenge and will be revealed soon! You can find details here: Battle 12"
    And I should not see "Stargate Atlantis"
    # work is still fine as another user
  When I am logged in as "myname4"
    And I view the work "Fulfilled Story"
  Then I should see "This work is part of an ongoing challenge and will be revealed soon! You can find details here: Battle 12"
    And I should see "Stargate Atlantis"

  Scenario: Prompt can be removed after response has been posted and still show properly on the work which fulfilled it

  Given I have Battle 12 prompt meme fully set up
  When I am logged in as "myname1"
  When I sign up for Battle 12 with combination B
    And I am logged in as "myname4"
    And I claim a prompt from "Battle 12"
  When I fulfill my claim
  When I am logged in as "myname1"
    And I delete my signup for the prompt meme "Battle 12"
  When I view the work "Fulfilled Story"
  Then I should see "This work is part of an ongoing challenge and will be revealed soon! You can find details here: Battle 12"
    And I should not see "Stargate Atlantis"
  When I am logged in as "myname4"
    And I view the work "Fulfilled Story"
  Then I should see "This work is part of an ongoing challenge and will be revealed soon! You can find details here: Battle 12"
    And I should see "Stargate Atlantis"

  Scenario: User can fulfill the same claim twice

  Given I have Battle 12 prompt meme fully set up
  When I am logged in as "myname1"
  When I sign up for Battle 12 with combination B
    And I am logged in as "myname4"
    And I claim a prompt from "Battle 12"
  When I fulfill my claim
  When I fulfill my claim again
  Then I should see "Work was successfully posted"
    And I should see "Second Story"
    And I should see "In response to a prompt by Anonymous"
    And I should see "Collections: Battle 12"

  Scenario: User edits existing work to fulfill claim

  Given I have Battle 12 prompt meme fully set up
  When I am logged in as "myname1"
  When I sign up for Battle 12 with combination B
    And I am logged in as "myname4"
    And I claim a prompt from "Battle 12"
    And I post the work "Existing Story"
    And I should not see "Battle 12"
    And I edit the work "Existing Story"
    And I check "random SGA love in Battle 12 (Anonymous)"
    And I press "Post"
  Then I should see "Battle 12"
  Then I should see "Existing Story"
    And I should see "This work is part of an ongoing challenge"
  When I reveal works for "Battle 12"
  When I view the work "Existing Story"
    And I should not see "This work is part of an ongoing challenge"

  Scenario: User edits existing work in another collection to fulfill claim

  Given I have Battle 12 prompt meme fully set up
    And I have a collection "Othercoll"
  When I am logged in as "myname1"
  When I sign up for Battle 12 with combination B
    And I am logged in as "myname4"
    And I claim a prompt from "Battle 12"
    And I post the work "Existing Story" in the collection "Othercoll"
    And I edit the work "Existing Story"
    And I check "random SGA love in Battle 12 (Anonymous)"
    And I press "Post"
  Then I should see "Battle 12"
    And I should see "Othercoll"

  Scenario: Fulfill a claim by editing an existing work

  Given I have Battle 12 prompt meme fully set up
    And everyone has signed up for Battle 12
  When I close signups for "Battle 12"
  When I reveal the "Battle 12" challenge
  When I reveal the authors of the "Battle 12" challenge
  When I am logged in as "myname1"
    And I go to "Battle 12" collection's page
    And I follow "Prompts ("
  When I press "Claim"
  Then I should see "New claim made"
  When I post the work "Here's one I made earlier"
    And I edit the work "Here's one I made earlier"
    And I check "Battle 12"
    And I press "Preview"
  Then I should see "In response to a prompt by"
    And I should see "Collections:"
    And I should see "Battle 12"
  When I press "Update"
  Then I should see "Work was successfully updated"
    And I should not see "draft"
    And I should see "In response to a prompt by"
  Then I should see "Collections:"
    And I should see "Battle 12"

  # claim is fulfilled on collection page
  When I go to "Battle 12" collection's page
    And I follow "Prompts"
  Then I should see "myname1" within ".prompt .work"
    And I should see "Fulfilled By"

  Scenario: When draft is posted, claim is fulfilled and posted to collection

  Given I have Battle 12 prompt meme fully set up
  When I am logged in as "myname2"
    And I sign up for Battle 12 with combination B
  When I am logged in as "myname4"
    And I claim a prompt from "Battle 12"
  When I close signups for "Battle 12"
  When I reveal the "Battle 12" challenge
  When I reveal the authors of the "Battle 12" challenge
  When I am logged in as "myname4"
    And I go to the "Battle 12" requests page
  When I press "Claim"
  When I follow "Fulfill"
    And I fill in the basic work information for "Existing work"
    And I check "random SGA love in Battle 12 (Anonymous)"
    And I press "Preview"
  When I am on my user page
    And I follow "Drafts"
    And all emails have been delivered
  When I follow "Post Draft"
  Then 1 email should be delivered
  Then I should see "Your work was successfully posted"
    And I should see "In response to a prompt by Anonymous"
  When I go to "Battle 12" collection's page
    And I follow "Prompts ("
  Then I should see "myname4"
    And I should see "Fulfilled By"
  When I follow "Existing work"
  Then I should see "Existing work"
    And I should see "Battle 12"
    And I should not see "draft"

  Scenario: Make another claim and then fulfill from the post new form (New Work)

  Given I have Battle 12 prompt meme fully set up
  Given everyone has signed up for Battle 12
  When I close signups for "Battle 12"
  When I reveal the "Battle 12" challenge
  Given all emails have been delivered
  When I reveal the authors of the "Battle 12" challenge
  When I go to "Battle 12" collection's page
    And I follow "Prompts (8)"
  When I press "Claim"
  Then I should see "New claim made."
  When I am logged in as "myname4"
    And I go to the collections page
    And I follow "Battle 12"
  When I follow "Prompts ("
  When I press "Claim"
  Then I should see "New claim made"
  When I follow "New Work"
  When I fill in the basic work information for "Existing work"
    And I check "Battle 12 (myname4)"
    And I press "Preview"
  Then I should see "Draft was successfully created"
    And I should see "In response to a prompt by myname4"
    And 0 emails should be delivered
    And I should see "Collections:"
    And I should see "Battle 12"
  When I view the work "Existing work"
  Then I should see "draft"

  Scenario: Fulfilled claims show as fulfilled to another user

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
  When I go to "Battle 12" collection's page
    And I follow "Prompts (8)"
  When I press "Claim"
  Then I should see "New claim made."
  When I am logged in as "myname4"
    And I go to the "Battle 12" requests page
  Then I should see "mod1" within ".prompt .work"
    And I should see "myname4" within ".prompt .work"

  Scenario: When a prompt is filled with a co-authored work, the e-mail should link to each author's URL instead of showing escaped HTML

  Given I have Battle 12 prompt meme fully set up
    And I am logged in as "myname3"
    And I go to myname3's user page
    And I follow "Preferences"
    And I check "Allow others to invite me to be a co-creator"
    And I press "Update"
  When I am logged in as "myname1"
    And I sign up for Battle 12 with combination A
    And I log out
  When I am logged in as "myname2"
    And I claim a prompt from "Battle 12"
    And I start to fulfill my claim with "Co-authored Fill"
    And I invite the co-author "myname3"
  When I press "Post"
  Then 1 email should be delivered to "myname3"
    And the email should contain "The user myname2 has invited your pseud myname3 to be listed as a co-creator on the following work"
    And the email should not contain "translation missing"
  When the user "myname3" accepts all co-creator requests
    And I am logged in as "mod1"
    And I reveal the authors of the "Battle 12" challenge
    And I reveal the "Battle 12" challenge
  Then 1 email should be delivered to "myname1"
    And the email should link to myname2's user url
    And the email should not contain "&lt;a href=&quot;http://archiveofourown.org/users/myname2/pseuds/myname2&quot;"
    And the email should link to myname3's user url
    And the email should not contain "&lt;a href=&quot;http://archiveofourown.org/users/myname3/pseuds/myname3&quot;"

  Scenario: check that completed ficlet is unrevealed

  Given I have Battle 12 prompt meme fully set up
  Given everyone has signed up for Battle 12
  When mod fulfills claim
  When I am logged in as "myname4"
  When I view the work "Fulfilled Story-thing"
  Then I should not see "In response to a prompt by myname4"
    And I should not see "Fandom: Stargate Atlantis"
    And I should not see "Anonymous"
    And I should not see "mod1"
    And I should see "This work is part of an ongoing challenge and will be revealed soon! You can find details here: Battle 12"

  Scenario: Mod can post a fic

  Given I have Battle 12 prompt meme fully set up
  Given everyone has signed up for Battle 12
  When I am logged in as "mod1"
  When I claim a prompt from "Battle 12"
  When I am on my user page
  Then I should see "Claims (1)"
  When I follow "Claims"
  Then I should see "My Claims"
    And I should see "canon SGA love by myname4 in Battle 12" within "div#main.challenge_claims-index h4"
  When I follow "Fulfill"
    And I fill in "Fandoms" with "Stargate Atlantis"
    And I fill in "Work Title*" with "Fulfilled Story-thing"
    And I select "Not Rated" from "Rating"
    And I check "No Archive Warnings Apply"
    And I select "English" from "Choose a language"
    And I fill in "content" with "This is an exciting story about Atlantis, but in a different universe this time"
  When I press "Preview"
    And I press "Post"
  Then I should see "Work was successfully posted"

  Scenario: Mod can complete a claim

  Given I have Battle 12 prompt meme fully set up
  Given everyone has signed up for Battle 12
  When I am logged in as "mod1"
  When I claim a prompt from "Battle 12"
  When I start to fulfill my claim
    And I fill in "Work Title" with "Fulfilled Story-thing"
    And I fill in "content" with "This is an exciting story about Atlantis, but in a different universe this time"
  When I press "Preview"
    And I press "Post"
  When I am on my user page
  Then I follow "Claims"
    And I should not see "mod" within "h4"
  Then I follow "Fulfilled Claims"
  # On the users' My Claims page, they see their anon works as Anonymous
    And I should see "Anonymous" within "div.work h4"

  Scenario: Fic shows what prompt it is fulfilling when mod views it

  Given I have Battle 12 prompt meme fully set up
  Given everyone has signed up for Battle 12
  When I am logged in as "mod1"
  When I claim a prompt from "Battle 12"
  When I start to fulfill my claim
    And I fill in "Work Title" with "Fulfilled Story-thing"
    And I fill in "content" with "This is an exciting story about Atlantis, but in a different universe this time"
  When I press "Preview"
    And I press "Post"
  When I view the work "Fulfilled Story-thing"
  Then I should see "In response to a prompt by myname4"
    And I should see "Fandom: Stargate Atlantis"
    And I should see "Anonymous" within ".byline"
    And I should see a link "prompt"

  Scenario: Work links to the prompt it fulfils, for all users

  Given I have Battle 12 prompt meme fully set up
    And I am logged in as "myname1"
    And I sign up for Battle 12 with combination B
    And I am logged in as "myname4"
    And I claim a prompt from "Battle 12"
    And I fulfill my claim
    And I reveal works for "Battle 12"
    And I view the work "Fulfilled Story"
  Then I should see "Fulfilled Story"
    And I should see "In response to a prompt by Anonymous"
    And I should see a link "prompt"
  When I follow "prompt"
  Then I should see "Request by Anonymous"
  When I am logged in as "myname2"
    And I view the work "Fulfilled Story"
  Then I should see "Fulfilled Story"
    And I should see "In response to a prompt by Anonymous"
    And I should see a link "prompt"
  When I follow "prompt"
  Then I should see "Request by Anonymous"
  When I am logged in as "mod"
    And I view the work "Fulfilled Story"
  Then I should see "Fulfilled Story"
    And I should see "In response to a prompt by Anonymous"
    And I should see a link "prompt"
  When I follow "prompt"
  Then I should see "Request by Anonymous"
  When I log out
    And I view the work "Fulfilled Story"
  Then I should see "Fulfilled Story"
    And I should see "In response to a prompt by Anonymous"
    And I should see a link "prompt"
  When I follow "prompt"
  Then I should see "Request by Anonymous"

  Scenario: A creator can give a gift to a user who disallows gifts if the work is connected to a claim of a non-anonymous prompt belonging to the recipient, and the recipient remains attached even if the work is later disconnected from the claim

  Given I have Battle 12 prompt meme fully set up
    And the user "prompter" exists and is activated
    And the user "prompter" disallows gifts
    And "prompter" has signed up for Battle 12 with combination A
  When I am logged in as "gifter"
    And I claim a prompt from "Battle 12"
    And I start to fulfill my claim
    And I fill in "Gift this work to" with "prompter"
    And I press "Post"
  Then I should see "For prompter."
  When I follow "Edit"
    And I uncheck "Battle 12 (prompter)"
    And I press "Post"
  Then I should see "For prompter."

  Scenario: A creator cannot give a gift to a user who disallows gifts if the work is connected to a claim of an anonymous prompt belonging to the recipient

  Given I have Battle 12 prompt meme fully set up
    And the user "prompter" exists and is activated
    And the user "prompter" disallows gifts
    And "prompter" has signed up for Battle 12 with combination B
  When I am logged in as "gifter"
    And I claim a prompt from "Battle 12"
    And I start to fulfill my claim
    And I fill in "Gift this work to" with "prompter"
    And I press "Post"
  Then I should see "prompter does not accept gifts."

  Scenario: A creator cannot give a gift to a user who disallows gifts if the work is connected to a claim of a non-anonymous prompt belonging to a different user

  Given I have Battle 12 prompt meme fully set up
    And the user "prompter" exists and is activated
    And the user "prompter" disallows gifts
    And "prompter" has signed up for Battle 12 with combination A
    And the user "bystander" exists and is activated
    And the user "bystander" disallows gifts
  When I am logged in as "gifter"
    And I claim a prompt from "Battle 12"
    And I start to fulfill my claim
    And I fill in "Gift this work to" with "prompter, bystander"
    And I press "Post"
  Then I should see "bystander does not accept gifts."
