class RenameTaggingsCountToTaggingsCountCache < ActiveRecord::Migration[4.2]
  def up
    rename_column :tags, :taggings_count, :taggings_count_cache
    add_index :tags, :taggings_count_cache
  end

  def down
    rename_column :tags, :taggings_count_cache, :taggings_count
    remove_index :tags, :taggings_count
  end
end
