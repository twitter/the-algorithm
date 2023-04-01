DEFAULT_EXTERNAL_URL = "http://example.org/200"
DEFAULT_EXTERNAL_CREATOR = "Zooey Glass"
DEFAULT_EXTERNAL_TITLE = "A Work Not Posted To The AO3"
DEFAULT_EXTERNAL_SUMMARY = "This is my story, I am its author."
DEFAULT_EXTERNAL_FANDOM = "External Fandom"
DEFAULT_EXTERNAL_RELATIONSHIP = "Charater A & Character B"
DEFAULT_EXTERNAL_CATEGORY = "F/M"
DEFAULT_EXTERNAL_CHARACTERS = "Character A, Character B"
DEFAULT_BOOKMARK_NOTES = "I liked this story."
DEFAULT_BOOKMARK_TAGS = "Awesome"

Given /^an external work$/ do
  step %{I set up an external work}
  click_button("Create")
end

Given /^I set up an external work$/ do
  step %{mock websites with no content}
  visit new_external_work_path
  fill_in("URL", with: DEFAULT_EXTERNAL_URL)
  fill_in("external_work_author", with: DEFAULT_EXTERNAL_CREATOR)
  fill_in("external_work_title", with: DEFAULT_EXTERNAL_TITLE)
  step %{I fill in basic external work tags}
  fill_in("bookmark_notes", with: DEFAULT_BOOKMARK_NOTES)
  fill_in("Your tags", with: DEFAULT_BOOKMARK_TAGS)
end

Given /^I bookmark the external work "([^\"]*)"(?: with fandom "([^"]*)")?(?: with character "([^"]*)")?$/ do |title, fandom, character|
  step %{I set up an external work}
  fill_in("external_work_title", with: title)
  fill_in("Fandoms", with: fandom) if fandom.present?
  fill_in("Characters", with: character) if character.present?
  click_button("Create")
end

Given "{string} has bookmarked an external work" do |user|
  step %{mock websites with no content}
  step %{basic tags}
  step %{I am logged in as "#{user}"}
  visit new_external_work_path
  # Typically, when we write step definitions, we prefer to use a
  # field's id attribute over its label. But in this case,
  # we have to use the labels for some fields because the ids
  # change when JavaScript is enabled.
  fill_in("URL", with: DEFAULT_EXTERNAL_URL)
  fill_in("external_work_author", with: DEFAULT_EXTERNAL_CREATOR)
  fill_in("external_work_title", with: DEFAULT_EXTERNAL_TITLE)
  fill_in("external_work_summary", with: DEFAULT_EXTERNAL_SUMMARY)
  fill_in("Fandoms", with: DEFAULT_EXTERNAL_FANDOM)
  select(ArchiveConfig.RATING_TEEN_TAG_NAME, from: "external_work_rating_string")
  check(DEFAULT_EXTERNAL_CATEGORY)
  fill_in("Relationships", with: DEFAULT_EXTERNAL_RELATIONSHIP)
  fill_in("Characters", with: DEFAULT_EXTERNAL_CHARACTERS)
  click_button("Create")
end

Given "the external work {string} has {int} {word} tag(s)" do |title, count, type|
  work = ExternalWork.find_by(title: title)
  work.send("#{type.pluralize}=", FactoryBot.create_list(type.to_sym, count))
end

When /^I view the external work "([^\"]*)"$/ do |external_work|
  external_work = ExternalWork.find_by_title(external_work)
  visit external_work_url(external_work)
end

When /^the (character|fandom|relationship) "(.*?)" is removed from the external work "(.*?)"$/ do |tag_type, tag, title|
  external_work = ExternalWork.find_by(title: title)
  tags = external_work.tags.where(type: tag_type).pluck(:name) - [tag]
  tag_string = tags.join(", ")
  step %{I am logged in as a "policy_and_abuse" admin}
  visit edit_external_work_path(external_work)
  fill_in("work_#{tag_type}", with: tag_string)
  click_button("Update External work")
  step %{all indexing jobs have been run}
end

Then /^the work info for my new bookmark should match the original$/ do
  works = ExternalWork.where(url: "http://example.org/200").order("created_at ASC")
  original_work = works[0]
  new_work = works[1]
  expect(new_work.author).to eq(original_work.author)
  expect(new_work.title).to eq(original_work.title)
  step %{the summary and tag info for my new bookmark should match the original}
end

Then /^the title and creator info for my new bookmark should vary from the original$/ do
  works = ExternalWork.where(url: "http://example.org/200").order("created_at ASC")
  original_work = works[0]
  new_work = works[1]
  expect(new_work.author).not_to eq(original_work.author)
  expect(new_work.title).not_to eq(original_work.title)
end

Then /^the summary and tag info for my new bookmark should match the original$/ do
  works = ExternalWork.where(url: "http://example.org/200").order("created_at ASC")
  original_work = works[0]
  new_work = works[1]
  expect(new_work.summary).to eq(original_work.summary)
  expect(new_work.tags).to eq(original_work.tags)
end
