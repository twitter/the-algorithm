class WorkQuery < Query
  include TaggableQuery

  def klass
    'Work'
  end

  def index_name
    WorkIndexer.index_name
  end

  def document_type
    WorkIndexer.document_type
  end

  # Combine the available filters
  def filters
    add_owner

    @filters ||= (
      visibility_filters +
      work_filters +
      creator_filters +
      collection_filters +
      tag_filters +
      range_filters
    ).flatten.compact
  end

  def exclusion_filters
    @exclusion_filters ||= [
      tag_exclusion_filter,
      named_tag_exclusion_filter
    ].flatten.compact
  end

  # Combine the available queries
  # In this case, name is the only text field
  def queries
    @queries = [
      general_query
    ].flatten.compact
  end

  def add_owner
    owner = options[:works_parent]
    field = case owner
            when Tag
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
  # GROUPS OF FILTERS
  ####################

  def visibility_filters
    [
      posted_filter,
      hidden_filter,
      restricted_filter,
      unrevealed_filter,
      anon_filter
    ]
  end

  def work_filters
    [
      complete_filter,
      single_chapter_filter,
      language_filter,
      crossover_filter,
      type_filter
    ]
  end

  def creator_filters
    [user_filter, pseud_filter]
  end

  def collection_filters
    [collection_filter]
  end

  def tag_filters
    [
      filter_id_filter,
      named_tag_inclusion_filter
    ].flatten.compact
  end

  def range_filters
    ranges = []
    [:word_count, :hits, :kudos_count, :comments_count, :bookmarks_count, :revised_at].each do |countable|
      if options[countable].present?
        ranges << { range: { countable => SearchRange.parsed(options[countable]) } }
      end
    end
    ranges += [date_range_filter, word_count_filter].compact
    ranges
  end

  ####################
  # FILTERS
  ####################

  def posted_filter
    term_filter(:posted, 'true')
  end

  def hidden_filter
    term_filter(:hidden_by_admin, 'false')
  end

  def restricted_filter
    term_filter(:restricted, 'false') unless include_restricted?
  end

  def unrevealed_filter
    term_filter(:in_unrevealed_collection, 'false') unless include_unrevealed?
  end

  def anon_filter
    term_filter(:in_anon_collection, 'false') unless include_anon?
  end

  def complete_filter
    term_filter(:complete, bool_value(options[:complete])) if options[:complete].present?
  end

  def single_chapter_filter
    term_filter(:expected_number_of_chapters, 1) if options[:single_chapter].present?
  end

  def language_filter
    term_filter(:"language_id.keyword", options[:language_id]) if options[:language_id].present?
  end

  def crossover_filter
    term_filter(:crossover, bool_value(options[:crossover])) if options[:crossover].present?
  end

  def type_filter
    terms_filter(:work_type, options[:work_types]) if options[:work_types]
  end

  def user_filter
    return if user_ids.blank?

    if viewing_own_collected_works_page?
      {
        has_child: {
          type: "creator",
          query: terms_filter(:private_user_ids, user_ids)
        }
      }
    else
      terms_filter(:user_ids, user_ids)
    end
  end

  def pseud_filter
    terms_filter(:pseud_ids, pseud_ids) if pseud_ids.present?
  end

  def collection_filter
    terms_filter(:collection_ids, options[:collection_ids]) if options[:collection_ids].present?
  end

  def filter_id_filter
    if filter_ids.present?
      filter_ids.map { |filter_id| term_filter(:filter_ids, filter_id) }
    end
  end

  def tag_exclusion_filter
    if exclusion_ids.present?
      exclusion_ids.map { |exclusion_id| term_filter(:filter_ids, exclusion_id) }
    end
  end

  # This filter is used to restrict our results to only include works
  # whose "tag" text matches all of the tag names in included_tag_names. This
  # is useful when the user enters a non-existent tag, which would be discarded
  # by the TaggableQuery.filter_ids function.
  def named_tag_inclusion_filter
    return if included_tag_names.blank?
    match_filter(:tag, included_tag_names.join(" "))
  end

  # This set of filters is used to prevent us from matching any works whose
  # "tag" text matches one of the passed-in tag names. This is useful when the
  # user enters a non-existent tag, which would be discarded by the
  # TaggableQuery.exclusion_ids function.
  #
  # Unlike the inclusion filter, we must separate these into different match
  # filters to get the results that we want (that is, excluding "A B" and "C D"
  # is the same as "not(A and B) and not(C and D)").
  def named_tag_exclusion_filter
    excluded_tag_names.map do |tag_name|
      match_filter(:tag, tag_name)
    end
  end

  def date_range_filter
    return unless options[:date_from].present? || options[:date_to].present?
    begin
      range = {}
      range[:gte] = clamp_search_date(options[:date_from].to_date) if options[:date_from].present?
      range[:lte] = clamp_search_date(options[:date_to].to_date) if options[:date_to].present?
      { range: { revised_at: range } }
    rescue ArgumentError
      nil
    end
  end

  def word_count_filter
    return unless options[:words_from].present? || options[:words_to].present?
    range = {}
    range[:gte] = options[:words_from].delete(",._").to_i if options[:words_from].present?
    range[:lte] = options[:words_to].delete(",._").to_i if options[:words_to].present?
    { range: { word_count: range } }
  end

  ####################
  # QUERIES
  ####################

  # Search for a tag by name
  # Note that fields don't need to be explicitly included in the
  # field list to be searchable directly (ie, "complete:true" will still work)
  def general_query
    input = (options[:q] || options[:query] || "").dup
    query = generate_search_text(input)

    return {
      query_string: {
        query: query,
        fields: ["creators^5", "title^7", "endnotes", "notes", "summary", "tag", "series.title"],
        default_operator: "AND"
      }
    } unless query.blank?
  end

  def generate_search_text(query = '')
    search_text = query
    %i[title creators].each do |field|
      search_text << split_query_text_words(field, options[field])
    end

    if options[:series_titles].present?
      search_text << split_query_text_words("series.title", options[:series_titles])
    end

    if options[:collection_ids].blank? && collected?
      search_text << " collection_ids:*"
    end
    escape_slashes(search_text.strip)
  end

  def sort
    column = options[:sort_column].present? ? options[:sort_column] : default_sort
    direction = options[:sort_direction].present? ? options[:sort_direction] : 'desc'
    sort_hash = { column => { order: direction } }

    if column == 'revised_at'
      sort_hash[column][:unmapped_type] = 'date'
    end

    [sort_hash, { id: { order: direction } }]
  end

  # When searching outside of filters, use relevance instead of date
  def default_sort
    facet_tags? || collected? ? 'revised_at' : '_score'
  end

  def aggregations
    aggs = {}
    if collected?
      aggs[:collections] = { terms: { field: 'collection_ids' } }
    end

    if facet_tags?
      %w(rating archive_warning category fandom character relationship freeform).each do |facet_type|
        aggs[facet_type] = { terms: { field: "#{facet_type}_ids" } }
      end
    end

    { aggs: aggs }
  end

  ####################
  # HELPERS
  ####################

  def facet_tags?
    options[:faceted]
  end

  def collected?
    options[:collected]
  end

  def viewing_own_collected_works_page?
    collected? && options[:works_parent].present? &&
      options[:works_parent] == User.current_user
  end

  def include_restricted?
    User.current_user.present? || options[:show_restricted]
  end

  # Include unrevealed works only if we're on a collection page
  # OR the collected works page of a user
  def include_unrevealed?
    options[:collection_ids].present? || collected?
  end

  # Include anonymous works if we're not on a user/pseud page
  # OR if the user is viewing their own collected works
  def include_anon?
    (user_ids.blank? && pseud_ids.blank?) ||
      viewing_own_collected_works_page?
  end

  def user_ids
    options[:user_ids]
  end

  def pseud_ids
    options[:pseud_ids]
  end

  # By default, ES6 expects yyyy-MM-dd and can't parse years with 4+ digits.
  def clamp_search_date(date)
    return date.change(year: 0) if date.year.negative?
    return date.change(year: 9999) if date.year > 9999
    date
  end
end
