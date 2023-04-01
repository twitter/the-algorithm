### GIVEN

Given /^I have no collections$/ do
  Collection.delete_all
end

Given /^the collection "([^\"]*)" is deleted$/ do |collection_title|
  step %{I am logged in as the owner of "#{collection_title}"}
  visit edit_collection_path(Collection.find_by(title: collection_title))
  click_link "Delete Collection"
  click_button "Yes, Delete Collection"
  page.should have_content("Collection was successfully deleted.")
end

When /^I am logged in as the owner of "([^\"]*)"$/ do |collection|
  c = Collection.find_by(title: collection)
  step %{I am logged in as "#{c.owners.first.user.login}"}
end

When /^I view the collection "([^\"]*)"$/ do |collection|
  visit collection_path(Collection.find_by(title: collection))
end

When /^I add my work to the collection$/ do
  step %{I follow "Add To Collection"}
  fill_in("collection_names", with: "Various_Penguins")
  click_button("Add")
end

When "I invite the work {string} to the collection {string}" do |work_title, collection_title|
  work = Work.find_by(title: work_title)
  collection = Collection.find_by(title: collection_title)
  visit work_path(work)
  click_link("Invite To Collections")
  fill_in("collection_names", with: collection.name)
  click_button("Invite")
end

When "I edit the work {string} to be in the collection(s) {string}" do |work, collection|
  step %{I edit the work "#{work}"}
  fill_in("Post to Collections / Challenges", with: collection)
  step %{I post the work}
end

When(/^I view the(?: ([^"]*)) collection items page for "(.*?)"$/) do |item_status, collection|
  c = Collection.find_by(title: collection)
  if item_status == "approved"
    visit collection_items_path(c, approved: true)
  elsif item_status == "rejected"
    visit collection_items_path(c, rejected: true)
  elsif item_status == "invited"
    visit collection_items_path(c, invited: true)
  else
    visit collection_items_path(c)
  end
end

When "the collection counts have expired" do
  step "all indexing jobs have been run"
  step "it is currently #{ArchiveConfig.SECONDS_UNTIL_COLLECTION_COUNTS_EXPIRE} seconds from now"
end

Given /^mod1 lives in Alaska$/ do
  step %{I am logged in as "mod1"}
  step %{I go to mod1 preferences page}
  step %{I select "(GMT-09:00) Alaska" from "preference_time_zone"}
  step %{I press "Update"}
end

Given /^(?:I have )?(?:a|an|the) (hidden)?(?: )?(anonymous)?(?: )?(moderated)?(?: )?(closed)?(?: )?collection "([^\"]*)"(?: with name "([^\"]*)")?$/ do |hidden, anon, moderated, closed, title, name|
  step %{I am logged in as "moderator"}
  step %{I set up the collection "#{title}" with name "#{name}"}
  check("This collection is unrevealed") unless hidden.blank?
  check("This collection is anonymous") unless anon.blank?
  check("This collection is moderated") unless moderated.blank?
  check("This collection is closed") unless closed.blank?
  step %{I submit}
  step %{I am logged out}
end

Given /^I open the collection with the title "([^\"]*)"$/ do |title|
  step %{I am logged in as "moderator"}
  visit collection_path(Collection.find_by(title: title))
  step %{I follow "Collection Settings"}
  step %{I uncheck "This collection is closed"}
  step %{I submit}
  step %{I am logged out}
end

Given /^I close the collection with the title "([^\"]*)"$/ do |title|
  step %{I am logged in as "moderator"}
  visit collection_path(Collection.find_by(title: title))
  step %{I follow "Collection Settings"}
  step %{I check "This collection is closed"}
  step %{I submit}
  step %{I am logged out}
end

Given /^I have added (?:a|the) co\-moderator "([^\"]*)" to collection "([^\"]*)"$/ do |name, title|
  # create the user
  step %{I am logged in as "#{name}"}
  step %{I am logged in as "mod1"}
  visit collection_path(Collection.find_by(title: title))
  click_link("Membership")
  step %{I fill in "participants_to_invite" with "#{name}"}
    step %{I press "Submit"}

  step %{I select "Moderator" from "#{name}_role"}
  # TODO: fix the form, it is malformed right now
  click_button("#{name}_submit")
  step %{I should see "Updated #{name}"}
end

### WHEN

When /^I set up (?:a|the) collection "([^"]*)"(?: with name "([^"]*)")?$/ do |title, name|
  visit new_collection_path
  fill_in("collection_name", with: (name.blank? ? title.gsub(/[^\w]/, '_') : name))
  fill_in("collection_title", with: title)
end

When /^I create (?:a|the) collection "([^"]*)"(?: with name "([^"]*)")?$/ do |title, name|
  name = title.gsub(/[^\w]/, '_') if name.blank?
  step %{I set up the collection "#{title}" with name "#{name}"}
  step %{I submit}
end

When /^I add (?:a|the) subcollection "([^"]*)"(?: with name "([^"]*)")? to (?:a|the) parent collection named "([^"]*)"$/ do |title, name, parent_name|
  if Collection.find_by_name(parent_name).nil?
    step %{I create the collection "#{parent_name}" with name "#{parent_name}"}
  end
  name = title.gsub(/[^\w]/, '_') if name.blank?
  step %{I set up the collection "#{title}" with name "#{name}"}
  fill_in("collection_parent_name", with: parent_name)
  step %{I submit}
end

When /^I sort by fandom$/ do
  within(:xpath, "//li[a[contains(@title,'Sort')]]") do
    step %{I follow "Fandom 1"}
  end
end

When /^I reveal works for "([^\"]*)"$/ do |title|
  step %{I am logged in as the owner of "#{title}"}
  visit collection_path(Collection.find_by(title: title))
  step %{I follow "Collection Settings"}
  uncheck "This collection is unrevealed"
  click_button "Update"
  page.should have_content("Collection was successfully updated")
end

When /^I reveal authors for "([^\"]*)"$/ do |title|
  step %{I am logged in as the owner of "#{title}"}
  visit collection_path(Collection.find_by(title: title))
  step %{I follow "Collection Settings"}
  uncheck "This collection is anonymous"
  click_button "Update"
  page.should have_content("Collection was successfully updated")
end

When /^I check all the collection settings checkboxes$/ do
  check("collection_collection_preference_attributes_moderated")
  check("collection_collection_preference_attributes_closed")
  check("collection_collection_preference_attributes_unrevealed")
  check("collection_collection_preference_attributes_anonymous")
  check("collection_collection_preference_attributes_show_random")
  check("collection_collection_preference_attributes_email_notify")
end

When /^I accept the invitation for my work in the collection "([^\"]*)"$/ do |collection|
  the_collection = Collection.find_by(title: collection)
  collection_item_id = the_collection.collection_items.first.id
  visit user_collection_items_path(User.current_user)
  step %{I select "Approved" from "collection_items_#{collection_item_id}_user_approval_status"}
end

When "I approve the work {string} in the collection {string}" do |work, collection|
  work = Work.find_by(title: work)
  collection = Collection.find_by(title: collection)
  item_id = CollectionItem.find_by(item: work, collection: collection).id
  visit collection_items_path(collection)
  step %{I select "Approved" from "collection_items_#{item_id}_collection_approval_status"}
end

### THEN

Then /^"([^"]*)" collection exists$/ do |title|
  assert Collection.where(title: title).exists?
end

Then /^the name of the collection "([^"]*)" should be "([^"]*)"$/ do |title, name|
  assert Collection.find_by(title: title).name == name
end

Then /^I should see a collection not found message for "([^\"]+)"$/ do |collection_name|
  step %{I should see /We couldn't find the collection(?:.+and)? #{collection_name}/}
end

Then /^the collection "(.*)" should be deleted/ do |collection|
  assert Collection.where(title: collection).first.nil?
end

Then /^the work "([^\"]*)" should be hidden from me$/ do |title|
  work = Work.find_by(title: title)
  visit work_path(work)
  expect(page.title).to include("Mystery Work")
  expect(page.title).not_to include(title)
  expect(page).not_to have_content(title)
  expect(page).to have_content("This work is part of an ongoing challenge and will be revealed soon!")
  expect(page).not_to have_content(Sanitize.clean(work.chapters.first.content))
  if work.collections.first
    step "all indexing jobs have been run"
    visit collection_path(work.collections.first)
    expect(page).not_to have_content(title)
    expect(page).to have_content("Mystery Work")
  end
  visit user_path(work.users.first)
  expect(page).not_to have_content(title)
end

Then /^the work "([^\"]*)" should be visible to me$/ do |title|
  work = Work.find_by(title: title)
  visit work_path(work)
  page.should have_content(title)
  page.should have_content(Sanitize.clean(work.chapters.first.content))
end

Then /^the author of "([^\"]*)" should be visible to me on the work page$/ do |title|
  work = Work.find_by(title: title)
  visit work_path(work)
  authors = work.pseuds.uniq.sort.collect(&:byline).join(", ")
  page.should have_content("Anonymous [#{authors}]")
end

Then /^the author of "([^\"]*)" should be publicly visible$/ do |title|
  work = Work.find_by(title: title)
  byline = work.users.first.pseuds.first.byline
  visit work_path(work)
  expect(page.title).to include(byline)
  step %{I should see "#{byline}" within ".byline"}
  if work.collections.first
    step "all indexing jobs have been run"
    visit collection_path(work.collections.first)
    expect(page).to have_content("#{title} by #{byline}")
  end
end

Then /^the author of "([^\"]*)" should be hidden from me$/ do |title|
  step "all indexing jobs have been run"
  work = Work.find_by(title: title)
  byline = work.users.first.pseuds.first.byline
  visit work_path(work)
  expect(page).not_to have_content(byline)
  expect(page.title).to include("Anonymous")
  step %{I should see "Anonymous" within ".byline"}
  visit collection_path(work.collections.first)
  expect(page).not_to have_content("#{title} by #{byline}")
  expect(page).to have_content("#{title} by Anonymous")
  visit user_path(work.users.first)
  expect(page).not_to have_content(title)
end
