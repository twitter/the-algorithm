class RelationshipNomination < CastNomination
  belongs_to :tag_set_nomination
  belongs_to :fandom_nomination, inverse_of: :relationship_nominations
  has_one :owned_tag_set, through: :tag_set_nomination
end