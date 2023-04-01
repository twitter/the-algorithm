Given /^I want to edit my profile$/ do
  visit user_profile_path(User.current_user)
  click_link("Edit My Profile")
  step %{I should see "Edit My Profile"}
end


When /^I fill in the details of my profile$/ do
  fill_in("Title", with: "Test title thingy")
  fill_in("Location", with: "Alpha Centauri")
  fill_in("About Me", with: "This is some text about me.")
  click_button("Update")
end


When /^I change the details in my profile$/ do
  fill_in("Title", with: "Alternative title thingy")
  fill_in("Location", with: "Beta Centauri")
  fill_in("About Me", with: "This is some different text about me.")
  click_button("Update")
end


When /^I remove details from my profile$/ do
  fill_in("Title", with: "")
  fill_in("Location", with: "")
  fill_in("About Me", with: "")
  click_button("Update")
end


When /^I enter an incorrect password$/ do
  click_link("Change Email")
  fill_in("new_email", with: "valid2@archiveofourown.org")
  fill_in("email_confirmation", with: "valid2@archiveofourown.org")
  fill_in("password_check", with: "passw")
  click_button("Change Email")
end


When /^I change my email$/ do
  click_link("Change Email")
  fill_in("new_email", with: "valid2@archiveofourown.org")
  fill_in("email_confirmation", with: "valid2@archiveofourown.org")
  fill_in("password_check", with: "password")
  click_button("Change Email")
end


When /^I view my profile$/ do
  visit user_path(User.current_user)
  step %{I should see "Dashboard"}
  click_link("Profile")
end


When /^I enter an invalid email$/ do
  click_link("Change Email")
  fill_in("new_email", with: "bob.bob.bob")
  fill_in("email_confirmation", with: "bob.bob.bob")
  fill_in("password_check", with: "password")
  click_button("Change Email")
end


When "I enter non-matching emails" do
  click_link("Change Email")
  fill_in("new_email", with: "correct@example.com")
  fill_in("email_confirmation", with: "invalid@example.com")
  fill_in("password_check", with: "password")
  click_button("Change Email")
end

When /^I enter a duplicate email$/ do
  user = FactoryBot.create(:user, login: "testuser2", password: "password", email: "foo@ao3.org")
  step %{confirmation emails have been delivered}

  click_link("Change Email")
  fill_in("new_email", with: "foo@ao3.org")
  fill_in("email_confirmation", with: "foo@ao3.org")
  fill_in("password_check", with: "password")
  click_button("Change Email")
end

When /^I enter a birthdate that shows I am under age$/ do
  date = 13.years.ago + 1.day
  select(date.year, from: "profile_attributes[date_of_birth(1i)]")
  select(date.strftime("%B"), from: "profile_attributes[date_of_birth(2i)]")
  select(date.day, from: "profile_attributes[date_of_birth(3i)]")
  click_button("Update")
end


When /^I change my preferences to display my date of birth$/ do
  click_link("Preferences")
  check ("Show my date of birth to other people.")
  click_button("Update")
  visit user_path(User.current_user)
  click_link("Profile")
end


When /^I change my preferences to display my email address$/ do
  click_link("Preferences")
  check ("Show my email address to other people.")
  click_button("Update")
  visit user_path(User.current_user)
  click_link("Profile")
end


When /^I fill in my date of birth$/ do
  select("1980", from: "profile_attributes[date_of_birth(1i)]")
  select("November", from: "profile_attributes[date_of_birth(2i)]")
  select("30", from: "profile_attributes[date_of_birth(3i)]")
  click_button("Update")
end


When /^I make a mistake typing my old password$/ do
  click_link("Password")
  fill_in("password", with: "newpass1")
  fill_in("password_confirmation", with: "newpass1")
  fill_in("password_check", with: "wrong")
  click_button("Change Password")
end


When /^I make a typing mistake confirming my new password$/ do
  click_link("Password")
  fill_in("password", with: "newpass1")
  fill_in("password_confirmation", with: "newpass2")
  fill_in("password_check", with: "password")
  click_button("Change Password")
end


When /^I change my password$/ do
  click_link("Password")
  fill_in("password", with: "newpass1")
  fill_in("password_confirmation", with: "newpass1")
  fill_in("password_check", with: "password")
  click_button("Change Password")
end
