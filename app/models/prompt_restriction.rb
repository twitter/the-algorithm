class PromptRestriction < ApplicationRecord
  has_many :owned_set_taggings, as: :set_taggable, dependent: :destroy
  has_many :owned_tag_sets, -> { select("DISTINCT owned_tag_sets.*") }, through: :owned_set_taggings
  has_many :tag_sets, through: :owned_tag_sets

  # note: there is no has_one/has_many association here because this class may or may not
  # be used by many different challenge classes. For convenience, if you use this class in
  # a challenge class, add that challenge class to this list so other coders can see where
  # it is used and how it behaves:
  #
  # challenge/gift_exchange
  #

  # VALIDATION
  %w(fandom_num_required category_num_required rating_num_required character_num_required
    relationship_num_required freeform_num_required archive_warning_num_required
    fandom_num_allowed category_num_allowed rating_num_allowed character_num_allowed
    relationship_num_allowed freeform_num_allowed archive_warning_num_allowed).each do |tag_limit_field|
      validates_numericality_of tag_limit_field, only_integer: true, less_than_or_equal_to: ArchiveConfig.PROMPT_TAGS_MAX, greater_than_or_equal_to: 0
  end

  before_validation :update_allowed_values
  # if anything is required make sure it is also allowed
  def update_allowed_values
    self.url_allowed = true if url_required
    self.description_allowed = true if description_required
    self.title_allowed = true if title_required

    TagSet::TAG_TYPES.each do |tag_type|
      required = eval("#{tag_type}_num_required") || eval("self.#{tag_type}_num_required") || 0
      allowed = eval("#{tag_type}_num_allowed") || eval("self.#{tag_type}_num_allowed") || 0
      if required > allowed
        eval("self.#{tag_type}_num_allowed = required")
      end
    end
  end

  def required(tag_type)
    self.send("#{tag_type}_num_required")
  end

  def allowed(tag_type)
    self.send("#{tag_type}_num_allowed")
  end

  def restricted?(tag_type, restriction)
    self.send("#{tag_type}_restrict_to_#{restriction}")
  end

  def allow_any?(tag_type)
    self.send("allow_any_#{tag_type}")
  end

  def require_unique?(tag_type)
    self.send("require_unique_#{tag_type}")
  end

  def topmost_tag_type
    topmost_type = ""
    TagSet::TAG_TYPES.each do |tag_type|
      if self.allowed(tag_type) > 0
        topmost_type = tag_type
        break
      end
    end
    topmost_type
  end

  def set_owned_tag_sets(sets)
    self.owned_set_taggings.delete_all
    current = self.owned_tag_sets
    new_sets = sets - self.owned_tag_sets
    remove_sets = self.owned_tag_sets - sets
    self.owned_tag_sets += new_sets
    self.owned_tag_sets -= remove_sets
  end

  def tag_sets_to_add=(tag_set_titles)
    tag_set_titles.split(',').reject {|title| title.blank?}.each do |title|
      title.strip!
      ots = OwnedTagSet.find_by(title: title)
      errors.add(:base, ts("We couldn't find the tag set {{title}}.", title: title)) and return if ots.nil?
      errors.add(:base, ts("The tag set {{title}} is not available for public use.", title: title)) and return if (!ots.usable && !ots.user_is_moderator?(User.current_user))
      unless self.owned_tag_sets.include?(ots)
        self.owned_tag_sets << ots
      end
    end
  end

  def tag_sets_to_remove=(tag_set_ids)
    tag_set_ids.reject {|id| id.blank?}.each do |id|
      id.strip!
      ots = OwnedTagSet.find(id) || nil
      if ots && self.owned_tag_sets.include?(ots)
        self.owned_tag_sets -= [ots]
      end
    end
  end

  def tag_sets_to_add; nil; end
  def tag_sets_to_remove; nil; end

  # Efficiently get ids of all tagsets thanks to Valium
  def owned_tag_set_ids
    OwnedSetTagging.where(set_taggable_type: self.class.name, set_taggable_id: self.id).pluck(:owned_tag_set_id)
  end

  def tag_set_ids
    TagSet.joins("INNER JOIN owned_tag_sets ON owned_tag_sets.tag_set_id = tag_sets.id
                  INNER JOIN owned_set_taggings ON owned_set_taggings.owned_tag_set_id = owned_tag_sets.id").
           where("owned_set_taggings.set_taggable_id = ? AND owned_set_taggings.set_taggable_type = 'PromptRestriction'", self.id).pluck :id
  end

  def has_tags?(type="tag")
    tags(type).exists?
  end

  def tags(type="tag")
    type = type.gsub(/\s+/, "").classify
    raise "Redshirt: Attempted to constantize invalid class initialize tags -#{type}-" unless Tag::TYPES.include?(type)
    type.constantize.in_prompt_restriction(self) # Safe constantize checked above
  end

end
