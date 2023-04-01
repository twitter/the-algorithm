class AddRecoverableLockableToAdmins < ActiveRecord::Migration[6.0]
  def change
    # Recoverable
    add_column :admins, :reset_password_token, :string
    add_index :admins, :reset_password_token, unique: true
    add_column :admins, :reset_password_sent_at, :datetime

    # Lockable
    add_column :admins, :locked_at, :datetime
  end
end
