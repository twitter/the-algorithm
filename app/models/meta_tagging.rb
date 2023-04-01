# Relationships between meta and sub tags
# Meta tags represent a superset of sub tags
class MetaTagging < ApplicationRecord
  include Wrangleable

  belongs_to :meta_tag, class_name: 'Tag'
  belongs_to :sub_tag, class_name: 'Tag'

  validates_presence_of :meta_tag, :sub_tag, message: "does not exist."
  validates_uniqueness_of :meta_tag_id,
                          scope: :sub_tag_id,
                          message: "has already been added (possibly as an indirect metatag)."

  after_create :expire_caching
  after_destroy :expire_caching
  after_commit :update_inherited

  validate :meta_tag_validation
  def meta_tag_validation
    if self.meta_tag && self.sub_tag
      unless self.meta_tag.class == self.sub_tag.class
        self.errors.add(:base, "Meta taggings can only exist between two tags of the same type.")
      end
      unless self.meta_tag.canonical? && self.sub_tag.canonical
        self.errors.add(:base, "Meta taggings can only exist between canonical tags.")
      end
      if self.meta_tag == self.sub_tag
        self.errors.add(:base, "A tag can't be its own metatag.")
      end
      if self.meta_tag.meta_taggings.where(meta_tag: self.sub_tag).exists?
        self.errors.add(:base, "A metatag can't be its own grandparent.")
      end
    end
  end

  def update_inherited
    sub_tag.async(:update_inherited_meta_tags) if direct && sub_tag
  end

  def expire_caching
    self.meta_tag&.update_works_index_timestamp!
  end

  # Go through all MetaTaggings and destroy the invalid ones.
  def self.destroy_invalid
    includes(:sub_tag, meta_tag: :meta_tags).find_each do |mt|
      valid = mt.valid?

      # Let callers do something on each iteration.
      yield mt, valid if block_given?

      mt.destroy unless valid
    end
  end
end
