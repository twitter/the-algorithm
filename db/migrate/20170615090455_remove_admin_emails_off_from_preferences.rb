class RemoveAdminEmailsOffFromPreferences < ActiveRecord::Migration[4.2]
  def change
    remove_column :preferences, :admin_emails_off, :boolean
  end
end
