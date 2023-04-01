class Reading < ApplicationRecord
  belongs_to :user
  belongs_to :work

  after_save :expire_cached_home_marked_for_later, if: :saved_change_to_toread?
  after_destroy :expire_cached_home_marked_for_later, if: :toread?

  # called from show in work controller
  def self.update_or_create(work, user)
    if user && user.preference.try(:history_enabled) && !user.is_author_of?(work)
      reading_json = [user.id, Time.now, work.id, work.major_version, work.minor_version, false].to_json
      REDIS_GENERAL.sadd("Reading:new", reading_json)
    end
  end

  # called from reading controller
  def self.mark_to_read_later(work, user, toread)
    reading = Reading.find_or_initialize_by(work_id: work.id, user_id: user.id)
    reading.major_version_read = work.major_version
    reading.minor_version_read = work.minor_version
    reading.last_viewed = Time.now
    reading.toread = toread
    reading.save
  end

  # create a reading object, but only if the user has reading
  # history enabled and is not the author of the work
  def self.reading_object(user_id, time, work_id, major_version, minor_version, later)
    reading = Reading.find_or_initialize_by(work_id: work_id, user_id: user_id)

    # Only update the view time/version number if it's newer:
    if reading.last_viewed.nil? || reading.last_viewed < time
      reading.last_viewed = time
      reading.major_version_read = major_version
      reading.minor_version_read = minor_version
    end

    reading.view_count = reading.view_count + 1 unless later
    reading.save
    reading
  end

  private

  def expire_cached_home_marked_for_later
    unless Rails.env.development?
      Rails.cache.delete("home/index/#{user_id}/home_marked_for_later")
    end
  end
end
