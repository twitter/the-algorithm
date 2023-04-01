class CreateModeratedWorks < ActiveRecord::Migration[5.1]
  def change
    create_table :moderated_works do |t|
      t.references :work, null: false #, foreign_key: true
      t.boolean :approved, null: false, default: false
      t.boolean :reviewed, null: false, default: false

      t.timestamps
    end
  end
end
