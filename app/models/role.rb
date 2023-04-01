# Defines named roles for users that may be applied to
# objects in a polymorphic fashion. For example, you could create a role
# "moderator" for an instance of a model (i.e., an object), a model class,
# or without any specification at all.
class Role < ApplicationRecord
  has_many :roles_users
  has_many :users, through: :roles_users
  belongs_to :authorizable, polymorphic: true

  scope :assignable, -> { where(authorizable_id: nil, authorizable_type: nil) }
end
