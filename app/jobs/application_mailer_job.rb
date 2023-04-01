class ApplicationMailerJob < ActionMailer::MailDeliveryJob
  # TODO: We have a mix of mailers that take ActiveRecords as arguments, and
  # mailers that take IDs as arguments. If an item is unavailable when the
  # notification is sent, it'll produce an ActiveJob::DeserializationError in
  # the former case, and an ActiveRecord::RecordNotFound error in the latter.
  #
  # Ideally, we don't want to catch RecordNotFound errors, because they might
  # be a sign of a different problem. But until we move all of the mailers over
  # to taking ActiveRecords as arguments, we need to catch both errors.

  retry_on ActiveJob::DeserializationError,
           attempts: 3,
           wait: 1.minute do
    # silently discard job after 3 failures
  end

  retry_on ActiveRecord::RecordNotFound,
           attempts: 3,
           wait: 1.minute do
    # silently discard job after 3 failures
  end

  # Retry three times when calling a method on a nil association, and then fall
  # back on the Resque failure queue if the error persists (so that we have a
  # record of it). This should address the issues that arise when we
  # successfully load an object, but can't load its associations because the
  # original object has been deleted in the meantime.
  #
  # (Most errors for calling a method on a nil object will be NoMethodErrors,
  # but any that occur within the template itself will be converted to
  # ActionView::Template:Errors. So we handle both.)
  retry_on NoMethodError, attempts: 3, wait: 1.minute
  retry_on ActionView::Template::Error, attempts: 3, wait: 1.minute
end
