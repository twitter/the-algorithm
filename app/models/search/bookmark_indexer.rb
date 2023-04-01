class BookmarkIndexer < Indexer

  def self.klass
    "Bookmark"
  end

  # Create the bookmarkable index/mapping first
  # Skip delete on the subclasses so it doesn't delete the ones we've just
  # reindexed
  def self.index_all(options = {})
    unless options[:skip_delete]
      options[:skip_delete] = true
      BookmarkableIndexer.delete_index
      BookmarkableIndexer.create_index(shards: 18)
      create_mapping
    end
    BookmarkedExternalWorkIndexer.index_all(skip_delete: true)
    BookmarkedSeriesIndexer.index_all(skip_delete: true)
    BookmarkedWorkIndexer.index_all(skip_delete: true)
    super
  end

  def self.mapping
    {
      properties: {
        bookmarkable_join: {
          type: "join",
          relations: {
            bookmarkable: "bookmark"
          }
        },
        title: {
          type: "text",
          analyzer: "simple"
        },
        creators: {
          type: "text",
          analyzer: "simple"
        },
        work_types: {
          type: "keyword"
        },
        bookmarkable_type: {
          type: "keyword"
        },
        bookmarker: {
          type: "text",
          analyzer: "simple"
        },
        tag: {
          type: "text",
          analyzer: "simple"
        },
        sort_id: {
          type: "keyword"
        }
      }
    }
  end

  ####################
  # INSTANCE METHODS
  ####################

  def routing_info(id)
    object = objects[id.to_i]
    {
      "_index" => index_name,
      "_id" => id,
      "routing" => parent_id(id, object)
    }
  end

  def parent_id(id, object)
    if object.nil?
      deleted_bookmark_info(id)
    else
      "#{object.bookmarkable_id}-#{object.bookmarkable_type.underscore}"
    end
  end

  def document(object)
    tags = object.tags
    json_object = object.as_json(
      root: false,
      only: [
        :id, :created_at, :bookmarkable_type, :bookmarkable_id, :user_id,
        :private, :updated_at, :hidden_by_admin, :pseud_id, :rec
      ],
      methods: [:bookmarker, :collection_ids, :with_notes, :bookmarkable_date]
    ).merge(
      user_id: object.pseud&.user_id,
      tag: tags.map(&:name),
      tag_ids: tags.map(&:id),
      notes: object.bookmarker_notes
    )

    unless parent_id(object.id, object).match("deleted")
      json_object.merge!(
        bookmarkable_join: {
          name: "bookmark",
          parent: parent_id(object.id, object)
        }
      )
    end

    json_object
  end

  def deleted_bookmark_info(id)
    REDIS_GENERAL.get("deleted_bookmark_parent_#{id}")
  end
end
