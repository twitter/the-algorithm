class RemovePromptRestrictionIdColumn < ActiveRecord::Migration[6.0]
  def change
    remove_column :gift_exchanges, :prompt_restriction_id, :integer
    remove_column :prompt_memes, :prompt_restriction_id, :integer
  end
end
