class QueryResult

  include Enumerable

  attr_reader :klass, :response, :current_page, :per_page, :error, :notice

  def initialize(model_name, response, options={})
    @klass = model_name.classify.constantize
    @response = response
    @current_page = options[:page] || 1
    @per_page = options[:per_page] || ArchiveConfig.ITEMS_PER_PAGE
    @error = response[:error]
    @notice = max_search_results_notice
  end

  def hits
    response.dig('hits', 'hits')
  end

  def items
    return [] if response[:error]
    if @items.nil?
      @items = klass.load_from_elasticsearch(hits, scopes: @scopes)
    end
    @items
  end

  def scope(*args)
    @scopes ||= []
    @scopes += args
    @items = nil # reset the items in case we already loaded them
    self # for chaining
  end

  def each(&block)
    items.each(&block)
  end

  def empty?
    items.empty?
  end

  def size
    items.size
  end
  alias :length :size

  def [](index)
    items[index]
  end

  def to_ary
    items
  end

  def load_tag_facets(type, info)
    @facets[type] = []
    buckets = info["buckets"]
    ids = buckets.map { |result| result['key'] }
    tags = Tag.where(id: ids).group_by(&:id)
    buckets.each do |facet|
      unless tags[facet['key'].to_i].blank?
        @facets[type] << QueryFacet.new(facet['key'], tags[facet['key'].to_i].first.name, facet['doc_count'])
      end
    end
  end

  def load_collection_facets(info)
    @facets["collections"] = []
    buckets = info["buckets"]
    ids = buckets.map { |result| result['key'] }
    collections = Collection.where(id: ids).group_by(&:id)
    buckets.each do |facet|
      unless collections[facet['key'].to_i].blank?
        @facets["collections"] << QueryFacet.new(facet['key'], collections[facet['key'].to_i].first.title, facet['doc_count'])
      end
    end
  end

  def facets
    return if response['aggregations'].nil?

    if @facets.nil?
      @facets = {}
      response['aggregations'].each_pair do |term, results|
        if Tag::TYPES.include?(term.classify) || term == 'tag'
          load_tag_facets(term, results)
        elsif term == 'collections'
          load_collection_facets(results)
        elsif term == 'bookmarks'
          load_tag_facets("tag", results["filtered_bookmarks"]["tag"])
        end
      end
    end
    @facets
  end

  def total_pages
    (total_entries / per_page.to_f).ceil rescue 0
  end

  # For pagination / fetching results.
  def total_entries
    [unlimited_total_entries, ArchiveConfig.MAX_SEARCH_RESULTS].min
  end

  def unlimited_total_entries
    response.dig('hits', 'total', 'value') || 0
  end

  def offset
    (current_page * per_page) - per_page
  end

  def max_search_results_notice
    # if we're on the last page of search results AND there are more results than we can show
    return unless current_page >= total_pages && unlimited_total_entries > total_entries
    ActionController::Base.helpers.ts("Displaying %{displayed} results out of %{total}. Please use the filters or edit your search to customize this list further.",
                                      displayed: total_entries,
                                      total: unlimited_total_entries
                                   ).html_safe
  end
end
