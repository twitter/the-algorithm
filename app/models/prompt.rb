class Prompt < ApplicationRecord
  include UrlHelpers
  include TagTypeHelper

  # -1 represents all matching
  ALL = -1

  # ASSOCIATIONS

  belongs_to :collection
  belongs_to :pseud
  has_one :user, through: :pseud

  belongs_to :challenge_signup, touch: true, inverse_of: :prompts

  belongs_to :tag_set, dependent: :destroy
  accepts_nested_attributes_for :tag_set
  has_many :tags, through: :tag_set

  belongs_to :optional_tag_set, class_name: "TagSet", dependent: :destroy
  accepts_nested_attributes_for :optional_tag_set
  has_many :optional_tags, through: :optional_tag_set, source: :tag

  has_many :request_claims, class_name: "ChallengeClaim", foreign_key: "request_prompt_id", inverse_of: :request_prompt, dependent: :destroy

  # SCOPES

  scope :claimed, -> { joins("INNER JOIN challenge_claims on prompts.id = challenge_claims.request_prompt_id") }

  scope :in_collection, lambda {|collection| where(collection_id: collection.id) }

  scope :with_tag, lambda { |tag|
    joins("JOIN set_taggings ON set_taggings.tag_set_id = prompts.tag_set_id").
    where("set_taggings.tag_id = ?", tag.id)
  }

  # VALIDATIONS

  before_validation :inherit_from_signup, on: :create, if: :challenge_signup
  def inherit_from_signup
    self.pseud = challenge_signup.pseud
    self.collection = challenge_signup.collection
  end

  validates_presence_of :collection_id

  validates_presence_of :challenge_signup

  # based on the prompt restriction
  validates_presence_of :url, if: :url_required?
  validates_presence_of :description, if: :description_required?
  validates_presence_of :title, if: :title_required?

  delegate :url_required?, :description_required?, :title_required?,
           to: :prompt_restriction, allow_nil: true

  validates_length_of :description,
    maximum: ArchiveConfig.NOTES_MAX,
    too_long: ts("must be less than %{max} letters long.", max: ArchiveConfig.NOTES_MAX)
  validates_length_of :title,
    maximum: ArchiveConfig.TITLE_MAX,
    too_long: ts("must be less than %{max} letters long.", max: ArchiveConfig.TITLE_MAX)

  validates :url, url_format: {allow_blank: true} # we validate the presence above, conditionally

  before_validation :cleanup_url
  def cleanup_url
    self.url = reformat_url(self.url) if self.url
  end

  validate :correct_number_of_tags
  def correct_number_of_tags
    prompt_type = self.class.name
    restriction = prompt_restriction
    if restriction
      # make sure tagset has no more/less than the required/allowed number of tags of each type
      TagSet::TAG_TYPES.each do |tag_type|
        # get the tags of this type the user has specified
        taglist = tag_set ? eval("tag_set.#{tag_type}_taglist") : []
        tag_count = taglist.count
        tag_label = tag_type_label_name(tag_type).downcase

        # check if user has chosen the "Any" option
        if self.send("any_#{tag_type}")
          if tag_count > 0
            errors.add(:base, ts("^You have specified tags for %{tag_label} in your %{prompt_type} but also chose 'Any,' which will override them! Please only choose one or the other.",
                                tag_label: tag_label, prompt_type: prompt_type))
          end
          next
        end

        # otherwise let's make sure they offered the right number of tags
        required = eval("restriction.#{tag_type}_num_required")
        allowed = eval("restriction.#{tag_type}_num_allowed")
        unless tag_count.between?(required, allowed)
          taglist_string = taglist.empty? ?
              ts("none") :
              "(#{tag_count}) -- " + taglist.collect(&:name).join(ArchiveConfig.DELIMITER_FOR_OUTPUT)
          if allowed == 0
            errors.add(:base, ts("^#{prompt_type}: Your #{prompt_type} cannot include any #{tag_label} tags, but you have included %{taglist}.",
              taglist: taglist_string))
          elsif required == allowed
            errors.add(:base, ts("^#{prompt_type}: Your #{prompt_type} must include exactly %{required} #{tag_label} tags, but you have included #{tag_count} #{tag_label} tags in your current #{prompt_type}.",
              required: required))
          else
            errors.add(:base, ts("^#{prompt_type}: Your #{prompt_type} must include between %{required} and %{allowed} #{tag_label} tags, but you have included #{tag_count} #{tag_label} tags in your current #{prompt_type}.",
              required: required, allowed: allowed))
          end
        end
      end
    end
  end

  # make sure that if there is a specified set of allowed tags, the user's choices
  # are within that set, or otherwise canonical
  validate :allowed_tags
  def allowed_tags
    restriction = prompt_restriction
    if restriction && tag_set
      TagSet::TAG_TYPES.each do |tag_type|
        # if we have a specified set of tags of this type, make sure that all the
        # tags in the prompt are in the set.
        if TagSet::TAG_TYPES_RESTRICTED_TO_FANDOM.include?(tag_type) && restriction.send("#{tag_type}_restrict_to_fandom")
          # skip the check, these will be tested in restricted_tags below
        elsif restriction.has_tags?(tag_type)
          disallowed_taglist = tag_set.send("#{tag_type}_taglist") - restriction.tags(tag_type)
          unless disallowed_taglist.empty?
            errors.add(:base, ts("^These %{tag_label} tags in your %{prompt_type} are not allowed in this challenge: %{taglist}",
              tag_label: tag_type_label_name(tag_type).downcase,
              prompt_type: self.class.name.downcase,
              taglist: disallowed_taglist.collect(&:name).join(ArchiveConfig.DELIMITER_FOR_OUTPUT)))
          end
        else
          noncanonical_taglist = tag_set.send("#{tag_type}_taglist").reject {|t| t.canonical}
          unless noncanonical_taglist.empty?
            errors.add(:base, ts("^These %{tag_label} tags in your %{prompt_type} are not canonical and cannot be used in this challenge: %{taglist}. To fix this, please ask your challenge moderator to set up a tag set for the challenge. New tags can be added to the tag set manually by the moderator or through open nominations.",
              tag_label: tag_type_label_name(tag_type).downcase,
              prompt_type: self.class.name.downcase,
              taglist: noncanonical_taglist.collect(&:name).join(ArchiveConfig.DELIMITER_FOR_OUTPUT)))
          end
        end
      end
    end
  end

  # make sure that if any tags are restricted to fandom, the user's choices are
  # actually in the fandom they have chosen.
  validate :restricted_tags
  def restricted_tags
    restriction = prompt_restriction
    if restriction
      TagSet::TAG_TYPES_RESTRICTED_TO_FANDOM.each do |tag_type|
        if restriction.send("#{tag_type}_restrict_to_fandom")
          # tag_type is one of a set set so we know it is safe for constantize
          allowed_tags = tag_type.classify.constantize.with_parents(tag_set.fandom_taglist).canonical
          disallowed_taglist = tag_set ? eval("tag_set.#{tag_type}_taglist") - allowed_tags : []
          # check for tag set associations
          disallowed_taglist.reject! {|tag| TagSetAssociation.where(tag_id: tag.id, parent_tag_id: tag_set.fandom_taglist).exists?}
          unless disallowed_taglist.empty?
            errors.add(:base, ts("^These %{tag_label} tags in your %{prompt_type} are not in the selected fandom(s), %{fandom}: %{taglist} (Your moderator may be able to fix this.)",
                              prompt_type: self.class.name.downcase,
                              tag_label: tag_type_label_name(tag_type).downcase, fandom: tag_set.fandom_taglist.collect(&:name).join(ArchiveConfig.DELIMITER_FOR_OUTPUT),
                              taglist: disallowed_taglist.collect(&:name).join(ArchiveConfig.DELIMITER_FOR_OUTPUT)))
          end
        end
      end
    end
  end

  # INSTANCE METHODS

  def can_delete?
    if challenge_signup && !challenge_signup.can_delete?(self)
      false
    else
      true
    end
  end

  def unfulfilled_claims
    self.request_claims.unfulfilled_in_collection(self.collection)
  end

  def fulfilled_claims
    self.request_claims.fulfilled
  end

  # Computes the "full" tag set (tag_set + optional_tag_set), and stores the
  # result as an instance variable for speed. This is used by the matching
  # algorithm, which doesn't change any signup/prompt/tagset information, so
  # it's okay to cache some information. (And if the info does change
  # mid-matching process, it's okay that we're using the tag sets that were
  # there when the moderator started the matching process.)
  def full_tag_set
    if @full_tag_set.nil?
      @full_tag_set = optional_tag_set ? tag_set + optional_tag_set : tag_set
    end

    @full_tag_set
  end

  # Returns true if there's a match, false otherwise.
  # self is the request, other is the offer
  def matches?(other, settings = nil)
    return nil if challenge_signup.id == other.challenge_signup.id
    return nil if settings.nil?

    TagSet::TAG_TYPES.each do |type|
      # We definitely match in this type if the request or the offer accepts
      # "any" for it. No need to check any more info for this type.
      next if send("any_#{type}") || other.send("any_#{type}")

      required_count = settings.send("num_required_#{type.pluralize}")
      match_count = if settings.send("include_optional_#{type.pluralize}")
                      full_tag_set.match_rank(other.full_tag_set, type)
                    else
                      # we don't use optional tags to count towards required
                      tag_set.match_rank(other.tag_set, type)
                    end

      # if we have to match all and don't, not a match
      return false if required_count == ALL && match_count != ALL

      # we are a match only if we either match all or at least as many as required
      return false if match_count != ALL && match_count < required_count
    end

    true
  end

  # Count the number of overlapping tags of all types. Does not use ALL to
  # indicate a 100% match, since the goal is to give a bonus to matches where
  # both requester and offerer were specific about their desires, and had a lot
  # of overlap.
  def count_tags_matched(other)
    self_tags = full_tag_set.tags.map(&:id)
    other_tags = other.full_tag_set.tags.map(&:id)
    (self_tags & other_tags).size
  end

  def accepts_any?(type)
    send("any_#{type.downcase}")
  end

  def prompt_restriction
    raise "Base-type Prompt objects cannot have prompt restrictions. Try creating a Request or an Offer."
  end

  # tag groups
  def tag_groups
    self.tag_set ? self.tag_set.tags.group_by { |t| t.type.to_s } : {}
  end

  def claim_by(user)
    ChallengeClaim.where(request_prompt_id: self.id, claiming_user_id: user.id)
  end

  # checks if a prompt has been filled in a prompt meme
  def unfulfilled?
    if self.request_claims.empty? || !self.request_claims.fulfilled.exists?
      return true
    end
  end

  # currently only prompt meme prompts can be claimed, and by any number of people
  def claimable?
    if self.collection.challenge.is_a?(PromptMeme)
      true
    else
      false
    end
  end
end
