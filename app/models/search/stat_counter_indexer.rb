# A class for reindexing work stats.

# Does not inherit from the standard Indexer, because it needs to do updates to
# existing records rather than creating whole records from scratch.
class StatCounterIndexer
  attr_reader :ids

  # Find StatCounter elasticsearch ids (StatCounters are stored by their
  # associated work_id) from provided StatCounter ActiveRecord object ids.
  def self.find_elasticsearch_ids(ids)
    StatCounter.where(work_id: ids).pluck(:id)
  end

  def initialize(ids)
    @ids = ids
  end

  def objects
    # Since we're updating works, the IDs of the individual stat counters don't
    # matter very much. If one of the stat counters that we're supposed to
    # reindex is missing from the database, there's nothing for us to delete --
    # it would only be destroyed if the corresponding work was destroyed, and
    # we're not responsible for cleaning up old works. (That's the
    # WorkIndexer's job.)
    @objects ||= StatCounter.where(id: ids).to_a
  end

  def batch
    return @batch if @batch

    @batch = []
    objects.each do |object|
      @batch << { update: routing_info(object) }
      @batch << document(object)
    end
    @batch
  end

  def index_documents
    return if batch.empty?

    $elasticsearch.bulk(body: batch)
  end

  # Use the routing information from the WorkIndexer, since we don't have an
  # index of our own. And use the work_id rather than our own id.
  def routing_info(stat_counter)
    {
      "_index" => WorkIndexer.index_name,
      "_id" => stat_counter.work_id
    }
  end

  # Since we're doing an update instead of an index, nest the values.
  def document(stat_counter)
    {
      doc: {
        hits: stat_counter.hit_count,
        comments_count: stat_counter.comments_count,
        kudos_count: stat_counter.kudos_count,
        bookmarks_count: stat_counter.bookmarks_count
      }
    }
  end
end
