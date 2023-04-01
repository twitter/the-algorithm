class IndexCommentsOnIpAddress < ActiveRecord::Migration[5.1]
  def change
    add_index :comments, :ip_address
  end
end
