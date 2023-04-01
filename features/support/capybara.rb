require 'capybara/poltergeist'

# Produce a screenshot for each failure.
require 'capybara-screenshot/cucumber'

Capybara.configure do |config|
  # Capybara 1.x behavior.
  config.match = :prefer_exact

  # Increased timeout to minimise failures on CI servers.
  config.default_max_wait_time = 25

  # Capybara 2.x behavior: match rendered text, squish whitespace by default.
  config.default_normalize_ws = true

  # Capybara 3.x changes the default server to Puma; we have WEBRick
  # (a dependency of Mechanize, used for importing; also used for the
  # Rails development server), so we'll stick with that for now.
  config.server = :webrick
end

# Reconfigure poltergeist to block twitter:
Capybara.register_driver :poltergeist do |app|
  Capybara::Poltergeist::Driver.new(
    app, url_blacklist: ["http://platform.twitter.com", "https://platform.twitter.com"]
  )
end

Capybara.default_driver = :rack_test
Capybara.javascript_driver = :poltergeist
