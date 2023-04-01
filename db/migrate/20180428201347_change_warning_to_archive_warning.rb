class ChangeWarningToArchiveWarning < ActiveRecord::Migration[5.1]
  def up
    execute "UPDATE tags SET type = 'ArchiveWarning' WHERE type = 'Warning'"
  end

  def down
    execute "UPDATE tags SET type = 'Warning' WHERE type = 'ArchiveWarning'"
  end
end
