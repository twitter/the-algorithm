class KudoMailer < ApplicationMailer
  # send a batched-up notification
  # user_kudos is a hash of arrays converted to JSON string format
  # [commentable_type]_[commentable_id] =>
  #   names: [array of users who left kudos with the last entry being "# guests" if any]
  #   guest_count: number of guest kudos
  def batch_kudo_notification(user_id, user_kudos)
    @commentables = []
    @user_kudos = JSON.parse(user_kudos)
    user = User.find(user_id)
    kudos_hash = JSON.parse(user_kudos)

    I18n.with_locale(Locale.find(user.preference.preferred_locale).iso) do
      kudos_hash.each_pair do |commentable_info, kudo_givers_hash|
        # Parse the key to extract the type and id of the commentable so we can
        # weed out any commentables that no longer exist.
        commentable_type, commentable_id = commentable_info.split("_")
        commentable = commentable_type.constantize.find_by(id: commentable_id)
        next unless commentable

        @commentables << commentable
      end
      mail(
        to: user.email,
        subject: default_i18n_subject(app_name: ArchiveConfig.APP_SHORT_NAME)
      )
    end
  end
end
