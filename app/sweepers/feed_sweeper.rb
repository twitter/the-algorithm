class FeedSweeper < ActionController::Caching::Sweeper
  include Rails.application.routes.url_helpers

  observe Chapter, Work

  def after_create(record)
    if record.posted? && (record.is_a?(Work) || (record.is_a?(Chapter) && record.work.present? && record.work.posted?))
      expire_caches(record)
    end
  end

  def after_update(record)
    if record.posted?
      expire_caches(record)
    end
  end

  private

  # When a chapter or work is created, updated or destroyed, expire:
  # - the cached feed page for each of its canonical tags
  # - the works index caches for its canonical tags, pseuds, users and collections
  def expire_caches(record)
    work = record
    work = record.work if record.is_a?(Chapter)

    return unless work.present?

    work.filters.each do |tag|
      # expire the atom feed page for the tags on the work and the corresponding filter tags
      ActionController::Base.expire_page feed_tag_path(tag.id, format: 'atom')
    end
  end

end
