class OwnedTagSet < ApplicationRecord
  # Rather than use STI or polymorphic associations, since really what we want to do here
  # is build an extra layer of functionality on top of the generic tag set structure,
  # I've gone with creating a separate model and making it contain a generic tag set
  # as a member. This way we don't have to duplicate the tag set code or functionality
  # and can just add on the extra stuff without cramming the tag set table full of empty
  # unused fields and having the controller have to sift out all the generic tag sets
  # being used in prompts.
  # -- NN May 2011

  belongs_to :tag_set, dependent: :destroy
  accepts_nested_attributes_for :tag_set

  # delegate the tag set commands
  delegate :tags, :with_type, :has_type?, to: :tag_set, allow_nil: true

  has_many :tag_set_associations, dependent: :destroy
  accepts_nested_attributes_for :tag_set_associations, allow_destroy: true,
    reject_if: proc { |attrs| !attrs[:create_association] || attrs[:tag_id].blank? || (attrs[:parent_tag_id].blank? && attrs[:parent_tagname].blank?) }

  has_many :tag_set_nominations, dependent: :destroy
  has_many :tag_nominations, through: :tag_set_nominations, dependent: :destroy
  has_many :fandom_nominations, through: :tag_set_nominations
  has_many :character_nominations, through: :tag_set_nominations
  has_many :relationship_nominations, through: :tag_set_nominations
  has_many :freeform_nominations, through: :tag_set_nominations

  has_many :tag_set_ownerships, dependent: :destroy
  has_many :moderators, -> { where('tag_set_ownerships.owner = ?', false) }, through: :tag_set_ownerships, source: :pseud
  has_many :owners, -> { where('tag_set_ownerships.owner = ?', true) }, through: :tag_set_ownerships, source: :pseud

  has_many :owned_set_taggings, dependent: :destroy
  has_many :set_taggables, through: :owned_set_taggings

  validates_presence_of :title, message: ts("^Please enter a title for your tag set.")
  validates :title, uniqueness: { message: ts("^Sorry, that name is already taken. Try again, please!") }
  validates_length_of :title,
    minimum: ArchiveConfig.TITLE_MIN,
    too_short: ts("must be at least %{min} characters long.", min: ArchiveConfig.TITLE_MIN)
  validates_length_of :title,
    maximum: ArchiveConfig.TITLE_MAX,
    too_long: ts("must be less than %{max} characters long.", max: ArchiveConfig.TITLE_MAX)
  validates_format_of :title,
    with: /\A[^,*<>^{}=`\\%]+\z/,
    message: '^The title of a tag set cannot include the following restricted characters: , &#94; * < > { } = ` \\ %'

  validates_length_of :description,
    allow_blank: true,
    maximum: ArchiveConfig.SUMMARY_MAX,
    too_long: ts("must be less than %{max} characters long.", max: ArchiveConfig.SUMMARY_MAX)

  validates_numericality_of :fandom_nomination_limit, :character_nomination_limit, :relationship_nomination_limit, :freeform_nomination_limit,
    only_integer: true, less_than_or_equal_to: 20, greater_than_or_equal_to: 0,
    message: ts('must be an integer between 0 and 20.')


  after_update :cleanup_outdated_associations
  def cleanup_outdated_associations
    tag_ids = SetTagging.where(tag_set_id: self.tag_set.id).collect(&:tag_id)
    TagSetAssociation.where(owned_tag_set_id: self.id).where("tag_id NOT IN (?) OR parent_tag_id NOT IN (?)", tag_ids, tag_ids).delete_all
  end

  validate :no_midstream_nomination_changes
  def no_midstream_nomination_changes
    if !self.tag_set_nominations.empty? &&
      %w(fandom_nomination_limit character_nomination_limit relationship_nomination_limit freeform_nomination_limit).any? {|field| self.changed.include?(field)}
      errors.add(:base, ts("You cannot make changes to nomination settings when nominations already exist. Please review and delete existing nominations first."))
    end
  end

  def add_tagnames(tag_type, tagnames_to_add)
    return true if tagnames_to_add.blank?
    self.tag_set.send("#{tag_type}_tagnames_to_add=", tagnames_to_add)
    return false unless self.tag_set.save && self.save

    # update the nominations -- approve any where an approved tag was either a synonym or the tag itself
    TagNomination.for_tag_set(self).where(type: "#{tag_type.classify}Nomination").where("tagname IN (?)", tagnames_to_add).where(rejected: false).update_all(approved: true)
    true
  end

  def remove_tagnames(tag_type, tagnames_to_remove)
    return true if tagnames_to_remove.blank?
    self.tag_set.tagnames_to_remove = tagnames_to_remove.join(',')
    return false unless self.save
    TagNomination.for_tag_set(self).where(type: "#{tag_type.classify}Nomination").where("tagname IN (?)", tagnames_to_remove).where(approved: false).update_all(rejected: true)

    if tag_type == "fandom"
      # reject children of rejected fandom
      TagNomination.for_tag_set(self).where(type: "#{tag_type.classify}Nomination").where("tagname IN (?)", tagnames_to_remove).each do |rejected_fandom_nom|
        rejected_fandom_nom.reject_children
      end
    end

    true
  end

  def self.owned_by(user = User.current_user)
    if user.is_a?(User)
      select("DISTINCT owned_tag_sets.*").
      joins("INNER JOIN tag_set_ownerships ON owned_tag_sets.id = tag_set_ownerships.owned_tag_set_id
             INNER JOIN pseuds ON tag_set_ownerships.pseud_id = pseuds.id
             INNER JOIN users ON pseuds.user_id = users.id").
      where("users.id = ?", user.id)
    end
  end

  def self.visible(user = User.current_user)
    if user.is_a?(User)
      select("DISTINCT owned_tag_sets.*").
      joins("INNER JOIN tag_set_ownerships ON owned_tag_sets.id = tag_set_ownerships.owned_tag_set_id
             INNER JOIN pseuds ON tag_set_ownerships.pseud_id = pseuds.id
             INNER JOIN users ON pseuds.user_id = users.id").
      where("owned_tag_sets.visible = true OR users.id = ?", user.id)
    else
      where("owned_tag_sets.visible = true")
    end
  end

  def self.usable(user = User.current_user)
    if user.is_a?(User)
      select("DISTINCT owned_tag_sets.*").
      joins("INNER JOIN tag_set_ownerships ON owned_tag_sets.id = tag_set_ownerships.owned_tag_set_id
             INNER JOIN pseuds ON tag_set_ownerships.pseud_id = pseuds.id
             INNER JOIN users ON pseuds.user_id = users.id").
      where("owned_tag_sets.usable = true OR users.id = ?", user.id)
    else
      where("owned_tag_sets.usable = true")
    end
  end

  def self.in_prompt_restriction(restriction)
    joins(:owned_set_taggings).
    where("owned_set_taggings.set_taggable_type = ?", restriction.class.to_s).
    where("owned_set_taggings.set_taggable_id = ?", restriction.id)
  end

  #### MODERATOR/OWNER

  def user_is_owner?(user)
    user.is_a?(User) && !(owners & user.pseuds).empty?
  end

  def user_is_moderator?(user)
    user.is_a?(User) && (user_is_owner?(user) || !(moderators & user.pseuds).empty?)
  end

  def add_owner(pseud)
    tag_set_ownerships.build({pseud: pseud, owner: true})
  end

  def add_moderator(pseud)
    tag_set_ownerships.build({pseud: pseud, owner: false})
  end

  def owner_changes=(pseud_list)
    Pseud.parse_bylines(pseud_list)[:pseuds].each do |pseud|
      if self.owners.include?(pseud)
        self.owners -= [pseud] if self.owners.count > 1
      else
        self.moderators -= [pseud] if self.moderators.include?(pseud)
        add_owner(pseud)
      end
    end
  end

  def moderator_changes=(pseud_list)
    Pseud.parse_bylines(pseud_list)[:pseuds].each do |pseud|
      if self.moderators.include?(pseud)
        self.moderators -= [pseud]
      else
        add_moderator(pseud) unless self.owners.include?(pseud)
      end
    end
  end

  def owner_changes; nil; end
  def moderator_changes; nil; end

  ##### MANAGING NOMINATIONS

  # we can use redis to speed this up since tagset data is loaded there for autocomplete
  def already_in_set?(tagname)
    tags_in_set.where("tags.name = ?", tagname).exists?
  end

  # returns an array of arrays [id, name]
  def tags_in_set
    Tag.joins(:set_taggings).where("set_taggings.tag_set_id = ?", self.tag_set.id)
  end

  def already_nominated?(tagname)
    TagNomination.joins(tag_set_nomination: :owned_tag_set).where("tag_set_nominations.owned_tag_set_id = ?", self.id).exists?(tagname: tagname)
  end

  def already_rejected?(tagname)
    TagNomination.joins(tag_set_nomination: :owned_tag_set).where("tag_set_nominations.owned_tag_set_id = ?", self.id).exists?(tagname: tagname, rejected: true)
  end

  def already_approved?(tagname)
    TagNomination.joins(tag_set_nomination: :owned_tag_set).where("tag_set_nominations.owned_tag_set_id = ?", self.id).exists?(tagname: tagname, approved: true)
  end

  def clear_nominations!
    TagSetNomination.where(owned_tag_set_id: self.id).delete_all
  end


  ##### MANAGING ASSOCIATIONS

  def associations_to_remove=(assoc_ids)
    TagSetAssociation.for_tag_set(self).where(id: assoc_ids).delete_all
  end

  def load_batch_associations!(batch_associations, options = {})
    options.reverse_merge!({do_relationships: false})
    association_lines = batch_associations.split("\n")
    fandom_tagnames_to_add = []
    child_tagnames_to_add = []
    assocs_to_save = []
    failed = []
    canonical = []

    association_lines.each do |line|
      children_names = line.split(',')
      parent_name = children_names.shift.strip
      parent_tag_id = Fandom.where(name: parent_name).pluck(:id).first
      unless parent_tag_id
        failed << line
        next
      end
      failed_children = []
      added_parent = false
      children_names.map {|c| c.strip}.each_with_index do |child_name|
        child_tag_id = (options[:do_relationships] ? Relationship : Character).where(name: child_name).pluck(:id).first
        unless child_tag_id
          failed_children << child_name
          next
        end
        assoc = tag_set_associations.build(tag_id: child_tag_id, parent_tag_id: parent_tag_id)
        unless assoc.valid?
          failed_children << child_name
          next
        end
        assocs_to_save << assoc
        child_tagnames_to_add << child_name
        fandom_tagnames_to_add << parent_name unless added_parent
        added_parent = true
      end
      failed << "#{parent_name},#{failed_children.join(',')}" unless failed_children.empty?
    end

    # add the tags to the set
    tag_set.fandom_tagnames_to_add = fandom_tagnames_to_add
    tag_set.send (options[:do_relationships] ? :relationship_tagnames_to_add= : :character_tagnames_to_add=), child_tagnames_to_add

    if tag_set.save
      # save the associations
      assocs_to_save.each {|assoc| assoc.save}
    else
      # whoops, nothing worked
      failed = association_lines
    end

    return failed
  end

  # Turn our various tag nomination limits into a single hash object
  def limits
    limit_hash = {}
    %w(fandom character relationship freeform).each do |tag_type|
      limit_hash[tag_type] = send("#{tag_type}_nomination_limit")
    end
    limit_hash.with_indifferent_access
  end

  def includes_fandoms?
    fandom_nomination_limit > 0
  end
end
