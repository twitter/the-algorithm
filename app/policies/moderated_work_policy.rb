class ModeratedWorkPolicy < ApplicationPolicy
  MANAGE_MODERATED_WORK = %w[superadmin policy_and_abuse].freeze

  def index?
    user_has_roles?(MANAGE_MODERATED_WORK)
  end

  alias bulk_update? index?
end
