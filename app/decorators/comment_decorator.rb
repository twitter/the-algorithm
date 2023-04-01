class CommentDecorator < SimpleDelegator
  # Given an array of thread IDs, loads all comments in those threads, wraps
  # all of them in CommentDecorators, and sets up reviewed_replies for all
  # comments. Returns a hash mapping from comment IDs to CommentDecorators.
  def self.from_thread_ids(thread_ids)
    wrapped_by_id = {}

    # Order by [thread, id] so that the comments in a thread are processed in
    # the order they were posted. This will ensure that when we process a
    # reply, its commentable has already been processed, and will also ensure
    # that the replies to a comment are displayed in the correct order:
    Comment.for_display.where(thread: thread_ids).order(:thread, :id).each do |comment|
      wrapped = CommentDecorator.new(comment)
      wrapped_by_id[comment.id] = wrapped

      next unless comment.reply_comment?

      wrapped_by_id[comment.commentable_id].comments << wrapped
    end

    wrapped_by_id
  end

  # Given an array of thread IDs, loads them with from_thread_ids, and then
  # replaces the contents of the array with the CommentDecorators for each
  # thread root.
  def self.wrap_thread_ids(thread_ids)
    comments_by_id = from_thread_ids(thread_ids)

    wrapped = thread_ids.map do |id|
      comments_by_id[id]
    end

    thread_ids.replace(wrapped)
  end

  # Given an array of comments, loads the threads associated with those
  # comments using from_thread_ids, and then replaces the contents of the array
  # with the decorated version of those comments.
  def self.wrap_comments(comments)
    comments_by_id = from_thread_ids(comments.map(&:thread))

    wrapped = comments.map do |comment|
      comments_by_id[comment.id]
    end

    comments.replace(wrapped)
  end

  # Given a commentable, gets the desired page of threads for that commentable,
  # and then loads all of the comments for those threads using wrap_thread_ids.
  def self.for_commentable(commentable, page:)
    thread_ids = commentable.comments.reviewed.pluck(:thread)
      .paginate(page: page, per_page: Comment.per_page)

    wrap_thread_ids(thread_ids)
  end

  # Override the comments association.
  def comments
    @comments ||= []
  end

  # Override the reviewed_replies association.
  def reviewed_replies
    @reviewed_replies ||= comments.reject(&:unreviewed?)
  end
end
