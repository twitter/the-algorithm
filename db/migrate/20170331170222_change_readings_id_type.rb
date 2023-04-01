class ChangeReadingsIdType < ActiveRecord::Migration[4.2]
  def up
    change_column :readings, :id, "bigint NOT NULL AUTO_INCREMENT"
  end

  def down
  end
end
