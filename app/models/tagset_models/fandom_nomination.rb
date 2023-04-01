class FandomNomination < TagNomination
  belongs_to :tag_set_nomination
  has_one :owned_tag_set, through: :tag_set_nomination

  has_many :character_nominations, dependent: :destroy, inverse_of: :fandom_nomination
  accepts_nested_attributes_for :character_nominations, allow_destroy: true, reject_if: proc { |attrs| attrs[:tagname].blank? && attrs[:id].blank? }

  has_many :relationship_nominations, dependent: :destroy, inverse_of: :fandom_nomination
  accepts_nested_attributes_for :relationship_nominations, allow_destroy: true, reject_if: proc { |attrs| attrs[:tagname].blank? && attrs[:id].blank? }


  def character_tagnames
    CharacterNomination.for_tag_set(owned_tag_set).where(parent_tagname: tagname).pluck :tagname
  end

  def relationship_tagnames
    RelationshipNomination.for_tag_set(owned_tag_set).where(parent_tagname: tagname).pluck :tagname
  end

  after_save :reject_children, if: :rejected?
  def reject_children
    character_nominations.each {|char| char.rejected = true; char.save}
    relationship_nominations.each {|rel| rel.rejected = true; rel.save}
    true
  end

  after_save :update_child_parent_tagnames, if: Proc.new { |nom| nom.saved_change_to_tagname? }
  def update_child_parent_tagnames
    self.character_nominations.readonly(false).each {|char| char.parent_tagname = self.tagname; char.save}
    self.relationship_nominations.readonly(false).each {|rel| rel.parent_tagname = self.tagname; rel.save}
    true
  end

end
