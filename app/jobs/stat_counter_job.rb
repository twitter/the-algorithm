class StatCounterJob < RedisSetJob
  queue_as :stats

  def self.base_key
    "works_to_update_stats"
  end

  def self.job_size
    ArchiveConfig.STAT_COUNTER_JOB_SIZE
  end

  def self.batch_size
    ArchiveConfig.STAT_COUNTER_BATCH_SIZE
  end

  def perform_on_batch(work_ids)
    Work.where(id: work_ids).find_each(&:update_stat_counter)
  end
end
