class Tagging < ApplicationRecord
  belongs_to :tagger, polymorphic: true, inverse_of: :taggings, autosave: true
  belongs_to :taggable, polymorphic: true, touch: true, inverse_of: :taggings

  validates_presence_of :tagger, :taggable

  # When we create or destroy a tagging, it may change the taggings count.
  after_create :update_taggings_count
  after_destroy :update_taggings_count
  after_commit :update_search

  after_create :update_filters
  after_destroy :update_filters

  def update_filters
    return unless taggable.is_a?(Filterable)

    taggable.update_filters
  end

  def self.find_by_tag(taggable, tag)
    Tagging.find_by(tagger_id: tag.id, taggable_id: taggable.id, tagger_type: 'Tag', taggable_type: taggable.class.name)
  end

  # Most of the time, we don't need the taggings_count_cache stored in the
  # database to be perfectly accurate. But because of the way Tag.in_use is
  # defined and used, the difference between a value of 0 and a value of 1 is
  # important. So we make sure to poke the taggings_count cache every time we
  # create or destroy a tagging. If it's a large tag, it'll fall back on the
  # cached value. If it's a small tag, it'll recompute -- and make sure that it
  # handles the transition from 0 uses to 1 use properly.
  def update_taggings_count
    tagger.update_tag_cache unless tagger.blank? || tagger.destroyed?
  end

  def update_search
    return unless tagger && Tag::USER_DEFINED.include?(tagger.type)

    # Reindex the tag for updated suggested tags.
    # Suggested tags help wranglers figure out where to wrangle new tags
    # and if it's necessary to disambiguate existing canonical/unfilterable tags
    # in multiple fandoms.
    tagger.enqueue_to_index if tagger.taggings_count < ArchiveConfig.TAGGINGS_COUNT_REINDEX_LIMIT
  end
end
