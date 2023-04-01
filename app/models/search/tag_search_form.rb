class TagSearchForm

  extend ActiveModel::Naming
  include ActiveModel::Conversion
  include ActiveModel::Validations

  ATTRIBUTES = [
    :query,
    :name,
    :canonical,
    :fandoms,
    :type,
    :created_at,
    :sort_column,
    :sort_direction
  ]

  attr_accessor :options

  ATTRIBUTES.each do |filterable|
    define_method(filterable) { options[filterable] }
  end

  def initialize(options={})
    @options = options
    set_fandoms
    @searcher = TagQuery.new(@options.delete_if { |_, v| v.blank? })
  end

  def persisted?
    false
  end

  def search_results
    @searcher.search_results
  end

  def set_fandoms
    return if @options[:fandoms].blank?

    names = @options[:fandoms].split(",").map(&:squish)
    @options[:fandom_ids] = Tag.where(name: names).pluck(:id)
  end

  def sort_columns
    options[:sort_column] || "name"
  end

  def sort_direction
    options[:sort_direction] || default_sort_direction
  end

  def sort_options
    [
      %w[Name name],
      ["Date Created", "created_at"]
    ]
  end

  def default_sort_direction
    %w[created_at].include?(sort_column) ? "desc" : "asc"
  end
end
