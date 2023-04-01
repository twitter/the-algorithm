class LocalePolicy < ApplicationPolicy
  MANAGE_LOCALES = %w[superadmin translation].freeze

  def index?
    user_has_roles?(MANAGE_LOCALES)
  end

  alias new? index?
  alias edit? index?
  alias update? index?
  alias create? index?
end
