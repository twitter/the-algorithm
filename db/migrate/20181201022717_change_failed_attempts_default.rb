class ChangeFailedAttemptsDefault < ActiveRecord::Migration[5.1]
  def change
    change_column_default :users, :failed_attempts, from: nil, to: 0
  end
end
