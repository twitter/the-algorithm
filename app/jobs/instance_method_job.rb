# Job for calling an instance method on an object.
class InstanceMethodJob < ApplicationJob
  def perform(object, *args, **kwargs)
    object.send(*args, **kwargs)
  end

  retry_on ActiveJob::DeserializationError, attempts: 3, wait: 5.minutes do
    # silently discard job after 3 failures
  end
end
