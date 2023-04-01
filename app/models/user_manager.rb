# Allows admins to manage user status via the admin users interface
class UserManager
  attr_reader :admin,
              :user,
              :admin_note,
              :admin_action,
              :suspension_length,
              :errors,
              :successes

  PERMITTED_ACTIONS = %w[note warn suspend unsuspend ban unban spamban].freeze

  def initialize(admin, user, params)
    @admin = admin
    @user = user
    @admin_note = params[:admin_note]
    @admin_action = params[:admin_action]
    @suspension_length = params[:suspend_days]
    @errors = []
    @successes = []
  end

  def save
    validate_user_and_admin &&
      validate_orphan_account &&
      validate_admin_note &&
      validate_suspension &&
      save_admin_action
  end

  def success_message
    successes.join(" ")
  end

  def error_message
    errors.join(" ")
  end

  private

  def validate_user_and_admin
    if user && admin
      true
    else
      errors << "Must have a valid user and admin account to proceed."
      false
    end
  end

  def validate_orphan_account
    if user == User.orphan_account
      errors << "orphan_account cannot be warned, suspended, or banned."
      false
    else
      true
    end
  end

  def validate_admin_note
    return true if admin_note.present? || admin_action.blank?

    if admin_action == "spamban"
      @admin_note = "Banned for spam"
    elsif admin_action.present?
      errors << "You must include notes in order to perform this action."
      false
    end
  end

  def validate_suspension
    if admin_action == "suspend" && suspension_length.blank?
      errors << "Please enter the number of days for which the user should be suspended."
      false
    else
      true
    end
  end

  def save_admin_action
    return true if admin_action.blank?

    send("#{admin_action}_user") if PERMITTED_ACTIONS.include?(admin_action)
  end

  def note_user
    log_action(ArchiveConfig.ACTION_NOTE)
    successes << "Note was recorded."
  end

  def warn_user
    log_action(ArchiveConfig.ACTION_WARN)
    successes << "Warning was recorded."
  end

  def suspend_user
    user.suspended = true
    user.suspended_until = suspension_length.to_i.days.from_now
    user.save!
    log_action(ArchiveConfig.ACTION_SUSPEND, enddate: user.suspended_until)
    successes << "User has been temporarily suspended."
  end

  def unsuspend_user
    user.suspended = false
    user.suspended_until = nil
    user.save!
    log_action(ArchiveConfig.ACTION_UNSUSPEND)
    successes << "Suspension has been lifted."
  end

  def ban_user
    user.banned = true
    user.save!
    log_action(ArchiveConfig.ACTION_BAN)
    successes << "User has been permanently suspended."
  end
  alias spamban_user ban_user

  def unban_user
    user.banned = false
    user.save!
    log_action(ArchiveConfig.ACTION_UNSUSPEND)
    successes << "Suspension has been lifted."
  end

  def log_action(message, options = {})
    options.merge!(
      action: message, 
      note: admin_note, 
      admin_id: admin.id
    )
    user.create_log_item(options)
  end
end
