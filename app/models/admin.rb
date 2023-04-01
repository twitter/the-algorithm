class Admin < ApplicationRecord
  VALID_ROLES = %w[superadmin board communications translation tag_wrangling docs support policy_and_abuse open_doors].freeze

  serialize :roles, Array

  devise :database_authenticatable,
         :lockable,
         :recoverable,
         :validatable,
         password_length: ArchiveConfig.ADMIN_PASSWORD_LENGTH_MIN..ArchiveConfig.ADMIN_PASSWORD_LENGTH_MAX,
         reset_password_within: ArchiveConfig.DAYS_UNTIL_ADMIN_RESET_PASSWORD_LINK_EXPIRES.days,
         lock_strategy: :none,
         unlock_strategy: :none

  include BackwardsCompatiblePasswordDecryptor

  has_many :log_items
  has_many :invitations, as: :creator
  has_many :wrangled_tags, class_name: 'Tag', as: :last_wrangler

  validates :login,
            presence: true,
            uniqueness: true,
            length: { in: ArchiveConfig.LOGIN_LENGTH_MIN..ArchiveConfig.LOGIN_LENGTH_MAX }
  validates_presence_of :password_confirmation, if: :new_record?
  validates_confirmation_of :password, if: :new_record?

  validate :allowed_roles
  def allowed_roles
    return unless roles && (roles - VALID_ROLES).present?

    errors.add(:roles, :invalid)
  end

  # For requesting admins set a new password before their first login. Uses same
  # mechanism as password reset requests, but different email notification.
  after_create :send_set_password_notification
  def send_set_password_notification
    token = set_reset_password_token
    AdminMailer.set_password_notification(self, token).deliver
  end
end
