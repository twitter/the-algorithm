class ProfilePolicy < ApplicationPolicy
  # Roles that allow updating a user's profile.
  EDIT_ROLES = %w[superadmin policy_and_abuse].freeze

  def can_edit_profile?
    user_has_roles?(EDIT_ROLES)
  end

  alias update? can_edit_profile?
end
