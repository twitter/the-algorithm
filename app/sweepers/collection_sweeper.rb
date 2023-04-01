class CollectionSweeper < ActionController::Caching::Sweeper
  observe Collection, CollectionItem, CollectionParticipant, CollectionProfile, Work

  def after_create(record)
    record.add_to_autocomplete if record.is_a?(Collection)
  end

  def after_update(record)
    if record.is_a?(Collection) && (record.saved_change_to_name? || record.saved_change_to_title?)
      Rails.logger.debug "Removing renamed collection from autocomplete: #{record.autocomplete_search_string_before_last_save}"
      record.remove_stale_from_autocomplete
      Rails.logger.debug "Adding renamed collection to autocomplete: #{record.autocomplete_search_string}"
      record.add_to_autocomplete
    end
  end

  def after_save(record)
    expire_collection_cache_for(record)
  end

  def before_destroy(record)
    record.remove_from_autocomplete if record.is_a?(Collection)
  end

  def after_destroy(record)
    expire_collection_cache_for(record)
  end

  private
  # return one or many collections associated with the changed record
  # converted into an array
  def get_collections_from_record(record)
    if record.is_a?(Collection)
      # send collection, its parent, and any children
      ([record, record.parent] + record.children).compact
    elsif record.respond_to?(:collection) && !record.collection.nil?
      ([record.collection, record.collection.parent] + record.collection.children).compact
    elsif record.respond_to?(:collections)
      (record.collections + record.collections.collect(&:parent) + record.collections.collect(&:children).flatten).compact
    else
      []
    end
  end

  # Whenever these records are updated, we need to blank out the collections cache
  def expire_collection_cache_for(record)
    collections = get_collections_from_record(record)
    collections.each do |collection|
      CollectionSweeper.expire_collection_blurb_and_profile(collection)
    end
  end

  # Expire the collection blurb and profile
  def self.expire_collection_blurb_and_profile(collection)
    # Expire both versions of the blurb, whether the user is logged in or not.
    %w[logged-in logged-out].each do |logged_in|
      cache_key = "collection-blurb-#{logged_in}-#{collection.id}-v3"
      ActionController::Base.new.expire_fragment(cache_key)
    end

    ActionController::Base.new.expire_fragment("collection-profile-#{collection.id}")
  end
end
