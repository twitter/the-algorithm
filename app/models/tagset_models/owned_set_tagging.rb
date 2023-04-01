class OwnedSetTagging < ApplicationRecord
  belongs_to :owned_tag_set
  belongs_to :set_taggable, polymorphic: :true

  validates_uniqueness_of :owned_tag_set_id, scope: [:set_taggable_id, :set_taggable_type], message: ts("^That tag set is already being used here.")
end
