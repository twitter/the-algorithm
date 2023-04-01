class Preference < ApplicationRecord
  self.ignored_columns = [:automatically_approve_collections]

  belongs_to :user
  belongs_to :skin

  validates_format_of :work_title_format, with: /^[a-zA-Z0-9_\-,\. ]+$/,
    message: ts("can only contain letters, numbers, spaces, and some limited punctuation (comma, period, dash, underscore)."),
    multiline: true

  validate :can_use_skin, if: :skin_id_changed?

  before_create :set_default_skin
  def set_default_skin
    self.skin_id = AdminSetting.current.default_skin_id
  end

  def self.disable_work_skin?(param)
     return false if param == 'creator'
     return true if param == 'light' || param == 'disable'
     return false unless User.current_user.is_a? User
     return User.current_user.try(:preference).try(:disable_work_skins)
  end

  def can_use_skin
    return if skin_id == AdminSetting.default_skin_id ||
              (skin.is_a?(Skin) && skin.approved_or_owned_by?(user))

    errors.add(:base, "You don't have permission to use that skin!")
  end
end
