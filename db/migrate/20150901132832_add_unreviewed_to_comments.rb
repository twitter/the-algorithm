class AddUnreviewedToComments < ActiveRecord::Migration[4.2]
  def change
    add_column :comments, :unreviewed, :boolean, default: false, null: false
  end
end
