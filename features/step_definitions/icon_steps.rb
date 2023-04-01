### GIVEN

Given /^I am editing a pseud$/ do
  step %{I am logged in as "myname"}
  visit edit_user_pseud_path(User.current_user, User.current_user.default_pseud)
end

#' cancelling highlighting

Given /^I have an icon uploaded$/ do
  step "I am editing a pseud"
  step %{I attach the file "features/fixtures/icon.gif" to "icon"}
  step %{I press "Update"}
end

### WHEN
When "I attach an icon with the extension {string}" do |extension|
  step %{I attach the file "features/fixtures/icon.#{extension}" to "icon"}
end

When /^I add an icon to the collection "([^"]*)"$/ do |title|
  step %{I am logged in as "moderator"}
  step %{I am on "#{title}" collection's page}
  step %{I follow "Settings"}
  step %{I attach the file "features/fixtures/icon.gif" to "collection_icon"}
  step %{I press "Update"}
end

When /^I delete the icon from the collection "([^"]*)"$/ do |title|
  step %{I am logged in as "moderator"}
  step %{I am on "#{title}" collection's page}
  step %{I follow "Settings"}
  check("collection_delete_icon")
  step %{I press "Update"}
end

Then /^the "([^"]*)" collection should have an icon$/ do |title|
  collection = Collection.find_by(title: title)
  assert !collection.icon_file_name.blank?
end

Then /^the "([^"]*)" collection should not have an icon$/ do |title|
  collection = Collection.find_by(title: title)
  assert collection.icon_file_name.blank?
end

### THEN
