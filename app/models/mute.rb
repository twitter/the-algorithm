class Mute < ApplicationRecord
  include MuteHelper

  belongs_to :muter, class_name: "User"
  belongs_to :muted, class_name: "User"

  validates :muter, :muted, presence: true
  validates :muted_id, uniqueness: { scope: :muter_id }

  validate :check_self
  def check_self
    errors.add(:muted, :self) if muted == muter
  end

  validate :check_official, if: :muted
  def check_official
    errors.add(:muted, :official) if muted.official
  end

  validate :check_mute_limit
  def check_mute_limit
    errors.add(:muted, :limit) if muter.muted_users.count >= ArchiveConfig.MAX_MUTED_USERS
  end

  after_create :update_cache
  after_destroy :update_cache
  def update_cache
    Rails.cache.write(mute_css_key(muter), mute_css_uncached(muter))
  end

  def muted_byline=(byline)
    pseuds = Pseud.parse_byline(byline, assume_matching_login: true)
    self.muted = pseuds.first.user unless pseuds.empty?
  end
end
