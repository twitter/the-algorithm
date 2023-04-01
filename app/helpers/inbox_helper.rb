module InboxHelper
  # Describes commentable - used on inbox show page
  def commentable_description_link(comment)
    commentable = comment.ultimate_parent
    return ts("Deleted Object") if commentable.blank?

    if commentable.is_a?(Tag)
      link_to commentable.name, tag_comment_path(commentable, comment)
    elsif commentable.is_a?(AdminPost)
      link_to commentable.title, admin_post_comment_path(commentable, comment)
    else
      link_to commentable.title, work_comment_path(commentable, comment)
    end
  end
  
  # get_commenter_pseud_or_name can be found in comments_helper

end
