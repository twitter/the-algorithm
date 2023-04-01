# Resque tasks
require 'resque/tasks'
require 'resque/scheduler/tasks'

namespace :resque do
  task :setup do
    require 'resque'
    require 'resque/scheduler/tasks'

    # you probably already have this somewhere
    # Resque.redis = 'localhost:6379'

    # If you want to be able to dynamically change the schedule,
    # uncomment this line.  A dynamic schedule can be updated via the
    # Resque::Scheduler.set_schedule (and remove_schedule) methods.
    # When dynamic is set to true, the scheduler process looks for
    # schedule changes and applies them on the fly.
    # Note: This feature is only available in >=2.0.0.
    Resque::Scheduler.dynamic = true

    # The schedule doesn't need to be stored in a YAML, it just needs to
    # be a hash.  YAML is usually the easiest.
    Resque.schedule = YAML.load_file("#{Rails.root}/config/resque_schedule.yml")

    # If your schedule already has +queue+ set for each job, you don't
    # need to require your jobs.  This can be an advantage since it's
    # less code that resque-scheduler needs to know about. But in a small
    # project, it's usually easier to just include you job classes here.
    # So, something like this:
    # require 'jobs'
  end

  def process_job(count)
    job = Resque::Failure.all(count,1)
    return unless job
    klass = job["payload"]["class"]
    args = job["payload"]["args"]
    klass.constantize.perform(*args)
    Resque::Failure.remove(count)
  rescue ActiveRecord::RecordNotFound
    pp args
    Resque::Failure.remove(count)
  rescue Exception => e
    puts "Job failed with error #{e.message}"
    pp args
  end

  desc "Run jobs in failure queue.
Removes them silently unless there are errors.
If it gets RecordNotFound prints the args to the whenever log.
If there are other exceptions prints out more information
  but does not remove it from the queue.
  These jobs will need to be removed manually."
  task(:run_failures => :environment) do
     (Resque::Failure.count-1).downto(0).each {|i| process_job(i)}
  end

end
