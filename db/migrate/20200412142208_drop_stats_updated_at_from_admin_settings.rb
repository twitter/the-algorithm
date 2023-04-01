class DropStatsUpdatedAtFromAdminSettings < ActiveRecord::Migration[5.1]
  def change
    remove_column :admin_settings, :stats_updated_at, :datetime
  end
end
