begin
  ActiveRecord::Base.connection
  # try to set language and locale using models (which use Archive Config)
  Language.default
  Locale.default
rescue ActiveRecord::NoDatabaseError
  # No database, this happens when we run rake db:create
rescue ActiveRecord::ConnectionNotEstablished
  # no database connection
rescue
  # ArchiveConfig didn't work, try to set it manually
  if Language.table_exists? && Locale.table_exists?
    language = Language.find_or_create_by(short: 'en', name: 'English')
    Locale.set_base_locale(iso: "en", name: "English (US)", language_id: language.id)
  end
end
