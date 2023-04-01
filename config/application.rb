require File.expand_path("boot", __dir__)

require "rails/all"

# If you have a Gemfile, require the gems listed there, including any gems
# you've limited to :test, :development, or :production.
Bundler.require(*Rails.groups)

module Otwarchive
  class Application < Rails::Application
    app_config = YAML.load_file(Rails.root.join("config/config.yml"))
    app_config.merge!(YAML.load_file(Rails.root.join("config/local.yml"))) if File.exist?(Rails.root.join("config/local.yml"))
    ::ArchiveConfig = OpenStruct.new(app_config)

    # Settings in config/environments/* take precedence over those specified here.
    # Application configuration should go into files in config/initializers
    # -- all .rb files in that directory are automatically loaded.

    config.load_defaults "6.0"

    # TODO: Remove in Rails 7.1, where it's false by default.
    config.add_autoload_paths_to_load_path = false

    %w[
      app/models/challenge_models
      app/models/feedback_reporters
      app/models/indexing
      app/models/potential_matcher
      app/models/search
      app/models/tagset_models
      lib
    ].each do |dir|
      config.eager_load_paths << Rails.root.join(dir)
    end

    # I18n validation deprecation warning fix

    I18n.config.enforce_available_locales = false
    I18n.config.available_locales = [
      :en, :af, :ar, :bg, :bn, :ca, :cs, :cy, :da, :de, :el, :es, :fa, :fi,
      :fil, :fr, :he, :hi, :hr, :hu, :id, :it, :ja, :ko, :ky, :lt, :lv, :mk,
      :mr, :ms, :nb, :nl, :pl, :"pt-BR", :"pt-PT", :ro, :ru, :sk, :sl, :sr, :sv,
      :th, :tr, :uk, :vi, :"zh-CN"
    ]

    # Set Time.zone default to the specified zone and make Active Record auto-convert to this zone.
    # Run "rake -D time" for a list of tasks for finding time zone names. Default is UTC.
    config.time_zone = "UTC"

    # The default locale is :en and all translations from config/locales/**/*.rb,yml are auto loaded.
    config.i18n.load_path += Dir[Rails.root.join("config/locales/**/*.{rb,yml}")]
    # config.i18n.default_locale = :de

    # Enable locale fallbacks for I18n (makes lookups for any locale fall back to
    # the I18n.default_locale when a translation can not be found)
    config.i18n.fallbacks = true

    # JavaScript files you want as :defaults (application.js is always included).
    # config.action_view.javascript_expansions[:defaults] = %w(jquery rails)

    # Configure the default encoding used in templates for Ruby 1.9.
    config.encoding = "utf-8"

    config.action_view.automatically_disable_submit_tag = false

    # Configure sensitive parameters which will be filtered from the log file.
    config.filter_parameters += [:content, :password, :terms_of_service_non_production]

    # Disable dumping schemas after migrations.
    config.active_record.dump_schema_after_migration = false

    # Allows belongs_to associations to be optional
    config.active_record.belongs_to_required_by_default = false

    # Keeps updated_at in cache keys
    config.active_record.cache_versioning = false

    # This class is not allowed by deafult when upgrading Rails to 6.0.5.1 patch
    config.active_record.yaml_column_permitted_classes = [ActiveSupport::TimeWithZone, Time, ActiveSupport::TimeZone]

    # handle errors with custom error pages:
    config.exceptions_app = self.routes

    # Bring the log under control
    config.lograge.enabled = true

    # Only send referrer information to ourselves
    config.action_dispatch.default_headers = {
      "Content-Security-Policy" => "frame-ancestors 'self'",
      "Referrer-Policy" => "strict-origin-when-cross-origin",
      "X-Frame-Options" => "SAMEORIGIN",
      "X-XSS-Protection" => "1; mode=block",
      "X-Content-Type-Options" => "nosniff",
      "X-Download-Options" => "noopen",
      "X-Permitted-Cross-Domain-Policies" => "none"
    }

    # Use Resque to run ActiveJobs (including sending delayed mail):
    config.active_job.queue_adapter = :resque

    config.active_model.i18n_customize_full_message = true

    config.action_mailer.default_url_options = { host: ArchiveConfig.APP_HOST }

    # Use "mailer" instead of "mailers" as the Resque queue for emails:
    config.action_mailer.deliver_later_queue_name = :mailer

    config.action_mailer.delivery_method = :smtp
    config.action_mailer.smtp_settings = {
      address: ArchiveConfig.SMTP_SERVER,
      domain: ArchiveConfig.SMTP_DOMAIN,
      port: ArchiveConfig.SMTP_PORT,
      enable_starttls_auto: ArchiveConfig.SMTP_ENABLE_STARTTLS_AUTO,
      openssl_verify_mode: ArchiveConfig.SMTP_OPENSSL_VERIFY_MODE
    }
    if ArchiveConfig.SMTP_AUTHENTICATION
      config.action_mailer.smtp_settings.merge!({
                                                  user_name: ArchiveConfig.SMTP_USER,
                                                  password: ArchiveConfig.SMTP_PASSWORD,
                                                  authentication: ArchiveConfig.SMTP_AUTHENTICATION
                                                })
    end
  end
end
