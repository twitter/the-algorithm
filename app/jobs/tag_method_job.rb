# Subclass of InstanceMethodJob with a custom retry setting that is useful for
# wrangling-related jobs.
class TagMethodJob < InstanceMethodJob
  # Retry if we insert a non-unique record.
  #
  # The jobs for this class mostly already check for uniqueness in validations,
  # so the only way this can be raised is if there are multiple transactions
  # trying to insert the same record at roughly the same time.
  retry_on ActiveRecord::RecordNotUnique,
           attempts: 3, wait: 5.minutes
end
