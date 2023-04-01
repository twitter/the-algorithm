World(Rack::Test::Methods)
# thanks to these guys: http://www.anthonyeden.com/2010/11/testing-rest-apis-with-cucumber-and-rack-test/

Given /^I use a PSP browser$/ do
  header 'Accept', '*/*;q=0.01'
end

Given /^I make a request for "([^\"]*)"$/ do |path|
  get path
end

Then /^the response should be "([^\"]*)"$/ do |status|
  last_response.status.should == status.to_i
end
