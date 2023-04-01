class AddSortableNameToLanguages < ActiveRecord::Migration[5.1]
  def change
    add_column :languages, :sortable_name, :string, default: "", null: false
    add_index :languages, :sortable_name
  end
end
