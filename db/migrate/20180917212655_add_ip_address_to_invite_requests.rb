class AddIpAddressToInviteRequests < ActiveRecord::Migration[5.1]
  def change
    add_column :invite_requests, :ip_address, :string
  end
end
