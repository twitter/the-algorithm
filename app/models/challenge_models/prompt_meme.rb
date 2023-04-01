class PromptMeme < ApplicationRecord
  PROMPT_TYPES = %w(requests)
  include ChallengeCore

  override_datetime_setters

  belongs_to :collection
  has_one :collection, as: :challenge

  # limits the kind of prompts users can submit
  belongs_to :request_restriction, class_name: "PromptRestriction", dependent: :destroy
  accepts_nested_attributes_for :request_restriction

  validates_length_of :signup_instructions_general, :signup_instructions_requests, {
    allow_blank: true,
    maximum: ArchiveConfig.INFO_MAX, too_long: ts("must be less than %{max} letters long.", max: ArchiveConfig.INFO_MAX)
  }

  PROMPT_TYPES.each do |type|
    %w(required allowed).each do |setting|
      prompt_limit_field = "#{type}_num_#{setting}"
      validates_numericality_of prompt_limit_field, only_integer: true, less_than_or_equal_to: ArchiveConfig.PROMPT_MEME_PROMPTS_MAX, greater_than_or_equal_to: 1
    end
  end

  before_validation :update_allowed_values, :update_allowed_prompts

  # make sure that challenge sign-up / close / open dates aren't contradictory
  validate :validate_signup_dates

  def update_allowed_prompts
    required = self.requests_num_required
    allowed = self.requests_num_allowed
    if required > allowed
      self.requests_num_allowed = required
    end
  end

  #  When Challenges are deleted, there are two references left behind that need to be reset to nil
  before_destroy :clear_challenge_references

  def user_allowed_to_see_signups?(user)
    true
  end

  def user_allowed_to_see_claims?(user)
    user_allowed_to_see_assignments?(user)
  end
end
