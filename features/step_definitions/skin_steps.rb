# Make sure that the methods in SkinCacheHelper are available to steps in this
# file (specifically, the steps checking cache expiration):
World(SkinCacheHelper)

DEFAULT_CSS = "\"#title { text-decoration: blink;}\""

Given /^basic skins$/ do
  assert Skin.default
  assert WorkSkin.basic_formatting
end

Given /^I set up the skin "([^"]*)"$/ do |skin_name|
  visit new_skin_path
  fill_in("Title", with: skin_name)
  fill_in("Description", with: "Random description")
  fill_in("CSS", with: "#title { text-decoration: blink;}")
end

Given /^I set up the skin "([^"]*)" with some css$/ do |skin_name|
  step %{I set up the skin "#{skin_name}" with css #{DEFAULT_CSS}}
end

Given /^I set up the skin "([^"]*)" with css "([^"]*)"$/ do |skin_name, css|
  step "I set up the skin \"#{skin_name}\""
  fill_in("CSS", with: css)
end

Given /^I create the skin "([^"]*)" with css "([^"]*)"$/ do |skin_name, css|
  step "I set up the skin \"#{skin_name}\" with css \"#{css}\""
  step %{I submit}
end

Given /^I create the skin "([^"]*)" with some css$/ do |skin_name, css|
  step "I set up the skin \"#{skin_name}\" with css \"#{css}\""
  step %{I submit}
end

Given /^I create the skin "([^"]*)"$/ do |skin_name|
  step "I create the skin \"#{skin_name}\" with css #{DEFAULT_CSS}"
end

Given /^I edit the skin "([^"]*)"$/ do |skin_name|
  skin = Skin.find_by(title: skin_name)
  visit edit_skin_path(skin)
end

Given /^the unapproved public skin "([^"]*)" with css "([^"]*)"$/ do |skin_name, css|
  # Delay to make sure all skins have at least 1 second of separation in their
  # creation dates, so that they will be listed in the right order:
  step "it is currently 1 second from now"

  step %{I am logged in as "skinner"}
  step %{I set up the skin "#{skin_name}" with css "#{css}"}
  attach_file("skin_icon", "features/fixtures/skin_test_preview.png")
  check("skin_public")
  step %{I submit}
  step %{I should see "Skin was successfully created"}
end

Given /^the unapproved public skin "([^"]*)"$/ do |skin_name|
  step "the unapproved public skin \"#{skin_name}\" with css #{DEFAULT_CSS}"
end

Given /^I approve the skin "([^"]*)"$/ do |skin_name|
  step %{I am logged in as a "superadmin" admin}
  visit admin_skins_url
  check("make_official_#{skin_name.downcase.gsub(/\s/, '_')}")
  step %{I submit}
end

Given /^I unapprove the skin "([^"]*)"$/ do |skin_name|
  step %{I am logged in as a "superadmin" admin}
  visit admin_skins_url
  step "I follow \"Approved Skins\""
  check("make_unofficial_#{skin_name.downcase.gsub(/\s/, '_')}")
  step %{I submit}
end

Given /^I have loaded site skins$/ do
  Skin.load_site_css
end

Given /^the approved public skin "([^"]*)" with css "([^"]*)"$/ do |skin_name, css|
  step "the unapproved public skin \"#{skin_name}\" with css \"#{css}\""
  step "I am logged in as an admin"
  step "I approve the skin \"#{skin_name}\""
  step "I am logged out"
end

Given /^the approved public skin "([^"]*)"$/ do |skin_name|
  step "the approved public skin \"#{skin_name}\" with css #{DEFAULT_CSS}"
end

Given /^"([^"]*)" is using the approved public skin "([^"]*)" with css "([^"]*)"$/ do |login, skin_name, css|
  step "the approved public skin \"public skin\" with css \"#{css}\""
  step "I am logged in as \"#{login}\""
  step "I am on #{login}'s preferences page"
  select(skin_name, from: "preference_skin_id")
  step %{I submit}
end

Given /^"([^"]*)" is using the approved public skin "([^"]*)"$/ do |login, skin_name|
  step "\"#{login}\" is using the approved public skin with css #{DEFAULT_CSS}"
end

Given /^I have a skin "(.*?)" with a parent "(.*?)"$/ do |child_title, parent_title|
  step %{I set up the skin "#{parent_title}"}
  click_button("Submit")
  step %{I set up the skin "#{child_title}"}
  click_button("Submit")

  child = Skin.find_by(title: child_title)
  parent = Skin.find_by(title: parent_title)
  child.skin_parents.create(position: 1, parent_skin: parent)
end

### WHEN

When /^I change my skin to "([^\"]*)"$/ do |skin_name|
  step "I am on my user page"
    step %{I follow "Preferences"}
    step %{I select "#{skin_name}" from "preference_skin_id"}
    step %{I submit}
  step %{I should see "Your preferences were successfully updated."}
end

When /^I create and use a skin to make the header pink$/ do
  visit new_skin_url(wizard: true)
  fill_in("skin_title", with: "Pink header")
  fill_in("skin_headercolor", with: "pink")
  click_button("Submit")
  click_button("Use")
end

When /^I create and use a skin to change the accent color$/ do
  visit new_skin_url(wizard: true)
  fill_in("skin_title", with: "Blue accent")
  fill_in("skin_accent_color", with: "blue")
  click_button("Submit")
  click_button("Use")
end

When /^I edit the skin "([^\"]*)" with the wizard$/ do |skin_name|
  skin = Skin.find_by(title: skin_name)
  visit edit_skin_path(skin, wizard: true)
end

When /^I edit my pink header skin to have a purple logo$/ do
  skin = Skin.find_by(title: "Pink header")
  visit edit_skin_path(skin)
  fill_in("CSS", with: "#header .heading a {color: purple;}")
  click_button("Update")
end

When /^I view the skin "([^\"]*)"$/ do |skin|
  skin = Skin.find_by(title: skin)
  visit skin_url(skin)
end

When /^the skin "([^\"]*)" is in the chooser$/ do |skin_name|
  skin = Skin.find_by(title: skin_name)
  skin.in_chooser = true
  skin.save!
end

When /^the skin "([^\"]*)" is cached$/ do |skin_name|
  skin = Skin.find_by(title: skin_name)
  skin.cached = true
  skin.save!
  skin.cache!
end

When /^I preview the skin "([^\"]*)"$/ do |skin_name|
  skin = Skin.find_by(title: skin_name)
  visit preview_skin_path(skin)
end

When /^I set the skin "([^\"]*)" for this session$/ do |skin_name|
  skin = Skin.find_by(title: skin_name)
  visit set_skin_path(skin)
end

### THEN

Then "I should see {string} in the page style" do |css|
  expect(page).to have_css("style", text: css, visible: false)
end

Then "I should not see {string} in the page style" do |css|
  expect(page).not_to have_css("style", text: css, visible: false)
end

Then "the page should have the cached skin {string}" do |skin_name|
  skin = Skin.find_by(title: skin_name)
  expect(page).to have_css("link[href*='#{skin.skin_dirname}']", visible: false)
end

Then "the page should not have the cached skin {string}" do |skin_name|
  skin = Skin.find_by(title: skin_name)
  expect(page).not_to have_css("link[href*='#{skin.skin_dirname}']", visible: false)
end

Then "I should see a pink header" do
  step %{I should see "#header .primary" in the page style}
  step %{I should see "background-image: none; background-color: pink;" in the page style}
end

Then "I should see a different accent color" do
  step %{I should see "fieldset, form dl, fieldset dl dl" in the page style}
  step %{I should see "background: blue; border-color: blue;" in the page style}
end

Then "the page should have a skin with the media query {string}" do |query|
  expect(page).to have_css("style[media='#{query}']", visible: false)
end

Then /^the cache of the skin on "([^\"]*)" should expire after I save the skin$/ do |title|
  skin = Skin.find_by(title: title)
  orig_cache_version = skin_cache_version(skin)
  visit edit_skin_path(skin)
  fill_in("CSS", with: "#random { text-decoration: blink;}")
  click_button("Update")
  assert orig_cache_version != skin_cache_version(skin), "Cache version #{orig_cache_version} matches #{skin_cache_version(skin)}."
end

Then(/^the cache of the skin on "(.*?)" should not expire after I save "(.*?)"$/) do |arg1, arg2|
  skin = Skin.find_by(title: arg1)
  save_me = Skin.find_by(title: arg2)
  orig_skin_version = skin_cache_version(skin)
  orig_save_me_version = skin_cache_version(save_me)
  visit edit_skin_path(save_me)
  fill_in("CSS", with: "#random { text-decoration: blink;}")
  click_button("Update")
  assert orig_save_me_version != skin_cache_version(save_me), "Cache version #{orig_save_me_version} matches #{skin_cache_version(save_me)}"
  assert orig_skin_version == skin_cache_version(skin), "Cache version #{orig_skin_version} does not match #{skin_cache_version(skin)}"
end

Then(/^the cache of the skin on "(.*?)" should expire after I save a parent skin$/) do |arg1|
  skin = Skin.find_by(title: arg1)
  orig_skin_version = skin_cache_version(skin)
  parent_id = SkinParent.where(child_skin_id: skin.id).last.parent_skin_id
  parent = Skin.find(parent_id)
  parent.save!
  assert orig_skin_version != skin_cache_version(skin), "Cache version #{orig_skin_version} matches #{skin_cache_version(skin)}"
end

Then "I should see a purple logo" do
  step %|I should see "#header .heading a { color: purple; }" in the page style|
end

Then /^I should see the skin "(.*?)" in the skin chooser$/ do |skin|
  with_scope("#footer .menu") do
    expect(page).to have_content(skin)
  end
end

Then /^I should not see the skin chooser$/ do
  expect(page).not_to have_content("Customize")
end

Then /^the filesystem cache of the skin "(.*?)" should include "(.*?)"$/ do |title, contents|
  skin = Skin.find_by(title: title)
  expect(skin.cached?).to be_truthy

  directory = Skin.skins_dir + skin.skin_dirname
  style = Skin.skin_dir_entries(directory, /.css$/).map do |filename|
    File.read(directory + filename)
  end.join("\n")

  expect(style).to include(contents)
end
