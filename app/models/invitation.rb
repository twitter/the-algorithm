# Beta invitations
# http://railscasts.com/episodes/124-beta-invitations
class Invitation < ApplicationRecord
  belongs_to :creator, polymorphic: true
  belongs_to :invitee, polymorphic: true
  belongs_to :external_author

  validate :recipient_is_not_registered, on: :create
  def recipient_is_not_registered
    # we allow invitations to be sent to existing users if the purpose is to claim an external author
    if self.invitee_email && User.find_by(email: self.invitee_email) && !self.external_author
      errors.add :invitee_email, ts('is already being used by an account holder.')
    end
  end

  # ensure email is valid
  validates :invitee_email, email_veracity: true, allow_blank: true

  scope :unsent, -> { where(invitee_email: nil, redeemed_at: nil) }
  scope :unredeemed, -> { where('invitee_email IS NOT NULL and redeemed_at IS NULL') }
  scope :redeemed, -> { where('redeemed_at IS NOT NULL') }

  before_validation :generate_token, on: :create
  after_save :send_and_set_date
  after_save :adjust_user_invite_status

  #Create a certain number of invitations for all valid users
  def self.grant_all(total)
    raise unless total > 0 && total < 20
    User.valid.each do |user|
      total.times do
        user.invitations.create
      end
      UserMailer.invite_increase_notification(user.id, total).deliver_later
    end
    User.out_of_invites.update_all('out_of_invites = 0')
  end

  #Create a certain number of invitations for all users who are out of them
  def self.grant_empty(total)
    raise unless total > 0 && total < 20
    User.valid.out_of_invites.each do |user|
      total.times do
        user.invitations.create
      end
      UserMailer.invite_increase_notification(user.id, total).deliver_later
    end
    User.out_of_invites.update_all('out_of_invites = 0')
  end

  def mark_as_redeemed(user=nil)
    self.invitee = user
    self.redeemed_at = Time.now
    save
  end

  private

  def generate_token
    self.token = Digest::SHA1.hexdigest([Time.now, rand].join)
  end

  def send_and_set_date
    if self.saved_change_to_invitee_email? && !self.invitee_email.blank?
      begin
        if self.external_author
          archivist = self.external_author.external_creatorships.collect(&:archivist).collect(&:login).uniq.join(", ")
          # send invite synchronously for now -- this should now work delayed but just to be safe
          UserMailer.invitation_to_claim(self.id, archivist).deliver_now
        else
          # send invitations actively sent by a user synchronously to avoid delays
          UserMailer.invitation(self.id).deliver_now
        end

        # Skip callbacks within after_save by using update_column to avoid a callback loop
        self.update_column(:sent_at, Time.now)
      rescue Exception => exception
        errors.add(:base, "Notification email could not be sent: #{exception.message}")
      end
    end
  end

  #Update the user's out_of_invites status
  def adjust_user_invite_status
    if self.creator.respond_to?(:out_of_invites)
      self.creator.out_of_invites = (self.creator.invitations.unredeemed.count < 1)
      self.creator.save!(validate: false)
    end
  end
end
