class FavoriteTag < ApplicationRecord
  belongs_to :user
  belongs_to :tag

  validates :user_id, presence: true
  validates :tag_id, presence: true,
                     uniqueness: { scope: :user_id,
                                   message: ts("is already in your favorite tags.")
                                 }

  validate :within_limit, on: :create
  def within_limit
    if user && user.favorite_tags.reload.count >= ArchiveConfig.MAX_FAVORITE_TAGS
      errors.add(:base,
                 ts("Sorry, you can only save %{maximum} favorite tags.",
                    maximum: ArchiveConfig.MAX_FAVORITE_TAGS))
    end
  end

  validate :canonical, on: :create
  def canonical
    unless tag && tag.canonical?
      errors.add(:base, "Sorry, you can only add canonical tags to your favorite tags.")
    end
  end

  after_save :expire_cached_home_favorite_tags
  after_destroy :expire_cached_home_favorite_tags

  def tag
    Tag.find_by(id: tag_id)
  end

  def tag_name
    tag = self.tag
    tag.name
  end

  private

  def expire_cached_home_favorite_tags
    unless Rails.env.development?
      Rails.cache.delete("home/index/#{user_id}/home_favorite_tags")
    end
  end
end
