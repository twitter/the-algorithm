module SkinCacheHelper
  def cache_timestamp
    Time.now.utc.to_s(:usec)
  end

  def skin_cache_version_key(skin_id)
    skin_id = skin_id.id if skin_id.is_a?(Skin)
    [:v1, :site_skin, skin_id, :version_key]
  end

  def skin_cache_version(skin_id)
    Rails.cache.fetch(skin_cache_version_key(skin_id)) do
      cache_timestamp
    end
  end

  def skin_cache_version_update(skin_id)
    Rails.cache.write(skin_cache_version_key(skin_id), cache_timestamp)
  end

  def skin_chooser_key
    [:v2, :skin_chooser]
  end

  def skin_chooser_expire_cache
    ActionController::Base.new.expire_fragment(skin_chooser_key)
  end
end
