# Set up a new/unsaved gift exchange

Given /^I have set up the gift exchange "([^\"]*)" with name "([^\"]*)"$/ do |challengename, name|
  step %{I am logged in as "mod1"}
    step "I have standard challenge tags setup"
    step %{I set up the collection "#{challengename}" with name "#{name}"}
    step %{I select "Gift Exchange" from "challenge_type"}
  click_button("Submit")
end

Given /^I have set up the gift exchange "([^\"]*)"$/ do |challengename|
  step %{I have set up the gift exchange "#{challengename}" with name "#{challengename.gsub(/[^\w]/, '_')}"}
end

Then /^"([^\"]*)" gift exchange should be correctly created$/ do |title|
  step %{I should see "Collection was successfully created"}
  step %{I should see "Setting Up the #{title} Gift Exchange"}
  step %{I should see "Offer Settings"}
  step %{I should see "Request Settings"}
  step %{I should see "If you plan to use automated matching"}
  step %{I should see "Allow Any"}
end

Then /^I should see gift exchange options$/ do
  step %{I should see "Offer Settings"}
    step %{I should see "Request Settings"}
    step %{I should see "If you plan to use automated matching"}
    step %{I should see "Allow Any"}
end

# Create and save a gift exchange with some common options

Given /^I have created the gift exchange "([^\"]*)" with name "([^\"]*)"$/ do |challengename, name|
  step %{I have set up the gift exchange "#{challengename}" with name "#{name}"}
  step "I fill in gift exchange challenge options"
    step "I submit"
  step %{I should see "Challenge was successfully created"}
end

Given /^I have created the gift exchange "([^\"]*)"$/ do |challengename|
  step %{I have created the gift exchange "#{challengename}" with name "#{challengename.gsub(/[^\w]/, '_')}"}
end

Given /^I have created the tagless gift exchange "([^\"]*)" with name "([^\"]*)"$/ do |challengename, name|
  step %{I have set up the gift exchange "#{challengename}" with name "#{name}"}
  step "I submit"
  step %{I should see "Challenge was successfully created"}
end

Given /^I have created the tagless gift exchange "([^\"]*)"$/ do |challengename|
  step %{I have created the tagless gift exchange "#{challengename}" with name "#{challengename.gsub(/[^\w]/, '_')}"}
end

When /^I fill in gift exchange challenge options$/ do
  current_date = DateTime.current
  fill_in("Sign-up opens", with: "#{current_date.months_ago(2)}")
    fill_in("Sign-up closes", with: "#{current_date.years_since(1)}")
    select("(GMT-05:00) Eastern Time (US & Canada)", from: "gift_exchange_time_zone")
    fill_in("Tag Sets To Use:", with: "Standard Challenge Tags")
    fill_in("gift_exchange_request_restriction_attributes_fandom_num_required", with: "1")
    fill_in("gift_exchange_request_restriction_attributes_fandom_num_allowed", with: "1")
    fill_in("gift_exchange_request_restriction_attributes_freeform_num_allowed", with: "2")
    fill_in("gift_exchange_offer_restriction_attributes_fandom_num_required", with: "1")
    fill_in("gift_exchange_offer_restriction_attributes_fandom_num_allowed", with: "1")
    fill_in("gift_exchange_offer_restriction_attributes_freeform_num_allowed", with: "2")
    select("1", from: "gift_exchange_potential_match_settings_attributes_num_required_fandoms")
end

When /^I fill in single-fandom gift exchange challenge options$/ do
  current_date = DateTime.current
  fill_in("Sign-up opens", with: current_date.months_ago(2).to_s)
  fill_in("Sign-up closes", with: current_date.years_since(1).to_s)
  select("(GMT-05:00) Eastern Time (US & Canada)", from: "gift_exchange_time_zone")
  fill_in("gift_exchange_request_restriction_attributes_fandom_num_required", with: "1")
  fill_in("gift_exchange_request_restriction_attributes_fandom_num_allowed", with: "1")
  fill_in("gift_exchange_request_restriction_attributes_character_num_required", with: "1")
  fill_in("gift_exchange_request_restriction_attributes_character_num_allowed", with: "3")
  fill_in("gift_exchange_request_restriction_attributes_relationship_num_allowed", with: "3")
  fill_in("gift_exchange_request_restriction_attributes_rating_num_allowed", with: "5")
  fill_in("gift_exchange_request_restriction_attributes_category_num_allowed", with: "5")
  fill_in("gift_exchange_request_restriction_attributes_archive_warning_num_allowed", with: "5")
  fill_in("gift_exchange_request_restriction_attributes_freeform_num_allowed", with: "2")
  fill_in("gift_exchange_offer_restriction_attributes_fandom_num_required", with: "1")
  fill_in("gift_exchange_offer_restriction_attributes_fandom_num_allowed", with: "1")
  fill_in("gift_exchange_offer_restriction_attributes_character_num_allowed", with: "3")
  fill_in("gift_exchange_offer_restriction_attributes_freeform_num_allowed", with: "2")
  select("1", from: "gift_exchange_potential_match_settings_attributes_num_required_characters")
  check("gift_exchange_offer_restriction_attributes_allow_any_rating")
  check("gift_exchange_offer_restriction_attributes_allow_any_category")
  check("gift_exchange_offer_restriction_attributes_allow_any_archive_warning")
  check("gift_exchange_offer_restriction_attributes_character_restrict_to_fandom")
  check("gift_exchange_offer_restriction_attributes_relationship_restrict_to_fandom")
end

When /^I allow warnings in my gift exchange$/ do
  fill_in("gift_exchange_request_restriction_attributes_archive_warning_num_allowed", with: "1")
  check("gift_exchange_request_restriction_attributes_allow_any_archive_warning")
  fill_in("gift_exchange_offer_restriction_attributes_archive_warning_num_allowed", with: "1")
  check("gift_exchange_offer_restriction_attributes_allow_any_archive_warning")
end

Then /^"([^\"]*)" gift exchange should be fully created$/ do |title|
  step %{I should see a create confirmation message}
  step %{"#{title}" collection exists}
  step %{I should see "(Open, Unmoderated, Gift Exchange Challenge)"}
end

Given /^the gift exchange "([^\"]*)" is ready for signups$/ do |title|
  step %{I am logged in as "mod1"}
  step %{I have created the gift exchange "#{title}"}
  step %{I open signups for "#{title}"}
end

# This is going to make broken assignments a la AO3-5748
Given /^"(.*?)" has two pinchhit assignments in the gift exchange "(.*?)"$/ do |user, collection_title|
  collection = Collection.find_by(title: collection_title)
  user = User.find_by(login: user)
  assignments = ChallengeAssignment.where(collection_id: collection.id).limit(2)
  assignments.each do |a|
    a.pinch_hitter_id = user.default_pseud_id
    a.save
    a.reload
  end
end

## Signing up

When /^I set up a signup for "([^\"]*)" with combination A$/ do |title|
  step %{I start signing up for "#{title}"}
  step %{I check the 1st checkbox with the value "Stargate Atlantis"}
  step %{I check the 2nd checkbox with value "Stargate SG-1"}
  step %{I fill in the 1st field with id matching "freeform_tagnames" with "Alternate Universe - Historical"}
  step %{I fill in the 2nd field with id matching "freeform_tagnames" with "Alternate Universe - High School"}
end

When /^I sign up for "([^\"]*)" with combination A$/ do |title|
  step %{I set up a signup for "#{title}" with combination A}
  click_button "Submit"
end

When /^I attempt to sign up for "([^\"]*)" with a pseud that is not mine$/ do |title|
  step %{the user "gooduser" exists and is activated}
  step %{I am logged in as "baduser"}
  step %{I set up a signup for "#{title}" with combination A}
  pseud_id = Pseud.where(name: "gooduser").first.id
  find("#challenge_signup_pseud_id", visible: false).set(pseud_id)
  click_button "Submit"
end

When /^I attempt to update my signup for "([^\"]*)" with a pseud that is not mine$/ do |title|
  step %{the user "gooduser" exists and is activated}
  step %{I am logged in as "baduser"}
  step %{I sign up for "#{title}" with combination A}
  step %{I follow "Edit Sign-up"}
  pseud_id = Pseud.where(name: "gooduser").first.id
  find("#challenge_signup_pseud_id", visible: false).set(pseud_id)
  click_button "Update"
end

When /^I sign up for "([^\"]*)" with combination B$/ do |title|
  step %{I start signing up for "#{title}"}
    step %{I check the 1st checkbox with value "Stargate SG-1"}
    step %{I check the 2nd checkbox with the value "Stargate Atlantis"}
    step %{I fill in the 1st field with id matching "freeform_tagnames" with "Alternate Universe - High School, Something else weird"}
    step %{I fill in the 2nd field with id matching "freeform_tagnames" with "Alternate Universe - High School"}
    click_button "Submit"
end

When /^I sign up for "([^\"]*)" with combination C$/ do |title|
  step %{I start signing up for "#{title}"}
    step %{I check the 1st checkbox with the value "Stargate SG-1"}
    step %{I check the 2nd checkbox with the value "Stargate SG-1"}
    step %{I fill in the 1st field with id matching "freeform_tagnames" with "Something else weird"}
    step %{I fill in the 2nd field with id matching "freeform_tagnames" with "Something else weird"}
    click_button "Submit"
end

When /^I sign up for "([^\"]*)" with combination D$/ do |title|
  step %{I start signing up for "#{title}"}
    step %{I check the 1st checkbox with the value "Stargate Atlantis"}
    step %{I check the 2nd checkbox with the value "Stargate Atlantis"}
    step %{I fill in the 1st field with id matching "freeform_tagnames" with "Something else weird, Alternate Universe - Historical"}
    step %{I fill in the 2nd field with id matching "freeform_tagnames" with "Something else weird, Alternate Universe - Historical"}
    click_button "Submit"
end

When /^I sign up for "([^\"]*)" with a mismatched combination$/ do |title|
  step %{I start signing up for "#{title}"}
    step %{I check the 1st checkbox with the value "Bad Choice"}
    step %{I check the 2nd checkbox with the value "Bad Choice"}
    click_button "Submit"
end


When /^I sign up for "([^\"]*)" with combination SGA$/ do |title|
  step %{I start signing up for "#{title}"}
    step %{I fill in "challenge_signup_requests_attributes_0_tag_set_attributes_fandom_tagnames" with "Stargate Atlantis"}
    fill_in("challenge_signup_requests_attributes_0_title", with: "SGA love")
    click_button "Submit"
end

When /^I sign up for "([^\"]*)" with combination SG-1$/ do |title|
  step %{I start signing up for "#{title}"}
    step %{I fill in "challenge_signup_requests_attributes_0_tag_set_attributes_fandom_tagnames" with "Stargate SG-1"}
    fill_in("challenge_signup_requests_attributes_0_title", with: "SG1 love")
    click_button "Submit"
end

When /^I sign up for "([^\"]*)" with missing prompts$/ do |title|
  step %{I start signing up for "#{title}"}
    step %{I check the 1st checkbox with the value "Stargate Atlantis"}
    step %{I fill in the 1st field with id matching "freeform_tagnames" with "Something else weird"}
    click_button "Submit"
end

When /^I start to sign up for "([^\"]*)"$/ do |title|
  step %{I start signing up for "#{title}"}
    step %{I check the 1st checkbox with value "Stargate SG-1"}
end

When /^I start to sign up for "([^\"]*)" tagless gift exchange$/ do |title|
  visit collection_path(Collection.find_by(title: title))
  step %{I follow "Sign Up"}
  step %{I fill in "Description" with "random text"}
  step %{I press "Submit"}
  step %{I should see "Sign-up was successfully created"}
end

## Matching

Given /^the gift exchange "([^\"]*)" is ready for matching$/ do |title|
  step %{the gift exchange "#{title}" is ready for signups}
  step %{everyone has signed up for the gift exchange "#{title}"}
end

Given /^I create an invalid signup in the gift exchange "([^\"]*)"$/ do |challengename|
  collection = Collection.find_by(title: challengename)
  # create an invalid signup by deleting the first one's offers,
  # bypassing the validation checks
  collection.signups.first.offers.delete_all
end

When /^I remove a recipient$/ do
  step %{I fill in the 1st field with id matching "_request_signup_pseud" with ""}
end

When /^I assign a recipient to herself$/ do
  first_recip_field = page.all("input[type='text']").select {|el| el['id'] && el['id'].match(/_request_signup_pseud/)}[0]
  recip = first_recip_field['value']
  id = first_recip_field['id']
  if id.match(/assignments_(\d+)_request/)
    num = $1
    fill_in "challenge_assignments_#{num}_offer_signup_pseud", with: recip
  end
end

When /^I manually destroy the assignments for "([^\"]*)"$/ do |title|
  collection = Collection.find_by(title: title)
  collection.assignments.destroy_all
end

When /^I assign a pinch hitter$/ do
  step %{I fill in the 1st field with id matching "pinch_hitter_byline" with "mod1"}
end

When /^I assign a pinch recipient$/ do
  name = page.all("td").select {|el| el['id'] && el['id'].match(/offer_signup_for/)}[0].text
  pseud = Pseud.find_by(name: name)
  request_pseud = ChallengeSignup.where(pseud_id: pseud.id).first.offer_potential_matches.first.request_signup.pseud.name
  step %{I fill in the 1st field with id matching "request_signup_pseud" with "#{request_pseud}"}
end

Given /^everyone has signed up for the gift exchange "([^\"]*)"$/ do |challengename|
  step %{I am logged in as "myname1"}
  step %{I sign up for "#{challengename}" with combination A}
  step %{I am logged in as "myname2"}
  step %{I sign up for "#{challengename}" with combination B}
  step %{I am logged in as "myname3"}
  step %{I sign up for "#{challengename}" with combination C}
  step %{I am logged in as "myname4"}
  step %{I sign up for "#{challengename}" with combination D}
end

Given /^I have generated matches for "([^\"]*)"$/ do |challengename|
  step %{I close signups for "#{challengename}"}
  step %{I follow "Matching"}
  step %{I follow "Generate Potential Matches"}
  step %{the system processes jobs}
    step %{I wait 3 seconds}
  step %{I reload the page}
  step %{all emails have been delivered}
end

Given /^I have sent assignments for "([^\"]*)"$/ do |challengename|
  step %{I follow "Send Assignments"}
  step %{the system processes jobs}
    step %{I wait 3 seconds}
  step %{I reload the page}
  step %{I should not see "Assignments are now being sent out"}
end

Given /^everyone has their assignments for "([^\"]*)"$/ do |challenge_title|
  step %{the gift exchange "#{challenge_title}" is ready for matching}
  step %{I have generated matches for "#{challenge_title}"}
  step %{I have sent assignments for "#{challenge_title}"}
end

Given "I have an assignment for the user {string} in the collection {string}" do |recip_login, collection_name|
  giver = User.current_user
  recip = User.find_by(login: recip_login)
  collection = FactoryBot.create(:collection, name: collection_name, title: collection_name)
  assignment = FactoryBot.create(:challenge_assignment, sent_at: Time.zone.now, collection_id: collection.id)
  assignment.offer_signup.update_column(:pseud_id, giver.default_pseud_id)
  assignment.request_signup.update_column(:pseud_id, recip.default_pseud_id)
  assignment.reload
end

### Fulfilling assignments

When /^I start to fulfill my assignment$/ do
  step %{I am on my user page}
  step %{I follow "Assignments ("}
  step %{I follow "Fulfill"}
    step %{I fill in "Work Title" with "Fulfilled Story"}
    step %{I select "Not Rated" from "Rating"}
    step %{I check "No Archive Warnings Apply"}
    step %{I select "English" from "Choose a language"}
    step %{I fill in "Fandom" with "Final Fantasy X"}
    step %{I fill in "content" with "This is a really cool story about Final Fantasy X"}
end

When /^I fulfill my assignment$/ do
  step %{I start to fulfill my assignment}
  step %{I press "Preview"}
    step %{I press "Post"}
  step %{I should see "Work was successfully posted"}
end

When /^an assignment has been fulfilled in a gift exchange$/ do
  step %{everyone has their assignments for "Awesome Gift Exchange"}
  step %{I am logged in as "myname1"}
  step %{I fulfill my assignment}
end

# we're not testing the process of rejection here, just that
# it doesn't affect the completion status of the challenge assignment
When /^I refuse my gift story "(.*?)"/ do |work|
  w = Work.find_by(title: work)
  w.gifts.first.toggle!(:rejected)
end

### WHEN we need the author attribute to be set
When /^I fulfill my assignment and the author is "([^\"]*)"$/ do |new_user|
  step %{I start to fulfill my assignment}
    step %{I select "#{new_user}" from "Author / Pseud(s)"}
  step %{I press "Preview"}
    step %{I press "Post"}
  step %{I should see "Work was successfully posted"}
end

When /^I have set up matching for "([^\"]*)" with no required matching$/ do |challengename|
  step %{I am logged in as "mod1"}
  step %{I have created the gift exchange "Awesome Gift Exchange"}
  step %{I open signups for "Awesome Gift Exchange"}
  step %{everyone has signed up for the gift exchange "Awesome Gift Exchange"}
end

### Deleting a gift exchange

Then /^I should not see the gift exchange dashboard for "([^\"]*)"$/ do |challenge_title|
  collection = Collection.find_by(title: challenge_title)
  visit collection_path(collection)
  step %{I should not see "Gift Exchange" within "#dashboard"}
  step %{I should not see "Sign-up Form" within "#dashboard"}
  step %{I should not see "My Sign-up" within "#dashboard"}
  step %{I should not see "Sign-ups" within "#dashboard"}
  step %{I should not see "Challenge Settings" within "#dashboard"}
  step %{I should not see "Sign-up Summary" within "#dashboard"}
  step %{I should not see "Requests Summary" within "#dashboard"}
  step %{I should not see "Matching" within "#dashboard"}
  step %{I should not see "Assignments" within "#dashboard"}
  step %{I should not see "Challenge Settings" within "#dashboard"}
end

Then /^no one should have an assignment for "([^\"]*)"$/ do |challenge_title|
  collection = Collection.find_by(title: challenge_title)
  User.all.each do |user|
    user.offer_assignments.in_collection(collection).should be_empty
    user.pinch_hit_assignments.in_collection(collection).should be_empty
  end
end
