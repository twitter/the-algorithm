class CommentMailer < ApplicationMailer
  # Sends email to an owner of the top-level commentable when a new comment is created
  def comment_notification(user, comment)
    @comment = comment
    @owner = user
    I18n.with_locale(Locale.find(user.preference.preferred_locale).iso) do
      mail(
        to: user.email,
        subject: "[#{ArchiveConfig.APP_SHORT_NAME}] Comment on " + (@comment.ultimate_parent.is_a?(Tag) ? "the tag " : "") + @comment.ultimate_parent.commentable_name.gsub("&gt;", ">").gsub("&lt;", "<")
      )
    end
  end

  # Sends email to an owner of the top-level commentable when a comment is edited
  def edited_comment_notification(user, comment)
    @comment = comment
    I18n.with_locale(Locale.find(user.preference.preferred_locale).iso) do
      mail(
        to: user.email,
        subject: "[#{ArchiveConfig.APP_SHORT_NAME}] Edited comment on " + (@comment.ultimate_parent.is_a?(Tag) ? "the tag " : "") + @comment.ultimate_parent.commentable_name.gsub("&gt;", ">").gsub("&lt;", "<")
      )
    end
  end

  # Sends email to commenter when a reply is posted to their comment
  # This may be a non-user of the archive
  def comment_reply_notification(your_comment, comment)
    return if your_comment.comment_owner_email.blank?
    return if your_comment.pseud_id.nil? && AdminBlacklistedEmail.is_blacklisted?(your_comment.comment_owner_email)

    @your_comment = your_comment
    @comment = comment
    mail(
      to: @your_comment.comment_owner_email,
      subject: "[#{ArchiveConfig.APP_SHORT_NAME}] Reply to your comment on " + (@comment.ultimate_parent.is_a?(Tag) ? "the tag " : "") + @comment.ultimate_parent.commentable_name.gsub("&gt;", ">").gsub("&lt;", "<")
    )
  end

  # Sends email to commenter when a reply to their comment is edited
  # This may be a non-user of the archive
  def edited_comment_reply_notification(your_comment, edited_comment)
    return if your_comment.comment_owner_email.blank?
    return if your_comment.pseud_id.nil? && AdminBlacklistedEmail.is_blacklisted?(your_comment.comment_owner_email)
    return if your_comment.is_deleted?

    @your_comment = your_comment
    @comment = edited_comment
    mail(
      to: @your_comment.comment_owner_email,
      subject: "[#{ArchiveConfig.APP_SHORT_NAME}] Edited reply to your comment on " + (@comment.ultimate_parent.is_a?(Tag) ? "the tag " : "") + @comment.ultimate_parent.commentable_name.gsub("&gt;", ">").gsub("&lt;", "<")
    )
  end

  # Sends email to the poster of a comment
  def comment_sent_notification(comment)
    @comment = comment
    @noreply = true # don't give reply link to your own comment
    mail(
      to: @comment.comment_owner_email,
      subject: "[#{ArchiveConfig.APP_SHORT_NAME}] Comment you left on " + (@comment.ultimate_parent.is_a?(Tag) ? "the tag " : "") + @comment.ultimate_parent.commentable_name.gsub("&gt;", ">").gsub("&lt;", "<")
    )
  end

end
