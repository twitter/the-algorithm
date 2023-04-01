class AddAcceptedTosVersionToUsers < ActiveRecord::Migration[5.1]
  def change
    add_column :users, :accepted_tos_version, :integer
  end
end
