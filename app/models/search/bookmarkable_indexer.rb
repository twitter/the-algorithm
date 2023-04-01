class BookmarkableIndexer < Indexer

  def self.index_name
    "#{ArchiveConfig.ELASTICSEARCH_PREFIX}_#{Rails.env}_bookmarks"
  end

  def self.document_type
    'bookmark'
  end

  def self.mapping
    BookmarkIndexer.mapping
  end

  # When we fail, we don't want to just keep adding the -klass suffix.
  def self.find_elasticsearch_ids(ids)
    ids.map(&:to_i)
  end

  def routing_info(id)
    {
      "_index" => index_name,
      "_id" => document_id(id)
    }
  end

  def document(object)
    object.bookmarkable_json.merge(
      sort_id: document_id(object.id)
    )
  end

  def document_id(id)
    "#{id}-#{klass.underscore}"
  end

end
