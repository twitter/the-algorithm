class RenameWarningsOnPromptRestrictions < ActiveRecord::Migration[5.1]
  def change
    rename_column :prompt_restrictions, :warning_num_required, :archive_warning_num_required
    rename_column :prompt_restrictions, :warning_num_allowed, :archive_warning_num_allowed
    rename_column :prompt_restrictions, :require_unique_warning, :require_unique_archive_warning
    rename_column :prompt_restrictions, :allow_any_warning, :allow_any_archive_warning
  end
end
