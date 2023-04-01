class AddLocaleToPreferences < ActiveRecord::Migration[4.2]
  def change
    add_column :preferences, :preferred_locale, :integer, default: 1, null: false
  end
end
