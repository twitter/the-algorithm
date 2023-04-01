# This migration handle the data migration from Authlogic to Devise
#
# The only data that will be lost is the persistence_token field that can be
# regenerated again if we switch back to Authlogic. The downside of forcing
# the admins to log in again is irrelevant
#
# Devise can handle authlogic password hash, so we don't have to force all
# admins to regenerate their passwords! Phew! :)
#
class MoveAdminsToDevise < ActiveRecord::Migration[4.2]
  def self.up
    # Rename default devise columns:
    rename_column :admins, :crypted_password, :encrypted_password
    rename_column :admins, :salt, :password_salt

    # Remove old authlogic field
    remove_column :admins, :persistence_token
  end

  def self.down
    # Rename columns back
    rename_column :admins, :encrypted_password, :crypted_password
    rename_column :admins, :password_salt, :salt

    # Recreate deleted column
    add_column :admins, :persistence_token, :string
  end
end
