class AdminActivityPolicy < ApplicationPolicy
  PERMITTED_ROLES = %w[policy_and_abuse superadmin].freeze

  def index?
    user_has_roles?(PERMITTED_ROLES)
  end

  alias show? index?
end
