class AdminPost < ApplicationRecord
  self.per_page = 8 # option for WillPaginate

  acts_as_commentable
  enum comment_permissions: {
    enable_all: 0,
    disable_anon: 1,
    disable_all: 2
  }, _suffix: :comments

  belongs_to :language
  belongs_to :translated_post, class_name: "AdminPost"
  has_many :translations, class_name: "AdminPost", foreign_key: "translated_post_id", dependent: :destroy
  has_many :admin_post_taggings
  has_many :tags, through: :admin_post_taggings, source: :admin_post_tag

  validates_presence_of :title
  validates_length_of :title,
    minimum: ArchiveConfig.TITLE_MIN,
    too_short: ts("must be at least %{min} characters long.", min: ArchiveConfig.TITLE_MIN)

  validates_length_of :title,
    maximum: ArchiveConfig.TITLE_MAX,
    too_long: ts("must be less than %{max} characters long.", max: ArchiveConfig.TITLE_MAX)

  validates_presence_of :content
  validates_length_of :content, minimum: ArchiveConfig.CONTENT_MIN,
    too_short: ts("must be at least %{min} characters long.", min: ArchiveConfig.CONTENT_MIN)

  validates_length_of :content, maximum: ArchiveConfig.CONTENT_MAX,
    too_long: ts("cannot be more than %{max} characters long.", max: ArchiveConfig.CONTENT_MAX)

  validate :translated_post_must_exist

  validate :translated_post_language_must_differ

  scope :non_translated, -> { where("translated_post_id IS NULL") }

  scope :for_homepage, -> { order("created_at DESC").limit(ArchiveConfig.NUMBER_OF_ITEMS_VISIBLE_ON_HOMEPAGE) }

  before_save :inherit_translated_post_comment_permissions, :inherit_translated_post_tags
  after_save :expire_cached_home_admin_posts, :update_translation_comment_permissions, :update_translation_tags
  after_destroy :expire_cached_home_admin_posts

  # Return the name to link comments to for this object
  def commentable_name
    self.title
  end
  def commentable_owners
    begin
        [Admin.find(self.admin_id)]
    rescue
      []
    end
  end

  def tag_list
    tags.map{ |t| t.name }.join(", ")
  end

  def tag_list=(list)
    return if translated_post_id.present?

    self.tags = list.split(",").uniq.collect { |t|
      AdminPostTag.fetch(name: t.strip, language_id: self.language_id, post: self)
      }.compact
  end

  def translated_post_must_exist
    if translated_post_id.present? && AdminPost.find_by(id: translated_post_id).nil?
      errors.add(:translated_post_id, "does not exist")
    end
  end

  def translated_post_language_must_differ
    return if translated_post.blank?
    return unless translated_post.language == language
          
    errors.add(:translated_post_id, "cannot be same language as original post")
  end

  private

  def expire_cached_home_admin_posts
    unless Rails.env.development?
      Rails.cache.delete("home/index/home_admin_posts")
    end
  end

  def inherit_translated_post_comment_permissions
    return if translated_post.blank?

    self.comment_permissions = translated_post.comment_permissions
  end

  def inherit_translated_post_tags
    return if translated_post.blank?

    self.tags = translated_post.tags
  end

  def update_translation_comment_permissions
    return if translations.blank?

    transaction do
      translations.find_each do |post|
        post.comment_permissions = self.comment_permissions
        post.save
      end
    end
  end

  def update_translation_tags
    return if translations.blank?

    transaction do
      translations.find_each do |post|
        post.tags = self.tags
        post.save
      end
    end
  end
end
