class SerialWork < ApplicationRecord
  belongs_to :series, touch: true
  belongs_to :work, touch: true
  validates_uniqueness_of :work_id, scope: [:series_id]
  acts_as_list scope: :series

  after_create :adjust_series_visibility
  after_destroy :adjust_series_visibility
  after_destroy :delete_empty_series
  after_create :update_series_index, :update_work_index
  after_destroy :update_series_index, :update_work_index

  scope :in_order, -> { order(:position) }

  # If you add or remove a work from a series, make sure restricted? is still accurate
  def adjust_series_visibility
    self.series.adjust_restricted unless self.series.blank?
  end

  # If you delete a work from a series and it was the last one, delete the series too
  def delete_empty_series
    if self.series.present? && self.series.serial_works.blank?
      self.series.destroy
    end
  end

  # Reindex works added to or removed from a series
  def update_work_index
    work&.enqueue_to_index
  end

  # Ensure series bookmarks are reindexed when a new work is added to a series
  def update_series_index
    return if series.blank?
    series.enqueue_to_index
    IndexQueue.enqueue_ids(Bookmark, series.bookmarks.pluck(:id), :main)
  end

  after_create :update_series_creatorships
  def update_series_creatorships
    return unless work && series

    work.pseuds_after_saving.each do |pseud|
      creatorship = series.creatorships.find_or_initialize_by(pseud: pseud)
      creatorship.approved = true
      creatorship.enable_notifications = true
      creatorship.save
    end
  end
end
