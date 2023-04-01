# frozen_string_literal: true

# Generates potential matches when the matching settings restrict who can be
# matched with whom. Uses PromptBatches to speed up matching. Does not generate
# assignments.
#
# The runtime is asymptotically quadratic in the number of signups, but the
# batching keeps the constants relatively low.
class PotentialMatcherConstrained
  attr_reader :collection, :settings, :batch_size
  attr_reader :index_tag_type, :index_optional

  def initialize(collection, index_tag_type = nil, batch_size = 100)
    @collection = collection
    @settings = collection.challenge.potential_match_settings
    @batch_size = batch_size

    @required_types = @settings.required_types
    @index_tag_type = index_tag_type || @required_types.first
    @index_optional = @settings.include_optional?(@index_tag_type)

    # Set up a new progress object for recording our progress.
    @progress = PotentialMatcherProgress.new(collection)

    # Set up a structure for holding multiple PotentialMatchBuilders.
    @builders = {}
  end

  private

  # Makes a batch for the given set of signups.
  # Passes @index_tag_type and @index_optional to the constructor, so that the
  # prompt batch knows how to build its indices properly.
  def make_batch(signups, prompt_type)
    PromptBatch.new(signups, prompt_type, @index_tag_type, @index_optional)
  end

  # Try matching the two prompts.
  def try_match_prompts(request, offer)
    return unless request.matches?(offer, @settings)

    request_signup = request.challenge_signup
    offer_signup = offer.challenge_signup
    pair_key = "#{request_signup.id}|#{offer_signup.id}"

    @builders[pair_key] ||= PotentialMatchBuilder.new(
      request_signup, offer_signup, @settings
    )

    # We've already checked that the request matches the offer, so we can use
    # add_prompt_match instead of try_prompt_match (to avoid duplicating work).
    @builders[pair_key].add_prompt_match(request, offer)
  end

  # Builds and saves all PotentialMatches using @builders, then clears out the
  # @builders table for the next batch.
  def save_potential_matches
    return if @builders.empty?

    PotentialMatch.transaction do
      @builders.each_value do |builder|
        match = builder.build_potential_match
        match.save unless match.nil?
      end
    end

    @builders.clear
  end

  # Tries to calculate all pairs of matching prompts between the given
  # challenge signup (to be used as a request), and the batch of offers.
  def build_matches_for_request(request_signup, offer_batch)
    request_signup.requests.each do |request|
      offer_candidates = offer_batch.candidates_for_matching(request)

      offer_candidates.each do |offer|
        try_match_prompts(request, offer)
      end
    end
  end

  # Generates (and saves) all PotentialMatches for the given request batch and
  # offer batch.
  def make_batch_matches(request_batch, offer_batch)
    @progress.start_subtask(request_batch.signups.size)

    request_batch.signups.each do |request_signup|
      build_matches_for_request(request_signup, offer_batch)
      save_potential_matches
      @progress.increment
    end

    @progress.end_subtask
  end

  public

  # Generates all potential matches for the collection.
  def generate
    # These two lines won't trigger SQL queries (which is good, because that'd
    # be an awful lot of data to load). They're just defining relations that we
    # can call find_in_batches on.
    offers = @collection.signups.with_offer_tags
    requests = @collection.signups.with_request_tags

    # We process a quadratic number of batch pairs.
    batch_count = 1 + (@collection.signups.count - 1) / @batch_size
    @progress.start_subtask(batch_count * batch_count)

    offers.find_in_batches(batch_size: @batch_size) do |offer_signups|
      break if PotentialMatch.canceled?(@collection)

      offer_batch = make_batch(offer_signups, :offers)

      requests.find_in_batches(batch_size: @batch_size) do |request_signups|
        break if PotentialMatch.canceled?(@collection)

        request_batch = make_batch(request_signups, :requests)
        make_batch_matches(request_batch, offer_batch)

        @progress.increment
      end
    end

    @progress.end_subtask
  end
end
