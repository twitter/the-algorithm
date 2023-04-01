Given /^mock websites with no content$/ do
  WebMock.disable_net_connect!
  WebMock.stub_request(:head, "http://example.org/200")
  WebMock.stub_request(:head, "http://example.org/301").to_return(status: 301)
  WebMock.stub_request(:head, "http://example.org/404").to_return(status: 404)
end

Given "all pages on the host {string} return status 200" do |url|
  WebMock.disable_net_connect!
  parsed_url = Addressable::URI.parse(url)
  WebMock.stub_request(:any, %r[https?://#{parsed_url.host}.*]).to_return(status: 200)
end

Given /^I have a bookmark for "([^\"]*)"$/ do |title|
  step %{I start a new bookmark for "#{title}"}
  fill_in("Your tags", with: DEFAULT_BOOKMARK_TAGS)
  step %{I press "Create"}
  step %{all indexing jobs have been run}
end

Given /^I have a bookmark of a deleted work$/ do
  title = "Deleted Work For Bookmarking"
  step %{I start a new bookmark for "#{title}"}
  fill_in("bookmark_tag_string", with: DEFAULT_BOOKMARK_TAGS)
  step %{I press "Create"}
  work = Work.find_by(title: title)
  work.destroy
  step %{all indexing jobs have been run}
end

Given /^I have bookmarks to search$/ do
  # set up a user
  user1 = FactoryBot.create(:user, login: "testuser")

  # set up the pseuds
  pseud1 = FactoryBot.create(:pseud, name: "testy", user_id: user1.id)
  pseud2 = FactoryBot.create(:pseud, name: "tester_pseud", user_id: user1.id)

  # set up a tag
  freeform1 = FactoryBot.create(:freeform, name: "classic")
  freeform2 = FactoryBot.create(:freeform, name: "rare")

  # set up some works
  work1 = FactoryBot.create(:work, title: "First work", freeform_string: freeform2.name)
  work2 = FactoryBot.create(:work, title: "second work")
  work3 = FactoryBot.create(:work, title: "third work")
  work4 = FactoryBot.create(:work, title: "fourth")
  work5 = FactoryBot.create(:work, title: "fifth")

  # set up an external work
  external1 = FactoryBot.create(:external_work, title: "Skies Grown Darker")

  # set up some series
  series1 = FactoryBot.create(:series, title: "First Series")
  series2 = FactoryBot.create(:series_with_a_work, title: "Second Series")

  # add work1 to series1 to ensure the series has tags
  FactoryBot.create(:serial_work, work_id: work1.id, series_id: series1.id)

  # set up the bookmarks
  FactoryBot.create(:bookmark,
                     bookmarkable_id: work1.id,
                     pseud_id: user1.default_pseud.id,
                     rec: true)

  FactoryBot.create(:bookmark,
                     bookmarkable_id: work2.id,
                     pseud_id: user1.default_pseud.id,
                     tag_string: freeform2.name)

  FactoryBot.create(:bookmark,
                     bookmarkable_id: work3.id,
                     pseud_id: user1.default_pseud.id,
                     tag_string: freeform1.name)

  FactoryBot.create(:bookmark, bookmarkable_id: work4.id, pseud_id: pseud1.id)

  FactoryBot.create(:bookmark,
                     bookmarkable_id: work5.id,
                     pseud_id: pseud2.id,
                     bookmarker_notes: "Left me with a broken heart")

  FactoryBot.create(:bookmark,
                     bookmarkable_id: external1.id,
                     bookmarkable_type: "ExternalWork",
                     pseud_id: pseud2.id,
                     bookmarker_notes: "I enjoyed this")

  FactoryBot.create(:bookmark,
                     bookmarkable_id: series1.id,
                     bookmarkable_type: "Series",
                     pseud_id: user1.default_pseud.id,
                     tag_string: freeform1.name)

  FactoryBot.create(:bookmark,
                     bookmarkable_id: series2.id,
                     bookmarkable_type: "Series",
                     pseud_id: pseud2.id,
                     rec: true,
                     bookmarker_notes: "A new classic")

  step %{all indexing jobs have been run}
end

Given /^I have bookmarks to search by any field$/ do
  work1 = FactoryBot.create(:work,
                             title: "Comfort",
                             freeform_string: "hurt a little comfort but only so much")
  work2 = FactoryBot.create(:work, title: "Hurt and that's it")
  work3 = FactoryBot.create(:work, title: "Fluff")

  external1 = FactoryBot.create(:external_work,
                                 title: "External Whump",
                                 author: "im hurt")
  external2 = FactoryBot.create(:external_work, title: "External Fix-It")

  series1 = FactoryBot.create(:series_with_a_work,
                              title: "H/C Series",
                              summary: "Hurt & comfort ficlets")
  series2 = FactoryBot.create(:series_with_a_work, title: "Ouchless Series")

  FactoryBot.create(:bookmark, bookmarkable_id: work1.id, bookmarker_notes: "whatever")
  FactoryBot.create(:bookmark, bookmarkable_id: work2.id, tag_string: "more please")
  FactoryBot.create(:bookmark, bookmarkable_id: work3.id, bookmarker_notes: "more please")
  FactoryBot.create(:bookmark,
                     bookmarkable_id: external1.id,
                     bookmarkable_type: "ExternalWork",
                     bookmarker_notes: "please rec me more like this")
  FactoryBot.create(:bookmark,
                     bookmarkable_id: external2.id,
                     bookmarkable_type: "ExternalWork",
                     tag_string: "please no more pain")
  FactoryBot.create(:bookmark,
                     bookmarkable_id: series1.id,
                     bookmarkable_type: "Series",
                     bookmarker_notes: "needs more comfort please")
  FactoryBot.create(:bookmark,
                     bookmarkable_id: series2.id,
                     bookmarkable_type: "Series",
                     pseud_id: FactoryBot.create(:pseud, name: "more please").id)

  step %{all indexing jobs have been run}
end

Given /^I have bookmarks to search by dates$/ do
  work1 = nil
  series1 = nil
  external1 = nil
  Timecop.freeze(901.days.ago) do
    work1 = FactoryBot.create(:work, title: "Old work")
    FactoryBot.create(:bookmark,
                       bookmarkable_id: work1.id,
                       bookmarker_notes: "Old bookmark of old work")

    series1 = FactoryBot.create(:series_with_a_work, title: "Old series")
    FactoryBot.create(:bookmark,
                       bookmarkable_id: series1.id,
                       bookmarkable_type: "Series",
                       bookmarker_notes: "Old bookmark of old series")

    external1 = FactoryBot.create(:external_work, title: "Old external")
    FactoryBot.create(:bookmark,
                       bookmarkable_id: external1.id,
                       bookmarkable_type: "ExternalWork",
                       bookmarker_notes: "Old bookmark of old external work")
  end
  FactoryBot.create(:bookmark,
                     bookmarkable_id: work1.id,
                     bookmarker_notes: "New bookmark of old work")
  FactoryBot.create(:bookmark,
                     bookmarkable_id: series1.id,
                     bookmarkable_type: "Series",
                     bookmarker_notes: "New bookmark of old series")
  FactoryBot.create(:bookmark,
                     bookmarkable_id: external1.id,
                     bookmarkable_type: "ExternalWork",
                     bookmarker_notes: "New bookmark of old external work")

  work2 = FactoryBot.create(:work, title: "New work")
  FactoryBot.create(:bookmark,
                     bookmarkable_id: work2.id,
                     bookmarker_notes: "New bookmark of new work")

  series2 = FactoryBot.create(:series_with_a_work, title: "New series")
  FactoryBot.create(:bookmark,
                     bookmarkable_id: series2.id,
                     bookmarkable_type: "Series",
                     bookmarker_notes: "New bookmark of new series")

  external2 = FactoryBot.create(:external_work, title: "New external")
  FactoryBot.create(:bookmark,
                     bookmarkable_id: external2.id,
                     bookmarkable_type: "ExternalWork",
                     bookmarker_notes: "New bookmark of new external work")

  step %{all indexing jobs have been run}
end

Given /^I have bookmarks of various completion statuses to search$/ do
  complete_work = FactoryBot.create(:work, title: "Finished Work")
  incomplete_work = FactoryBot.create(:work, title: "Incomplete Work", complete: false, expected_number_of_chapters: 2)

  complete_series = FactoryBot.create(:series_with_a_work, title: "Complete Series", complete: true)
  incomplete_series = FactoryBot.create(:series_with_a_work, title: "Incomplete Series", complete: false)

  external_work = FactoryBot.create(:external_work, title: "External Work")

  FactoryBot.create(:bookmark, bookmarkable_id: complete_work.id)
  FactoryBot.create(:bookmark, bookmarkable_id: incomplete_work.id)
  FactoryBot.create(:bookmark, bookmarkable_id: complete_series.id, bookmarkable_type: "Series")
  FactoryBot.create(:bookmark, bookmarkable_id: incomplete_series.id, bookmarkable_type: "Series")
  FactoryBot.create(:bookmark, bookmarkable_id: external_work.id, bookmarkable_type: "ExternalWork")

  step %{all indexing jobs have been run}
end

Given /^I have bookmarks of old series to search$/ do
  step %{basic tags}
  step %{the user "creator" exists and is activated}
  creator = User.find_by(login: "creator").default_pseud

  Timecop.freeze(30.days.ago) do
    older_work = FactoryBot.create(:work, title: "WIP in a Series", authors: [creator])
    older_series = FactoryBot.create(:series, title: "Older WIP Series", works: [older_work])
    FactoryBot.create(:bookmark,
                       bookmarkable_id: older_series.id,
                       bookmarkable_type: "Series")
  end

  Timecop.freeze(7.days.ago) do
    newer_series = FactoryBot.create(:series_with_a_work, title: "Newer Complete Series")
    FactoryBot.create(:bookmark,
                       bookmarkable_id: newer_series.id,
                       bookmarkable_type: "Series")
  end
end

# Freeform is omitted because there is no freeform option on the bookmark external work form
Given /^bookmarks of all types tagged with the (character|relationship|fandom) tag "(.*?)"$/ do |tag_type, tag|
  work = if tag_type == "character"
           FactoryBot.create(:work,
                              title: "BookmarkedWork",
                              character_string: tag)
         elsif tag_type == "relationship"
           FactoryBot.create(:work,
                              title: "BoomarkedWork",
                              relationship_string: tag)
         elsif tag_type == "fandom"
           FactoryBot.create(:work,
                              title: "BookmarkedWork",
                              fandom_string: tag)
         end

  FactoryBot.create(:bookmark, bookmarkable_id: work.id, bookmarkable_type: "Work")

  step %{bookmarks of external works and series tagged with the #{tag_type} tag "#{tag}"}
end

# Freeform is omitted because there is no freeform option on the bookmark external work form
Given /^bookmarks of external works and series tagged with the (character|relationship|fandom) tag "(.*?)"$/ do |tag_type, tag|
  # Series get their tags from works, so we have to create the work first
  work = if tag_type == "character"
           FactoryBot.create(:work, character_string: tag)
         elsif tag_type == "relationship"
           FactoryBot.create(:work, relationship_string: tag)
         elsif tag_type == "fandom"
           FactoryBot.create(:work, fandom_string: tag)
         end

  # We're going to need to use the series ID, so make the series
  series = FactoryBot.create(:series, title: "BookmarkedSeries")

  # Now add the work to the series
  FactoryBot.create(:serial_work, work_id: work.id, series_id: series.id)

  external_work = if tag_type == "character"
                    FactoryBot.create(:external_work, title: "BookmarkedExternalWork", character_string: tag)
                  elsif tag_type == "relationship"
                    FactoryBot.create(:external_work, title: "BookmarkedExternalWork", relationship_string: tag)
                  elsif tag_type == "fandom"
                    FactoryBot.create(:external_work, title: "BookmarkedExternalWork", fandom_string: tag)
                  end

  FactoryBot.create(:bookmark,
                     bookmarkable_id: series.id,
                     bookmarkable_type: "Series")

  FactoryBot.create(:bookmark,
                     bookmarkable_id: external_work.id,
                     bookmarkable_type: "ExternalWork")

  step %{all indexing jobs have been run}
end

Given /^"(.*?)" has bookmarks of works in various languages$/ do |user|
  step %{I have loaded the "languages" fixture}

  step %{the user "#{user}" exists and is activated}
  user_pseud = User.find_by(login: user).default_pseud

  lang_en = Language.find_by(name: "English")
  lang_de = Language.find_by(name: "Deutsch")

  work1 = FactoryBot.create(:work, title: "english work", language_id: lang_en.id)
  work2 = FactoryBot.create(:work, title: "german work", language_id: lang_de.id)

  FactoryBot.create(:bookmark, bookmarkable_id: work1.id, pseud_id: user_pseud.id)
  FactoryBot.create(:bookmark, bookmarkable_id: work2.id, pseud_id: user_pseud.id)

  step %{all indexing jobs have been run}
end

def submit_bookmark_form(pseud, note, tags)
  select(pseud, from: "bookmark_pseud_id") unless pseud.nil?
  fill_in("bookmark_notes", with: note) unless note.nil?
  fill_in("Your tags", with: tags) unless tags.nil?
  click_button("Create")
  step %{all indexing jobs have been run}
end

When /^I bookmark the work "(.*?)"(?: as "(.*?)")?(?: with the note "(.*?)")?(?: with the tags "(.*?)")?$/ do |title, pseud, note, tags|
  step %{I start a new bookmark for "#{title}"}
  submit_bookmark_form(pseud, note, tags)
end

When /^I bookmark the work "(.*?)"(?: as "(.*?)")?(?: with the note "(.*?)")?(?: with the tags "(.*?)")? from new bookmark page$/ do |title, pseud, note, tags|
  step %{I go to the new bookmark page for work "#{title}"}
  submit_bookmark_form(pseud, note, tags)
end

When /^I bookmark the series "([^\"]*)"$/ do |series_title|
  series = Series.find_by(title: series_title)
  visit series_path(series)
  click_link("Bookmark Series")
  click_button("Create")
  step %{all indexing jobs have been run}
end

When /^I start a new bookmark for "([^\"]*)"$/ do |title|
  step %{I open the bookmarkable work "#{title}"}
  click_link("Bookmark")
end

When /^I start a new bookmark$/ do
  step %{I start a new bookmark for "#{DEFAULT_TITLE}"}
end

When /^I bookmark the works "([^\"]*)"$/ do |worklist|
  worklist.split(/, ?/).each do |work_title|
    step %{I bookmark the work "#{work_title}"}
  end
end

When /^I edit the bookmark for "([^\"]*)"$/ do |title|
  step %{I open the bookmarkable work "#{title}"}
  click_link("Edit Bookmark")
end

When /^I open a bookmarkable work$/ do
  step %{I open the bookmarkable work "#{DEFAULT_TITLE}"}
end

When /^I open the bookmarkable work "([^\"]*)"$/ do |title|
  work = Work.find_by(title: title)
  if !work
    step %{I post the work "#{title}"}
    work = Work.find_by(title: title)
  end
  visit work_path(work)
end

When /^I add my bookmark to the collection "([^\"]*)"$/ do |collection_name|
  step %{I follow "Add To Collection"}
  fill_in("collection_names", with: collection_name)
  click_button("Add")
end

When /^I rec the current work$/ do
  click_link("Bookmark")
  check("bookmark_rec")
  click_button("Create")
  step %{all indexing jobs have been run}
end

When(/^I attempt to create a bookmark of "([^"]*)" with a pseud that is not mine$/) do |work|
  step %{I am logged in as "commenter"}
  step %{I start a new bookmark for "#{work}"}
  pseud_id = User.first.pseuds.first.id
  find("#bookmark_pseud_id", visible: false).set(pseud_id)
  click_button "Create"
end

When(/^I attempt to transfer my bookmark of "([^"]*)" to a pseud that is not mine$/) do |work|
  step %{the user "not_the_bookmarker" exists and is activated}
  step %{I edit the bookmark for "#{work}"}
  pseud_id = User.find_by(login: "not_the_bookmarker").pseuds.first.id
  find("#bookmark_pseud_id", visible: false).set(pseud_id)
  click_button "Update"
end

When(/^I use the bookmarklet on a previously bookmarked URL$/) do
  url = ExternalWork.first.url
  visit new_external_work_path(params: { url_from_external: url })
  step %{all AJAX requests are complete}
end

Then /^the bookmark on "([^\"]*)" should have tag "([^\"]*)"$$/ do |title, tag|
  work = Work.find_by(title: title)
  bookmark = work.bookmarks.first
  bookmark.reload
  bookmark.tags.collect(&:name).include?(tag)
end
Then /^the ([\d]+)(?:st|nd|rd|th) bookmark result should contain "([^"]*)"$/ do |n, text|
  selector = "ol.bookmark > li:nth-of-type(#{n})"
  with_scope(selector) do
    page.should have_content(text)
  end
end

Then /^the cache of the bookmark on "([^\"]*)" should expire after I edit the bookmark tags$/ do |title|
  work = Work.find_by(title: title)
  bookmark = work.bookmarks.first
  orig_cache_key = bookmark.cache_key
  Kernel::sleep 1
  visit edit_bookmark_path(bookmark)
  fill_in("bookmark_tag_string", with: "New Tag")
  click_button("Update")
  bookmark.reload
  assert orig_cache_key != bookmark.cache_key, "Cache key #{orig_cache_key} matches #{bookmark.cache_key}."
end

Then /^the cache of the bookmark on "([^\"]*)" should not expire if I have not edited the bookmark$/ do |title|
  work = Work.find_by(title: title)
  bookmark = work.bookmarks.first
  orig_cache_key = bookmark.cache_key
  Kernel::sleep 1
  visit edit_bookmark_path(bookmark)
  visit bookmark_path(bookmark)
  bookmark.reload
  assert orig_cache_key == bookmark.cache_key, "Cache key #{orig_cache_key} does not match #{bookmark.cache_key}."
end
