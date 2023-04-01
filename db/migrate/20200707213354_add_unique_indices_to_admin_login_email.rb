class AddUniqueIndicesToAdminLoginEmail < ActiveRecord::Migration[5.1]
  def change
    add_index :admins, :email, unique: true
    add_index :admins, :login, unique: true
  end
end
