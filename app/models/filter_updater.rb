# frozen_string_literal: true

# A class for calculating the filters that should be on a particular
# filterable, and updating the FilterTaggings to match.
#
# Operates in bulk to try to work faster.
class FilterUpdater
  include AfterCommitEverywhere

  attr_reader :klass, :type, :ids, :reindex_queue

  # Takes as argument the type of filterable that we're modifying, the list of
  # IDs of filterables that we're modifying, and the priority of the current
  # change. (The priority, reindex_queue, is not used in this class. It's just
  # passed along to the filterable class through reindex_for_filter_changes.)
  def initialize(type, ids, reindex_queue)
    @type = type.to_s
    @ids = ids.to_a
    @reindex_queue = reindex_queue

    @klass = [Work, ExternalWork, Collection].find { |klass| klass.to_s == @type }

    raise "FilterUpdater type '#{type}' not allowed." unless @klass

    # A list for storing all of the FilterTaggings that we end up creating,
    # modifying, or deleting. That way, we have a better idea of what we need
    # to reindex.
    @modified = []
  end

  ########################################
  # DELAY CALCULATIONS WITH RESQUE
  ########################################

  # Put this object on the Resque queue so that update will be called later.
  def async_update(job_queue: :utilities)
    Resque.enqueue_to(job_queue, self.class, type, ids, reindex_queue)
  end

  # Perform for Resque.
  def self.perform(type, ids, reindex_queue)
    FilterUpdater.new(type, ids, reindex_queue).update
  end

  ########################################
  # COMPUTE INFO
  ########################################

  # Calculate what the filters should be for every valid item in the batch, and
  # updates the existing FilterTaggings to match.
  def update
    FilterTagging.transaction do
      load_info

      filter_taggings_by_id = FilterTagging.where(
        filterable_type: type, filterable_id: @valid_item_ids
      ).group_by(&:filterable_id)

      @valid_item_ids.each do |id|
        update_filters_for_item(id, filter_taggings_by_id[id] || [])
      end
    end

    # Even if we're inside a nested transaction, the asynchronous steps
    # required by reindexing should always take place outside of the
    # transaction.
    after_commit { reindex_changed }
  end

  private

  # Calculate what the filters should be for a particular item, and perform the
  # updates needed to ensure that those are the current filter taggings. Takes
  # as argument the ID of the item to update, and the list of filter_taggings
  # for that item.
  def update_filters_for_item(item_id, filter_taggings)
    missing_direct = Set.new(@direct_filters[item_id])
    missing_inherited = Set.new(@inherited_filters[item_id])

    filter_taggings.each do |ft|
      if missing_direct.delete?(ft.filter_id)
        update_inherited(ft, false)
      elsif missing_inherited.delete?(ft.filter_id)
        update_inherited(ft, true)
      else
        destroy(ft)
      end
    end

    create_multiple(item_id, missing_direct, false)
    create_multiple(item_id, missing_inherited, true)
  end

  # Notify the filterable class about the changes that we made, so that it can
  # perform the appropriate steps to reindex everything.
  def reindex_changed
    klass.reindex_for_filter_changes(@valid_item_ids, @modified, reindex_queue)
  end

  ########################################
  # RETRIEVE INFO FROM DATABASE
  ########################################

  # Calculates @direct_filters, @meta_tags, and @inherited_filters for this
  # batch of items.
  def load_info
    load_valid_item_ids
    load_direct_filters
    load_meta_tags
    load_inherited_filters
  end

  # Calculate which items exist in the database, so that we don't try to create
  # FilterTaggings for items that have been deleted.
  def load_valid_item_ids
    @valid_item_ids = klass.unscoped.where(id: ids).distinct.pluck(:id)
  end

  # Calculates what the direct filters should be for this batch of items.
  #
  # Sets @direct_filters equal to a hash mapping from item IDs to a list of
  # direct filter IDs (that is, filters that the item is either directly tagged
  # with, or tagged with one of its synonyms). The default value for the
  # hash is an empty list.
  def load_direct_filters
    taggings = Tagging.where(taggable_type: type, taggable_id: @valid_item_ids)

    filter_relations = [
      Tag.canonical.joins(:taggings),
      Tag.canonical.joins(mergers: :taggings)
    ]

    pairs = filter_relations.flat_map do |filters|
      filters.merge(taggings).pluck("taggings.taggable_id", "tags.id")
    end

    @direct_filters = hash_from_pairs(pairs)
  end

  # Reads MetaTagging info from the database for all tags included in this
  # batch.
  #
  # Sets @meta_tags equal to a hash mapping from tag IDs to the tag's metatag
  # IDs. The default value for the hash is an empty list.
  def load_meta_tags
    all_filters = @direct_filters.values.flatten.uniq

    pairs = Tag.canonical.joins(:sub_taggings).where(
      meta_taggings: { sub_tag_id: all_filters }
    ).pluck(:sub_tag_id, :meta_tag_id)

    @meta_tags = hash_from_pairs(pairs)
  end

  # Uses @direct_filters and @meta_tags to calculate what the inherited filters
  # should be for each of the items in this batch. Creates a hash
  # @inherited_filters mapping from item IDs to the inherited tag IDs.
  def load_inherited_filters
    @inherited_filters = Hash.new([].freeze)

    @direct_filters.each_pair do |item_id, filter_ids|
      inherited = filter_ids.flat_map { |filter_id| @meta_tags[filter_id] }
      @inherited_filters[item_id] = (inherited - filter_ids).uniq
    end
  end

  # Given a list of pairs of IDs, treat each pair as a (key, value) pair, and
  # return a hash that associates each key with a list of values. Sets the
  # default value of the hash to an empty frozen list, and freezes all of the
  # lists in the hash at the end.
  def hash_from_pairs(pairs)
    hash = Hash.new([].freeze)

    pairs.uniq.each do |key, value|
      hash[key] = [] unless hash.key?(key)
      hash[key] << value
    end

    hash.transform_values!(&:freeze)
  end

  ########################################
  # CHANGE FILTERS AND RECORD
  ########################################

  # Create multiple FilterTaggings for the same item, with the given
  # filter_ids. Records the new item in the list @modified.
  def create_multiple(item_id, filter_ids, inherited)
    filter_ids.each do |filter_id|
      @modified << FilterTagging.create(filter_id: filter_id,
                                        filterable_type: type,
                                        filterable_id: item_id,
                                        inherited: inherited)
    end
  end

  # Modify an existing filter tagging, and record the modified item in the list
  # @modified.
  def update_inherited(filter_tagging, inherited)
    return if filter_tagging.inherited == inherited

    filter_tagging.update(inherited: inherited)
    @modified << filter_tagging
  end

  # Destroy an existing filter tagging, and record the modified item in
  # the list @modified.
  def destroy(filter_tagging)
    filter_tagging.destroy
    @modified << filter_tagging
  end
end
