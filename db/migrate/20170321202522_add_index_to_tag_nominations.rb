class AddIndexToTagNominations < ActiveRecord::Migration[4.2]
  def change
    add_index :tag_nominations, :tagname
  end
end
