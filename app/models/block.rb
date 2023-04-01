class Block < ApplicationRecord
  belongs_to :blocker, class_name: "User"
  belongs_to :blocked, class_name: "User"

  validates :blocker, :blocked, presence: true
  validates :blocked_id, uniqueness: { scope: :blocker_id }

  validate :check_self
  def check_self
    errors.add(:blocked, :self) if blocked == blocker
  end

  validate :check_official, if: :blocked
  def check_official
    errors.add(:blocked, :official) if blocked.official
  end

  validate :check_block_limit
  def check_block_limit
    errors.add(:blocked, :limit) if blocker.blocked_users.count >= ArchiveConfig.MAX_BLOCKED_USERS
  end

  def blocked_byline=(byline)
    pseuds = Pseud.parse_byline(byline, assume_matching_login: true)
    self.blocked = pseuds.first.user unless pseuds.empty?
  end
end
