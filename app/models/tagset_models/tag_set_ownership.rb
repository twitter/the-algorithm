class TagSetOwnership < ApplicationRecord
  belongs_to :pseud
  belongs_to :owned_tag_set
end
