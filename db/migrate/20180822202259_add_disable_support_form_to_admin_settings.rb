class AddDisableSupportFormToAdminSettings < ActiveRecord::Migration[5.1]
  def change
    add_column :admin_settings, :disable_support_form, :boolean, default: false, null: false
    add_column :admin_settings, :disabled_support_form_text, :text, null: true
    add_column :admin_settings, :disabled_support_form_text_sanitizer_version, :integer, default: 0, null: false, limit: 2
  end
end
