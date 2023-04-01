ENV["RAILS_ENV"] ||= "test"

require File.expand_path("../config/environment", __dir__)
require "simplecov"

require "rspec/rails"
require "factory_bot"
require "database_cleaner"
require "email_spec"
require "webmock/rspec"
require "n_plus_one_control/rspec"

DatabaseCleaner.start
DatabaseCleaner.clean

# Requires supporting ruby files with custom matchers and macros, etc,
# in spec/support/ and its subdirectories.
Dir[Rails.root.join("spec/support/**/*.rb")].sort.each { |f| require f }

RSpec.configure do |config|
  config.mock_with :rspec

  config.expect_with :rspec do |c|
    c.syntax = [:should, :expect]
  end

  # TODO: Remove gems delorean and timecop now that Rails has time-travel helpers.
  config.include ActiveSupport::Testing::TimeHelpers
  config.include FactoryBot::Syntax::Methods
  config.include EmailSpec::Helpers
  config.include EmailSpec::Matchers
  config.include Devise::Test::ControllerHelpers, type: :controller
  config.include Devise::Test::IntegrationHelpers, type: :request
  config.include TaskExampleGroup, type: :task

  config.before :suite do
    Rails.application.load_tasks
    DatabaseCleaner.strategy = :transaction
    DatabaseCleaner.clean
    Indexer.all.map(&:prepare_for_testing)
    ArchiveWarning.find_or_create_by!(name: ArchiveConfig.WARNING_CHAN_TAG_NAME, canonical: true)
    ArchiveWarning.find_or_create_by!(name: ArchiveConfig.WARNING_NONE_TAG_NAME, canonical: true)
    Category.find_or_create_by!(name: ArchiveConfig.CATEGORY_SLASH_TAG_NAME, canonical: true)

    # TODO: The "Not Rated" tag ought to be marked as adult, but we want to
    # keep the adult status of the tag consistent with the features, so for now
    # we have a non-adult "Not Rated" tag:
    Rating.find_or_create_by!(name: ArchiveConfig.RATING_DEFAULT_TAG_NAME, canonical: true)

    Rating.find_or_create_by!(name: ArchiveConfig.RATING_EXPLICIT_TAG_NAME, canonical: true, adult: true)
    # Needs these for the API tests.
    ArchiveWarning.find_or_create_by!(name: ArchiveConfig.WARNING_DEFAULT_TAG_NAME, canonical: true)
    ArchiveWarning.find_or_create_by!(name: ArchiveConfig.WARNING_NONCON_TAG_NAME, canonical: true)
    Rating.find_or_create_by!(name: ArchiveConfig.RATING_GENERAL_TAG_NAME, canonical: true)
  end

  config.before :each do
    DatabaseCleaner.start
    User.current_user = nil
    User.should_update_wrangling_activity = false
    clean_the_database

    # Clears used values for all generators.
    Faker::UniqueGenerator.clear

    # Reset global locale setting.
    I18n.locale = I18n.default_locale

    # Assume all spam checks pass by default.
    allow(Akismetor).to receive(:spam?).and_return(false)

    # Stub all requests to example.org, the default external work URL:
    WebMock.stub_request(:any, "www.example.org")
  end

  config.after :each do
    DatabaseCleaner.clean
  end

  config.after :suite do
    DatabaseCleaner.clean_with :truncation
    Indexer.all.map(&:delete_index)
  end

  # Remove the folder where test images are saved.
  config.after(:suite) do
    FileUtils.rm_rf(Dir[Rails.root.join("public/system/test")])
  end

  config.before :each, bookmark_search: true do
    BookmarkIndexer.prepare_for_testing
  end

  config.after :each, bookmark_search: true do
    BookmarkIndexer.delete_index
  end

  config.before :each, pseud_search: true do
    PseudIndexer.prepare_for_testing
  end

  config.after :each, pseud_search: true do
    PseudIndexer.delete_index
  end

  config.before :each, tag_search: true do
    TagIndexer.prepare_for_testing
  end

  config.after :each, tag_search: true do
    TagIndexer.delete_index
  end

  config.before :each, work_search: true do
    WorkIndexer.prepare_for_testing
  end

  config.after :each, work_search: true do
    WorkIndexer.delete_index
  end

  config.before :each, default_skin: true do
    AdminSetting.current.update_attribute(:default_skin, Skin.default)
  end

  config.before :each, type: :controller do
    @request.host = "www.example.com"
  end

  config.before :each, :frozen do
    freeze_time
  end

  # If you're not using ActiveRecord, or you'd prefer not to run each of your
  # examples within a transaction, remove the following line or assign false
  # instead of true.
  config.use_transactional_fixtures = true

  # For email veracity checks
  BAD_EMAILS = ['Abc.example.com', 'A@b@c@example.com', 'a\"b(c)d,e:f;g<h>i[j\k]l@example.com', 'this is"not\allowed@example.com', 'this\ still\"not/\/\allowed@example.com', 'nodomain', 'foo@oops'].freeze
  # For email format checks
  BADLY_FORMATTED_EMAILS = ['ast*risk@example.com', 'asterisk@ex*ample.com'].freeze
  INVALID_URLS = ['no_scheme.com', 'ftp://ftp.address.com', 'http://www.b@d!35.com', 'https://www.b@d!35.com', 'http://b@d!35.com', 'https://www.b@d!35.com'].freeze
  VALID_URLS = ['http://rocksalt-recs.livejournal.com/196316.html', 'https://rocksalt-recs.livejournal.com/196316.html'].freeze
  INACTIVE_URLS = ['https://www.iaminactive.com', 'http://www.iaminactive.com', 'https://iaminactive.com', 'http://iaminactive.com'].freeze

  # rspec-rails 3 will no longer automatically infer an example group's spec type
  # from the file location. You can explicitly opt-in to the feature using this
  # config option.
  # To explicitly tag specs without using automatic inference, set the `:type`
  # metadata manually:
  #
  #     describe ThingsController, type: :controller do
  #       # Equivalent to being in spec/controllers
  #     end
  config.infer_spec_type_from_file_location!
  config.define_derived_metadata(file_path: %r{/spec/lib/tasks/}) do |metadata|
    metadata[:type] = :task
  end

  config.formatter = :documentation

  config.file_fixture_path = "spec/support/fixtures"
end

RSpec::Matchers.define_negated_matcher :avoid_changing, :change

def clean_the_database
  # Now clear memcached
  Rails.cache.clear

  # Clear Redis
  REDIS_AUTOCOMPLETE.flushall
  REDIS_GENERAL.flushall
  REDIS_HITS.flushall
  REDIS_KUDOS.flushall
  REDIS_RESQUE.flushall
  REDIS_ROLLOUT.flushall
end

def run_all_indexing_jobs
  %w[main background stats].each do |reindex_type|
    ScheduledReindexJob.perform reindex_type
  end
  Indexer.all.map(&:refresh_index)
end

def create_invalid(*args, **kwargs)
  build(*args, **kwargs).tap do |object|
    object.save!(validate: false)
  end
end
