###### ERROR MESSAGES
Then /^I should see a not\-in\-fandom error message for "([^"]+)" in "([^"]+)"$/ do |tag, fandom|
  step %{I should see "are not in the selected fandom(s), #{fandom}: #{tag}"}
end

Then /^I should see a not\-in\-fandom error message$/ do
  step %{I should see "are not in the selected fandom(s)"}
end

### Set up dates correctly ###
Then /^I set up the challenge dates$/ do
  fill_in("Sign-up opens:", with: Date.yesterday)
  fill_in("Sign-up closes:", with: Date.tomorrow)
end

### Clear out old data

Given /^I have no prompts$/ do
  Prompt.delete_all
end

### CHALLENGE TAGS

Given /^signup summaries are always visible$/ do
  stub_const("ArchiveConfig", OpenStruct.new(ArchiveConfig))
  ArchiveConfig.ANONYMOUS_THRESHOLD_COUNT = 0
end

Given /^all signup summaries are delayed$/ do
  stub_const("ArchiveConfig", OpenStruct.new(ArchiveConfig))
  ArchiveConfig.MAX_SIGNUPS_FOR_LIVE_SUMMARY = 0
end

Given /^all signup summaries are live$/ do
  stub_const("ArchiveConfig", OpenStruct.new(ArchiveConfig))
  ArchiveConfig.MAX_SIGNUPS_FOR_LIVE_SUMMARY = 1_000_000
end

Given /^I have standard challenge tags set ?up$/ do
  begin
    unless UserSession.find
      step %{I am logged in as "mod1"}
    end
  rescue
    step %{I am logged in as "mod1"}
  end
  step "I have no tags"
    step "basic tags"
    step %{a canonical fandom "Stargate Atlantis"}
    step %{a canonical fandom "Stargate SG-1"}
    step %{a canonical fandom "Bad Choice"}
    step %{a canonical character "John Sheppard"}
    step %{a canonical freeform "Alternate Universe - Historical"}
    step %{a canonical freeform "Alternate Universe - High School"}
    step %{a canonical freeform "Something else weird"}
    step %{a canonical freeform "My extra tag"}
    step %{I set up the tag set "Standard Challenge Tags" with the fandom tags "Stargate Atlantis, Stargate SG-1, Bad Choice", the character tag "John Sheppard"}
end

Given /^I have Yuletide challenge tags set ?up$/ do
  step "I have standard challenge tags setup"
    step %{I add the fandom tags "Starsky & Hutch, Tiny fandom, Care Bears, Yuletide Hippos RPF, Unoffered, Unrequested" to the tag set "Standard Challenge Tags"}
    step %{a canonical fandom "Starsky & Hutch"}
    step %{a canonical fandom "Tiny fandom"}
    step %{a canonical fandom "Care Bears"}
    step %{a canonical fandom "Yuletide Hippos RPF"}
    step %{a canonical fandom "Unoffered"}
    step %{a canonical fandom "Unrequested"}
end

### General Challenge Settings

When /^I edit settings for "([^\"]*)" challenge$/ do |title|
  visit collection_path(Collection.find_by(title: title))
  step %{I follow "Challenge Settings"}
end

# Timezone

When /^I change the challenge timezone to Alaska$/ do
  step %{I follow "Challenge Settings"}
    step %{I select "(GMT-09:00) Alaska" from "prompt_meme_time_zone"}
    step %{I submit}
    step %{I should see "Challenge was successfully updated"}
end

Then /^I should see both timezones$/ do
  step %{I follow "Profile"}
  step %{I should see "EST ("}
  step %{I should see "AKST)"}
end

Then /^I should see just one timezone$/ do
  step %{I follow "Profile"}
  step %{I should see "Sign-up: Open"}
  step %{I should not see "EST" within "#main"}
  step %{I should see "AKST" within "#main"}
end

# Open signup

When /^I open signups for "([^\"]*)"$/ do |title|
  step %{I am logged in as "mod1"}
  visit collection_path(Collection.find_by(title: title))
  step %{I follow "Challenge Settings"}
    step %{I check "Sign-up open?"}
    step %{I submit}
  step %{I should see "Challenge was successfully updated"}
end

Then /^signup should be open$/ do
  step %{I should see "Profile" within "div#main .collection .navigation"}
  step %{I should see "Sign-up: Open" within ".collection .meta"}
    step %{I should see "Sign-up Closes:"}
end

When /^I view open challenges$/ do
  step "I go to the collections page"
  step %{I follow "Open Challenges"}
end

### Signup process

When /^I start signing up for "([^\"]*)"$/ do |title|
  visit collection_path(Collection.find_by(title: title))
  step %{I follow "Sign Up"}
end

When /^I view the sign-up summary for "(.*?)"$/ do |title|
  visit collection_path(Collection.find_by(title: title))
  step %(I follow "Sign-up Summary")
end

### Editing signups

When /^I edit my signup for "([^\"]*)"$/ do |title|
  visit collection_path(Collection.find_by(title: title))
  step %{I follow "Edit Sign-up"}
end

### WHEN other

When /^I close signups for "([^\"]*)"$/ do |title|
  collection = Collection.find_by(title: title)
  user_id = collection.all_owners.first.user_id
  mod_login = User.find_by(id: user_id).login
  step %{I am logged in as "#{mod_login}"}
  visit collection_path(collection)
  step %{I follow "Challenge Settings"}
    step %{I uncheck "Sign-up open?"}
    step %{I press "Update"}
  step %{I should see an update confirmation message}
end

When /^I delete my signup for the prompt meme "([^\"]*)"$/ do |title|
  visit collection_path(Collection.find_by(title: title))
  step %{I follow "My Prompts"}
  step %{I delete the signup}
end

When /^I delete my signup for the gift exchange "([^\"]*)"$/ do |title|
  visit collection_path(Collection.find_by(title: title))
  step %{I follow "My Sign-up"}
  step %{I delete the signup}
end

When /^I delete the signup by "([^\"]*)"$/ do |participant|
  click_link("#{participant}")
  step %{I delete the signup}
end

When /^I delete the signup$/ do
  step %{I follow "Delete Sign-up"}
  step %{I press "Yes, Delete Sign-up"}
  step %{I should see "Challenge sign-up was deleted."}
end

When /^I reveal the "([^\"]*)" challenge$/ do |title|
  step %{I am logged in as "mod1"}
  visit collection_path(Collection.find_by(title: title))
    step %{I follow "Collection Settings"}
    step %{I uncheck "This collection is unrevealed"}
    step %{I press "Update"}
end

When /^I approve the first item in the collection "([^\"]*)"$/ do |collection|
  collection = Collection.find_by(title: collection)
  collection_item = collection.collection_items.first.id
  visit collection_path(collection)
  step %{I follow "Manage Items"}
  step %{I select "Approved" from "collection_items_#{collection_item}_collection_approval_status"}
  step %{I press "Submit"}
end


When /^I reveal the authors of the "([^\"]*)" challenge$/ do |title|
  step %{I am logged in as "mod1"}
  visit collection_path(Collection.find_by(title: title))
    step %{I follow "Collection Settings"}
    step %{I uncheck "This collection is anonymous"}
    step %{I press "Update"}
    step %{all indexing jobs have been run}
end

When /^I use tomorrow as the "Sign-up closes" date$/ do
  fill_in("Sign-up closes:", with: Date.tomorrow)
end

# Notification messages

When /^I create an assignment notification message with (an ampersand|linebreaks) for "([^\"]*)"$/ do |message_content, title|
  c = Collection.find_by(title: title)
  field = "collection_collection_profile_attributes_assignment_notification"
  message = if message_content == "an ampersand"
              "The first thing & the second thing."
            else
              "First Line\nSecond Line"
            end

  step %{I am logged in as "#{c.owners.first.name}"}
  visit collection_path(c)

  # TODO: Once AO3-3376 is fixed, this will need to change.
  step %{I follow "Collection Settings"}

  fill_in(field, with: message)
  step %{I press "Update"}
end

Then /^the notification message to "([^\"]*)" should contain linebreaks$/ do |user|
  @user = User.find_by(login: user)
  email = emails("to: \"#{email_for(@user.email)}\"").first
  email.multipart?.should be == true

  text_lines = email.text_part.body.to_s.split("\n")
  html_lines = email.html_part.body.to_s.split(%r{<(?:\/?p|br|div)\b[^>]*>}i)

  (text_lines + html_lines).each do |line|
    # We shouldn't see "First Line" and "Second Line" on the same line.
    line.should_not =~ /Second Line/ if line =~ /First Line/
  end

  email.text_part.body.should =~ /First Line/
  email.text_part.body.should =~ /Second Line/
  email.html_part.body.should =~ /First Line/
  email.html_part.body.should =~ /Second Line/
end

Then /^the notification message to "([^\"]*)" should escape the ampersand$/ do |user|
  @user = User.find_by(login: user)
  email = emails("to: \"#{email_for(@user.email)}\"").first
  email.multipart?.should be == true

  email.html_part.body.should =~ /The first thing &amp; the second thing./
  email.html_part.body.should_not =~ /The first thing & the second thing./
end

Then /^the notification message to "([^\"]*)" should contain the no archive warnings tag$/ do |user|
  @user = User.find_by(login: user)
  email = emails("to: \"#{email_for(@user.email)}\"").first
  email.multipart?.should be == true

  email.text_part.body.should =~ /Warning:/
  email.text_part.body.should =~ /No Archive Warnings Apply/
  email.text_part.body.should_not =~ /Name with colon/

  email.html_part.body.should =~ /Warning:/
  email.html_part.body.should =~ /No Archive Warnings Apply/
  email.html_part.body.should_not =~ /Name with colon/
end

# Delete challenge

Given /^the challenge "([^\"]*)" is deleted$/ do |challenge_title|
  collection = Collection.find_by(title: challenge_title)
  collection.challenge.destroy
end

When /^I delete the challenge "([^\"]*)"$/ do |challenge_title|
  step %{I edit settings for "#{challenge_title}" challenge}
  step %{I follow "Delete Challenge"}
end

Then /^no one should be signed up for "([^\"]*)"$/ do |challenge_title|
  collection = Collection.find_by(title: challenge_title)
  if collection.present?
    User.all.each do |user|
      user.challenge_signups.in_collection(collection).should be_empty
    end
  # we don't have a collection id because the collection has been deleted
  # so let's make sure any remaining sign ups are for exisiting collections
  else
    ChallengeSignup.all.each do |signup|
      collection_id = signup.collection_id
      Collection.find_by(id: collection_id).should_not be_nil
    end
  end
end
