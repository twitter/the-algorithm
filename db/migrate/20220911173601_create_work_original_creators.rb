class CreateWorkOriginalCreators < ActiveRecord::Migration[6.0]
  def change
    create_table :work_original_creators do |t|
      t.references :work, null: false
      t.references :user, null: false

      t.timestamps
    end

    add_index :work_original_creators, %i[work_id user_id], unique: true
  end
end
