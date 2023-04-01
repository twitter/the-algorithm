class AddIndexToPreferences < ActiveRecord::Migration[5.1]
  def change
    add_index :preferences, :skin_id
  end
end
