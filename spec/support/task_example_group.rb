require "rake"

# https://www.eliotsykes.com/test-rails-rake-tasks-with-rspec
# https://github.com/crismali/fantaskspec#rake-testing-gotchas
module TaskExampleGroup
  extend ActiveSupport::Concern

  included do
    let(:task_name) { self.class.top_level_description.sub(/\Arake /, "") }
    subject { Rake::Task[task_name] }

    # Rake tasks can be run by either "invoke" or "execute".
    #
    # "execute" will run the task but not its dependencies.
    # "invoke" will run the task and its dependencies, but the task and its dependencies
    # will be disabled unless we "reenable" them.
    #
    # "invoke" is how Rake behaves on the command line, so let's favor it during tests.
    after do
      subject.all_prerequisite_tasks.each { |prerequisite| Rake::Task[prerequisite].reenable }
      subject.reenable
    end

    it "depends on the :environment task" do
      expect(subject.all_prerequisite_tasks.map(&:name)).to include("environment")
    end
  end
end
