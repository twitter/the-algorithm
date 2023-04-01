class IndexSweeper

  REDIS = AsyncIndexer::REDIS

  def self.async_cleanup(klass, expected_ids, found_ids)
    deleted_ids = expected_ids.map(&:to_i).select { |id| !found_ids.include?(id) }

    if deleted_ids.any?
      AsyncIndexer.index(klass, deleted_ids, "cleanup")
    end
  end

  def initialize(batch, indexer)
    @batch = batch
    @indexer = indexer
    @success_ids = []
    @rerun_ids = []
  end

  def process_batch
    return if @batch.nil?

    load_errors

    @batch["items"].each do |item|
      process_document(item)
    end

    save_errors

    if @success_ids.present? && @indexer.respond_to?(:handle_success)
      @indexer.handle_success(@success_ids)
    end

    if @rerun_ids.any?
      AsyncIndexer.new(@indexer, "failures").enqueue_ids(
        @indexer.find_elasticsearch_ids(@rerun_ids)
      )
    end
  end

  # Returns a list of all permanent failures associated with the given indexer.
  # Used for testing purposes. (Can be used for diagnostic purposes, as well.)
  def self.permanent_failures(indexer)
    failures = []

    REDIS.hgetall("#{indexer}:failures").each_pair do |id, value|
      JSON.parse(value).each do |info|
        if info["count"] >= 3
          failures << { id.to_s => info["error"] }
        end
      end
    end

    failures
  end

  private

  # Calculate which IDs were included in this batch.
  def batch_ids
    @batch_ids ||= @batch["items"].map { |item| item.values.first["_id"].to_s }
  end

  # Load information about previous errors for all the items in this batch.
  def load_errors
    @errors = REDIS.mapped_hmget("#{@indexer}:failures", *batch_ids)
    @errors.transform_values! { |value| JSON.parse(value || "[]") }
  end

  # Save information about all the errors for all the items in this batch.
  def save_errors
    return unless @errors.present?

    # Clear out the blank errors.
    blank = @errors.select { |_, v| v.blank? }.keys
    REDIS.hdel("#{@indexer}:failures", blank) if blank.present?

    # Save the items with non-blank errors.
    present = @errors.select { |_, v| v.present? }
    present.transform_values!(&:to_json)
    REDIS.mapped_hmset("#{@indexer}:failures", present) if present.present?
  end

  def process_document(item)
    document = item[item.keys.first] # update/index/delete
    id = document["_id"]

    if document["error"]
      if add_error(id, document["error"]) < 3
        @rerun_ids << id
      end
    else
      @errors[id.to_s].clear
      @success_ids << id
    end
  end

  # Add an error for the given document ID. Return the total number of times
  # that error has occurred.
  def add_error(id, error)
    @errors[id.to_s].each do |info|
      next unless info["error"] == error
      return info["count"] += 1
    end

    # The error hasn't been seen before, so we need to add it with a count of 1.
    @errors[id.to_s] << { "error" => error, "count" => 1 }
    1 # we return the count
  end
end
