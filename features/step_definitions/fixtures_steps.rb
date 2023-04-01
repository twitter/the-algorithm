Given /^I have loaded the fixtures$/ do
  ActiveRecord::FixtureSet.reset_cache
  fixtures_folder = File.join(Rails.root, 'features', 'fixtures')
  fixtures = Dir[File.join(fixtures_folder, '*.yml')].map { |f| File.basename(f, '.yml') }
  ActiveRecord::FixtureSet.create_fixtures(fixtures_folder, fixtures)

  # Records loaded from fixtures don't run indexing callbacks, so
  # we need to queue them all for indexing.
  Indexer.all.map(&:index_from_db)
  step %{all indexing jobs have been run}
end

Given /^I have loaded the "([^\"]*)" fixture$/ do |fixture|
  ActiveRecord::FixtureSet.reset_cache
  fixtures_folder = File.join(Rails.root, 'features', 'fixtures')
  ActiveRecord::FixtureSet.create_fixtures(fixtures_folder, [fixture])

  # We should queue the new records for indexing, but this step is not
  # used for any Searchable models yet.
end
