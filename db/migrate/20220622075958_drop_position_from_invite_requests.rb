class DropPositionFromInviteRequests < ActiveRecord::Migration[6.0]
  def change
    remove_column :invite_requests, :position, :integer
  end
end
