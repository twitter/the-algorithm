class TagNomination < ApplicationRecord
  belongs_to :tag_set_nomination, inverse_of: :tag_nominations
  has_one :owned_tag_set, through: :tag_set_nomination

  attr_accessor :from_fandom_nomination

  validates_length_of :tagname,
    maximum: ArchiveConfig.TAG_MAX,
    message: ts("^Tag nominations must be between 1 and #{ArchiveConfig.TAG_MAX} characters.")

  validates_format_of :tagname,
    if: Proc.new { |tag_nomination| !tag_nomination.tagname.blank? },
    with: /\A[^,*<>^{}=`\\%]+\z/,
    message: ts("^Tag nominations cannot include the following restricted characters: , &#94; * < > { } = ` \\ %")

  validate :type_validity
  def type_validity
    if !tagname.blank? && (tag = Tag.find_by_name(tagname)) && "#{tag.type}Nomination" != self.type
      errors.add(:base, ts("^The tag %{tagname} is already in the archive as a #{tag.type} tag. (All tags have to be unique.) Try being more specific, for instance tacking on the medium or the fandom.", tagname: self.tagname))
    end
  end

  validate :not_already_reviewed, on: :update
  def not_already_reviewed
    # allow mods and the archive code to update
    unless (!User.current_user || (User.current_user && User.current_user.is_a?(User) && owned_tag_set.user_is_moderator?(User.current_user)))
      if tagname_changed? && (self.approved || self.rejected) && (tagname != tagname_was)  && !tagname_was.blank?
        errors.add(:base, ts("^You cannot change %{tagname_was} to %{tagname} because that nomination has already been reviewed.", tagname_was: self.tagname_was, tagname: self.tagname))
        tagname = self.tagname_was
      end
    end
  end

  # This makes sure no tagnames are nominated for different parents in this tag set
  validate :require_unique_tagname_with_parent
  def require_unique_tagname_with_parent
    query = TagNomination.for_tag_set(get_owned_tag_set).where(tagname: self.tagname).where("parent_tagname != ?", (self.get_parent_tagname || ''))
    # let people change their own!
    query = query.where("tag_nominations.id != ?", self.id) if !(self.new_record?)
    if query.exists?
      other_parent = query.pluck(:parent_tagname).uniq.join(", ") # should only be one but just in case
      errors.add(:base, ts("^Someone else has already nominated the tag %{tagname} for this set but in fandom %{other_parent}. (All nominations have to be unique for the approval process to work.) Try making your nomination more specific, for instance tacking on (%{fandom}).", tagname: self.tagname, other_parent: other_parent, fandom: self.get_parent_tagname || 'Fandom'))
    end
  end

  after_save :destroy_if_blank
  def destroy_if_blank
    if tagname.blank?
      self.destroy
    end
  end

  def get_owned_tag_set
    @tag_set || self.tag_set_nomination.owned_tag_set
  end

  before_save :set_tag_status
  def set_tag_status
    if (tag = Tag.find_by_name(tagname))
      self.exists = true
      self.tagname = tag.name
      self.canonical = tag.canonical
      self.synonym = tag.merger ? tag.merger.name : nil
    else
      self.exists = false
      self.canonical = false
      self.synonym = nil
    end
    true
  end

  before_save :set_parented
  def set_parented
    if type == "FreeformNomination"
      # skip freeforms
      self.parented = true
    elsif (tag = Tag.find_by_name(tagname)) &&
      ((!tag.parents.empty? && get_parent_tagname.blank?) || tag.parents.collect(&:name).include?(get_parent_tagname))
      # if this is an existing tag and has matching parents, or no parent specified and it already has one
      self.parented = true
      self.parent_tagname ||= get_parent_tagname
    else
      self.parented = false
      self.parent_tagname ||= get_parent_tagname
    end
    true
  end

  # sneaky bit: if the tag set moderator has already rejected or approved this tag, don't
  # show it to them again.
  before_save :set_approval_status
  def set_approval_status
    set_noms = tag_set_nomination
    set_noms = fandom_nomination.tag_set_nomination if !set_noms && from_fandom_nomination
    self.rejected = set_noms.owned_tag_set.already_rejected?(tagname) || false
    if self.rejected
      self.approved = false
    elsif synonym && set_noms.owned_tag_set.already_in_set?(synonym)
      self.tagname = synonym
      self.synonym = nil
      self.approved = true
    else
      self.approved = set_noms.owned_tag_set.already_in_set?(tagname) || false
    end
    true
  end

  def self.for_tag_set(tag_set)
    joins(tag_set_nomination: :owned_tag_set).
    where("owned_tag_sets.id = ?", tag_set.id)
  end

  def self.names_with_count
    select("tagname, count(*) as count").group("tagname").order("tagname")
  end

  def self.unreviewed
    where(approved: false).where(rejected: false)
  end

  # returns an array of all the parent tagnames for the given tag
  # can be chained with other queries but must come at the end
  def self.nominated_parents(child_tagname, parent_search_term="")
    parents = where(tagname: child_tagname).where("parent_tagname != ''")
    unless parent_search_term.blank?
      parents = parents.where("parent_tagname LIKE ?", "%#{parent_search_term}%")
    end
    parents.group("parent_tagname").order("count_id DESC").count('id').keys
  end

  # We need this manual join in order to do a query over multiple types of tags
  # (ie, via TagNomination.where(type: ...))
  def self.join_fandom_nomination
    joins("INNER JOIN tag_nominations fandom_nominations_tag_nominations ON
      fandom_nominations_tag_nominations.id = tag_nominations.fandom_nomination_id AND
      fandom_nominations_tag_nominations.type = 'FandomNomination'")
  end

  # Can we change the name to this new name?
  def change_tagname?(new_tagname)
    self.tagname = new_tagname
    if self.valid?
      return true
    else
      return false
    end
  end

  # If the mod is changing our name, change all other noms in this set as well
  def self.change_tagname!(owned_tag_set_to_change, old_tagname, new_tagname)
    TagNomination.for_tag_set(owned_tag_set_to_change).where(tagname: old_tagname).readonly(false).each do |tagnom|
      tagnom.tagname = new_tagname
      Rails.logger.info "Tagnom: #{tagnom.tagname} #{tagnom.valid?}"
      tagnom.save or return false
    end
    return true
  end

  # here so we can override it in char/relationship noms
  def get_parent_tagname
    self.parent_tagname.present? ? self.parent_tagname : nil
  end

  def unreviewed?
    !approved && !rejected
  end

  def reviewed?
    approved || rejected
  end

  def approve!
    self.approved = true
    self.rejected = false
    self.owned_tag_set.tag_set.send("#{self.class.to_s.gsub(/Nomination/,'').downcase}_tagnames_to_add=", self.tagname)
    self.owned_tag_set.tag_set.save
  end

  def times_nominated(tag_set)
    TagNomination.for_tag_set(tag_set).where(tagname: self.tagname).count
  end
end
