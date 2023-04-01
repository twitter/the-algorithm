class DropDisableFilteringFromAdminSettings < ActiveRecord::Migration[5.1]
  def change
    remove_column :admin_settings, :disable_filtering, :boolean, default: false, null: false
  end
end
