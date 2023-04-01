# This task will reset the db and load in clean test data using the fixtures
# Usage: rake db:otwseed
namespace :db do
  desc "Raise an error unless the Rails.env is development"
  task :development_environment_only do
    raise "ZOMG NOT IN PRODUCTION!" unless Rails.env.development? || Rails.env.test?
  end

  desc "Reset and then seed the development database with test data from the fixtures"
  task otwseed: [
    :environment, :development_environment_only,
    # We can't use:
    # - db:reset, because schema files may not be up-to-date and migrations are required.
    # - db:migrate:reset, because we've deleted old migrations at various points.
    :drop, :create, "schema:load", :migrate, :seed, "fixtures:load",
    "work:missing_stat_counters", "Tag:reset_filters", "Tag:reset_filter_counts"
  ]
end
