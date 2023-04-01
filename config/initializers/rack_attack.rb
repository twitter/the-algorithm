class Rack::Attack

  # The following is a useful resource.
  # https://www.driftingruby.com/episodes/rails-api-throttling-with-rack-attack
  #
  ### Configure Cache ###

  # If you don't want to use Rails.cache (Rack::Attack's default), then
  # configure it here.
  #
  # Note: The store is only used for throttling (not blocklisting and
  # safelisting). It must implement .increment and .write like
  # ActiveSupport::Cache::Store

  # Rack::Attack.cache.store = ActiveSupport::Cache::MemoryStore.new

  ### Throttle Spammy Clients ###

  # If we fail to unmask the remote IP for a request, the
  # frontends will pass the internal network (10.0.0.0/8) to the
  # unicorns. We need to ensure that we don't block these requests.

  ArchiveConfig.RATE_LIMIT_SAFELIST.each do |ip|
    Rack::Attack.safelist_ip(ip)
  end

  # If any single client IP is making tons of requests, then they're
  # probably malicious or a poorly-configured scraper. Either way, they
  # don't deserve to hog all of the app server's CPU. Cut them off!
  #
  # Note: If you're serving assets through rack, those requests may be
  # counted by rack-attack and this throttle may be activated too
  # quickly. If so, enable the condition to exclude them from tracking.
  #

  # This stanza allows us to limit by which backend is selected by nginx.

  ArchiveConfig.RATE_LIMIT_PER_NGINX_UPSTREAM.each do |k, v|
    throttle("req/#{k}/ip", limit: v["limit"], period: v["period"]) do |req|
      req.ip if req.env['HTTP_X_UNICORNS'] == k
    end
  end

  # Throttle all requests by IP (60rpm)
  #
  # Key: "rack::attack:#{Time.now.to_i/:period}:req/ip:#{req.ip}"
  limit = ArchiveConfig.RATE_LIMIT_NUMBER
  period = ArchiveConfig.RATE_LIMIT_PERIOD
  throttle('req/ip', limit: limit, period: period) do |req|
    req.ip
  end

  ### Prevent Brute-Force Login Attacks ###

  # The most common brute-force login attack is a brute-force password
  # attack where an attacker simply tries a large number of emails and
  # passwords to see if any credentials match.
  #
  # Another common method of attack is to use a swarm of computers with
  # different IPs to try brute-forcing a password for a specific account.

  login_limit = ArchiveConfig.RATE_LIMIT_LOGIN_ATTEMPTS
  login_period = ArchiveConfig.RATE_LIMIT_LOGIN_PERIOD

  # Throttle POST requests to /users/login by IP address
  #
  # Key: "rack::attack:#{Time.now.to_i/:period}:logins/ip:#{req.ip}"
  throttle("logins/ip", limit: login_limit, period: login_period) do |req|
    req.ip if req.path == "/users/login" && req.post?
  end

  # Throttle POST requests to /users/login by login param (user name or email)
  #
  # Key: "rack::attack:#{Time.now.to_i/:period}:logins/email:#{login}"
  #
  # Note: This creates a problem where a malicious user could intentionally
  # throttle logins for another user and force their login requests to be
  # denied, but that's not very common and shouldn't happen to you. (Knock
  # on wood!)
  throttle("logins/email", limit: login_limit, period: login_period) do |req|
    req.params.dig("user", "login").presence if req.path == "/users/login" && req.post?
  end
  
  # Add Retry-After response header to let polite clients know
  # how many seconds they should wait before trying again
  Rack::Attack.throttled_response_retry_after_header = true

  ### Custom Throttle Response ###

  # By default, Rack::Attack returns an HTTP 429 for throttled responses,
  # which is just fine.
  #
  # If you want to return 503 so that the attacker might be fooled into
  # believing that they've successfully broken your app (or you just want to
  # customize the response), then uncomment these lines.
  # self.throttled_response = lambda do |env|
  #  [ 503,  # status
  #    {},   # headers
  #    ['']] # body
  # end
end
