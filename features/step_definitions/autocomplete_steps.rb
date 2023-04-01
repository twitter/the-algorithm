Given /^a set of tags for testing autocomplete$/ do
  step %{basic tags}
  step %{a canonical fandom "Supernatural"}
  step %{a canonical fandom "Battlestar Galactica"}
  step %{a noncanonical fandom "Super Awesome"}
  step %{a canonical character "Ellen Harvelle" in fandom "Supernatural"}
  step %{a canonical character "Ellen Tigh" in fandom "Battlestar Galactica"}
  step %{a noncanonical character "ellen somebody"}
  step %{a canonical relationship "Dean/Castiel" in fandom "Supernatural"}
  step %{a canonical relationship "Sam/Dean" in fandom "Supernatural"}
  step %{a canonical relationship "Ellen Tigh/Lee Adama" in fandom "Battlestar Galactica"}
  step %{a noncanonical relationship "Destiel"}
  step %{a canonical freeform "Alternate Universe"}
  step %{a canonical freeform "Superduper"}
  step %{a noncanonical freeform "alternate sundays"}
end

Then /^I should see HTML "(.*)?" in the autocomplete$/ do |string|
  # There should be only one visible autocomplete dropdown.
  within("input + .autocomplete") do
    # Wait for results to appear, then check their HTML content
    expect(current_scope).to have_selector("li")
    expect(current_scope["innerHTML"]).to include(string)
  end
end

Then /^I should see "([^\"]+)" in the autocomplete$/ do |string|
  # There should be only one visible autocomplete dropdown.
  expect(find("input + .autocomplete")).to have_content(string)
end

Then /^I should not see "([^\"]+)" in the autocomplete$/ do |string|
  # There should be only one visible autocomplete dropdown.
  expect(find("input + .autocomplete")).to have_no_content(string)
end

# Define all values to be entered here depending on the fieldname
When /^I enter text in the "([^\"]+)" autocomplete field$/ do |fieldname|
  text = case fieldname
         when "Fandoms"
           "sup"
         when "Additional Tags"
           "alt"
         when "Characters"
           "ellen"
         when "Relationships"
           "cast"
         when "Your tags"
           "sup"
         else
           ""
         end
  step %{I enter "#{text}" in the "#{fieldname}" autocomplete field}
end

When /^I enter "([^\"]+)" in the "([^\"]+)" autocomplete field$/ do |text, fieldname|
  field = find_field(fieldname)
  # Clear the field.
  field.set("")
  # Simulate keystrokes to make the autocomplete dropdown appear (instead of fill_in).
  field.send_keys(text)
  # Wait for the autocomplete right after the field to appear,
  # so in the Then steps we can look for the only active autocomplete
  # without caring where it is.
  expect(page).to have_selector("##{field[:id]} + .autocomplete")
end

When /^I choose "([^\"]+)" from the "([^\"]+)" autocomplete$/ do |text, fieldname|
  field = find_field(fieldname)
  # Clear the field.
  field.set("")
  # Simulate keystrokes to make the autocomplete dropdown appear (instead of fill_in)
  field.send_keys(text)
  # In the autocomplete right after the field...
  with_scope("##{field[:id]} + .autocomplete") do
    # Wait for the expected result to appear and click to select it
    find("li", text: text).click
  end
end

# alias for most common fields
When /^I enter text in the (\w+) autocomplete field$/ do |fieldtype|
  fieldname = case fieldtype
              when "fandom"
                "Fandoms"
              when "character"
                "Characters"
              when "relationship"
                "Relationships"
              when "freeform"
                "Additional Tags"
              end
  step %{I enter text in the "#{fieldname}" autocomplete field}
end

When "I remove selected values from the autocomplete field within {string}" do |selector|
  within(selector) do
    find_all(".autocomplete .delete").each(&:click)
  end
end

When /^I specify a fandom and enter text in the character autocomplete field$/ do
  step %{I choose "Supernatural" from the "Fandoms" autocomplete}
  step %{I enter text in the character autocomplete field}
end

When /^I specify a fandom and enter text in the relationship autocomplete field$/ do
  step %{I choose "Supernatural" from the "Fandoms" autocomplete}
  step %{I enter text in the relationship autocomplete field}
end

When /^I specify two fandoms and enter text in the character autocomplete field$/ do
  step %{I choose "Supernatural" from the "Fandoms" autocomplete}
  step %{I choose "Battlestar Galactica" from the "Fandoms" autocomplete}
  step %{I enter text in the character autocomplete field}
end

When /^I choose a previously bookmarked URL from the autocomplete$/ do
  url = ExternalWork.first.url
  step %{I choose "#{url}" from the "URL" autocomplete}
  step %{all AJAX requests are complete}
end

## Here's where we create the steps defining which tags should appear/not appear
## based on the set of tags and the data entered

Then /^I should only see matching canonical fandom tags in the autocomplete$/ do
  step %{I should see "Supernatural" in the autocomplete}
  step %{I should not see "Super Awesome" in the autocomplete}
  step %{I should not see "Battlestar Galactica" in the autocomplete}
  step %{I should not see "Superduper" in the autocomplete}
end

Then /^I should only see matching canonical freeform tags in the autocomplete$/ do
  step %{I should see "Alternate Universe" in the autocomplete}
  step %{I should not see "alternate sundays" in the autocomplete}
  step %{I should not see "Superduper" in the autocomplete}
end

Then /^I should only see matching canonical character tags in the autocomplete$/ do
  step %{I should see "Ellen Harvelle" in the autocomplete}
  step %{I should see "Ellen Tigh" in the autocomplete}
  step %{I should not see "Ellen Somebody" in the autocomplete}
end

Then /^I should only see matching canonical relationship tags in the autocomplete$/ do
  step %{I should see "Dean/Castiel" in the autocomplete}
  step %{I should not see "Sam/Dean" in the autocomplete}
  step %{I should not see "Destiel" in the autocomplete}
end

Then /^I should only see matching canonical character tags in the specified fandom in the autocomplete$/ do
  step %{I should see "Ellen Harvelle" in the autocomplete}
  step %{I should not see "Ellen Tigh" in the autocomplete}
  step %{I should not see "Ellen Somebody" in the autocomplete}
end

Then /^I should see matching canonical character tags from both fandoms in the autocomplete$/ do
  step %{I should see "Ellen Harvelle" in the autocomplete}
  step %{I should see "Ellen Tigh" in the autocomplete}
  step %{I should not see "Ellen Somebody" in the autocomplete}
end

Then /^I should only see matching canonical relationship tags in the specified fandom in the autocomplete$/ do
  step %{I should see "Dean/Castiel" in the autocomplete}
  step %{I should not see "Destiel" in the autocomplete}
end

Then /^I should only see matching canonical tags in the autocomplete$/ do
  step %{I should see "Supernatural" in the autocomplete}
  step %{I should see "Superduper" in the autocomplete}
  step %{I should not see "Dean/Castiel" in the autocomplete}
end

Then /^I should only see matching noncanonical tags in the autocomplete$/ do
  step %{I should see "Super Awesome" in the autocomplete}
  step %{I should not see "Supernatural" in the autocomplete}
end

Then /^the tag autocomplete fields should list only matching canonical tags$/ do
  step %{I enter text in the fandom autocomplete field}
  step %{I should only see matching canonical fandom tags in the autocomplete}
  step %{I enter text in the character autocomplete field}
  step %{I should only see matching canonical character tags in the autocomplete}
  step %{I enter text in the relationship autocomplete field}
  step %{I should only see matching canonical relationship tags in the autocomplete}
  if page.body =~ /Additional Tags/
    step %{I enter text in the freeform autocomplete field}
    step %{I should only see matching canonical freeform tags in the autocomplete}
  end
end

Then /^the fandom-specific tag autocomplete fields should list only fandom-specific canonical tags$/ do
  step %{I specify a fandom and enter text in the character autocomplete field}
  step %{I should only see matching canonical character tags in the specified fandom in the autocomplete}
  step %{I specify a fandom and enter text in the relationship autocomplete field}
  step %{I should only see matching canonical relationship tags in the specified fandom in the autocomplete}
  step %{I specify two fandoms and enter text in the character autocomplete field}
  step %{I should see matching canonical character tags from both fandoms in the autocomplete}
end

Then /^the external url autocomplete field should list the urls of existing external works$/ do
  step %{I enter "exam" in the "URL" autocomplete field}
  step %{I should see "http://example.org/200" in the autocomplete}
end

Given /^a set of users for testing autocomplete$/ do
  %w(myname coauthor giftee).each do |username|
    user = FactoryBot.create(:user, login: username)
    user.pseuds.first.add_to_autocomplete
  end
end

Then /^the coauthor autocomplete field should list matching users$/ do
  check("co-authors-options-show")
  step %{I enter "coa" in the "pseud_byline_autocomplete" autocomplete field}
  step %{I should see "coauthor" in the autocomplete}
  step %{I should not see "giftee" in the autocomplete}
end

Then /^the gift recipient autocomplete field should list matching users$/ do
  step %{I enter "gif" in the "Gift this work to" autocomplete field}
  step %{I should see "giftee" in the autocomplete}
  step %{I should not see "coauthor" in the autocomplete}
end

Given /^a set of collections for testing autocomplete$/ do
  step %{I create the collection "awesome"}
  step %{I create the collection "great"}
  step %{I create the collection "really great"}
end

Then /^the collection item autocomplete field should list matching collections$/ do
  step %{I enter "gre" in the "Post to Collections / Challenges" autocomplete field}
  step %{I should see "great" in the autocomplete}
  step %{I should see "really great" in the autocomplete}
  step %{I should not see "awesome" in the autocomplete}
end

Given /^a gift exchange for testing autocomplete$/ do
  step %{I have created the gift exchange "autocomplete"}
end

When /^I edit the gift exchange for testing autocomplete$/ do
  visit(edit_collection_gift_exchange_path(Collection.find_by(name: "autocomplete")))
end

Then(/^the pseud autocomplete should contain "([^\"]*)"$/) do |pseud|
  results = Pseud.autocomplete_lookup(search_param: pseud, autocomplete_prefix: "autocomplete_pseud").map { |res| Pseud.fullname_from_autocomplete(res) }
  assert results.include?(pseud)
end

Then(/^the pseud autocomplete should not contain "([^\"]*)"$/) do |pseud|
  results = Pseud.autocomplete_lookup(search_param: pseud, autocomplete_prefix: "autocomplete_pseud").map { |res| Pseud.fullname_from_autocomplete(res) }
  assert !results.include?(pseud)
end
