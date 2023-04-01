### GIVEN

Given /^I have no tags$/ do
  # Tag.delete_all if Tag.count > 1
  # silence_warnings {load "#{Rails.root}/app/models/fandom.rb"}
end

Given /^basic tags$/ do
  step %{the default ratings exist}
  step %{the basic warnings exist}
  Fandom.where(name: "No Fandom", canonical: true).first_or_create
  step %{the basic categories exist}
  step %{all indexing jobs have been run}
end

Given /^the default ratings exist$/ do
  # TODO: "Not Rated" should be adult, to match the behavior in production, but
  # there are many tests that rely on being able to view a "Not Rated" work
  # without clicking through the adult content warning. So until those tests
  # are fixed, we leave "Not Rated" as a non-adult rating.
  [
    ArchiveConfig.RATING_DEFAULT_TAG_NAME,
    ArchiveConfig.RATING_GENERAL_TAG_NAME,
    ArchiveConfig.RATING_TEEN_TAG_NAME
  ].each do |rating|
    Rating.find_or_create_by!(name: rating, canonical: true)
  end

  [
    ArchiveConfig.RATING_MATURE_TAG_NAME,
    ArchiveConfig.RATING_EXPLICIT_TAG_NAME
  ].each do |rating|
    Rating.find_or_create_by!(name: rating, canonical: true, adult: true)
  end
end

Given /^the basic warnings exist$/ do
  warnings = [ArchiveConfig.WARNING_DEFAULT_TAG_NAME,
              ArchiveConfig.WARNING_NONE_TAG_NAME]
  warnings.each do |warning|
    ArchiveWarning.find_or_create_by!(name: warning, canonical: true)
  end
end

Given /^all warnings exist$/ do
  step %{the basic warnings exist}
  warnings = [ArchiveConfig.WARNING_VIOLENCE_TAG_NAME,
              ArchiveConfig.WARNING_DEATH_TAG_NAME,
              ArchiveConfig.WARNING_NONCON_TAG_NAME,
              ArchiveConfig.WARNING_CHAN_TAG_NAME]
  warnings.each do |warning|
    ArchiveWarning.find_or_create_by!(name: warning, canonical: true)
  end
end

Given /^the basic categories exist$/ do
  %w(Gen Other F/F Multi F/M M/M).each do |category|
    Category.find_or_create_by!(name: category, canonical: true)
  end
end

Given /^I have a canonical "([^\"]*)" fandom tag named "([^\"]*)"$/ do |media, fandom|
  fandom = Fandom.find_or_create_by_name(fandom)
  fandom.update(canonical: true)
  media = Media.find_or_create_by_name(media)
  media.update(canonical: true)
  fandom.add_association media
end

Given /^I add the fandom "([^\"]*)" to the character "([^\"]*)"$/ do |fandom, character|
  char = Character.find_or_create_by(name: character)
  fand = Fandom.find_or_create_by_name(fandom)
  char.add_association(fand)
end

Given /^a canonical character "([^\"]*)" in fandom "([^\"]*)"$/ do |character, fandom|
  char = Character.where(name: character, canonical: true).first_or_create
  fand = Fandom.where(name: fandom, canonical: true).first_or_create
  char.add_association(fand)
end

Given /^a canonical relationship "([^\"]*)" in fandom "([^\"]*)"$/ do |relationship, fandom|
  rel = Relationship.where(name: relationship, canonical: true).first_or_create
  fand = Fandom.where(name: fandom, canonical: true).first_or_create
  rel.add_association(fand)
end

Given /^a (non-?canonical|canonical) (\w+) "([^\"]*)"$/ do |canonical_status, tag_type, tag_name|
  t = tag_type.classify.constantize.find_or_create_by_name(tag_name)
  t.canonical = canonical_status == "canonical"
  t.save
end

Given /^a synonym "([^\"]*)" of the tag "([^\"]*)"$/ do |synonym, merger|
  merger = Tag.find_by_name(merger)
  merger_type = merger.type

  synonym = merger_type.classify.constantize.find_or_create_by(name: synonym)
  synonym.reload.merger = merger
  synonym.save
end

Given /^"([^\"]*)" is a metatag of the (\w+) "([^\"]*)"$/ do |metatag, tag_type, tag|
  tag = tag_type.classify.constantize.find_or_create_by_name(tag)
  metatag = tag_type.classify.constantize.find_or_create_by_name(metatag)
  tag.meta_tags << metatag
  tag.save
end

Given /^I am logged in as a tag wrangler$/ do
  step "I start a new session"
  username = "wrangler"
  step %{I am logged in as "#{username}"}
  user = User.find_by(login: username)
  role = Role.find_or_create_by(name: "tag_wrangler")
  user.roles = [role]
end

Given /^the tag wrangler "([^\"]*)" with password "([^\"]*)" is wrangler of "([^\"]*)"$/ do |user, password, fandomname|
  tw = User.find_by(login: user)

  if tw.blank?
    tw = FactoryBot.create(:user, login: user, password: password)
  else
    tw.password = password
    tw.password_confirmation = password
    tw.save
  end

  role = Role.find_or_create_by(name: "tag_wrangler")
  tw.roles = [role]

  step %{I am logged in as "#{user}" with password "#{password}"}

  fandom = Fandom.where(name: fandomname, canonical: true).first_or_create
  visit tag_wranglers_url
  fill_in "tag_fandom_string", with: fandomname
  click_button "Assign"
end

Given /^a tag "([^\"]*)" with(?: (\d+))? comments$/ do |tagname, n_comments|
  tag = Fandom.find_or_create_by_name(tagname)
  step "I start a new session"

  n_comments = 3 if n_comments.blank? || n_comments.zero?
  FactoryBot.create_list(:comment, n_comments.to_i, :on_tag, commentable: tag)
end

Given /^(?:a|the) canonical(?: "([^"]*)")? fandom "([^"]*)" with (\d+) works$/ do |media, tag_name, number_of_works|
  fandom = FactoryBot.create(:fandom, name: tag_name, canonical: true)
  fandom.add_association(Media.find_by(name: media)) if media.present?
  number_of_works.to_i.times do
    FactoryBot.create(:work, fandom_string: tag_name)
  end
  step %(the periodic filter count task is run)
end

Given /^a period-containing tag "([^\"]*)" with(?: (\d+))? comments$/ do |tagname, n_comments|
  tag = Fandom.find_or_create_by_name(tagname)
  step "I start a new session"

  n_comments = 3 if n_comments.blank? || n_comments.zero?
  FactoryBot.create_list(:comment, n_comments.to_i, :on_tag, commentable: tag)
end

Given /^the unsorted tags setup$/ do
  30.times do |i|
    UnsortedTag.find_or_create_by_name("unsorted tag #{i}")
  end
end

Given /^the tag wrangling setup$/ do
  step %{basic tags}
  step %{a media exists with name: "TV Shows", canonical: true}
  step %{I am logged in as a random user}
  step %{I post the work "Revenge of the Sith 2" with fandom "Star Wars, Stargate SG-1" with character "Daniel Jackson" with second character "Jack O'Neil" with rating "Not Rated" with relationship "JackDaniel"}
  step %{The periodic tag count task is run}
  step %{I flush the wrangling sidebar caches}
end

Given /^I have posted a Wrangling Guideline?(?: titled "([^\"]*)")?$/ do |title|
  step %{I am logged in as an admin}
  visit new_wrangling_guideline_path
  if title
    fill_in("Guideline text", with: "This is a page about how we wrangle things.")
    fill_in("Title", with: title)
    click_button("Post")
  else
    step %{I make a 1st Wrangling Guideline}
  end
end

Given(/^the following typed tags exists$/) do |table|
  table.hashes.each do |hash|
    type = hash["type"].downcase.to_sym
    hash.delete("type")
    FactoryBot.create(type, hash)
  end
end

Given /^the tag "([^"]*)" does not exist$/ do |tag_name|
  tag = Tag.find_by_name(tag_name)
  tag.destroy if tag.present?
end

### WHEN

When /^the periodic tag count task is run$/i do
  RedisSetJobSpawner.perform_now("TagCountUpdateJob")
end

When /^the periodic filter count task is run$/i do
  FilterCount.update_counts_for_small_queue
  FilterCount.update_counts_for_large_queue
end

When /^I check the canonical option for the tag "([^"]*)"$/ do |tagname|
  tag = Tag.find_by(name: tagname)
  check("canonicals_#{tag.id}")
end

When /^I select "([^"]*)" for the unsorted tag "([^"]*)"$/ do |type, tagname|
  tag = Tag.find_by(name: tagname)
  select(type, from: "tags[#{tag.id}]")
end

When /^I check the (?:mass )?wrangling option for "([^"]*)"$/ do |tagname|
  tag = Tag.find_by(name: tagname)
  check("selected_tags_#{tag.id}")
end

When "I edit the tag {string}" do |tag|
  tag = Tag.find_by!(name: tag)
  visit tag_path(tag)
  within(".header") do
    click_link("Edit")
  end
end

When /^I view the tag "([^\"]*)"$/ do |tag|
  tag = Tag.find_by!(name: tag)
  visit tag_path(tag)
end

When /^I create the fandom "([^\"]*)" with id (\d+)$/ do |name, id|
 tag = Fandom.new(name: name)
 tag.id = id.to_i
 tag.canonical = true
 tag.save
end

When /^I set up the comment "([^"]*)" on the tag "([^"]*)"$/ do |comment_text, tag|
  tag = Tag.find_by!(name: tag)
  visit tag_url(tag)
  click_link(" comment")
  fill_in("Comment", with: comment_text)
end

When /^I post the comment "([^"]*)" on the tag "([^"]*)"$/ do |comment_text, tag|
  step "I set up the comment \"#{comment_text}\" on the tag \"#{tag}\""
  click_button("Comment")
end

When /^I post the comment "([^"]*)" on the period-containing tag "([^"]*)"$/ do |comment_text, tag|
  step "I am on the search tags page"
  fill_in("tag_search_name", with: tag)
  click_button "Search tags"
  click_link(tag)
  click_link(" comment")
  fill_in("Comment", with: comment_text)
  click_button("Comment")
end

When /^I post the comment "([^"]*)" on the tag "([^"]*)" via web$/ do |comment_text, tag|
  step %{I view the tag "#{tag}"}
  step %{I follow " comments"}
    step %{I fill in "Comment" with "#{comment_text}"}
    step %{I press "Comment"}
  step %{I should see "Comment created!"}
end

When /^I add "([^\"]*)" to my favorite tags$/ do |tag|
  step %{I view the "#{tag}" works index}
  step %{I press "Favorite Tag"}
end

When /^I remove "([^\"]*)" from my favorite tags$/ do |tag|
  step %{I view the "#{tag}" works index}
  step %{I press "Unfavorite Tag"}
end

When /^the tag "([^\"]*)" is decanonized$/ do |tag|
  tag = Tag.find_by!(name: tag)
  tag.canonical = false
  tag.save
end

When /^the tag "([^"]*)" is canonized$/ do |tag|
  tag = Tag.find_by!(name: tag)
  tag.canonical = true
  tag.save
end

When /^I make a(?: (\d+)(?:st|nd|rd|th)?)? Wrangling Guideline$/ do |n|
  n = 1 if n.zero?
  visit new_wrangling_guideline_path
  fill_in("Guideline text", with: "Number #{n} posted Wrangling Guideline, this is.")
  fill_in("Title", with: "Number #{n} Wrangling Guideline")
  click_button("Post")
end

When /^(\d+) Wrangling Guidelines? exists?$/ do |n|
  (1..n).each do |i|
    FactoryBot.create(:wrangling_guideline, id: i)
  end
end

When /^I flush the wrangling sidebar caches$/ do
  [Fandom, Character, Relationship, Freeform].each do |klass|
    Rails.cache.delete("/wrangler/counts/sidebar/#{klass}")
  end
end

When /^I syn the tag "([^"]*)" to "([^"]*)"$/ do |syn, merger|
  syn = Tag.find_by(name: syn)
  visit edit_tag_path(syn)
  fill_in("Synonym of", with: merger)
  click_button("Save changes")
end

When /^I de-syn the tag "([^"]*)" from "([^"]*)"$/ do |syn, merger|
  merger = Tag.find_by(name: merger)
  syn_id = Tag.find_by(name: syn).id
  visit edit_tag_path(merger)
  check("child_Merger_associations_to_remove_#{syn_id}")
  click_button("Save changes")
end

When /^I subtag the tag "([^"]*)" to "([^"]*)"$/ do |subtag, metatag|
  subtag = Tag.find_by(name: subtag)
  visit edit_tag_path(subtag)
  fill_in("Add MetaTags:", with: metatag)
  click_button("Save changes")
end

When /^I remove the metatag "([^"]*)" from "([^"]*)"$/ do |metatag, subtag|
  subtag = Tag.find_by(name: subtag)
  metatag_id = Tag.find_by(name: metatag).id
  visit edit_tag_path(subtag)
  check("parent_MetaTag_associations_to_remove_#{metatag_id}")
  click_button("Save changes")
end

When /^I view the (canonical|synonymous|unfilterable|unwrangled|unwrangleable) (character|relationship|freeform) bin for "(.*?)"$/ do |status, type, tag|
  visit wrangle_tag_path(Tag.find_by(name: tag), show: type.pluralize, status: status)
end

### THEN

Then /^I should see the tag wrangler listed as an editor of the tag$/ do
  step %{I should see "wrangler" within "fieldset dl"}
end

Then /^I should see the tag search result "([^\"]*)"(?: within "([^"]*)")?$/ do |result, selector|
    with_scope(selector) do
      page.has_text?(result)
    end
end

Then /^I should not see the tag search result "([^\"]*)"(?: within "([^"]*)")?$/ do |result, selector|
    with_scope(selector) do
      page.has_no_text?(result)
    end
end

Then /^the ([\d]+)(?:st|nd|rd|th) tag result should contain "(.*?)"$/ do |n, text|
  selector = "ol.tag > li:nth-of-type(#{n})"
  with_scope(selector) do
    expect(page).to have_content(text)
  end
end

Then /^"([^\"]*)" should not be a tag wrangler$/ do |username|
  user = User.find_by(login: username)
  user.tag_wrangler.should be_falsey
end

Then /^"([^\"]*)" should be assigned to the wrangler "([^\"]*)"$/ do |fandom, username|
  user = User.find_by(login: username)
  fandom = Fandom.find_by(name: fandom)
  assignment = WranglingAssignment.where(user_id: user.id, fandom_id: fandom.id ).first
  assignment.should_not be_nil
end

Then /^"([^\"]*)" should not be assigned to the wrangler "([^\"]*)"$/ do |fandom, username|
  user = User.find_by(login: username)
  fandom = Fandom.find_by(name: fandom)
  assignment = WranglingAssignment.where(user_id: user.id, fandom_id: fandom.id ).first
  assignment.should be_nil
end

Then(/^the "([^"]*)" tag should be a "([^"]*)" tag$/) do |tagname, tag_type|
  tag = Tag.find_by(name: tagname)
  assert tag.type == tag_type
end

Then(/^the "([^"]*)" tag should (be|not be) canonical$/) do |tagname, canonical|
  tag = Tag.find_by(name: tagname)
  expected = canonical == "be"
  assert tag.canonical == expected
end

Then(/^the "([^"]*)" tag should (be|not be) unwrangleable$/) do |tagname, unwrangleable|
  tag = Tag.find_by(name: tagname)
  expected = unwrangleable == "be"
  assert tag.unwrangleable == expected
end

Then(/^the "([^"]*)" tag should be in the "([^"]*)" fandom$/) do |tagname, fandom_name|
  tag = Tag.find_by(name: tagname)
  fandom = Fandom.find_by(name: fandom_name)
  assert tag.has_parent?(fandom)
end

Then(/^show me what the tag "([^"]*)" is like$/) do |tagname|
  tag = Tag.find_by(name: tagname)
  puts tag.inspect
end
