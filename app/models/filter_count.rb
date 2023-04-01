class FilterCount < ApplicationRecord
  belongs_to :filter, class_name: 'Tag', inverse_of: :filter_count
  validates_presence_of :filter_id
  validates_uniqueness_of :filter_id

  # "Large" filter counts should be updated less frequently, to reduce strain
  # on the database.
  def self.large
    where("unhidden_works_count > ?",
          ArchiveConfig.LARGE_FILTER_COUNT_THRESHOLD || 1000)
  end

  # Return a relation containing all tags that need FilterCount objects.
  # We only need to cache counts for canonical tags, because non-canonicals
  # don't have associated filter-taggings. And we only need to cache counts for
  # user-defined tags, because those are the only ones whose counts are used
  # and/or displayed.
  def self.filters_needing_counts
    Tag.canonical.where(type: Tag::USER_DEFINED)
  end

  # Set accurate filter counts for all canonical tags
  def self.set_all
    filters_needing_counts.select(:id).find_in_batches do |batch|
      enqueue_filters(batch)
    end
  end

  def self.suspended?
    admin_settings = AdminSetting.current
    admin_settings.suspend_filter_counts?
  end

  ####################
  # DELAYED JOBS
  ####################

  include AsyncWithResque
  @queue = :utilities

  ####################
  # QUEUE MANAGEMENT
  ####################

  REDIS = REDIS_GENERAL
  QUEUE_KEY_SMALL = "filter_count:queue_small".freeze
  QUEUE_KEY_LARGE = "filter_count:queue_large".freeze
  BATCH_SIZE = 1000

  # Queue up a single filter (or filter ID) to have its counts recalculated.
  def self.enqueue_filter(filter)
    enqueue_filters([filter])
  end

  # Queue up a list of filters (or filter IDs) to have their filter counts
  # recalculated in the next periodic task.
  def self.enqueue_filters(filters)
    return if suspended? || filters.blank?

    ids = filters.map do |filter|
      filter.respond_to?(:id) ? filter.id : filter
    end.uniq

    # Separate the large filters from the small filters, so that they can be
    # processed at different intervals.
    large_ids = FilterCount.large.where(filter_id: ids).pluck(:filter_id)
    small_ids = ids - large_ids

    # Add all filters to the appropriate queues.
    REDIS.sadd(QUEUE_KEY_LARGE, large_ids) if large_ids.present?
    REDIS.sadd(QUEUE_KEY_SMALL, small_ids) if small_ids.present?
  end

  # Update counts for small filters.
  def self.update_counts_for_small_queue
    update_counts_for_queue(QUEUE_KEY_SMALL)
  end

  # Update counts for large filters.
  def self.update_counts_for_large_queue
    update_counts_for_queue(QUEUE_KEY_LARGE)
  end

  # Divide the queue up into batches of size BATCH_SIZE, and asynchronously
  # process each batch.
  def self.update_counts_for_queue(queue_key)
    return if suspended? || REDIS.scard(queue_key).zero?

    # Rename to a temporary key to make sure that we don't run into concurrency
    # issues.
    temp_key = "#{queue_key}:#{Time.now.to_i}"
    return unless REDIS.renamenx(queue_key, temp_key)

    while REDIS.scard(temp_key).positive?
      batch = REDIS.spop(temp_key, BATCH_SIZE)

      # Build a separate REDIS set for the next batch to process.
      batch_key = "#{temp_key}:#{batch.first}"
      REDIS.sadd(batch_key, batch)
      async(:update_counts_for_batch, batch_key)
    end

    REDIS.del(temp_key)
  end

  # Given the key for a batch of filter IDs, either update or create the
  # FilterCount objects for those filters.
  def self.update_counts_for_batch(key)
    batch_ids = REDIS.smembers(key).map(&:to_i)

    filters_needing_counts.where(id: batch_ids).each do |filter|
      if filter.filter_count.nil?
        filter.build_filter_count.update_counts
      else
        filter.filter_count.update_counts
      end
    end

    REDIS.del(key)
  end

  ####################
  # COUNT CALCULATION
  ####################

  # Calculate the two counts for this object, and save the results.
  def update_counts
    # Perform a single SQL query to get the counts of restricted and
    # unrestricted works with the desired filter.
    counts = filter.filtered_works.posted.unhidden.group(:restricted).count

    # Retrieve the total number of works with restricted set to false.
    self.public_works_count = counts[false].to_i

    # Combine the restricted/unrestricted totals to get the number visible to
    # logged-in users.
    self.unhidden_works_count = counts.values.sum

    save if changed?
  end
end
