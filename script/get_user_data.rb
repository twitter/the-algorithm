#!script/rails runner
# usage:
# bundle exec rails c
# load "#{Rails.root}/script/get_user_data.rb"

include Rails.application.routes.url_helpers

# Base URL
default_url_options[:host] = ArchiveConfig.APP_URL

# Prompt for the username
print "Enter username: "
login = gets.chomp.downcase

# Find the user
u = User.find_by(login: login)

# Names of user's favorite tags
favorite_tags = u.favorite_tags.joins(:tag).pluck(:name)

# URLs of all comments
comment_urls = []
u.comments.pluck(:id)&.map do |id|
  comment_urls << comment_url(id)
end

# URLs of all works user has kudosed
# Kudos can only be on works now, but there is a pull request for admin posts
kudosed_item_urls = [] 
u.kudos.pluck(:commentable_type, :commentable_id)&.map do |type, id|
  kudosed_item_urls << "#{ArchiveConfig.APP_URL}/#{type.underscore.downcase}s/#{id}"
end

# URLs of all nominations made in tag sets
tag_set_nomination_urls = []
TagSetNomination.where(pseud_id: u.pseuds.pluck(:id)).pluck(:id, :owned_tag_set_id)&.map do |id, tag_set_id|
  tag_set_nomination_urls << tag_set_nomination_url(tag_set_id: tag_set_id, id: id)
end

# Name of user's Fannish Next of Kin
if u.fannish_next_of_kin
  next_of_kin = User.find(u.fannish_next_of_kin.pluck(:user_id)).login
end

# Names of people listing user as their FNOK
next_of_kin_for = []
FannishNextOfKin.where(kin_id: u.id).pluck(:user_id)&.map do |id|
  next_of_kin_for << User.find(id).login
end

# List of roles in collections
collection_roles = []
u.pseuds.each do |pseud|
  pseud.collection_participants.pluck(:participant_role, :collection_id)&.map do |role, collection_id|
    collection_roles << "Role of #{role} in #{collection_url(Collection.find(collection_id).name)}"
  end
end

# List of IP addresses
# We handle IPs from the audits table below
ips = []
u.comments.pluck(:ip_address)&.map { |ip| ips << ip unless ip.blank? }
u.works.pluck(:ip_address)&.map { |ip| ips << ip unless ip.blank? }

# List of user agents
user_agents = []
u.comments.pluck(:user_agent)&.map { |ua| user_agents << ua unless ua.blank? }

# Lists of IP addresses and previous email addresses and usernames
# Actions that are or may be taken by admins, e.g. account activation, are
# excluded to avoid revealing admins' IP addresses
# We migrated to Devise on 27 December, 2018
previous_usernames = []
previous_emails = []
audits = u.audits.pluck(:action, :audited_changes, :remote_address)
audits.map do |audit|
  action = audit[0]
  changes = audit[1]
  ip = audit[2]
  # Created account
  if action == "create"
    ips << ip unless ip.blank?
  # Other account changes
  elsif action == "update"
    changes.each do |k, v|
      case k
      when "accepted_tos_version"
        ips << ip unless ip.blank?
      # Changed email address
      when "email"
        ips << ip unless ip.blank?
        previous_emails << v[0] unless v[0].blank?
      # Changed password, post-Devise
      when "encrypted_password"
        ips << ip unless ip.blank?
      # Failed login attempt, post-Devise
      # This is currently only recorded after a password reset or after the
      # account is locked and unlocked
      when "failed_attempts"
        ips << ip unless ip.blank?
      # Failed login attempt, pre-Devise
      when "failed_login_count"
        ips << ip unless ip.blank?
      # Failed login attempt that resulted in account being locked, post-Devise
      when "locked_at"
        ips << ip unless ip.blank?
      # Changed username
      when "login"
        ips << ip unless ip.blank?
        previous_usernames << v[0] unless v[0].blank?
      # Requested password reset email, pre-Devise
      when "recently_reset"
        ips << ip unless ip.blank?
      # Submitted login form with "Remember me" checked, post-Devise
      # This is recorded whether the login attempt was successful or not
      when "remember_created_at"
        ips << ip unless ip.blank?
      # Requested password reset email, post-Devise
      when "reset_password_sent_at"
        ips << ip unless ip.blank?
      # Logged in, post-Devise
      when "sign_in_count"
        ips << ip unless ip.blank?
      end
    end
  end
end


puts "Data for #{u.login} (#{u.email})"
unless previous_usernames.empty?
  puts
  puts "Previous Usernames: #{previous_usernames.to_sentence}"
end
unless previous_emails.empty?
  puts
  puts "Previous Email Addresses: #{previous_emails.to_sentence}"
end
unless ips.empty?
  puts
  puts "IP Addresses:"
  ips.uniq.map do |ip|
    puts "  #{ip}"
  end
end
unless user_agents.empty?
  puts
  puts "User Agents:"
  user_agents.uniq.map do |user_agent|
    puts "  #{user_agent}"
  end
end
if u.fannish_next_of_kin || !next_of_kin_for.empty?
  puts
  puts "Fannish Next of Kin: #{next_of_kin}" if u.fannish_next_of_kin
  puts "Fannish Next of Kin For: #{next_of_kin_for.to_sentence}" unless next_of_kin_for.empty?
end
puts
puts "Pseuds: #{user_pseuds_url(u)}"
puts "Profile: #{user_profile_url(u)}"
puts "Preferences: #{user_preferences_url(u)}"
puts
puts "Works: #{user_works_url(u)}"
puts "Drafts: #{drafts_user_works_url(u)}"
puts "Series: #{user_series_index_url(u)}"
puts "Bookmarks: #{user_bookmarks_url(u)}"
puts
puts "Collections: #{user_collections_url(u)}"
unless collection_roles.empty?
  puts "Collection Roles: "
  collection_roles.map do |role|
    puts "  #{role}"
  end
end
puts
puts "Tag Sets: #{user_tag_sets_url(u)}"
unless tag_set_nomination_urls.empty?
  puts "Tag Set Nominations: "
  tag_set_nomination_urls.map do |url|
    puts "  #{url}"
  end
end
puts
puts "Challenge Sign-ups: #{user_signups_url(u)}"
puts "Gift Exchange Assignments: #{user_assignments_url(u)}"
puts "Prompt Meme Claims: #{user_claims_url(u)}"
puts
puts "History and Marked for Later: #{user_readings_url(u)}"
puts "Subscriptions: #{user_subscriptions_url(u)}"
puts
puts "Gifts: #{user_gifts_url(u)}"
puts "Related Works: #{user_related_works_url(u)}"
puts
puts "Skins: #{user_skins_url(u)}"
puts
puts "Invitations: #{user_invitations_url(u)}"
unless favorite_tags.empty?
  puts
  puts "Favorite Tags: #{favorite_tags.to_sentence}"
end
unless comment_urls.empty?
  puts
  puts "Comments Left: "
  comment_urls.map do |url|
    puts "  #{url}"
  end
end
unless kudosed_item_urls.empty?
  puts
  puts "Kudos Given To: "
  kudosed_item_urls.map do |url|
    puts "  #{url}"
  end
end
