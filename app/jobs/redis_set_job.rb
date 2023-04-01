# An abstract class designed to make it easier to queue up jobs with a Redis
# set, then split those jobs into chunks to process them.
class RedisSetJob < ApplicationJob
  include RedisScanning

  # The redis server used for this job.
  def self.redis
    REDIS_GENERAL
  end

  # The default key for the Redis set that we want to process. Used by the
  # RedisSetJobSpawner.
  def self.base_key
    raise "Must be implemented in subclass!"
  end

  # The number of items we'd like to have in a single job. Used by the
  # RedisSetJobSpawner.
  def self.job_size
    1000
  end

  # The number of items to process in a single call to perform_on_batch. This
  # should be smaller than job_size, otherwise it'll just use job_size for the
  # batch size.
  def self.batch_size
    100
  end

  def perform(*args, **kwargs)
    # Use sscan to iterate through the objects for this job:
    scan_set_in_batches(redis, key, batch_size: batch_size) do |batch|
      perform_on_batch(batch, *args, **kwargs)
      redis.srem(key, batch)
    end
  end

  # This is where the real work happens:
  def perform_on_batch(*)
    raise "Must be implemented in subclass!"
  end

  # The Redis key used to store the objects that this job needs to process:
  def key
    @key ||= "job:#{self.class.name.underscore}:batch:#{job_id}"
  end

  # Add items to be processed when this job runs:
  def add_to_job(batch)
    redis.sadd(key, batch)
  end

  delegate :redis, :job_size, :batch_size, to: :class
end
