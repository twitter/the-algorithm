class PseudQuery < Query

  # The "klass" function in the query classes is used only to determine what
  # type of search results to return (that is, which class the QueryResult
  # class will call "load_from_elasticsearch" on). Because the Pseud search
  # should always wrap Pseuds up in a PseudDecorator, we return PseudDecorator
  # instead of Pseud.
  def klass
    'PseudDecorator'
  end

  def index_name
    PseudIndexer.index_name
  end

  def document_type
    PseudIndexer.document_type
  end

  def filters
    [collection_filter, fandom_filter].flatten.compact
  end

  def queries
    [general_query, name_query].compact
  end

  ###########
  # FILTERS
  ###########

  def collection_filter
    { term: { collection_ids: options[:collection_id] } } if options[:collection_id]
  end

  def fandom_filter
    key = User.current_user.present? ? "fandoms.id" : "fandoms.id_for_public"
    if options[:fandom_ids]
      options[:fandom_ids].map do |fandom_id|
        { term: { key => fandom_id } }
      end
    end
  end

  ###########
  # QUERIES
  ###########

  def general_query
    {
      simple_query_string:{
        query: escape_reserved_characters(options[:query]),
        fields: ["byline^5", "name^4", "user_login^2", "description"],
        default_operator: "AND"
      }
    } if options[:query]
  end

  def name_query
    { match: { byline: escape_reserved_characters(options[:name]) } } if options[:name]
  end
end
