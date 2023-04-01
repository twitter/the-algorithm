class BookmarkableQuery < Query
  include TaggableQuery

  attr_accessor :bookmark_query

  # Rather than compute this information twice, we rely on the BookmarkQuery
  # class to calculate information about sorting.
  delegate :sort_column, :sort_direction,
           to: :bookmark_query

  # The "klass" function here returns the class name used to load search
  # results. The BookmarkableQuery is unique among Query classes because it can
  # return objects from more than one table, so we need to use a special class
  # that can handle IDs of multiple types.
  def klass
    'BookmarkableDecorator'
  end

  def index_name
    BookmarkableIndexer.index_name
  end

  def document_type
    BookmarkableIndexer.document_type
  end

  # The BookmarkableQuery is unique among queries in that it depends wholly on
  # the BookmarkQuery for all of its options. So we have a slightly different
  # constructor.
  def initialize(bookmark_query)
    self.bookmark_query = bookmark_query
    @options = bookmark_query.options
  end

  # Do a regular search and return only the aggregations
  # Note that we make a few modifications to the query before sending it. We
  # don't want to return the nested bookmark aggregations, since this is called
  # in the BookmarkQuery class (which has its own aggregations). And we don't
  # want to return any results.
  def aggregation_results
    # Override the values for "from" and "size":
    modified_query = generated_query.merge(from: 0, size: 0)

    # Delete the bookmark aggregations.
    modified_query[:aggs].delete(:bookmarks)

    $elasticsearch.search(
      index: index_name,
      body: modified_query
    )["aggregations"]
  end

  # Because we want to calculate our score based on the bookmark's search results,
  # we use bookmark_filter as our "query" (because it goes in the "must"
  # section of the query, meaning that its score isn't discarded).
  def queries
    bookmark_filter
  end

  # Because we want to calculate the score from our bookmarks, we only use the
  # bookmarkable filters here.
  def filters
    @filters ||= [
      bookmarkable_query_or_filter, # acts as a filter
      bookmarkable_filters
    ].flatten.compact
  end

  # Because we want to calculate the score from our bookmarks, we only use the
  # bookmarkable exclusion filters here.
  def exclusion_filters
    @exclusion_filters ||= [
      bookmarkable_exclusion_filters
    ].flatten.compact
  end

  ####################
  # QUERIES
  ####################

  def bookmarkable_query_or_filter
    return nil if bookmarkable_query_text.blank?
    { query_string: { query: bookmarkable_query_text, default_operator: "AND" } }
  end

  def bookmarkable_query_text
    query_text = (options[:bookmarkable_query] || "").dup
    escape_slashes(query_text.strip)
  end

  ####################
  # SORTING AND AGGREGATIONS
  ####################

  # When sorting by bookmarkable date, we use the revised_at field to order the
  # results. When sorting by created_at, we use _score to sort (because the
  # only way to sort by a child's fields is to store the value in the _score
  # field and sort by score).
  def sort
    if sort_column == "bookmarkable_date"
      sort_hash = { revised_at: { order: sort_direction, unmapped_type: "date" } }
    else
      sort_hash = { _score: { order: sort_direction } }
    end

    [sort_hash, { sort_id: { order: sort_direction } }]
  end

  # Define the aggregations for the search
  # In this case, the various tag fields
  def aggregations
    aggs = {}

    %w(rating archive_warning category fandom character relationship freeform).each do |facet_type|
      aggs[facet_type] = {
        terms: {
          field: "#{facet_type}_ids"
        }
      }
    end

    if bookmark_query.facet_tags? || bookmark_query.facet_collections?
      aggs[:bookmarks] = {
        # Aggregate on our child bookmarks.
        children: { type: "bookmark" },
        aggs: {
          filtered_bookmarks: {
            # Only include bookmarks that satisfy the bookmark_query's filters.
            filter: make_bool(
              must: bookmark_query.bookmark_query_or_filter, # acts as a query
              filter: bookmark_query.bookmark_filters,
              must_not: bookmark_query.bookmark_exclusion_filters
            )
          }.merge(bookmark_query.aggregations) # Use bookmark aggregations.
        }
      }
    end

    { aggs: aggs }
  end


  ####################
  # GROUPS OF FILTERS
  ####################

  # Filters that apply only to the bookmarkable.
  def bookmarkable_filters
    @bookmarkable_filters ||= [
      complete_filter,
      language_filter,
      filter_id_filter,
      named_tag_inclusion_filter,
      date_filter
    ].flatten.compact
  end

  # Exclusion filters that apply only to the bookmarkable.
  # Note that in order to include bookmarks of deleted works/series/external
  # works in some search results, we set up all of the visibility filters
  # (unposted/hidden/restricted) as *exclusion* filters.
  def bookmarkable_exclusion_filters
    @bookmarkable_exclusion_filters ||= [
      unposted_filter,
      hidden_filter,
      restricted_filter,
      tag_exclusion_filter,
      named_tag_exclusion_filter
    ].flatten.compact
  end

  # Create a single has_child query with ALL of the child's queries and filters
  # included. In order to avoid issues with multiple bookmarks combining to
  # create an (incorrect) bookmarkable match, there MUST be exactly one
  # has_child query. (Plus, it probably makes it faster.)
  def bookmark_filter
    @bookmark_filter ||= {
      has_child: {
        type: "bookmark",
        score_mode: "max",
        query: bookmark_bool,
        inner_hits: {
          size: inner_hits_size,
          sort: { created_at: { order: "desc", unmapped_type: "date" } }
        }
      }
    }
  end

  # The bool used in the has_child query.
  def bookmark_bool
    if sort_column == "created_at"
      # In this case, we need to take the max of the creation dates of our
      # children in order to calculate the correct order.
      make_bool(
        must: field_value_score("created_at"), # score = bookmark's created_at
        filter: [
          bookmark_query.bookmark_query_or_filter, # acts as a filter
          bookmark_query.bookmark_filters
        ].flatten.compact,
        must_not: bookmark_query.bookmark_exclusion_filters
      )
    else
      # In this case, we can fall back on the default behavior and use the
      # bookmark query to score the bookmarks.
      make_bool(
        must: bookmark_query.bookmark_query_or_filter, # acts as a query
        filter: bookmark_query.bookmark_filters,
        must_not: bookmark_query.bookmark_exclusion_filters
      )
    end
  end

  ####################
  # FILTERS
  ####################

  def complete_filter
    term_filter(:complete, 'true') if options[:complete].present?
  end

  def language_filter
    term_filter(:"language_id.keyword", options[:language_id]) if options[:language_id].present?
  end

  def filter_id_filter
    if filter_ids.present?
      filter_ids.map { |filter_id| term_filter(:filter_ids, filter_id) }
    end
  end

  # The date filter on the bookmarkable (i.e. when the bookmarkable was last
  # updated).
  def date_filter
    if options[:bookmarkable_date].present?
      { range: { revised_at: SearchRange.parsed(options[:bookmarkable_date]) } }
    end
  end

  # Exclude drafts from bookmarkable search results.
  # Note that this is used as an exclusion filter, not an inclusion filter, so
  # the boolean is flipped from the way you might expect.
  def unposted_filter
    term_filter(:posted, 'false')
  end

  # Exclude items hidden by admin from bookmarkable search results.
  # Note that this is used as an exclusion filter, not an inclusion filter, so
  # the boolean is flipped from the way you might expect.
  def hidden_filter
    term_filter(:hidden_by_admin, 'true')
  end

  # Exclude restricted works/series when the user isn't logged in.
  # Note that this is used as an exclusion filter, not an inclusion filter, so
  # the boolean is flipped from the way you might expect.
  def restricted_filter
    term_filter(:restricted, 'true') unless include_restricted?
  end

  def tag_exclusion_filter
    if exclusion_ids.present?
      terms_filter(:filter_ids, exclusion_ids)
    end
  end

  # This filter is used to restrict our results to only include bookmarkables
  # whose "tag" text matches all of the tag names in included_tag_names. This
  # is useful when the user enters a non-existent tag, which would be discarded
  # by the TaggableQuery.filter_ids function.
  def named_tag_inclusion_filter
    return if included_tag_names.blank?
    match_filter(:tag, included_tag_names.join(" "))
  end

  # This set of filters is used to prevent us from matching any bookmarkables
  # whose "tag" text matches one of the passed-in tag names. This is useful
  # when the user enters a non-existent tag, which would be discarded by the
  # TaggableQuery.exclusion_ids function.
  #
  # Note that we separate these into different filters to get the logic of tag
  # exclusion right: if we're excluding "A B" and "C D", we want the query to
  # be "not(A and B) and not(C and D)", which can't be accomplished in a single
  # match query.
  def named_tag_exclusion_filter
    excluded_tag_names.map do |tag_name|
      match_filter(:tag, tag_name)
    end
  end

  ####################
  # HELPERS
  ####################

  # The number of bookmarks to return with each bookmarkable.
  def inner_hits_size
    ArchiveConfig.NUMBER_OF_BOOKMARKS_SHOWN_PER_BOOKMARKABLE || 5
  end

  def include_restricted?
    # Use fetch instead of || here to make sure that we don't accidentally
    # override a deliberate choice not to show restricted bookmarks.
    options.fetch(:show_restricted, User.current_user.present?)
  end
end
