class FreeformNomination < TagNomination
  belongs_to :tag_set_nomination #, inverse_of: :freeform_nominations
  has_one :owned_tag_set, through: :tag_set_nomination #, inverse_of: :freeform_nominations
  
end