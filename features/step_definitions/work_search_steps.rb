### GIVEN

Given /^a set of alternate universe works for searching$/ do
  step %{basic tags}

  # Create a metatag with a syn
  step %{a canonical freeform "Alternate Universe"}
  step %{a synonym "AU" of the tag "Alternate Universe"}

  # Create a subtag with a syn
  step %{a canonical freeform "Alternate Universe - High School"}
  step %{a synonym "High School AU" of the tag "Alternate Universe - High School"}

  # Create another subtag
  step %{a canonical freeform "Alternate Universe - Coffee Shops & Cafés"}

  # Set up the tree
  step %{"Alternate Universe" is a metatag of the freeform "Alternate Universe - High School"}
  step %{"Alternate Universe" is a metatag of the freeform "Alternate Universe - Coffee Shops & Cafés"}

  # Create a work with every tag except Alternate Universe - High School, and a
  # work with the unwrangled tag Coffee Shop AU
  ["Alternate Universe",
   "AU",
   "High School AU",
   "Alternate Universe - Coffee Shops & Cafés",
   "Coffee Shop AU"].each do |freeform|
    FactoryBot.create(:work, freeform_string: freeform)
  end

  # Create a work with a summary that is a text match for both the unwrangled
  # tag (Coffee Shop AU) and the metatag's syn (AU)
  FactoryBot.create(:work, summary: "A humble Coffee Shop AU")

  # Create a work with a character tag that is a text match for the metatag's
  # syn (AU)
  FactoryBot.create(:work, character_string: "AU Character")

  step %{all indexing jobs have been run}
end

Given /^a set of Steve Rogers works for searching$/ do
  step %{basic tags}

  # Create two fandoms
  step %{a canonical fandom "Marvel Cinematic Universe"}
  step %{a canonical fandom "The Avengers (Marvel Movies)"}

  # Create a character with a syn
  step %{a canonical character "Steve Rogers"}
  step %{a synonym "Captain America" of the tag "Steve Rogers"}

  # Create a meta tag for that character
  step %{a canonical character "Steve"}
  step %{"Steve" is a metatag of the character "Steve Rogers"}

  # Create a work for each character tag in each fandom
  ["Marvel Cinematic Universe", "The Avengers (Marvel Movies)"].each do |fandom|
    ["Steve Rogers", "Captain America"].each do |character|
      FactoryBot.create(:work,
                         fandom_string: fandom,
                         character_string: character)
    end
  end

  # Create a work without Steve as a character but with him in a relationship
  FactoryBot.create(:work,
                     relationship_string: "Steve Rogers/Tony Stark")

  # Create a work that only mentions Steve in the summary
  FactoryBot.create(:work,
                     summary: "Bucky thinks about his pal Steve Rogers.")

  step %{all indexing jobs have been run}
end

Given /^a set of Kirk\/Spock works for searching$/ do
  step %{basic tags}

  # Create a relationship with two syns
  step %{a canonical relationship "James T. Kirk/Spock"}
  step %{a synonym "K/S" of the tag "James T. Kirk/Spock"}
  step %{a synonym "Spirk" of the tag "James T. Kirk/Spock"}

  # Create a work for each tag
  ["James T. Kirk/Spock", "K/S", "Spirk"].each do |relationship|
    FactoryBot.create(:work, relationship_string: relationship)
  end

  # Create a F/M work using one of the synonyms
  FactoryBot.create(:work,
                     title: "The Genderswap K/S Work That Uses a Synonym",
                     relationship_string: "Spirk",
                     category_string: "F/M")

  step %{all indexing jobs have been run}
end

Given /^a set of Spock\/Uhura works for searching$/ do
  step %{basic tags}

  # Create a canonical two-character relationship with a syn
  step %{a canonical relationship "Spock/Nyota Uhura"}
  step %{a synonym "Uhura/Spock" of the tag "Spock/Nyota Uhura"}

  # Create a threesome with a name that is a partial match for the relationship
  step %{a canonical relationship "James T. Kirk/Spock/Nyota Uhura"}

  # Create a work for each tag
  ["Spock/Nyota Uhura",
   "Uhura/Spock",
   "James T. Kirk/Spock/Nyota Uhura"].each do |relationship|
    FactoryBot.create(:work,
                       relationship_string: relationship)
  end

  step %{all indexing jobs have been run}
end

Given /^a set of works with various categories for searching$/ do
  step %{basic tags}

  # Create one work with each category
  %w(Gen Other F/F Multi F/M M/M).each do |category|
    FactoryBot.create(:work, category_string: category)
  end

  # Create one work using multiple categories
  FactoryBot.create(:work, category_string: "M/M, F/F")

  step %{all indexing jobs have been run}
end

Given /^a set of works with comments for searching$/ do
  step %{basic tags}

  counts = {
    "Work 1" => 0,
    "Work 2" => 1,
    "Work 3" => 1,
    "Work 4" => 1,
    "Work 5" => 3,
    "Work 6" => 3,
    "Work 7" => 10
  }

  counts.each_pair do |title, comment_count|
    work = FactoryBot.create(:work, title: title)
    FactoryBot.create_list(:comment, comment_count, :by_guest,
                           commentable: work.last_posted_chapter)
  end

  step %{the statistics for all works are updated}
  step %{all indexing jobs have been run}
end

Given /^a set of Star Trek works for searching$/ do
  step %{basic tags}

  # Create three related canonical fandoms
  step %{a canonical fandom "Star Trek"}
  step %{a canonical fandom "Star Trek: The Original Series"}
  step %{a canonical fandom "Star Trek: The Original Series (Movies)"}

  # Create a syn for one of the fandoms
  step %{a synonym "ST: TOS" of the tag "Star Trek: The Original Series"}

  # Create an unrelated fourth fandom we'll use for a crossover
  step %{a canonical fandom "Battlestar Galactica (2003)"}

  # Set up the tree for the related fandoms
  step %{"Star Trek" is a metatag of the fandom "Star Trek: The Original Series"}
  step %{"Star Trek: The Original Series" is a metatag of the fandom "Star Trek: The Original Series (Movies)"}

  # Create a work using each of the related fandoms
  ["Star Trek", "Star Trek: The Original Series",
   "Star Trek: The Original Series (Movies)", "ST: TOS"].each do |fandom|
    FactoryBot.create(:work, fandom_string: fandom)
  end

  # Create a work with two fandoms (e.g. a crossover)
  FactoryBot.create(:work,
                     fandom_string: "ST: TOS,
                                    Battlestar Galactica (2003)")

  # Create a work with an additional tag (freeform) that references the fandom
  FactoryBot.create(:work,
                     fandom_string: "Battlestar Galactica (2003)",
                     freeform_string: "Star Trek Fusion")

  step %{all indexing jobs have been run}
end

Given /^a set of works with bookmarks for searching$/ do
  step %{basic tags}

  counts = {
    "Work 1" => 0,
    "Work 2" => 1,
    "Work 3" => 1,
    "Work 4" => 2,
    "Work 5" => 2,
    "Work 6" => 4,
    "Work 7" => 10
  }

  counts.each_pair do |title, bookmark_count|
    work = FactoryBot.create(:work, title: title)
    FactoryBot.create_list(:bookmark, bookmark_count, bookmarkable: work)
  end

  step %{the statistics for all works are updated}
  step %{all indexing jobs have been run}
end

Given /^a set of works with various ratings for searching$/ do
  step %{basic tags}

  ratings = [ArchiveConfig.RATING_DEFAULT_TAG_NAME,
             ArchiveConfig.RATING_GENERAL_TAG_NAME,
             ArchiveConfig.RATING_TEEN_TAG_NAME,
             ArchiveConfig.RATING_MATURE_TAG_NAME,
             ArchiveConfig.RATING_EXPLICIT_TAG_NAME]

  ratings.each do |rating|
    FactoryBot.create(:work, rating_string: rating)
  end

  FactoryBot.create(:work,
                     rating_string: ArchiveConfig.RATING_DEFAULT_TAG_NAME,
                     summary: "Nothing explicit here.")

  step %{all indexing jobs have been run}
end

Given /^a set of works with various warnings for searching$/ do
  step %{basic tags}
  step %{all warnings exist}

  warnings = [ArchiveConfig.WARNING_DEFAULT_TAG_NAME,
              ArchiveConfig.WARNING_NONE_TAG_NAME,
              ArchiveConfig.WARNING_VIOLENCE_TAG_NAME,
              ArchiveConfig.WARNING_DEATH_TAG_NAME,
              ArchiveConfig.WARNING_NONCON_TAG_NAME,
              ArchiveConfig.WARNING_CHAN_TAG_NAME]

  # Create a work for each warning
  warnings.each do |warning|
    FactoryBot.create(:work, archive_warning_string: warning)
  end

  # Create a work that uses multiple warnings
  FactoryBot.create(:work,
                     archive_warning_string: "#{ArchiveConfig.WARNING_DEFAULT_TAG_NAME},
                                     #{ArchiveConfig.WARNING_NONE_TAG_NAME}")

  step %{all indexing jobs have been run}
end

Given /^a set of works with various access levels for searching$/ do
  # Create a draft
  FactoryBot.create(:draft, title: "Draft Work")

  # Create a work
  FactoryBot.create(:work, title: "Posted Work")

  # Create a work restricted to registered users
  FactoryBot.create(:work, restricted: true, title: "Restricted Work")

  # Create a work hidden by an admin
  FactoryBot.create(:work,
                     hidden_by_admin: true,
                     title: "Work Hidden by Admin")

  step %{all indexing jobs have been run}
end

### WHEN

When /^I search for a simple term from the search box$/ do
  step %{I am on the homepage}
      step %{I fill in "site_search" with "first"}
      step %{I press "Search"}
end

When /^I search for works containing "([^"]*)"$/ do |term|
  step %{I am on the homepage}
      step %{I fill in "site_search" with "#{term}"}
      step %{I press "Search"}
end

When /^I search for works by "([^"]*)"$/ do |creator|
  step %{I am on the homepage}
  step %{I fill in "site_search" with "creator: #{creator}"}
  step %{I press "Search"}
end

When /^I search for works without the "([^"]*)"(?: and "([^"]*)")? filter_ids?$/ do |tag1, tag2|
  filter_id1 = Tag.find_by_name(tag1).filter_taggings.first.filter_id
  filter_id2 = Tag.find_by_name(tag2).filter_taggings.first.filter_id if tag2
  step %{I am on the homepage}
  if tag2
    fill_in("site_search", with: "-filter_ids: #{filter_id1} -filter_ids: #{filter_id2}")
  else
    fill_in("site_search", with: "-filter_ids: #{filter_id1}")
  end
  step %{I press "Search"}
end

When /^I exclude the tags? "([^"]*)"(?: and "([^"]*)")? by filter_id$/ do |tag1, tag2|
  filter_id1 = Tag.find_by_name(tag1).filter_taggings.first.filter_id
  filter_id2 = Tag.find_by_name(tag2).filter_taggings.first.filter_id if tag2
  if tag2
    fill_in('work_search_query', with: "-filter_ids: #{filter_id1} -filter_ids: #{filter_id2}")
  else
    fill_in('work_search_query', with: "-filter_ids: #{filter_id1}")
  end
end

### THEN

Then /^the results should contain the ([^"]*) tag "([^"]*)"$/ do |type, tag|
  selector = if type == "fandom"
               "ol.work .fandoms"
             elsif %w(rating category).include?(type)
               "ol.work .required-tags .#{type}"
             else
               "ol.work .tags .#{type.pluralize}"
             end
  expect(page).to have_css(selector, text: tag)
end

Then /^the results should not contain the ([^"]*) tag "([^"]*)"$/ do |type, tag|
  selector = if type == "fandom"
               "ol.work .fandoms"
             elsif %w(rating category).include?(type)
               "ol.work .required-tags .#{type}"
             else
               "ol.work .tags .#{type.pluralize}"
             end
  expect(page).not_to have_css(selector, text: tag)
end

Then /^the results should contain (?:a|the) synonyms? of "([^"]*)"$/ do |tag|
  tag = Tag.find_by_name(tag)
  type = tag.type.downcase.pluralize
  synonyms = tag.synonyms.map(&:name)
  selector = if type == "fandoms"
               "ol.work .fandoms"
             else
               "ol.work .tags .#{type}"
             end
  synonyms.each do |synonym|
    expect(page).to have_css(selector, text: synonym)
  end
end

Then /^the results should contain (?:a|the) subtags? of "([^"]*)"$/ do |tag|
  tag = Tag.find_by_name(tag)
  type = tag.type.downcase.pluralize
  subtags = tag.sub_tags.map(&:name)
  selector = if type == "fandoms"
               "ol.work .fandoms"
             else
               "ol.work .tags .#{type}"
             end
  subtags.each do |subtag|
    expect(page).to have_css(selector, text: subtag)
  end
end

Then /^the results should contain a ([^"]*) mentioning "([^"]*)"$/ do |item, term|
  selector = if item == "fandom"
               "ol.work .fandoms"
             elsif item == "summary"
               "ol.work .summary"
             else
               "ol.work .tags .#{item.pluralize}"
             end
  expect(page).to have_css(selector, text: term)
end

Then /^the results should not contain a ([^"]*) mentioning "([^"]*)"$/ do |item, term|
  selector = if item == "fandom"
               "ol.work .fandoms"
             elsif item == "summary"
               "ol.work .summary"
             else
               "ol.work .tags .#{item.pluralize}"
             end
  expect(page).not_to have_css(selector, text: term)
end

Then /^the ([\d]+)(?:st|nd|rd|th) result should contain "([^"]*)"$/ do |n, text|
  selector = "ol.work > li:nth-of-type(#{n})"
  with_scope(selector) do
    page.should have_content(text)
  end
end

# If JavaScript is enabled and we want to check that information is retained
# when editing a search, we can't look at what is in the input -- we have to
# look at the contents of the ul that contains both the field and the added tags
Then /^"([^"]*)" should already be entered in the work search ([^"]*) autocomplete field$/ do |tag, field|
  within(:xpath, "//input[@id=\"work_search_#{field.singularize}_names_autocomplete\"]/parent::li/parent::ul") do
    page.should have_content(tag)
  end
end

Then /^the search summary should include the filter_id for "([^"]*)"$/ do |tag|
  filter_id = Tag.find_by_name(tag).filter_taggings.first.filter_id
  step %{I should see "filter_ids: #{filter_id}" within "#main h4.heading"}
end

Then /^the results should contain only the restricted work$/ do
  step %{I should see "Restricted Work"}
  step %{I should not see "Posted Work"}
  step %{I should not see "Work Hidden by Admin"}
  step %{I should not see "Draft Work"}
end
