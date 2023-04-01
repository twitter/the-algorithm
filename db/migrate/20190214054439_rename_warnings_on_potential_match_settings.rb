class RenameWarningsOnPotentialMatchSettings < ActiveRecord::Migration[5.1]
  def change
    rename_column :potential_match_settings, :num_required_warnings, :num_required_archive_warnings
    rename_column :potential_match_settings, :include_optional_warnings, :include_optional_archive_warnings
  end
end
