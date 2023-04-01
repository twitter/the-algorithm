module Globalized
  extend ActiveSupport::Concern

  included do
    validate :check_locale
  end

  def check_locale
    return if Locale.exists?(iso: locale)

    globalized_model.errors.add(:base, ts("The locale %{name} does not exist.", name: locale))
  end
end
