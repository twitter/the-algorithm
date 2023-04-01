class ChangeCreatorshipApprovedDefault < ActiveRecord::Migration[5.1]
  def change
    # Once all pre-existing records have been marked as approved, we want to
    # change the default to unapproved.
    change_column_default :creatorships, :approved, from: true, to: false
  end
end
