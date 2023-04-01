class ChallengeSignup < ApplicationRecord
  include TagTypeHelper

  # -1 represents all matching
  ALL = -1

  belongs_to :pseud
  belongs_to :collection

  has_many :prompts, dependent: :destroy, inverse_of: :challenge_signup
  has_many :requests, dependent: :destroy, inverse_of: :challenge_signup
  has_many :offers, dependent: :destroy, inverse_of: :challenge_signup

  has_many :offer_potential_matches, class_name: "PotentialMatch", foreign_key: 'offer_signup_id', dependent: :destroy
  has_many :request_potential_matches, class_name: "PotentialMatch", foreign_key: 'request_signup_id', dependent: :destroy

  has_many :offer_assignments, class_name: "ChallengeAssignment", foreign_key: 'offer_signup_id'
  has_many :request_assignments, class_name: "ChallengeAssignment", foreign_key: 'request_signup_id'

  has_many :request_claims, class_name: "ChallengeClaim", foreign_key: 'request_signup_id'

  before_destroy :clear_assignments_and_claims
  def clear_assignments_and_claims
    # remove this signup reference from any existing assignments
    offer_assignments.each {|assignment| assignment.offer_signup = nil; assignment.save}
    request_assignments.each {|assignment| assignment.request_signup = nil; assignment.save}
    request_claims.each {|claim| claim.destroy}
  end

  # we reject prompts if they are empty except for associated references
  accepts_nested_attributes_for :offers, :prompts, :requests, {allow_destroy: true,
    reject_if: proc { |attrs|
                          attrs[:url].blank? && attrs[:description].blank? &&
                          (attrs[:tag_set_attributes].nil? || attrs[:tag_set_attributes].all? {|k,v| v.blank?}) &&
                          (attrs[:optional_tag_set_attributes].nil? || attrs[:optional_tag_set_attributes].all? {|k,v| v.blank?})
                        }
  }

  scope :by_user, lambda {|user|
    select("DISTINCT challenge_signups.*").
    joins(pseud: :user).
    where('users.id = ?', user.id)
  }

  scope :by_pseud, lambda {|pseud| where('pseud_id = ?', pseud.id) }

  scope :pseud_only, -> { select(:pseud_id) }

  scope :order_by_pseud, -> { joins(:pseud).order("pseuds.name") }

  scope :order_by_date, -> { order("updated_at DESC") }

  scope :in_collection, lambda {|collection| where('challenge_signups.collection_id = ?', collection.id)}

  scope :no_potential_offers, -> { where("id NOT IN (SELECT offer_signup_id FROM potential_matches)") }
  scope :no_potential_requests, -> { where("id NOT IN (select request_signup_id FROM potential_matches)") }

  # Scopes used to include extra data when loading.
  scope :with_request_tags, -> { includes(
    requests: [tag_set: :tags, optional_tag_set: :tags]
  ) }
  scope :with_offer_tags, -> { includes(
    offers: [tag_set: :tags, optional_tag_set: :tags]
  ) }

  ### VALIDATION

  validates_presence_of :pseud, :collection

  # only one signup per challenge!
  validates_uniqueness_of :pseud_id, scope: [:collection_id], message: ts("^You seem to already have signed up for this challenge.")

  # we validate number of prompts/requests/offers at the challenge
  validate :number_of_prompts
  def number_of_prompts
    if (challenge = collection.challenge)
      errors_to_add = []
      %w(offers requests).each do |prompt_type|
        allowed = self.send("#{prompt_type}_num_allowed")
        required = self.send("#{prompt_type}_num_required")
        count = eval("@#{prompt_type}") ? eval("@#{prompt_type}.size") : eval("#{prompt_type}.size")
        unless count.between?(required, allowed)
          if allowed == 0
            errors_to_add << ts("You cannot submit any #{prompt_type.pluralize} for this challenge.")
          elsif required == allowed
            errors_to_add << ts("You must submit exactly %{required} #{required > 1 ? prompt_type.pluralize : prompt_type} for this challenge. You currently have %{count}.",
              required: required, count: count)
          else
            errors_to_add << ts("You must submit between %{required} and %{allowed} #{prompt_type.pluralize} to sign up for this challenge. You currently have %{count}.",
              required: required, allowed: allowed, count: count)
          end
        end
      end
      unless errors_to_add.empty?
        # yuuuuuck :( but so much less ugly than define-method'ing these all
        self.errors.add(:base, errors_to_add.join("</li><li>").html_safe)
      end
    end
  end

  # make sure that tags are unique across each group of prompts
  validate :unique_tags
  def unique_tags
    return unless (challenge = collection.challenge)

    challenge.class::PROMPT_TYPES.each do |prompt_type|
      # requests => request_restriction, offers => offer_restriction
      restriction = challenge.send("#{prompt_type.singularize}_restriction")

      next unless restriction

      prompts = send(prompt_type)

      TagSet::TAG_TYPES.each do |tag_type|
        next unless restriction.require_unique?(tag_type)

        all_tags_used = prompts.flat_map do |prompt|
          prompt.tag_set.send("#{tag_type}_taglist")
        end

        unless all_tags_used.size == all_tags_used.uniq.size
          errors.add(:base, ts("You have submitted more than one %{prompt_type} with the same %{tag_type} tags. This challenge requires them all to be unique.",
                               prompt_type: prompt_type.singularize, tag_type: tag_type_label_name(tag_type).downcase))
        end
      end
    end
  end

  # define "offers_num_allowed" etc here
  %w(offers requests).each do |prompt_type|
    %w(required allowed).each do |permission|
      define_method("#{prompt_type}_num_#{permission}") do
        collection.challenge.respond_to?("#{prompt_type}_num_#{permission}") ? collection.challenge.send("#{prompt_type}_num_#{permission}") : 0
      end
    end
  end

  def can_delete?(prompt)
    prompt_type = prompt.class.to_s.downcase.pluralize
    current_num_prompts = self.send(prompt_type).count
    required_num_prompts = self.send("#{prompt_type}_num_required")
    if current_num_prompts > required_num_prompts
      true
    else
      false
    end
  end

  # sort alphabetically
  include Comparable
  def <=>(other)
    self.pseud.name.downcase <=> other.pseud.name.downcase
  end

  def user
    self.pseud.user
  end

  def user_allowed_to_destroy?(current_user)
    (self.pseud.user == current_user) || self.collection.user_is_maintainer?(current_user)
  end

  def user_allowed_to_see?(current_user)
    (self.pseud.user == current_user) || user_allowed_to_see_signups?(current_user)
  end

  def user_allowed_to_see_signups?(user)
    self.collection.user_is_maintainer?(user) ||
    self.collection.challenge_type == "PromptMeme" ||
      (self.challenge.respond_to?("user_allowed_to_see_signups?") && self.challenge.user_allowed_to_see_signups?(user))
  end

  def byline
    pseud.byline
  end

  # Returns nil if not a match otherwise returns PotentialMatch object
  # self is the request, other is the offer
  def match(other, settings = nil)
    if settings.nil? || settings.no_match_required?
      # No match is required, so everything matches everything, and the best
      # match is perfect.
      return PotentialMatch.new(
        offer_signup: other,
        request_signup: self,
        collection_id: collection_id,
        num_prompts_matched: ALL,
        max_tags_matched: ALL
      )
    end

    builder = PotentialMatchBuilder.new(self, other, settings)

    requests.each do |request|
      other.offers.each do |offer|
        builder.try_prompt_match(request, offer)
      end
    end

    builder.build_potential_match
  end
end
