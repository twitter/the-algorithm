class BookmarkSearchForm

  extend ActiveModel::Naming
  include ActiveModel::Conversion
  include ActiveModel::Validations

  ATTRIBUTES = [
    :bookmark_query,
    :bookmarkable_query,
    :rec,
    :bookmark_notes,
    :with_notes,
    :date,
    :show_private,
    :pseud_ids,
    :user_ids,
    :bookmarker,
    :bookmarkable_pseud_names,
    :bookmarkable_pseud_ids,
    :bookmarkable_type,
    :language_id,
    :excluded_tag_names,
    :excluded_bookmark_tag_names,
    :excluded_tag_ids,
    :excluded_bookmark_tag_ids,
    :other_tag_names,
    :other_bookmark_tag_names,
    :tag_ids,
    :filter_ids,
    :filter_names,
    :fandom_ids,
    :character_ids,
    :relationship_ids,
    :freeform_ids,
    :rating_ids,
    :archive_warning_ids,
    :category_ids,
    :bookmarkable_title,
    :bookmarkable_date,
    :bookmarkable_complete,
    :collection_ids,
    :bookmarkable_collection_ids,
    :sort_column,
    :show_restricted,
    :page,
    :faceted
  ]

  attr_accessor :options

  ATTRIBUTES.each do |filterable|
    define_method(filterable) { options[filterable] }
  end

  def initialize(options = {})
    @options = processed_options(options)
    @searcher = BookmarkQuery.new(@options)
  end

  def persisted?
    false
  end

  # This is used by SearchHelper.search_header.
  def query
    queries = []
    %w[bookmarkable_query bookmark_query].each do |key|
      queries << options[key] if options[key].present?
    end
    queries.join(', ')
  end

  def summary
    summary = []
    %w[bookmarkable_query bookmark_query].each do |key|
      if options[key].present?
        summary << options[key]
      end
    end
    if options[:bookmarker].present?
      summary << "Bookmarker: #{options[:bookmarker]}"
    end
    if options[:notes].present?
      summary << "Notes: #{options[:notes]}"
    end
    tags = []
    %w[other_tag_names other_bookmark_tag_names].each do |key|
      if options[key].present?
        tags << options[key]
      end
    end
    all_tag_ids = []
    [:filter_ids, :fandom_ids, :rating_ids, :category_ids, :archive_warning_ids, :character_ids, :relationship_ids, :freeform_ids].each do |tag_ids|
      if options[tag_ids].present?
        all_tag_ids += options[tag_ids]
      end
    end
    unless all_tag_ids.empty?
      tags << Tag.where(id: all_tag_ids).pluck(:name).join(", ")
    end
    unless tags.empty?
      summary << "Tags: #{tags.uniq.join(", ")}"
    end
    if self.bookmarkable_type.present?
      summary << "Type: #{self.bookmarkable_type}"
    end
    if self.language_id.present?
      language = Language.find_by(short: self.language_id)
      if language.present?
        summary << "Work language: #{language.name}"
      end
    end
    if %w(1 true).include?(self.rec.to_s)
      summary << "Rec"
    end
    if %w(1 true).include?(self.with_notes.to_s)
      summary << "With Notes"
    end
    [:date, :bookmarkable_date].each do |countable|
      if options[countable].present?
        desc = (countable == :date) ? "Date bookmarked" : "Date updated"
        summary << "#{desc}: #{options[countable]}"
      end
    end
    summary.join(", ")
  end

  def search_results
    @searcher.search_results
  end

  # Special function for returning the BookmarkableQuery results (instead of
  # the BookmarkQuery results).
  def bookmarkable_search_results
    @searcher.bookmarkable_query.search_results
  end

  ###############
  # SORTING
  ###############

  def sort_options
    [
      ['Date Bookmarked', 'created_at'],
      ['Date Updated', 'bookmarkable_date'],
    ]
  end

  def sort_values
    sort_options.map{ |option| option.last }
  end

  def sort_direction(sort_column)
    'desc'
  end

  private

  def processed_options(opts = {})
    [:date, :bookmarkable_date].each do |countable|
      if opts[countable].present?
        opts[countable] = opts[countable].gsub("&gt;", ">").
                                          gsub("&lt;", "<")
      end
    end

    # If we call the form field 'notes', the parser adds html to it
    opts[:notes] = opts[:bookmark_notes]

    opts = standardize_language_ids(opts)

    # Support legacy warning searches
    if opts[:warning_ids].present?
      opts[:archive_warning_ids] = opts.delete(:warning_ids)
    end

    # We need to respect some options that are deliberately set to false, and
    # false.blank? is true, so we check for nil? and not blank? here.
    opts.delete_if { |_, v| v.nil? }
  end

  # Maintain backward compatibility for old bookmark searches/filters:
  def standardize_language_ids(opts)
    # - Using language IDs in the "Work language" dropdown
    if opts[:language_id].present? && opts[:language_id].to_i != 0
      language = Language.find_by(id: opts[:language_id])
      opts[:language_id] = language.short if language.present?
    end

    # - Using language IDs in "Any field on work" (search) or "Search within results" (filters)
    if opts[:bookmarkable_query].present?
      opts[:bookmarkable_query] = opts[:bookmarkable_query].gsub(/\blanguage_id\s*:\s*(\d+)/) do
        lang = Language.find_by(id: Regexp.last_match[1])
        lang = Language.default if lang.blank?
        "language_id: " + lang.short
      end
    end
    opts
  end
end
