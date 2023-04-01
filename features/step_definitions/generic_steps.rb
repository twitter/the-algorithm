When /^(?:|I )unselect "([^"]+)" from "([^"]+)"$/ do |item, selector|
  unselect(item, from: selector)
end

Then /^debug$/ do
  binding.pry
end

Then /^tell me I got (.*)$/ do |spot|
  puts "got #{spot}"
end

Then /^show me the response$/ do
  puts page.body
end

Then /^show me the html$/ do
  puts page.body
end

Then /^show me the main content$/ do
  puts "\n" + find("#main").native.inner_html
end

Then /^show me the errors$/ do
  puts "\n" + find("div.error").native.inner_html
end

Then /^show me the sidebar$/ do
  puts "\n" + find("#dashboard").native.inner_html
end

Then /^I should see errors/ do
  assert find("div.error")
end

Then /^show me the form$/ do
  step %{show me the 1st form}
end

Then /^show me the (\d+)(?:st|nd|rd|th) form$/ do |index|
  puts "\n" + page.all("#main form")[(index.to_i-1)].native.inner_html
end

Then "I should see the {string} form" do |form_id|
  expect(page).to have_css("form##{form_id}")
end

Then "I should not see the {string} form" do |form_id|
  expect(page).not_to have_css("form##{form_id}")
end

Given /^I wait (\d+) seconds?$/ do |number|
  Kernel::sleep number.to_i
end

When "all AJAX requests are complete" do
  wait_for_ajax if @javascript
end

When 'the system processes jobs' do
  #resque runs inline during testing. see resque.rb in initializers/gem-plugin_config
  #Delayed::Worker.new.work_off
end

When 'I reload the page' do
  visit current_url
end

Then /^I should see Posted now$/ do
  now = Time.zone.now.to_s
  step "I should see \"Posted #{now}\""
end

When /^I fill in "([^\"]*)" with$/ do |field, value|
  fill_in(field, with: value)
end

When /^I fill in "([^\"]*)" with `([^\`]*)`$/ do |field, value|
  fill_in(field, with: value)
end

When /^I fill in "([^\"]*)" with '([^\']*)'$/ do |field, value|
  fill_in(field, with: value)
end

Then /^I should see a create confirmation message$/ do
  page.should have_content('was successfully created')
end

Then /^I should see an update confirmation message$/ do
  page.should have_content('was successfully updated')
end

Then /^I should see a save error message$/ do
  step %{I should see "We couldn't save"}
end

Then /^I should see a success message$/ do
  step %{I should see "success"}
end

def assure_xpath_present(tag, attribute, value, selector)
  with_scope(selector) do
    page.should have_xpath("//#{tag}[@#{attribute}='#{value}']")
  end
end

def assure_xpath_not_present(tag, attribute, value, selector)
  with_scope(selector) do
    page.should_not have_xpath("//#{tag}[@#{attribute}='#{value}']")
  end
end

# img attributes
Then /^I should see the image "([^"]*)" text "([^"]*)"(?: within "([^"]*)")?$/ do |attribute, text, selector|
  assure_xpath_present("img", attribute, text, selector)
end

Then /^I should not see the image "([^"]*)" text "([^"]*)"(?: within "([^"]*)")?$/ do |attribute, text, selector|
  assure_xpath_not_present("img", attribute, text, selector)
end

Then /^"([^"]*)" should be selected within "([^"]*)"$/ do |value, field|
  page.has_select?(field, selected: value).should == true
end

Then /^"(.*)?" should( not)? be an option within "(.*)?"$/ do |value, negation, field|
  expect(page.has_select?(field, with_options: [value])).to be !negation
end

Then /^I should see "([^"]*)" in the "([^"]*)" input/ do |content, labeltext|
  find_field("#{labeltext}").value.should == content
end

Then /^I should see a button with text "(.*?)"(?: within "(.*?)")?$/ do |text, selector|
  assure_xpath_present("input", "value", text, selector)
end

Then /^I should not see a button with text "(.*?)"(?: within "(.*?)")?$/ do |text, selector|
  assure_xpath_not_present("input", "value", text, selector)
end

Then "the {string} input should be blank" do |label|
  expect(find_field(label).value).to be_blank
end

Then /^I should see (a|an) "([^"]*)" button(?: within "([^"]*)")?$/ do |_article, text, selector|
  assure_xpath_present("input", "value", text, selector)
end

Then /^I should not see (a|an) "([^"]*)" button(?: within "([^"]*)")?$/ do |_article, text, selector|
  assure_xpath_not_present("input", "value", text, selector)
end

When /^"([^\"]*)" is fixed$/ do |what|
  puts "\nDEFERRED (#{what})"
end

Then /^the "([^"]*)" checkbox(?: within "([^"]*)")? should be disabled$/ do |label, selector|
  with_scope(selector) do
    field_disabled = find_field(label, disabled: true)
    if field_disabled.respond_to? :should
      field_disabled.should be_truthy
    else
      assert field_disabled
    end
  end
end

Then /^the "([^"]*)" checkbox(?: within "([^"]*)")? should not be disabled$/ do |label, selector|
  with_scope(selector) do
    field_disabled = find_field(label)['disabled']
    if field_disabled.respond_to? :should
      field_disabled.should be_falsey
    else
      assert !field_disabled
    end
  end
end

Then /^I should see the input with id "([^"]*)"(?: within "([^"]*)")?$/ do |id, selector|
  assure_xpath_present("input", "id", id, selector)
end

Then /^I should not see the input with id "([^"]*)"(?: within "([^"]*)")?$/ do |id, selector|
  assure_xpath_not_present("input", "id", id, selector)
end

When /^I check the (\d+)(?:st|nd|rd|th) checkbox with the value "([^"]*)"$/ do |index, value|
  check(page.all("input[type='checkbox']").select {|el| el['value'] == value}[(index.to_i-1)]['id'])
end

When /^I check the (\d+)(st|nd|rd|th) checkbox with value "([^"]*)"$/ do |index, suffix, value|
  step %{I check the #{index}#{suffix} checkbox with the value "#{value}"}
end

When /^I uncheck the (\d+)(?:st|nd|rd|th) checkbox with the value "([^"]*)"$/ do |index, value|
  uncheck(page.all("input[type='checkbox']").select {|el| el['value'] == value}[(index.to_i-1)]['id'])
end

When /^I check the (\d+)(?:st|nd|rd|th) checkbox with id matching "([^"]*)"$/ do |index, id_string|
  check(page.all("input[type='checkbox']").select {|el| el['id'] && el['id'].match(/#{id_string}/)}[(index.to_i-1)]['id'])
end

When /^I uncheck the (\d+)(?:st|nd|rd|th) checkbox with id matching "([^"]*)"$/ do |index, id_string|
  uncheck(page.all("input[type='checkbox']").select {|el| el['id'] && el['id'].match(/#{id_string}/)}[(index.to_i-1)]['id'])
end

When /^I fill in the (\d+)(?:st|nd|rd|th) field with id matching "([^"]*)" with "([^"]*)"$/ do |index, id_string, value|
  fill_in(page.all("input[type='text']").select {|el| el['id'] && el['id'].match(/#{id_string}/)}[(index.to_i-1)]['id'], with: value)
end


# If you have multiple forms on a page you will need to specify which one you want to submit with, eg,
# "I submit with the 2nd button", but in those cases you probably want to make sure that
# the different forms have different button text anyway, and submit them using
# When I press "Button Text"
When /^I submit with the (\d+)(?:st|nd|rd|th) button$/ do |index|
  page.all("input[type='submit']")[(index.to_i - 1)].click
end

# This will submit the first submit button inside a <p class="submit"> by default
# That wrapping paragraph tag will be generated automatically if you use
# the submit_button or submit_fieldset helpers in application_helper.rb
# The text on the button will not matter and can be changed without breaking tests.
When /^I submit$/ do
  page.all("p.submit input[type='submit']")[0].click
end

# we want greedy matching for this one so we can handle tags that have attributes in them
Then /^I should see the text with tags "(.*)"$/ do |text|
  page.body.should =~ /#{Regexp.escape(text)}/m
end

Then /^I should see the text with tags '(.*)'$/ do |text|
  page.body.should =~ /#{Regexp.escape(text)}/m
end

Then /^I should not see the text with tags '(.*)'$/ do |text|
  page.body.should_not =~ /#{Regexp.escape(text)}/m
end

Then /^I should find a checkbox "([^\"]*)"$/ do |name|
  field = find_field(name)
  field['checked'].respond_to? :should
end

Then /^I should see a link "([^\"]*)"$/ do |name|
  text = name + "</a>"
  page.body.should =~ /#{Regexp.escape(text)}/m
end

Then /^I should not see a link "([^\"]*)"$/ do |name|
  text = name + "</a>"
  page.body.should_not =~ /#{Regexp.escape(text)}/m
end

Then "the page should be hidden from search engines" do
  expect(page).to have_css("meta[name=robots][content=noindex]", visible: false)
  expect(page).to have_css("meta[name=googlebot][content=noindex]", visible: false)
end

Then "the page should not be hidden from search engines" do
  expect(page).not_to have_css("meta[name=robots][content=noindex]", visible: false)
  expect(page).not_to have_css("meta[name=googlebot][content=noindex]", visible: false)
end

When /^I want to search for exactly one term$/ do
  Capybara.exact = true
end

When /^I should see the correct time zone for "(.*)"$/ do |zone|
  Time.zone = zone
  page.body.should =~ /#{Regexp.escape(Time.zone.now.zone)}/
end
