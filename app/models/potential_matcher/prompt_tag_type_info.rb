# frozen_string_literal: true

# A class used for gathering information about the rough frequency of different
# required tag types in all of the prompts in a collection. Used to figure out
# what would be a good "index type" -- that is, a type that we can use to try
# to look up matches with.
class PromptTagTypeInfo
  # We use a larger batch size here because prompts are smaller than signups
  # (which can contain many prompts), so we can store more of them in memory at
  # the same time.
  def initialize(collection, batch_size = 300)
    @collection = collection
    @batch_size = batch_size

    load_collection_info
    initialize_data_tables
  end

  private

  # Load information about the passed-in collection.
  def load_collection_info
    @total_prompts = @collection.prompts.count
    @total_signups = @collection.signups.count
    @settings = @collection.challenge.potential_match_settings
    @required_types = @settings.required_types

    # Control the number of tags of each type that we want to see, in order to
    # consider the tag type "good." (This is a lower limit -- the more
    # different tags we see, the easier it will be to match on that type.)
    @limit_first_tags = [
      ArchiveConfig.PREPROCESS_COUNT_TAGS_MAX,
      @total_signups / ArchiveConfig.PREPROCESS_COUNT_TAGS_DIVISOR
    ].min

    # Control the number of prompts with any that we want to see for a given
    # type, in order to consider the type "good." (This is an upper limit --
    # the more prompts we see with "any," the harder it will be to match on
    # that type.)
    @limit_prompts_with_any = [
      ArchiveConfig.PREPROCESS_COUNT_ANY_MIN,
      @total_prompts / ArchiveConfig.PREPROCESS_COUNT_ANY_DIVISOR
    ].max
  end

  # Set up @count_prompts_with_any and @first_tags_of_type with the correct
  # defaults.
  def initialize_data_tables
    @count_prompts_with_any = {}
    @first_tags_of_type = {}

    @required_types.each do |type|
      @count_prompts_with_any[type] = 0
      @first_tags_of_type[type] = []
    end
  end

  # A good type to use for indexing has a fair number of tags of that type (so
  # that we aren't, e.g. trying to match prompts on fandom in a single-fandom
  # exchange), and doesn't have very many people requesting "any" (so that we
  # have a lot of constraints on who can match with whom).
  def calculate_good_index_types
    @good_index_types = @required_types.select do |type|
      (@first_tags_of_type[type].size >= @limit_first_tags) &&
        (@count_prompts_with_any[type] <= @limit_prompts_with_any)
    end
  end

  # Read in data from the passed-in prompt, and use it to update our data
  # stored in @first_tags_of_type and @count_prompts_with_any.
  def process_prompt(prompt)
    @required_types.each do |type|
      @count_prompts_with_any[type] += 1 if prompt.send("any_#{type}")

      next if @first_tags_of_type[type].size >= @limit_first_tags

      prompt_tags = prompt.full_tag_set.tag_ids_by_type[type]
      next if prompt_tags.nil?

      @first_tags_of_type[type] += prompt_tags
      @first_tags_of_type[type].uniq!
    end
  end

  # Iterate through all prompts in the collection, and process data from each.
  def build_good_index_types
    return if @required_types.nil? || @required_types.empty?

    prompts = @collection.prompts.includes(
      tag_set: :tags,
      optional_tag_set: :tags
    )

    prompts.find_each(batch_size: @batch_size) do |prompt|
      process_prompt(prompt)
    end

    calculate_good_index_types
  end

  public

  # Builds the list of good index types if necessary; otherwise just returns
  # the pre-calculated list.
  def good_index_types
    build_good_index_types if @good_index_types.nil?
    @good_index_types
  end
end
