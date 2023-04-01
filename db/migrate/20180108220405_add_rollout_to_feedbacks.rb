class AddRolloutToFeedbacks < ActiveRecord::Migration[5.1]
  def change
    add_column :feedbacks, :rollout, :string
  end
end
