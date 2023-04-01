class CreateBlocks < ActiveRecord::Migration[6.0]
  def change
    create_table :blocks do |t|
      t.references :blocker
      t.references :blocked

      t.index [:blocker_id, :blocked_id], unique: true

      t.timestamps
    end
  end
end
