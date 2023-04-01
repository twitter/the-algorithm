class WranglingAssignment < ApplicationRecord
  belongs_to :user
  belongs_to :fandom
  
  validates_uniqueness_of :user_id, scope: :fandom_id
  validates_presence_of :user_id
  validates_presence_of :fandom_id
  
end
