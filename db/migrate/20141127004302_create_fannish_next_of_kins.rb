class CreateFannishNextOfKins < ActiveRecord::Migration[4.2]
  def change
    create_table :fannish_next_of_kins do |t|
      t.integer :user_id
      t.integer :kin_id
      t.string :kin_email
    end
    add_index :fannish_next_of_kins, :user_id
  end
end
