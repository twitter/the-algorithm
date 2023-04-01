# A concern defining the async/async_after_commit functions, which allow
# instance methods on an object to be used as ActiveJobs.
module AsyncWithActiveJob
  extend ActiveSupport::Concern

  included do
    class_attribute :async_job_class, default: InstanceMethodJob
  end

  def async(*args, job_class: async_job_class, **kwargs)
    job_class.perform_later(self, *args, **kwargs)
  end

  def async_after_commit(*args, job_class: async_job_class, **kwargs)
    job_class.perform_after_commit(self, *args, **kwargs)
  end
end
