### GIVEN

Given /^an invitation(?: for "([^\"]+)") exists$/ do |invitee_email|
  invite = Invitation.new
  invite.invitee_email = (invitee_email ? invitee_email : "default@example.org")
  invite.save
end

def invite(attributes = {})
  @invite ||= Invitation.new
  @invite.invitee_email = "default@example.org"
  @invite.save
  @invite
end

Given /^account creation is disabled$/ do
  steps %Q{
    Given the following admin settings are configured:
    | account_creation_enabled | 0 |
    | creation_requires_invite | 0 |
    | invite_from_queue_enabled | 0 |
    | request_invite_enabled | 0 |
  }
end

Given /^account creation is enabled$/ do
  steps %Q{
    Given the following admin settings are configured:
    | account_creation_enabled | 1 |
  }
end

Given /^invitations are required$/ do
  steps %{
    Given I have no users
      And account creation requires an invitation
      And users can request invitations
  }
end

Given /^account creation requires an invitation$/ do
  steps %Q{
    Given the following admin settings are configured:
    | account_creation_enabled | 1 |
    | creation_requires_invite | 1 |
  }
end

Given /^account creation does not require an invitation$/ do
  steps %Q{
    Given the following admin settings are configured:
    | account_creation_enabled | 1 |
    | creation_requires_invite | 0 |
  }
end

Given /^users can request invitations$/ do
  steps %Q{
    Given the following admin settings are configured:
    | request_invite_enabled | 1 |
  }
end

Given /^the invitation queue is enabled$/ do
  steps %Q{
    Given the following admin settings are configured:
    | invite_from_queue_enabled | 1 |
  }
end

Given /^the invitation queue is disabled$/ do
  steps %Q{
    Given the following admin settings are configured:
    | invite_from_queue_enabled | 0 |
  }
end

Given /^"([^\"]*)" has "([^"]*)" invitations?$/ do |login, invitation_count|
  user = User.find_by(login: login)
  # If there are more invitations than we want, first destroy them
  if invitation_count.to_i < user.invitations.count
    user.invitations.destroy_all
  end
  # Now create the number of invitations we want
  (invitation_count.to_i - user.invitations.count).times { user.invitations.create }
end

Given /^an invitation request for "([^"]*)"$/ do |email|
  visit invite_requests_path
  fill_in("invite_request[email]", with: email)
  click_button("Add me to the list")
end

Given "there are {int} invite request(s) per page" do |amount|
  allow(InviteRequest).to receive(:per_page).and_return(amount)
end

### WHEN

When /^I use an invitation to sign up$/ do
  visit signup_path(invite.token)
end

When /^I use an already used invitation to sign up$/ do
  steps %Q{
    Given the following activated user exists
      | login    | password |
      | invited  | password |
  }
  user = User.find_by(login: "invited")
  invite.redeemed_at = Time.now
  invite.mark_as_redeemed(user)
  invite.save
  visit signup_path(invite.token)
end

When /^I try to invite a friend from my user page$/ do
  step %{I am logged in as "user1"}
  step %{I go to my user page}
  step %{I follow "Invitations"}
end

When /^I request some invites$/ do
  step %{I try to invite a friend from my user page}
  step %{I follow "Request invitations"}
  step %{I fill in "How many invitations would you like? (max 10)" with "3"}
  step %{I fill in "Please specify why you'd like them:" with "I want them for a friend"}
  step %{I press "Send Request"}
end

When /^I view requests as an admin$/ do
  step %{I am logged in as an admin}
  step %{I follow "Invitations"}
  step %{I follow "Manage Requests"}
end

When /^an admin grants the request$/ do
  step %{I view requests as an admin}
  step %{I fill in "requests[user1]" with "2"}
  step %{I press "Update"}
end

When /^I check how long "(.*?)" will have to wait in the invite request queue$/ do |email|
  visit(status_invite_requests_path)
  fill_in("email", with: "#{email}")
  click_button("Look me up")
end

### Then

Then /^I should see how long I have to activate my account$/ do
  days_to_activate = AdminSetting.first.days_to_purge_unactivated? ? (AdminSetting.first.days_to_purge_unactivated * 7) : ArchiveConfig.DAYS_TO_PURGE_UNACTIVATED
  step %{I should see "You must confirm your email address within #{days_to_activate} days"}
end

Then /^"([^"]*)" should have "([^"]*)" invitations$/ do |login, invitation_count|
  user = User.find_by(login: login)
  assert user.invitations.count == invitation_count.to_i
end

Then "the invite queue should list the following:" do |desired|
  actual = all("table tbody tr").map do |row|
    {
      "position" => row.find("td:nth-child(1)").text,
      "email" => row.find("td:nth-child(2)").text
    }
  end

  expect(actual).to eq(desired.hashes)
end
