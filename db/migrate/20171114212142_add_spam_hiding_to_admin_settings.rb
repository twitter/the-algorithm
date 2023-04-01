class AddSpamHidingToAdminSettings < ActiveRecord::Migration[5.1]
  def change
    add_column :admin_settings, :hide_spam, :boolean, default: false, null: false
  end
end
