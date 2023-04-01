# This module is included by both the work and chapter models
module WorkChapterCountCaching
  def key_for_chapter_posted_counting(work)
    "/v1/chapters_posted/#{work.id}"
  end

  def key_for_chapter_total_counting(work)
    "/v1/chapters_total/#{work.id}"
  end

  def invalidate_work_chapter_count(work)
    Rails.cache.delete(key_for_chapter_total_counting(work))
    Rails.cache.delete(key_for_chapter_posted_counting(work))
  end
end
