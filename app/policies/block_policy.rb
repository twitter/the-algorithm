class BlockPolicy < ApplicationPolicy
  def index?
    user_has_roles?(%w[policy_and_abuse support superadmin])
  end
end
