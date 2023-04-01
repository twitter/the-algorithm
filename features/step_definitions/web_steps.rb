require 'uri'
require 'cgi'

module WithinHelpers
  def with_scope(locator)
    locator ? within(locator) { yield } : yield
  end
end
World(WithinHelpers)

When /^I am in (.*) browser$/ do |name|
  Capybara.session_name = name
end

Given /^(?:|I )am on (.+)$/ do |page_name|
  visit path_to(page_name)
end

When /^I take a screenshot$/ do
  screenshot_and_save_page
end

# We set the default domain to example.org.
# The phantomjs drive fetchs pages directly so some tests will go to example.org
# setting this whitelist stops this happening which is in itself a good thing
# and makes the network traces easier to read as there are less calls to twitter etc.

When /^I limit myself to the Archive$/ do
  page.driver.browser.url_whitelist = ['http://127.0.0.1']
end

When /^I clear the network traffic$/ do
  page.driver.clear_network_traffic
end

When /^(?:|I )go to (.+)$/ do |page_name|
  visit path_to(page_name)
end

When /^(?:|I )press "([^"]*)"(?: within "([^"]*)")?$/ do |button, selector|
  with_scope(selector) do
    click_button(button)
  end
end

When /^(?:|I )follow "([^"]*)"(?: within "([^"]*)")?$/ do |link, selector|
  with_scope(selector) do
    click_link(link)
  end
end

When /^(?:|I )follow '([^']*)'(?: within "([^"]*)")?$/ do |link, selector|
  with_scope(selector) do
    click_link(link)
  end
end

When /^(?:|I )fill in "([^"]*)" with "([^"]*)"(?: within "([^"]*)")?$/ do |field, value, selector|
  with_scope(selector) do
    fill_in(field, with: value)
  end
end

When /^(?:|I )fill in "([^"]*)" for "([^"]*)"(?: within "([^"]*)")?$/ do |value, field, selector|
  with_scope(selector) do
    fill_in(field, with: value)
  end
end

# Use this to fill in an entire form with data from a table. Example:
#
#   When I fill in the following:
#     | Account Number | 5002       |
#     | Expiry date    | 2009-11-01 |
#     | Note           | Nice guy   |
#     | Wants Email?   |            |
#
# TODO: Add support for checkbox, select og option
# based on naming conventions.
#
When /^(?:|I )fill in the following(?: within "([^"]*)")?:$/ do |selector, fields|
  with_scope(selector) do
    fields.rows_hash.each do |name, value|
      step %{I fill in "#{name}" with "#{value}"}
    end
  end
end

When /^(?:|I )select "([^"]*)" from "([^"]*)"(?: within "([^"]*)")?$/ do |value, field, selector|
  with_scope(selector) do
    select(value, from: field)
  end
end

When /^(?:|I )check "([^"]*)"(?: within "([^"]*)")?$/ do |field, selector|
  with_scope(selector) do
    check(field)
  end
end

When /^(?:|I )uncheck "([^"]*)"(?: within "([^"]*)")?$/ do |field, selector|
  with_scope(selector) do
    uncheck(field)
  end
end

When /^(?:|I )choose "(.*)"$/ do |field|
  choose(field)
end

When /^(?:|I )attach the file "([^"]*)" to "([^"]*)"(?: within "([^"]*)")?$/ do |path, field, selector|
  with_scope(selector) do
    attach_file(field, path)
  end
end

Then /^visiting "([^"]*)" should fail with a not found error$/ do |path|
  expect {
    visit path
  }.to raise_error(ActiveRecord::RecordNotFound)
end

Then /^visiting "([^"]*)" should fail with "([^"]*)"$/ do |path, flash_error|
  visit path
  step %{I should see "#{flash_error}" within ".flash"}
end

Then /^(?:|I )should see JSON:$/ do |expected_json|
  require 'json'
  expected = JSON.pretty_generate(JSON.parse(expected_json))
  actual   = JSON.pretty_generate(JSON.parse(response.body))
  expected.should == actual
end

Then /^(?:|I )should see "([^"]*)"(?: within "([^"]*)")?$/ do |text, selector|
  with_scope(selector) do
    page.should have_content(text)
  end
end

Then /^(?:|I )should see the raw text "([^"]*)"(?: within "([^"]*)")?$/ do |text, selector|
  with_scope(selector) do
    page.body.should =~ /#{Regexp.escape(text)}/m
  end
end

Then /^(?:|I )should see "([^"]*)"(?: within "([^"]*)") on my work?$/ do |text, selector|
  my_work = User.current_user.works.first.id
  selector = "#work_#{my_work}"
  with_scope(selector) do
    if page.respond_to? :should
      page.should have_content(text)
    else
      assert page.has_content?(text)
    end
  end
end

Then /^(?:|I )should not see "([^"]*)"(?: within "([^"]*)") on the other work?$/ do |text, selector|
  other_user = User.find_by(login: "mywarning1")
  other_work = other_user.works.first.id
  selector = "#work_#{other_work}"
  with_scope(selector) do
    if page.respond_to? :should
      page.should have_no_content(text)
    else
      assert page.has_no_content?(text)
    end
  end
end

Then /^(?:|I )should see '([^']*)'(?: within "([^"]*)")?$/ do |text, selector|
  with_scope(selector) do
    if page.respond_to? :should
      page.should have_content(text)
    else
      assert page.has_content?(text)
    end
  end
end

Then /^(?:|I )should see \/([^\/]*)\/(?: within "([^"]*)")?$/ do |regexp, selector|
  regexp = Regexp.new(regexp)
  with_scope(selector) do
    if page.respond_to? :should
      page.should have_xpath('//*', text: regexp)
    else
      assert page.has_xpath?('//*', text: regexp)
    end
  end
end

Then /^(?:|I )should not see "([^"]*)"(?: within "([^"]*)")?$/ do |text, selector|
  with_scope(selector) do
    if page.respond_to? :should
      page.should have_no_content(text)
    else
      assert page.has_no_content?(text)
    end
  end
end

Then /^(?:|I )should not see '([^']*)'(?: within "([^"]*)")?$/ do |text, selector|
  with_scope(selector) do
    if page.respond_to? :should
      page.should have_no_content(text)
    else
      assert page.has_no_content?(text)
    end
  end
end

Then /^(?:|I )should not see \/([^\/]*)\/(?: within "([^"]*)")?$/ do |regexp, selector|
  regexp = Regexp.new(regexp)
  with_scope(selector) do
    if page.respond_to? :should
      page.should have_no_xpath('//*', text: regexp)
    else
      assert page.has_no_xpath?('//*', text: regexp)
    end
  end
end

Then /"(.*)" should appear before "(.*)"/ do |first_example, second_example|
  page.body.should =~ /#{first_example}.*#{second_example}/m
end

Then /^the "([^"]*)" field(?: within "([^"]*)")? should contain "([^"]*)"$/ do |field, selector, value|
  with_scope(selector) do
    field = find_field(field)
    field_value = (field.tag_name == 'textarea') ? field.text : field.value
    if field_value.respond_to? :should
      field_value.should =~ /#{value}/
    else
      assert_match(/#{value}/, field_value)
    end
  end
end

Then /^the field labeled "([^"]*)" should contain "([^"]*)"$/ do |label, value|
  field = find_field(label)
  field_value = (field.tag_name == 'textarea') ? field.text : field.value
  if field_value.respond_to? :should
    field_value.should =~ /#{value}/
  else
    assert_match(/#{value}/, field_value)
  end
end

Then /^the "([^"]*)" field(?: within "([^"]*)")? should not contain "([^"]*)"$/ do |field, selector, value|
  with_scope(selector) do
    field = find_field(field)
    field_value = (field.tag_name == 'textarea') ? field.text : field.value
    if field_value.respond_to? :should_not
      field_value.should_not =~ /#{value}/
    else
      assert_no_match(/#{value}/, field_value)
    end
  end
end

Then /^the "(.*?)" (checkbox|radio button)(?: within "(.*?)")? should be checked( and disabled)?$/ do |label, _input_type, selector, disabled|
  with_scope(selector) do
    assert has_checked_field?(label, disabled: disabled.present?)
  end
end

Then /^the "(.*?)" checkbox(?: within "(.*?)")? should not be checked$/ do |label, selector|
  with_scope(selector) do
    assert has_unchecked_field?(label)
  end
end

Then /^(?:|I )should be on (.+)$/ do |page_name|
  current_path = URI.parse(current_url).path
  if current_path.respond_to? :should
    current_path.should == path_to(page_name)
  else
    assert_equal path_to(page_name), current_path
  end
end

Then /^(?:|The )url should include (.+)$/ do |url|
  current_url.should include(url)
end

Then /^(?:|I )should have the following query string:$/ do |expected_pairs|
  query = URI.parse(current_url).query
  actual_params = query ? CGI.parse(query) : {}
  expected_params = {}
  expected_pairs.rows_hash.each_pair{|k,v| expected_params[k] = v.split(',')}

  if actual_params.respond_to? :should
    actual_params.should == expected_params
  else
    assert_equal expected_params, actual_params
  end
end

Then /^I should download a ([^"]*) file with(?: (\d+) rows and)? the header row "(.*?)"$/ do |type, rows, header|
  page.response_headers['Content-Disposition'].should =~ /attachment; filename=.*?\.#{type}/i
  page.response_headers['Content-Type'].should =~ /\/#{type}/i
  body_without_bom = page.body.encode("UTF-8").delete!("\xEF\xBB\xBF")
  csv = CSV.parse(body_without_bom, col_sep: "\t") # array of arrays
  expect(csv.first.join(" ")).to eq(header)
  expect(csv.size).to eq(rows) unless rows.blank? || rows.zero?
end

Then /^show me the page$/ do
  save_and_open_page
  sleep 120
end

Then /^show me the network traffic$/ do
  puts page.driver.network_traffic.to_yaml
end

Then /^cookie "([^\"]*)" should be like "([^\"]*)"$/ do |cookie, value|
  cookie_value = Capybara.current_session.driver.request.cookies.[](cookie)
  if cookie_value.respond_to? :should
    cookie_value.should =~ /#{value}/
  else
    assert cookie_value =~ /#{value}/
  end
end

Then /^cookie "([^"]*)" should be deleted$/ do |cookie|
  cookie_value = Capybara.current_session.driver.request.cookies.[](cookie)
  assert cookie_value.nil?
end
