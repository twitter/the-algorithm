When /^I choose to take my pseud off$/ do
  step %{I choose "Take my pseud off as well"}
  step %{I press "Yes, I'm sure"}
  step %{I should see "Orphaning was successful."}
end

When /^I choose to (?:keep|leave) my pseud on$/ do
  step %{I choose "Leave a copy of my pseud on"}
  step %{I press "Yes, I'm sure"}
  step %{I should see "Orphaning was successful."}
end

When /^I begin orphaning the work "([^"]*)"$/ do |name|
  step %{I wait 1 second}
  step %{I edit the work "#{name}"}
  step %{I follow "Orphan Work"}
  step %{I should see "Orphan Works"}
end

When /^I begin orphaning the series "([^"]*)"$/ do |name|
  step %{I wait 1 second}
  step %{I view the series "#{name}"}
  step %{I follow "Orphan Series"}
  step %{I should see "Orphan Series"}
end

When /^I orphan(?:| and take my pseud off) the (work|series) "([^"]*)"$/ do |type, name|
  step %{I begin orphaning the #{type} "#{name}"}
  step %{I choose to take my pseud off}
end

When /^I orphan and (?:keep|leave) my pseud on the (work|series) "([^"]*)"$/ do |type, name|
  step %{I begin orphaning the #{type} "#{name}"}
  step %{I choose to keep my pseud on}
end

Then /^"([^"]*)" (should|should not) be (?:a|the) (?:|co-)creator (?:of|on) the work "([^"]*)"$/ do |user, should_or_should_not, work|
  step %{I view the work "#{work}"}
  step %{I #{should_or_should_not} see "#{user}" within ".byline"}
end

Then /^"([^"]*)" (should|should not) be (?:a|the) (?:|co-)creator (?:of|on) Chapter (\d+) of "([^"]*)"$/ do |user, should_or_should_not, chapter, work|
  step %{I view the work "#{work}"}
  step %{I view the #{chapter}th chapter}

  if page.has_css? ".chapter .byline"
    # the chapter has different co-authors from the full work
    step %{I #{should_or_should_not} see "#{user}" within ".chapter .byline"}
  else
    # the chapter's co-authors are the same as the full work's
    step %{I #{should_or_should_not} see "#{user}" within ".byline"}
  end
end

Then /^"([^"]*)" (should|should not) be (?:a|the) (?:|co-)creator (?:of|on) the series "([^"]*)"$/ do |user, should_or_should_not, series|
  step %{I view the series "#{series}"}
  step %{I #{should_or_should_not} see "#{user}" within ".series.meta"}
end
