class UserInviteRequest < ApplicationRecord
  MAX_USER_INVITE_REQUEST = ArchiveConfig.MAX_USER_INVITE_REQUEST

  belongs_to :user
  validates_presence_of :quantity
  validates_presence_of :reason
  validates :quantity, numericality: {less_than_or_equal_to: MAX_USER_INVITE_REQUEST}

  before_update :check_status, :grant_request

  scope :not_handled, -> { where(handled: false) }

  private

  #Mark the request granted and/or handled as appropriate
  def check_status
    self.handled = true
    if self.quantity > 0
      self.granted = true
    end
  end

  #Create new invitations for the user who requested them
  def grant_request
    if self.granted?
      self.quantity.times do
        self.user.invitations.create
      end
      UserMailer.invite_increase_notification(self.user.id, self.quantity).deliver_after_commit
    end
  end
end
