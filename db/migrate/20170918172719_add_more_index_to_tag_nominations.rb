class AddMoreIndexToTagNominations < ActiveRecord::Migration[5.1]
  def change
    add_index :tag_nominations, [:type, :tag_set_nomination_id]
  end
end
