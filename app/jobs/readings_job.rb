class ReadingsJob < RedisSetJob
  queue_as :readings

  retry_on ActiveRecord::Deadlocked, attempts: 10
  retry_on ActiveRecord::LockWaitTimeout, attempts: 10

  def self.base_key
    "Reading:new"
  end

  def self.job_size
    ArchiveConfig.READING_JOB_SIZE
  end

  def self.batch_size
    ArchiveConfig.READING_BATCH_SIZE
  end

  def perform_on_batch(batch)
    # Each item in the batch is an array of arguments encoded as a JSON:
    parsed_batch = batch.map do |json|
      ActiveSupport::JSON.decode(json)
    end

    # Sort to try to reduce deadlocks.
    #
    # The first argument is user_id, the third argument is work_id:
    sorted_batch = parsed_batch.sort_by do |args|
      [args.first.to_i, args.third.to_i]
    end

    # Finally, start a transaction and call Reading.reading_object to write the
    # information to the database:
    Reading.transaction do
      sorted_batch.each do |args|
        Reading.reading_object(*args)
      end
    end
  end
end
