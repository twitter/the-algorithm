### GIVEN

Given /^I have related works setup$/ do
  step "basic tags"
  step "all emails have been delivered"
  step "I start a new session"
  step %{I have loaded the "languages" fixture}

  inspiration = FactoryBot.create(:user, login: "inspiration", confirmed_at: Time.now.utc)
  FactoryBot.create(:user, login: "translator", confirmed_at: Time.now.utc)
  FactoryBot.create(:user, login: "remixer", confirmed_at: Time.now.utc)

  FactoryBot.create(:work, title: "Worldbuilding", authors: inspiration.pseuds)
  FactoryBot.create(:work, title: "Worldbuilding Two", authors: inspiration.pseuds)
end

Given /^an inspiring parent work has been posted$/ do
  step %{I post an inspiring parent work as testy}
end

# given for remixes / related works

Given /^a related work has been posted$/ do
  step %{I post a related work as remixer}
end

Given /^a related work has been posted and approved$/ do
  step %{I post a related work as remixer}
  step %{I approve a related work}
end

# given for translations

Given /^a translation has been posted$/ do
  step %{I post a translation as translator}
end

Given /^a translation has been posted and approved$/ do
  step %{I post a translation as translator}
  step %{I approve a related work}
end

### WHEN

When /^I post an inspiring parent work as testy$/ do
  step %{I am logged in as "testuser"}
  step %{I post the work "Parent Work"}
end

When /^I approve a related work$/ do
  step %{I am logged in as "inspiration"}
  step %{I go to my related works page}
  step %{I follow "Approve"}
  step %{I press "Yes, link me!"}
end

When /^I view my related works$/ do
  step %{I go to my related works page}
end

# when for remixes / related works

When /^I post a related work as remixer$/ do
  step %{I am logged in as "remixer"}
    step %{I go to the new work page}
    step %{I select "Not Rated" from "Rating"}
    step %{I check "No Archive Warnings Apply"}
    step %{I select "English" from "Choose a language"}
    step %{I fill in "Fandoms" with "Stargate"}
    step %{I fill in "Work Title" with "Followup"}
    step %{I fill in "content" with "That could be an amusing crossover."}
    step %{I list the work "Worldbuilding" as inspiration}
    step %{I press "Preview"}
  step %{I press "Post"}
end

When /^I post a related work as remixer for an external work$/ do
  step %{I am logged in as "remixer"}
  step %{I go to the new work page}
  step %{I select "Not Rated" from "Rating"}
  step %{I check "No Archive Warnings Apply"}
  step %{I select "English" from "Choose a language"}
  step %{I fill in "Fandoms" with "Stargate"}
  step %{I fill in "Work Title" with "Followup"}
  step %{I fill in "content" with "That could be an amusing crossover."}
  step %{I list an external work as inspiration}
  step %{I press "Preview"}
  step %{I press "Post"}
end

# when for translations

When /^I post a translation as translator$/ do
  step %{I am logged in as "translator"}
  step %{I draft a translation}
  step %{I press "Post"}
end

When /^I post a translation of my own work$/ do
  step %{I am logged in as "inspiration"}
  step %{I draft a translation}
  step %{I press "Post"}
end

When /^I draft a translation$/ do
  step %{I go to the new work page}
    step %{I check "No Archive Warnings Apply"}
    step %{I fill in "Fandoms" with "Stargate"}
    step %{I fill in "Work Title" with "Worldbuilding Translated"}
    step %{I fill in "content" with "That could be an amusing crossover."}
    step %{I list the work "Worldbuilding" as inspiration}
    step %{I check "This is a translation"}
    step %{I select "Deutsch" from "Choose a language"}
    step %{I press "Preview"}
end

When /^I list a series as inspiration$/ do
  with_scope("#parent-options") do
    fill_in("URL", with: "#{ArchiveConfig.APP_HOST}/series/123")
  end
end

When /^I list a nonexistent work as inspiration$/ do
  work = Work.find_by_id(123)
  work.destroy unless work.nil?
  with_scope("#parent-options") do
    fill_in("URL", with: "#{ArchiveConfig.APP_HOST}/works/123")
  end
end

### THEN

Then /^the original author should be emailed$/ do
  step "1 email should be delivered"
end

Then /^approving the related work should succeed$/ do
  step %{I should see "Link was successfully approved"}
end

# then for remixes / related works

Then /^a parent related work should be seen$/ do
  step %{I should see "Work was successfully posted"}
  step %{I should find a list for associations}
  step %{I should see "Inspired by Worldbuilding by inspiration" within ".preface .notes"}
end

Then /^I should see the inspiring parent work in the beginning notes$/ do
 step %{I should see "Inspired by Parent Work by testuser" within ".preface .notes"}
end

Then /^I should see a beginning note about related works$/ do
  step %{I should see "See the end of the work for other works inspired by this one" within ".preface .notes"}
end

Then /^I should see the related work in the end notes$/ do
  step %{I should see "Works inspired by this one:" within ".afterword .children"}
  step %{I should see "Followup by remixer" within ".afterword .children"}
end

Then /^I should not see the related work listed on the original work$/ do
  step %{I should not see "See the end of the work for other works inspired by this one"}
  step %{I should not see "Works inspired by this one:"}
  step %{I should not see "Followup by remixer"}
end

# then for translations

Then /^a parent translated work should be seen$/ do
  step %{I should see "Work was successfully posted"}
  step %{I should find a list for associations}
  step %{I should see "A translation of Worldbuilding by inspiration" within ".preface .notes"}
end

Then /^I should see the translation in the beginning notes$/ do
  step %{I should see "Translation into Deutsch available:" within ".preface .notes"}
  step %{I should see "Worldbuilding Translated by translator" within ".preface .notes"}
end

Then /^I should not see the translation listed on the original work$/ do
  step %{I should not see "Translation into Deutsch available:"}
  step %{I should not see "Worldbuilding Translated by translator"}
end

Then /^I should not see the translation in the end notes$/ do
  step %{I should not see "Translation into Deutsch available:" within ".afterword"}
  step %{I should not see "Worldbuilding Translated by translator" within ".afterword"}
end
