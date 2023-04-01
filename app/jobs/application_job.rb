class ApplicationJob < ActiveJob::Base
  include AfterCommitEverywhere
  extend AfterCommitEverywhere

  queue_as :utilities

  def self.perform_after_commit(*args, **kwargs)
    after_commit { perform_later(*args, **kwargs) }
  end

  def enqueue_after_commit
    after_commit { enqueue }
  end
end
