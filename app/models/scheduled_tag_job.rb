class ScheduledTagJob
  def self.perform(job_type)
    case job_type
    when 'add_counts_to_queue'
      Tag.where("taggings_count_cache > ?", 40 * (ArchiveConfig.TAGGINGS_COUNT_CACHE_DIVISOR || 1500)).each do |tag|
        tag.async(:update_counts_cache, tag.id)
      end
    end
  end
end
