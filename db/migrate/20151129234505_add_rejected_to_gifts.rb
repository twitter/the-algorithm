class AddRejectedToGifts < ActiveRecord::Migration[4.2]
  def change
    add_column :gifts, :rejected, :boolean, default: false, null: false
  end
end
