# This is currently being tested without loading most of Rails
# so beware of changes that rely on other classes or Rails methods
class QueryCleaner

  attr_reader :params

  SORT_OPTIONS = [
    ['Author', 'authors_to_sort_on'],
    ['Title', 'title_to_sort_on'],
    ['Date Posted', 'created_at'],
    ['Date Updated', 'revised_at'],
    ['Word Count', 'word_count'],
    ['Hits', 'hits'],
    ['Kudos', 'kudos_count'],
    ['Comments', 'comments_count'],
    ['Bookmarks', 'bookmarks_count']
  ].freeze

  def initialize(params = {})
    @params = params.dup
  end

  def clean
    return params if params[:query].nil? || params[:query].empty?
    unescape_angle_brackets
    extract_countables
    set_sorting
    add_quotes_to_categories
    escape_angle_brackets
    clean_up_query

    params
  end

  private

  def unescape_angle_brackets
    params[:query] = params[:query].gsub('&gt;', '>').gsub('&lt;', '<')
  end

  def escape_angle_brackets
    params[:query] = params[:query].gsub('>', '&gt;').gsub('<', '&lt;')
  end

  # extract countable params
  def extract_countables
    %w(word kudo comment bookmark hit).each do |term|
      count_regex = /#{term}s?\s*(?:\_?count)?\s*:?\s*((?:<|>|=|:)\s*\d+(?:\-\d+)?)/i
      m = params[:query].match(count_regex)
      next if m.nil?
      params[:query] = params[:query].gsub(count_regex, '')
      # pluralize, add _count, convert to symbol
      term = term.pluralize unless term == 'word'
      term += '_count' unless term == 'hits'
      term = term.to_sym

      value = m[1].gsub(/^(:|=)/, '').strip # get rid of : and =
      # don't overwrite if submitting from advanced search?
      params[term] = value if params[term].nil? || params[term].empty?
    end
  end

  # get sort-by
  def set_sorting
    sort_regex = /sort(?:ed)?\s*(?:by)?\s*:?\s*(<|>|=|:)\s*(\w+)\s*(ascending|descending)?/i
    return unless m = params[:query].match(sort_regex)
    params[:query] = params[:query].gsub(sort_regex, '')
    sortdir = m[3] || m[1]
    # turn word_count or word count or words into just "word" eg
    sortby = m[2].gsub(/\s*_?count/, '').singularize

    sort_column = SORT_OPTIONS.find { |opt, _| opt =~ /#{sortby}/i }&.last
    params[:sort_column] = sort_column unless sort_column.nil?
    params[:sort_direction] = sort_direction(sortdir)
  end

  def sort_direction(sortdir)
    if sortdir == '>' || sortdir == 'ascending'
      'asc'
    elsif sortdir == '<' || sortdir == 'descending'
      'desc'
    end
  end

  # put categories into quotes
  # don't match if the letters are part of larger words (ie, "Tom/Mark")
  def add_quotes_to_categories
    qr = Regexp.new('(?:"|\')?')
    %w(m/m f/f f/m m/f).each do |cat|
      cr = Regexp.new("(\\A|\\s)#{qr}#{cat}#{qr}(\\z|\\s)", Regexp::IGNORECASE)
      params[:query] = params[:query].gsub(cr, " \"#{cat}\" ")
    end
  end

  # If we've stripped out everything in the query, null it out
  def clean_up_query
    if params[:query] =~ /^\s*$/
      params[:query] = nil
    else
      params[:query] = params[:query].strip
    end
  end
end
