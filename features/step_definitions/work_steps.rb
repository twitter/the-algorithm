require "cgi"

DEFAULT_TITLE = "My Work Title"
DEFAULT_FANDOM = "Stargate SG-1"
DEFAULT_RATING = "Not Rated"
DEFAULT_WARNING = "No Archive Warnings Apply"
DEFAULT_FREEFORM = "Scary tag"
DEFAULT_CONTENT = "That could be an amusing crossover."
DEFAULT_CATEGORY = "Other"

### Setting up a work
# These steps get used a lot by many other steps and tests to create works in the archive to test with

When /^I fill in the basic work information for "([^"]*)"$/ do |title|
  step %{I fill in basic work tags}
  check(DEFAULT_WARNING)
  fill_in("Work Title", with: title)
  select("English", from: "work_language_id")
  fill_in("content", with: DEFAULT_CONTENT)
end
# Here we set up a draft and can then post it as a draft, preview and post, post,
# or fill in additional information on the work form.
# Example: I set up the draft "Foo"
# Example: I set up the draft "Foo" with fandom "Captain America" in the collection "MCU Stories" as a gift to "Bob"
#
# This is a complex regexp because it attempts to be flexible and match a lot of options (including using a/the, in/to etc)
# the (?: ) construct means: do not use the stuff in () as a capture/match
# the ()? construct means: the stuff in () is optional
# This can handle any number of the options being omitted, but you DO have to match in order
# if you are using more than one of the options. That is, if you are specifying fandom AND freeform AND collection,
# it has to be:
#   with fandom "X" with freeform "Y" in collection "Z"
# and NOT:
#   with freeform "Y" in collection "Z" with fandom "X"
#
# If you add to this regexp, you probably want to update all the
# similar regexps in the I post/Given the draft/the work steps below.
When /^I set up (?:a|the) draft "([^"]*)"(?: with fandom "([^"]*)")?(?: with character "([^"]*)")?(?: with second character "([^"]*)")?(?: with freeform "([^"]*)")?(?: with second freeform "([^"]*)")?(?: with category "([^"]*)")?(?: with rating "([^\"]*)")?(?: (?:in|to) (?:the )?collection "([^"]*)")?(?: as a gift (?:for|to) "([^"]*)")?(?: as part of a series "([^"]*)")?(?: with relationship "([^"]*)")?(?: using the pseud "([^"]*)")?$/ do |title, fandom, character, character2, freeform, freeform2, category, rating, collection, recipient, series, relationship, pseud|
  step %{basic tags}
  visit new_work_path
  step %{I fill in the basic work information for "#{title}"}
  select(rating.blank? ? DEFAULT_RATING : rating, from: "Rating")
  check(category.blank? ? DEFAULT_CATEGORY : category)
  fill_in("Fandoms", with: (fandom.blank? ? DEFAULT_FANDOM : fandom))
  fill_in("Additional Tags", with: (freeform.blank? ? DEFAULT_FREEFORM : freeform)+(freeform2.blank? ? '' : ','+freeform2))
  unless character.blank?
    fill_in("work[character_string]", with: character + ( character2.blank? ? '' : ','+character2 ) )
  end
  unless collection.blank?
    c = Collection.find_by(title: collection)
    fill_in("Collections", with: c.name)
  end
  unless series.blank?
    if page.has_select?("work[series_attributes][id]", with_options: [series])
      select(series, from: "work[series_attributes][id]")
    else
      fill_in("work[series_attributes][title]", with: series)
    end
  end
  unless relationship.blank?
    fill_in("work[relationship_string]", with: relationship)
  end
  select(pseud, from: "work[author_attributes][ids][]") unless pseud.blank?
  fill_in("work_recipients", with: "#{recipient}") unless recipient.blank?
end

# This is the same regexp as above
When /^I post (?:a|the) (?:(\d+) chapter )?work "([^"]*)"(?: with fandom "([^"]*)")?(?: with character "([^"]*)")?(?: with second character "([^"]*)")?(?: with freeform "([^"]*)")?(?: with second freeform "([^"]*)")?(?: with category "([^"]*)")?(?: with rating "([^\"]*)")?(?: (?:in|to) (?:the )?collection "([^"]*)")?(?: as a gift (?:for|to) "([^"]*)")?(?: as part of a series "([^"]*)")?(?: with relationship "([^"]*)")?(?: using the pseud "([^"]*)")?$/ do |number_of_chapters, title, fandom, character, character2, freeform, freeform2, category, rating, collection, recipient, series, relationship, pseud|
  # If the work is already a draft then visit the preview page and post it
  work = Work.find_by(title: title)
  if work
    visit preview_work_url(work)
    click_button("Post")
  else
    # Note: this will match the above regexp and work just fine even if all the options are blank!
    step %{I set up the draft "#{title}" with fandom "#{fandom}" with character "#{character}" with second character "#{character2}" with freeform "#{freeform}" with second freeform "#{freeform2}" with category "#{category}" with rating "#{rating}" in collection "#{collection}" as a gift to "#{recipient}" as part of a series "#{series}" with relationship "#{relationship}" using the pseud "#{pseud}"}
    click_button("Post")
  end
  # Now add the chapters
  if number_of_chapters.present? && number_of_chapters.to_i > 1
    work = Work.find_by_title(title)
    visit work_url(work)
    (number_of_chapters.to_i - 1).times do
      step %{I follow "Add Chapter"}
      fill_in("content", with: "Yet another chapter.")
      click_button("Post")
    end
  end
  step %{all indexing jobs have been run}
  step "the periodic tag count task is run"
  step %(the periodic filter count task is run)
end

# Again, same regexp, it just creates a draft and not a posted
# To test posting after preview, use: Given the draft "Foo"
# Then use: When I post the work "Foo"
# and the above step
Given /^the draft "([^"]*)"(?: with fandom "([^"]*)")?(?: with character "([^"]*)")?(?: with second character "([^"]*)")?(?: with freeform "([^"]*)")?(?: with second freeform "([^"]*)")?(?: with category "([^"]*)")?(?: (?:in|to) (?:the )?collection "([^"]*)")?(?: as a gift (?:for|to) "([^"]*)")?(?: as part of a series "([^"]*)")?(?: with relationship "([^"]*)")?(?: using the pseud "([^"]*)")?$/ do |title, fandom, character, character2, freeform, freeform2, category, collection, recipient, series, relationship, pseud|
  step %{I set up the draft "#{title}" with fandom "#{fandom}" with character "#{character}" with second character "#{character2}" with freeform "#{freeform}" with second freeform "#{freeform2}" with category "#{category}" in collection "#{collection}" as a gift to "#{recipient}" as part of a series "#{series}" with relationship "#{relationship}" using the pseud "#{pseud}"}
  click_button("Preview")
end

When /^I post the works "([^"]*)"$/ do |worklist|
  worklist.split(/, ?/).each do |work_title|
    step %{I post the work "#{work_title}"}
    # Ensure all works are created with different timestamps to avoid flakiness
    step %{it is currently 1 second from now}
  end
end

### GIVEN

Given(/^I have the Battle set loaded$/) do
  step %{I have loaded the fixtures}
  step %{I have Battle 12 prompt meme fully set up}
  step %{everyone has signed up for Battle 12}
  step %{mod fulfills claim}
  step %{I reveal the "Battle 12" challenge}
  step %{I am logged in as "myname4"}
  step %{the statistics for all works are updated}
  step %{all indexing jobs have been run}
end

Given /^I have no works or comments$/ do
  Work.delete_all
  Comment.delete_all
end

Given /^the chaptered work(?: with ([\d]+) chapters)?(?: with ([\d]+) comments?)? "([^"]*)"$/ do |n_chapters, n_comments, title|
  step %{I start a new session}
  step %{basic tags}

  title ||= "Blabla"
  n_chapters ||= 2

  work = FactoryBot.create(:work, title: title, expected_number_of_chapters: n_chapters.to_i)

  # In order to make sure that the chapter positions are valid, we have to set
  # them manually. So we can't use create_list, and have to loop instead:
  (n_chapters.to_i - 1).times do |index|
    FactoryBot.create(:chapter, work: work, position: index + 2)
  end

  # Make sure that the word count is set properly:
  work.save

  n_comments ||= 0
  FactoryBot.create_list(:comment, n_comments.to_i, :by_guest,
                         commentable: work.first_chapter,
                         comment_content: "Bla bla")
end

Given /^I have a work "([^"]*)"$/ do |work|
  step %{I am logged in as a random user}
  step %{I post the work "#{work}"}
end

Given /^I have a locked work "([^"]*)"$/ do |work|
  step %{I am logged in as a random user}
  step %{I post the locked work "#{work}"}
end

Given /^I have a multi-chapter draft$/ do
  step %{I am logged in as a random user}
  step %{I post the chaptered draft "Multi-chapter Draft"}
end

Given /^the work(?: "([^"]*)")? with(?: (\d+))? comments setup$/ do |title, n_comments|
  step %{I start a new session}
  step %{basic tags}

  title ||= "Blabla"
  work = FactoryBot.create(:work, title: title)

  n_comments = 3 if n_comments.blank? || n_comments.zero?
  FactoryBot.create_list(:comment, n_comments.to_i, :by_guest,
                         commentable: work.last_posted_chapter)
end

Given /^the work(?: "([^"]*)")? with(?: (\d+))? bookmarks? setup$/ do |title, n_bookmarks|
  step %{I start a new session}
  step %{basic tags}

  title ||= "Blabla"
  work = FactoryBot.create(:work, title: title)

  n_bookmarks = 3 if n_bookmarks.blank? || n_bookmarks.zero?
  FactoryBot.create_list(:bookmark, n_bookmarks.to_i, bookmarkable: work)
end

Given /^the chaptered work setup$/ do
  step %{the chaptered work with 3 chapters "BigBang"}
end

Given /^the chaptered work with comments setup$/ do
  step %{the chaptered work with 3 chapters "BigBang"}
  step "I am logged in as a random user"
    step %{I view the work "BigBang"}
    step %{I post a comment "Woohoo"}
  (2..3).each do |i|
    step %{I view the work "BigBang"}
    step %{I view the #{i.to_s}th chapter}
    step %{I post a comment "Woohoo"}
  end
  step "I log out"
end

Given "the work {string}" do |title|
  FactoryBot.create(:work, title: title)
end

Given "the work {string} by {string}" do |title, login|
  user = ensure_user(login)
  FactoryBot.create(:work, title: title, authors: [user.default_pseud])
end

Given "the work {string} by {string} and {string}" do |title, login1, login2|
  user1 = ensure_user(login1)
  user2 = ensure_user(login2)
  FactoryBot.create(:work, title: title, authors: [user1.default_pseud, user2.default_pseud])
end

Given /^the work "([^\"]*)" by "([^\"]*)" with chapter two co-authored with "([^\"]*)"$/ do |work, author, coauthor|
  step %{I am logged in as "#{author}"}
  step %{I post the work "#{work}"}
  step %{a chapter with the co-author "#{coauthor}" is added to "#{work}"}
end

Given /^there is a work "([^"]*)" in an unrevealed collection "([^"]*)"$/ do |work, collection|
  step %{I have the hidden collection "#{collection}"}
  step %{I am logged in as a random user}
  step %{I post the work "#{work}" to the collection "#{collection}"}
  step %{I log out}
end

Given /^there is a work "([^"]*)" in an anonymous collection "([^"]*)"$/ do |work, collection|
  step %{I have the anonymous collection "#{collection}"}
  step %{I am logged in as a random user}
  step %{I post the work "#{work}" to the collection "#{collection}"}
  step %{I log out}
end

Given /^I am logged in as the author of "([^"]*)"$/ do |work|
  work = Work.find_by_title(work)
  step %{I am logged in as "#{work.users.first.login}"}
end

Given /^the spam work "([^\"]*)"$/ do |work|
  step %{I have a work "#{work}"}
  step %{I log out}
  w = Work.find_by_title(work)
  w.update_attribute(:spam, true)
  w.update_attribute(:hidden_by_admin, true)
end

Given "the user-defined tag limit is {int}" do |count|
  allow(ArchiveConfig).to receive(:USER_DEFINED_TAGS_MAX).and_return(count)
end

Given "the work {string} has {int} {word} tag(s)" do |title, count, type|
  work = Work.find_by(title: title)
  work.send("#{type.pluralize}=", FactoryBot.create_list(type.to_sym, count))
end

### WHEN

When /^I view the ([\d]+)(?:st|nd|rd|th) chapter$/ do |chapter_no|
  (chapter_no.to_i - 1).times do |i|
    step %{I follow "Next Chapter"}
  end
end

When /^I view the work "([^"]*)"(?: in (full|chapter-by-chapter) mode)?$/ do |work, mode|
  work = Work.find_by_title(work)
  visit work_path(work)
  step %{I follow "Entire Work"} if mode == "full"
  step %{I follow "Chapter by Chapter"} if mode == "chapter-by-chapter"
end

When /^I view a deleted work$/ do
  visit "/works/12345/chapters/12345"
end

When /^I view a deleted chapter$/ do
  step "the draft \"DeletedChapterWork\""
  work = Work.find_by(title: "DeletedChapterWork")
  visit "/works/#{work.id}/chapters/12345"
end

When /^I edit the work "([^"]*)"$/ do |work|
  work = Work.find_by(title: work)
  visit edit_work_path(work)
end
When /^I edit the draft "([^"]*)"$/ do |draft|
  step %{I edit the work "#{draft}"}
end

When /^I post the chaptered work "([^"]*)"(?: in the collection "([^"]*)")?$/ do |title, collection|
  step %{I post the work "#{title}" in the collection "#{collection}"}
  step %{I follow "Add Chapter"}
  fill_in("content", with: "Another Chapter.")
  click_button("Preview")
  step %{I press "Post"}
  step %{all indexing jobs have been run}
  step "the periodic tag count task is run"
end

When /^I post the chaptered draft "([^"]*)"$/ do |title|
  step %{the draft "#{title}"}
  step %{a draft chapter is added to "#{title}"}
end

When /^I post the work "([^"]*)" without preview$/ do |title|
  # we now post as our default test case
  step %{I post the work "#{title}"}
end

When /^a chapter is added to "([^"]*)"$/ do |work_title|
  step %{a draft chapter is added to "#{work_title}"}
  click_button("Post")
  step %{all indexing jobs have been run}
  step "the periodic tag count task is run"
end

When /^a chapter with the co-author "([^\"]*)" is added to "([^\"]*)"$/ do |coauthor, work_title|
  step %{a chapter is set up for "#{work_title}"}
  step %{I invite the co-author "#{coauthor}"}
  click_button("Post")
  step %{the user "#{coauthor}" accepts all co-creator requests}
  step %{all indexing jobs have been run}
  step "the periodic tag count task is run"
end

When /^a draft chapter is added to "([^"]*)"$/ do |work_title|
  step %{a chapter is set up for "#{work_title}"}
  step %{I press "Preview"}
  step %{all indexing jobs have been run}

  step "the periodic tag count task is run"
end

When /^I delete chapter ([\d]+) of "([^"]*)"$/ do |chapter, title|
  step %{I edit the work "#{title}"}
  step %{I follow "#{chapter}"}
  step %{I follow "Delete Chapter"}
  step %{I press "Yes, Delete Chapter"}
  step %{all indexing jobs have been run}
end

# Posts a chapter for the current user
When /^I post a chapter for the work "([^"]*)"$/ do |work_title|
  work = Work.find_by(title: work_title)
  visit work_url(work)
  step %{I follow "Add Chapter"}
  step %{I fill in "content" with "la la la la la la la la la la la"}
  step %{I post the chapter}
end

When /^a chapter is set up for "([^"]*)"$/ do |work_title|
  work = Work.find_by(title: work_title)
  user = work.users.first
  step %{I am logged in as "#{user.login}"}
  visit work_url(work)
  step %{I follow "Add Chapter"}
  step %{I fill in "content" with "la la la la la la la la la la la"}
end

# meant to be used in conjunction with above step
When /^I post the(?: draft)? chapter$/ do
  click_button("Post")
  step %{all indexing jobs have been run}

  step "the periodic tag count task is run"
end

Then /^I should see the default work content$/ do
  page.should have_content(DEFAULT_CONTENT)
end

Then /^I should not see the default work content$/ do
  page.should_not have_content(DEFAULT_CONTENT)
end

When /^I fill in basic work tags$/ do
  select(DEFAULT_RATING, from: "Rating")
  fill_in("Fandoms", with: DEFAULT_FANDOM)
  fill_in("Additional Tags", with: DEFAULT_FREEFORM)
end

When /^I fill in basic external work tags$/ do
  select(DEFAULT_RATING, from: "Rating")
  fill_in("Fandoms", with: DEFAULT_FANDOM)
  fill_in("Your tags", with: DEFAULT_FREEFORM)
end

When /^I set the fandom to "([^"]*)"$/ do |fandom|
  fill_in("Fandoms", with: fandom)
end
# on the edit multiple works page
When /^I select "([^"]*)" for editing$/ do |title|
  id = Work.find_by(title: title).id
  check("work_ids_#{id}")
end

When /^I edit the multiple works "([^"]*)" and "([^"]*)"/ do |title1, title2|
  # check if the works have been posted yet
  unless Work.where(title: title1).exists?
    step %{I post the work "#{title1}"}
  end
  unless Work.where(title: title2).exists?
    step %{I post the work "#{title2}"}
  end
  step %{I go to my edit multiple works page}
  step %{I select "#{title1}" for editing}
  step %{I select "#{title2}" for editing}
  step %{I press "Edit"}
end

When /^I edit multiple works with different comment moderation settings$/ do
  step %{I set up the draft "Work with Comment Moderation Enabled"}
  check("work_moderated_commenting_enabled")
  step %{I post the work without preview}
  step %{I post the work "Work with Comment Moderation Disabled"}
  step %{I go to my edit multiple works page}
  step %{I select "Work with Comment Moderation Enabled" for editing}
  step %{I select "Work with Comment Moderation Disabled" for editing}
  step %{I press "Edit"}
end

When /^I edit multiple works with different commenting settings$/ do
  step %{I set up the draft "Work with All Commenting Enabled"}
  choose("Registered users and guests can comment")
  step %{I post the work without preview}

  step %{I set up the draft "Work with Anonymous Commenting Disabled"}
  choose("Only registered users can comment")
  step %{I post the work without preview}

  step %{I set up the draft "Work with All Commenting Disabled"}
  choose("No one can comment")
  step %{I post the work without preview}

  step %{I go to my edit multiple works page}
  step %{I select "Work with All Commenting Enabled" for editing}
  step %{I select "Work with Anonymous Commenting Disabled" for editing}
  step %{I select "Work with All Commenting Disabled" for editing}
  step %{I press "Edit"}
end

When /^I edit multiple works coauthored as "(.*)" with "(.*)"$/ do |author, coauthor|
  step %{I coauthored the work "Shared Work 1" as "#{author}" with "#{coauthor}"}
  step %{I coauthored the work "Shared Work 2" as "#{author}" with "#{coauthor}"}
  step %{I go to my edit multiple works page}
  step %{I select "Shared Work 1" for editing}
  step %{I select "Shared Work 2" for editing}
  step %{I press "Edit"}
end

When /^the purge_old_drafts rake task is run$/ do
  step %{I run the rake task "work:purge_old_drafts"}
end

When /^the work "([^"]*)" was created (\d+) days ago$/ do |title, number|
  step "the draft \"#{title}\""
  work = Work.find_by(title: title)
  work.update_attribute(:created_at, number.to_i.days.ago)
  step %{all indexing jobs have been run}

  step "the periodic tag count task is run"
end

When /^I post the locked work "([^"]*)"$/ do |title|
  work = Work.find_by(title: work)
  if work.blank?
    step "the locked draft \"#{title}\""
    work = Work.find_by(title: title)
  end
  visit preview_work_url(work)
  click_button("Post")
  step %{all indexing jobs have been run}

  step "the periodic tag count task is run"
end

When /^the locked draft "([^"]*)"$/ do |title|
  step "basic tags"
  visit new_work_url
  step %{I fill in the basic work information for "#{title}"}
  check("work_restricted")
  click_button("Preview")
end

When /^I lock the work$/ do
  check("work_restricted")
end

When /^I lock the work "([^"]*)"$/ do |work|
  step %{I edit the work "#{work}"}
  step %{I lock the work}
  step %{I post the work}
end

When /^I unlock the work$/ do
  uncheck("work_restricted")
end

When /^I unlock the work "([^"]*)"$/ do |work|
  step %{I edit the work "#{work}"}
  step %{I unlock the work}
  step %{I post the work}
end

When /^I list the work "([^"]*)" as inspiration$/ do |title|
  work = Work.find_by(title: title)
  check("parent-options-show")
  url_of_work = work_url(work).sub("www.example.com", ArchiveConfig.APP_HOST)
  with_scope("#parent-options") do
    fill_in("URL", with: url_of_work)
  end
end

When /^I list an external work as inspiration$/ do
  check("parent-options-show")
  with_scope("#parent-options") do
    fill_in("URL", with: "https://example.com")
    fill_in("Title", with: "Example External")
    fill_in("Author", with: "External Author")
    select("English", from: "Language")
  end
end

When /^I set the publication date to (\d+) (.*) (\d+)$/ do |day, month, year|
  if page.has_selector?("#backdate-options-show")
    check("backdate-options-show") if page.find("#backdate-options-show")
    select(day.to_s, from: "work[chapter_attributes][published_at(3i)]")
    select(month, from: "work[chapter_attributes][published_at(2i)]")
    select(year.to_s, from: "work[chapter_attributes][published_at(1i)]")
  else
    select(day.to_s, from: "chapter[published_at(3i)]")
    select(month, from: "chapter[published_at(2i)]")
    select(year.to_s, from: "chapter[published_at(1i)]")
  end
end

When /^I set the publication date to today$/ do
  today = Date.current
  month = today.strftime("%B")
  step %{I set the publication date to #{today.day} #{month} #{today.year}}
end

When /^I browse the "(.*?)" works$/ do |tagname|
  tag = Tag.find_by_name(tagname)
  visit tag_works_path(tag)
  step %{all indexing jobs have been run}

  step "the periodic tag count task is run"
end

When /^I browse the "(.*?)" works with page parameter "(.*?)"$/ do |tagname, page|
  tag = Tag.find_by_name(tagname)
  visit tag_works_path(tag, page: page)
  step %{all indexing jobs have been run}

  step "the periodic tag count task is run"
end

When /^I delete the work "([^"]*)"$/ do |work|
  work = Work.find_by(title: CGI.escapeHTML(work))
  visit edit_work_path(work)
  step %{I follow "Delete Work"}
  # If JavaScript is enabled, window.confirm will be used and this button will not appear
  click_button("Yes, Delete Work") unless @javascript
  step %{all indexing jobs have been run}

  step "the periodic tag count task is run"
end
When /^I preview the work$/ do
  click_button("Preview")
  step %{all indexing jobs have been run}

  step "the periodic tag count task is run"
end
When /^I update the work$/ do
  click_button("Update")
  step %{all indexing jobs have been run}

  step "the periodic tag count task is run"
end
When /^I post the work without preview$/ do
  click_button "Post"
  step %{all indexing jobs have been run}

  step "the periodic tag count task is run"
end
When /^I post the work$/ do
  click_button "Post"
  step %{all indexing jobs have been run}
end

When /^the statistics for all works are updated$/ do
  RedisSetJobSpawner.perform_now("StatCounterJob")
  step %{the hit counts for all works are updated}
end

When /^I add the co-author "([^"]*)" to the work "([^"]*)"$/ do |coauthor, work|
  step %{I edit the work "#{work}"}
  step %{I invite the co-author "#{coauthor}"}
  step %{I post the work without preview}
  step %{the user "#{coauthor}" accepts the creator invite for the work "#{work}"}
end

When /^the user "([^"]*)" accepts the creator invite for the work "([^"]*)"/ do |user, work|
  # Make sure that we don't have caching issues with the byline:
  step %{I wait 1 second}
  u = User.find_by(login: user)
  w = Work.find_by(title: work)
  w.creatorships.unapproved.for_user(u).each(&:accept!)
end

When(/^I try to invite the co-authors? "([^"]*)"$/) do |coauthor|
  check("co-authors-options-show")
  fill_in("pseud_byline", with: "#{coauthor}")
end

When /^I invite the co-authors? "([^"]*)"$/ do |coauthor|
  coauthor.split(",").map(&:strip).reject(&:blank?).each do |user|
    step %{the user "#{user}" allows co-creators}
  end
  step %{I try to invite the co-authors "#{coauthor}"}
end

When "I give the work to {string}" do |recipient|
  fill_in("Gift this work to", with: recipient)
end

When /^I give the work "([^"]*)" to the user "([^"]*)"$/ do |work_title, recipient|
  step %{the user "#{recipient}" exists and is activated}
  visit edit_work_path(Work.find_by(title: work_title))
  fill_in("work_recipients", with: "#{recipient}")
  click_button("Post")
end

When /^I add the beginning notes "([^"]*)"$/ do |notes|
  check("at the beginning")
  fill_in("work_notes", with: "#{notes}")
end

When /^I add the end notes "([^"]*)"$/ do |notes|
  check("at the end")
  fill_in("work_endnotes", with: "#{notes}")
end

When /^I add the beginning notes "([^"]*)" to the work "([^"]*)"$/ do |notes, work|
  step %{I edit the work "#{work}"}
  step %{I add the beginning notes "#{notes}"}
  step %{I post the work without preview}
end

When /^I add the end notes "([^"]*)" to the work "([^"]*)"$/ do |notes, work|
  step %{I edit the work "#{work}"}
  step %{I add the end notes "#{notes}"}
  step %{I post the work without preview}
end

When /^I mark the work "([^"]*)" for later$/ do |work|
  work = Work.find_by(title: work)
  visit work_url(work)
  step %{I follow "Mark for Later"}
  step "the readings are saved to the database"
end

When /^I follow the recent chapter link for the work "([^\"]*)"$/ do |work|
  work = Work.find_by_title(work)
  work_id = work.id.to_s
  find("#work_#{work_id} dd.chapters a").click
end

When "I follow the kudos link for the work {string}" do |work|
  work = Work.find_by(title: work)
  find("#work_#{work.id} dd.kudos a").click
end

When "I follow the comments link for the work {string}" do |work|
  work = Work.find_by(title: work)
  find("#work_#{work.id} dd.comments a").click
end

When "the cache for the work {string} is cleared" do |title|
  work = Work.find_by(title: title)
  # Touch the work to actually expire the cache
  work.touch
end

When "the statistics for the work {string} are updated" do |title|
  step %{the statistics for all works are updated}
  step %{all indexing jobs have been run}
  step %{the cache for the work "#{title}" is cleared}
end

When /^the hit counts for all works are updated$/ do
  step "all AJAX requests are complete"
  RedisHitCounter.save_recent_counts
end

When /^all hit count information is reset$/ do
  redis = RedisHitCounter.redis
  redis.keys.each do |key|
    redis.del(key)
  end
end

### THEN
Then /^I should see Updated today$/ do
  today = Date.current.to_s
  step "I should see \"Updated:#{today}\""
end

Then /^I should not see Updated today$/ do
  today = Date.current.to_s
  step "I should not see \"Updated:#{today}\""
end

Then /^I should see Completed today$/ do
  today = Date.current.to_s
  step "I should see \"Completed:#{today}\""
end

Then /^I should not see Completed today$/ do
  today = Date.current.to_s
  step "I should not see \"Completed:#{today}\""
end

Then /^I should find a list for associations$/ do
  page.should have_xpath("//ul[@class=\"associations\"]")
end

Then /^I should not find a list for associations$/ do
  page.should_not have_xpath("//ul[@class=\"associations\"]")
end

Then /^the work "([^"]*)" should be deleted$/ do |work|
  assert !Work.where(title: work).exists?
end

Then /^the Remove Me As Chapter Co-Creator option should be on the ([\d]+)(?:st|nd|rd|th) chapter$/ do |chapter_number|
  step %{I should see "Remove Me As Chapter Co-Creator" within "ul#sortable_chapter_list > li:nth-of-type(#{chapter_number})"}
end

Then /^the Remove Me As Chapter Co-Creator option should not be on the ([\d]+)(?:st|nd|rd|th) chapter$/ do |chapter_number|
  step %{I should not see "Remove Me As Chapter Co-Creator" within "ul#sortable_chapter_list > li:nth-of-type(#{chapter_number})"}
end

Then "I should see {string} within the work blurb of {string}" do |content, work|
  work = Work.find_by(title: work)
  step %{I should see "#{content}" within "li#work_#{work.id}"}
end

Then "I should not see {string} within the work blurb of {string}" do |content, work|
  work = Work.find_by(title: work)
  step %{I should not see "#{content}" within "li#work_#{work.id}"}
end
