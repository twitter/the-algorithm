# frozen_string_literal: true

# A module used to make it easy to call asynchronous methods on ActiveRecord
# objects (both on the class and on the instance).
module AsyncWithResque
  extend ActiveSupport::Concern
  include AfterCommitEverywhere

  # The instance version of the async function. Uses perform_on_instance so
  # that we can use the same perform() function for instance methods and class
  # methods.
  def async(method, *args)
    self.class.async(:perform_on_instance, id, method, *args)
  end

  class_methods do
    # The class version of the async function. Uses base_class so that we
    # handle subclasses properly.
    def async(method, *args)
      Resque.enqueue(base_class, method, *args)
    end

    # Actually perform the delayed action.
    def perform(method, *args)
      if method.is_a?(Integer)
        # TODO: For backwards compatibility, if the "method" is an integer, we
        # treat it like an ID and use perform_on_instance instead. But once all
        # of the jobs in the queue have been processed (or deleted), we should
        # be able to remove this check.
        perform_on_instance(method, *args)
      else
        send(method, *args)
      end
    end

    # Find the instance with the given ID, and call the method on it instead.
    # This function allows us to use the same perform() function for both class
    # functions and instance functions.
    def perform_on_instance(id, method, *args)
      find(id).send(method, *args)
    end
  end

  # A function that can be used to help with stale data issues. Instead of
  # immediately adding the desired action to the Resque queue the way that
  # async() does, it uses AfterCommitEverywhere to call async() after the
  # commit has gone through.
  def async_after_commit(*args)
    after_commit do
      async(*args)
    end
  end
end
