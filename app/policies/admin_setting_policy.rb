class AdminSettingPolicy < ApplicationPolicy
  # Defines the roles that allow admins to view all settings.
  SETTINGS_ROLES = %w[policy_and_abuse superadmin support tag_wrangling].freeze

  # Define which roles can update which settings.
  ALLOWED_SETTINGS_BY_ROLES = {
    "policy_and_abuse" => %i[
      hide_spam
      invite_from_queue_enabled
      invite_from_queue_number
    ],
    "superadmin" => %i[
      account_creation_enabled
      cache_expiration
      creation_requires_invite
      days_to_purge_unactivated
      disable_support_form
      disabled_support_form_text
      downloads_enabled
      enable_test_caching
      hide_spam
      invite_from_queue_enabled
      invite_from_queue_frequency
      invite_from_queue_number
      request_invite_enabled
      suspend_filter_counts
      tag_wrangling_off
    ],
    "support" => %i[disable_support_form disabled_support_form_text],
    "tag_wrangling" => %i[tag_wrangling_off]
  }.freeze

  def can_view_settings?
    user_has_roles?(SETTINGS_ROLES)
  end

  def permitted_attributes
    ALLOWED_SETTINGS_BY_ROLES.values_at(*user.roles).compact.flatten
  end

  alias index? can_view_settings?
  alias update? can_view_settings?
end
