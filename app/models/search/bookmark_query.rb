class BookmarkQuery < Query
  attr_accessor :bookmarkable_query

  def klass
    'Bookmark'
  end

  def index_name
    BookmarkIndexer.index_name
  end

  def document_type
    BookmarkIndexer.document_type
  end

  # Load the options and create the linked BookmarkableQuery class (which is
  # used to generate all of our parent filters).
  def initialize(options = {})
    @options = HashWithIndifferentAccess.new(options)
    add_owner
    self.bookmarkable_query = BookmarkableQuery.new(self)
  end

  # After the initial search, run an additional query to get work/series tag filters
  # Elasticsearch doesn't support parent aggregations, and doing the main query on the parents
  # limits searching and sorting on the bookmarks themselves
  # Hopefully someday they'll fix this and we can get the data from a single query
  def search_results
    response = search
    if response['aggregations']
      response['aggregations'].merge!(bookmarkable_query.aggregation_results)
    end
    QueryResult.new(klass, response, options.slice(:page, :per_page))
  end

  # Combine the query on the bookmark with the query on the bookmarkable.
  def queries
    @queries ||= [
      bookmark_query_or_filter,
      parent_bookmarkable_query_or_filter
    ].flatten.compact
  end

  # Combine the filters on the bookmark with the filters on the bookmarkable.
  def filters
    @filters ||= [
      bookmark_filters,
      bookmarkable_filter
    ].flatten.compact
  end

  # Combine the exclusion filters on the bookmark with the exclusion filters on
  # the bookmarkable.
  def exclusion_filters
    @exclusion_filters ||= [
      bookmark_exclusion_filters,
      bookmarkable_exclusion_filter
    ].flatten.compact
  end

  def add_owner
    owner = options[:parent]
    field = case owner
            when Tag
              # Note that in a bookmark search for a Tag owner, we want to return
              # the bookmarkables, not bookmarks, with that tag.
              # This field will be handled in the linked BookmarkableQuery.
              :filter_ids
            when Pseud
              :pseud_ids
            when User
              :user_ids
            when Collection
              :collection_ids
            end
    return unless field.present?
    options[field] ||= []
    options[field] << owner.id
  end

  ####################
  # QUERIES
  ####################

  def bookmark_query_or_filter
    return nil if bookmark_query_text.blank?
    { query_string: { query: bookmark_query_text, default_operator: "AND" } }
  end

  def parent_bookmarkable_query_or_filter
    return nil if bookmarkable_query.bookmarkable_query_or_filter.blank?
    {
      has_parent: {
        parent_type: "bookmarkable",
        score: true, # include the score from the bookmarkable
        query: bookmarkable_query.bookmarkable_query_or_filter
      }
    }
  end

  def bookmark_query_text
    query_text = (options[:bookmark_query] || "").dup
    query_text << split_query_text_words(:bookmarker, options[:bookmarker])
    query_text << split_query_text_words(:notes, options[:notes])
    escape_slashes(query_text.strip)
  end

  ####################
  # SORTING AND AGGREGATIONS
  ####################

  def sort_column
    @sort_column ||=
      options[:sort_column].present? ? options[:sort_column] : default_sort
  end

  def sort_direction
    @sort_direction ||=
      options[:sort_direction].present? ? options[:sort_direction] : "desc"
  end

  def default_sort
    facet_tags? ? 'created_at' : '_score'
  end

  def sort
    sort_hash = { sort_column => { order: sort_direction } }

    if %w(created_at bookmarkable_date).include?(sort_column)
      sort_hash[sort_column][:unmapped_type] = 'date'
    end

    [sort_hash, { id: { order: sort_direction } }]
  end

  def aggregations
    aggs = {}
    if facet_collections?
      aggs[:collections] = { terms: { field: 'collection_ids' } }
    end

    if facet_tags?
      aggs[:tag] = { terms: { field: "tag_ids" } }
    end

    { aggs: aggs }
  end

  ####################
  # GROUPS OF FILTERS
  ####################

  # Filters that apply only to the bookmark. These are must/and filters,
  # meaning that all of them are required to occur in all bookmarks.
  def bookmark_filters
    @bookmark_filters ||= [
      privacy_filter,
      hidden_filter,
      bookmarks_only_filter,
      pseud_filter,
      user_filter,
      rec_filter,
      notes_filter,
      tags_filter,
      named_tag_inclusion_filter,
      collections_filter,
      type_filter,
      date_filter
    ].flatten.compact
  end

  # Exclusion filters that apply only to the bookmark. These are must_not/not
  # filters, meaning that none of them are allowed to occur in any search
  # results. DO NOT INCLUDE FILTERS ON THE BOOKMARKABLE HERE. If you do, this
  # may cause an infinite loop.
  def bookmark_exclusion_filters
    @bookmark_exclusion_filters ||= [
      tag_exclusion_filter,
      named_tag_exclusion_filter
    ].flatten.compact
  end

  # Wrap all of the must/and filters on the bookmarkable into a single
  # has_parent query. (The more has_parent queries we have, the slower our
  # search will be.)
  def bookmarkable_filter
    return if bookmarkable_query.bookmarkable_filters.blank?

    @bookmarkable_filter ||= {
      has_parent: {
        parent_type: "bookmarkable",
        query: make_bool(
          must: bookmarkable_query.bookmarkable_filters
        )
      }
    }
  end

  # Wrap all of the must_not/not filters on the bookmarkable into a single
  # has_parent query. Note that we wrap them in a should/or query because if
  # any of the parent queries return true, we want to return false. (De
  # Morgan's Law.)
  def bookmarkable_exclusion_filter
    return if bookmarkable_query.bookmarkable_exclusion_filters.blank?

    @bookmarkable_exclusion_filter ||= {
      has_parent: {
        parent_type: "bookmarkable",
        query: make_bool(
          should: bookmarkable_query.bookmarkable_exclusion_filters
        )
      }
    }
  end

  ####################
  # FILTERS
  ####################

  def privacy_filter
    term_filter(:private, 'false') unless include_private?
  end

  def hidden_filter
    term_filter(:hidden_by_admin, 'false')
  end

  def rec_filter
    term_filter(:rec, 'true') if %w(1 true).include?(options[:rec].to_s)
  end

  def notes_filter
    term_filter(:with_notes, 'true') if %w(1 true).include?(options[:with_notes].to_s)
  end

  def type_filter
    term_filter(:bookmarkable_type, options[:bookmarkable_type].gsub(" ", "")) if options[:bookmarkable_type].present?
  end

  # The date filter on the bookmark (i.e. when the bookmark was created).
  def date_filter
    if options[:date].present?
      { range: { created_at: SearchRange.parsed(options[:date]) } }
    end
  end

  def pseud_filter
    if options[:pseud_ids].present?
      terms_filter(:pseud_id, options[:pseud_ids].flatten.uniq)
    end
  end

  def user_filter
    return unless options[:user_ids].present?
    options[:user_ids].flatten.uniq.map { |user_id| term_filter(:user_id, user_id) }
  end

  def tags_filter
    if included_bookmark_tag_ids.present?
      included_bookmark_tag_ids.map { |tag_id| term_filter(:tag_ids, tag_id) }
    end
  end

  def collections_filter
    terms_filter(:collection_ids, options[:collection_ids]) if options[:collection_ids].present?
  end

  def tag_exclusion_filter
    terms_filter(:tag_ids, excluded_bookmark_tag_ids) if excluded_bookmark_tag_ids.present?
  end

  # We don't want to accidentally return Bookmarkable documents when we're
  # doing a search for Bookmarks. So we should only include documents that are
  # marked as "bookmark" in their bookmarkable_join field.
  def bookmarks_only_filter
    term_filter(:bookmarkable_join, "bookmark")
  end

  # This filter is used to restrict our results to only include bookmarks whose
  # "tag" text matches all of the tag names in included_bookmark_tag_names.
  # This is useful when the user enters a non-existent tag, which would be
  # discarded by the included_bookmark_tag_ids function.
  def named_tag_inclusion_filter
    return if included_bookmark_tag_names.blank?
    match_filter(:tag, included_bookmark_tag_names.join(" "))
  end

  # This set of filters is used to prevent us from matching any bookmarks
  # whose "tag" text matches one of the passed-in tag names. This is useful
  # when the user enters a non-existent tag, which would be discarded by the
  # excluded_bookmark_tag_ids function.
  #
  # Unlike the inclusion filter, we separate the queries to make sure that with
  # tags "A B" and "C D", we're searching for "not(A and B) and not(C and D)",
  # instead of "not(A and B and C and D)" or "not(A or B or C or D)".
  def named_tag_exclusion_filter
    excluded_bookmark_tag_names.map do |tag_name|
      match_filter(:tag, tag_name)
    end
  end

  ####################
  # HELPERS
  ####################

  def facet_tags?
    options[:faceted]
  end

  def facet_collections?
    false
  end

  def include_private?
    # Use fetch instead of || here to make sure that we don't accidentally
    # override a deliberate choice not to show private bookmarks.
    options.fetch(:show_private,
                  User.current_user.is_a?(User) &&
                  user_ids.include?(User.current_user.id))
  end

  def user_ids
    user_ids = []
    if options[:user_ids].present?
      user_ids += options[:user_ids].map(&:to_i)
    end
    if options[:pseud_ids].present?
      user_ids += Pseud.where(id: options[:pseud_ids]).pluck(:user_id)
    end
    user_ids
  end

  # The list of all tag IDs that should be required for our bookmarks.
  def included_bookmark_tag_ids
    @included_bookmark_tag_ids ||= [
      options[:tag_ids],
      parsed_included_tags[:ids]
    ].flatten.compact.uniq
  end

  # The list of all tag IDs that should be prohibited for our bookmarks.
  def excluded_bookmark_tag_ids
    @excluded_bookmark_tag_ids ||= [
      options[:excluded_bookmark_tag_ids],
      parsed_excluded_tags[:ids]
    ].flatten.compact.uniq
  end

  # The list of included tag names that weren't found in the database (and thus
  # have to be used as text-matching constraints on the tag field).
  def included_bookmark_tag_names
    parsed_included_tags[:missing]
  end

  # The list of excluded tag names that weren't found in the database (and thus
  # have to be used as text-matching constraints on the tag field).
  def excluded_bookmark_tag_names
    parsed_excluded_tags[:missing]
  end

  # Parse the tag names that should be included in our results.
  def parsed_included_tags
    @parsed_included_tags ||=
      bookmarkable_query.parse_named_tags(%i[other_bookmark_tag_names])
  end

  # Parse the tag names that should be excluded from our results.
  def parsed_excluded_tags
    @parsed_excluded_tags ||=
      bookmarkable_query.parse_named_tags(%i[excluded_bookmark_tag_names])
  end
end
