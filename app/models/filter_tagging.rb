# This is essentially a mirror of the taggings table as applied to works (right now)
# except with all works connected to canonical tags instead of their synonyms for
# browsing and filtering purposes. Filter = tag, filterable = thing that's been tagged.
#
# Note that this doesn't have very many validations or callbacks -- this is
# because the ONLY class that should be adding/updating FilterTaggings is the
# FilterUpdater, which handles those itself.
class FilterTagging < ApplicationRecord
  belongs_to :filter, class_name: "Tag", inverse_of: :filter_taggings
  belongs_to :filterable, polymorphic: true, inverse_of: :filter_taggings

  validates_uniqueness_of :filter_id, scope: [:filterable_type, :filterable_id]

  after_destroy_commit :expire_caches
  def expire_caches
    return unless filterable_type == "Work"

    CacheMaster.record(filterable_id, "tag", filter_id)
  end

  def self.update_filter_counts_since(date)
    if date
      FilterCount.enqueue_filters(
        FilterTagging.where("created_at > ?", date).distinct.pluck(:filter_id)
      )
    else
      raise "date not set for filter count suspension! very bad!"
    end
  end
end
