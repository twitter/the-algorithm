# frozen_string_literal: true

# A class used to generate PotentialMatch objects when the matching is
# unconstrained -- that is, anyone can be assigned to anyone else.
class PotentialMatcherUnconstrained
  ALL = -1

  def initialize(collection, batch_size = 100)
    @collection = collection
    @batch_size = batch_size

    # Set up a new progress object for recording our progress.
    @progress = PotentialMatcherProgress.new(collection)
  end

  # Generates potential match objects for all (valid) pairs of requests and
  # offers from the passed-in lists of ids. Saves time by not loading the
  # offers or requests, because unconstrained challenges always have ALL for
  # num_prompts_matched and max_tags_matched.
  def make_all_matches(request_ids, offer_ids)
    PotentialMatch.transaction do
      request_ids.each do |request_id|
        offer_ids.each do |offer_id|
          next if offer_id == request_id

          PotentialMatch.create(collection: @collection,
                                offer_signup_id: offer_id,
                                request_signup_id: request_id,
                                num_prompts_matched: ALL,
                                max_tags_matched: ALL)
        end
      end
    end
  end

  # Divides the given array into batches using @batch_size.
  def divide_into_batches(array)
    temp = array.dup

    batches = []
    batches << temp.shift(@batch_size) until temp.empty?

    batches
  end

  # Generates all potential matches for the collection, under the assumption
  # that matching isn't constrained (so that everyone can match with everyone
  # else, and all matches are equally good).
  def generate
    all_ids = @collection.signups.pluck(:id)
    batched_ids = divide_into_batches(all_ids)

    @progress.start_subtask(all_ids.size)

    all_ids.each do |request_id|
      break if PotentialMatch.canceled?(@collection)

      batched_ids.each do |offer_ids|
        make_all_matches([request_id], offer_ids)
      end

      @progress.increment
    end

    @progress.end_subtask
  end
end
