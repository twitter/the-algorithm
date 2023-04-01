class ExternalWorkPolicy < UserCreationPolicy
  def update?
    user_has_roles?(%w[superadmin policy_and_abuse])
  end
end
