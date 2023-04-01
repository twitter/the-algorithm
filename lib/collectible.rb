module Collectible

  def self.included(collectible)
    collectible.class_eval do

      has_many :collection_items, as: :item, inverse_of: :item
      accepts_nested_attributes_for :collection_items, allow_destroy: true
      has_many :approved_collection_items, -> { approved_by_both }, class_name: "CollectionItem", as: :item
      has_many :user_approved_collection_items, -> { approved_by_user }, class_name: "CollectionItem", as: :item

      has_many :collections,
               through: :collection_items,
               after_add: :set_visibility,
               after_remove: :set_visibility,
               before_remove: :destroy_collection_item
      has_many :approved_collections,
               through: :approved_collection_items,
               source: :collection
      has_many :user_approved_collections,
               through: :user_approved_collection_items,
               source: :collection
      has_many :rejected_collections,
               -> { CollectionItem.rejected_by_user },
               through: :collection_items,
               source: :collection

      # Note: this scope includes the items in the children of the specified collection
      scope :in_collection, lambda { |collection|
        distinct.joins(:approved_collection_items).merge(collection.all_items)
      }

      after_destroy :clean_up_collection_items
    end
  end

  # add collections based on a comma-separated list of names
  def collections_to_add=(collection_names)
    old_collections = self.collection_items.collect(&:collection_id)
    names = trim_collection_names(collection_names)
    names.each do |name|
      c = Collection.find_by(name: name)
      errors.add(:base, ts("We couldn't find the collection %{name}.", name: name)) and return if c.nil?
      if c.closed?
        errors.add(:base, ts("The collection %{name} is not currently open.", name: name)) and return unless c.user_is_maintainer?(User.current_user) || old_collections.include?(c.id)
      end
      add_to_collection(c)
    end
  end

  # remove collections based on an array of ids
  def collections_to_remove=(collection_ids)
    collection_ids.reject {|id| id.blank?}.map {|id| id.is_a?(String) ? id.strip : id}.each do |id|
      c = Collection.find(id) || nil
      remove_from_collection(c)
    end
  end
  def collections_to_add; nil; end
  def collections_to_remove; nil; end

  def add_to_collection(collection)
    if collection && !self.collections.include?(collection)
      self.collections << collection
    end
  end

  def remove_from_collection(collection)
    if collection && self.collections.include?(collection)
      self.collections -= [collection]
    end
  end

  private
  def trim_collection_names(names)
    names.split(',').map{ |name| name.strip }.reject {|name| name.blank?}
  end

  public
  # Set ALL of an item's collections based on a list of collection names
  # Refactored to use collections_to_(add,remove) above so we only have one set of code
  #  performing the actual add/remove actions
  # This method now just does the convenience work of getting the removed ids -- any missing collections
  # will be identified
  # IMPORTANT: cannot delete all existing collections, or else items in closed collections
  # can't be edited
  def collection_names=(new_collection_names)
    new_names = trim_collection_names(new_collection_names)
    remove_ids = self.collections.reject {|c| new_names.include?(c.name)}.collect(&:id)
    self.collections_to_add = new_names.join(",")
    self.collections_to_remove = remove_ids
  end

  # NOTE: better to use collections_to_add/remove above instead for more consistency
  def collection_names
    @collection_names ? @collection_names : self.collections.collect(&:name).uniq.join(",")
  end


  #### UNREVEALED/ANONYMOUS

  # Set the anonymous/unrevealed status of the collectible based on its collections
  # We can't check for user approval because the collection item doesn't exist
  # and don't need to because this only gets called when the work is a new record and
  # therefore being created by its author
  def set_anon_unrevealed
    if self.respond_to?(:in_anon_collection) && self.respond_to?(:in_unrevealed_collection)
      # if we have collection items saved here then the collectible is not a new object
      if self.id.nil? || self.collection_items.empty?
        self.in_anon_collection = !self.collections.select(&:anonymous?).empty?
        self.in_unrevealed_collection = !self.collections.select(&:unrevealed?).empty?
      else
        update_anon_unrevealed
      end
    end
    return true
  end

  # TODO: need a better, DRY, long-term fix
  # Collection items can be revealed independently of a collection, so we don't want
  # to check the collection status when those are updated
  # Only include collections approved by the user
  def update_anon_unrevealed
    if self.respond_to?(:in_anon_collection) && self.respond_to?(:in_unrevealed_collection)
      self.in_anon_collection = self.user_approved_collection_items.anonymous.any?
      self.in_unrevealed_collection = self.user_approved_collection_items.unrevealed.any?
    end
  end

  #### CALLBACKS

  # Calculate (but don't save) whether this work should be anonymous and/or
  # unrevealed. Saving the results of this will be handled when the work saves,
  # or by the collection item's callbacks.
  def set_visibility(collection)
    set_anon_unrevealed
  end

  # We want to do this after the work is deleted to avoid issues with
  # accidentally trying to reveal the work during deletion (which wouldn't
  # successfully reveal the work because it'd fail while trying to save the
  # partially invalid work, but would cause an error).
  def clean_up_collection_items
    self.collection_items.destroy_all
  end

  # Destroy the collection item before the collection is deleted, so that we
  # trigger the CollectionItem's after_destroy callbacks.
  def destroy_collection_item(collection)
    self.collection_items.find_by(collection: collection).try(:destroy)
  end
end
