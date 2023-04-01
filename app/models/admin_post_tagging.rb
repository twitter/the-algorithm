class AdminPostTagging < ApplicationRecord
  belongs_to :admin_post
  belongs_to :admin_post_tag
end
