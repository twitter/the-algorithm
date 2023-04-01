module BlockHelper
  def block_link(user, block: nil)
    if block.nil?
      block = user.block_by_current_user
      blocking_user = current_user
    else
      blocking_user = block.blocker
    end

    if block
      link_to(t("blocked.unblock"), confirm_unblock_user_blocked_user_path(blocking_user, block))
    else
      link_to(t("blocked.block"), confirm_block_user_blocked_users_path(blocking_user, blocked_id: user))
    end
  end

  def blocked_by?(object)
    return false unless current_user

    blocker = users_for(object)

    # Users can't be blocked by their own creations, even if one of their
    # co-creators has blocked them:
    return false if blocker.include?(current_user)

    blocker.any?(&:block_of_current_user)
  end

  def blocked_by_comment?(comment)
    (comment.is_a?(Comment) || comment.is_a?(CommentDecorator)) &&
      comment.parent_type != "Tag" &&
      blocked_by?(comment)
  end

  def users_for(object)
    if object.is_a?(User)
      [object]
    elsif object.respond_to?(:user)
      [object.user].compact
    elsif object.respond_to?(:users)
      object.users
    else
      []
    end
  end
end
