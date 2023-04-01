class Comment < ApplicationRecord
  include HtmlCleaner
  include AfterCommitEverywhere

  belongs_to :pseud
  belongs_to :commentable, polymorphic: true
  belongs_to :parent, polymorphic: true

  has_many :inbox_comments, foreign_key: 'feedback_comment_id', dependent: :destroy
  has_many :users, through: :inbox_comments

  has_many :reviewed_replies, -> { reviewed },
           class_name: "Comment", as: :commentable, inverse_of: :commentable

  has_many :thread_comments, class_name: 'Comment', foreign_key: :thread

  validates_presence_of :name, unless: :pseud_id
  validates :email, email_veracity: {on: :create, unless: :pseud_id}, email_blacklist: {on: :create, unless: :pseud_id}

  validates_presence_of :comment_content
  validates_length_of :comment_content,
    maximum: ArchiveConfig.COMMENT_MAX,
    too_long: ts("must be less than %{count} characters long.", count: ArchiveConfig.COMMENT_MAX)

  delegate :user, to: :pseud, allow_nil: true

  # Check if the writer of this comment is blocked by the writer of the comment
  # they're replying to:
  validates :user, not_blocked: {
    by: :commentable,
    if: :reply_comment?,
    unless: :on_tag?,
    message: :blocked_reply
  }

  # Check if the writer of this comment is blocked by one of the creators of
  # the work they're replying to:
  validates :user, not_blocked: {
    by: :ultimate_parent,
    unless: :on_tag?,
    message: :blocked_comment
  }

  def on_tag?
    parent_type == "Tag"
  end

  def by_anonymous_creator?
    ultimate_parent.try(:anonymous?) && user&.is_author_of?(ultimate_parent)
  end

  validate :check_for_spam, on: :create

  def check_for_spam
    errors.add(:base, ts("This comment looks like spam to our system, sorry! Please try again, or create an account to comment.")) unless check_for_spam?
  end

  validates :comment_content, uniqueness: {
    scope: [:commentable_id, :commentable_type, :name, :email, :pseud_id],
    unless: :is_deleted?,
    message: ts("^This comment has already been left on this work. (It may not appear right away for performance reasons.)")
  }

  scope :ordered_by_date, -> { order('created_at DESC') }
  scope :top_level,       -> { where.not(commentable_type: "Comment") }
  scope :include_pseud,   -> { includes(:pseud) }
  scope :not_deleted,     -> { where(is_deleted: false) }
  scope :reviewed,        -> { where(unreviewed: false) }
  scope :unreviewed_only, -> { where(unreviewed: true) }

  scope :for_display, lambda {
    includes(
      pseud: { user: [:roles, :block_of_current_user, :block_by_current_user] },
      parent: { work: [:pseuds, :users] }
    )
  }

  # Gets methods and associations from acts_as_commentable plugin
  acts_as_commentable
  has_comment_methods

  def akismet_attributes
    {
      key: ArchiveConfig.AKISMET_KEY,
      blog: ArchiveConfig.AKISMET_NAME,
      user_ip: ip_address,
      user_agent: user_agent,
      comment_author: name,
      comment_author_email: email,
      comment_content: comment_content
    }
  end

  after_create :expire_parent_comments_count
  after_update :expire_parent_comments_count, if: :saved_change_to_visibility?
  after_destroy :expire_parent_comments_count
  def expire_parent_comments_count
    after_commit { parent&.expire_comments_count }
  end

  def saved_change_to_visibility?
    pertinent_attributes = %w[is_deleted hidden_by_admin unreviewed approved]
    (saved_changes.keys & pertinent_attributes).present?
  end

  before_validation :set_parent_and_unreviewed, on: :create

  before_create :set_depth
  before_create :set_thread_for_replies
  before_create :set_parent_and_unreviewed
  after_create :update_thread
  before_create :adjust_threading, if: :reply_comment?

  after_create :update_work_stats
  after_destroy :update_work_stats

  # If a comment has changed too much, we might need to put it back in moderation:
  before_update :recheck_unreviewed
  def recheck_unreviewed
    return unless edited_at_changed? &&
                  comment_content_changed? &&
                  moderated_commenting_enabled? &&
                  !is_creator_comment? &&
                  content_too_different?(comment_content, comment_content_was)

    self.unreviewed = true
  end

  after_update :after_update
  def after_update
    users = []
    admins = []

    if self.saved_change_to_edited_at? || (self.saved_change_to_unreviewed? && !self.unreviewed?)
      # Reply to owner of parent comment if this is a reply comment
      # Potentially we are notifying the original commenter of a newly-approved reply to their comment
      if (parent_comment_owner = notify_parent_comment_owner)
        users << parent_comment_owner
      end
    end

    if self.saved_change_to_edited_at?
      # notify the commenter
      if self.comment_owner && notify_user_of_own_comments?(self.comment_owner)
        users << self.comment_owner
      end
      if notify_user_by_email?(self.comment_owner) && notify_user_of_own_comments?(self.comment_owner)
        CommentMailer.comment_sent_notification(self).deliver_after_commit
      end

      # send notification to the owner(s) of the ultimate parent, who can be users or admins
      if self.ultimate_parent.is_a?(AdminPost)
        AdminMailer.edited_comment_notification(self.id).deliver_after_commit
      else
        # at this point, users contains those who've already been notified
        if users.empty?
          users = self.ultimate_parent.commentable_owners
        else
          # replace with the owners of the commentable who haven't already been notified
          users = self.ultimate_parent.commentable_owners - users
        end
        users.each do |user|
          unless user == self.comment_owner && !notify_user_of_own_comments?(user)
            if notify_user_by_email?(user) || self.ultimate_parent.is_a?(Tag)
              CommentMailer.edited_comment_notification(user, self).deliver_after_commit
            end
            if notify_user_by_inbox?(user)
              update_feedback_in_inbox(user)
            end
          end
        end
      end

    end
  end

  after_create :after_create
  def after_create
    self.reload
    # eventually we will set the locale to the user's stored language of choice
    #Locale.set ArchiveConfig.SUPPORTED_LOCALES[ArchiveConfig.DEFAULT_LOCALE]
    users = []
    admins = []

    # notify the commenter
    if self.comment_owner && notify_user_of_own_comments?(self.comment_owner)
      users << self.comment_owner
    end
    if notify_user_by_email?(self.comment_owner) && notify_user_of_own_comments?(self.comment_owner)
      CommentMailer.comment_sent_notification(self).deliver_after_commit
    end

    # Reply to owner of parent comment if this is a reply comment
    if (parent_comment_owner = notify_parent_comment_owner)
      users << parent_comment_owner
    end

    # send notification to the owner(s) of the ultimate parent, who can be users or admins
    if self.ultimate_parent.is_a?(AdminPost)
      AdminMailer.comment_notification(self.id).deliver_after_commit
    else
      # at this point, users contains those who've already been notified
      if users.empty?
        users = self.ultimate_parent.commentable_owners
      else
        # replace with the owners of the commentable who haven't already been notified
        users = self.ultimate_parent.commentable_owners - users
      end
      users.each do |user|
        unless user == self.comment_owner && !notify_user_of_own_comments?(user)
          if notify_user_by_email?(user) || self.ultimate_parent.is_a?(Tag)
            CommentMailer.comment_notification(user, self).deliver_after_commit
          end
          if notify_user_by_inbox?(user)
            add_feedback_to_inbox(user)
          end
        end
      end
    end
  end

  after_create :record_wrangling_activity, if: :on_tag?
  def record_wrangling_activity
    self.comment_owner&.update_last_wrangling_activity
  end

  protected

    def notify_user_of_own_comments?(user)
      if user.nil? || user == User.orphan_account
        false
      elsif user.is_a?(Admin)
        true
      else
        !user.preference.comment_copy_to_self_off?
      end
    end

    def notify_user_by_inbox?(user)
      if user.nil? || user == User.orphan_account
        false
      elsif user.is_a?(Admin)
        true
      else
        !user.preference.comment_inbox_off?
      end
    end

    def notify_user_by_email?(user)
      if user.nil? || user == User.orphan_account
        false
      elsif user.is_a?(Admin)
        true
      else
        !user.preference.comment_emails_off?
      end
    end

    def update_feedback_in_inbox(user)
      if (edited_feedback = user.inbox_comments.find_by(feedback_comment_id: self.id))
        edited_feedback.update_attribute(:read, false)
      else # original inbox comment was deleted
        add_feedback_to_inbox(user)
      end
    end

    def add_feedback_to_inbox(user)
      new_feedback = user.inbox_comments.build
      new_feedback.feedback_comment_id = self.id
      new_feedback.save
    end

    def content_too_different?(new_content, old_content)
      # we added more than the threshold # of chars, just return
      return true if new_content.length > (old_content.length + ArchiveConfig.COMMENT_MODERATION_THRESHOLD)

      # quick and dirty iteration to compare the two strings
      cost = 0
      new_i = 0
      old_i = 0
      while new_i < new_content.length && old_i < old_content.length
        if new_content[new_i] == old_content[old_i]
          new_i += 1
          old_i += 1
          next
        end

        cost += 1
        # interrupt as soon as we have changed > threshold chars
        return true if cost > ArchiveConfig.COMMENT_MODERATION_THRESHOLD

        # peek ahead to see if we can catch up on either side eg if a letter has been inserted/deleted
        if new_content[new_i + 1] == old_content[old_i]
          new_i += 1
        elsif new_content[new_i] == old_content[old_i + 1]
          old_i += 1
        else
          # just keep going
          new_i += 1
          old_i += 1
        end
      end

      return cost > ArchiveConfig.COMMENT_MODERATION_THRESHOLD
    end

    def not_user_commenter?(parent_comment)
      (!parent_comment.comment_owner && parent_comment.comment_owner_email && parent_comment.comment_owner_name)
    end

    def have_different_owner?(parent_comment)
      return not_user_commenter?(parent_comment) || (parent_comment.comment_owner != self.comment_owner)
    end

    def notify_parent_comment_owner
      if self.reply_comment? && !self.unreviewed?
        parent_comment = self.commentable
        parent_comment_owner = parent_comment.comment_owner # will be nil if not a user, including if an admin

        # if I'm replying to a comment you left for me, mark your comment as replied to in my inbox
        if self.comment_owner
          if (inbox_comment = self.comment_owner.inbox_comments.find_by(feedback_comment_id: parent_comment.id))
            inbox_comment.update(replied_to: true, read: true)
          end
        end

        # send notification to the owner of the original comment if they're not the same as the commenter
        if (have_different_owner?(parent_comment))
          if !parent_comment_owner || notify_user_by_email?(parent_comment_owner) || self.ultimate_parent.is_a?(Tag)
            if self.saved_change_to_edited_at?
              CommentMailer.edited_comment_reply_notification(parent_comment, self).deliver_after_commit
            else
              CommentMailer.comment_reply_notification(parent_comment, self).deliver_after_commit
            end
          end
          if parent_comment_owner && notify_user_by_inbox?(parent_comment_owner)
            if self.saved_change_to_edited_at?
              update_feedback_in_inbox(parent_comment_owner)
            else
              add_feedback_to_inbox(parent_comment_owner)
            end
          end
          if parent_comment_owner
            return parent_comment_owner
          end
        end
        return nil
      end
    end

  public

  # Set the depth of the comment: 0 for a first-class comment, increasing with each level of nesting
  def set_depth
    self.depth = self.reply_comment? ? self.commentable.depth + 1 : 0
  end

  # The thread value for a reply comment should be the same as its parent comment
  def set_thread_for_replies
    self.thread = self.commentable.thread if self.reply_comment?
  end

  # Save the ultimate parent and reviewed status
  def set_parent_and_unreviewed
    self.parent = self.reply_comment? ? self.commentable.parent : self.commentable
    # we only mark comments as unreviewed if moderated commenting is enabled on their parent
    self.unreviewed = self.parent.respond_to?(:moderated_commenting_enabled?) &&
                      self.parent.moderated_commenting_enabled? &&
                      !User.current_user.try(:is_author_of?, self.ultimate_parent)
    true
  end

  # is this a comment by the creator of the ultimate parent
  def is_creator_comment?
    pseud && pseud.user && pseud.user.try(:is_author_of?, ultimate_parent)
  end

  def moderated_commenting_enabled?
    parent.respond_to?(:moderated_commenting_enabled?) && parent.moderated_commenting_enabled?
  end

  # We need a unique thread id for replies, so we'll make use of the fact
  # that ids are unique
  def update_thread
    self.update_attribute(:thread, self.id) unless self.thread
  end

  def adjust_threading
    self.commentable.add_child(self)
  end

  # Is this a first-class comment?
  def top_level?
    !self.reply_comment?
  end

  def comment_owner
    self.pseud.try(:user)
  end

  def comment_owner_name
    self.pseud.try(:name) || self.name
  end

  def comment_owner_email
    comment_owner.try(:email) || self.email
  end

  # override this method from commentable_entity.rb
  # to return the name of the ultimate parent this is on
  # we have to do this somewhat roundabout because until the comment is
  # set and saved, the ultimate_parent method will not work (the thread is not set)
  # and this is being called from before then.
  def commentable_name
    self.reply_comment? ? self.commentable.ultimate_parent.commentable_name : self.commentable.commentable_name
  end

  # override this method from comment_methods.rb to return ultimate
  alias :original_ultimate_parent :ultimate_parent
  def ultimate_parent
    myparent = self.original_ultimate_parent
    myparent.kind_of?(Chapter) ? myparent.work : myparent
  end

  def self.commentable_object(commentable)
    commentable.kind_of?(Work) ? commentable.last_posted_chapter : commentable
  end

  def find_all_comments
    self.all_children
  end

  def count_all_comments
    self.children_count
  end

  def count_visible_comments
    self.children_count #FIXME
  end

  def check_for_spam?
    #don't check for spam while running tests or if the comment is 'signed'
    self.approved = Rails.env.test? || !self.pseud_id.nil? || !Akismetor.spam?(akismet_attributes)
  end

  def mark_as_spam!
    update_attribute(:approved, false)
    # don't submit spam reports unless in production mode
    Rails.env.production? && Akismetor.submit_spam(akismet_attributes)
  end

  def mark_as_ham!
    update_attribute(:approved, true)
    # don't submit ham reports unless in production mode
    Rails.env.production? && Akismetor.submit_ham(akismet_attributes)
  end

  # Freeze single comment.
  def mark_frozen!
    update_attribute(:iced, true)
  end

  # Unfreeze single comment.
  def mark_unfrozen!
    update_attribute(:iced, false)
  end

  def mark_hidden!
    update_attribute(:hidden_by_admin, true)
  end

  def mark_unhidden!
    update_attribute(:hidden_by_admin, false)
  end

  def sanitized_content
    sanitize_field self, :comment_content
  end
  include Responder
end
