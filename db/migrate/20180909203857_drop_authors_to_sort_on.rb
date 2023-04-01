class DropAuthorsToSortOn < ActiveRecord::Migration[5.1]
  def change
    remove_column :works, :authors_to_sort_on, :string
  end
end
