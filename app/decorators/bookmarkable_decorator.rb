class BookmarkableDecorator < SimpleDelegator
  attr_accessor :inner_hits, :loaded_bookmarks

  # Given a list of bookmarkable objects, with inner bookmark hits, load the
  # objects and their bookmarks and wrap everything up in a
  # BookmarkableDecorator object.
  def self.load_from_elasticsearch(hits, **options)
    bookmarks = load_bookmarks(hits, **options)
    bookmarkables = load_bookmarkables(hits, **options)

    hits.map do |hit|
      id = hit["_id"]
      next if bookmarkables[id].blank?
      new_with_inner_hits(
        bookmarkables[id],
        hit.dig("inner_hits", "bookmark"),
        bookmarks
      )
    end.compact
  end

  # Given search results for bookmarkables, with inner_hits for the matching
  # bookmarks, load all of the referenced bookmarks and return a hash mapping
  # from IDs to bookmarks.
  def self.load_bookmarks(hits, **options)
    all_bookmark_hits = hits.flat_map do |bookmarkable_item|
      bookmarkable_item.dig("inner_hits", "bookmark", "hits", "hits")
    end

    Bookmark.load_from_elasticsearch(all_bookmark_hits, **options).group_by(&:id)
  end

  # Given search results for bookmarkables, return a hash mapping from IDs to
  # Works, Series, or ExternalWorks (depending on which type the ID is marked
  # with).
  def self.load_bookmarkables(hits, **options)
    hits_by_bookmarkable_type = hits.group_by do |item|
      item.dig("_source", "bookmarkable_type")
    end

    bookmarkables = {}

    [Work, Series, ExternalWork].each do |klass|
      hits_for_klass = hits_by_bookmarkable_type[klass.to_s]
      next if hits_for_klass.blank?
      type = klass.to_s.underscore
      klass.load_from_elasticsearch(hits_for_klass, **options).each do |item|
        bookmarkables["#{item.id}-#{type}"] = item
      end
    end

    bookmarkables
  end

  # Create a new bookmarkable decorator with information about this
  # bookmarkable's inner hits, and a hash of pre-loaded bookmarks.
  def self.new_with_inner_hits(bookmarkable, inner_hits, bookmarks)
    new(bookmarkable).tap do |decorator|
      decorator.inner_hits = inner_hits
      decorator.loaded_bookmarks = bookmarks
    end
  end

  # Return the number of inner bookmarks matching the query.
  def matching_bookmark_count
    @matching_bookmark_count ||= inner_hits.dig("hits", "total")
  end

  # Return a small sampling of inner bookmarks matching the query.
  def matching_bookmarks
    @matching_bookmarks = inner_hits.dig("hits", "hits").flat_map do |hit|
      loaded_bookmarks[hit["_id"].to_i]
    end.compact
  end
end
