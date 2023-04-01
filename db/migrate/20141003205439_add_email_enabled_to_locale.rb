class AddEmailEnabledToLocale < ActiveRecord::Migration[4.2]
  def change
    add_column :locales, :email_enabled, :boolean, default: false, null: false
  end
end
