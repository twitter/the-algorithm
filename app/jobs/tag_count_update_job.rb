class TagCountUpdateJob < RedisSetJob
  queue_as :tag_counts

  retry_on ActiveRecord::Deadlocked, attempts: 10
  retry_on ActiveRecord::LockWaitTimeout, attempts: 10

  def self.base_key
    "tag_update"
  end

  def self.job_size
    ArchiveConfig.TAG_UPDATE_JOB_SIZE
  end

  def self.batch_size
    ArchiveConfig.TAG_UPDATE_BATCH_SIZE
  end

  def perform_on_batch(tag_ids)
    Tag.transaction do
      tag_ids.each do |id|
        value = REDIS_GENERAL.get("tag_update_#{id}_value")
        Tag.where(id: id).update_all(taggings_count_cache: value) if value.present?
      end
    end
  end
end
