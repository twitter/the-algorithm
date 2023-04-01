class AdminBannerPolicy < ApplicationPolicy
  def index?
    user_has_roles?(%w[superadmin board communications support])
  end

  alias show? index?
  alias create? index?
  alias update? index?
  alias destroy? index?
end
