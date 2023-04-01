class AddApprovedToCreatorships < ActiveRecord::Migration[5.1]
  def change
    # We set the default to true here because we want all pre-existing records
    # to count as approved.
    add_column :creatorships, :approved, :boolean, null: false, default: true
  end
end
