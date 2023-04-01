class UserPolicy < ApplicationPolicy
  # Roles that allow:
  # - troubleshooting for a user
  # - managing a user's invitations
  # - updating a user's email and roles (e.g. wranglers, archivists, not admin roles)
  # This is further restricted using ALLOWED_ATTRIBUTES_BY_ROLES.
  MANAGE_ROLES = %w[superadmin policy_and_abuse open_doors support tag_wrangling].freeze

  # Roles that allow updating the Fannish Next Of Kin of a user.
  MANAGE_NEXT_OF_KIN_ROLES = %w[superadmin policy_and_abuse support].freeze

  # Roles that allow deleting all of a spammer's creations.
  SPAM_CLEANUP_ROLES = %w[superadmin policy_and_abuse].freeze

  # Define which roles can update which attributes.
  ALLOWED_ATTRIBUTES_BY_ROLES = {
    "open_doors" => [roles: []],
    "policy_and_abuse" => [:email, roles: []],
    "superadmin" => [:email, roles: []],
    "support" => %i[email],
    "tag_wrangling" => [roles: []]
  }.freeze

  def can_manage_users?
    user_has_roles?(MANAGE_ROLES)
  end

  def can_manage_next_of_kin?
    user_has_roles?(MANAGE_NEXT_OF_KIN_ROLES)
  end

  def can_destroy_spam_creations?
    user_has_roles?(SPAM_CLEANUP_ROLES)
  end

  def permitted_attributes
    ALLOWED_ATTRIBUTES_BY_ROLES.values_at(*user.roles).compact.flatten
  end

  alias index? can_manage_users?
  alias bulk_search? can_manage_users?
  alias show? can_manage_users?
  alias update? can_manage_users?

  alias update_next_of_kin? can_manage_next_of_kin?

  alias confirm_delete_user_creations? can_destroy_spam_creations?
  alias destroy_user_creations? can_destroy_spam_creations?

  alias troubleshoot? can_manage_users?
  alias send_activation? can_manage_users?
  alias activate? can_manage_users?
end
