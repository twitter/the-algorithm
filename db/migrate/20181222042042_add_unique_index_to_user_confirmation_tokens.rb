class AddUniqueIndexToUserConfirmationTokens < ActiveRecord::Migration[5.1]
  def change
    remove_index :users, :confirmation_token
    add_index :users, :confirmation_token, unique: true
  end
end
