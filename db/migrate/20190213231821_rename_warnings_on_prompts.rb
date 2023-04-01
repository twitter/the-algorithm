class RenameWarningsOnPrompts < ActiveRecord::Migration[5.1]
  def change
    rename_column :prompts, :any_warning, :any_archive_warning
  end
end
