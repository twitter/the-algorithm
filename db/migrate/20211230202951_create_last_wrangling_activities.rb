class CreateLastWranglingActivities < ActiveRecord::Migration[6.0]
  def change
    create_table :last_wrangling_activities do |t|
      t.references :user,
                   null: false,
                   dependent: :destroy,
                   index: { unique: true }

      t.timestamps
    end
  end
end
