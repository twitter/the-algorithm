class MovesUsersToDevise < ActiveRecord::Migration[5.1]
  def up
    change_table :users do |t|
      # Remove old authlogic field
      t.remove :persistence_token

      # Database authenticatable
      t.rename :crypted_password, :encrypted_password
      t.rename :salt, :password_salt

      # Confirmable
      t.rename :activation_code, :confirmation_token
      t.rename :activated_at, :confirmed_at
      t.datetime :confirmation_sent_at

      # Recoverable
      t.string :reset_password_token
      t.index :reset_password_token, unique: true
      t.datetime :reset_password_sent_at

      # Rememberable
      t.datetime :remember_created_at

      # Trackable
      t.integer :sign_in_count, default: 0
      t.datetime :current_sign_in_at
      t.datetime :last_sign_in_at
      t.string :current_sign_in_ip
      t.string :last_sign_in_ip

      # Lockable
      t.rename :failed_login_count, :failed_attempts
      t.string :unlock_token
      t.index :unlock_token, unique: true
      t.datetime :locked_at
    end
  end

  def down
    change_table :users do |t|
      # Remove old authlogic field
      t.string :persistence_token

      # Database authenticatable
      t.rename :encrypted_password, :crypted_password
      t.rename :password_salt, :salt

      # Confirmable
      t.rename :confirmation_token, :activation_code
      t.rename :confirmed_at, :activated_at
      t.remove :confirmation_sent_at

      # Recoverable
      t.remove :reset_password_token
      t.remove :reset_password_sent_at

      # Rememberable
      t.remove :remember_created_at

      # Trackable
      t.remove :sign_in_count
      t.remove :current_sign_in_at
      t.remove :last_sign_in_at
      t.remove :current_sign_in_ip
      t.remove :last_sign_in_ip

      # Lockable
      t.rename :failed_attempts, :failed_login_count
      t.remove :unlock_token
      t.remove :locked_at
    end
  end
end
