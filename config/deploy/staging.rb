# ORDER OF EVENTS
# Calling "cap deploy" runs:
#   deploy:update which runs:
#       deploy:update_code
#       deploy:symlink
#   deploy:restart
#
# Calling "cap deploy:migrations" inserts the task "deploy:migrate" before deploy:symlink

require "capistrano/gitflow_version"

fetch(:servers).each do |s|
  server s[:host], *s[:roles], s[:options] || {}
end

set :rails_env, 'staging'

# our tasks which are staging specific
namespace :stage_only do
  desc "Set up staging robots.txt file"
  task :update_robots, roles: :web do
    run "cp #{release_path}/public/robots.private.txt #{release_path}/public/robots.txt"
  end

  desc "Send out 'Testarchive deployed' notification"
  task :notify_testers do
    system "echo 'Testarchive deployed' | mail -s 'Testarchive deployed' #{mail_to}"
  end
end

#before "deploy:update_code", "stage_only:git_in_home"
after "deploy:update_code", "stage_only:update_robots"

#before "db:reset_on_stage", "deploy:web:disable"
# reset the database and clear subscriptions and emails out of it
#after "db:reset_on_stage", "stage_only:reset_db", "stage_only:clear_subscriptions", "stage_only:clear_emails"
#after "db:reset_on_stage", "stage_only:reindex_elasticsearch"
#after "db:reset_on_stage", "deploy:web:enable"

# reload the site skins after each deploy since there may have been CSS changes
after "deploy:restart", "stage_only:notify_testers"
