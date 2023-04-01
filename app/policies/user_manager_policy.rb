class UserManagerPolicy < ApplicationPolicy
  # Roles that allow adding notes to users.
  NOTE_ROLES = %w[superadmin policy_and_abuse support].freeze

  # Roles that allow warning, suspending, and banning users.
  JUDGE_ROLES = %w[superadmin policy_and_abuse].freeze

  def can_manage_users?
    user_has_roles?(NOTE_ROLES) || user_has_roles?(JUDGE_ROLES)
  end

  def update_status?
    return true if user_has_roles?(JUDGE_ROLES)
    return record.admin_action == "note" if user_has_roles?(NOTE_ROLES)

    false
  end
end
