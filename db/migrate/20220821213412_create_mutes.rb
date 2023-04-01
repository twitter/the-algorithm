class CreateMutes < ActiveRecord::Migration[6.0]
  def change
    create_table :mutes do |t|
      t.references :muter
      t.references :muted

      t.index [:muter_id, :muted_id], unique: true

      t.timestamps
    end
  end
end
