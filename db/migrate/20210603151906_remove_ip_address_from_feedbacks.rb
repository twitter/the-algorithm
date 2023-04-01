class RemoveIpAddressFromFeedbacks < ActiveRecord::Migration[5.2]
  def change
    remove_column :feedbacks, :ip_address, :string
  end
end
