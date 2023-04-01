# RAILS_ROOT/lib/redis_test_setup.rb

module RedisTestSetup

  def start_redis!(rails_root, env)
    dir_temp = File.expand_path(File.join(rails_root, 'log'))
    dir_conf = File.expand_path(File.join(rails_root, 'config'))
    cwd = Dir.getwd
    Dir.chdir(rails_root)
    raise "unable to launch redis-server" unless system("redis-server #{dir_conf}/redis-#{env}.conf")
    Dir.chdir(cwd)
    at_exit do
      if (pid = `cat #{dir_temp}/redis-#{env}.pid`.strip) =~ /^\d+$/
        `rm -f #{dir_temp}/redis-cucumber-#{env}.rdb`
        `rm -f #{dir_temp}/redis-#{env}.pid`
        Process.kill("KILL", pid.to_i)
      end
    end
  end
end
