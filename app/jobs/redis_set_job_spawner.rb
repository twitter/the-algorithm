# An ActiveJob designed to spawn a bunch of subclasses of RedisSetJob, using
# the contents of a Redis set to generate the jobs.
class RedisSetJobSpawner < ApplicationJob
  include RedisScanning

  def perform(job_name, *args, key: nil, redis: nil, job_size: nil, **kwargs)
    job_class = job_name.constantize

    # Read settings from the job class:
    redis ||= job_class.redis
    job_size ||= job_class.job_size
    key ||= job_class.base_key

    # Bail out early if there's nothing to process:
    return if redis.scard(key).zero?

    # Rename the job to a unique name to avoid conflicts when this is called
    # multiple times in a short period:
    spawn_id = redis.incr("job:#{job_name.underscore}:spawn:id")
    spawn_key = "job:#{job_name.underscore}:spawn:#{spawn_id}"
    redis.rename(key, spawn_key)

    # Use sscan to iterate through the set, because it may be large:
    scan_set_in_batches(redis, spawn_key, batch_size: job_size) do |batch|
      # Create a new job, add the objects to it, and enqueue:
      job = job_class.new(*args, **kwargs)
      job.add_to_job(batch)
      job.enqueue

      # Then, once everything is safely enqueued, remove it from the set:
      redis.srem(spawn_key, batch)
    end
  end
end
