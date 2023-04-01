class WorkPolicy < UserCreationPolicy
  def show_admin_options?
    super || edit_tags? || set_spam?
  end

  # Allow admins to edit work tags and languages.
  # Include support admins due to AO3-4932.
  def update_tags?
    user_has_roles?(%w[superadmin policy_and_abuse support])
  end

  alias edit_tags? update_tags?

  # Support admins need to be able to delete duplicate works.
  def destroy?
    super || user_has_roles?(%w[support])
  end

  def set_spam?
    user_has_roles?(%w[superadmin policy_and_abuse])
  end
end
