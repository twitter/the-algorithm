class LanguagePolicy < ApplicationPolicy
  MANAGE_LANGUAGES = %w[superadmin translation].freeze

  def new?
    user_has_roles?(MANAGE_LANGUAGES)
  end

  alias create? new?
  alias edit? new?
  alias update? new?
end
