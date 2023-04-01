### GIVEN

Given /^the following admin settings are configured:$/ do |table|
  admin_settings = AdminSetting.first
  admin_settings.assign_attributes(table.rows_hash.symbolize_keys)
  # Skip validations which require setting an admin as the last updater.
  admin_settings.save!(validate: false)
end

Given /the following admins? exists?/ do |table|
  table.hashes.each do |hash|
    FactoryBot.create(:admin, hash)
  end
end

Given "I am logged in as a super admin" do
  step %{I am logged in as a "superadmin" admin}
end

Given "I am logged in as a(n) {string} admin" do |role|
  step "I start a new session"
  login = "testadmin-#{role}"
  FactoryBot.create(:admin, login: login, roles: [role]) if Admin.find_by(login: login).nil?
  visit new_admin_session_path
  fill_in "Admin user name", with: login
  fill_in "Admin password", with: "adminpassword"
  click_button "Log In as Admin"
  step %{I should see "Successfully logged in"}
end

Given "I am logged in as an admin" do
  step "I start a new session"
  FactoryBot.create(:admin, login: "testadmin", email: "testadmin@example.org") if Admin.find_by(login: "testadmin").nil?
  visit new_admin_session_path
  fill_in "Admin user name", with: "testadmin"
  fill_in "Admin password", with: "adminpassword"
  click_button "Log In as Admin"
  step %{I should see "Successfully logged in"}
end

Given /^basic languages$/ do
  Language.default
  german = Language.find_or_create_by(short: "DE", name: "Deutsch", support_available: true, abuse_support_available: true)
  Locale.create(iso: "de", name: "Deutsch", language: german)
end

Given /^Persian language$/ do
  Language.default
  persian = Language.find_or_create_by(short: "fa", name: "Persian", support_available: true, abuse_support_available: true)
  Locale.create(iso: "fa", name: "Persian", language: persian)
end

Given /^downloads are off$/ do
  step("I am logged in as a super admin")
  visit(admin_settings_path)
  uncheck("Allow downloads")
  click_button("Update")
end

Given /^tag wrangling is off$/ do
  step(%{I am logged in as a "tag_wrangling" admin})
  visit(admin_settings_path)
  step(%{I check "Turn off tag wrangling for non-admins"})
  step(%{I press "Update"})
  step("I log out")
end

Given /^tag wrangling is on$/ do
  step(%{I am logged in as a "tag_wrangling" admin})
  visit(admin_settings_path)
  step(%{I uncheck "Turn off tag wrangling for non-admins"})
  step(%{I press "Update"})
  step("I log out")
end

Given /^the support form is disabled and its text field set to "Please don't contact us"$/ do
  step(%{I am logged in as a "support" admin})
  visit(admin_settings_path)
  check("Turn off support form")
  fill_in(:admin_setting_disabled_support_form_text, with: "Please don't contact us")
  click_button("Update")
end

Given /^the support form is enabled$/ do
  step(%{I am logged in as a "support" admin})
  visit(admin_settings_path)
  uncheck("Turn off support form")
  click_button("Update")
end

Given /^I have posted known issues$/ do
  step %{I am logged in as an admin}
  step %{I follow "Admin Posts"}
  step %{I follow "Known Issues" within "#header"}
  step %{I follow "make a new known issues post"}
  step %{I fill in "known_issue_title" with "First known problem"}
  step %{I fill in "content" with "This is a bit of a problem"}
  step %{I press "Post"}
end

Given /^I have posted an admin post$/ do
  step(%{I am logged in as a "communications" admin})
  step("I make an admin post")
  step("I log out")
end

Given "the admin post {string}" do |title|
  FactoryBot.create(:admin_post, title: title)
end

Given "the fannish next of kin {string} for the user {string}" do |kin, user|
  user = ensure_user(user)
  kin = ensure_user(kin)
  user.create_fannish_next_of_kin(kin: kin, kin_email: "fnok@example.com")
end

Given /^the user "([^\"]*)" is suspended$/ do |user|
  step %{the user "#{user}" exists and is activated}
  step %{I am logged in as a "policy_and_abuse" admin}
  step %{I go to the abuse administration page for "#{user}"}
  choose("admin_action_suspend")
  fill_in("suspend_days", with: 30)
  fill_in("Notes", with: "Why they are suspended")
  click_button("Update")
end

Given /^the user "([^\"]*)" is banned$/ do |user|
  step %{the user "#{user}" exists and is activated}
  step(%{I am logged in as a "policy_and_abuse" admin})
  step %{I go to the abuse administration page for "#{user}"}
  choose("admin_action_ban")
  fill_in("Notes", with: "Why they are banned")
  click_button("Update")
end

Then /^the user "([^\"]*)" should be permanently banned$/ do |user|
  u = User.find_by(login: user)
  assert u.banned?
end

Given /^I have posted an admin post without paragraphs$/ do
  step(%{I am logged in as a "communications" admin})
  step("I make an admin post without paragraphs")
  step("I log out")
end

Given /^I have posted an admin post with tags "(.*?)"$/ do |tags|
  step(%{I am logged in as a "communications" admin})
  visit new_admin_post_path
  fill_in("admin_post_title", with: "Default Admin Post")
  fill_in("content", with: "Content of the admin post.")
  fill_in("admin_post_tag_list", with: tags)
  click_button("Post")
end

Given(/^the following language exists$/) do |table|
  table.hashes.each do |hash|
    FactoryBot.create(:language, hash)
  end
end

Given /^I have posted an admin post with comments disabled$/ do
  step %{I am logged in as a "communications" admin}
  step %{I start to make an admin post}
  choose("No one can comment")
  click_button("Post")
  step %{I log out}
end

Given "an abuse ticket ID exists" do
  ticket = {
    "departmentId" => ArchiveConfig.ABUSE_ZOHO_DEPARTMENT_ID,
    "status" => "Open",
    "webUrl" => Faker::Internet.url
  }
  allow_any_instance_of(ZohoResourceClient).to receive(:find_ticket).and_return(ticket)
end

Given "a work {string} with the original creator {string}" do |title, creator|
  step %{I am logged in as "#{creator}"}
  step %{I post the work "#{title}"}
  FactoryBot.create(:user, login: "orphan_account")
  step %{I orphan the work "#{title}"}
  step %{I log out}
end

Given "the admin {string} is locked" do |login|
  admin = Admin.find_by(login: login) || FactoryBot.create(:admin, login: login)
  # Same as script/lock_admin.rb
  admin.lock_access!({ send_instructions: false })
end

Given "the admin {string} is unlocked" do |login|
  admin = Admin.find_by(login: login) || FactoryBot.create(:admin, login: login)
  # Same as script/unlock_admin.rb
  admin.unlock_access!
end

### WHEN

When /^I visit the last activities item$/ do
  visit("/admin/activities/#{AdminActivity.last.id}")
end

When /^I fill in "([^"]*)" with "([^"]*)'s" invite code$/ do |field, login|
  user = User.find_by(login: login)
  token = user.invitations.first.token
  fill_in(field, with: token)
end

When /^I start to make an admin post$/ do
  visit new_admin_post_path
  fill_in("admin_post_title", with: "Default Admin Post")
  fill_in("content", with: "Content of the admin post.")
end

When /^I make an admin post$/ do
  step %(I start to make an admin post)
  click_button("Post")
end

When /^I make an admin post without paragraphs$/ do
  visit new_admin_post_path
  fill_in("admin_post_title", with: "Admin Post Without Paragraphs")
  fill_in("content", with: "<ul><li>This post</li><li>is just</li><li>a list</li></ul>")
  click_button("Post")
end

When /^I make a multi-question FAQ post$/ do
  visit new_archive_faq_path
  fill_in("Question*", with: "Number 1 Question.")
  fill_in("Answer*", with: "Number 1 posted FAQ, this is.")
  fill_in("Category name*", with: "Standard FAQ Category")
  fill_in("Anchor name*", with: "Number1anchor")
  click_button("Post")
  step %{I follow "Edit"}
  step %{I fill in "Questions:" with "3"}
  step %{I press "Update Form"}
  fill_in("archive_faq_questions_attributes_1_question", with: "Number 2 Question.")
  fill_in("archive_faq_questions_attributes_1_content", with: "This is an answer to the second question")
  fill_in("archive_faq_questions_attributes_1_anchor", with: "whatisao32")
  fill_in("archive_faq_questions_attributes_2_question", with: "Number 3 Question.")
  fill_in("archive_faq_questions_attributes_2_content", with: "This is an answer to the third question")
  fill_in("archive_faq_questions_attributes_2_anchor", with: "whatisao33")
  click_button("Post")
end

When /^(\d+) Archive FAQs? exists?$/ do |n|
  (1..n.to_i).each do |i|
    FactoryBot.create(:archive_faq, id: i)
  end
end

When /^the invite_from_queue_at is yesterday$/ do
  AdminSetting.first.update_attribute(:invite_from_queue_at, Time.now - 1.day)
end

When "the scheduled check_invite_queue job is run" do
  Resque.enqueue(AdminSetting, :check_queue)
end

When /^I edit known issues$/ do
  step %{I am logged in as an admin}
  step %{I follow "Admin Posts"}
  step %{I follow "Known Issues" within "#header"}
  step %{I follow "Edit"}
  step %{I fill in "known_issue_title" with "More known problems"}
  step %{I fill in "content" with "This is a bit of a problem, and this is too"}
  step %{I press "Post"}
end

When /^I delete known issues$/ do
  step %{I am logged in as an admin}
  step %{I follow "Admin Posts"}
  step %{I follow "Known Issues" within "#header"}
  step %{I follow "Delete"}
end

When /^I uncheck the "([^\"]*)" role checkbox$/ do |role|
  role_name = role.parameterize.underscore
  role_id = Role.find_by(name: role_name).id
  uncheck("user_roles_#{role_id}")
end

When /^I make a translation of an admin post( with tags "(.*?)")?$/ do |tags|
  admin_post = AdminPost.find_by(title: "Default Admin Post")
  # If post doesn't exist, assume we want to reference a non-existent post
  admin_post_id = !admin_post.nil? ? admin_post.id : 0
  visit new_admin_post_path
  fill_in("admin_post_title", with: "Deutsch Ankuendigung")
  fill_in("content", with: "Deutsch Woerter")
  step %{I select "Deutsch" from "Choose a language"}
  fill_in("admin_post_translated_post_id", with: admin_post_id)
  fill_in("admin_post_tag_list", with: tags) if tags
  click_button("Post")
end

When /^I hide the work "(.*?)"$/ do |title|
  work = Work.find_by(title: title)
  visit work_path(work)
  step %{I follow "Hide Work"}
end

When "the search criteria contains the ID for {string}" do |login|
  user_id = User.find_by(login: login).id
  fill_in("user_id", with: user_id)
end

When "it is past the admin password reset token's expiration date" do
  days = ArchiveConfig.DAYS_UNTIL_ADMIN_RESET_PASSWORD_LINK_EXPIRES + 1
  step "it is currently #{days} days from now"
end

### THEN

Then (/^the translation information should still be filled in$/) do
  step %{the "admin_post_title" field should contain "Deutsch Ankuendigung"}
  step %{the "content" field should contain "Deutsch Woerter"}
  step %{"Deutsch" should be selected within "Choose a language"}
end

Then /^I should see a translated admin post( with tags "(.*?)")?$/ do |tags|
  tags = tags.split(/, ?/) if tags
  step %{I go to the admin-posts page}
  step %{I should see "Default Admin Post"}
  step %{I should see "Tags: #{tags.join(' ')}"} if tags
  step %{I should see "Translations: Deutsch"}
  step %{I follow "Default Admin Post"}
  step %{I should see "Deutsch" within "dd.translations"}
  step %{I follow "Deutsch"}
  step %{I should see "Deutsch Woerter"}
  tags&.each do |tag|
    step %{I should see "#{tag}" within "dd.tags"}
  end
end

Then (/^I should not see a translated admin post$/) do
  step %{I go to the admin-posts page}
  step %{I should see "Default Admin Post"}
  step %{I should see "Deutsch Ankuendigung"}
  step %{I follow "Default Admin Post"}
  step %{I should not see "Translations: Deutsch"}
end

Then /^the work "([^\"]*)" should be hidden$/ do |work|
  w = Work.find_by_title(work)
  user = w.pseuds.first.user.login
  step %{logged out users should not see the hidden work "#{work}" by "#{user}"}
  step %{logged in users should not see the hidden work "#{work}" by "#{user}"}
end

Then /^the work "([^\"]*)" should not be hidden$/ do |work|
  w = Work.find_by_title(work)
  user = w.pseuds.first.user.login
  step %{logged out users should see the unhidden work "#{work}" by "#{user}"}
  step %{logged in users should see the unhidden work "#{work}" by "#{user}"}
end

Then /^logged out users should not see the hidden work "([^\"]*)" by "([^\"]*)"?/ do |work, user|
  step "I am a visitor"
  step %{I should not see the hidden work "#{work}" by "#{user}"}
end

Then /^logged in users should not see the hidden work "([^\"]*)" by "([^\"]*)"?/ do |work, user|
  step %{I am logged in as a random user}
  step %{I should not see the hidden work "#{work}" by "#{user}"}
end

Then /^I should not see the hidden work "([^\"]*)" by "([^\"]*)"?/ do |work, user|
  step %{I am on #{user}'s works page}
  step %{I should not see "#{work}"}
  step %{I view the work "#{work}"}
  step %{I should see "Sorry, you don't have permission to access the page you were trying to reach."}
end

Then /^"([^\"]*)" should see their work "([^\"]*)" is hidden?/ do |user, work|
  step %{I am logged in as "#{user}"}
  step %{I am on my works page}
  step %{I should not see "#{work}"}
  step %{I view the work "#{work}"}
  step %{I should see the image "title" text "Hidden by Administrator"}
end

Then /^logged out users should see the unhidden work "([^\"]*)" by "([^\"]*)"?/ do |work, user|
  step "I am a visitor"
  step %{I should see the unhidden work "#{work}" by "#{user}"}
end

Then /^logged in users should see the unhidden work "([^\"]*)" by "([^\"]*)"?/ do |work, user|
  step %{I am logged in as a random user}
  step %{I should see the unhidden work "#{work}" by "#{user}"}
end

Then /^I should see the unhidden work "([^\"]*)" by "([^\"]*)"?/ do |work, user|
  step %{I am on #{user}'s works page}
  step %{I should see "#{work}"}
  step %{I view the work "#{work}"}
  step %{I should see "#{work}"}
end

Then(/^the work "(.*?)" should not be deleted$/) do |work|
  w = Work.find_by(title: work)
  assert w && w.posted?
end

Then(/^there should be no bookmarks on the work "(.*?)"$/) do |work|
  w = Work.find_by(title: work)
  assert w.bookmarks.count == 0
end

Then(/^there should be no comments on the work "(.*?)"$/) do |work|
  w = Work.find_by(title: work)
  assert w.comments.count == 0
end

When(/^the user "(.*?)" is unbanned in the background/) do |user|
  u = User.find_by(login: user)
  u.update_attribute(:banned, false)
end

Given "I have banned the address {string}" do |email|
  visit admin_blacklisted_emails_url
  fill_in("Email to ban", with: email)
  click_button("Ban Email")
end

Given "I have banned the address for user {string}" do |user|
  visit admin_blacklisted_emails_url
  u = User.find_by(login: user)
  fill_in("admin_blacklisted_email_email", with: u.email)
  click_button("Ban Email")
end

Then "the address {string} should be banned" do |email|
  visit admin_blacklisted_emails_url
  fill_in("Email to find", with: email)
  click_button("Search Banned Emails")
  assert page.should have_content(email)
end

Then "the address {string} should not be banned" do |email|
  visit admin_blacklisted_emails_url
  fill_in("Email to find", with: email)
  click_button("Search Banned Emails")
  step %{I should see "0 emails found"}
end

Then(/^I should not be able to comment with the address "([^"]*)"$/) do |email|
  step %{the work "New Work"}
  step %{I post the comment "I loved this" on the work "New Work" as a guest with email "#{email}"}
  step %{I should see "has been blocked at the owner's request"}
  step %{I should not see "Comment created!"}
end

Then(/^I should be able to comment with the address "([^"]*)"$/) do |email|
  step %{the work "New Work"}
  step %{I post the comment "I loved this" on the work "New Work" as a guest with email "#{email}"}
  step %{I should not see "has been blocked at the owner's request"}
  step %{I should see "Comment created!"}
end

Then /^the work "([^\"]*)" should be marked as spam/ do |work|
  w = Work.find_by_title(work)
  assert w.spam?
end

Then /^the work "([^\"]*)" should not be marked as spam/ do |work|
  w = Work.find_by_title(work)
  assert !w.spam?
end

Then "I should see {int} admin activity log entry/entries" do |count|
  expect(page).to have_css("tr[id^=admin_activity_]", count: count)
end

Then /^the user content should be shown as right-to-left$/ do
  page.should have_xpath("//div[contains(@class, 'userstuff') and @dir='rtl']")
end

Then "I should see the original creator {string}" do |creator|
  user = User.find_by(login: creator)
  expect(page).to have_selector(".original_creators",
                                text: "#{user.id} (#{creator})")
end
