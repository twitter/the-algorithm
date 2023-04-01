# A class for reindexing private work creator info (info that should not be
# available during normal searches).
class WorkCreatorIndexer < Indexer
  def self.klass
    "Work"
  end

  def self.klass_with_includes
    Work.includes(:pseuds, :users)
  end

  def self.mapping
    WorkIndexer.mapping
  end

  # When we fail, we don't want to just keep adding the -klass suffix.
  def self.find_elasticsearch_ids(ids)
    ids.map(&:to_i)
  end

  def routing_info(id)
    {
      "_index" => index_name,
      "_id" => document_id(id),
      "routing" => parent_id(id, nil)
    }
  end

  def document_id(id)
    "#{id}-creator"
  end

  def parent_id(id, _object)
    id
  end

  def document(object)
    {
      private_user_ids: object.user_ids,
      private_pseud_ids: object.pseud_ids,
      creator_join: {
        name: :creator,
        parent: object.id
      }
    }
  end
end
