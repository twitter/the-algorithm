class AddIndexesToNominations < ActiveRecord::Migration[5.1]
  def change
    add_index :tag_nominations, [:type, :fandom_nomination_id]
    add_index :tag_nominations, [:type, :synonym]
    add_index :tag_nominations, [:type, :tagname]
  end
end
