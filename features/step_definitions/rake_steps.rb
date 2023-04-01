require 'rake'

When /^I run the rake task "(.*?)"$/ do |name|
  Rails.application.load_tasks unless Rake::Task.task_defined?(name)
  task = Rake::Task[name]
  task.invoke

  # As in spec/support/task_example_group.rb, use "invoke" (and re-enable the
  # task and its prerequisites) over "execute" (which doesn't require
  # re-enabling, but doesn't run prerequisites) because it more closely matches
  # the behavior of rake itself.
  task.all_prerequisite_tasks.each { |prerequisite| Rake::Task[prerequisite].reenable }
  task.reenable
end
