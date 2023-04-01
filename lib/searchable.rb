module Searchable

  def self.included(searchable)
    searchable.class_eval do
      after_save :enqueue_to_index
      after_destroy :enqueue_to_index
    end
    searchable.extend(ClassMethods)
  end

  module ClassMethods
    # A class method to reindex every item in the current relation.
    def reindex_all(queue = :background)
      distinct.select(:id).find_in_batches do |batch|
        IndexQueue.enqueue_ids(base_class, batch.map(&:id), queue)
      end
    end

    def successful_reindex(ids)
      # override to do something in response
    end

    # Given search results from Elasticsearch, retrieve the corresponding hits
    # from the database, ordered the same way. (If the database items
    # corresponding to the search results don't exist, don't error, just notify
    # IndexSweeper so that the Elasticsearch indices can be cleaned up.)
    # Override for special behavior.
    def load_from_elasticsearch(hits, scopes: nil)
      ids = hits.map { |item| item['_id'] }

      # Apply the desired scopes to the ActiveRecord relation:
      relation = self.all
      Array(scopes).each do |scope|
        relation = relation.public_send(scope)
      end

      # Find results with where rather than find in order to avoid
      # ActiveRecord::RecordNotFound
      items = relation.where(id: ids).group_by(&:id)
      IndexSweeper.async_cleanup(self, ids, items.keys)
      ids.flat_map { |id| items[id.to_i] }.compact
    end
  end

  def enqueue_to_index
    IndexQueue.enqueue(self, :main)
  end

  def indexers
    Indexer.for_object(self)
  end

  def reindex_document(options = {})
    responses = []
    self.indexers.each do |indexer|
      if options[:async]
        queue = options[:queue] || :main
        responses << AsyncIndexer.index(indexer, [id], queue)
      else
        responses << indexer.new([id]).index_document(self)
      end
    end
    responses
  end
end
