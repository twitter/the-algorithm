class IndexTagSetNominations < ActiveRecord::Migration[5.2]
  def change
    add_index :tag_set_nominations, [:owned_tag_set_id]
    add_index :tag_set_nominations, [:pseud_id, :owned_tag_set_id]
  end
end
