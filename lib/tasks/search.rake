# frozen_string_literal: true

namespace :search do
  BATCH_SIZE = 1000

  desc "Update all index mappings"
  task(update_all_mappings: :environment) do
    # If multiple indexers share an index and a mapping, we only need to call
    # create_mapping on one of them.
    Indexer.all.group_by(&:index_name).values.map(&:first).map(&:create_mapping)
  end

  desc "Recreate tag index"
  task(index_tags: :environment) do
    TagIndexer.index_all
  end

  desc "Recreate pseud index"
  task(index_pseuds: :environment) do
    PseudIndexer.index_all
  end

  desc "Recreate work index"
  task(index_works: :environment) do
    WorkIndexer.index_all
    WorkCreatorIndexer.index_from_db
  end

  desc "Recreate bookmark index"
  task(index_bookmarks: :environment) do
    BookmarkIndexer.index_all
  end

  desc "Reindex all works without recreating the index"
  task(reindex_works: :environment) do
    WorkIndexer.index_from_db
    WorkCreatorIndexer.index_from_db
  end

  desc "Reindex all bookmarkables without recreating the index"
  task(reindex_bookmarkables: :environment) do
    BookmarkedExternalWorkIndexer.index_from_db
    BookmarkedSeriesIndexer.index_from_db
    BookmarkedWorkIndexer.index_from_db
  end

  desc "Reindex all recently-modified items"
  task timed_all: %i[timed_works timed_tags timed_pseud timed_bookmarks] do
  end

  desc "Reindex recent bookmarks"
  task timed_bookmarks: :environment do
    time = ENV["TIME_PERIOD"] || "NOW() - INTERVAL 1 DAY"
    ExternalWork.where("external_works.updated_at >  #{time}").select(:id).find_in_batches(batch_size: BATCH_SIZE) do |group|
      AsyncIndexer.new(BookmarkedExternalWorkIndexer, :world).enqueue_ids(group.map(&:id))
    end
    Series.where("series.updated_at >  #{time}").select(:id).find_in_batches(batch_size: BATCH_SIZE) do |group|
      AsyncIndexer.new(BookmarkedSeriesIndexer, :world).enqueue_ids(group.map(&:id))
    end
    Work.where("works.revised_at >  #{time}").select(:id).find_in_batches(batch_size: BATCH_SIZE) do |group|
      AsyncIndexer.new(BookmarkedWorkIndexer, :world).enqueue_ids(group.map(&:id))
    end
    Bookmark.where("bookmarks.updated_at >  #{time}").select(:id).find_in_batches(batch_size: BATCH_SIZE) do |group|
      AsyncIndexer.new(BookmarkIndexer, :world).enqueue_ids(group.map(&:id))
    end
  end

  desc "Reindex recent works"
  task timed_works: :environment do
    time = ENV["TIME_PERIOD"] || "NOW() - INTERVAL 1 DAY"
    Work.where("works.revised_at >  #{time}").select(:id).find_in_batches(batch_size: BATCH_SIZE) do |group|
      AsyncIndexer.new(WorkIndexer, :world).enqueue_ids(group.map(&:id))
    end
  end

  desc "Reindex recent tags"
  task timed_tags: :environment  do
    time = ENV["TIME_PERIOD"] || "NOW() - INTERVAL 1 DAY"
    Tag.where("tags.updated_at >  #{time}").select(:id).find_in_batches(batch_size: BATCH_SIZE) do |group|
      AsyncIndexer.new(TagIndexer, :world).enqueue_ids(group.map(&:id))
    end
  end

  desc "Reindex pseuds"
  task timed_pseud: :environment do
    time = ENV["TIME_PERIOD"] || "NOW() - INTERVAL 1 DAY"
    Pseud.where("pseuds.updated_at >  #{time}").select(:id).find_in_batches(batch_size: BATCH_SIZE) do |group|
      AsyncIndexer.new(PseudIndexer, :world).enqueue_ids(group.map(&:id))
    end
  end

  desc "Run tasks enqueued to the world queue by IndexQueue."
  task run_world_index_queue: :environment do
    ScheduledReindexJob::MAIN_CLASSES.each do |klass|
      IndexQueue.from_class_and_label(klass, :world).run
    end
  end
end
