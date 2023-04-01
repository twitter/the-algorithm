class RemoveRecentlyResetFromUsers < ActiveRecord::Migration[5.1]
  def change
    remove_column :users, :recently_reset, :boolean
  end
end
