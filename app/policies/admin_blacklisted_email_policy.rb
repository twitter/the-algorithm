class AdminBlacklistedEmailPolicy < ApplicationPolicy
  def index?
    user_has_roles?(%w[superadmin policy_and_abuse support])
  end

  alias create? index?
  alias destroy? index?
end
