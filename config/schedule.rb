# Use this file to easily define all of your cron jobs.
#
# It's helpful, but not entirely necessary to understand cron before proceeding.
# http://en.wikipedia.org/wiki/Cron

# Example:
#
# set :cron_log, "/path/to/my/cron_log.log"
#
# every 2.hours do
#   command "/usr/bin/some_great_command"
#   runner "MyModel.some_method"
#   rake "some:great:rake:task"
# end
#
# every 4.days do
#   runner "AnotherModel.prune_old_records"
# end

# Learn more: http://github.com/javan/whenever

# set your path in cron before the whenever section and use --update-crontab
set :set_path_automatically, false

set :cron_log, "#{path}/log/whenever.log"

# put a timestamp in the whenever log
every 1.days, at: 'midnight' do
  command "date"
end

# Purge user accounts that haven't been activated
every 1.days, at: '6:31 am' do
  rake "admin:purge_unvalidated_users"
end

# Unsuspend selected users
every 1.day, at: '6:51 am'  do
  rake "admin:unsuspend_users"
end

# Delete unused tags
every 1.day, at: '7:10 am' do
  rake "Tag:delete_unused"
end

# Delete old drafts
every 1.day, at: '7:40 am' do
  rake "work:purge_old_drafts"
end

# Send kudos notifications
every 1.day, at: '10:00 am' do
  rake "notifications:deliver_kudos"
end

# Send subscription notifications
every 1.hour do
  rake "notifications:deliver_subscriptions"
end

# Rerun redis jobs
every 10.minutes do
  rake "resque:run_failures"
end
