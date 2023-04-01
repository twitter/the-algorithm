module BookmarkCountCaching
  def key_for_public_bookmarks_count
    "/v1/public_bookmarks_count/#{self.id}"
  end

  def public_bookmarks_count
    Rails.cache.fetch(self.key_for_public_bookmarks_count) do
      self.bookmarks.is_public.count
    end
  end

  def invalidate_public_bookmarks_count
    Rails.cache.delete(self.key_for_public_bookmarks_count)
  end
end
