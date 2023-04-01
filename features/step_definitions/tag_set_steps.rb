# encoding: utf-8
When /^I follow the add new tag ?set link$/ do
  step %{I follow "New Tag Set"}
end

# This takes strings like:
# ...the fandom tags "x, y, z"
# ...the category tags "a, b, c"
# If you want ratings, warnings, or categories, first load basic or default tags for those types
When /^I add (.*) to the tag ?set$/ do |tags|
  tags.scan(/the (\w+) tags "([^\"]*)"/).each do |type, scanned_tags|
    if type == "category" || type == "rating" || type == "warning"
      tags = scanned_tags.split(/, ?/)
      tags.each { |tag| check(tag) }
    else
      field_name = "owned_tag_set_tag_set_attributes_#{type}_tagnames_to_add"
      field_name += "_autocomplete" if @javascript
      fill_in(field_name, with: scanned_tags)
    end
  end
end

# This takes strings like:
# ...with a visible tag list
# ...with the fandom tags "x, y, z" and the character tags "a, b, c"
# ...with an invisible tag list and the freeform tags "m, n, o"
When /^I set up the tag ?set "([^\"]*)" with(?: (?:an? )(visible|invisible) tag list and)? (.*)$/ do |title, visibility, tags|
  unless OwnedTagSet.find_by(title: title).present?
    visit new_tag_set_path
    fill_in("owned_tag_set_title", with: title)
    fill_in("owned_tag_set_description", with: "Here's my tagset")
    visibility ||= "invisible"
    check("owned_tag_set_visible") if visibility == "visible"
    uncheck("owned_tag_set_visible") if visibility == "invisible"
    step %{I add #{tags} to the tag set}
    step %{I submit}
    step %{I should see a create confirmation message}
  end
end

# Takes things like When I add the fandom tags "Bandom" to the tag set "MoreJoyDay".
# Don't forget the extra s, even if it's singular.
When /^I add (.*) to the tag ?set "([^\"]*)"$/ do |tags, title|
  step %{I go to the "#{title}" tag set edit page}
  step %{I add #{tags} to the tag set}
  step %{I submit}
  step %{I should see an update confirmation message}
end

# Takes things like When I remove the fandom tags "Bandom" to the tag set "MoreJoyDay".
# Don't forget the extra s, even if it's singular.
When /^I remove (.*) from the tag ?set "([^\"]*)"$/ do |tags, title|
  step %{I go to the "#{title}" tag set edit page}
  tags.scan(/the (\w+) tags "([^\"]*)"/).each do |type, scanned_tags|
    tags = scanned_tags.split(/, ?/)

    if type == "category" || type == "rating" || type == "warning"
      tags.each { |tag| uncheck(tag) }
    else
      tags.each { |tag| check(tag) }
    end
  end
  step %{I submit}
  step %{I should see an update confirmation message}
end

When /^I set up the nominated tag ?set "([^\"]*)" with (\d*) fandom noms? and (\d*) (character|relationship) noms?$/ do |title, fandom_count, nested_count, nested_type|
  unless OwnedTagSet.find_by(title: "#{title}").present?
    step %{I go to the new tag set page}
    fill_in("owned_tag_set_title", with: title)
    fill_in("owned_tag_set_description", with: "Here's my tagset")
    check("Currently taking nominations?")
    fill_in("Fandom nomination limit", with: fandom_count)
    fill_in("#{nested_type.titleize} nomination limit", with: nested_count)
    step %{I submit}
    step %{I should see a create confirmation message}
  end
end

When /^I nominate (.*) fandoms and (.*) characters in the "([^\"]*)" tag ?set as "([^\"]*)"/ do |fandom_count, char_count, title, login|
  step %{I am logged in as "#{login}"}
  step %{I go to the "#{title}" tag set page}
  step %{I follow "Nominate"}
  1.upto(fandom_count.to_i) do |i|
    fill_in("Fandom #{i}", with: "Blah #{i}")
    0.upto(char_count.to_i - 1) do |j|
      fill_in("tag_set_nomination_fandom_nominations_attributes_#{i - 1}_character_nominations_attributes_#{j}_tagname", with: "Foobar #{i} #{j}")
    end
  end
end

When /^I have (?:a|the) nominated tag ?set "([^\"]*)"/ do |title|
  step %{I am logged in as "tagsetter"}
  step %{I set up the nominated tag set "#{title}" with 3 fandom noms and 3 character noms}
  step %{I nominate 3 fandoms and 3 characters in the "#{title}" tagset as "nominator"}
  step %{I submit}
  step %{I should see a success message}
end

When /^I start to nominate fandoms? "([^\"]*)" and characters? "([^\"]*)" in "([^\"]*)"(?: as "([^"]*)")?$/ do |fandom, char, title, user|
  user ||= "nominator"
  step %{I am logged in as "#{user}"}
  step %{I go to the "#{title}" tag set page}
  step %{I follow "Nominate"}
  @fandoms = fandom.split(/, ?/)
  @chars = char.split(/, ?/)
  char_index = 0
  chars_per_fandom = @chars.size/@fandoms.size
  1.upto(@fandoms.size) do |i|
    fill_in("Fandom #{i}", with: @fandoms[i - 1])
    0.upto(chars_per_fandom - 1) do |j|
      fill_in("tag_set_nomination_fandom_nominations_attributes_#{i - 1}_character_nominations_attributes_#{j}_tagname", with: @chars[char_index])
      char_index += 1
    end
  end
end

When /^I nominate fandoms? "([^\"]*)" and characters? "([^\"]*)" in "([^\"]*)"(?: as "([^"]*)")?$/ do |fandom, char, title, user|
  user ||= "nominator"
  step %{I start to nominate fandoms "#{fandom}" and characters "#{char}" in "#{title}" as "#{user}"}
  step %{I submit}
  step %{I should see a success message}
end

When /^there are (\d+) unreviewed nominations$/ do |n|
  (1..n.to_i).each do |i|
    step %{I am logged in as \"nominator#{i}\"}
    step %{I nominate 6 fandoms and 6 characters in the "Nominated Tags" tag set as \"nominator#{i}\"}
    step %{I press "Submit"}
  end
end

When /^I review nominations for "([^\"]*)"/ do |title|
  step %{I am logged in as "tagsetter"}
  step %{I go to the "#{title}" tag set page}
  step %{I follow "Review Nominations"}
end

When /^I review associations for "([^\"]*)"/ do |title|
  step %{I am logged in as "tagsetter"}
  step %{I go to the "#{title}" tag set page}
  step %{I follow "Review Associations"}
end

When /^I nominate and approve fandom "([^\"]*)" and character "([^\"]*)" in "([^\"]*)"/ do |fandom, char, title|
  step %{I am logged in as "tagsetter"}
  step %{I set up the nominated tag set "#{title}" with 3 fandom noms and 3 character noms}
  step %{I nominate fandom "#{fandom}" and character "#{char}" in "#{title}"}
  step %{I review nominations for "#{title}"}
  step %{I check "fandom_approve_#{fandom}"}
  step %{I check "character_approve_#{char}"}
  step %{I submit}
  step %{I should see "Successfully added to set: #{fandom}"}
  step %{I should see "Successfully added to set: #{char}"}
end

When /^I nominate and approve tags with Unicode characters in "([^\"]*)"/ do |title|
  tags = "The Hobbit - All Media Types, Dís, Éowyn, Kíli, Bifur/Óin, スマイルプリキュア, 新白雪姫伝説プリーティア".split(', ')
  step %{I am logged in as "tagsetter"}
  step %{I set up the nominated tag set "#{title}" with 7 fandom noms and 0 character noms}
  step %{I am logged in as "nominator"}
  step %{I go to the "#{title}" tag set page}
  step %{I follow "Nominate"}
  tags.each_with_index do |tag, i|
    fill_in("Fandom #{i + 1}", with: tag)
  end
  step %{I submit}
  step %{I should see a success message}
  step %{I review nominations for "#{title}"}
  tags.each do |tag|
    step %{I check "fandom_approve_#{tag}"}
  end
  step %{I submit}
  step %{I should see "Successfully added to set"}
end

When /^I should see the tags with Unicode characters/ do
  tags = "The Hobbit - All Media Types, Dís, Éowyn, Kíli, Bifur/Óin, スマイルプリキュア, 新白雪姫伝説プリーティア".split(', ')
  tags.each do |tag|
    step %{I should see "#{tag}"}
  end
end

When /^I view the tag set "([^\"]*)"/ do |tagset|
  tagset = OwnedTagSet.find_by(title: tagset)
  visit tag_set_path(tagset)
end

When /^I view associations for a tag set that does not exist/ do
  id = 1
  tagset = OwnedTagSet.find_by(id: id)
  tagset.destroy if tagset
  visit tag_set_associations_path(id)
end

When /^I expand the unassociated characters and relationships$/ do
  within('span[action_target="#list_for_unassociated_char_and_rel"]') do
    click_link("↓")
  end
end

Then /^"([^\"]*)" should be associated with the "([^\"]*)" fandom "([^\"]*)"$/ do |tag, fandom_type, fandom_name|
  name = fandom_name.tr(" ", "_")
  type = fandom_type.tr(" ", "_")
  step %{I should see "#{tag}" within "ol#list_for_fandom_#{name}_in_#{type}_Fandoms li"}
end

Then /^"([^\"]*)" should be an unassociated tag$/ do |tag|
  step %{I should see "#{tag}" within "ol#list_for_unassociated_char_and_rel"}
end
