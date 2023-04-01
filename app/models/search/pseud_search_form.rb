class PseudSearchForm

  extend ActiveModel::Naming
  include ActiveModel::Conversion
  include ActiveModel::Validations

  ATTRIBUTES = [
    :query,
    :name,
    :collection_ids,
    :fandom
  ]

  attr_accessor :options

  ATTRIBUTES.each do |filterable|
    define_method(filterable) { options[filterable] }
  end

  def initialize(options={})
    @options = options
    set_fandoms
    @searcher = PseudQuery.new(@options.delete_if { |_, v| v.blank? })
  end

  def persisted?
    false
  end

  def search_results
    @searcher.search_results
  end

  def set_fandoms
    return unless @options[:fandom].present?
    names = @options[:fandom].split(',').map(&:squish)
    @options[:fandom_ids] = Tag.where(name: names).pluck(:id)
  end

end
