class AddSupportAvailableToLanguages < ActiveRecord::Migration[4.2]
  def change
    add_column :languages, :support_available, :boolean, default: false, null: false
  end
end
