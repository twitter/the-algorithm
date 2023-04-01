Given /^I set my preferences to View Full Work mode by default$/ do
  user = User.current_user
  user.preference.view_full_works = true
  user.preference.save
end

Given(/^the user "(.*?)" disallows co-creators$/) do |login|
  user = User.where(login: login).first
  user = find_or_create_new_user(login, DEFAULT_PASSWORD) if user.nil?
  user.preference.allow_cocreator = false
  user.preference.save
end

Given(/^the user "(.*?)" allows co-creators$/) do |login|
  user = User.where(login: login).first
  user = find_or_create_new_user(login, DEFAULT_PASSWORD) if user.nil?
  user.preference.allow_cocreator = true
  user.preference.save
end

Given "the user {string} disallows gifts" do |login|
  user = User.where(login: login).first
  user = find_or_create_new_user(login, DEFAULT_PASSWORD) if user.nil?
  user.preference.allow_gifts = false
  user.preference.save
end

Given "the user {string} allows gifts" do |login|
  user = User.where(login: login).first
  user = find_or_create_new_user(login, DEFAULT_PASSWORD) if user.nil?
  user.preference.allow_gifts = true
  user.preference.save
end

Given "the user {string} is hidden from search engines" do |login|
  user = User.find_by(login: login)
  user.preference.update(minimize_search_engines: true)
end

When /^I set my preferences to turn off notification emails for comments$/ do
  user = User.current_user
  user.preference.comment_emails_off = true
  user.preference.save
end

When /^I set my preferences to turn off notification emails for kudos$/ do
  user = User.current_user
  user.preference.kudos_emails_off = true
  user.preference.save
end

When /^I set my preferences to turn off notification emails for gifts$/ do
  user = User.current_user
  user.preference.recipient_emails_off = true
  user.preference.save
end

When /^I set my preferences to hide warnings$/ do
  step %{I follow "My Preferences"}
  check("preference_hide_warnings")
  click_button("Update")
end

When /^I set my preferences to hide freeform$/ do
  step %{I follow "My Preferences"}
  check("preference_hide_freeform")
  click_button("Update")
end

When /^I set my preferences to hide the share buttons on my work$/ do
  user = User.current_user
  user.preference.disable_share_links = true
  user.preference.save
end

When /^I set my preferences to turn off messages to my inbox about comments$/ do
  user = User.current_user
  user.preference.comment_inbox_off = true
  user.preference.save
end

When /^I set my preferences to turn on messages to my inbox about comments$/ do
  user = User.current_user
  user.preference.comment_inbox_off = false
  user.preference.save
end

When /^I set my preferences to turn off copies of my own comments$/ do
  user = User.current_user
  user.preference.comment_copy_to_self_off = true
  user.preference.save
end

When /^I set my preferences to turn on copies of my own comments$/ do
  user = User.current_user
  user.preference.comment_copy_to_self_off = false
  user.preference.save
end

When /^I set my preferences to turn off the banner showing on every page$/ do
  user = User.current_user
  user.preference.banner_seen = true
  user.preference.save
end

When /^I set my preferences to turn off history$/ do
  user = User.current_user
  user.preference.history_enabled = false
  user.preference.save
end

When /^I set my time zone to "([^"]*)"$/ do |time_zone|
  user = User.current_user
  user.preference.time_zone = time_zone
  user.preference.save
end

When "I set my preferences to allow collection invitations" do
  user = User.current_user
  user.preference.allow_collection_invitation = true
  user.preference.save
end

When /^I set my preferences to hide both warnings and freeforms$/ do
  step %{I follow "My Preferences"}
  check("preference_hide_warnings")
  check("preference_hide_freeform")
  click_button("Update")
end

When /^I set my preferences to show adult content without warning$/ do
  user = User.current_user
  user.preference.adult = true
  user.preference.save
end

When /^I set my preferences to warn before showing adult content$/ do
  user = User.current_user
  user.preference.adult = false
  user.preference.save
end
