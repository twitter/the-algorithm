
Given /^I have Battle 12 prompt meme set up$/ do
  step %{I am logged in as "mod1"}
  step "I have standard challenge tags setup"
  step "I set up Battle 12 promptmeme collection"
end

Given /^I have Battle 12 prompt meme fully set up$/ do
  step %{I am logged in as "mod1"}
  step "I have standard challenge tags setup"
  step "I set up Battle 12 promptmeme collection"
  step "I fill in Battle 12 challenge options"
end

Given /^I have no-column prompt meme fully set up$/ do
  step %{I am logged in as "mod1"}
  step "I have standard challenge tags setup"
  step "I set up Battle 12 promptmeme collection"
  step "I fill in no-column challenge options"
end

Given /^I have single-prompt prompt meme fully set up$/ do
  step %{I am logged in as "mod1"}
  step "I have standard challenge tags setup"
  step "I set up Battle 12 promptmeme collection"
  step "I fill in single-prompt challenge options"
end

Given /^everyone has signed up for Battle 12$/ do
  # no anon
  step %{I am logged in as "myname1"}
  step %{I sign up for Battle 12 with combination A}

  # both anon
  step %{I am logged in as "myname2"}
  step %{I sign up for Battle 12 with combination B}

  # one anon
  step %{I am logged in as "myname3"}
  step %{I sign up for Battle 12}

  # no anon
  step %{I am logged in as "myname4"}
  step %{I sign up for Battle 12 with combination C}
end

Given /^an anon has signed up for Battle 12$/ do
  # both anon
  step %{I am logged in as "myname2"}
  step %{I sign up for Battle 12 with combination B}
end

Given /^"([^\"]*)" has signed up for Battle 12 with combination ([^\"]*)$/ do |username, combo|
  step %{I am logged in as "#{username}"}
  step %{I sign up for Battle 12 with combination #{combo}}
end

Given /^"([^\"]*)" has signed up for Battle 12 with one more prompt than required$/ do |username|
  step %{I am logged in as "#{username}"}
  step %{I sign up for Battle 12 with combination C}
  step %{I add a new prompt to my signup for a prompt meme}
end

Given /^"([^\"]*)" has fulfilled a claim from Battle 12$/ do |username|
  step %{"#{username}" has claimed a prompt from Battle 12}
  step %{I fulfill my claim}
end

Given /^"([^\"]*)" has deleted their sign up for the prompt meme "([^\"]*)"$/ do |username, challenge_title|
  step %{I am logged in as "#{username}"}
  step %{I delete my signup for the prompt meme "#{challenge_title}"}
end

Given /^"([^\"]*)" has claimed a prompt from Battle 12$/ do |username|
  step %{I am logged in as "#{username}"}
  step %{I claim a prompt from "Battle 12"}
end

When /^I set up an?(?: ([^"]*)) promptmeme "([^\"]*)"(?: with name "([^"]*)")?$/ do |type, title, name|
  step %{I am logged in as "mod1"}
  visit new_collection_path
  if name.nil?
    fill_in("collection_name", with: "promptcollection")
  else
    fill_in("collection_name", with: name)
  end
  fill_in("collection_title", with: title)
  if type == "anon"
    check("This collection is unrevealed")
    check("This collection is anonymous")
  end
  select("Prompt Meme", from: "challenge_type")
  step %{I submit}
  step "I should see \"Collection was successfully created\""

  check("prompt_meme_signup_open")
  fill_in("prompt_meme_requests_num_allowed", with: ArchiveConfig.PROMPT_MEME_PROMPTS_MAX)
  fill_in("prompt_meme_requests_num_required", with: 1)
  fill_in("prompt_meme_request_restriction_attributes_fandom_num_required", with: 1)
  fill_in("prompt_meme_request_restriction_attributes_fandom_num_allowed", with: 2)
  fill_in("Sign-up opens:", with: Date.yesterday)
  fill_in("Sign-up closes:", with: Date.tomorrow)
  step %{I submit}
  step "I should see \"Challenge was successfully created\""
end

When /^I set up Battle 12 promptmeme collection$/ do
  step %{I am logged in as "mod1"}
  visit new_collection_path
  fill_in("collection_name", with: "lotsofprompts")
  fill_in("collection_title", with: "Battle 12")
  fill_in("Introduction", with: "Welcome to the meme")
  fill_in("FAQ", with: "<dl><dt>What is this thing?</dt><dd>It is a comment fic thing</dd></dl>")
  fill_in("Rules", with: "Be nicer to people")
  check("This collection is unrevealed")
  check("This collection is anonymous")
  select("Prompt Meme", from: "challenge_type")
  step %{I submit}
  step "I should see \"Collection was successfully created\""
end

When /^I create Battle 12 promptmeme$/ do
  step "I set up Battle 12 promptmeme collection"
  step "I fill in Battle 12 challenge options"
end

When /^I fill in Battle 12 challenge options$/ do
  step "I fill in prompt meme challenge options"
    step %{I fill in "Sign-up Instructions" with "Please request easy things"}
    fill_in("Sign-up opens:", with: Date.yesterday)
    fill_in("Sign-up closes:", with: Date.tomorrow)
    step %{I select "(GMT-05:00) Eastern Time (US & Canada)" from "Time zone"}
    step %{I fill in "prompt_meme_requests_num_allowed" with "3"}
    check("prompt_meme_request_restriction_attributes_title_allowed")
    step %{I submit}
end

When /^I fill in future challenge options$/ do
  step "I fill in prompt meme challenge options"
    fill_in("Sign-up opens:", with: Date.yesterday)
    fill_in("Sign-up closes:", with: Date.tomorrow)
    step %{I fill in "prompt_meme_requests_num_allowed" with "3"}
    step %{I uncheck "Sign-up open?"}
    step %{I submit}
end

When /^I fill in past challenge options$/ do
  step "I fill in prompt meme challenge options"
    step %{I fill in "Sign-up opens" with "2010-09-20 12:40AM"}
    step %{I fill in "Sign-up closes" with "2010-09-20 12:40AM"}
    step %{I fill in "prompt_meme_requests_num_allowed" with "3"}
    step %{I uncheck "Sign-up open?"}
    step %{I submit}
end

When /^I fill in no-column challenge options$/ do
  step %{I fill in "prompt_meme_requests_num_required" with "1"}
    step %{I fill in "prompt_meme_request_restriction_attributes_fandom_num_allowed" with "0"}
    step %{I fill in "prompt_meme_request_restriction_attributes_character_num_allowed" with "0"}
    step %{I fill in "prompt_meme_request_restriction_attributes_relationship_num_allowed" with "0"}
    step %{I check "Sign-up open?"}
    fill_in("Sign-up opens:", with: Date.yesterday)
    fill_in("Sign-up closes:", with: Date.tomorrow)
    step %{I submit}
end

When /^I fill in single-prompt challenge options$/ do
  step %{I fill in "prompt_meme_requests_num_required" with "1"}
    step %{I check "Sign-up open?"}
    check("prompt_meme_request_restriction_attributes_title_allowed")
    fill_in("Sign-up opens:", with: Date.yesterday)
    fill_in("Sign-up closes:", with: Date.tomorrow)
    step %{I submit}
end

When /^I fill in multi-prompt challenge options$/ do
  step "I fill in prompt meme challenge options"
    step %{I fill in "prompt_meme_requests_num_allowed" with "4"}
    step %{I submit}
end

When /^I fill in prompt meme challenge options$/ do
  step %{I fill in "General Sign-up Instructions" with "Here are some general tips"}
    fill_in("Tag Sets To Use:", with: "Standard Challenge Tags")
    step %{I fill in "prompt_meme_request_restriction_attributes_fandom_num_required" with "1"}
    step %{I fill in "prompt_meme_request_restriction_attributes_fandom_num_allowed" with "1"}
    step %{I fill in "prompt_meme_request_restriction_attributes_freeform_num_allowed" with "2"}
    step %{I fill in "prompt_meme_requests_num_required" with "2"}
    step %{I check "Sign-up open?"}
    fill_in("Sign-up opens:", with: Date.yesterday)
    fill_in("Sign-up closes:", with: Date.tomorrow)
end

When /^I allow (\d+) prompts$/ do |number|
  fill_in("prompt_meme_requests_num_allowed", with: number)
end

When /^I require (\d+) prompts$/ do |number|
  fill_in("prompt_meme_requests_num_required", with: number)
end

When /^I sign up for Battle 12$/ do
  step %{I start signing up for "Battle 12"}
    step %{I check the 1st checkbox with the value "Stargate SG-1"}
    step %{I check the 2nd checkbox with the value "Stargate SG-1"}
    step %{I check the 2nd checkbox with id matching "anonymous"}
    step %{I fill in the 1st field with id matching "freeform_tagnames" with "Something else weird"}
    step %{I fill in the 1st field with id matching "title" with "crack"}
    # We have to use explicit button names because there are two forms on this page - the form to expand prompts
    click_button "Submit"
end

When /^I sign up for Battle 12 with combination A$/ do
  step %{I start signing up for "Battle 12"}
    step %{I check the 1st checkbox with the value "Stargate Atlantis"}
    step %{I check the 2nd checkbox with the value "Stargate Atlantis"}
    step %{I fill in the 1st field with id matching "freeform_tagnames" with "Alternate Universe - Historical"}
    click_button "Submit"
end

When /^I sign up for Battle 12 with combination B$/ do
  step %{I start signing up for "Battle 12"}
    step %{I check the 1st checkbox with the value "Stargate SG-1"}
    step %{I check the 2nd checkbox with the value "Stargate Atlantis"}
    step %{I check the 1st checkbox with id matching "anonymous"}
    step %{I check the 2nd checkbox with id matching "anonymous"}
    step %{I fill in the 1st field with id matching "freeform_tagnames" with "Alternate Universe - High School, Something else weird"}
    step %{I fill in the 1st field with id matching "title" with "High School AU SG1"}
    step %{I fill in the 2nd field with id matching "title" with "random SGA love"}
    click_button "Submit"
  step %{I should see "Sign-up was successfully created"}
end

When /^I sign up for Battle 12 with combination C$/ do
  step %{I start signing up for "Battle 12"}
    step %{I check the 1st checkbox with the value "Stargate Atlantis"}
    step %{I check the 2nd checkbox with the value "Stargate Atlantis"}
    step %{I fill in the 1st field with id matching "freeform_tagnames" with "Something else weird, Alternate Universe - Historical"}
    step %{I fill in the 1st field with id matching "title" with "weird SGA history AU"}
    step %{I fill in the 2nd field with id matching "title" with "canon SGA love"}
    click_button "Submit"
  step %{I should see "Sign-up was successfully created"}
    step %{I should see "Stargate Atlantis"}
    step %{I should see "Something else weird"}
end

When /^I sign up for Battle 12 with combination D$/ do
  step %{I start signing up for "Battle 12"}
    step %{I check the 1st checkbox with the value "Stargate Atlantis"}
    step %{I check the 2nd checkbox with the value "Stargate Atlantis"}
    click_button "Submit"
end

When /^I sign up for Battle 12 with combination E$/ do
  step "I go to the collections page"
    step "I follow \"Battle 12\""
    step "I follow \"Sign Up\""
    step %{I fill in "Description:" with "Weird description"}
    step "I press \"Submit\""
end

When /^I sign up for "([^\"]*)" fixed-fandom prompt meme$/ do |title|
  visit collection_path(Collection.find_by(title: title))
  step %{I follow "Sign Up"}
    step %{I check the 1st checkbox with value "Stargate SG-1"}
    step %{I check the 2nd checkbox with value "Stargate SG-1"}
    step %{I check the 2nd checkbox with id matching "anonymous"}
    step %{I fill in the 1st field with id matching "freeform_tagnames" with "Something else weird"}
    click_button "Submit"
end

When /^I sign up for "([^\"]*)" many-fandom prompt meme$/ do |title|
  visit collection_path(Collection.find_by(title: title))
  step %{I follow "Sign Up"}
    step %{I fill in the 1st field with id matching "fandom_tagnames" with "Stargate Atlantis"}
    step %{I check the 1st checkbox with id matching "anonymous"}
    click_button "Submit"
end

When /^I add prompt (\d+)$/ do |number|
  step %{I add prompt #{number} with "Stargate Atlantis"}
end

When /^I add prompt (\d+) with "([^"]+)"$/ do |number, tag|
  step %{I follow "Add Prompt"}
  step %{I should see "Request #{number}"}
  step %{I check the 1st checkbox with the value "#{tag}"}
    # there is only one form on the individual prompt page
    step %{I submit}
  step %{I should see "Prompt was successfully added"}
end

When /^I add prompts up to (\d+) starting with (\d+)$/ do |final_number_of_prompts, start|
  @index = start
  while @index <= final_number_of_prompts
    step "I add prompt #{@index}"
    @index = @index + 1
  end
end

When /^I fill in the missing prompt$/ do
  step %{I check the 2nd checkbox with the value "Stargate Atlantis"}
    click_button "Submit"
end

When /^I add a new prompt to my signup$/ do
  step %{I follow "Add Prompt"}
    step %{I check "Stargate Atlantis"}
    step %{I fill in the 1st field with id matching "freeform_tagnames" with "My extra tag"}
    step %{I press "Submit"}
end

When /^I add a new prompt to my signup for a prompt meme$/ do
  step %{I follow "Add Prompt"}
    step %{I check "Stargate Atlantis"}
    step %{I press "Submit"}
end

When /^I edit the signup by "([^\"]*)"$/ do |participant|
  visit collection_path(Collection.find_by(title: "Battle 12"))
  step %{I follow "Prompts ("}
  step %{I follow "Edit Sign-up"}
end

### WHEN viewing after signups

When /^I view my signup for "([^\"]*)"$/ do |title|
  visit collection_path(Collection.find_by(title: title))
  step %{I follow "My Prompts"}
end

When /^I view unposted claims for "([^\"]*)"$/ do |title|
  visit collection_path(Collection.find_by(title: title))
  # step %{show me the sidebar}
  step %{I follow "Unposted Claims ("}
end

When /^I view prompts for "([^\"]*)"$/ do |title|
  visit collection_path(Collection.find_by(title: title))
  step %{I follow "Prompts ("}
end

### WHEN claiming

When /^I claim a prompt from "([^\"]*)"$/ do |title|
  visit collection_path(Collection.find_by(title: title))
    step %{I follow "Prompts ("}
    step %{I press "Claim"}
end

When /^I claim two prompts from "([^\"]*)"$/ do |title|
  step %{I claim a prompt from "#{title}"}
  step %{I claim a prompt from "#{title}"}
end

### WHEN fulfilling claims

When /^I start to fulfill my claim with "([^\"]*)"$/ do |title|
  step %{I am on my user page}
  step %{I follow "Claims ("}
  step %{I follow "Fulfill"}
    step %{I fill in "Work Title" with "#{title}"}
    step %{I select "Not Rated" from "Rating"}
    step %{I check "No Archive Warnings Apply"}
    step %{I select "English" from "Choose a language"}
    step %{I fill in "Fandom" with "Stargate Atlantis"}
    step %{I fill in "content" with "This is an exciting story about Atlantis"}
end

When /^I start to fulfill my claim$/ do
  step %{I start to fulfill my claim with "Fulfilled Story"}
end

When /^I fulfill my claim$/ do
  step %{I start to fulfill my claim with "Fulfilled Story"}
  step %{I press "Preview"}
    step %{I press "Post"}
  step %{I should see "Work was successfully posted"}
end

When /^I fulfill my claim again$/ do
  step %{I am on my user page}
  step %{I follow "Claims ("}
  step %{I follow "Fulfilled Claims"}
  step %{I follow "Fulfill"}
  step %{I fill in "Work Title" with "Second Story"}
    step %{I select "Not Rated" from "Rating"}
    step %{I check "No Archive Warnings Apply"}
    step %{I select "English" from "Choose a language"}
    step %{I fill in "Fandom" with "Stargate Atlantis"}
    step %{I fill in "content" with "This is an exciting story about Atlantis"}
  step %{I press "Preview"}
    step %{I press "Post"}
  step %{I should see "Work was successfully posted"}
end

When /^mod fulfills claim$/ do
  step %{I am logged in as "mod1"}
  step %{I claim a prompt from "Battle 12"}
  step %{I start to fulfill my claim}
    step %{I fill in "Work Title" with "Fulfilled Story-thing"}
    step %{I fill in "content" with "This is an exciting story about Atlantis, but in a different universe this time"}
  step %{I press "Preview"}
    step %{I press "Post"}
end

When /^I delete my prompt in "([^\"]*)"$/ do |title|
  visit collection_path(Collection.find_by(title: title))
  step %{I follow "Prompts ("}
  step %{I press "Delete Prompt"}
end

When /^I delete the prompt by "([^\"]*)"$/ do |participant|
  visit collection_path(Collection.find_by(title: "Battle 12"))
  step %{I follow "Prompts ("}
  step %{I follow "Delete Prompt"}
end

When /^I edit the first prompt$/ do
  visit collection_path(Collection.find_by(title: "Battle 12"))
  step %{I follow "Prompts ("}
  # The 'Edit Sign-up' and 'Edit Prompt' buttons were removed for mods in
  # Prompt Meme challenges
  #step %{I follow "Edit Prompt"}
end

Then /^I should see prompt meme options$/ do
  step %{I should not see "Offer Settings"}
    step %{I should see "Request Settings"}
    step %{I should not see "If you plan to use automated matching"}
    step %{I should not see "Allow Any"}
end

Then /^I should see a prompt is claimed$/ do
  # note, prompts are in reverse date order by default
  step %{I should see "New claim made."}
    step %{I should see "My Claims in Battle 12"}
    step %{I should see "Fulfill"}
    step %{I should see "Drop Claim"}

  # Claims in the user page are just the prompts that have been claimed
  step "I am on my user page"
    step %{I follow "Claims"}
  step %{I should see "Fulfill"}
    step %{I should see "by Anonymous"}
    step %{I should not see "myname" within ".index"}
end

Then /^I should see correct signups for Battle 12$/ do
  step %{I should see "myname4"}
    step %{I should see "myname3"}
    step %{I should not see "myname2"}
    step %{I should see "by Anonymous"}
    step %{I should see "myname1"}
    step %{I should see "Stargate Atlantis"}
    step %{I should see "Stargate SG-1"}
    step %{I should see "Something else weird"}
    step %{I should see "Alternate Universe - Historical"}
    step %{I should not see "Matching"}
end

Then /^claims are hidden$/ do
  step %{I go to "Battle 12" collection's page}
    step %{I follow "Unposted Claims"}
  step %{I should see "Unposted Claims"}
    step %{I should see "Fulfilled Claims"}
    step %{I should see "myname" within ".claims"}
    step %{I should see "Secret!" within ".claims"}
    step %{I should see "Stargate Atlantis"}
end

Then /^claims are shown$/ do
  step %{I go to "Battle 12" collection's page}
    step %{I follow "Unposted Claims"}
  step %{I should see "myname4" within "h5"}
    step %{I should not see "Secret!"}
    step %{I should see "Stargate Atlantis"}
end

Then /^Battle 12 prompt meme should be correctly created$/ do
  step %{I should see "Challenge was successfully created"}
  step "signup should be open"
  step %{"Battle 12" collection exists}
  step %{I should see "(Open, Unmoderated, Unrevealed, Anonymous, Prompt Meme Challenge)"}
end

Then /^my claim should be fulfilled$/ do
  step %{I should see "Work was successfully posted"}
    step %{I should see "Fandom:"}
    step %{I should see "Stargate Atlantis"}
    step %{I should not see "Alternate Universe - Historical"}
    step %{I should see "In response to a prompt by"}
end

Then /^I should see the whole signup$/ do
  page.should have_content("Sign-up for")
  page.should have_content("Requests")
  page.should have_content("Request 1")
  page.should have_content("Request 2")
end

Then /^I should see single prompt editing$/ do
  page.should have_content("Edit Sign-up")
  page.should have_content("Additional Tags")
  step %{the field labeled "Additional Tags" should contain "Alternate Universe - Historical"}
  page.should have_no_content("Just add one new prompt instead")
end

Then /^I should see Battle 12 descriptions$/ do
  step %{I should see "Welcome to the meme" within "#intro"}
  step %{I should see "Sign-up: Open"}
  step %{I should see "Sign-up Closes:"}
  step %{I should see "#{Time.now.year}" within ".collection .meta"}
  step %{I should see "What is this thing?" within "#faq"}
  step %{I should see "It is a comment fic thing" within "#faq"}
  step %{I should see "Be nicer to people" within "#rules"}
end

Then /^I should be editing the challenge settings$/ do
  step %{I should see "Setting Up the Battle 12 Prompt Meme"}
end

Then "{int} prompt(s) should be required" do |number|
  expect(page).to have_field("prompt_meme_requests_num_required", with: number.to_s)
end

Then "{int} prompt(s) should be allowed" do |number|
  expect(page).to have_field("prompt_meme_requests_num_allowed", with: number.to_s)
end

Then /^I should not see the prompt meme dashboard for "([^\"]*)"$/ do |challenge_title|
  collection = Collection.find_by(title: challenge_title)
  visit collection_path(collection)
  step %{I should not see "Prompt Meme" within "#dashboard"}
  step %{I should not see "Prompts" within "#dashboard"}
  step %{I should not see "My Prompts" within "#dashboard"}
  step %{I should not see "Prompt Form" within "#dashboard"}
  step %{I should not see "My Claims" within "#dashboard"}
  step %{I should not see "Unposted Claims" within "#dashboard"}
  step %{I should not see "Challenge Settings" within "#dashboard"}
end

Then /^no one should have a claim in "([^\"]*)"$/ do |challenge_title|
  collection = Collection.find_by(title: challenge_title)
  if collection.present?
    User.all.each do |user|
      user.request_claims.in_collection(collection).should be_empty
    end
  # we don't have a collection id because the collection has been deleted
  # so let's make sure any remaining claims are for exisiting collections
  else
    ChallengeClaim.all.each do |claim|
      collection_id = claim.collection_id
      Collection.find_by(id: collection_id).should_not be_nil
    end
  end
end
