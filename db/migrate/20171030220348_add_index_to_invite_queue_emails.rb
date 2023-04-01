class AddIndexToInviteQueueEmails < ActiveRecord::Migration[5.1]
  def change
    add_index :invite_requests, :simplified_email, unique: true
  end
end
