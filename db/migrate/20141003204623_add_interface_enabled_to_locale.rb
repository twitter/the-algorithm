class AddInterfaceEnabledToLocale < ActiveRecord::Migration[4.2]
  def change
    add_column :locales, :interface_enabled, :boolean, default: false, null: false
  end
end
