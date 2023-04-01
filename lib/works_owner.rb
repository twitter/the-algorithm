# Used to generate cache keys for any works index page
# Include in models that can "own" works, eg ...tags/TAGNAME/works or users/LOGIN/works
module WorksOwner

  def self.included(base)
    base.extend(ClassMethods)
  end

  module ClassMethods
    # expire a bunch of keys without having to look up the objects in the database
    def expire_ids(ids)
      ids.each do |id|
        klass = (self.superclass == Tag) ? 'tag' : self.to_s.underscore
        REDIS_GENERAL.set("#{klass}_#{id}_windex", Time.now.to_i.to_s)
      end
    end
  end

  # Used in works_controller to determine whether to expire the cache for this object's works index page
  # The timestamp should reflect the last update that would cause the list to need refreshing
  # When both a collection and a tag are given, include both in the key and use the tag's timestamp
  def works_index_cache_key(tag=nil)
    key = "works_index_for_#{self.class.name.underscore}_#{self.id}_"
    if tag.present?
      key << "tag_#{tag.id}_#{tag.works_index_timestamp}"
    else
      key << "#{self.works_index_timestamp}"
    end
    key
  end

  # Set the timestamp if it doesn't yet exist
  def works_index_timestamp
    REDIS_GENERAL.get(redis_works_index_key) || update_works_index_timestamp!
  end

  # Should be called wherever works are updated
  # Making the timestamp a stringy integer mostly for ease of testing
  def update_works_index_timestamp!
    t = Time.now.to_i.to_s
    REDIS_GENERAL.set(redis_works_index_key, t)
    return t
  end

  private

  def redis_works_index_key
    klass = self.is_a?(Tag) ? 'tag' : self.class.to_s.underscore
    "#{klass}_#{self.id}_windex"
  end

end
