class AdminPostPolicy < ApplicationPolicy
  POSTING_ROLES = %w[superadmin board communications support translation].freeze

  def can_post?
    user_has_roles?(POSTING_ROLES)
  end

  alias new? can_post?
  alias edit? can_post?
  alias create? can_post?
  alias update? can_post?
  alias destroy? can_post?
end
