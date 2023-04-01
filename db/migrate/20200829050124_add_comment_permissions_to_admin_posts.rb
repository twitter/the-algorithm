class AddCommentPermissionsToAdminPosts < ActiveRecord::Migration[5.1]
  def change
    add_column :admin_posts, :comment_permissions, :tinyint, default: 0, null: false
  end
end
