class Kudo < ApplicationRecord
  include Responder

  VALID_COMMENTABLE_TYPES = %w[Work].freeze

  belongs_to :user
  belongs_to :commentable, polymorphic: true

  validates :commentable_type, inclusion: { in: VALID_COMMENTABLE_TYPES }
  validates :commentable,
            presence: true,
            if: proc { |c| VALID_COMMENTABLE_TYPES.include?(c.commentable_type) }

  validate :cannot_be_author, on: :create
  def cannot_be_author
    return unless user&.is_author_of?(commentable)

    errors.add(:commentable, :author_on_own_work)
  end

  validate :guest_cannot_kudos_restricted_work, on: :create
  def guest_cannot_kudos_restricted_work
    return unless user.blank? && commentable.is_a?(Work) && commentable.restricted?

    errors.add(:commentable, :guest_on_restricted)
  end

  validate :cannot_be_suspended, on: :create
  def cannot_be_suspended
    return unless user&.banned || user&.suspended

    if user.banned
      errors.add(:commentable, :user_is_banned)
    else
      errors.add(:commentable, :user_is_suspended)
    end
  end

  validates :ip_address,
            uniqueness: { scope: [:commentable_id, :commentable_type] },
            if: proc { |kudo| kudo.ip_address.present? }

  validates :user_id,
            uniqueness: { scope: [:commentable_id, :commentable_type] },
            if: proc { |kudo| kudo.user.present? }

  scope :with_user, -> { where("user_id IS NOT NULL") }
  scope :by_guest, -> { where("user_id IS NULL") }

  after_destroy :update_work_stats
  after_create :after_create, :update_work_stats
  def after_create
    users = self.commentable.pseuds.map(&:user).uniq

    users.each do |user|
      if notify_user_by_email?(user)
        RedisMailQueue.queue_kudo(user, self)
      end
    end
  end

  after_save :expire_caches
  def expire_caches
    if commentable_type == "Work"
      # Expire the work's cached total kudos count.
      Rails.cache.delete("works/#{commentable_id}/kudos_count-v2")
      # If it's a guest kudo, also expire the work's cached guest kudos count.
      Rails.cache.delete("works/#{commentable_id}/guest_kudos_count-v2") if user_id.nil?
    end

    # Expire the cached kudos section under the work.
    ActionController::Base.new.expire_fragment("#{commentable.cache_key}/kudos-v3")
  end

  def notify_user_by_email?(user)
    user.nil? ? false : ( user.is_a?(Admin) ? true :
      !(user == User.orphan_account || user.preference.kudos_emails_off?) )
  end

  # Return either the name of the kudo-giver or "guest".
  # Used in kudo notifications.
  def name
    if self.user
      user.login
    else
      "guest"
    end
  end
end
