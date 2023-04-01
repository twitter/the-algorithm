Otwarchive::Application.configure do
  # Settings specified here will take precedence over those in config/environment.rb

  config.hosts = ArchiveConfig.PERMITTED_HOSTS

  # The production environment is meant for finished, "live" apps.
  # Code is not reloaded between requests
  config.cache_classes = true
  config.eager_load = true

  # Full error reports are disabled and caching is turned on
  config.consider_all_requests_local       = false
  config.action_controller.perform_caching = true
  config.action_mailer.perform_caching     = true

  # Specifies the header that your server uses for sending files
  # config.action_dispatch.x_sendfile_header = "X-Sendfile"

  # For nginx:
  # config.action_dispatch.x_sendfile_header = 'X-Accel-Redirect'

  # If you have no front-end server that supports something like X-Sendfile,
  # just comment this out and Rails will serve the files

  # Disable IP spoofing protection
  config.action_dispatch.ip_spoofing_check = false

  # See everything in the log (default is now :debug)
  # config.log_level = :debug
  config.log_level = :info

  # Use a different logger for distributed setups
  # config.logger = SyslogLogger.new

  # Use a different cache store in production
  config.cache_store = :mem_cache_store, ArchiveConfig.MEMCACHED_SERVERS,
                       { namespace: "ao3-v2", compress: true, pool_size: 5 }

  # Disable Rails's static asset server
  # In production, Apache or nginx will already do this
  config.serve_static_files = false

  # Enable serving of images, stylesheets, and javascripts from an asset server
  # config.action_controller.asset_host = "http://assets.example.com"

  # Disable delivery errors, bad email addresses will be ignored
  # config.action_mailer.raise_delivery_errors = false

  # Enable mailer previews.
  config.action_mailer.show_previews = true

  # Enable threaded mode
  # config.threadsafe!

  # Send deprecation notices to registered listeners
  config.active_support.deprecation = :notify

  # Make it clear we are on staging
  config.rack_dev_mark.enable = true
  config.rack_dev_mark.theme = [:title, Rack::DevMark::Theme::GithubForkRibbon.new(position: "left", fixed: "true", color: "orange")]

  config.after_initialize do
    Bullet.enable = true
    Bullet.bullet_logger = true
    Bullet.add_footer = false
    Bullet.console = true
    Bullet.rails_logger = true
    Bullet.counter_cache_enable = false
  end

  config.middleware.use Rack::Attack
end
