module Responder
  def update_work_stats
    work = get_work
    return unless work.present?
    REDIS_GENERAL.sadd('works_to_update_stats', work.id)
  end

  def get_work
    work = nil
    if self.respond_to?(:ultimate_parent)
      work = self.ultimate_parent
    elsif self.respond_to?(:commentable)
      work = self.commentable
    elsif self.respond_to?(:bookmarkable)
      work = self.bookmarkable
    end

    work.is_a?(Work) ? work : nil
  end
end

