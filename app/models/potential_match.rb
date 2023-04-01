class PotentialMatch < ApplicationRecord

  # We use "-1" to represent all the requested items matching
  ALL = -1

  CACHE_PROGRESS_KEY = "potential_match_status_for_".freeze
  CACHE_SIGNUP_KEY = "potential_match_signups_for_".freeze
  CACHE_INTERRUPT_KEY = "potential_match_interrupt_for_".freeze
  CACHE_INVALID_SIGNUP_KEY = "potential_match_invalid_signup_for_".freeze

  belongs_to :collection
  belongs_to :offer_signup, class_name: "ChallengeSignup"
  belongs_to :request_signup, class_name: "ChallengeSignup"

protected

  def self.progress_key(collection)
    CACHE_PROGRESS_KEY + "#{collection.id}"
  end

  def self.signup_key(collection)
    CACHE_SIGNUP_KEY + "#{collection.id}"
  end

  def self.interrupt_key(collection)
    CACHE_INTERRUPT_KEY + "#{collection.id}"
  end

  def self.invalid_signup_key(collection)
    CACHE_INVALID_SIGNUP_KEY + "#{collection.id}"
  end

public

  def self.clear!(collection)
    # rapidly delete all potential prompt matches and potential matches
    # WITHOUT CALLBACKS
    pmids = collection.potential_matches.pluck(:id)
    PotentialMatch.where(id: pmids).delete_all
  end

  def self.set_up_generating(collection)
    REDIS_GENERAL.set progress_key(collection), "0.0"
  end

  def self.cancel_generation(collection)
    REDIS_GENERAL.set interrupt_key(collection), "1"
  end

  def self.canceled?(collection)
    REDIS_GENERAL.get(interrupt_key(collection)) == "1"
  end

  @queue = :collection

  # This only works on class methods
  def self.perform(method, *args)
    self.send(method, *args)
  end

  def self.generate(collection)
    Resque.enqueue(self, :generate_in_background, collection.id)
  end

  # Regenerate the potential matches for a given signup
  def self.regenerate_for_signup(signup)
    Resque.enqueue(self, :regenerate_for_signup_in_background, signup.id)
  end

  def self.invalid_signups_for(collection)
    REDIS_GENERAL.smembers(invalid_signup_key(collection))
  end

  def self.clear_invalid_signups(collection)
    REDIS_GENERAL.del invalid_signup_key(collection)
  end

  # The actual method that generates the potential matches for an entire collection
  def self.generate_in_background(collection_id)
    collection = Collection.find(collection_id)

    if collection.challenge.assignments_sent_at.present?
      # If assignments have been sent, we don't want to delete everything and
      # regenerate. (If the challenge moderator wants to recalculate potential
      # matches after sending assignments, they can use the Purge Assignments
      # button.)
      return
    end

    # check for invalid signups
    PotentialMatch.clear_invalid_signups(collection)
    invalid_signup_ids = collection.signups.select {|s| !s.valid?}.collect(&:id)
    unless invalid_signup_ids.empty?
      invalid_signup_ids.each {|sid| REDIS_GENERAL.sadd invalid_signup_key(collection), sid}
      UserMailer.invalid_signup_notification(collection.id, invalid_signup_ids).deliver_later
      PotentialMatch.cancel_generation(collection)
    else

      PotentialMatch.clear!(collection)
      settings = collection.challenge.potential_match_settings

      if settings.no_match_required?
        matcher = PotentialMatcherUnconstrained.new(collection)
      else
        index_type = PromptTagTypeInfo.new(collection).good_index_types.first
        matcher = PotentialMatcherConstrained.new(collection, index_type)
      end

      matcher.generate
    end
    # TODO: for any signups with no potential matches try regenerating?
    PotentialMatch.finish_generation(collection)
  end

  # Generate potential matches for a single signup.
  def self.generate_for_signup(collection, signup, settings, collection_tag_sets, required_types, prompt_type = "request")
    # only check the signups that have any overlap
    match_signup_ids = PotentialMatch.matching_signup_ids(collection, signup, collection_tag_sets, required_types, prompt_type)

    # We randomize the signup ids to make sure potential matches are distributed across all the participants
    match_signup_ids.shuffle.each do |other_signup_id|
      next if signup.id == other_signup_id

      # The "match" method of ChallengeSignup creates and returns a new
      # (unsaved) potential match object. It assumes the signup that is calling
      # is the requesting signup, so if this is meant to be an offering signup
      # instead, we call it from the other signup.
      if prompt_type == "request"
        other_signup = ChallengeSignup.with_offer_tags.find(other_signup_id)
        potential_match = signup.match(other_signup, settings)
      else
        other_signup = ChallengeSignup.with_request_tags.find(other_signup_id)
        potential_match = other_signup.match(signup, settings)
      end

      potential_match.save if potential_match && potential_match.valid?
    end
  end

  # Get the ids of all signups that have some overlap in the tag types required for matching
  def self.matching_signup_ids(collection, signup, collection_tag_sets, required_types, prompt_type = "request")
    matching_signup_ids = []

    if required_types.empty?
      # nothing is required, so any signup can match -- check all of them
      return collection.signups.pluck(:id)
    end

    # get the tagsets used in the signup we are trying to match
    signup_tagsets = signup.send(prompt_type.pluralize).pluck(:tag_set_id, :optional_tag_set_id).flatten.compact

    # get the ids of all the tags of the required types in the signup's tagsets
    signup_tags = SetTagging.where(tag_set_id: signup_tagsets).joins(:tag).where("tags.type IN (?)", required_types).pluck(:tag_id)

    if signup_tags.empty?
      # a match is required by the settings but the user hasn't put any of the required tags in, meaning they are open to anything
      return collection.signups.pluck(:id)
    else
      # now find all the tagsets in the collection that share the original signup's tags
      match_tagsets = SetTagging.where(tag_id: signup_tags, tag_set_id: collection_tag_sets).pluck(:tag_set_id).uniq

      # and now we look up any signups that have one of those tagsets in the opposite position -- ie,
      # if this signup is a request, we are looking for offers with the same tag; if it's an offer, we're
      # looking for requests with the same tag.
      matching_signup_ids = (prompt_type == "request" ? Offer : Request).
                            where("tag_set_id IN (?) OR optional_tag_set_id IN (?)", match_tagsets, match_tagsets).
                            pluck(:challenge_signup_id).compact

      # now add on "any" matches for the required types
      condition = case required_types.first.underscore
                  when "fandom"
                    "any_fandom = 1"
                  when "character"
                    "any_character = 1"
                  when "rating"
                    "any_rating = 1"
                  when "relationship"
                    "any_relationship = 1"
                  when "category"
                    "any_category = 1"
                  when "archive_warning"
                    "any_archive_warning = 1"
                  when "freeform"
                    "any_freeform = 1"
                  else
                    " 1 = 0"
                  end
      matching_signup_ids += collection.prompts.where(condition).pluck(:challenge_signup_id)
    end

    matching_signup_ids.uniq
  end

  # Regenerate potential matches for a single signup within a challenge where (presumably)
  # the other signups already have matches generated.
  # To do this, we have to regenerate its potential matches both as a request and as an offer
  # (instead of just generating them as a request as we do when generating ALL potential matches)
  def self.regenerate_for_signup_in_background(signup_id)
    # The signup will be acting as both offer and request, so we want to load
    # both request tags and offer tags.
    signup = ChallengeSignup.with_request_tags.with_offer_tags.find(signup_id)
    collection = signup.collection

    # Get all the data
    settings = collection.challenge.potential_match_settings
    collection_tag_sets = Prompt.where(collection_id: collection.id).pluck(:tag_set_id, :optional_tag_set_id).flatten.compact
    required_types = settings.required_types.map {|t| t.classify}

    # clear the existing potential matches for this signup in each direction
    signup.offer_potential_matches.destroy_all
    signup.request_potential_matches.destroy_all

    # We check the signup in both directions -- as a request signup and as an offer signup
    %w(request offer).each do |prompt_type|
      PotentialMatch.generate_for_signup(collection, signup, settings, collection_tag_sets, required_types, prompt_type)
    end
  end

  # Finish off the potential match generation
  def self.finish_generation(collection)
    REDIS_GENERAL.del progress_key(collection)
    REDIS_GENERAL.del signup_key(collection)
    if PotentialMatch.canceled?(collection)
      REDIS_GENERAL.del interrupt_key(collection)
      # eventually we'll want to be able to pick up where we left off,
      # but not there yet
      PotentialMatch.clear!(collection)
    else
      ChallengeAssignment.delayed_generate(collection.id)
    end
  end

  def self.in_progress?(collection)
    if REDIS_GENERAL.get(progress_key(collection))
      if PotentialMatch.canceled?(collection)
        self.finish_generation(collection)
        return false
      end
      return true
    end
    false
  end

  # The PotentialMatcherProgress class calculates the percent, so we just need
  # to retrieve it from redis.
  def self.progress(collection)
    REDIS_GENERAL.get(progress_key(collection))
  end

  # sorting routine -- this gets used to rank the relative goodness of potential matches
  include Comparable
  def <=>(other)
    return 0 if self.id == other.id

    # start with seeing how many offers/requests match
    cmp = compare_all(self.num_prompts_matched, other.num_prompts_matched)
    return cmp unless cmp == 0

    # compare the "quality" of the best prompt match
    # (i.e. the number of matching tags between the most closely-matching
    # request prompt/offer prompt pair)
    cmp = compare_all(max_tags_matched, other.max_tags_matched)
    return cmp unless cmp == 0

    # if we're a match down to here just match on id
    return self.id <=> other.id
  end

protected
  def compare_all(self_value, other_value)
    self_value == ALL ? (other_value == ALL ? 0 : 1) : (other_value == ALL ? -1 : self_value <=> other_value)
  end

end
