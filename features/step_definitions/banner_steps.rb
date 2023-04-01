# encoding: UTF-8

### GIVEN

Given /^there are no banners$/ do
  AdminBanner.delete_all
end

### WHEN

When /^an admin creates an?( active)?(?: "([^\"]*)")? banner$/ do |active, banner_type|
  step %{I am logged in as a "communications" admin}
  visit(new_admin_banner_path)
  fill_in("admin_banner_content", with: "This is some banner text")
  if banner_type.present?
    if banner_type == "alert"
      choose("admin_banner_banner_type_alert")
    elsif banner_type == "event"
      choose("admin_banner_banner_type_event")
    else
      choose("admin_banner_banner_type_")
    end
  end
  check("admin_banner_active") unless active.blank?
  click_button("Create Banner")
  step %{I should see "Setting banner back on for all users. This may take some time."} unless active.blank?
end

When /^an admin deactivates the banner$/ do
  step %{I am logged in as a "communications" admin}
  visit(admin_banners_path)
  step %{I follow "Edit"}
  uncheck("admin_banner_active")
  click_button("Update Banner")
  step %{I should see "Banner successfully updated."}
end

When /^an admin edits the active banner$/ do
  step %{I am logged in as a "communications" admin}
  visit(admin_banners_path)
  step %{I follow "Edit"}
  fill_in("admin_banner_content", with: "This is some edited banner text")
  click_button("Update Banner")
  step %{I should see "Setting banner back on for all users. This may take some time."}
end

When /^an admin makes a minor edit to the active banner$/ do
  step %{I am logged in as a "communications" admin}
  visit(admin_banners_path)
  step %{I follow "Edit"}
  fill_in("admin_banner_content", with: "This is some banner text!")
  check("admin_banner_minor_edit")
  click_button("Update Banner")
  step %{I should see "Updating banner for users who have not already dismissed it. This may take some time."}
end

When /^an admin creates a different active banner$/ do
  step %{I am logged in as a "communications" admin}
  visit(new_admin_banner_path)
  fill_in("admin_banner_content", with: "This is new banner text")
  check("admin_banner_active")
  click_button("Create Banner")
  step %{I should see "Setting banner back on for all users. This may take some time."}
end

When /^I turn off the banner$/ do
  step %{I am logged in as "newname"}
  step %{I am on my user page}
  click_button("×")
end

### THEN

Then /^a logged-in user should see the(?: "([^\"]*)")? banner$/ do |banner_type|
  step %{I am logged in as "ordinaryuser"}
  visit(works_path)
  if banner_type.present?
    if banner_type == "alert"
      page.should have_xpath("//div[@class=\"alert announcement group\"]")
    elsif banner_type == "event"
      page.should have_xpath("//div[@class=\"event announcement group\"]")
    else
      page.should have_xpath("//div[@class=\"announcement group\"]")
      page.should_not have_xpath("//div[@class=\"alert announcement group\"]")
      page.should_not have_xpath("//div[@class=\"event\"]")
    end
  end
  step %{I should see "This is some banner text"}
end

Then /^a logged-out user should see the(?: "([^\"]*)")? banner$/ do |banner_type|
  step "I am a visitor"
  visit(works_path)
  if banner_type.present?
    if banner_type == "alert"
      page.should have_xpath("//div[@class=\"alert announcement group\"]")
    elsif banner_type == "event"
      page.should have_xpath("//div[@class=\"event announcement group\"]")
    else
      page.should have_xpath("//div[@class=\"announcement group\"]")
      page.should_not have_xpath("//div[@class=\"alert announcement group\"]")
      page.should_not have_xpath("//div[@class=\"event announcement group\"]")
    end
  end
  step %{I should see "This is some banner text"}
end

Then /^a logged-in user should see the edited active banner$/ do
  step %{I am logged in as "ordinaryuser"}
  visit(works_path)
  step %{I should see "This is some edited banner text"}
end

Then /^a logged-out user should see the edited active banner$/ do
  step "I am a visitor"
  visit(works_path)
  step %{I should see "This is some edited banner text"}
end

Then /^a logged-in user should not see a banner$/ do
  step %{I am logged in as "ordinaryuser"}
  page.should_not have_xpath("//div[@class=\"announcement group\"]")
end

Then /^a logged-out user should not see a banner$/ do
  step "I am a visitor"
  page.should_not have_xpath("//div[@class=\"announcement group\"]")
end

Then /^I should see the first login banner$/ do
  step %{I should see "It looks like you've just logged into the Archive for the first time"}
end

Then /^I should not see the first login banner$/ do
  step %{I should not see "It looks like you've just logged into the Archive for the first time"}
end

Then /^I should see the first login popup$/ do
  step %{I should see "Here are some tips to help you get started."}
    step %{I should see "To log in, locate the login link"}
end

Then /^I should see the banner with minor edits$/ do
  step %{I should see "This is some banner text!"}
end

Then /^I should not see the banner with minor edits$/ do
  step %{I should not see "This is some banner text!"}
end

Then /^the page should have the different banner$/ do
  step %{I should see "This is new banner text"}
end

Then /^the page should not have a banner$/ do
  page.should_not have_xpath("//div[@class=\"announcement group\"]")
end

