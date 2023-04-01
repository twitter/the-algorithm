class UserCreationPolicy < ApplicationPolicy
  def show_admin_options?
    destroy? || hide? || edit?
  end

  def destroy?
    user_has_roles?(%w[superadmin policy_and_abuse])
  end

  def hide?
    user_has_roles?(%w[superadmin policy_and_abuse])
  end

  def show_ip_address?
    user_has_roles?(%w[superadmin policy_and_abuse])
  end

  def show_original_creators?
    user_has_roles?(%w[superadmin policy_and_abuse])
  end
end
