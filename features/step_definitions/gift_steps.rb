### NOTE: many of these steps rely on the background in gift.feature

Then /^"(.+)" should be notified by email about their gift "(.+)"$/ do |recipient, title|
  step %{1 email should be delivered to "#{recipient}"}
  step %{the email should contain "A gift work has been posted for you"}
  step %{the email should link to the "#{title}" work page}
end

When /^I have given the work to "(.*?)"/ do |recipient|
  step %{I give the work to "#{recipient}"}
  step %{I post the work without preview}
end

Given(/^I have refused the work/) do
  step %{I have given the work to "giftee1"}

  # Delay to force the cache to expire when the gift is refused:
  step "it is currently 1 second from now"

  step %{I am logged in as "giftee1" with password "something"}
  step %{I go to my gifts page}
  step %{I follow "Refuse Gift"}
end

When /^I have removed the recipients/ do
  fill_in("work_recipients", with: "")
  step %{I post the work without preview}
end

Then /^"(.*?)" should be listed as a recipient in the form/ do |recipient|
  recipients = page.find("input#work_recipients")['value']
  assert recipients =~ /#{recipient}/
end

Then /^"(.*?)" should not be listed as a recipient in the form/ do |recipient|
  recipients = page.find("input#work_recipients")['value']
  assert recipients !~ /#{recipient}/
end

Then(/^the gift for "(.*?)" should still exist on "(.*?)"$/) do |recipient, work|
  w = Work.find_by(title: work)
  assert w.gifts.map(&:recipient).include?(recipient)
end
