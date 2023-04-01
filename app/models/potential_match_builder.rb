# frozen_string_literal: true

# A class for building PotentialMatch objects by trying to add pairs of
# matching prompts.
class PotentialMatchBuilder
  attr_accessor :num_prompts_matched, :max_tags_matched

  ALL = -1

  def initialize(request, offer, settings)
    @request = request
    @offer = offer
    @settings = settings

    @num_prompts_matched = 0
    @max_tags_matched = 0
  end

  # Check whether the PotentialMatch we're building is valid -- that is,
  # whether it has the required number of prompt matches.
  def valid?
    desired_matches = @settings.num_required_prompts
    desired_matches = @request.requests.size if desired_matches == ALL
    @num_prompts_matched >= desired_matches
  end

  # Check whether the two prompts match, and if so, record information about
  # the match.
  def try_prompt_match(request_prompt, offer_prompt)
    return unless request_prompt.matches?(offer_prompt, @settings)
    add_prompt_match(request_prompt, offer_prompt)
  end

  # Record the fact that the two passed-in prompts match.
  def add_prompt_match(request_prompt, offer_prompt)
    @num_prompts_matched += 1

    # Compute the number of matching tags, and update max_tags_matched if
    # necessary.
    curr_tags_matched = request_prompt.count_tags_matched(offer_prompt)
    @max_tags_matched = [@max_tags_matched, curr_tags_matched].max
  end

  # If possible, create the potential match that we've been building.
  def build_potential_match
    return nil unless valid?

    PotentialMatch.new(
      offer_signup: @offer,
      request_signup: @request,
      collection_id: @offer.collection_id,
      num_prompts_matched: @num_prompts_matched,
      max_tags_matched: @max_tags_matched
    )
  end
end
