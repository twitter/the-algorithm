module ActsAsCommentable::CommentableEntity

  def self.included(commentable)
    commentable.class_eval do
      has_many :comments, as: :commentable, dependent: :destroy
      has_many :total_comments, class_name: 'Comment', as: :parent
      extend ClassMethods
    end
  end

  module ClassMethods
  end

  # Returns all comments
  def find_all_comments
    self.total_comments.order('thread, threaded_left')
  end

  # Returns the total number of comments
  def count_all_comments
    self.total_comments.count
  end

  # These below have all been redefined to work for the archive

  # The total number of visible comments on this commentable, not including
  # deleted comments, spam comments, unreviewed comments, and comments hidden
  # by an admin.
  #
  # This is the uncached version, and should only be used when calculating an
  # accurate value is important (e.g. when updating the StatCounter in the
  # database).
  def count_visible_comments_uncached
    self.total_comments.where(
      hidden_by_admin: false,
      is_deleted: false,
      unreviewed: false,
      approved: true
    ).count
  end

  # The total number of visible comments on this commentable. Cached to reduce
  # computation. The cache is manually expired whenever a comment is added,
  # removed, or changes visibility, but the cache also expires after a fixed
  # amount of time in case of issues with the cache (e.g. stale data when
  # calculating the count).
  def count_visible_comments
    @count_visible_comments ||=
      Rails.cache.fetch(count_visible_comments_key,
                        expires_in: ArchiveConfig.SECONDS_UNTIL_COMMENT_COUNTS_EXPIRE.seconds,
                        race_condition_ttl: 10.seconds) do
        count_visible_comments_uncached
      end
  end  

  def count_visible_comments_key
    "#{self.class.table_name}/#{self.id}/count_visible_comments"
  end

  def expire_comments_count
    @count_visible_comments = nil
    Rails.cache.delete(count_visible_comments_key)
  end

  # Return the name of this commentable object
  # Should be overridden in the implementing class if necessary
  def commentable_name
    begin
      self.title
    rescue
      ""
    end
  end

  def commentable_owners
    begin
      self.pseuds.map {|p| p.user}.uniq
    rescue
      begin
        [self.pseud.user]
      rescue
        []
      end
    end
  end

  # Return the email to reach the owner of this commentable object
  # Should be overridden in the implementing class if necessary
  def commentable_owner_email
    if self.commentable_owners.empty?
      begin
        self.email
      rescue
        ""
      end
    else
      self.commentable_owners.email.join(',')
    end
  end
end
