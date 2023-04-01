# This class represents parent-child relationships between tags
# It should probably be renamed "ChildTagging" and have the flip tagging called "ParentTagging"?
# Also it doesn't need to be polymorphic -- in practice, all the types are Tag
# -- NN 11/2012
# Because I never understand without looking at one in the database: common_tag
# is the child tag and filterable is the parent tag.
# -- Sarken 01/2019
class CommonTagging < ApplicationRecord
  include Wrangleable

  # we need "touch" here so that when a common tagging changes, the tag(s) themselves are updated and
  # they get noticed by the tag sweeper (which then updates their autocomplete data)
  belongs_to :common_tag, class_name: 'Tag', touch: true
  belongs_to :filterable, polymorphic: true, touch: true

  validates_presence_of :common_tag, :filterable, message: "does not exist."
  validates_uniqueness_of :common_tag_id, scope: :filterable_id

  after_create :update_wrangler
  after_create :inherit_parents
  after_create :remove_uncategorized_media

  after_commit :update_search

  def update_wrangler
    unless User.current_user.nil?
      common_tag.update!(last_wrangler: User.current_user)
    end
  end

  # A relationship should inherit its characters' fandoms
  def inherit_parents
    if common_tag.is_a?(Relationship) && filterable.is_a?(Character)
      filterable.fandoms.each do |fandom|
        common_tag.add_association(fandom)
      end
    end
  end

  validate :check_canonical_filterable, :check_compatible_types

  # The parent tag must always be canonical.
  def check_canonical_filterable
    return if filterable.nil? || filterable.canonical
    errors.add(:base, "Parent tag is not canonical.")
  end

  # The child tag must have a type compatible with the parent tag.
  def check_compatible_types
    return if filterable.nil? || common_tag.nil?
    return if filterable.child_types.include?(common_tag.type)

    errors.add(:base, "A tag of type #{filterable.type} cannot have a child " \
                      "of type #{common_tag.type}.")
  end

  # Make sure that our fandoms lose the Uncategorized media as soon as they're
  # assigned to a particular medium.
  def remove_uncategorized_media
    return unless common_tag.is_a?(Fandom) && filterable.is_a?(Media)
    return if common_tag.medias.count < 2
    common_tag.common_taggings.where(filterable: Media.uncategorized).destroy_all
  end

  # If a tag's parent changes, reindex immediately to update unwrangled bins.
  def update_search
    common_tag&.reindex_document
  end

  # Go through all CommonTaggings and destroy the invalid ones.
  def self.destroy_invalid
    includes(:common_tag, :filterable).find_each do |ct|
      valid = ct.valid?

      # Let callers do something on each iteration.
      yield ct, valid if block_given?

      ct.destroy unless valid
    end
  end
end
