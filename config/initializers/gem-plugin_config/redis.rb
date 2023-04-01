require 'redis_test_setup'
include RedisTestSetup

rails_root = ENV["RAILS_ROOT"] || File.dirname(__FILE__) + "/../../.."
rails_env = ENV["RAILS_ENV"] || "development"

unless ENV["CI"]
  if rails_env == "test" && !ENV["OTWA_NO_START_REDIS"]
    # https://gist.github.com/441072
    start_redis!(rails_root, :cucumber)
  end
end

redis_configs = YAML.load_file(rails_root + '/config/redis.yml')
redis_configs.each_pair do |name, redis_config|
  redis_host, redis_port = redis_config[rails_env].split(":")
  redis_connection = Redis.new(host: redis_host, port: redis_port)
  if ENV['DEV_USER']
    namespaced_redis = Redis::Namespace.new(ENV['DEV_USER'], redis: redis_connection)
    redis_connection = namespaced_redis
  end
  Object.const_set(name.upcase, redis_connection)
end
