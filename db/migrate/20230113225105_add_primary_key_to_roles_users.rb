class AddPrimaryKeyToRolesUsers < ActiveRecord::Migration[6.0]
  def change
    add_column :roles_users, :id, :primary_key
  end
end
