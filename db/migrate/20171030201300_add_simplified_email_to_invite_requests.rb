class AddSimplifiedEmailToInviteRequests < ActiveRecord::Migration[5.1]
  def change
    add_column :invite_requests, :simplified_email, :string, null: false, default: ''
  end
end
