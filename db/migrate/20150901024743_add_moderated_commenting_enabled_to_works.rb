class AddModeratedCommentingEnabledToWorks < ActiveRecord::Migration[4.2]
  def change
    add_column :works, :moderated_commenting_enabled, :boolean, default: false, null: false
  end
end
