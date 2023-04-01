class RemoveGuestDownloadingOffFromAdminSettings < ActiveRecord::Migration[5.1]
  def change
    remove_column :admin_settings, :guest_downloading_off, :boolean
  end
end
