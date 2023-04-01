@collections
Feature: Collection
  I want to test Yuletide, because it has several specific settings that are different from an ordinary gift exchange

  # Basic tag set testing is covered in challenge_giftexchange_tagsets.feature.
  # Advanced stuff and nominations are covered in tags_and_wrangling/tag_set.feature.

  # uncomment this and the other 'javascript' lines below when testing on local
  # in order to test javascript-based features
  #@javascript
  Scenario: Create a Yuletide gift exchange, sign up for it, run matching, post, fulfil pinch hits

  Given the following activated users exist
    | login          | password    |
    | mod1           | password   |
    | myname1        | password   |
    | myname2        | password   |
    | myname3        | password   |
    | myname4        | password   |
    | pinchhitter    | password    |
    And I am logged in as "mod1"
    And I have no collections
    And I have Yuletide challenge tags setup
    And I add the fandom "Stargate Atlantis" to the character "John Sheppard"
    And I add the fandom "Starsky & Hutch" to the character "John Sheppard"
    And I add the fandom "Tiny fandom" to the character "John Sheppard"
    And a character exists with name: "Teyla Emmagan", canonical: true
    And I add the fandom "Stargate Atlantis" to the character "Teyla Emmagan"
    And I add the fandom "Starsky & Hutch" to the character "Teyla Emmagan"
    And a character exists with name: "Foo The Wonder Goat", canonical: true
    And I add the fandom "Tiny fandom" to the character "Foo The Wonder Goat"
    And I add the fandom "Starsky & Hutch" to the character "Foo The Wonder Goat"
    And a character exists with name: "Obscure person", canonical: true
    And I add the fandom "Tiny fandom" to the character "Obscure person"
  When I go to the collections page
  Then I should see "Collections in the "
    And I should not see "Yuletide"
  When I follow "New Collection"
    And I fill in "Display title" with "Yuletide"
    And I fill in "Collection name" with "yule2011"
    And I fill in "Introduction" with "Welcome to the exchange"
    And I fill in "FAQ" with "<dl><dt>What is this thing?</dt><dd>It's a gift exchange-y thing</dd></dl>"
    And I fill in "Rules" with "Be even nicer to people"
    And I select "Gift Exchange" from "challenge_type"
    And I check "This collection is unrevealed"
    And I check "This collection is anonymous"
    And I submit
  Then I should see "Collection was successfully created"
    And I should see "Setting Up the Yuletide Gift Exchange"
  When I fill in "General Sign-up Instructions" with "Here are some general tips"
    And I fill in "Request Instructions" with "Please request easy things"
    And I fill in "Offer Instructions" with "Please offer lots of stuff"
    # for testing convenience while still exercising the options, we are going with
    # 2-3 requests, 2-3 offers
    # url allowed in request
    # description not allowed in offer
    # 1 fandom required in offer and request
    # 0-2 characters allowed in request
    # 2-3 characters required in offer
    # unique fandoms required in offers and requests
    # "any" option available in character offers
    # restrict character to fandom only
    # match on 1 fandom and 1 character
    And I check "gift_exchange_request_restriction_attributes_url_allowed"
    And I uncheck "gift_exchange_offer_restriction_attributes_description_allowed"
    And I fill in "gift_exchange_requests_num_required" with "2"
    And I fill in "gift_exchange_requests_num_allowed" with "3"
    And I fill in "gift_exchange_offers_num_required" with "2"
    And I fill in "gift_exchange_offers_num_allowed" with "3"
    And I fill in "Tag Sets To Use:" with "Standard Challenge Tags"
    And I fill in "gift_exchange_request_restriction_attributes_fandom_num_required" with "1"
    And I fill in "gift_exchange_request_restriction_attributes_fandom_num_allowed" with "1"
    And I check "gift_exchange_request_restriction_attributes_require_unique_fandom"
    And I fill in "gift_exchange_request_restriction_attributes_character_num_allowed" with "2"
    And I fill in "gift_exchange_offer_restriction_attributes_fandom_num_required" with "1"
    And I fill in "gift_exchange_offer_restriction_attributes_fandom_num_allowed" with "1"
    And I fill in "gift_exchange_offer_restriction_attributes_character_num_required" with "2"
    And I fill in "gift_exchange_offer_restriction_attributes_character_num_allowed" with "3"
    And I check "gift_exchange_offer_restriction_attributes_require_unique_fandom"
    And I check "gift_exchange_offer_restriction_attributes_allow_any_character"
    And I select "1" from "gift_exchange_potential_match_settings_attributes_num_required_fandoms"
    And I select "1" from "gift_exchange_potential_match_settings_attributes_num_required_characters"
    And I check "gift_exchange_offer_restriction_attributes_character_restrict_to_fandom"
    And I check "Sign-up open?"
    And I set up the challenge dates
    And I submit
  Then I should see "Challenge was successfully created"
  When I log out
    And I am logged in as "myname1"
  When I go to the collections page
  Then I should see "Yuletide"
  When I follow "Yuletide"
  Then I should see "Sign Up"
  When I follow "Profile"
  Then I should see "About Yuletide (yule2011)"
    And I should see "Sign-up:" within ".collection .meta"
    And I should see "Open" within ".collection .meta"
    And I should see "Sign-up Closes:" within ".collection .meta"
    And I should see "Assignments Due:" within ".collection .meta"
    And I should see "Works Revealed:" within ".collection .meta"
    And I should see "Creators Revealed:" within ".collection .meta"
    And I should see "Signed up:" within ".collection .meta"
    And I should see "0" within ".collection .meta"
    And I should see "Welcome to the exchange" within "#intro"
    And I should see "What is this thing?" within "#faq"
    And I should see "It's a gift exchange-y thing" within "#faq"
    And I should see "Be even nicer to people" within "#rules"
  When I follow "Sign Up"
  Then I should see "General Sign Up Instructions"
    And I should see "Here are some general tips"
    And I should see "Requests (2 - 3)"
    And I should see "Please request easy things"
    And I should see "Request 1"
    And I should see "Fandom (1):"
    And I should see "Care Bears"
    And I should see "Stargate Atlantis"
    And I should see "Starsky & Hutch"
    And I should see "Tiny fandom"
    And I should see "Yuletide Hippos RPF"
    And I should see "Characters (0 - 2):"
    And I should see "Prompt URL:"
    And I should see "Description:"
    And I should see "Request 2"
    And I should not see "Request 3"
    And I should see "Add another request? (Up to 3 allowed.)"
    And I should see "Offers (2 - 3)"
    And I should see "Please offer lots of stuff"
    And I should see "Offer 1"
    And I should see "Characters (2 - 3)"
    And I should see "Any Character" within "dd.any.option"
    And I should see "Offer 2"
    And I should not see "Offer 3"
    And I should see "Add another offer? (Up to 3 allowed.)"
  # we fill in 1 request with 1 fandom, 1 character; 1 offer with 1 fandom and 1 character
  When I check the 1st checkbox with the value "Stargate Atlantis"
    And I fill in "challenge_signup_requests_attributes_0_tag_set_attributes_character_tagnames" with "John Sheppard"
    And I fill in "Prompt URL" with "http://user.dreamwidth.org/123.html"
    And I fill in "Description" with "This is my wordy request"
    And I check the 3rd checkbox with the value "Care Bears"
    And I fill in "challenge_signup_offers_attributes_0_tag_set_attributes_character_tagnames" with "Obscure person"
    And I press "Submit"
  Then I should see a save error message
    # errors for the empty request
    And I should see "Request: Your Request must include exactly 1 fandom tags, but you have included 0 fandom tags in your current Request"
    # errors for the not-quite-filled offer
    And I should see "Offer: Your Offer must include between 2 and 3 character tags, but you have included 1 character tags in your current Offer"
    And I should see a not-in-fandom error message
    # errors for the empty offer
    And I should see "Offer: Your Offer must include exactly 1 fandom tags, but you have included 0 fandom tags in your current Offer"
    And I should see "Offer: Your Offer must include between 2 and 3 character tags, but you have included 0 character tags in your current Offer"
  # Over-fill the remaining missing fields and duplicate fandoms
  When I fill in "challenge_signup_requests_attributes_0_tag_set_attributes_character_tagnames" with "John Sheppard, Teyla Emmagan, Obscure person"
    And I check the 2nd checkbox with the value "Tiny fandom"
    And I check the 2nd checkbox with the value "Starsky & Hutch"
    And I fill in "challenge_signup_requests_attributes_1_tag_set_attributes_character_tagnames" with "Teyla Emmagan"
    And I fill in "challenge_signup_offers_attributes_0_tag_set_attributes_character_tagnames" with "Obscure person, John Sheppard"
    And I check the 4th checkbox with the value "Care Bears"
    And I fill in "challenge_signup_offers_attributes_1_tag_set_attributes_character_tagnames" with "Obscure person, John Sheppard, Teyla Emmagan, Foo The Wonder Goat"
    And I press "Submit"
  Then I should see a save error message
    And I should see "Request: Your Request must include between 0 and 2 character tags, but you have included 3 character tags in your current Request"
    And I should see a not-in-fandom error message for "Obscure person" in "Stargate Atlantis"
    And I should see "Request: Your Request must include exactly 1 fandom tags, but you have included 2 fandom tags in your current Request"
    And I should see a not-in-fandom error message for "Obscure person, John Sheppard" in "Care Bears"
    And I should see "Offer: Your Offer must include between 2 and 3 character tags, but you have included 4 character tags in your current Offer"
    And I should see a not-in-fandom error message for "Obscure person, John Sheppard, Teyla Emmagan, Foo The Wonder Goat" in "Care Bears"
    And I should see "You have submitted more than one offer with the same fandom tags. This challenge requires them all to be unique."
  
  
  # now fill in correctly
  # We have six participants who sign up as follows:
  
  # myname1 requests: SGA (JS, TE), Tiny fandom (Obscure person)
  #         offers: Tiny fandom (Obscure person, JS), Hippos (Any)
  # (is the only person who can write for myname2 and should therefore be assigned to them)
  When I fill in "challenge_signup_requests_attributes_0_tag_set_attributes_character_tagnames" with "John Sheppard, Teyla Emmagan"
    And I uncheck the 2nd checkbox with the value "Starsky & Hutch"
    And I fill in "challenge_signup_requests_attributes_1_tag_set_attributes_character_tagnames" with "Obscure person"
    And I uncheck the 3rd checkbox with the value "Care Bears"
    And I check the 3rd checkbox with the value "Tiny fandom"
    And I uncheck the 4th checkbox with the value "Care Bears"
    And I check the 4th checkbox with the value "Yuletide Hippos RPF"
    And I fill in "challenge_signup_offers_attributes_1_tag_set_attributes_character_tagnames" with ""
    And I check "challenge_signup_offers_attributes_1_any_character"
    And I press "Submit"
  Then I should see "Sign-up was successfully created"
    And I should see "Sign-up for myname1"
    And I should see "Requests"
    And I should see "This is my wordy request"
    And I should see "Offers"
    And I should see "Edit"
    And I should see "Delete"

  # another person signs up
  When I log out
    And I am logged in as "myname2"
  When I go to the collections page
    And I follow "Yuletide"
    And I follow "Profile"
  # before signing up, you can check who else has already signed up
  Then I should see "Signed up:" within ".collection .meta"
    And I should see "1" within ".collection .meta"

  # myname2 requests: Unoffered (no chars), Hippos (no chars)
  #         offers: S&H (JS, TE), SGA (JS, TE)
  # can only get from myname1 
  When I follow "Sign Up"
    And I check the 1st checkbox with value "Unoffered"
    And I check the 2nd checkbox with value "Yuletide Hippos RPF"
    And I check the 3rd checkbox with value "Starsky & Hutch"
    And I check the 4th checkbox with value "Stargate Atlantis"
    And I fill in "challenge_signup_requests_attributes_0_tag_set_attributes_character_tagnames" with "Any"
    And I fill in "challenge_signup_offers_attributes_0_tag_set_attributes_character_tagnames" with "Teyla Emmagan, John Sheppard"
    And I fill in "challenge_signup_offers_attributes_1_tag_set_attributes_character_tagnames" with "Teyla Emmagan, John Sheppard"
    And I press "Submit"
  Then I should see a save error message
    And I should see a not-in-fandom error message for "Any" in "Unoffered"
  When I fill in "challenge_signup_requests_attributes_0_tag_set_attributes_character_tagnames" with ""
    And I press "Submit"
  Then I should see "Sign-up was successfully created"

  # and a third person signs up
  # myname3 requests: S&H (JS), Tiny fandom; 
  #           offers: SGA (JS, TE), S&H (JS, TE, Foo)
  When I log out
    And I am logged in as "myname3"
  When I go to the collections page
    And I follow "Yuletide"
    And I follow "Sign Up"
  When I check the 1st checkbox with the value "Starsky & Hutch"
    And I fill in "challenge_signup_requests_attributes_0_tag_set_attributes_character_tagnames" with "John Sheppard"
    And I check the 2nd checkbox with the value "Tiny fandom"
    And I check the 3rd checkbox with the value "Stargate Atlantis"
    And I fill in "challenge_signup_offers_attributes_0_tag_set_attributes_character_tagnames" with "John Sheppard, Teyla Emmagan"
    And I check the 4th checkbox with the value "Starsky & Hutch"
    And I fill in "challenge_signup_offers_attributes_1_tag_set_attributes_character_tagnames" with "John Sheppard, Teyla Emmagan, Foo The Wonder Goat"
    # TRICKY note here! the index value for the javascript-added request 3 is actually 3; this is
    # a workaround because otherwise it would display a duplicate number
    # These three commented out so it can run on the command-line
    #And I follow "Add another request? (Up to 3 allowed.)"
    #Then I should see "Request 3"
    #And I check "challenge_signup_requests_attributes_3_fandom_30"
    And I press "Submit"
  Then I should see "Sign-up was successfully created"

  # fourth person signs up
  # myname4 requests SGA, S&H (JS, TE)
  #     offers Tiny (Obscure, JS), S&H (Foo, JS)
  When I log out
    And I am logged in as "myname4"
  When I go to the collections page
    And I follow "Yuletide"
    And I follow "Sign Up"
    And I check the 1st checkbox with value "Stargate Atlantis"
    And I check the 2nd checkbox with value "Starsky & Hutch"
    And I fill in "challenge_signup_requests_attributes_1_tag_set_attributes_character_tagnames" with "John Sheppard, Teyla Emmagan"
    And I check the 3rd checkbox with value "Tiny fandom"
    And I fill in "challenge_signup_offers_attributes_0_tag_set_attributes_character_tagnames" with "Obscure person, John Sheppard"
    And I check the 4th checkbox with value "Starsky & Hutch"
    And I fill in "challenge_signup_offers_attributes_1_tag_set_attributes_character_tagnames" with "Foo The Wonder Goat, John Sheppard"
    And I press "Submit"
  Then I should see "Sign-up was successfully created"

  # ordinary users can't see signups until 5 people have signed up
  When I go to the collections page
    And I follow "Yuletide"
  Then I should not see "Sign-ups" within "#dashboard"
    And I should see "Sign-up Summary"
  When I follow "Sign-up Summary"
  Then I should see "Summary does not appear until at least 5 sign-ups have been made!"
    And I should not see "Stargate Atlantis"

  # fifth person signs up
  # myname5 requests SGA, S&H
  #   offers Tiny (Foo, Obscure), SGA (JS, TE)
  When I log out
    And I am logged in as "myname5"
  When I go to the collections page
    And I follow "Yuletide"
    And I follow "Sign Up"
    And I check the 1st checkbox with value "Stargate Atlantis"
    And I check the 2nd checkbox with value "Starsky & Hutch"
    And I check the 3rd checkbox with value "Tiny fandom"
    And I fill in "challenge_signup_offers_attributes_0_tag_set_attributes_character_tagnames" with "Foo The Wonder Goat, Obscure Person"
    And I check the 4th checkbox with value "Stargate Atlantis"
    And I fill in "challenge_signup_offers_attributes_1_tag_set_attributes_character_tagnames" with "Teyla Emmagan, John Sheppard"
    And I press "Submit"
  Then I should see "Sign-up was successfully created"

  # ordinary users can't see signups but can see summary
  When I go to the collections page
    And I follow "Yuletide"
  Then I should not see "Sign-ups" within "#dashboard"
    And I should see "Sign-up Summary"
  When I follow "Sign-up Summary"
  Then I should see "Sign-up Summary for Yuletide"
    And I should see "Requested Fandoms"
    And I should see "Starsky & Hutch 3 3"
    And I should see "Stargate Atlantis 3 3"
    And I should see "Tiny fandom 2 3"

  # signup summary changes when another person signs up
  # myname6 requests: SGA, S&H
  #    offers: Tiny (Foo, Obscure), SGA (JS, TE)
  When I log out
    And I am logged in as "myname6"
  When I go to the collections page
    And I follow "Yuletide"
    And I follow "Sign Up"
    And I check the 1st checkbox with value "Stargate Atlantis"
    And I check the 2nd checkbox with value "Starsky & Hutch"
    And I check the 3rd checkbox with value "Tiny fandom"
    And I fill in "challenge_signup_offers_attributes_0_tag_set_attributes_character_tagnames" with "Foo The Wonder Goat, Obscure Person"
    And I check the 4th checkbox with value "Stargate Atlantis"
    And I fill in "challenge_signup_offers_attributes_1_tag_set_attributes_character_tagnames" with "Teyla Emmagan, John Sheppard"
    And I press "Submit"
  Then I should see "Sign-up was successfully created"
  When I go to the collections page
    And I follow "Yuletide"
    And I follow "Sign-up Summary"
  Then I should see "Sign-up Summary for Yuletide"
    And I should see "Requested Fandoms"
    And I should see "Starsky & Hutch 4 3"
    And I should see "Stargate Atlantis 4 4"
    And I should see "Tiny fandom 2 4"

  # mod can view signups
  When I log out
    And I am logged in as "mod1"
    And I go to the collections page
    And I follow "Yuletide"
    And I follow "Sign-ups"
  Then I should see "myname4" within "#main"
    And I should see "myname3" within "#main"
    And I should see "myname2" within "#main"
    And I should see "myname1" within "#main"
    And I should see "myname5" within "#main"
    And I should see "myname6" within "#main"
    And I should see "John Sheppard"
    And I should see "Obscure person"
    And I should see "http://user.dreamwidth.org/123.html"

  # mod runs matching
  When I follow "Matching"
  Then I should see "You can't generate matches while sign-up is still open."
    And I should not see "Generate Potential Matches"
  When I follow "Challenge Settings"
    And I uncheck "Sign-up open?"
    And I press "Update"
  Then I should see "Challenge was successfully updated"
  When I follow "Matching"
  Then I should see "Matching for Yuletide"
    And I should see "Generate Potential Matches"
    And I should see "No potential matches yet"
  When all emails have been delivered
  When I follow "Generate Potential Matches"
  Then I should see "Beginning generation of potential matches. This may take some time, especially if your challenge is large."
  Given the system processes jobs
    And I wait 3 seconds
  When I reload the page
  Then I should see "Reviewing Assignments"
    And I should see "Complete"
    And I should not see "No Recipient"
    And I should not see "No Giver"
    And I should see "Regenerate Assignments"
    And I should see "Regenerate All Potential Matches"
    And I should see "Send Assignments"
    And 1 email should be delivered

  # mod regenerates the assignments
  When all emails have been delivered
  When I follow "Regenerate Assignments"
  Then I should see "Beginning regeneration of assignments. This may take some time, especially if your challenge is large."
  Given the system processes jobs
    And I wait 3 seconds
  When I reload the page
  Then I should see "Complete"
    And I should not see "No Recipient"
    And I should not see "No Giver"
    And 1 email should be delivered

  # mod sends assignments out
  When all emails have been delivered
    And I follow "Send Assignments"
  Then I should see "Assignments are now being sent out"
    And I should see "No assignments to review"
    And I should see "Defaulted"
    And I should see "Pinch Hits"
    And I should see "Open"
    And I should see "Complete"
    And I should see "Purge Assignments"
    And I should see "Default All Incomplete"
    Given the system processes jobs
      And I wait 3 seconds
    When I reload the page
    Then I should not see "Assignments are now being sent out"
      # 6 users and the mod should get emails :)
      And 7 emails should be delivered


  # Notes for understanding the matching here:
  #
  # myname1 requests: SGA (JS, TE), Tiny fandom (Obscure person)
  #         offers: Tiny fandom (Obscure person, JS), Hippos (Any)
  # myname2 requests: Unoffered (no chars), Hippos (no chars)
  #         offers: S&H (JS, TE), SGA (JS, TE)
  # myname3 requests: S&H (JS), Tiny fandom; 
  #           offers: SGA (JS, TE), S&H (JS, TE, Foo)
  # myname4 requests SGA, S&H (JS, TE)
  #     offers Tiny (Obscure, JS), S&H (Foo, JS)
  # myname5 requests SGA, S&H
  #   offers Tiny (Foo, Obscure), SGA (JS, TE)
  # myname6 requests: SGA, S&H
  #    offers: Tiny (Foo, Obscure), SGA (JS, TE)
  #
  # so myname1 is the only person who can write for myname2 and therefore myname2 should be their assignment
  #
  
  # first user starts posting
  When I log out
    And I am logged in as "myname1"
    And I go to myname1's user page
    And all emails have been delivered
    #' stop annoying syntax highlighting after apostrophe
  Then I should see "Assignments (1)"
  When I follow "Assignments"
  Then I should see "Yuletide for myname2" within "dl"
    And I should see "Fulfill"
  When I follow "Fulfill"
  Then I should see "Post New Work"
  When I fill in "Work Title" with "Fulfilling Story 1"
    And I fill in "Fandoms" with "Stargate Atlantis"
    And I select "Not Rated" from "Rating"
    And I check "No Archive Warnings Apply"
    And I select "English" from "Choose a language"
    And I fill in "content" with "This is an exciting story about Atlantis"
  When I press "Preview"
  Then I should see "Preview"
    And 0 emails should be delivered

  # someone looks while it's still a draft
  When I log out
    And I am logged in as "myname2"
    And I go to myname2's user page
    #' stop annoying syntax highlighting after apostrophe
  Then I should see "Gifts (0)"
    And I should not see "Gifts (1)"
  When I follow "Gifts"
  Then I should not see "Stargate Atlantis"
    And I should not see "myname" within "ul.gift"
  When I go to the collections page
    And I follow "Yuletide"
  Then I should see "Works (0)"
    And I should see "Fandoms (0)"
  When I follow "Works (0)"
  Then I should not see "Stargate"
    And I should not see "myname" within "#main"
  When I follow "Fandoms (0)"
  Then I should not see "Stargate"
    And I should not see "myname" within "#main"
  When I follow "Random Items"
  Then I should not see "Stargate"
    And I should not see "myname" within "#main"

  # first user posts the work
  When I log out
    And I am logged in as "myname1"
    And I go to myname1's user page
    #' stop annoying syntax highlighting after apostrophe
    And I follow "Drafts"
  Then I should see "Fulfilling Story 1"
  When I follow "Edit"
    And I fill in "Fandoms" with "Stargate Atlantis"
    And I press "Preview"
  Then I should see "Preview"
    And I should see "Fulfilling Story"
    And I should see "myname" within "#main"
    And I should see "Anonymous"
    And 0 emails should be delivered
  When I press "Post"
    And all indexing jobs have been run
  Then I should see "Work was successfully posted"
    And I should see "For myname"
    And I should see "Collections:"
    And I should see "Yuletide" within ".meta"
    And I should see "Anonymous"
    # notification is still not sent, because it's unrevealed
    And 0 emails should be delivered

  # someone tries to view it
  When I log out
    And I go to myname1's user page
    #' stop annoying syntax highlighting after apostrophe
  Then I should not see "Mystery Work"
    And I should not see "Yuletide"
    And I should not see "Fulfilling Story 1"
    And I should not see "Stargate Atlantis"
  When I follow "Works (0)"
  Then I should not see "Stargate Atlantis"

  # user edits it to undo fulfilling the assignment
  # When I am logged in as "myname1"
  #   And I go to myname1's user page
  #   #' stop highlighting
  # Then I should see "Fulfilling Story"
  # When I follow "Edit"
  # When I uncheck "Yuletide (myname3)"
  #   And I fill in "work_collection_names" with ""
  #   And I fill in "work_recipients" with ""
  # When I press "Preview"
  # Then show me the html
  # Then I should see "Work was successfully updated"

  # post works for all the assignments
  When "myname2" posts the fulfilling story "Fulfilling Story 2" in "Stargate Atlantis"
    And "myname3" posts the fulfilling story "Fulfilling Story 3" in "Tiny Fandom"
    And "myname4" posts the fulfilling story "Fulfilling Story 4" in "Starsky & Hutch, Tiny Fandom"
    And "myname5" posts the fulfilling draft "Fulfilling Story 5" in "Starsky & Hutch"
    And I log out
  Then I should see "Sorry, you don't have permission to access the page you were trying to reach. Please log in."

  # Mod checks for unfulfilled assignments, and gets pinch-hitters to do them.
  When I am logged in as "mod1"
    And I go to the collections page
    And I follow "Yuletide"
    And I follow "Assignments"
  Then I should see "No assignments to review!"
  When I follow "Open"
  Then I should see "myname5" within "dl.index.group"
    And I should see "myname6" within "dl.index.group"
  When I follow "Complete"
  Then I should see "myname1" within "dl.index.group"
    And I should see "myname2" within "dl.index.group"
    And I should see "myname3" within "dl.index.group"
    And I should see "myname4" within "dl.index.group"
    And I should see "Fulfilling Story"
  When I follow "Default All Incomplete"
  Then I should see "All unfulfilled assignments marked as defaulting."
  And I should not see "No assignments to review!"
  When I fill in the 1st field with id matching "cover" with "pinchhitter"
    And I submit
  Then 1 email should be delivered
    And the email should contain "You have been assigned the following request"
    And I should see "Assignment updates complete!"
    And all emails have been delivered    
  When I follow "Pinch Hits"
  Then I should see "pinchhitter"

  # pinch hitter writes story
  When "pinchhitter" posts the fulfilling story "Fulfilling Story pinch" in "Starsky & Hutch"
    And I am logged in as "mod1"
    And I go to "Yuletide" collection's page
    And I follow "Assignments"
    And I follow "Pinch Hits"
  Then I should not see "pinchhitter"
  When I follow "Complete"
  Then I should see "pinchhitter"
    And I should see "Fulfilling Story pinch"

  # mod reveals challenge on Dec 25th
  When I am logged in as "mod1"
    And 0 emails should be delivered
    And all emails have been delivered
    And I go to "Yuletide" collection's page
    And I follow "Collection Settings"
    And I uncheck "This collection is unrevealed"
    And I press "Update"
  Then I should see "Collection was successfully updated"
  Given the system processes jobs
    And I wait 3 seconds
  When I reload the page
  # 5 gift notification emails are delivered for the 5 stories that have been posted so far (4 standard, 1 pinch-hit, 1 still a draft)
  Then 5 emails should be delivered
    And the email should contain "A gift work has been posted for you in the"
    And the email should contain "Yuletide"
    And the email should contain "at the Archive of Our Own"
    And the email should contain "by Anonymous"
    And the email should not contain "by myname1"
    And the email should not contain "by myname2"
    And the email should not contain "by myname3"
    And the email should not contain "by myname4"
    And the email should not contain "by myname5"
    And the email should not contain "by myname6"

  # someone views their gift and it is anonymous
  # Needs everyone to have fulfilled their assignments to be sure of finding a gift
  When I am logged in as "myname2"
    And I go to myname2's user page
    #'
    And I follow "Gifts"
  Then I should see "Anonymous"
    And I should not see "myname1"
    And I should not see "myname3"
    And I should not see "myname4"
    And I should not see "myname5"
    And I should not see "myname6"
    And I should not see "pinchhitter"
  When I follow "Fulfilling Story 1"
  Then I should see the page title "Fulfilling Story 1 - Anonymous - Stargate Atlantis [Example Archive]"
  Then I should see "Anonymous"
    And I should not see "myname1"
    And I should not see "myname3"
    And I should not see "myname4"
    And I should not see "myname5"
    And I should not see "myname6"
    And I should not see "pinchhitter" within ".byline"
    # TODO: Check downloads more thoroughly
  # When I follow "MOBI"
  # Then I should see "Anonymous"
  When I log out
  Then I should see "Successfully logged out"

  # mod reveals authors on Jan 1st
  When I am logged in as "mod1"
    And I go to the collections page
    And I follow "Yuletide"
    And I follow "Collection Settings"
    And I uncheck "This collection is anonymous"
    And I press "Update"
  Then I should see "Collection was successfully updated"

  # someone can now see their writer
  When I log out
    And I am logged in as "myname1"
    And the system processes jobs
    And I go to myname1's user page
    #'
  Then I should see "Fulfilling Story 1"
    And I should not see "Anonymous"
  When I follow "Fulfilling Story 1"
  Then I should not see "Anonymous"
   And I should see "myname" within ".byline"

  When I follow "New Work"
  Then I should not see "Does this fulfill a challenge assignment"
  When I log out
    And I am logged in as "pinchhitter"
    And I follow "New Work"
  Then I should not see "Does this fulfill a challenge assignment"
  When I log out
    And I am logged in as "myname6"
    And I follow "New Work"
  Then I should not see "Does this fulfill a challenge assignment"


