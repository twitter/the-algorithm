class TagSetNomination < ApplicationRecord
  belongs_to :pseud
  belongs_to :owned_tag_set, inverse_of: :tag_set_nominations
  
  has_many :tag_nominations, dependent: :destroy, inverse_of: :tag_set_nomination
  has_many :fandom_nominations, dependent: :destroy, inverse_of: :tag_set_nomination
  has_many :character_nominations, dependent: :destroy, inverse_of: :tag_set_nomination
  has_many :relationship_nominations, dependent: :destroy, inverse_of: :tag_set_nomination
  has_many :freeform_nominations, dependent: :destroy, inverse_of: :tag_set_nomination

  accepts_nested_attributes_for :fandom_nominations, :character_nominations, :relationship_nominations, :freeform_nominations, {
    allow_destroy: true,
    reject_if: proc { |attrs| attrs[:tagname].blank? && attrs[:id].blank? }
  }
  
  validates_presence_of :owned_tag_set_id
  validates_presence_of :pseud_id

  validates_uniqueness_of :owned_tag_set_id, scope: [:pseud_id], message: ts("You have already submitted nominations for that tag set. Try editing them instead.")
  
  validate :can_nominate
  def can_nominate
    unless owned_tag_set.nominated
      errors.add(:base, ts("%{title} is not currently accepting nominations.", title: owned_tag_set.title))
    end
  end
  
  validate :nomination_limits
  def nomination_limits
    TagSet::TAG_TYPES_INITIALIZABLE.each do |tag_type|    
      limit = self.owned_tag_set.send("#{tag_type}_nomination_limit")
      if count_by_fandom?(tag_type)
        if self.fandom_nominations.any? {|fandom_nom| fandom_nom.send("#{tag_type}_nominations").try(:count) > limit}
          errors.add(:base, ts("You can only nominate %{limit} #{tag_type} tags per fandom.", limit: limit))
        end
      else
        count = self.send("#{tag_type}_nominations").count
        errors.add(:base, ts("You can only nominate %{limit} #{tag_type} tags", limit: limit)) if count > limit
      end
    end 
  end

  # This makes sure a single user doesn't nominate the same tagname twice
  validate :require_unique_tagnames
  def require_unique_tagnames
    noms = self.fandom_nominations + self.freeform_nominations
    if self.fandom_nominations.empty?
      noms += self.character_nominations + self.relationship_nominations
    else
      noms += self.fandom_nominations.collect(&:character_nominations).flatten
      noms += self.fandom_nominations.collect(&:relationship_nominations).flatten
    end
    tagnames = noms.map(&:tagname).reject {|t| t.blank?}
    duplicates = tagnames.group_by {|tagname| tagname}.select {|k,v| v.size > 1}.keys
    errors.add(:base, ts("You seem to be trying to nominate %{duplicates} more than once.", duplicates: duplicates.join(', '))) unless duplicates.empty?
  end

  # Have NONE of the nominations been reviewed?
  def unreviewed?
    TagSet::TAG_TYPES_INITIALIZABLE.each do |tag_type|
      return false if self.send("#{tag_type}_nominations").any? {|tn| tn.reviewed?}
    end
    return true
  end

  # Have ALL the nominations been reviewed?
  def reviewed?
    TagSet::TAG_TYPES_INITIALIZABLE.each do |tag_type|
      return false if self.send("#{tag_type}_nominations").any? {|tn| tn.unreviewed?}
    end
    return true
  end

  def count_by_fandom?(tag_type)
    %w(character relationship).include?(tag_type) && self.owned_tag_set.fandom_nomination_limit > 0
  end
  
  def self.owned_by(user = User.current_user)
    select("DISTINCT tag_set_nominations.*").
    joins(pseud: :user).
    where("users.id = ?", user.id)
  end

  def self.for_tag_set(tag_set)
    where(owned_tag_set_id: tag_set.id)
  end

  def nominated_tags(tag_type = "fandom", index = -1)
    if count_by_fandom?(tag_type)
      if index == -1
        # send ALL the collected char/relationship nominations per fandom
        self.fandom_nominations.collect(&("#{tag_type}_nominations".to_sym)).flatten
      else
        # send just the nominations for this fandom
        self.fandom_nominations[index].send("#{tag_type}_nominations")
      end
    else
       self.send("#{tag_type}_nominations")
    end
  end
  
end
