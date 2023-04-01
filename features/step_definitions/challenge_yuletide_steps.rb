
When /^"([^"]*)" posts the fulfilling draft "([^"]*)" in "([^"]*)"$/ do |name, title, fandom|
  step %{I am logged in as "#{name}"}
  step %{I go to #{name}'s user page}
  step %{I follow "Assignments"}
  step %{I follow "Fulfill"}
  step %{I fill in "Work Title" with "#{title}"}
  step %{I fill in "Fandoms" with "#{fandom}"}
  step %{I select "Not Rated" from "Rating"}
  step %{I check "No Archive Warnings Apply"}
  step %{I select "English" from "Choose a language"}
  step %{I fill in "content" with "This is an exciting story about #{fandom}"}
  step %{I press "Preview"}
end

When /^"([^"]*)" posts the fulfilling story "([^"]*)" in "([^"]*)"$/ do |name, title, fandom|
  step %{"#{name}" posts the fulfilling draft "#{title}" in "#{fandom}"}
  step %{I press "Post"}
  step %{I should see "Work was successfully posted"}
  step %{I should see "For myname"}
  step %{I should see "Collections:"}
  step %{I should see "Yuletide" within ".meta"}
  step %{I should see "Anonymous"}
end