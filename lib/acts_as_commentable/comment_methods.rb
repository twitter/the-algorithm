module ActsAsCommentable::CommentMethods

  def self.included(comment)
    comment.class_eval do
      include InstanceMethods

      before_destroy :fix_threading_on_destroy
      after_destroy :check_can_destroy_parent
    end
  end

  module InstanceMethods

    # Gets the object (chapter, bookmark, etc.) that the comment ultimately belongs to
    def ultimate_parent
      self.parent
    end

    # gets the comment that is the parent of this thread
    def thread_parent
      self.reply_comment? ? self.commentable.thread_parent : self
    end

    # Only destroys childless comments, sets is_deleted to true for the rest
    def destroy_or_mark_deleted
      if self.children_count > 0
        self.is_deleted = true
        self.comment_content = "deleted comment" # wipe out the content
        self.save(validate: false)
      else
        self.destroy
      end
    end

    # Returns true if the comment is a reply to another comment
    def reply_comment?
      self.commentable_type == self.class.to_s
    end

    # Returns the total number of sub-comments
    def children_count
      self.threaded_right ? (self.threaded_right - self.threaded_left - 1)/2 : 0
    end

    # Returns all sub-comments plus the comment itself
    # Returns comment itself if unthreaded
    def full_set
      if self.threaded_left
        Comment.includes(:pseud).where("threaded_left BETWEEN (?) and (?) AND thread = (?)",
                            self.threaded_left, self.threaded_right, self.thread).order(:threaded_left)
      else
        return [self]
      end
    end

    # TODO: Remove when AO3-5939 is fixed.
    def set_to_freeze_or_unfreeze
      # Our set always starts with the comment we pressed the button on.
      comment_set = [self]

      # We're going to find all of the comments on @comment's ultimate parent
      # and then use the comments' commentables to figure which comments belong
      # to the set (thread) we are freezing or unfreezing.
      all_comments = self.ultimate_parent.find_all_comments

      # First, we'll loop through all_comments to find any direct replies to
      # self. Then we'll loop through again to find any direct replies to
      # _those_ replies. We'll repeat this until we find no more replies.
      newest_ids = [self.id]

      while newest_ids.present?
        child_comments_by_commentable = all_comments.where(commentable_id: newest_ids, commentable_type: "Comment")

        comment_set << child_comments_by_commentable unless child_comments_by_commentable.empty?
        newest_ids = child_comments_by_commentable.pluck(:id)
      end
      comment_set.flatten
    end

    # Returns all sub-comments
    def all_children
      self.children_count > 0 ? Comment.includes(:pseud).where("threaded_left > (?) and threaded_right < (?) and thread = (?)",
                                             self.threaded_left, self.threaded_right, self.thread).order(:threaded_left) : []
    end

    # Returns a full comment thread
    def full_thread
      Comment.where("thread = (?)", self.thread).order(:threaded_left)
    end


    # Adds a child to this object in the tree. This method will update all of the
    # other elements in the tree and shift them to the right, keeping everything
    # balanced.
    #
    # Skips validations so that we can reply to invalid comments.
    def add_child( child )
      if ( (self.threaded_left == nil) || (self.threaded_right == nil) )
        # Looks like we're now the root node!  Woo
        self.threaded_left = 1
        self.threaded_right = 4

        return nil unless save(validate: false)

        child.commentable_id = self.id
        child.threaded_left = 2
        child.threaded_right= 3
      else
        # OK, we need to add and shift everything else to the right
        child.commentable_id = self.id
        right_bound = self.threaded_right
        child.threaded_left = right_bound
        child.threaded_right = right_bound + 1
        self.threaded_right += 2
        # Updates all comments in the thread to set their relative positions
        Comment.transaction {
          Comment.where(["thread = (?) AND threaded_left >= (?)", self.thread, right_bound]).update_all("threaded_left = (threaded_left + 2)")
          Comment.where(["thread = (?) AND threaded_right >= (?)", self.thread, right_bound]).update_all("threaded_right = (threaded_right + 2)")
          save(validate: false)
        }
      end
    end

    # Adjusts left and right threading counts when a comment is deleted
    # otherwise, children_count is wrong
    def fix_threading_on_destroy
      Comment.transaction {
        Comment.where(["thread = (?) AND threaded_left > (?)", self.thread, self.threaded_left]).update_all("threaded_left = (threaded_left - 2)")
        Comment.where(["thread = (?) AND threaded_right > (?)", self.thread, self.threaded_right]).update_all("threaded_right = (threaded_right - 2)")
      }
    end

    # When we delete a comment, we may be deleting the last of our parent's
    # children. If our parent was marked as deleted (but not actually
    # destroyed), we may be able to destroy it.
    def check_can_destroy_parent
      # We're in the middle of a cascade deletion (e.g. a work is being destroyed),
      # so don't try to recursively reload and check our parents.
      return if destroyed_by_association

      immediate_parent = commentable.reload

      return unless immediate_parent.is_a?(Comment)
      return unless immediate_parent.is_deleted
      return unless immediate_parent.children_count.zero?

      immediate_parent.destroy
    end
  end
end
