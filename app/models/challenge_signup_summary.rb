class ChallengeSignupSummary

  attr_reader :collection, :challenge

  def initialize(collection)
    @collection = collection
    @challenge = collection.challenge
  end

  ######################################################################
  # CALCULATING INFO FOR THE SUMMARY
  ######################################################################

  # The type of tags to be summarized.
  #
  # For a multi-fandom challenge, this is probably fandom, but for a
  # single-fandom challenge, it will probably be character or relationship (or
  # one of the other tag types, if the challenge doesn't include either).
  #
  # Note that this returns the lowercase tag type.
  def tag_type
    @tag_type = collection.challenge.topmost_tag_type
  end

  # Returns an array of tag listings that includes the number of requests and
  # offers each tag has in this challenge, sorted by least-offered and most-requested
  def summary
    @summary ||= tags.map { |tag| tag_summary(tag) }.compact.sort
  end

  private

  # The class of tags to be summarized. Calls tag_type to retrieve the type.
  def tag_class
    raise "Redshirt: Attempted to constantize invalid class initialize tag_class #{tag_type.classify}" unless Tag::TYPES.include?(tag_type.classify)
    tag_type.classify.constantize
  end

  # All of the tags of the desired type that have been
  # used in requests or offers for this challenge
  def tags
    @tags ||= tag_class.in_challenge(collection)
  end
  
  def tag_summary(tag)
    request_count = Request.in_collection(collection).with_tag(tag).count
    offer_count = Offer.in_collection(collection).with_tag(tag).count

    if request_count > 0
      ChallengeSignupTagSummary.new(tag.id, tag.name, request_count, offer_count)
    end
  end

  public

  ######################################################################
  # GENERATING THE SUMMARY IN RESQUE
  ######################################################################

  @queue = :collection

  # The action to be performed when this class is enqueued using Resque.
  def self.perform(collection_id)
    collection = Collection.find(collection_id)
    summary = ChallengeSignupSummary.new(collection)
    summary.generate_in_background
  end

  # Asynchronously generate the cached version.
  def enqueue_for_generation
    Resque.enqueue(ChallengeSignupSummary, collection.id)
  end

  # Write the summary to Memcached, so that it can be retrieved and displayed.
  def generate_in_background
    locals = {
      challenge_collection: collection,
      tag_type: self.tag_type,
      summary_tags: self.summary,
      generated_live: false
    }

    partial = "challenge/#{self.challenge.class.name.demodulize.tableize.singularize}/challenge_signups_summary"

    self.cached_contents = ChallengeSignupsController.render(partial: partial,
                                                             locals: locals)
  end

  ######################################################################
  # CACHING THE SUMMARY
  ######################################################################

  # Retrieve the value of the cache for this signup summary.
  def cached_contents
    cached_info[:contents]
  end

  # Retrieve the time that the cache was last updated.
  def cached_time
    cached_info[:time]
  end

  # The equivalent of touching a file in the file system. Update the
  # modification time.
  def touch_cache
    data = cached_info.dup
    data[:time] = Time.now
    Rails.cache.write(cache_key, data)
  end

  private

  # Set the value of the cache for this signup summary.
  def cached_contents=(value)
    data = {
      contents: value,
      time: Time.now
    }

    Rails.cache.write(cache_key, data)
  end

  # Retrieve the hash containing cached info: the value being cached (if it
  # exists), and the time that it was updated.
  def cached_info
    Rails.cache.read(cache_key) || {}
  end

  # The key used to store info about the signup summary in memcached.
  def cache_key
    "/v1/challenge_signup_summaries/#{collection.id}"
  end
end

class ChallengeSignupTagSummary < Struct.new(:id, :name, :requests, :offers)

  # Prioritize tags with the fewest offers and most requests
  # If they have the same number of offers and requests, sort by name
  def <=>(other)
    if self.offers == other.offers
      if self.requests == other.requests
        self.name <=> other.name
      else
        other.requests <=> self.requests
      end
    else
      self.offers <=> other.offers
    end
  end

end
